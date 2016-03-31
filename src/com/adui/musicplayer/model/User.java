package com.adui.musicplayer.model;
public class User {
	/**
	 * 用户名
	 */
	private String name;
	
	/**
	 * 用户密码
	 */
	private int passwork;
	
	/**
	 * 用户性别
	 */
	private String gender;
	/**
	 * 个性签名
	 */
	private String signature;
	/**
	 * 地区
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

