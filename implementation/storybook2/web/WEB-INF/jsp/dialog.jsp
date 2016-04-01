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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dialog.css">
</head>
<body ng-app="dialog">

<jsp:include page="nav_top.jsp" />
<jsp:include page="left-navigation.jsp" />

<div id="content">
    <div id="dialog-ctrl" class="col-lg-12 col-md-12">
        <i class="glyphicon glyphicon-plus-sign"></i>
        <i class="glyphicon glyphicon-minus-sign"></i>
        <select>
            <option>scene1</option>
            <option>scene2</option>
        </select>
        <a href="#" class="btn btn-primary pull-right">Save</a>
    </div>
    <div id="dialog-content" class="col-lg-12 col-md-12">

        <table class="col-lg-12 col-md-12 table table-bordered table-responsive">
            <tr>
                <th class="col-lg-2 col-md-2">Speaker</th>
                <th class="col-lg-4 col-md-4">Text</th>
                <th class="col-lg-2 col-md-2">To Whom</th>
                <th class="col-lg-2 col-md-2">Sound File</th>
                <th class="col-lg-2 col-md-2">Sound Source</th>
            </tr>
            <tr>
                <td class="col-lg-2 col-md-2">
                    <select>
                        <option>aaa</option>
                        <option>bbb</option>
                    </select>
                </td>
                <td class="col-lg-4 col-md-4">
                    <textarea style="width: 100%;"></textarea>
                </td>
                <td class="col-lg-2 col-md-2">
                    <select>
                        <option>aaa</option>
                        <option>bbb</option>
                    </select>
                </td>
                <td class="col-lg-2 col-md-2">
                    <span>sound.mp3</span>
                </td>
                <td class="col-lg-2 col-md-2">
                    <button class="btn btn-info">upload</button>
                </td>
            </tr>
        </table>

    </div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/library/jquery-2.2.0.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/library/jquery-ui-1.10.4.custom.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/library/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/library/jquery.iframe-transport.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/library/jquery.fileupload.js"></script>
<script type="text/javascript">

    // deal with the conflict between jquery and angular (global variable "$")
    // after this, need to use jQuery instead of "$" for jquery's function
    $.noConflict();


</script>

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/library/angular.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/apptest.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/dialog.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/left_sidebar.js"></script>

</body>
</html>
