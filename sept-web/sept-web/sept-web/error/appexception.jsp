<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.lang.Exception"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>AppException</title>
</head>
<%
	String exceptiontext = (String) request.getAttribute("exception");
%>
<body>
	<font size="3" style="color: red;"><%=exceptiontext%></font>
</body>
</html>