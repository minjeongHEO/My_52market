<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<!-- header 시작 -->
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0" />
<%-- <link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css"> --%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/header_footer.css">

</head>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<div class="header">
	<header id="main_header" >
	 
<%-- ===첫번째 메뉴=== --%>
	<%-- **로고 --%>
		<div class="logo-img">
			<a href="${pageContext.request.contextPath}/main/main.do">
				<img src="${pageContext.request.contextPath}/images/oi.png" width="65" height="60" class="logo-img">
			</a>
		</div>
		<div class="logo-txt">
			<a href="${pageContext.request.contextPath}/main/main.do">
			<h2><b id="logo_text">52MARKET</b></h2></a>
		</div>
		 
	<%-- **검색창 --%>
 		<form class="navbar-form navbar-right" id="search_form" action="mainList.do" method="get">
            <div class="form-group search">
              <input type="search" name="keyword" id="keyword" class="form-control input-lg" placeholder="오늘은 이거 ? ! " />
            </div>
            <button type="submit" id="search_btn" class="btn btn-success btn-lg" >검색</button>
          </form>
		

	
	<%-- **관리자)회원관리 --%>	
		<c:if test="${!empty user_num && user_auth == 2}"> <%--관리자일 경우--%>
			<a href="${pageContext.request.contextPath}/member/adminMemberList.do">회원관리</a>
		</c:if>
		
	<%-- **MY HOME/MAMAGER HOME--%>	
		<c:if test="${!empty user_num}"><%--로그인 했을 때--%>
			<c:if test="${!empty user_num && user_auth < 2}"> <%--회원일 경우--%>
				<a href="${pageContext.request.contextPath}/member/myHome.do">MY HOME</a>
			</c:if>
			<c:if test="${!empty user_num && user_auth == 2}"> <%--관리자일 경우--%>
				<a href="${pageContext.request.contextPath}/member/myHome.do">MANAGER HOME</a>
			</c:if>
		</c:if>
		
	<%-- **프로필 사진/user아이디 --%>		 
		<c:if test="${!empty user_num && !empty user_photo }"> <%--회원, 사진O--%>
			<img src="${pageContext.request.contextPath}/upload/${user_photo}" width="25" height="25" class="my-photo">
		</c:if>
		<c:if test="${!empty user_num && empty user_photo}"> <%--회원, 사진X--%>
			<img src="${pageContext.request.contextPath}/images/oi.png" width="30" height="25" class="my-photo">
		</c:if>
		<c:if test="${!empty user_num}">
			<c:if test="${!empty user_num && user_auth < 2}"> <%--회원일 경우--%>
				<a><span id="menu_logout">[&nbsp;${user_id}&nbsp;]회원 님</span>&nbsp;</a>
			</c:if>
			<c:if test="${!empty user_num && user_auth == 2}"> <%--관리자일 경우--%>
				<a><span id="menu_logout">[&nbsp;${user_id}&nbsp;]관리자</span>&nbsp;</a>
			</c:if>
		</c:if>
	
	<%-- **로그인/로그아웃 --%>			
		<c:if test="${empty user_num}"><%--로그인 안했을 때--%>
			<a href="${pageContext.request.contextPath}/member/loginForm.do">로그인</a>
		</c:if>
		<c:if test="${!empty user_num }"><%--로그인 했을 때--%>
			<a href="${pageContext.request.contextPath}/member/logout.do">로그아웃</a>
		</c:if>
	</header>
	
<%-- ===두번째 메뉴=== --%>
	<div class="second-menu">
	<nav class="navbar navbar-nav centered " id="second_menu" >
        <div class="collapse navbar-collapse navbar-ex1-collapse">
          	<ul class="nav navbar-nav">
	<%-- **카테고리 --%>			
		      	<li class="dropdown " id="nav1">
		        	<a href="#" class="dropdown-toggle" data-toggle="dropdown">상품목록</a>
			        	<ul class="dropdown-menu">
		    		 	<c:forEach var="cate" items="${cateName_list}">
					        <li><a class="dropdown" id="cate_name_menu" href="${pageContext.request.contextPath}/item/saleList.do?cate_num=${cate.cate_num}">${cate.cate_name}</a></li>
		       			</c:forEach>
				        </ul>
		      	</li>
				
	<%-- **상품등록 --%>			
		        <c:if test="${!empty user_num }"><%--로그인 했을 때--%>
		            <li id="nav2">
		            <a href="${pageContext.request.contextPath}/item/itemWriteForm.do">상품등록</a></li>
	            </c:if>	 			
		        <c:if test="${!empty user_num }"><%--로그인 했을 때--%>
		            <li id="nav4">
		            <a href="${pageContext.request.contextPath}/item/likeList.do">찜목록</a></li>
	            </c:if> 
	 <%-- **채팅목록 --%>			
		        <%-- active로 기본적으로 보이게한다 --%>
		        <c:if test="${!empty user_num }"><%--로그인 했을 때 --%>
		        	<li id="nav5">
		        	<a href="${pageContext.request.contextPath}/chatting/chattingBox.do">채팅목록</a></li>
		        </c:if>            
	            	      
	 <%-- **문의게시판 --%>			
		       	<c:if test="${!empty user_num }"><%--로그인 했을 때--%>
		        	<li id="nav6">
		        	<a href="${pageContext.request.contextPath}/board/oList.do">문의 게시판</a></li>
		        </c:if>	         
   <%-- 
    **카테고리 임시 테스트메뉴 
	            <c:if test="${!empty user_num && user_auth == 2}"> 관리자일 경우
		            <li id="nav7">
		            <a href="${pageContext.request.contextPath}/category/adminCate.do">카테고리테스트</a></li>
	            </c:if> --%>
		        
          </ul>
        </div>
    </nav>
    </div>
     
</div>	

	
</body>
</html>  