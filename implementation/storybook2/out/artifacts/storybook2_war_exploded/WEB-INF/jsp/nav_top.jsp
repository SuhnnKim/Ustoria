<%--
  Created by IntelliJ IDEA.
  User: liuwei
  Date: 2016-03-04
  Time: 10:54 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<style>
    #project-title h3 {
        text-align: center;
    }

    .navbar-brand{
        padding-top: 0 ;
        padding-bottom: 0;
    }

    #main_nav{
        height: 96px;
    }

    #main_nav form{
        margin-top: 0;
        margin-bottom: 0;
        height: 96px;
    }
    #main_nav form div{
        margin: 22px 0;
    }

    #project-title {
        margin: 15% 0;
        font-size: 25px;
    }

    #myNavbar {
        padding: 1% 0;
        margin: 1% 0;
    }

    #saveStory{
        margin:30% 0;
    }

    #navbar-collapse-1 > ul > li > a{
        height: 96px;
        line-height: 66px;
    }
</style>

<nav class="navbar navbar-default" role="navigation" id="main_nav">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navbar-collapse-1">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="#">
            <img src="${pageContext.request.contextPath}/resources/img/logo_whole_white.png" alt="Ustoria" height="96">
        </a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="navbar-collapse-1">
        <ul class="nav navbar-nav" id="nav_links">
            <c:choose>
                <c:when test="${pageName == 'Projects'}">
                    <li <c:if test="${pageName == 'Projects'}">class="active"</c:if> ><a href="${pageContext.request.contextPath}/home">My Stories</a></li>
                </c:when>
                <c:otherwise>
                    <li <c:if test="${pageName == 'Projects'}">class="active"</c:if> ><a href="${pageContext.request.contextPath}/home">My Stories</a></li>
                    <li <c:if test="${pageName == 'Summary'}">class="active"</c:if> ><a href="summary">Summary</a></li>
                    <li <c:if test="${pageName == 'Character'}">class="active"</c:if> ><a href="character.form">Character</a></li>
                    <li <c:if test="${pageName == 'Dialog'}">class="active"</c:if> ><a href="dialog">Dialog</a></li>
                    <li <c:if test="${pageName == 'Playground'}">class="active"</c:if> ><a href="playground.form">Playground</a></li>
                    <li><button type="button" class="btn btn-danger" id="saveStory">Save Story</button></li>
                </c:otherwise>
            </c:choose>
        </ul>
        <div class="col-sm-2 col-md-2">
            <p id="project-title">${projectTitle}</p>

        </div>
        <ul class="nav navbar-nav navbar-right">
            <li> <form action="/${storyId}/search" method="get" class="navbar-form" role="search">

                <div class="input-group">
                    <input class="navbar-right" id="searchbar" type="search" name="search" placeholder="search">

                </div>
            </form>
            </li>
            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">${email} <b class="caret"></b></a>
                <ul class="dropdown-menu">
                    <li><a href="#">Action</a></li>
                    <li><a href="#">Another action</a></li>
                    <li><a href="#">Something else here</a></li>
                    <li class="divider"></li>
                    <li><a href="/logout">Log out</a></li>
                </ul>
            </li>
        </ul>
    </div><!-- /.navbar-collapse -->
</nav>
