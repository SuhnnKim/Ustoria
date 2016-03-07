<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<title>Summary</title>
	<style type="text/css">
		[ng\:cloak], [ng-cloak], .ng-cloak{
			display: none;
		}



	</style>

<jsp:include page="head.jsp" />

</head>
<body ng-app="playground" ng-controller="MainController" ng-cloak>

<jsp:include page="nav_top.jsp" />

<jsp:include page="left-navigation.jsp" />
<div class="" id="content" expandable ng-controller="characterPanelController as characterPanel">
	<div id="control-panel">
		<a class="ctrl-btn pull-left" id="save-story" href="#">Save Story</a>
		<a class="ctrl-btn pull-left" id="undo" href="character.form">Character</a>
		<a class="ctrl-btn pull-left" id="redo" href="playground.form">PlayGround</a>

		<button class="ctrl-btn pull-right" id="saveText"  data-toggle="modal" data-target="#myModal">Create Section</button>
	</div>

	<div id="main-panel">
		<div id="summary-panel" contentEditable="true" data-placeholder="Please Enter your summary here">
            <c:if test="${not empty summary}">
                ${summary}
            </c:if>
        </div>

		<div id="summary-section-list">
			<c:if test="${not empty summaryList}">

				<c:forEach var="summary" items="${summaryList}">
					<div id="${summary.getName()}" class="summary-section-wrapper"><button type="button" class="btn btn-info summary-section-button">${summary.getName()}</button><button type="button" class="btn btn-success btn-small"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></button><button type="button" class="btn btn-danger btn-small"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></button></div>
				</c:forEach>

			</c:if>

		</div>


	</div>

</div>


<div id="timeline-panel"></div>


<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title" id="myModalLabel">Please enter the summary section name</h4>
			</div>
			<div class="modal-body">
				<form class="form-inline">
					<div class="form-group" >
						<label for="summaryName">Name</label>
						<input type="text" class="form-control" id="summaryName" placeholder="Summary Section Name">
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				<button type="button" class="btn btn-primary" id="btnSummaryNameSave">Save</button>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-2.2.0.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-ui-1.10.4.custom.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/resources/vendor/jquery.ui.widget.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery.iframe-transport.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery.fileupload.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery.highlight-5.closure.js"></script>

<script type="text/javascript">

	// deal with the conflict between jquery and angular (global variable "$")
	// after this, need to use jQuery instead of "$" for jquery's function
	$.noConflict();

	///// for sidebar menu's animation
	jQuery('#cssmenu li.active').addClass('open').children('ul').show();
	jQuery('#cssmenu li.has-sub>a').on('click', function(){
		jQuery(this).removeAttr('href');
		var element = jQuery(this).parent('li');
		if (element.hasClass('open')) {
			element.removeClass('open');
			element.find('li').removeClass('open');
			element.find('ul').slideUp(200);
		}
		else {
			element.addClass('open');
			element.children('ul').slideDown(200);
			// element.siblings('li').children('ul').slideUp(200);
			// element.siblings('li').removeClass('open');
			element.siblings('li').find('li').removeClass('open');
			// element.siblings('li').find('ul').slideUp(200);
		}
	});




</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/summary.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/angular.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/dragula.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/apptest.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/character.js"></script>

<script src="${pageContext.request.contextPath}/resources/js/ga.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/plugins.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/sly.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/horizontal.js"></script>
</body>
</html>
