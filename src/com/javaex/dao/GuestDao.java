package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.GuestVo;

public class GuestDao {
	
	// 필드
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		private String driver = "oracle.jdbc.driver.OracleDriver";
		private String url = "jdbc:oracle:thin:@localhost:1521:xe";
		private String id = "webdb";
		private String pw = "webdb";
		
	//DB접속
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
	
	//리스트
		public List<GuestVo> getGuestList() {
			
			List<GuestVo> guestList = new ArrayList<GuestVo>();
			
			getConnection();
			
			try {
				//쿼리문 준비/바인딩/실행
				String query = "";
				query += " select no, ";
				query += "        name, ";
				query += "        password, ";
				query += "        content, ";
				query += "        reg_date ";
				query += " from guestbook ";
				
				pstmt = conn.prepareStatement(query); //쿼리문 준비
				
				rs = pstmt.executeQuery();
				
				//결과처리
				while(rs.next()) {
					int no = rs.getInt("no");
					String name = rs.getString("name");
					String password = rs.getString("password");
					String content = rs.getString("content");
					String regDate = rs.getString("reg_date");
					
					GuestVo vo = new GuestVo(no, name, password, content, regDate );
					guestList.add(vo);
				}
				
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
			
			close();
			
			return guestList;
		}
	
	//등록
		public int insert(GuestVo guestVo) {
			int count = 0;
			
			getConnection();
			
			try {
				// 3. SQL문 준비 / 바인딩 / 실행
				String query = "";
				query += " insert into guestbook ";
				query += " values(seq_no.nextval, ?, ?, ?, sysdate) ";

				pstmt = conn.prepareStatement(query);

				pstmt.setString(1, guestVo.getName());
				pstmt.setString(2, guestVo.getPassword());
				pstmt.setString(3, guestVo.getContent());

				count = pstmt.executeUpdate();

				// 4.결과처리
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
			
			
			close();
			
			return count;
		}
	
	//삭제
		public int delete(GuestVo guestVo) {
			int count = 0;
			
			getConnection();
			
			try {

				String query = "";
				query += " delete from guestbook ";
				query += " where no = ? ";
				query += " and password = ? ";

				pstmt = conn.prepareStatement(query);

				pstmt.setInt(1, guestVo.getNo() );
				pstmt.setString(2, guestVo.getPassword());

				count = pstmt.executeUpdate();

			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
			
			close();
			
			return count;
		}

}
