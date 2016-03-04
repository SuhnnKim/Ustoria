<!DOCTYPE html>
<html>
<head>
	<title>Character</title>
	<style type="text/css">
	[ng\:cloak], [ng-cloak], .ng-cloak{
		display: none;
	}
	</style>
	<jsp:include page="head.jsp" />
</head>

<body ng-app="playground" ng-controller="MainController" ng-cloak>

	<nav class="navbar navbar-default">
	  <div class="container-fluid">
	    <div class="navbar-header">
	      <a class="navbar-brand" href="#">Ustoria</a>
	    </div>
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
				   <li class='active' ><a style="border-bottom: dashed 2px white;" href='index.jsp'>Home</a></li>
				   <li  class='has-sub'><a href='#'>Characters</a>
				      <ul id="saveListLeft">
				         <li><a href='#'>Diana</a></li>
				         <li><a href='#'>Prannoy</a></li>
				         <li><a href='#'>Zhipeng</a></li>
				         <li><a href='#'>Wei</a></li>
						  ${sidebar}
				      </ul>
				   </li>
				   <li class='has-sub'><a href='#'>Locations</a>
				      <ul>
				         <li><a href='#'>Humber College</a></li>


				         <!-- <li><a href='#'>Location</a></li> -->
				      </ul>
				   </li>
				   <li><a href='#'>Summary</a></li>
				</ul>
			</div>
			<div><a id="link-add-category" href="#"><span class="glyphicon glyphicon-plus floating-left" id="add-sign"></span><span id="add-category-text">Add Category</span></a></div>
			<div class="link-playground-wrapper"><a id="link-playground" href="#">Playground</a></div>

		</div>
	</div>


	<form action="saveCharacter.form">
	<div class="" id="content" expandable ng-controller="characterPanelController as characterPanel">
		<div id="control-panel">
			<a class="ctrl-btn pull-left" id="new-character" href="newCharacter.form">Create a new character</a>
			<a class="ctrl-btn pull-left" id="undo" href="summary.form">Summary</a>
			<a class="ctrl-btn pull-left" id="redo" href="playground.form">PlayGround</a>
			<input type="submit" class="ctrl-btn pull-right" name="save" id="save" value="Save"/>
		</div>
		<div id="character-panel">
			<div id="character-form">
				<div class="row" id="row1">
					<!-- <img src="#" class="col-sm-3" alt="Image" height="240" width="240"> -->
					<a href="#" id="pop" class="col-sm-3">
    				<img id="imageresource" src="${pageContext.request.contextPath}/resources/img/noimage.png" alt="Image" height="240" width="220"/>
					</a>

					<div class="modal fade" id="imagemodal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				  <div class="modal-dialog">
				    <div class="modal-content">
				      <div class="modal-header">
				        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
				        <h4 class="modal-title" id="myModalLabel">Image Upload</h4>
				      </div>
				      <div class="modal-body">
				        <!-- <img src="" id="imagepreview"> -->
								<label class="control-label">Select File</label>
								<input id="input-1" type="file" class="file">

				      </div>
				      <div class="modal-footer">
								<button type="button" class="btn btn-default" data-dismiss="modal">Save</button>
				        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				      </div>
				    </div>
				  </div>
				</div>
					<div class="col-sm-8">
						<label class="radio-inline">
							<input type="text" class="form-control" name="name" id="name" placeholder="Name" value="${name}"/>
						</label>
						<label class="radio-inline">
							Model:
						</label>
						<label class="radio-inline">
							<input type="radio" name="model" value="2D">2D</input>
						</label>
						<label class="radio-inline">
							<input type="radio" name="model" value="3D">3D</input>
						</label>
					</div>
					<div class="col-sm-8">
						<textarea class="form-control" name="desc" rows="8" id="desc" placeholder="Description">${desc}</textarea>
					</div>
				</div>
				<div class="row" id="row2">

					<!-- Relationship list -->
					<div class="col-sm-2">
						<ul name="relationshipList" class="list-group">
							<table id="relationship">
								<tr><td>Relationship Attribute</td></tr>
							</table>
						</ul>
						<div class="btn-group btn-block dropup">
							<button id="addRel" type="button" class="btn btn-primary btn-block dropdown-toggle" data-toggle="dropdown">
								Add+
							</button>
							<ul id="relationAddlist" class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu">
							  <li><a href="">Attribute1</a></li>
							  <li><a href="">Attribute2</a></li>
							  <li><a href="">Attribute3</a></li>
							  <li class="divider"></li>
							  <li><a href="">Attribute4</a></li>
							</ul>
						</div>
					</div>

					<!-- Role list -->
					<div class="col-sm-2">
						<ul class="list-group">
							<table id="role">
								<tr><td>Role Attribute</td></tr>
							</table>
						</ul>
						<div class="btn-group btn-block dropup">
							<button id="addRole" type="button" class="btn btn-primary btn-block dropdown-toggle" data-toggle="dropdown">
								Add+
							</button>
							<ul id="roleAddlist" class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu">
							  <li><a href="">Attribute1</a></li>
							  <li><a href="">Attribute2</a></li>
							  <li><a href="">Attribute3</a></li>
							  <li class="divider"></li>
							  <li><a href="">Attribute4</a></li>
							</ul>
						</div>
					</div>

					<!-- Scene list -->
					<div class="col-sm-2">
						<ul class="list-group">
							<table id="scene">
								<tr><td>Scene Attribute</td></tr>
							</table>
						</ul>
						<div class="btn-group btn-block dropup">
							<button id="addScene" type="button" class="btn btn-primary btn-block dropdown-toggle" data-toggle="dropdown">
								Add+
							</button>
							<ul id="sceneAddlist" class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu">
							  <li><a href="">Attribute1</a></li>
							  <li><a href="">Attribute2</a></li>
							  <li><a href="">Attribute3</a></li>
							  <li class="divider"></li>
							  <li><a href="">Attribute4</a></li>
							</ul>
						</div>
					</div>

					<!-- Attribute list -->
					<div class="col-sm-2">
						<ul class="list-group">
							<table id="att">
								<tr><td>Attribute</td></tr>
							</table>
						</ul>
						<div class="btn-group btn-block dropup">
							<button id="addAtt" type="button" class="btn btn-primary btn-block dropdown-toggle" data-toggle="dropdown">
								Add+
							</button>
							<ul id="attAddlist" class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu">
							  <li><a href="">Attribute1</a></li>
							  <li><a href="">Attribute2</a></li>
							  <li><a href="">Attribute3</a></li>
							  <li class="divider"></li>
							  <li><a href="">Attribute4</a></li>
							</ul>
						</div>
					</div>
						<div class="col-sm-1"></div>
				</div>
			</div>

			<div id="character-list">
				${thumbnail}
			</div>

			</div>


		</div>

		<div class="frame" id="timeline_wrap" style="overflow: hidden;">
				
			</div>
	</form>

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-2.2.0.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-ui-1.10.4.custom.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/resources/vendor/jquery.ui.widget.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery.iframe-transport.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery.fileupload.js"></script>
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
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/dragula.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/apptest.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/character.js"></script>

<script src="${pageContext.request.contextPath}/resources/js/ga.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/plugins.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/sly.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/horizontal.js"></script>

</body>
</html>
