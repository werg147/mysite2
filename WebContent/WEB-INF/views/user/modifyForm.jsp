<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="/mysite2/assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="/mysite2/assets/css/user.css" rel="stylesheet" type="text/css">

</head>

<body>
	<div id="wrap">

		<!-- header + navi 공통으로 뺌 -->
		<c:import url="/WEB-INF/views/include/header.jsp"></c:import>

		<div id="aside">
			<h2>회원</h2>
			<ul>
				<li>회원정보</li>
				<li><a href="/mysite2/user?action=loginForm">로그인</a></li>
				<li><a href="/mysite2/user?action=joinForm">회원가입</a></li>
			</ul>
		</div>
		<!-- //aside -->

		<div id="content">
			
			<div id="content-head">
            	<h3>회원정보</h3>
            	<div id="location">
            		<ul>
            			<li>홈</li>
            			<li>회원</li>
            			<li class="last">회원정보</li>
            		</ul>
            	</div>
                <div class="clear"></div>
            </div>
             <!-- //content-head -->

			<div id="user">
				<div id="modifyForm">
					<form action="/mysite2/user" method="get">
						<!-- request.getAttribute("userVo"); -->
						<!-- 아이디 -->
						<div class="form-group">
							<label class="form-text" for="input-uid">아이디</label> 
							<span class="text-large bold">${requestScope.userVo.id}</span>
						</div>

						<!-- 비밀번호 -->
						<div class="form-group">
							<label class="form-text" for="input-pass">패스워드</label> 
							<input type="text" id="input-pass" name="pw" value="${userVo.password}" placeholder="비밀번호를 입력하세요"	>
						</div>

						<!-- 이메일 -->
						<div class="form-group">
							<label class="form-text" for="input-name">이름</label> 
							<input type="text" id="input-name" name="name" value="${userVo.name}" placeholder="이름을 입력하세요">
						</div>

						<!-- //나이 -->
						<div class="form-group">
							<span class="form-text">성별</span> 
							
							
						<c:choose>
							<c:when test="${userVo.gender == mail}">
								<!-- 가져온 데이터가 남자일때 -->
								<label for="rdo-male">남</label> 
								<input type="radio" id="rdo-male" name="gender" value="male" checked="checked" > 
								
								<label for="rdo-female">여</label> 
								<input type="radio" id="rdo-female" name="gender" value="female" > 
							</c:when>
							<c:otherwise>
								<!-- 가져온 데이터가 여자일때 -->
								<label for="rdo-male">남</label> 
								<input type="radio" id="rdo-male" name="gender" value="male" >
								
								<label for="rdo-female">여</label> 
								<input type="radio" id="rdo-female" name="gender" value="female" checked="checked" > 
							</c:otherwise>
						</c:choose>
						</div>

						<!-- 버튼영역 -->
		                <div class="button-area">
		                    <button type="submit" id="btn-submit">회원정보수정</button>
		                    <input type="hidden" name="action" value="modify">
		                </div>
						
					</form>
				
				
				</div>
				<!-- //modifyForm -->
			</div>
			<!-- //user -->
		</div>
		<!-- //content  -->
		<div class="clear"></div>
		
		<!-- //footer -->
		<c:import url="/WEB-INF/views/include/footer.jsp"></c:import>
		
	</div>
	<!-- //wrap -->

</body>

</html>