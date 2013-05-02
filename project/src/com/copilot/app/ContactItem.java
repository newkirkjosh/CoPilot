package com.copilot.app;

public class ContactItem {

	public String fullName;
	public String phoneNum;
	public int imageRes;

	public ContactItem(String name, String phoneNum, int imageRes) {
		this.fullName = name;
		this.phoneNum = phoneNum;
		this.imageRes = imageRes;
	}

	@Override
	public String toString() {
		return "ContactItem [fullName=" + fullName + ", phoneNum=" + phoneNum
				+ ", imageRes=" + imageRes + "]";
	}
}
