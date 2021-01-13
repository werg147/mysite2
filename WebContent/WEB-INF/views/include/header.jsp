<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "com.javaex.vo.UserVo" %>

<%
	UserVo authUser = (UserVo)session.getAttribute("authUser");
%>

        <div id="header">
			<h1><a href="/mysite2/main">MySite</a></h1>
			
			<!-- if 로그인 안했을때 session영역에 값이 없으면--> 
			<%if(authUser==null){ %>
			<ul>
				<li><a href="/mysite2/user?action=loginForm">로그인</a></li>
				<li><a href="/mysite2/user?action=joinForm">회원가입</a></li>
			</ul>
			<%} else { %>
			<!-- if 로그인 했을때 session영역에 값이 있으면--> 
			<ul>
				<li><%=authUser.getName() %> 님 안녕하세요^^</li>
				<li><a href="/mysite2/user?action=logout">로그아웃</a></li>
				<li><a href="/mysite2/user?action=modifyForm">회원정보수정</a></li>
			</ul>
			<%} %>
			
		</div>
		<!-- //header -->

		<div id="nav">
			<ul>
				<li><a href="/mysite2/guest">방명록</a></li>
				<li><a href="">갤러리</a></li>
				<li><a href="">게시판</a></li>
				<li><a href="">입사지원서</a></li>
			</ul>
			<div class="clear"></div>
		</div>
		<!-- //nav -->