<%--
  Created by IntelliJ IDEA.
  User: liuwei
  Date: 2016-03-04
  Time: 10:54 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<style>
    #project-title h3{
        text-align: center;
    }
    #project-title{
        padding:2% 0;
    }
    #myNavbar{
        padding:1% 0;
        margin:1% 0;
    }
</style>

<nav class="navbar navbar-default">
    <div class="container-fluid">
        <%--<div class="navbar-header col-xs-6 col-md-4" >--%>
            <%--<img src="${pageContext.request.contextPath}/resources/img/logo_white.png" alt="Ustoria" height="50"><span style="font-size: 20px; line-height: 50px; margin-left: 10px">Ustoria</span>--%>
        <%--</div>--%>
        <div class="navbar-header col-md-2">
            <img src="${pageContext.request.contextPath}/resources/img/logo_whole_white.png" alt="Ustoria" height="96">
        </div>
       <div class="col-md-6" id="project-title">
        <h3>${projectTitle}</h3>
       </div>
        <!-- <a href="#">My Projects</a> -->
        <div class="col-md-4" id="myNavbar">

            <ul class="nav navbar-nav navbar-right" >

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
            <form action="search" method="get">
                <input class="navbar-right" id="searchbar" type="search" name="search" placeholder="search" >

            </form>

        </div>
    </div>
</nav>
