/**
 * 
 * 类名称： AddressEntity.java 
 * 作者： hlr 
 * 创建时间： 2012-4-23 上午10:49:48 
 * 版权声明 ： Copyright(C) 2008-2010 RichPeak
 *
 */
package com.yingqida.richplay.baseapi;

/**
 * 
 * 类名称： AddressEntity.java 作者： hlr 创建时间：2012-4-23 上午10:49:48 类描述：
 * 
 */
public class AddressEntity {

	private String rid;

	private String receiver;

	// 所在地区 省份-城市
	private String area;

	private String address;

	private String deliverytype;

	private String phone;

	private String postcode;

	// 收货id
	private String receiveid;

	// 1增加，2修改，3默认并且修改
	private String type;

	// 是否为默认地址是否默认收货地址 3：是默认收货地址 其他：不是
	private String def = "";

	private String flag = "0";

	// 邮费
	private String postage = "";

	public String getPostage() {
		return postage;
	}

	public void setPostage(String postage) {
		this.postage = postage;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @return the def
	 */
	public String getDef() {
		return def;
	}

	/**
	 * @param def
	 *            the def to set
	 */
	public void setDef(String def) {
		this.def = def;
	}

	/**
	 * @return the area
	 */
	public String getArea() {
		return area;
	}

	/**
	 * @param area
	 *            the area to set
	 */
	public void setArea(String area) {
		this.area = area;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the rid
	 */
	public String getRid() {
		return rid;
	}

	/**
	 * @param rid
	 *            the rid to set
	 */
	public void setRid(String rid) {
		this.rid = rid;
	}

	/**
	 * @return the receiveid
	 */
	public String getReceiveid() {
		return receiveid;
	}

	/**
	 * @param receiveid
	 *            the receiveid to set
	 */
	public void setReceiveid(String receiveid) {
		this.receiveid = receiveid;
	}

	/**
	 * @return the postcode
	 */
	public String getPostcode() {
		return postcode;
	}

	/**
	 * @param postcode
	 *            the postcode to set
	 */
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	/**
	 * @return the receiver
	 */
	public String getReceiver() {
		return receiver;
	}

	/**
	 * @param receiver
	 *            the receiver to set
	 */
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the deliverytype
	 */
	public String getDeliverytype() {
		return deliverytype;
	}

	/**
	 * @param deliverytype
	 *            the deliverytype to set
	 */
	public void setDeliverytype(String deliverytype) {
		this.deliverytype = deliverytype;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone
	 *            the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * 方法名称： getFlag 作者： hlr 方法描述： 字段类型： String 返回字段： flag 备注：
	 */
	public String getFlag() {
		return flag;
	}

	/**
	 * 方法名称： setFlag 作者： hlr 方法描述： 字段类型： String 设置字段： 设置 flag 给 flag 备注：
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

}
