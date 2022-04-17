<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원목록</title>
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
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
   $(function(){
      $('#search_form').submit(function(){
         if($('#keyword').val().trim()==''){
            alert('검색어를 입력하세요');
            $('#keyword').val('').focus();
            return false;
         }
      });
   });
</script>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<div class="page-main" style="padding-top: 220px; margin: 0 auto;">
   <h2>회원목록(관리자 전용)</h2>
   <form id="search_form" action="adminMemberList.do" method="get">
      <ul class="search">
         <li>
            <select name="keyfield">
               <option value="1">ID</option>
               <option value="2">이름</option>
               <option value="3">회원등급</option>
            </select>
         </li>
         <li>
            <input type="search" size="16" name="keyword" id="keyword">
         </li>
         <li>
         	<input type="submit" value="찾기">
         </li>
      </ul>
   </form>
   <br><br>
   <div class="list-space align-right">
      <input type="button" value="목록" onclick="location.href='adminMemberList.do'">
      <input type="button" value="홈으로" 
       onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
   </div>
   <c:if test="${count == 0}">
   <div class="result-display">
      표시할 내용이 없습니다.
   </div>   
   </c:if>
   <c:if test="${count > 0}">
   <table>
   <thead>
      <tr>
         <th>회원번호</th>
         <th>회원ID</th>
         <th>회원이름</th>
         <th>연락처</th>
         <th>회원가입날짜</th>
         <th>계정상태</th>
         <th>회원등급</th>
      </tr>
      </thead>
      <tbody>
      <c:forEach var="member" items="${list}">
      <tr>
         <td>${member.mem_num}</td>
         <td>
            <c:if test="${member.mem_auth > 0}">
            <a href="adminDetailUserForm.do?mem_num=${member.mem_num}">${member.mem_id}</a>
            </c:if>
            <c:if test="${member.mem_auth == 0}">${member.mem_id}</c:if>
         </td>
         <td>${member.mem_nick}</td>
         
         <td>${member.mem_phone}</td>
         <td>${member.mem_date}</td>
         <td>
         <c:if test="${member.mem_auth == 0}">탈퇴</c:if>
         <c:if test="${member.mem_auth == 1}">일반</c:if>
         <c:if test="${member.mem_auth == 2}">관리</c:if>
         </td>
         <td>${member.mem_auth}</td>
      </tr>
      </c:forEach>
      </tbody>
   </table>
 <br>
   <div class="align-center">
      ${pagingHtml}
   </div>
   </c:if>
</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>  
</body>
</html>