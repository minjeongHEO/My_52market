<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>판매 내역</title>
<style type="text/css">
div li select,input[type="search"],input[type="submit"]{
	list-style-type: none;
	float: left;
	margin-left: 3px;
}
input[type="search"]{
	width: 120px;
}
input[type="submit"]{
	width: 30px;
}
</style>
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/layout.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-3.6.0.min.js">/* 컨텍스트경로부터 명시해야 에러안남 */</script>
<!-- <script type="text/javascript">
	$(function () {
		$('#search_form').submit(function () {
			if($('#keyword').val().trim()==''){
				alert('검색어를 입력하세요!');
				$('#keyword').val('').focus();
				return false;
			}
		});
	});
</script> -->
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="page-main">
		<h2>상품분류 : ${category.cate_name}</h2>
		<form action="saleList.do" method="get" id="search_form">
			<input type="hidden" name="cate_num" value="${category.cate_num}"> 
			<div class="align-center">
			<ul class="search">
				<li>
					<select name="keyfield">
						<option value="1">제목+내용</option>
					</select>
				</li>
				<li>
					<input type="search" size="16" name="keyword" id="keyword" value="${param.keyword }"><!-- value = 검색을 했다면 검색한것이 남아있게함 선택사항임 -->
				</li>
				<li>
					<input type="submit" value="검색" ><br><br>
				</li>
			</ul>
			</div>
		</form>
		<div class="list-space align-right">
			<input type="button" value="상품등록" 
				onclick="location.href='itemWriteForm.do'" <c:if test="${empty user_num}">disabled="disabled"</c:if>>  <!-- 같은경로이기 떄문에 파일경로만 작성했음  -->
		</div>
		<br>
		
		<c:if test="${count == 0 }">
			<div class="result-display">
				표시할 게시물이 없습니다.
			</div>
		</c:if>
		<c:if test="${count > 0 }">
		<div class="item-space">
			<c:forEach var="item" items="${list }">
			<div class="horizontal-area">
				
					<img src="${pageContext.request.contextPath}/upload/${item.filename}" >
					<a href="itemDetail.do?item_num=${item.item_num}" style="color: black;">${item.title }</a>
					<span>
					<br>
					<c:if test="${item.state == 0 }">판매중</c:if>
					<c:if test="${item.state == 1 }">예약중</c:if>
					<c:if test="${item.state == 2 }">판매완료</c:if>
					<br><fmt:formatNumber value="${item.price}" pattern="#,###"/>
					</span>							
				</div>	
				</c:forEach>
				</div>
			</c:if>	
			
				<div class="empty">
				</div>
				<br>
				
			</div>
				<br><br><br><br><div style="text-align : center !important">
				${pagingHtml}
				</div>

	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>  
</html>