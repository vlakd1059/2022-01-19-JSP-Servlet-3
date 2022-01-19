package com.message.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MessageDAO {
	private Connection conn;
	private PreparedStatement psmt;
	private ResultSet rs;
	private int cnt;
	private String sql;

	public void connect() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String user = "hr";
			String password = "hr";

			conn = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			// 1. OracleDriver 클래스가 해당 위치에 없는경우(ojdbc6.jar 미포함)
			// 해결방안 : WEB-INF -> lib -> ojdbc6.jar 저장

			// 2. OracleDriver 경로가 오타인경우
			e.printStackTrace();
		} catch (SQLException e) {
			// DB연결 정보가 정확하지 않을 경우
			e.printStackTrace();
		}
	}

	// DB연결종료 -> 연결 종류 순서는 역순으로
	public void close() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (psmt != null) {
				psmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int sendMessage(MessageDTO messageDTO) {

		connect();
		sql = "insert into web_message values(num_seq.nextval,?,?,?,sysdate)";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, messageDTO.getM_sendName());
			psmt.setString(2, messageDTO.getM_receiveEmail());
			psmt.setString(3, messageDTO.getM_content());

			cnt = psmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}

		return cnt;
	}

	public ArrayList<MessageDTO> messageSelect(String email) {
		ArrayList<MessageDTO> list = new ArrayList<MessageDTO>();

		connect();
		sql = "select m_num, m_sendName, m_content, m_sendDate from web_message where m_receiveEmail=?";

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, email);

			rs = psmt.executeQuery();
			while (rs.next()) {

				int m_num = rs.getInt(1);
				String m_sendName = rs.getString(2);
				String m_content = rs.getString(3);
				String m_sendDate = rs.getString(4);

				list.add(new MessageDTO(m_num, m_sendName, null, m_content, m_sendDate));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}

		return list;
	}

	public int messageDelete(String email, String num) {

		connect();
		sql="delete from web_message where m_receiveEmail=? and m_num=?";
		try {
			psmt=conn.prepareStatement(sql);
			psmt.setString(1, email);
			psmt.setString(2, num);
			
			cnt=psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close();
		}
		
		
		
		
		return cnt;
	}


	public int messageDeleteAll(String m_email) {
		
		connect();
		sql="delete from web_message where m_receiveEmail=?";
		try {
			psmt=conn.prepareStatement(sql);
			psmt.setString(1, m_email);
			
			
			cnt=psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close();
		}
		
		
		
		return cnt;
	}
}