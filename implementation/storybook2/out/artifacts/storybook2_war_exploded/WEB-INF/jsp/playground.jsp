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

	<jsp:include page="nav_top.jsp"/>

	<jsp:include page="left-navigation.jsp" />

	<div id="myModal" class="modal fade" role="dialog"  ng-controller="addSceneController" ng-init="addSceneController.id_counter = 2" >
	  <div class="modal-dialog">

	    <!-- Modal content-->
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal">&times;</button>
	        <h4 class="modal-title">Create a scene</h4>
	      </div>
	      <div class="modal-body">
	        <input class="form-control" type="text" name="scene_title" id="new_scene_title" ng-model="title" placeholder="Title" />
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

			<a class="ctrl-btn pull-left" id="undo" href="#">Undo</a>
			<a class="ctrl-btn pull-left" id="redo" href="#">Redo</a>
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



		<div id="timeline-panel" class="wrap">
			<div class="scrollbar">
				<div class="handle" style="transform: translateZ(0px) translateX(760px); width: 190px;">
					<div class="mousearea"></div>
				</div>
			</div>

			<div class="frame" id="timeline_wrap" style="overflow: hidden;">
				<ul id="timeline" class="clearfix" style="transform: translateZ(0px) translateX(-4560px); width: 6840px;">
					<li>0</li><li>1</li><li>2</li><li>3</li><li>4</li><li>5</li><li>6</li><li>7</li><li>8</li><li>9</li>
					<li>10</li><li>11</li><li>12</li><li>13</li><li>14</li><li>15</li><li>16</li><li>17</li><li>18</li>
					<li>19</li><li>20</li><li>21</li><li>22</li><li>23</li><li>24</li><li>25</li><li>26</li><li>27</li>
					<li>28</li><li>29</li>
				</ul>
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
	///////
</script>



<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/angular.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jsPlumb-2.0.7.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/static.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/drag-drop.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/app.js"></script>

<script src="${pageContext.request.contextPath}/resources/js/ga.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/plugins.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/sly.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/horizontal.js"></script>

</body>
</html>