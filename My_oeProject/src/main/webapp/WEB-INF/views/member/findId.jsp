<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
 
 <c:choose>
 
	<c:when test="${FindId==NotSame}">
		<script type="text/javascript">
			alert('회원이름과 전화번호가 불일치합니다.')
			history.go(-1);
		</script>	
	</c:when> 
	
	<c:when test="${FindId==NotExist}">
		<script type="text/javascript">
			alert('존재하지않는 회원정보입니다.')
			history.go(-1);
		</script>	
	</c:when>
	
    <c:otherwise> 
		<!DOCTYPE html>
		<html>
		<head>
		<meta charset="UTF-8">
		<title> ID찾기 </title>
		<link rel="stylesheet" href="${pageContext.request.contextPath }/css/layout.css">
		
		</head>
		<body>
		<jsp:include page="/WEB-INF/views/common/header.jsp"/>
			<div class="page-main">
				<h2> 회원 ID </h2>
				<div class="result-display">
					<div class="align-center">
						회원님의 아이디는 ${Success}입니다.
						<p>
						<input type="button" value="홈으로" onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
					</div>
				</div>
			</div>
			<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
		</body>
		</html>
	</c:otherwise>	
</c:choose>