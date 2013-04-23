package indi;

import java.util.Map;

public class BizOrder extends BaseDO {

	// /group/taobao/taobao/dw/stb/20130422/tc_biz_order/days=50

	public BizOrder(String str) {
		super(str);
		attMap = Utils.getAttr(getAttributes());
	}

	public String getAttribute(String code) {
		return attMap.get(code);
	}

	public String getRealRootCat() {
		return getAttribute("realRootCat");
	}

	public String getBizOrderId() {

		return getVal(0);
	}

	public String getBuyerId() {
		return getVal(1);
	}

	public String getSellerId() {
		return getVal(2);
	}

	public String getBizType() {
		return getVal(3);
	}

	public String getPayStatus() {
		return getVal(4);
	}

	public String getAttributes() {
		return getVal(5);
	}

	public String getBuyerIp() {
		return getVal(6);
	}

	public boolean isSubOrder() {
		return "1".equals(getVal(7));
	}

	public String getPayOrderId() {
		return getVal(8);
	}

	public String getLogisticOrderId() {
		return getVal(9);
	}

	public String get() {
		return getVal(-1);
	}

	private Map<String, String> attMap;

	@Override
	protected String getIndexStr() {
		return "0,6,7,13,15,31,33,37,1,2";
	}

	@Override
	protected String getTableName() {
		return Utils.BIZORDER;
	}

	@Override
	public int getLen() {
		return 56;
	}

	@Override
	public String getKey() {
		return getBizOrderId();
	}

}
