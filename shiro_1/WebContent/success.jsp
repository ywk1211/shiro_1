<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h3>Success Page</h3>
	<shiro:hasAnyRoles name="admin">
		
		<br/><br/>
		<a href="admin.jsp">To Admin Page</a>
	
	</shiro:hasAnyRoles>
	
	<shiro:hasAnyRoles name="user">
		
		<br/><br/>
		<a href="user.jsp">To User Page</a>
	
	</shiro:hasAnyRoles>
	<br/><br/>
	<shiro:hasAnyRoles name="user">
		
		<a href="test">Test Shiro Annotation</a>
		<br/><br/>
	
	</shiro:hasAnyRoles>
	<a href="shiro-logout">Logout</a>
</body>
</html>