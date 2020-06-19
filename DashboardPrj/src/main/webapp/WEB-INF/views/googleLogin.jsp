<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Map"%>
<%
String googleUrl = "";
if(null != request.getAttribute("googleUrl")){
	googleUrl = ""+request.getAttribute("googleUrl");	
}
%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript">
<%
if(!"".equals(googleUrl)){	//구글 로그인 페이지 이동
%>
	location.href = "<%=googleUrl%>";
<%
} else {		//구글 로그인 후 결과값 핸들링
	String success = ""+request.getAttribute("success");
	if("Y".equals(success)){
%>
	window.opener.location.href = "/";
	window.close();
<%
	} else {
		String failMsg = "";
		if(null != request.getAttribute("failMsg")){
			failMsg = ""+request.getAttribute("failMsg");
		}
%>
	alert('<%=failMsg%>');
	window.close();
<%
	}
}
%>
</script>
</head>
<body>
</body>
</html>
