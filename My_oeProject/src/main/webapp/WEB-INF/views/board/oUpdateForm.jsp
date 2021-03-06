<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글수정</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<div class="page-main">
<br><br>
   <form action="oUpdate.do" method="post" enctype="multipart/form-data" id="update_form">
      <h2 style="text-align: center">게시판 글수정</h2><br> 
      <input type="hidden" name="board_num" value="${board.board_num}"> 
	  <ul style="padding: 0 0 0 75px;">
         <li>
            <label for="title">제목</label>
            <input type="text" name="title" id="title" 
                                 value="${board.title}" maxlength="50">
         </li>
         <li>
            <label for="content">내용</label>
            <textarea rows="5" cols="30" name="content" 
                               id="content">${board.content}</textarea>
         </li>
         <li>
            <label for="filename">파일</label>
            <input type="file" name="filename" id="filename" 
                                accept="image/gif,image/png,image/jpeg">
            <c:if test="${!empty board.filename}">
            <br>
            <span id="file_detail">
               (${board.filename})파일이 등록되어 있습니다. 
               다시 파일을 업로드하면 기존 파일은 삭제됩니다.
               <input type="button" value="파일삭제" id="file_del">
            </span>
<script type="text/javascript">
   $(function(){
      $('#file_del').click(function(){
         let choice = confirm('삭제하시겠습니까?');
         if(choice){
            $.ajax({
               url:'oDeleteFile.do',
               type:'post',
               data:{board_num:${board.board_num}},
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
         <input type="button" value="목록" onclick="location.href='oList.do'">
      </div>                                                    
   </form>
</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>