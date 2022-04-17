<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메인</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
<script type="text/javascript" src="../js/bootstrap.min.js"></script>
<link rel="stylesheet" href="../css/bootstrap.min.css" type="text/css">
</head>

<body>
    <jsp:include page="/WEB-INF/views/common/header.jsp"/>
    

<div class="container">
  	<div class="container" style="padding-top:200px;">
  		<div class="row">
  			<div class="col-mg-9">
  				<div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
  				
  					<!-- Indicators : 이미지를 개별적으로 선택할 수 있도록 해주는 태그 / 사진아래 동그라미 네개-->
  					<ol class="carousel-indicators">
  							<!-- 이 네개 li가 어떻게 아래의 이미지 링크랑 연결되는거지 ?  
  									data target과 data-slide-to를 보고 순서에맞게 내부적으로 알아서 찾아감-->
  						<li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
  						<li data-target="#carousel-example-generic" data-slide-to="1"></li>

  					</ol>
  					
  					<!-- 이미지 링크 (슬라이드되는 부분) -->
  					<div class="carousel-inner">
  					
  						<div class="item active">
  						<a href="${pageContext.request.contextPath }/board/oList.do">
  							<img src="../images/test1.png" alt="First slide">
  						</a>	
  							<div class="carousel-caption">
  							</div>
  						</div>
  						
  						<div class="item">
  						<a href="${pageContext.request.contextPath }/board/oList.do">
  							<img src="../images/test2.png" alt="Second slide">
  						</a>	
  							<div class="carousel-caption">
  							</div>
  							
  						</div>
  					
  					</div>		<!-- end of carousel-inner -->
  					
  					<!-- 
  					<!-- Controls : 이전, 다음으로 갈 수 있도록 해주는 기능 
  					<a class="left carousel-control"
  						href="#carousel-example-generic"
  						data-slide="prev">
  					<span class="glyphicon glyphicon-chevron-left"></span></a>	
  							<!-- class=glyphicon glyphicon-chevron-left 내장된 아이콘  
  					
  					<a class="right carousel-control"
  						href="#carousel-example-generic"
  						data-slide="next">
  					<span class="glyphicon glyphicon-chevron-right"></span></a> -->
 			
  				</div>
  			</div>
  		</div>
	</div>
</div>

    
    
<div class="page-main2">
		<!-- include태그 위치 저기 맞아 ? -->
	<div>
	<br>
		<h3>실시간 OE 판매 상품 </h3>  
	<br><br>
		<div class="item-space">
			<c:forEach var="item" items="${itemList }">
			<div class="horizontal-area">
				<a href="${pageContext.request.contextPath }/item/itemDetail.do?item_num=${item.item_num}">
					<img src="${pageContext.request.contextPath}/upload/${item.filename}" >
					<span>${item.title }
					<br>
					<b><fmt:formatNumber value="${item.price }"/>원</b>
					</span>
				</a>
			</div>
			</c:forEach>
	<br>
			<div class="float-clear">
				<hr width="100%" size="1" noshade="noshade">
			</div>
		</div>	
	</div>
</div>
    <jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
