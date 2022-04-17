<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MY PAGE</title>

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

<div class="page-main-mypage" >

   <h1 class="mypage-txt">MY PAGE</h1><br>
   
   <div class="mypage-div1">
   		<div class="profile" >
    	<h2>프로필 사진</h2><br>
	    	<ul>
		      	<li id="profile">
		            <c:if test="${empty member.mem_photo}">
		            	<img src="${pageContext.request.contextPath}/images/face.png" width="150" height="150" class="my-photo">
		            </c:if>
		            <c:if test="${!empty member.mem_photo}">
		            	<img src="${pageContext.request.contextPath}/upload/${member.mem_photo}" width="150" height="150" class="my-photo"> 
		            </c:if>
		         </li> 
		         <li>
		            <div class="modify-btn">
		               <input type="button" value="수정" id="photo_btn">
			            <div id="photo_choice" style="display:none;">
			               <input type="file" id="photo" accept="image/gif,image/png,image/jpeg"><br>
			               <input type="button" value="전송" id="photo_submit">
			               <input type="button" value="취소" id="photo_reset">
			            </div>
		            </div>
		         </li>
	      	</ul>
      	</div>
	   <div class="info-modify" >
	      <h2>회원정보확인/수정</h2><br><br>
	      <ul>
	         <li>이름 : ${member.mem_nick}</li>
	         <li>전화번호 : ${member.mem_phone}</li>
	         <li>이메일 : ${member.mem_email}</li>
	         <li>우편번호 : ${member.mem_zipcode}</li>
	         <li>주소 : ${member.mem_addr} ${member.mem_addr2}</li>
	         <li>가입일 : ${member.mem_date}</li>
	         <c:if test="${!empty member.mem_modifydate}">
	         	<li>최근 정보 수정일 : ${member.mem_modifydate}</li><br>
	         </c:if>
	         <li>
	            <input id="info-modify-btn" type="button" value="수정" onclick="location.href='modifyUserForm.do'">
	         </li>
	      </ul>
	    </div>
     </div>
      
   <div class="mypage-div2">
      
      <div class="ad-box" align="center">
       <!-- style="position: absolute; top: 730px; left:400px; border: 1px;width: 400px;height: 190px; overflow: hidden;" -->
  		<img class="image_ad" src="${pageContext.request.contextPath}/images/oemarket.png">
      </div>
      
      <div class="passwd-modify" >
	      <h2>보안관리</h2>
	      <div style="padding-top: 25px;">
		      <b>52MARCKET</b> 로그인 시, <br>사용하는 비밀번호를 변경할 수 있습니다.
		      <br><br>
		      주기적인 <b><font color="red">비밀번호 변경</font></b>으로 개인정보를 안전하게 보호하세요.<br><br>
		      <ul>
		         <li>
		            <a href="modifyPasswordForm.do">비밀번호<b>변경하기</b></a>
		         </li>
		      </ul>
	      </div>
      </div>
   </div>
   
   
</div>

<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>