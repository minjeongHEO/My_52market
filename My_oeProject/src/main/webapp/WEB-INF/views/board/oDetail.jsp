<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>       
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 글 상세</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/layout.css"> <%--css스타일 경로--%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/board-reply.js"></script>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<div class="page-main">
	<h2>게시판 글상세</h2>
	<ul>
		<li>글번호 : ${board.board_num}</li>
		<li>글제목 : ${board.title}</li>
		<li>작성자 : ${board.id}</li>
		<li>조회수 : ${board.hit}</li>
	</ul>
	<hr size="1" noshade width="100%">
	<c:if test="${!empty board.filename}">
		<div class="align-center">
			<img src="${pageContext.request.contextPath}/upload/${board.filename}" class="detail-img">
		</div>
	</c:if>
	<p>
		${board.content}
	</p>
	<hr size="1" noshade="noshade" width="100%">
	<div class="align-right">
		<c:if test="${!empty board.modify_date}">
			최근 수정일 : ${board.modify_date}		<%--수정일이 있으면 최근수정일 보이도록 --%>
			
		</c:if>		
		작성일 : ${board.reg_date}
		
		<%--로그인한 회원번호와 작성자 회원번호가 일치해야 수정, 삭제 가능 --%>	
		<c:if test="${user_num == board.mem_num }">	
			<input type="button" value="수정" onclick="location.href='oUpdateForm.do?board_num=${board.board_num}'">
			<input type="button" value="삭제" id="delete_btn">
			<script type="text/javascript">
				let delete_btn = document.getElementById('delete_btn');
				//이벤트 연결
				delete_btn.onclick=function(){
					let choice = confirm('삭제하시겠습니까?');
					if(choice){
						location.replace('oDelete.do?board_num=${board.board_num}'); 
							//히스토리를 지우고(전페이지로 back불가) + delete로 이동하고 싶으면 location.replace
							//히스토리를 남기면서(back가능) 이동하고 싶으면 location.href를 쓰면됨
					}
				};
			</script>	
		</c:if>	
		<input type="button" value="목록" onclick="location.href='oList.do'">
	</div>
	
<!-- 댓글시작 -->
	<div id="reply_div">
		<span class="re_title">댓글 달기</span>
		<form id="re_form">	<!-- ajax방식이기때문에 action이 없다. -->	<!-- value는 부모글번호  -->
			<input type="hidden" name="board_num" value="${board.board_num }" id="board_num">
			
			<textarea rows="3" cols="50" name="re_content" id="re_content" class="rep-content"
			<c:if test="${empty user_num}">disabled="disabled"</c:if>
			><c:if test="${empty user_num }">로그인해야 작성할 수 있습니다.</c:if></textarea>
			<!-- textarea태그 사이의 공백은 모두 데이터로 인식하기때문에, 공백을 넣으면안됨 -->
			
			<c:if test="${!empty user_num }">
				<div id="re_first">
					<span class="letter-count">300/300</span>
				</div>
				<div id="re_second" class="align-right">
					<input type="submit" value="전송">
				</div>
			</c:if>
		</form>
	</div>
		<!-- 댓글목록 출력시작 -->
		<div id="output"></div>
		<div class="paging-button" style="display:none;">
			<input type="button" value="다음댓글 보기">
		</div>
		<div id="loading" style="display:none;">
			<img src="${pageContext.request.contextPath}/images/ajax-loader.gif">
		</div>
		<!-- 댓글목록 출력 끝  -->
<!-- 댓글 끝 -->
</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>		
</body>
</html>