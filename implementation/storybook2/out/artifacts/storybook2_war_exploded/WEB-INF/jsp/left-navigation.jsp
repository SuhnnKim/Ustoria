<%--
  Created by IntelliJ IDEA.
  User: AshirwadTank
  Date: 3/4/2016
  Time: 3:20 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>

<div id="show-sidebar" ng-show="sidebar.hidden" ng-click="sidebar.toggle()">
    </span><span class="glyphicon glyphicon-menu-right"></span>
</div>
<div id="sidebar" ng-hide="sidebar.hidden">
    <div id="hide-sidebar" ng-click="sidebar.toggle()">
        <span class="glyphicon glyphicon-menu-left"></span>
    </div>
    <div class="inner">
        <div id='cssmenu'>
            <ul>

                <li class="has-sub">
                    <a href='#'>Character</a>
                    <ul id="character-list">
                        <c:if test="${not empty characterList}">

                            <c:forEach var="character" items="${characterList}">
                                <li class="draggable menu_item" id=<c:out value="${character.getName()}" /> >
                                    <a href="<c:out value="loadCharacter?id=${character.getId()}"/>"><c:out value="${character.getName()}"/></a>
                                </li>
                            </c:forEach>

                        </c:if>

                    </ul>
                </li>
                <li class="has-sub">
                    <a href='#'>Summary</a>
                    <ul id="summary-list">
                        <c:if test="${not empty summaryList}">

                            <c:forEach var="summary" items="${summaryList}">
                                <li id=<c:out value="${summary.getName()}" /> >
                                    <a href="#" ><c:out value="${summary.getName()}"/></a>
                                </li>
                            </c:forEach>

                        </c:if>

                    </ul>
                </li>
            </ul>
        </div>
        <div><a id="link-add-category" href="#"><span class="glyphicon glyphicon-plus floating-left" id="add-sign"></span><span id="add-category-text">Add Category</span></a></div>
        <div class="link-playground-wrapper"><a id="link-playground" href="#">Playground</a></div>

    </div>
</div>


