<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 목록</title>
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
            alert('검색어를 입력하세요!');
            $('#keyword').val('').focus();
            return false;
         }
      });
   });
</script>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<div class="page-main">
  
   <h2>게시판 목록</h2>
   <form id="search_form" action="oList.do" method="get">
      <ul class="search">
         <li>
            <select name="keyfield">
               <option value="1">제목</option>
               <option value="2">작성자</option>
               <option value="3">내용</option>
            </select>
         </li>
         <li>
            <input type="search" size="16" name="keyword" id="keyword"
                                               value="${param.keyword}">
         </li>
         <li>
            <input type="submit" value="검색">
         </li>
      </ul>
   </form>
   <div class="list-space align-right">
      <input type="button" value="글쓰기" onclick="location.href='oWriteForm.do'" 
      <c:if test="${empty user_num}">disabled="disabled"</c:if>>
      <input type="button" value="목록" onclick="location.href='oList.do'">
      <input type="button" value="홈으로" 
       onclick="location.href='${pageContext.request.contextPath}/main/main.do'">
   </div>
   <c:if test="${count == 0}">
   <div class="result-display">
      표시할 게시물이 없습니다.
   </div>   
   </c:if>
   <c:if test="${count > 0}">
   <table>
      <tr>
         <th>글번호</th>
         <th>제목</th>
         <th>작성자</th>
         <th>작성일</th>
         <th>조회</th>
      </tr>
      <c:forEach var="board" items="${list}">
      <tr>
         <td>${board.board_num}</td>
         <td><a href="oDetail.do?board_num=${board.board_num}">${board.title}</a></td>
         <td>${board.id}</td>
         <td>${board.reg_date}</td>
         <td>${board.hit}</td>
      </tr>   
      </c:forEach>
   </table>
   <div class="align-center">
      ${pagingHtml}
   </div>
   </c:if>
</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>