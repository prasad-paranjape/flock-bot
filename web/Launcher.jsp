<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: shashwat.ku
  Date: 25/9/16
  Time: 3:08 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<ul>
<c:forEach items="${requestScope.customerList}" var="customer">
    <li>${customer}</li>
</c:forEach>
</ul>
</body>
</html>
