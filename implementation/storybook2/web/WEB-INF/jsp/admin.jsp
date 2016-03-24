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
    <title>Admin</title>
    <jsp:include page="head.jsp" />
</head>
<body>
<jsp:include page="nav_top.jsp"/>
<div class="container">
    <div class="table-responsive">
        <table class="table">
            <tr>
                <th><input type="checkbox"/>Check All</th>
                <th>User List</th>
                <th>Delete Selected</th>
            </tr>
            <c:forEach items="${users}" var="user">
                <tr>
                    <td><input type="checkbox"/></td>
                    <td><strong>${user.getUserName()}</strong></td>
                    <td><a href="#" class="btn btn-default">Delete</a></td>
                </tr>
            </c:forEach>
        </table>
    </div>

</div>


<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-2.2.0.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/bootstrap.js"></script>






</body>
</html>
