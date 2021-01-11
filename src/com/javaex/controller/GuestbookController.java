package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.GuestVo;

@WebServlet("/guest")
public class GuestbookController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("GuestbookController");
		
		//파라미터 action값으로 읽기
		String action = request.getParameter("action");
		System.out.println("action: " + action);
		
		if("addList".equals(action)) {
			System.out.println("일반방명록 폼");
			
			//리스트 가져오기
			GuestDao guestDao = new GuestDao();
			List<GuestVo> guestList = guestDao.getGuestList();

			//데이터 담기
			request.setAttribute("gList", guestList);
			
			//포워드
			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/addList.jsp");
			
		} else if("add".equals(action)) {
			System.out.println("방명록 등록");
			
			//파라미터값 읽기
			String name = request.getParameter("name");
			String password = request.getParameter("pass");
			String content = request.getParameter("content");
			
			//vo에 담기
			GuestVo guestVo = new GuestVo(name, password, content);
			
			//Dao insert -> 등록
			GuestDao guestDao = new GuestDao();
			guestDao.insert(guestVo);
			
			//리다이렉트 (addList)
			WebUtil.redirect(request, response, "/mysite2/guest?action=addList");
			
		} else if("deleteForm".equals(action)) {
			System.out.println("삭제폼");
			
			//포워드
			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/deleteForm.jsp");
			
		} else if("delete".equals(action)) {
			System.out.println("삭제");
			
			//파라미터 읽기
			int no = Integer.parseInt(request.getParameter("no"));
			String password = request.getParameter("pass");
			
			//vo에 담기
			GuestVo guestVo = new GuestVo(no, password);
			
			//Dao -> delete 
			GuestDao guestDao = new GuestDao();
			int count = guestDao.delete(guestVo);
			
			//리턴값 1이면 삭제, 0이면 실패
			if(1==count) {
				//리다이렉트
				WebUtil.redirect(request, response, "/mysite2/guest?action=addList");
			} else if(0==count){
				//포워드 -> deleteForm
				WebUtil.forward(request, response, "/WEB-INF/views/guestbook/deleteForm.jsp");
			}
			
		} else {
			System.out.println("addList로 기본설정");
			
			//리스트 가져오기
			GuestDao guestDao = new GuestDao();
			List<GuestVo> guestList = guestDao.getGuestList();

			//데이터 담기
			request.setAttribute("gList", guestList);
			
			//포워드
			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/addList.jsp");
			
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
