package com.situ.mall.common.response;

import java.io.Serializable;

public class ServerResponse<T> implements Serializable {
	private Integer code;
	private String msg;
	private Integer count;
	private T data;
	
	public ServerResponse() {
		
	}
	

	public ServerResponse(Integer code) {
		this.code = code;
	}
	

	public ServerResponse(Integer code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}
	

	

	public ServerResponse(Integer code, String msg, T data) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = data;
	}


	public ServerResponse(Integer code, String msg, Integer count, T data) {
		super();
		this.code = code;
		this.msg = msg;
		this.count = count;
		this.data = data;
	}
	//只是告诉前台：成功这种状态
		public static <T> ServerResponse<T> createSuccess() {
			return new ServerResponse<>(ResponseCode.SUCCESS.getCode());
		}

		//告诉前台：code,msg
		public static <T> ServerResponse<T> createSuccess(String msg) {
			return new ServerResponse<>(ResponseCode.SUCCESS.getCode(), msg);
		}

		//告诉前台：code,msg,data
		public static <T> ServerResponse<T> createSuccess(String msg, T data) {
			return new ServerResponse<>(ResponseCode.SUCCESS.getCode(), msg, data);
		}

		//告诉前台：code,msg,count,data
		public static <T> ServerResponse<T> createSuccess(String msg, Integer count, T data) {
			return new ServerResponse<>(ResponseCode.SUCCESS.getCode(), msg, count, data);
		}

		//只是告诉前台：失败这种状态
		public static <T> ServerResponse<T> createError() {
			return new ServerResponse<>(ResponseCode.ERROR.getCode());
		}

		//告诉前台：code,msg
		public static <T> ServerResponse<T> createError(String msg) {
			return new ServerResponse<>(ResponseCode.ERROR.getCode(), msg);
		}

		//告诉前台：code,msg,data
		public static <T> ServerResponse<T> createError(String msg, T data) {
			return new ServerResponse<>(ResponseCode.ERROR.getCode(), msg, data);
		}

		public boolean isSuccess() {
			return code == ResponseCode.SUCCESS.getCode();
		}

		public Integer getCode() {
			return code;
		}

		public void setCode(Integer code) {
			this.code = code;
		}

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}

		public T getData() {
			return data;
		}

		public void setData(T data) {
			this.data = data;
		}

		public Integer getCount() {
			return count;
		}

		public void setCount(Integer count) {
			this.count = count;
		}

		public static void main(String[] args) {
			System.out.println(ResponseCode.ERROR.getCode());
		}
}
