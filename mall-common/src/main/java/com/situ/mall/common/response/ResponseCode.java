package com.situ.mall.common.response;

public enum ResponseCode {
	
	SUCCESS(0,"SECCESS"),
	ERROR(1, "ERROR"),
	NEED_LOGIN(2, "NEED_LOGIN"),
	ILLEGAL_PERMISSION(3, "ILLEGAL_PERMISSION");
	
	private int code;
	private String desc;
	private ResponseCode(int code,String desc){
		this.code = code;
		this.desc = desc;
	}
	public int getCode(){
		return code;
	}
	public String getDesc(){
		return desc;
	}
	
}
