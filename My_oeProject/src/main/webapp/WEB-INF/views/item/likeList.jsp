<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>찜하기</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(function(){
		//장바구니 상품 삭제
		$('.like-del').on('click',function(){
			$.ajax({
				url:'likeDelete.do',
				data:{like_num:$(this).attr('data-likenum')},
				dataType:'json',
				cache:false,
				timeout:30000,
				success:function(param){
					if(param.result == 'logout'){
						alert('로그인 후 사용하세요!');
					}else if(param.result == 'success'){
						alert('선택하신 상품을 삭제했습니다.');
						location.href='likeList.do';
					}else{
						alert('삭제시 오류 발생');
					}
				},
				error:function(){
					alert('네크워크 오류 발생');
				}
			});
		});
		
	});
</script>
</head>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<body>
<div class="page-main">
   <c:if test="${empty list}">
   <div class="result-display">
      찜하기에 담은 상품이 없습니다.
   </div>
   </c:if>
   
   <c:if test="${!empty list}">
    <div>
      <h3> OE 찜하기 상품 </h3>   
      
      <div class="item-space">
         
         <c:forEach var="item" items="${list}">
         <div class="like">
         
            <a href="${pageContext.request.contextPath}/item/itemDetail.do?item_num=${item.item_num}">
               <img src="${pageContext.request.contextPath}/upload/${item.item.filename}" >
               <br>${item.item.title }
               <c:if test="${item.item.state == 0 }">[구매가능]</c:if>
               <c:if test="${item.item.state == 1 }">[예약중]</c:if>
               <c:if test="${item.item.state == 2 }">[판매완료]</c:if>
               <br>
               <b><fmt:formatNumber value="${item.item.price }"/>원</b>
               <br>
               <input type="button" value="삭제" class="like-del" data-likenum="${item.like_num}">
            </a>
         </div>
         </c:forEach>
         <div class="empty"></div>
      </div>   
   </div>
   </c:if>   
   </div>
 <jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>