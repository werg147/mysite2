package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.BoardDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.BoardVo;

@WebServlet("/board")
public class BoardController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("BoardController");
		
		//파라미터 액션값 읽기
		String action = request.getParameter("action");
		System.out.println("action: " + action);
		
		if("list".equals(action)) {
			System.out.println("게시판 리스트");
			
			//리스트 가져오기
			BoardDao boardDao = new BoardDao();
			List<BoardVo> boardList = boardDao.getBoardList();
			
			//리스트 담기
			request.setAttribute("boardList", boardList);
			
			//포워드 -> 리스트
			WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");
		
		} else if("delete".equals(action)) {
			System.out.println("삭제");
			
			//no 파라미터 읽기
			int no = Integer.parseInt(request.getParameter("no"));
			
			//Dao -> 삭제하기
			BoardDao boardDao = new BoardDao();
			boardDao.delete(no);
			
			//리다이렉트 -> 리스트
			WebUtil.redirect(request, response, "/mysite2/board?action=list");
			
		} else if("read".equals(action)) {
			System.out.println("게시글 읽기");
			
			//파라미터 no값 읽기
			int no = Integer.parseInt(request.getParameter("no"));
			
			//게시글 가져오기
			BoardDao boardDao = new BoardDao();
		    BoardVo read = boardDao.getRead(no);
			
			//리스트 담기
			request.setAttribute("read", read);
			
			//포워드 --> 읽기 폼
			WebUtil.forward(request, response, "/WEB-INF/views/board/read.jsp");
			
		} else if("writeForm".equals(action)) {
			System.out.println("글쓰기 폼");
			
			//포워드 --> 글쓰기 폼
			WebUtil.forward(request, response, "/WEB-INF/views/board/writeForm.jsp");
			
		} else if("insert".equals(action)) {
			System.out.println("게시글 등록");
			
			//타이틀,컨텐츠 파라미터 읽기
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			
			//세션 no꺼내기
			HttpSession session = request.getSession();
			BoardVo authUser = (BoardVo)session.getAttribute("authUser");
			int no = authUser.getNo();
			
			//vo로 묶기
			BoardVo boardVo = new BoardVo(title, content, no);
			
			//Dao -> 저장하기
			BoardDao boardDao = new BoardDao();
			boardDao.insert(boardVo);
			
			//리다이렉트 -> 리스트
			WebUtil.redirect(request, response, "/mysite2/board?action=list");
			
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
