<!DOCTYPE html>
<html>
<head>
	<title>Playground</title>
	<style type="text/css">
		[ng\:cloak], [ng-cloak], .ng-cloak{
			display: none;
		}
	</style>
	<jsp:include page="head.jsp"/>
</head>

<body ng-app="playground" ng-controller="MainController" ng-cloak>

<jsp:include page="nav_top.jsp"/>

<div id="show-sidebar" ng-show="sidebar.hidden" ng-click="sidebar.toggle()">
	<span class="glyphicon glyphicon-menu-right"></span>
</div>
<div id="sidebar" ng-hide="sidebar.hidden">
	<div id="hide-sidebar" ng-click="sidebar.toggle()">
		<span class="glyphicon glyphicon-menu-left"></span>
	</div>
	<div class="inner">
		<div id='cssmenu'>

			<ul >
				<li ng-repeat="menuItem in menuItems" ng-class="menuItem.children.length!=0?'has-sub':''"  on-menu-finish-render>
					<a href="#">{{menuItem.name}}</a>
					<ul ng-class="menuItem.children.length != 0?'menu_item_list':''" >
						<li class="menu_item" ng-repeat="subMenuItem in menuItem.children" on-menu-finish-render on-finish-render>
							<a href='#'>{{subMenuItem.name}}</a>
						</li>
					</ul>
				</li>
			</ul>



		</div>
		<div>
			<a id="link-add-category" href="#" data-toggle="modal" data-target="#addCategoryModal">
				<span class="glyphicon glyphicon-plus floating-left" id="add-sign"></span>
				<span id="add-category-text">Add Category</span>
			</a>
		</div>

		<div id="addCategoryModal" class="modal fade" role="dialog" ng-init="newMenuItem=''">
			<div class="modal-dialog ">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Add a Category</h4>
					</div>
					<div class="modal-body"  >
						<div class="col-lg-12" style="padding: 0;">
							<input class="form-control" type="text" name="category_name" id="category_name" ng-model="newMenuItem.name" placeholder="Add new category name"/>
						</div>
						<div id="subItemsGroup" class="clearfix col-lg-10 col-md-10 col-sm-10 col-lg-offset-2 col-md-offset-2 col-sm-offset-2" style="padding: 10px 10px 10px 10px ;">

							<p ng-show="menuSubItems.length === 0">No sub-items yet.</p>

							<ul style="padding-left: 0;">
								<li ng-repeat="subItem in menuSubItems" style='list-style: none;'><img src="${pageContext.request.contextPath}/resources/img/cross.png" alt="X" style="margin-right: 10px;cursor: pointer;"  ng-click="removeSubMenuItem(subItem)">{{subItem.name}}</li>
							</ul>

							<div class="col-lg-10 col-md-10 col-sm-10" style="padding: 0;">
								<input class="form-control" type="text" ng-model="menuSubItem.name" placeholder="add sub-items">
							</div>
							<div class="col-lg-1 col-md-1 col-sm-1" style="padding: 0;">
								<img src="${pageContext.request.contextPath}/resources/img/plus.png" alt="+" style="cursor: pointer;" ng-click="addSubMenuItem(menuSubItem)" />
							</div>
						</div>
					</div>
					<div class="modal-footer" style="clear: both;">
						<p class="pull-left">{{ missingNewMenuItemError }}</p>
						<button class="btn btn-default" id="create_ok" ng-click="addMenuItem(newMenuItem)">OK</button>
						<button class="btn btn-default" ng-click="cancelCategoryModal(newMenuItem, newSubMenuItems)">Close</button>
					</div>
				</div>

			</div>
		</div>


		<div class="link-playground-wrapper"><a id="link-playground" href="#">Playground</a></div>

	</div>
</div>

<div id="myModal" class="modal fade" role="dialog"  ng-controller="addSceneController" ng-init="addSceneController.id_counter = 2" >
	<div class="modal-dialog">

		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Create a scene</h4>
			</div>
			<div class="modal-body">
				<input class="form-control" type="text" name="scene_title" id="new_scene_title" ng-model="title" placeholder="Title"/>
				<p ng-show="title == ''">{{ error_message }}</p>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" id="create_ok" ng-click="addNew(title)">OK</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			</div>
		</div>

	</div>
</div>

<div class="" id="content" expandable ng-controller="scenePanelController as scenePanel">
	<div id="control-panel">
		<a class="ctrl-btn pull-left" id="new-scene" href="#" data-toggle="modal" data-target="#myModal">Create a new scene</a>

		<!-- <a class="ctrl-btn pull-left" id="undo" href="#">Undo</a>
        <a class="ctrl-btn pull-left" id="redo" href="#">Redo</a> -->
		<a class="ctrl-btn pull-right" id="save" href="#">Save</a>
	</div>
	<div id="scene-panel" >
		<!-- loop scenes -->
		<div  style="float: none;"ng-repeat="scene in scenePanel.scenes" class="scene panel" data-toggle="tooltip" title="{{scene.title}}" id="{{scene.id}}" on-finish-render="ngRepeatFinished">
			<div class="panel-heading scene-title">
				{{ scene.title }}
			</div>
			<ul class="panel-body">
				<!-- <div class="member" id="member-left" style="top: 100px; position: absolute; margin-right: 10px;  width: 50px;height: 50px;background-color: white;"></div>
                <div class="member" id="member-right" style="position: absolute; margin-right: 10px;float: left; width: 50px;height: 50px;background-color: white;"></div> -->
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
				<div class="handle" style="transform: translateZ(0px) translateX(99999999px); /*width: 100%;*/">
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

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-2.2.0.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-ui-1.10.4.custom.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/bootstrap.js"></script>

<script type="text/javascript">

	// deal with the conflict between jquery and angular (global variable "$")
	// after this, need to use jQuery instead of "$" for jquery's function
	$.noConflict();

</script>



<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/angular.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jsPlumb-2.0.7.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/static.js"></script>


<!-- <script src="js/ga.js"></script> -->
<script src="${pageContext.request.contextPath}/resources/js/plugins.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/sly.min.js"></script>
<!-- <script src="js/horizontal.js"></script> -->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/drag-drop.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/app.js"></script>
</body>
</html>