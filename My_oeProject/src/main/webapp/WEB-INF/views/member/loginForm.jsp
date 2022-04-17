<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(function(){
		$('#login_form').submit(function(){
			if($('#id').val().trim()==''){
				alert('아이디를 입력하세요');\
				$('#id').val('').focus();
				return false;
			} 
			if($('#passwd').val().trim()==''){
				alert('비밀번호를 입력하세요!');
				$('#passwd').val('').focus();
				return false;
			}
		});
	});
</script>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<div class="page-main" style="padding-top: 250px; margin:0 auto; text-align: center;">
	<form id="login_form" action="login.do" method="post">
		<div class="align-center">
		<h2>중고 직거래 사이트 오이마켓</h2>
			<img alt="oi" src="${pageContext.request.contextPath}/images/oi.png" width="90" height="90">
			<h4><font size="5" color="green">오</font>늘은 <font size="5" color="green">이</font>거 어때요? <br> 지금 지구를 위해 오이를 시작해보세요!</h4>	
		<ul>
			<li>
				<!--<label for="id" >id</label>  -->
				<input type="text" name="id" id="id" maxlength="12" placeholder="UserID">
			</li>
			<li>
				<!--<label for="passwd" >비밀번호</label>  -->
				<input type="password" name="passwd" id="passwd" maxlength="12" placeholder="PassWord">
			</li>
		</ul>
		</div>
		<div class="align-center">
			<p>
			<input type="submit" value="시작하기" style="background-color:green; color:white; width:50%; height: 30px;"><br>
			<p>
			 
			<a href="${pageContext.request.contextPath}/main/main.do"><img alt="홈으로" src="${pageContext.request.contextPath}/images/home.jpg" width="30px" height="30px"></a>
			<%-- <input type="button" value="홈으로" onclick="location.href='${pageContext.request.contextPath}/main/main.do'""> --%>
			<%-- <input type="button" value="ID찾기" onclick="location.href='${pageContext.request.contextPath}/member/findIdForm.do'"> --%>
		</div>
		<br>
		<div class="align-left">
			<font size="3" color="gray">아직 계정이 없으신가요?</font> <a href="joinForm.do" style="color: black"><b>회원가입</b></a>
			<font size="3" color="gray">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			아이디를 잊으셨나요?</font><a href="${pageContext.request.contextPath}/member/findIdForm.do" style="color: black"><b>&nbsp;id찾기</b></a>
		</div>
	</form>
</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html> 