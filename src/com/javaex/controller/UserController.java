package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.UserVo;

@WebServlet("/user")
public class UserController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("UserController");
		
		String action = request.getParameter("action");
		System.out.println("action: " + action);
		
		if("joinForm".equals(action)) {
			System.out.println("회원가입폼");
			
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinForm.jsp");
			
		} else if("join".equals(action)) {
			System.out.println("회원가입");
			
			//user?uid=jus&pw=1234&uname=정우성&gender=male&action=join
			//파라미터 값 읽어서
			String id = request.getParameter("uid");
			String password = request.getParameter("pw");
			String name = request.getParameter("uname");
			String gender = request.getParameter("gender");
			
			//vo로 묶기
			UserVo userVo = new UserVo(id, password, name, gender);
			System.out.println(userVo.toString());
			
			//dao --> insert()로 저장 --> 회원가입
			UserDao userDao = new UserDao();
			userDao.insert(userVo);
					
			//포워드 --> joinOk.jsp
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinOk.jsp");
			
		} else if("loginForm".equals(action)) {
			System.out.println("로그인폼");
			
			//포워드
			WebUtil.forward(request, response, "/WEB-INF/views/user/loginForm.jsp");
			
		} else if("login".equals(action)) {
			System.out.println("로그인");
			//파라미터 값 읽기
			String id = request.getParameter("id");
			String pw = request.getParameter("pw");
			
			//Dao -> getUser
			UserDao userDao = new UserDao();
			UserVo authVo = userDao.getUser(id, pw);
			
			//System.out.println(authvo); //id,pw 넘기면 --> no,name 을 줌(Dao 쿼리문)
			
			if(authVo == null) { //로그인 실패
				System.out.println("로그인 실패");
				//리다이렉트 --> loginForm
				WebUtil.redirect(request, response, "/mysite2/user?action=loginForm&result=fail");
				
			} else { //로그인 성공
				System.out.println("로그인 성공");
				//세션영역에 필요한 값(Vo)을 넣어준다.
				HttpSession session = request.getSession();
				session.setAttribute("authUser", authVo);
				
				//리다이렉트 --> main
				WebUtil.redirect(request, response, "/mysite2/main");
			} 
						
		} else if("logout".equals(action)) {
			System.out.println("로그아웃");
			
			//세션영역에 있는 Vo값을 삭제
			HttpSession session = request.getSession();
			session.removeAttribute("authUser");
			session.invalidate();
			
			//리다이렉트 --> main
			WebUtil.redirect(request, response, "/mysite2/main");
		
		} else if("modifyForm".equals(action)) {
			System.out.println("수정폼");
			
			//session no를 이용해서 로그인한 유저 정보 가져오기
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			
			//로그인 안 한 상태면 가져올수없음 (로그인 안하고 modifyForm접근시 오류)
			int no = authUser.getNo();
			
			//Dao --> 로그인유저 정보 가져오기
			UserDao userDao = new UserDao();
			UserVo userVo = userDao.getUser(no);
			
			System.out.println("getUser(no)" + userVo);
			
			//Attribute에 담아 데이터전달
			request.setAttribute("userVo", userVo);
			
			//포워드 --> modifyForm
			WebUtil.forward(request, response, "/WEB-INF/views/user/modifyForm.jsp");
		
		} else if("modify".equals(action)) { //action=update까지 오긴 하지만 수정이 안됨
			System.out.println("수정");
			
			//user?pw=123&name=이수정&gender=male&action=modify
			//파라미터값 읽기
			String pw = request.getParameter("pw");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");
			
			//세션에서 no 가져오기
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			int no = authUser.getNo();
			
			//UserVo로 만들기
			UserVo userVo = new UserVo(no, pw, name, gender);
			//System.out.println(userVo); 테스트
			
			//Dao -> update() 으로 정보업데이트
			UserDao userDao = new UserDao();
			userDao.update(userVo);
			
			//session 정보도 업데이트
			//session name값만 변경 --> ㅇㅇㅇ님 안녕하세요^^ 에서 이름바뀜
			authUser.setName(name);
			
			//리다이렉트 -> main
			WebUtil.redirect(request, response, "/mysite2/main");
			
		}
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//doGet(request, response);
	}

}
