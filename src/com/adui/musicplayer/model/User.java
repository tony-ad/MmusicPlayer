package com.adui.musicplayer.model;
public class User {
	/**
	 * �û���
	 */
	private String name;
	
	/**
	 * �û�����
	 */
	private int passwork;
	
	/**
	 * �û��Ա�
	 */
	private String gender;
	/**
	 * ����ǩ��
	 */
	private String signature;
	/**
	 * ����
	 */
	private String region;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public int getPasswork() {
		return passwork;
	}
	public void setPasswork(int passwork) {
		this.passwork = passwork;
	}
	
	
	
	
}

