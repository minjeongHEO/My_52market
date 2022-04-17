<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>판매상품 글상세</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/layout.css"> <%--css스타일 경로--%>
<style type="text/css">
	#output_fav{
		cursor:pointer;
	}
</style>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/item-reply.js"></script>
<script type="text/javascript">
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
   	//====================찜하기===============================
  	   var status;
  	   //좋아요 수 
  	   function selectData(item_num){
  	      $.ajax({
  	         type:'post',
  	         data:{item_num:item_num},
  	         url:'getFav.do',
  	         dataType:'json',
  	         cache:false,
  	         timeout:30000,
  	         success:function(data){
  	            if(data.result=='success'){
  	               displayFav(data);
  	            }else{
  	               alert('좋아요 읽기 오류');
  	            }
  	         },
  	         error:function(){
  	            alert('네트워크 오류');
  	         }
  	      });
  	   }
  	   
  	   //좋아요 등록
  	   $('#output_fav').click(function(){   
  	      $.ajax({
  	         type:'post',
  	         data:{item_num:${item.item_num}},
  	         url:'writeFav.do',
  	         dataType:'json',
  	         cache:false,
  	         timeout:30000,
  	         success:function(data){
  	            if(data.result=='logout'){
  	               alert('로그인 후 좋아요를 눌러주세요!');
  	            }else if(data.result=='success'){
  	               displayFav(data);
  	            }else{
  	               alert('등록시 오류 발생!');
  	            }
  	         },
  	         error:function(){
  	            alert('네트워크 오류!');
  	         }
  	      });
  	   });
  	   
  	   //좋아요 표시
  	   function displayFav(data){
  	      status = data.status;
  	      var count = data.count;
  	      var output;
  	      if(status=='noFav'){
  	         output = '../images/heart01.png';
  	      }else{
  	         output = '../images/heart02.png';
  	      }         
  	      //문서 객체에 추가
  	      $('#output_fav').attr('src',output);
  	      $('#output_fcount').text(count);
  	   }
  	   
  	   
  	   //초기 데이터(목록) 호출
  	   selectData(${item.item_num});
    	 
   	});
</script>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>

<div class="no-line">	<!-- css로 line안보이게 -->
<form id="item_like" action="${pageContext.request.contextPath}/item/likeWrite.do" method="post">
	<input type="hidden" name="oitem_num" id="oitem_num" value="${item.item_num }"  >	<!-- 전송할 때 쓰려고 -->
	<input type="hidden" name="oitem_price" id="oitem_price" value="${item.price }" >	<!-- 연산해서 총 금액 구하려고 -->
</form>	
</div>	

<div class="page-main">
	<h2>판매상품 글상세 </h2>
	<ul>
		<li>제목 : ${item.title}</li>
		<li>판매자 : ${item.mem_id}</li>
		<li>
			<c:if test="${item.state == 0 }">판매상황 : 판매중</c:if>
			<c:if test="${item.state == 1 }">판매상황 : 예약중</c:if>
			<c:if test="${item.state == 2 }">판매상황 : 판매완료</c:if>
		</li>
		<li>조회수 : ${item.hit}</li>
		<li>카테고리 : ${item.cate_num}</li>
		<li>가격 : <fmt:formatNumber value="${item.price}" pattern="#,###"/></li>
	</ul>
	
	 
	
	 
	<hr size="1" noshade width="100%">
	<c:if test="${!empty item.filename}">
		<div class="align-center">
			<img src="${pageContext.request.contextPath}/upload/${item.filename}" class="detail-img">
		</div>
		
	</c:if>
	<p>
		${item.content}
	</p>
	
	<hr size="1" noshade="noshade" width="100%">
	<c:if test="${!empty user_num && user_num == item.mem_num}">
      <input type="button" value="채팅하기" onclick="location.href='${pageContext.request.contextPath}/chatting/chattingListForBuyer.do?item_num=${item.item_num}'">
      </c:if>
      
      <c:if test="${!empty user_num && user_num != item.mem_num && item.state != 2}">	<!-- state(2: 판매완료)아니면 채팅하기가 보임 -->
      <input type="button" value="채팅하기" onclick="location.href='${pageContext.request.contextPath}/chatting/chatting.do?item_num=${item.item_num}&trans_num=${item.mem_num}'">
      </c:if>
      
      <input type="button" value="찜목록" onclick="location.href='${pageContext.request.contextPath}/item/likeList.do'">	
      
      <c:if test="${!empty user_num && user_num != item.mem_num && item.state != 2}">	<!-- state(2 : 판매완료)아니면 찜하기가 보임 -->
      <img id="output_fav" src="${pageContext.request.contextPath}/images/heart01.png"><span id="output_fcount"></span>
      </c:if>
	<div class="align-right">
		<c:if test="${!empty board.modify_date}">
			최근 수정일 : ${item.modify_date}		<%--수정일이 있으면 최근수정일 보이도록 --%>
		</c:if>		
		작성일 : ${item.reg_date}
		
		<%--로그인한 회원번호와 작성자 회원번호가 일치해야 수정, 삭제 가능 --%>	
		<c:if test="${user_num == item.mem_num }">	
			<input type="button" value="수정" onclick="location.href='itemUpdateForm.do?item_num=${item.item_num}'">
			<input type="button" value="삭제" id="delete_btn" onclick="location.href='itemDelete.do?item_num=${item.item_num}'">		
			
			<script type="text/javascript">
				let delete_btn = document.getElementById('delete_btn');
				//이벤트 연결
				delete_btn.onclick=function(){
					let choice = confirm('삭제하시겠습니까?');
					if(choice){
						location.replace('itemDelete.do?item_num=${item.item_num}'); 
							//히스토리를 지우고(전페이지로 back불가) + delete로 이동하고 싶으면 location.replace
							//히스토리를 남기면서(back가능) 이동하고 싶으면 location.href를 쓰면됨
					}
				};
			</script>	
		</c:if>	
		<input type="button" value="목록" onclick="location.href='saleList.do'">	
	</div>
	
	<!-- 댓글시작 -->
	<div id="reply_div">
		<span class="re_title">댓글 달기</span>
		<form id="re_form">	<!-- ajax방식이기때문에 action이 없다. -->	<!-- value는 부모글번호  -->
			<input type="hidden" name="item_num" value="${item.item_num }" id="item_num">
			
			<textarea rows="3" cols="50" name="re_content" id="re_content" class="rep-content"
			<c:if test="${empty user_num}">disabled="disabled"</c:if>
			><c:if test="${empty user_num }">로그인해야 작성할 수 있습니다.</c:if></textarea>
			<!-- textarea태그 사이의 공백은 모두 데이터로 인식하기때문에, 공백을 넣으면안됨 -->
			
			
			<c:if test="${!empty user_num }">
				<div id="re_first">
					<span class="letter-count">300/300</span>
				</div>
				<div id="re_second" class="align-right">
					<input type="submit" value="전송">
				</div>
			</c:if>
		</form>
	</div>
			<!-- 댓글목록 출력시작 -->
			<div id="output"></div>
			<div class="paging-button" style="display:none;">
				<input type="button" value="다음댓글 보기">
			</div>
			<div id="loading" style="display:none;">
				<img src="${pageContext.request.contextPath}/images/ajax-loader.gif">
			</div>
			<!-- 댓글목록 출력 끝  -->
		
	<!-- 댓글 끝 -->
	</div>	
	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
	</body>
</html>