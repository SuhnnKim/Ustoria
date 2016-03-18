<%--
  Created by IntelliJ IDEA.
  User: Zhipe
  Date: 2016-03-16
  Time: 7:14 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Dialog Page</title>
    <jsp:include page="head.jsp" />
</head>
<body>

<jsp:include page="nav_top.jsp" />
<jsp:include page="left-navigation.jsp" />

<div id="dialog-main" class="container">
    <div id="dialog-ctrl" class="col-lg-12 col-md-12">
        <i class="glyphicon glyphicon-plus-sign"></i>
        <i class="glyphicon glyphicon-minus-sign"></i>
    </div>
    <div class="col-lg-2 col-md-2">Speaker</div>
    <div class="col-lg-4 col-md-4">Text</div>
    <div class="col-lg-2 col-md-2">To Whom</div>
    <div class="col-lg-2 col-md-2"></div>
    <div class="col-lg-2 col-md-2">Sound Source</div>
</div>

</body>
</html>
