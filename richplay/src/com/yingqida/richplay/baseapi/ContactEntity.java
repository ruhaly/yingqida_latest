package com.yingqida.richplay.baseapi;

import java.io.Serializable;

public class ContactEntity implements Serializable {
	private static final long serialVersionUID = -5503118036771183066L;

	/**
	 * 性别
	 */
	public static final byte SEX_FEMALE = 1;
	public static final byte SEX_MALE = 0;

	/**
	 * 联系人关系
	 */
	public static final int TYPE_FRIEND = 1;
	public static final int TYPE_STRANGER = 2;

	/**
	 * 用户等级
	 */
	public static final int LEVEL_TOURIST = 1;
	public static final int LEVEL_NORMAL = 2;
	public static final int LEVEL_VIP = 3;

	private String mAccount;
	private String mAddress;
	private int mAge;
	private String mCity;
	private String mHeadId;
	private int mId;
	private float mLatitude;

	// 角色
	private int mLevel = LEVEL_NORMAL;
	private float mLongitude;
	private String mNickName;
	private byte mSex;
	private String mTitle;
	private int type = TYPE_FRIEND;

	/**
	 * 用户标签
	 */
	private int tag;

	public boolean equals(Object paramObject) {
		String str1 = getmAccount();
		String str2 = ((ContactEntity) paramObject).getmAccount();
		return str1.equals(str2);
	}

	public int getType() {
		return this.type;
	}

	public String getmAccount() {
		return this.mAccount;
	}

	public String getmAddress() {
		return this.mAddress;
	}

	public int getmAge() {
		return this.mAge;
	}

	public String getmCity() {
		return this.mCity;
	}

	public String getmHeadId() {
		return this.mHeadId;
	}

	public int getmId() {
		return this.mId;
	}

	public float getmLatitude() {
		return this.mLatitude;
	}

	public int getmLevel() {
		return this.mLevel;
	}

	public float getmLongitude() {
		return this.mLongitude;
	}

	public String getmNickName() {
		return this.mNickName;
	}

	public byte getmSex() {
		return this.mSex;
	}

	public String getmTitle() {
		return this.mTitle;
	}

	public void setType(int paramInt) {
		this.type = paramInt;
	}

	public void setmAccount(String paramString) {
		this.mAccount = paramString;
	}

	public void setmAddress(String paramString) {
		this.mAddress = paramString;
	}

	public void setmAge(int paramInt) {
		this.mAge = paramInt;
	}

	public void setmCity(String paramString) {
		this.mCity = paramString;
	}

	public void setmHeadId(String paramString) {
		this.mHeadId = paramString;
	}

	public void setmId(int paramInt) {
		this.mId = paramInt;
	}

	public void setmLatitude(float paramFloat) {
		this.mLatitude = paramFloat;
	}

	public void setmLevel(int level) {
		this.mLevel = level;
	}

	public void setmLongitude(float paramFloat) {
		this.mLongitude = paramFloat;
	}

	public void setmNickName(String paramString) {
		this.mNickName = paramString;
	}

	public void setmSex(byte paramByte) {
		this.mSex = paramByte;
	}

	public void setmTitle(String paramString) {
		this.mTitle = paramString;
	}

	public int getTag() {
		return tag;
	}

	public void setTag(int tag) {
		this.tag = tag;
	}
}