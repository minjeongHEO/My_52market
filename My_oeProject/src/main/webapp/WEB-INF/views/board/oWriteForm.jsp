<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
   $(function(){
       $('#write_form').submit(function(){
         /*if($('#kind').val().trim()==''){
            alert('질문의 분류를 선택하세요!');
            return false;
         } */
         if($('#title').val().trim()==''){
               alert('제목을 입력하세요!');
               $('#title').val('').focus();
               return false;
           }
         if($('#content').val().trim()==''){
               alert('내용을 입력하세요!');
               $('#content').val('').focus();
               return false;
         }
      });
   });
</script>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<div class="page-main">
<br><br>
   <form id="write_form" action="oWrite.do" method="post" enctype="multipart/form-data">
   <h2 style="text-align: center">글쓰기</h2><br> 
      <ul style="padding: 0 0 0 75px;">
         <!-- <li>
            <label for="kind">질문분류</label>
            <select name="kind">
               <option value="0">신고합니다</option>
               <option value="1">상품문의</option>
               <option value="2">광고문의</option>
            </select>
            <p>
            </li> -->
         <li>
            <label for="title">제목</label>
            <input type="text" name="title" id="title" maxlength="50">
         </li>
         <li>
            <label for="content">내용</label>
            <textarea rows="5" cols="40" name="content" id="content"></textarea>
         </li>
         <li>
            <label for="filename">파일</label>
            <input type="file" name="filename" id="filename" accept="image/gif,image/png,image/jpeg">
         </li>
      </ul>
      <div class="align-center">
         <input type="submit" value="등록">
         <input type="button" value="목록" onclick="location.href='oList.do'">
      </div>
   </form>
</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>