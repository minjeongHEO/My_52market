<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>My Home</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>

<script type="text/javascript">
	$(function() {
		let photo_path = $('.my-photo').attr('src');//처음 화면에 보여지는 이미지 읽기
		let my_photo;

		$('#photo_btn').click(function() {
			$('#photo_choice').show();
			$(this).hide();//수정 버튼 감추기
		});
		//이미지 미리보기 취소
		$('#photo_reset').click(function() {
			//이미지 미리보기 전 이미지로 되돌리기
			$('.my-photo').attr('src', photo_path);
			$('#photo').val('');
			$('#photo_choice').hide();
			$('#photo_btn').show(); //수정 버튼 노출
		});

		//이미지 선택 및 이미지 미리보기
		$('#photo').change(function() {
			my_photo = this.files[0];
			if (!my_photo) {
				$('.my-photo').attr('src', photo_path);
				return;
			}

			if (my_photo.size > 1024 * 1024) {
				alert('1MB까지만 업로드 가능!');
				photo.value = '';
				return;
			}

			var reader = new FileReader();
			reader.readAsDataURL(my_photo);

			reader.onload = function() {
				$('.my-photo').attr('src', reader.result);
			};
		});//end of change

		//이미지 전송
		$('#photo_submit').click(function() {
			if ($('#photo').val() == '') {
				alert('파일을 선택하세요!');
				$('#photo').focus();
				return;
			}

			//파일 전송
			let form_data = new FormData();
			form_data.append('photo', my_photo);
			$.ajax({
				url : 'updateMyPhoto.do',
				type : 'post',
				data : form_data,
				dataType : 'json',
				contentType : false, //데이터 객체를 문자열로 바꿀지 지정 true이면 일반문자
				processData : false, //해당 타입을 true 로 하면 일반 text로 구분
				enctype : 'multipart/form-data',
				success : function(param) {
					if (param.result == 'logout') {
						alert('로그인 후 사용하세요!');
					} else if (param.result == 'success') {
						alert('프로필 사진이 수정되었습니다.');
						photo_path = $('.my-photo').attr('src');
						$('#photo').val('');
						$('#photo_choice').hide();
						$('#photo_btn').show();
					} else {
						alert('파일 전송 오류 발생');
					}
				},    
				error : function() {
					alert('네트워크 오류 발생');
				}
			});
		});
	});
</script>

</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>

<div class="page-main-home" >
<div id="greenbox">
	<p>MY HOME</p>
	<div class="mypage-div">
		<div class="mypage-div-2">
			<c:if test="${empty member.mem_photo}">
				<img src="${pageContext.request.contextPath}/images/face.png" 
				     width="150" height="150" class="my-photo">
			</c:if>
			<c:if test="${!empty member.mem_photo}">
				<img src="${pageContext.request.contextPath}/upload/${member.mem_photo}"
				     width="150" height="150" class="my-photo"> 
			</c:if>
		</div>
		<div class="mypage-div-3">
			<img alt="oi" src="${pageContext.request.contextPath}/images/oi.png" width="38" height="35">
			<b id="my_home_b">${member.mem_nick}(${member.mem_id})님의 <font color="green">MY HOME</font>입니다.</b><br>
			<a href='${pageContext.request.contextPath}/member/myPage.do' >회원정보 수정</a>
		</div>
	<br>
	</div>
	
	<div class="align-center-home">
		<ul>	
			<li>
				<input type="button" value="판매내역" 
				onclick="location.href='${pageContext.request.contextPath}/item/mySaleList.do'">
	
				<input type="button" value="구매내역" 
				onclick="location.href='${pageContext.request.contextPath}/item/buyListForm.do'">
	
				<input type="button" value="채팅목록" 
				onclick="location.href='${pageContext.request.contextPath}/chatting/chattingBox.do'">
	
				<input type="button" value="찜목록" 
				onclick="location.href='${pageContext.request.contextPath}/item/likeList.do'">
	
				<input type="button" value="문의게시판" 
				onclick="location.href='${pageContext.request.contextPath}/board/oList.do'">
			</li>
		</ul>
	</div>
</div>
</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>