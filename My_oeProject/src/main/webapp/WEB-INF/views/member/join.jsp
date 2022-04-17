<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입 완료</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<div class="page-main" style="padding-top: 300px; margin: 0 auto;">
	<div class="result-display2">
		<div class="align-center">
			<b><font color="green">52MARCKET</font>&nbsp;회원가입이 완료되었습니다.</b>
			<br>
			<br>
			<input type="button" value="로그인" onclick="location.href='${pageContext.request.contextPath}/member/loginForm.do'">&nbsp;
			<%-- <a href="${pageContext.request.contextPath}/main/main.do"><img alt="홈으로" src="${pageContext.request.contextPath}/images/home.jpg" width="30px" height="30px"></a> --%>
			<input type="button" value="홈" onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
		</div>
	</div>
</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>