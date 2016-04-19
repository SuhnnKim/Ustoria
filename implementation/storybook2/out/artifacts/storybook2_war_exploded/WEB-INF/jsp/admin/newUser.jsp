<%--
  Created by IntelliJ IDEA.
  User: liuwei
  Date: 2016-03-18
  Time: 12:49 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="sping" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>New User</title>
    <jsp:include page="../head.jsp" />
</head>
<body>
<jsp:include page="../nav_top.jsp"/>
<div class="container">
    <form class="form-horizontal" action="saveNew" method="post">
        <div class="form-group">
            <label class="control-label col-sm-2" for="name">Name:</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="name" name="name" placeholder="username"/>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-2" for="email">Email:</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="email" name="email" placeholder="email"/>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-sm-2" for="password">Password:</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="password" name="password" placeholder="password"/>
            </div>
        </div>

        <div class="form-group">
            <label class="control-label col-sm-2" for="role">Role:</label>
            <div class="col-sm-10">
                <select id="role" class="form-control" name="role">
                    <option value="0" selected="selected">User</option>
                    <option value="1">Admin</option>
                </select>
            </div>
        </div>

        <div class="form-group">
            <div class="control-label col-sm-4"/>
            <div class="col-sm-9">
                <input type="submit" class="control-label btn btn-default" value="Save">
            </div>

        </div>
    </form>

</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/library/jquery-2.2.0.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/library/bootstrap.js"></script>




</body>
</html>
