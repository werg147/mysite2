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
				WebUtil.redirect(request, response, "/mysite2/user?action=loginForm");
				
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
			
			//Dao --> 로그인유저 정보 가져오기
			UserDao userDao = new UserDao();
			
			//vo에 담기
			UserVo userVo = new UserVo();
			userVo = userDao.loginUser(authUser.getNo()); //authUser에서 no만 꺼내어 조회 (int에 세션값을 억지로 집어넣으려는등..많은실패가 있었다)
			
			//Attribute에 담아 데이터전달
			request.setAttribute("loginUser", userVo);
			
			//리다이렉트 --> modifyForm
			WebUtil.forward(request, response, "/WEB-INF/views/user/modifyForm.jsp");
		
		} else if("update".equals(action)) { //action=update까지 오긴 하지만 수정이 안됨..
			System.out.println("수정");
			
			//session no를 이용해서 로그인한 유저 정보 가져오기
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			
			//파라미터값 읽기
			String pw = request.getParameter("pw");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");
			Integer no = (Integer)session.getAttribute("no");
			
			//vo에 담기
			UserVo userVo = new UserVo(no, pw, name, gender); //여기서 오류 수정폼에서 no를 Integer로 담았어서 읽지 못하는건가?
			
			//Dao -> 수정하기
			UserDao userDao = new UserDao();
			userDao.update(userVo);
		
			//리다이렉트 --> main
			WebUtil.redirect(request, response, "/mysite2/main");
		}
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//doGet(request, response);
	}

}
