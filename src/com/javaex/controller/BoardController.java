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
import com.javaex.vo.UserVo;

@WebServlet("/board")
public class BoardController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("BoardController");
		
		//한글깨짐방지 (post)
		request.setCharacterEncoding("UTF-8");
		
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
		    
		    //조회수 1 증가
		    boardDao.hitCount(no);
			
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
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			int userNo = authUser.getNo(); 
						
			//vo로 묶기
			BoardVo boardVo = new BoardVo(title, content, userNo);
			//ORA-02291: integrity constraint no -> 참조하는 값이 존재하지 않음(sql의 userNo) -> 이름을 userNo로 맞춰줌
			
			//Dao -> 저장하기
			BoardDao boardDao = new BoardDao();
			boardDao.insert(boardVo);
			
			//리다이렉트 -> 리스트
			WebUtil.redirect(request, response, "/mysite2/board?action=list");
			
		} else if("modifyForm".equals(action)) {
			System.out.println("수정 폼");
			
			//파라미터 no값 읽기
			int no = Integer.parseInt(request.getParameter("no"));
			
			//게시글 가져오기
			BoardDao boardDao = new BoardDao();
		    BoardVo read = boardDao.getRead(no);
			
			//리스트 담기
			request.setAttribute("read", read);
			
			//포워드 -> 글쓰기폼
			WebUtil.forward(request, response, "/WEB-INF/views/board/modifyForm.jsp");
		
		} else if("modify".equals(action)) {
			System.out.println("수정");
			
			//파라미터 값 읽기
			String title = request.getParameter("title");
			String content = request.getParameter("content");		
			
			//세션 no꺼내기
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			int userNo = authUser.getNo();
			
			//vo 묶기
			BoardVo boardVo = new BoardVo(title, content, userNo);
			
			//Dao -> 수정하기
			BoardDao boardDao = new BoardDao();
			boardDao.modify(boardVo);
			
			//리다이렉트 -> 리스트
			WebUtil.redirect(request, response, "/mysite2/board?action=list");
			
		} else if ("search".equals(action)) {
			System.out.println("게시글 검색");
			
			//파라미터 값 읽기
			String key = request.getParameter("key");
			
			//Dao -> 검색
			BoardDao boardDao = new BoardDao();
			List<BoardVo> searchList = boardDao.getSearchList(key);
			
			//리스트 담기
			request.setAttribute("boardList", searchList);

			//포워드 -> 리스트
			WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");
		
		} else {
			System.out.println("기본값 설정");
			//리스트 가져오기
			BoardDao boardDao = new BoardDao();
			List<BoardVo> boardList = boardDao.getBoardList();
			
			//리스트 담기
			request.setAttribute("boardList", boardList);
			
			//포워드 -> 리스트
			WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");
		
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
