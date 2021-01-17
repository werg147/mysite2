<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="/mysite2/assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="/mysite2/assets/css/board.css" rel="stylesheet" type="text/css">

</head>


<body>
	<div id="wrap">

		<!-- header + navi 공통으로 뺌 -->
		<c:import url="/WEB-INF/views/include/header.jsp"></c:import>

		<!-- //aside_board 공통으로 뺌 -->
		<c:import url="/WEB-INF/views/include/aside_board.jsp"></c:import>


		<div id="content">

			<div id="content-head">
				<h3>게시판</h3>
				<div id="location">
					<ul>
						<li>홈</li>
						<li>게시판</li>
						<li class="last">일반게시판</li>
					</ul>
				</div>
				<div class="clear"></div>
			</div>
			<!-- //content-head -->

			<div id="board">
				<div id="modifyForm">
					<form action="/mysite2/board" method="get">
						<!-- 작성자 -->
						<div class="form-group">
							<span class="form-text">작성자</span> <span class="form-value">${read.name}</span>
						</div>

						<!-- 조회수 -->
						<div class="form-group">
							<span class="form-text">조회수</span> <span class="form-value">${read.hit}</span>
						</div>

						<!-- 작성일 -->
						<div class="form-group">
							<span class="form-text">작성일</span> <span class="form-value">${read.regDate}</span>
						</div>

						<!-- 제목 -->
						<div class="form-group">
							<label class="form-text" for="txt-title">제목</label> 
							<input type="text" id="txt-title" name="title" value="${read.title}">
						</div>



						<!-- 내용 -->
						<div class="form-group">
							<textarea id="txt-content" name="content" value="${read.content}">
${read.content}</textarea>
						</div>

						<a id="btn_cancel" href="/mysite2/board?action=list">취소</a>
						<button id="btn_modify" type="submit">수정</button>
						<input type="hidden" name="action" value="modify">

					</form>
					<!-- //form -->
				</div>
				<!-- //modifyForm -->
			</div>
			<!-- //board -->
		</div>
		<!-- //content  -->
		<div class="clear"></div>

		<!-- //footer -->
		<c:import url="/WEB-INF/views/include/footer.jsp"></c:import>
	</div>
	<!-- //wrap -->

</body>

</html>