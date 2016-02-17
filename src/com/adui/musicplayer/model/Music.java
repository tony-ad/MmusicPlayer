package com.adui.musicplayer.model;

import android.graphics.Bitmap;

public class Music {
	private String name;
	private String musicN;
	private int id;
	private String zhuanji;
	private String url;
	private int time;
	private Long aa;
	private Long bb;
	private Bitmap bm;
	public Music(){
		
	}
	
	public Music(String name,String musicN,int time){
		this.musicN=musicN;
		this.name=name;
		this.time=time;
	}
	
	public Music(String name,String musicN,String path){
		this.musicN=musicN;
		this.name=name;
		url=path;
	}
	
	public Music(String name,String musicN, int id,String zhuanji,String url,int time){
		this.musicN=musicN;
		this.name=name;
		this.id=id;
		this.zhuanji=zhuanji;
		this.time=time;
		this.url=url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMusicN() {
		return musicN;
	}

	public void setMusicN(String musicN) {
		this.musicN = musicN;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getZhuanji() {
		return zhuanji;
	}

	public void setZhuanji(String zhuanji) {
		this.zhuanji = zhuanji;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public Long getAa() {
		return aa;
	}

	public void setAa(Long aa) {
		this.aa = aa;
	}

	public Long getBb() {
		return bb;
	}

	public void setBb(Long bb) {
		this.bb = bb;
	}

	public Bitmap getBm() {
		return bm;
	}

	public void setBm(Bitmap bm) {
		this.bm = bm;
	}
	
	
	
	
}
