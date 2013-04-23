package indi;

public class LogiOrder extends BaseDO {

	// /group/taobao/taobao/dw/stb/20130422/tc_logistics_order/days=50

	public LogiOrder(String str) {
		super(str);
	}

	public String getAddress() {
		return getVal(0);
	}

	public String getLogisticsOrderId() {

		return getVal(1);
	}

	@Override
	protected String getIndexStr() {
		return "2,0";
	}

	@Override
	protected String getTableName() {
		return Utils.LOGIORDER;
	}

	@Override
	public int getLen() {
		return 25;
	}

	@Override
	public String getKey() {
		return getLogisticsOrderId();
	}
}
