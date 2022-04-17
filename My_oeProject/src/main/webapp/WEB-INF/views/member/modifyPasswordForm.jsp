<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 수정</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/layout.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-3.6.0.min.js">/* 컨텍스트경로부터 명시해야 에러안남 */</script>

<!-- !!아래는 mvcPage_modifypassWordForm.jsp code이다 -->
<!--  member.properies 설정파일에 경로작성 잊지않기 -->

<script type="text/javascript">
	$(function(){
		//이벤트연결
		$('#password_form').submit(function () {
			if($('#id').val().trim()=='') {
				alert('아이디를 입력하세요!');
				$('#id').val('').focus();
				return false;
			}
			if($('#origin_passwd').val().trim()=='') {
				alert('현재 비밀번호를 입력하세요!');
				$('#origin_passwd').val('').focus();
				return false;
			}
			if($('#passwd').val().trim()=='') {
				alert('새 비밀번호를 입력하세요!');
				$('#passwd').val('').focus();
				return false;
			}
			if($('#cpasswd').val().trim()=='') {
				alert('새 비밀번호 확인을 입력하세요!');
				$('#cpasswd').val('').focus();
				return false;
			}
			if($('#passwd').val()!=$('#cpasswd').val()) {
				alert('새 비밀번호가 일치하지 않습니다');
				$('#cpasswd').val('').focus(); /* #cpasswd를 지우고 다시 입력하도록 */
				return false;
			}
		});//End of submit
		
		//입력한 다음 다시 바꾸는 경우에(새 비밀번호 확인까지 명시한 후)
		//다시 새비밀번호를 수정하려고 하면 새비밀번호 확인을 초기화
		$('#passwd').keyup(function () {
			$('#cpasswd').val('');
			$('#message_cpasswd').text('');
		})
		
		//새비밀번호와 새비밀번호확인 일치여부 체크
		$('#cpasswd').keyup(function () {
			if($('#passwd').val()== $('#cpasswd').val()){
				$('#message_cpasswd').text('새 비밀번호 일치');
			}else{
				$('#message_cpasswd').text('');
			}
		})
	});
</script>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<div class="page-main"> 
<br><br>
	<form action="modifyPassword.do" method="post" id="password_form">
	<h2 style="text-align: center">나의 PW수정하기</h2><br>
	<ul style="padding: 0 0 0 100px;">
		<li>
			<label for="id">아이디</label>
			<input type="text" name="id" id="id" value="${member.mem_nick}" maxlength="18" placeholder="id를 입력하세요.">
		</li>
		<li>
			<label for="origin_passwd">현재 비밀번호</label>
			<input type="password" name="origin_passwd" id="origin_passwd" maxlength="18" placeholder="현재 비밀번호를 입력하세요.">
		</li>
		<li>
			<label for="passwd">새 비밀번호</label>
			<input type="password" name="passwd" id="passwd" maxlength="18" placeholder="변경할 비밀번호를 입력하세요.">
		</li>		
		<li>
			<label for="cpasswd">새 비밀번호 확인</label>
			<input type="password" name="cpasswd" id="cpasswd" maxlength="18" placeholder="변경할 비밀번호를 확인하세요.">
			<!-- 전송은 되는데 처리는 안할 것 -->
			<span id="message_cpasswd"></span>
		</li>
	</ul>
	<br>
	<div class="align-center">
		<input type="submit" value="수정" style="background-color:green; color:white; width:10%;">
		<input type="button" value="MyPage" onclick="location.href='myPage.do'">
		<!-- <input type="submit" value="비밀번호 수정"> --><br><br>
		<!-- <input type="button" value="MyPage" onclick="location.href='myPage.do'"> -->
	</div>
	</form>
</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>