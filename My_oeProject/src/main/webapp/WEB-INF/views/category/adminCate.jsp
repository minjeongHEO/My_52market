<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>운영자 카테고리 등록</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/layout.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-3.6.0.min.js">/* 컨텍스트경로부터 명시해야 에러안남 */</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/category.js"></script>

</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>

	<div class="page-main">
		<br>
		<h2>[MANAGER] 카테고리 관리</h2>
		<br>
	<!-- 댓글시작 -->
	<div id="reply_div">
		<span class="re_title2">카테고리 추가</span>
		
		<form id="re_form">	<!-- ajax방식이기때문에 action이 없다. -->	<!-- value는 부모글번호 //아마 수정 시 쓰임//카테고리는 부모글이없으니 필요없다  -->
			<%-- <input type="hidden" name="item_num" value="${item.item_num }" id="item_num"> --%>
			<!-- textarea태그 사이의 공백은 모두 데이터로 인식하기때문에, 공백을 넣으면안됨 -->
			<textarea rows="2" cols="20" name="re_content" id="re_content" class="rep-content"
			<c:if test="${empty user_num}">disabled="disabled"</c:if>
			><c:if test="${empty user_num }">로그인해야 작성할 수 있습니다.</c:if></textarea>
			<br>
			사용 여부 [
			<input type="radio" name="cate_status" value="0" id="cate_status" >미사용
			<input type="radio" name="cate_status" value="1" id="cate_status" checked="checked" >사용
			 ]
			<!-- <input type="submit" value="추가"> -->
			 
			<c:if test="${!empty user_num }">
				<div id="re_first">
					<span class="letter-count">10/10</span>
				</div>
				<div id="re_second" class="align-right">
					<input type="submit" value="추가" class="submit_button">
				</div>
			</c:if>
		</form>
	</div>
	
	<hr size="2" width="100%" align="center">
	
	<!-- js에 작성할 폼 테스트 -->	
			<table>
				<tr id="cate_tr">
					<th id="cate_num">카테고리번호</th>
					<th id="cate_name">카테고리명</th>
					<th id="cate_status">사용 여부</th>
					<th id="cate_del">수정 / 삭제</th>
				</tr>
			</table>
	
			<!-- 댓글목록 출력시작 -->
			<div id="output"></div>
			<div class="paging-button" style="display:none;">
				<input type="button" value="다음댓글 보기">
			</div>
			<!-- 로딩이미지 노출 -->
			<div id="loading" style="display:none;">
				<img src="${pageContext.request.contextPath}/images/ajax-loader.gif">
			</div>
			<!-- 댓글목록 출력 끝  -->
		
	<!-- 댓글 끝 -->
	

	
</div>
		
	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>