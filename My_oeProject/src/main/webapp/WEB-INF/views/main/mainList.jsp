<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> 검색글 찾기 </title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/layout.css"> <%--css스타일 경로--%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
</head>

<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>

<div class="page-main">

	<c:if test="${count == 0}">
		<div class="result-display">
			 검색된 게시물이 없습니다.
		</div>
	</c:if>
	
	<div>
	<c:if test="${count > 0}">
		<h3> 검색상품 </h3> 
		<div class="item-space">
			<c:forEach var="item" items="${list }">
			<div class="horizontal-area">
				<a href="${pageContext.request.contextPath }/item/itemDetail.do?item_num=${item.item_num}">
					<img src="${pageContext.request.contextPath}/upload/${item.filename}" >
					<span>${item.title }</span>
					<br>
					<b><fmt:formatNumber value="${item.price }"/>원</b>
					<br>
					<span>${item.mem_num }</span>
					<br>
					<span>${item.reg_date }
					</span>
				</a>
			</div>
			</c:forEach>
			<div class="float-clear">
				<hr width="100%" size="1" noshade="noshade">
			</div>			
			<div class="align-center">
			${pagingHtml}
			</div>
		</div>
	</c:if>	

	<div class="list-space align-right">							
		<input type="button" value="홈으로" 
				onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
	</div>
</div>
</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>