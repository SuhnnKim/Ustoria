<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<title>Playground</title>
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

<div id="myModal" class="modal fade" role="dialog"  ng-controller="addSceneController" ng-init="addSceneController.id_counter = 2; title=''" >
	<div class="modal-dialog">

		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Create a scene</h4>
			</div>
			<div class="modal-body">
				<input class="form-control" type="text" name="scene_title" id="new_scene_title" ng-model="title" placeholder="Title" />

			</div>
			<div class="modal-footer">
				<p class="pull-left" ng-show="title == ''">{{ error_message }}</p>
				<button type="button" class="btn btn-default" ng-click="addNew(title)">OK</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			</div>
		</div>

	</div>
</div>

<div class="" id="content" expandable ng-controller="scenePanelController as scenePanel">

	<div id="myEditScenesModal" class="modal fade" role="dialog" ng-init="addSceneController.id_counter = 2; title=''" >
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Delete Scenes</h4>
				</div>
				<div class="modal-body">
					<ul>
						<li ng-repeat="scene in scenes">
							<img src="${pageContext.request.contextPath}/resources/img/cross.png" alt="X" style="margin-right: 10px;cursor: pointer;"  ng-click="removeScene(scene)">{{scene.title}}
						</li>
					</ul>

				</div>
				<div class="modal-footer">
				</div>
			</div>

		</div>
	</div>

	<div id="control-panel">
		<a class="ctrl-btn pull-left" id="new-scene" href="#" data-toggle="modal" data-target="#myModal">New scene</a>
		<a class="ctrl-btn pull-left" href="#" data-toggle="modal" data-target="#myEditScenesModal">Delete Scenes</a>
		<a class="ctrl-btn pull-left" id="undo" href="#">Undo</a>
		<a class="ctrl-btn pull-left" id="redo" href="#">Redo</a>
		<a class="ctrl-btn pull-left" id="character" href="character.form">Character</a>
		<a class="ctrl-btn pull-left" id="summary" href="summary.form">Summary</a>
		<a class="ctrl-btn pull-right" id="save" href="#">Save</a>
	</div>
	<div id="play_wrap" style="width: inherit;height: inherit;">
		<div id="scene-panel" >
			<!-- loop scenes -->
			<div ng-repeat="scene in scenes" class="scene panel" data-toggle="tooltip" title="{{scene.title}}" id="{{scene.id}}" on-finish-render="ngRepeatFinished">
				<div class="panel-heading scene-title">
					{{ scene.title }}
				</div>
				<ul class="panel-body drop-panel">
				</ul>
			</div>
		</div>

		<div id="timeline_panel" class="wrap" ng-controller="TimeLineController">

			<div id="timeline_show" class="toggle_timeline toggle_btn" ng-show="timeline.hidden" ng-click="timeline.toggle()">
				<span class="glyphicon glyphicon-menu-up"></span>
			</div>

			<div id="timeline_hide" class="toggle_timeline toggle_btn" ng-show="!timeline.hidden" ng-click="timeline.toggle()">
				<span class="glyphicon glyphicon-menu-down"></span>
			</div>

			<div id="timeline_wrapper">
				<div class="scrollbar">
					<div class="handle">
						<div class="mousearea"></div>
					</div>
				</div>



				<div class="frame" id="timeline_wrap" style="overflow: hidden;">
					<ul id="timeline" class="clearfix" style="transform: translateZ(0px) translateX(-99999999px); /*width: 100%;*/">
						<li>0</li><li>1</li><li>2</li><li>3</li><li>4</li><li>5</li><li>6</li><li>7</li><li>8</li><li>9</li>
						<li>10</li><li>11</li><li>12</li><li>13</li>
					</ul>
				</div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-2.2.0.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-ui-1.10.4.custom.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery.iframe-transport.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery.fileupload.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery.highlight-5.closure.js"></script>
<script type="text/javascript">

	// deal with the conflict between jquery and angular (global variable "$")
	// after this, need to use jQuery instead of "$" for jquery's function
	$.noConflict();

	jQuery(function() {


		jQuery(".draggable").draggable({

			helper: "clone"

		});
		jQuery( ".drop-panel" ).droppable({
			activeClass: "ui-state-default",
			hoverClass: "ui-state-hover",
			accept: ":not(.ui-sortable-helper)",
			drop: function( event, ui ) {
				jQuery( this ).find( ".placeholder" ).remove();
				jQuery(ui.draggable).appendTo(this);
			}
		}).sortable({
			items: "li:not(.placeholder)",
			sort: function() {
				jQuery( this ).removeClass( "ui-state-default" );
			}
		});

	})


</script>



<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/summary.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/angular.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/apptest.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jsPlumb-2.0.7.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/character.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/plugins.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/sly.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/app.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/drag-drop.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/static.js"></script>
</body>
</html>