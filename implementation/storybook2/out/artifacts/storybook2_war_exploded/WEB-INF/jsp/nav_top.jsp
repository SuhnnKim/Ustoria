<%--
  Created by IntelliJ IDEA.
  User: liuwei
  Date: 2016-03-04
  Time: 10:54 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<style>
    #ProjectTitle{
        text-align: center;
    }

</style>

<nav class="navbar navbar-default">
    <div class="container-fluid">
        <%--<div class="navbar-header col-xs-6 col-md-4" >--%>
            <%--<img src="${pageContext.request.contextPath}/resources/img/logo_white.png" alt="Ustoria" height="50"><span style="font-size: 20px; line-height: 50px; margin-left: 10px">Ustoria</span>--%>
        <%--</div>--%>
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <img src="${pageContext.request.contextPath}/resources/img/logo_white.png" alt="Ustoria" height="50"><span style="font-size: 20px; line-height: 50px; margin-left: 10px">Ustoria</span>
        </div>
       <div class="col-xs-6 col-md-4">
        <h3 id="ProjectTitle">${projectTitle}</h3>
       </div>
        <!-- <a href="#">My Projects</a> -->
        <div class="collapse navbar-collapse" id="myNavbar">

            <ul class="nav navbar-nav navbar-right" >

                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">${email}admin@admin.com
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
    </div>
</nav>
