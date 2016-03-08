<%--
  Created by IntelliJ IDEA.
  User: liuwei
  Date: 2016-03-04
  Time: 10:54 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>

<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <img src="${pageContext.request.contextPath}/resources/img/logo_white.png" alt="Ustoria" height="50"><span style="font-size: 20px; line-height: 50px; margin-left: 10px">Ustoria</span>
        </div>

        <h2 id="Project Title">${projectTitle}</h2>
        <!-- <a href="#">My Projects</a> -->
        <ul class="nav navbar-nav navbar-right">

            <li class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#">${email}
                    <span class="caret"></span></a>
                <ul class="dropdown-menu">
                    <li><a href="#">My Stories</a></li>
                    <li><a href="#">Profile</a></li>
                    <li><a href="#">Log Out</a></li>
                </ul>
            </li>

        </ul>
        <form><input class="navbar-right" id="searchbar" type="search" name="search" placeholder="search" ></form>
    </div>
</nav>
