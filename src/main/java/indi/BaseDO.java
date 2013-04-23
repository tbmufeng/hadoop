package indi;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseDO {
	String[] arr;
	boolean isShort = false;
	static Map<String, Map<Integer, Integer>> doIndexMap = new HashMap<String, Map<Integer, Integer>>();

	public abstract String getKey();

	protected int getIndex(int idx) {
		Map<Integer, Integer> idxMap = doIndexMap.get(getTableName());
		if (idxMap == null) {
			idxMap = new HashMap<Integer, Integer>();
			String[] idxArr = getIndexStr().split(",");
			for (int i = 0; i < idxArr.length; i++) {
				idxMap.put(i, Integer.parseInt(idxArr[i]));
			}
			doIndexMap.put(getTableName(), idxMap);
		}
		return idxMap.get(idx);
	}

	protected abstract String getIndexStr();

	protected abstract String getTableName();

	public BaseDO(String str) {

		if (str.startsWith(":"))
			str = str.substring(1);
		String[] temp = str.split(Utils.SPLIT);
		if (temp.length == 2) {
			str = temp[1];
			isShort = true;
		}
		if (str != null)
			arr = str.split("\t");
	}

	public boolean isValid() {
		if (arr != null) {
			if (isShort) {
				return arr.length == getIndexStr().split(",").length;
			} else {
				return arr.length == getLen();
			}
		}
		return false;
	}

	public abstract int getLen();

	public String get() {
		return arr[-1];
	}

	String getVal(int idx) {
		if (isShort)
			return arr[idx];
		return arr[getIndex(idx)];
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("TB:");
		sb.append(getTableName() + Utils.SPLIT);
		int len = getIndexStr().split(",").length;
		for (int i = 0; i < len - 1; i++) {

			sb.append(getVal(i)).append("\t");
		}
		sb.append(arr[len - 1]);
		return sb.toString();
	}
}
