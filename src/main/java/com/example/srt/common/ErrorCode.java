package com.example.srt.common;

import lombok.Getter;

@Getter
public enum ErrorCode {
	FAIL(999, "실패"),
	BAD_REQUEST(10, ""),
	PATTERN_ERROR(20, ""),
	SIZE_ERROR(30, ""),
	DUPLICATION_ERROR(40, ""),
	LOGIN_ERROR(50, ""),
	VERIFY_ERROR(60, ""),
	NOT_LOGIN(70, ""),
	PUBLIC_API_ERROR(80, ""),
	SERVER_ERROR(500, "");
	

	private int code;
	private String message;

	ErrorCode(int code, String message) {
		this.code = code;
		this.message = message;
	}

}
