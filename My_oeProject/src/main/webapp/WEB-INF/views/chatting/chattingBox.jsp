<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>채팅메시지함</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
$(function(){
	function selectData(pageNum){
		$('#output').empty();
		
		$.ajax({
			url:'chattingBoxAjax.do',
			dataType:'json',
			cache:false,
			timeout:30000,
			success:function(param){
				if(param.result == 'logout'){
					alert('로그인해야 채팅 메시지함을 볼 수 있습니다.');
				}else if(param.result == 'success'){
					$(param.list).each(function(index,item){
						let output = '<br><div class="output2" style="background-color: #ffd891; padding : 10px 0 0 0; width: 500px; height: 50px; vertical-align : middle; border-radius: 15px; text-align: center; margin: 0 auto; font-size:20px;">';
						output += '<a style="color:black;"; href="chatting.do?item_num='+item.item_num+'&trans_num='+item.from_num+'">' + item.title + '('+item.chatstate_num+')</a>';
						output += '</div><br>';
						
						//문서 객체에 추가
						$('#output').append(output);
					});
				}
				
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		});
		
	}
	selectData();
});
</script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<br><br><br><br><br><br><br><br>
	<h2 style="text-align: center; padding-top : 70px; ">${user_id}님의 채팅메시지함</h2>
	<div id="output"></div>
</div>
 <jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>