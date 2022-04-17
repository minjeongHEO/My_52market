<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>판매 글수정</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
$(function() {
	$('#update_form').submit(function(){
		if($('#title').val().trim()==''){
			alert('판매글 제목을 입력하세요.');
			$('#title').val('').focus();
			return false;
		}
		if($('#price').val()==''){
			alert('물품 가격을 입력하세요.');
			$('#price').val('').focus();
			return false;
		}
		if($('#content').val().trim()==''){
			alert('판매 설명을 입력하세요.');
			$('#content').val('').focus();
			return false;
		}
	});
});

$(function(){
    let photo_path = $('.my-file').attr('src');//처음 화면에 보여지는 이미지 읽기(기본값 셋팅)
    let my_photo;
       
    //***이벤트3 )이미지 선택 및 이미지 미리보기
    $('#filename').change(function(){
       my_photo = this.files[0];
       if(!my_photo){
          $('.my-file').attr('src',photo_path);//처음 원본 이미지로 설정
          return;
       }
       
       //용량체크
       if(my_photo.size > 1024*1024){
          alert('1MB까지만 업로드 가능!');
          $(this).val(''); //선택하지 못하게 막기
          return;
       }
       
       var reader = new FileReader(); //파일객체
       reader.readAsDataURL(my_photo);
       
       reader.onload=function(){
          $('.my-file').attr('src',reader.result);
       };
    });//end of change
 });
</script>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<div class="page-main">
<br><br>
	<form action="itemUpdate.do" method="post" enctype="multipart/form-data" id="update_form">
	<h2 style="text-align: center">판매상품 글수정</h2><br> 
		<input type="hidden" name="item_num" value="${item.item_num}"> 
		<ul style="padding: 0 0 0 100px;">
			<li>
			<label for="cate">카테고리</label>
			<select name="cate_num">
	         <c:forEach var="cate" items="${cateName_list}">
	         <option value="${cate.cate_num}" <c:if test="${cate.cate_num == item.cate_num}">selected</c:if>>${cate.cate_name}</option>	
	         </c:forEach>
         </select>
		</li>
			<li>
				<label for="title">판매글 제목</label>
				<input type="text" name="title" id="title" value="${item.title}" maxlength="50">
			</li>
			<li>
				<label for="price">가격</label>
				<input type="number" name="price" id="price" value="${item.price}" min="1" max="9999999">				
			</li>
			<c:if test="${item.state == 1 or item.state == 2}">
			<li>
				<label for="state">판매상태</label>
				<input type="radio" name="state" id="state1" value="0" <c:if test="${item.state == 0}">checked</c:if>>판매중
				<input type="radio" name="state" id="state2" value="1" <c:if test="${item.state == 1}">checked</c:if>>예약중	
				<input type="radio" name="state" id="state3" value="2" <c:if test="${item.state == 2}">checked</c:if>>판매완료		
			</li>
			</c:if>
			<li>
				<label for="content">내용</label>
				<textarea rows="5" cols="30" name="content" id="content">${item.content}</textarea>
			</li>
			<li>
				<label for="filename">판매상품 사진</label>
				<input type="file" name="filename" id="filename" accept="image/gif,image/png,image/jpeg">
				
				<%-- 사진 미리보기 --%>
      		<li>
         		<c:if test="${empty item.filename}">
         		<img src="${pageContext.request.contextPath}/images/face.png" 
               		width="200" height="200" class="my-file">
         		</c:if>
     
      		
				<c:if test="${!empty item.filename}">
				<img src="${pageContext.request.contextPath}/upload/${item.filename}"
                           		width="200" height="200" class="my-file">
				<br>
				<span id="file_detail">
					(${item.filename})파일이 등록되어 있습니다. 
					다시 파일을 업로드하면 기존 파일은 삭제됩니다.
					<input type="button" value="파일삭제" id="file_del">
				</span>
				
<script type="text/javascript">
	$(function(){
		$('#file_del').click(function(){
			let choice = confirm('삭제하시겠습니까?');
			if(choice){
				$.ajax({
					url:'itemDeleteFile.do',
					type:'post',
					data:{item_num:${item.item_num}},
					dataType:'json',
					cache:false,
					timeout:30000,
					success:function(param){
						if(param.result == 'logout'){
							alert('로그인 후 사용하세요!');
						}else if(param.result == 'success'){
							$('#file_detail').hide();
						}else if(param.result == 'wrongAccess'){
							alert('잘못된 접속입니다.');
						}else{
							alert('파일 삭제 오류 발생');
						}
					},
					error:function(){
						alert('네트워크 오류 발생!');
					}
				});
			}
		});
	});
</script>
				</c:if>
				</li>                    
		</ul>   
		<div class="align-center">
			<input type="submit" value="수정">
			<input type="button" value="목록" onclick="location.href='saleList.do'">
		</div>                                                    
	</form>
</div>

<jsp:include page="/WEB-INF/views/common/footer.jsp"/>	
</body>
</html>