package com.example.srt.common;

public class ApiHeader {
	private int resultCode; // 성공 200, 실패다 999, 600
	private String resultMsg; // success, fail, NOT_FOUND_USER

	public ApiHeader(int resultCode, String resultMsg) {
		this.resultCode = resultCode;
		this.resultMsg = resultMsg;
	}

	public int getResultCode() {
		return resultCode;
	}

	public String getResultMsg() {
		return resultMsg;
	}

}
