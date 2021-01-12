package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import com.javaex.vo.UserVo;

public class UserDao {

	// 필드
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb";
	private String pw = "webdb";

	// DB접속
	private void getConnection() {

		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName(driver);

			// 2. Connection 얻어오기
			conn = DriverManager.getConnection(url, id, pw);

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

	}

	// 자원정리
	private void close() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	public int insert(UserVo userVo) {

		int count = 0;

		getConnection();

		try {
			// 쿼리문 준비/ 바인딩/ 실행
			String query = "";
			query += " insert into users ";
			query += " values(seq_users_no.nextval, ";
			query += "        ?, ";
			query += "        ?, ";
			query += "        ?, ";
			query += "        ? ";
			query += " ) ";

			pstmt = conn.prepareStatement(query); // 쿼리로 만들기

			pstmt.setString(1, userVo.getId()); // 순서대로 ? 값 넣기
			pstmt.setString(2, userVo.getPassword());
			pstmt.setString(3, userVo.getName());
			pstmt.setString(4, userVo.getGender());

			pstmt.executeUpdate(); // 쿼리문 실행

			// 결과처리
			System.out.println("insert" + count + "건 회원정보 저장");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();

		return count;

	}
	
	//로그인한 유저 정보
	public UserVo getUser(String id, String pw) {
		UserVo userVo = null;
		
		getConnection();
		
		try {
			String query = "";
			query += " select no, ";
			query += "        name ";
			query += " from users ";
			query += " where id = ? ";
			query += " and password = ? ";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name");
				
				userVo = new UserVo(no,name);
			}
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		close();
		
		return userVo;
	}
	
	//수정
	public int update(UserVo userVo) {
		int count = 0;
		
		getConnection();
		
		try {
			String query = "";
			query += " update users ";
			query += " set password = ?, ";
			query += "     name = ?, ";
			query += "     gender = ? ";
			query += " where no = ? ";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, userVo.getPassword());
			pstmt.setString(2, userVo.getName());
			pstmt.setString(3, userVo.getGender());
			pstmt.setInt(4, userVo.getNo());
			
			count = pstmt.executeUpdate();
		
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		close();
		
		return count;
	}
	
	//no로 유저정보 가져오기
	public UserVo loginUser(int no) {
		UserVo userVo = null;
		
		getConnection();
		
		try {
			String query = "";
			query += " select password, ";
			query += "        name, ";
			query += "        gender ";
			query += " from users ";
			query += " where no = ? ";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String pw = rs.getString("password");
				String name = rs.getString("name");
				String gender = rs.getString("gender");
				
				userVo = new UserVo(pw, name, gender);
			}
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		close();
		
		return userVo;
	}

}
