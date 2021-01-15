package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.BoardVo;

public class BoardDao {

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

	// 게시판 리스트
	public List<BoardVo> getBoardList() {

		List<BoardVo> boardList = new ArrayList<BoardVo>();

		getConnection();

		try {
			String query = "";
			query += " select bo.no, ";
			query += "        bo.title, ";
			query += "        us.name, ";
			query += "        bo.hit, ";
			query += "        to_char(bo.reg_date, 'YYYY-MM-DD') reg_date ";
			query += " from board bo, users us ";
			query += " where bo.user_no = us.no ";
			query += " order by bo.no desc ";

			pstmt = conn.prepareStatement(query);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				int no = rs.getInt("no");
				String title = rs.getString("title");
				String name = rs.getString("name");
				int hit = rs.getInt("hit");
				String regDate = rs.getString("reg_date");

				BoardVo vo = new BoardVo(no, title, name, hit, regDate);
				boardList.add(vo);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();
		return boardList;
	}

	// 게시글 읽기
	public BoardVo getRead(int no) {

		BoardVo boardVo = null;

		getConnection();

		try {
			String query = "";
			query += " select us.name, ";
			query += "		  bo.hit, ";
			query += "        to_char(bo.reg_date, 'YYYY-MM-DD') reg_date, ";
			query += " 		  bo.title, ";
			query += "        bo.content ";
			query += " from board bo, users us ";
			query += " where bo.user_no = us.no ";
			query += " and bo.no = ? ";

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				String name = rs.getString("name");
				int hit = rs.getInt("hit");
				String regDate = rs.getString("reg_date");
				String title = rs.getString("title");
				String content = rs.getString("content");
				
				boardVo = new BoardVo(name, hit, regDate, title, content);

			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();
		return boardVo;
	}

	// 게시글 삭제
	public int delete(int no) {
		int count = 0;

		getConnection();

		try {
			String query = "";
			query += " delete from board ";
			query += " where no = ? ";

			pstmt = conn.prepareStatement(query);

			pstmt.setInt(1, no);

			count = pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();
		return count;
	}
	
	//게시글 등록
	public int insert(BoardVo boardVo) {
		int count = 0;
		
		getConnection();
		
		try {
			String query = "";
			query += " insert into board ";
			query += " values(seq_board_no.nextval, ?, ?, 0, sysdate, ?) ";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, boardVo.getTitle());
			pstmt.setString(2, boardVo.getContent());
			pstmt.setInt(3, boardVo.getUserNo());
			
			count = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		close();
		return count;
	}

	
	
	
	
	
	
	
	
	
	
}
