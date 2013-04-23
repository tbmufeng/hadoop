package indi;

public class PayOrder extends BaseDO {

	// /group/taobao/taobao/dw/stb/20130422/tc_pay_order/days=50

	public PayOrder(String str) {
		super(str);
	}

	public double getTotalFee() {
		return Double.parseDouble(getVal(0));
	}

	public String getPayOrderId() {
		return getVal(1);
	}

	@Override
	protected String getIndexStr() {
		return "1,0";
	}

	@Override
	protected String getTableName() {
		return Utils.PAYORDER;
	}

	@Override
	public int getLen() {
		return 30;
	}

	@Override
	public String getKey() {
		return getPayOrderId();
	}

}
