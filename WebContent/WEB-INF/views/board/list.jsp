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
				<div id="list">
					<form action="/mysite2/board" method="get">
						<div class="form-group text-right">
							<input type="text" name="key" value="">
							<button type="submit" id=btn_search>검색</button>
							<input type="hidden" name="action" value="search">
						</div>
					</form>
					<table >
						<thead>
							<tr>
								<th>번호</th>
								<th>제목</th>
								<th>글쓴이</th>
								<th>조회수</th>
								<th>작성일</th>
								<th>관리</th>
							</tr>
						</thead>
						<tbody>
						<c:forEach items="${boardList}" var="boardVo">
							<tr>
								<td>${boardVo.no}</td>
								<td class="text-left"><a href="/mysite2/board?action=read&no=${boardVo.no}">${boardVo.title}</a></td>
								<td>${boardVo.name}</td>
								<td>${boardVo.hit}</td>
								<td>${boardVo.regDate}</td>
								<td>
									<c:if test="${authUser.no == boardVo.userNo}">
										<a href="/mysite2/board?action=delete&no=${boardVo.no}">[삭제]</a>
									</c:if>
								</td>
							
							</tr>
						</c:forEach>
						</tbody>
					</table>
		
					<div id="paging">
						<ul>
							<li><a href="">◀</a></li>
							<li><a href="">1</a></li>
							<li><a href="">2</a></li>
							<li><a href="">3</a></li>
							<li><a href="">4</a></li>
							<li class="active"><a href="">5</a></li>
							<li><a href="">6</a></li>
							<li><a href="">7</a></li>
							<li><a href="">8</a></li>
							<li><a href="">9</a></li>
							<li><a href="">10</a></li>
							<li><a href="">▶</a></li>
						</ul>
					
						<div class="clear"></div>
					</div>
					
					<!-- session.setAttribute("authUser", authVo); -->		
					<c:if test="${authUser != null}">
						<a id="btn_write" href="/mysite2/board?action=writeForm&no=${authUser.no}">글쓰기</a>
					</c:if>

				</div>
				<!-- //list -->
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
