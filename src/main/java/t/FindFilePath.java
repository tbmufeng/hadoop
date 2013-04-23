package t;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class FindFilePath {

	private static Set<String> titles = new HashSet<String>();
	static {
		titles.add("������˹ ����ҹ�� �ֱ� 1080P ��������� 8G�ڴ� 1920*1080P ��Ƶ�ֱ���");
		titles.add("������˹ ����ҹ�� ʱ���ֱ� 1080P ���� ���� �����ֱ� ����� 16G�ڴ� 1920*1080 ��Ƶ�ֱ���");
		titles.add("������˹ ����ҹ�� �ֱ� 1080P ��������� 32G�ڴ� 1920*1080P ��Ƶ�ֱ���");
		titles.add("������JZ9988 ƫƵ�״� �����̶����ٵ���Ԥ���� ��С �������� ��ɫjz9988+;");
		titles.add("������JZ9988+ ƫƵ�� �� �����̶�����һ��� ��ɫ");
		titles.add("��Ʒ ������JZ99 ����JZ99 ����JZ998 JZ9988 JZ980 GPS18");
		titles.add("������JZ9988 ƫƵ�״� �����̶����ٵ���Ԥ���� ��С �������� ��ɫjz9988");
	}
	static boolean show = false;

	public static class TokenizerMapper extends
			Mapper<Object, Text, Text, Text> {

		public void map(Object key, Text value, Context context)
				throws IOException, InterruptedException {
			String line = new String(value.getBytes(), "gb2312");

			int i = -1;
			if ((i = line.indexOf("=")) > -1 && i < line.length() - 4) {
				String k = line.substring(0, i).trim();
				String val = line.substring(i + 1).trim();

				if ("title".equals(k)) {
					if (!show) {
						context.write(new Text("[" + key + "]"), new Text(val));
						show = true;
					}
					if (titles.contains(val)) {
						context.write(
								new Text("{" + val + "}"),
								new Text(ToStringBuilder
										.reflectionToString(context,
												ToStringStyle.MULTI_LINE_STYLE)));
					}
				}
			}
		}
	}

	public static class IntSumReducer extends Reducer<Text, Text, Text, Text> {

		public void reduce(Text key, Iterable<Text> values, Context context)
				throws IOException, InterruptedException {

			for (Text txt : values) {
				context.write(key, txt);
			}
		}
	}

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		String[] otherArgs = args;
		if (otherArgs.length != 2) {
			System.err
					.println("Usage: </group/etao/leopard/20130123/b2c/> </group/tbctu/mufeng/out>");
			System.exit(2);
		}
		Job job = new Job(conf, "word count");
		conf.set("ugi", "mufeng.qcg");
		conf.set("fs.default.name", "hdfs://v129201.sqa.cm4.tbsite.net:9000//");
		job.setJarByClass(FindFilePath.class);
		job.setMapperClass(TokenizerMapper.class);
		job.setCombinerClass(IntSumReducer.class);
		job.setReducerClass(IntSumReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
		Path p = new Path(otherArgs[1]);
		FileSystem fs = FileSystem.get(conf);
		if (fs.exists(p)) {
			fs.delete(p, true);
		}

		FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}

}
