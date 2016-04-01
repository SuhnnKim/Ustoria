<%--
  Created by IntelliJ IDEA.
  User: Zhipe
  Date: 2016-03-23
  Time: 3:30 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Search Result</title>
    <jsp:include page="head.jsp" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/search.css">
</head>
<body>
<jsp:include page="nav_top.jsp" />
<h2>Search Result</h2>
<p>keyword: ${search}</p>
<p>found: ${CharacterCount} records in Charater</p>
<c:forEach var="character" items="${foundCharacterList}">
    <p><a href="<c:out value="loadCharacter?id=${character.getId()}"/>"><c:out value="${character.getName()}"/></a></p>
</c:forEach>

<p>found: ${SummaryCount} records in Summary</p>
<c:forEach var="summary" items="${foundSummaryList}">
    <p><c:out value="${summary.content}"/></p>
</c:forEach>


</body>
</html>
