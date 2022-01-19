package com.message.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Data : getter/setter 메소드를 자동으로 생성
//@NoArgsConstructor : 기본 생성자 생성
//@AllArgsConstructor : 모든 필드변수를 초기화하는 생성자 생성
@Data
@NoArgsConstructor
@AllArgsConstructor

public class MessageDTO {
	private int m_num;
	private String m_sendName;
	private String m_receiveEmail;
	private String m_content;
	private String m_sendDate;

}
