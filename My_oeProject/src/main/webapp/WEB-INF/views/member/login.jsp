<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
	<%-- 탈퇴회원(0인 경우) 일때 --%>
<c:when test="${auth == 0}">
	<!DOCTYPE html>
	<html>
	<head>
	<meta charset="UTF-8">
	<title>로그인 정보</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
	</head>
	<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<br>
<br>
	<div class="page-main">
		<div class="result-display">
			<div class="align-center">
				탈퇴된 계정입니다.
				<p>
				<input type="button" value="홈으로" 
			onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
			</div>
		</div>
	</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
	</body>
	</html>
	</c:when>

	<%-- 정지회원외의 모든 회원 전부 --%>
	<c:otherwise>
	<script type="text/javascript">
		alert('아이디 또는 비밀번호가 불일치합니다.');
		history.go(-1);
	</script>
	</c:otherwise>
</c:choose>