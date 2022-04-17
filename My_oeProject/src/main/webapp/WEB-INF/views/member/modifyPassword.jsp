<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- !!아래는 mvcPage_modifypassWord.jsp code이다 -->

<c:if test="${check }">
	<script type="text/javascript">
		alert('비밀번호 수정했습니다');
		location.href='myPage.do';
	</script>
</c:if>
<c:if test="${!check }">
	<script type="text/javascript">
		alert('아이디 또는 비밀번호가 불일치합니다');
		history.go(-1);
	</script>
</c:if>