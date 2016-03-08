<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

	<jsp:include page="nav_top.jsp"/>

	<jsp:include page="left-navigation.jsp" />

	<form id="saveForm" method="post">
	<div class="" id="content" expandable ng-controller="characterPanelController as characterPanel">
		<div id="control-panel">
			<a class="ctrl-btn pull-left" id="new-character" href="#">New character</a>
			<a class="ctrl-btn pull-left" id="undo" href="summary.form">Summary</a>
			<a class="ctrl-btn pull-left" id="redo" href="playground.form">PlayGround</a>
			<input type="hidden" id="uuid" value="${loadCharacter.id}"/>
			<input type="submit" class="ctrl-btn pull-right" id="save" name="save" value="Save"/>
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
							<input type="text" class="form-control" name="name" id="name" placeholder="Name" value="${loadCharacter.name}"/>
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
						<textarea class="form-control" name="desc" rows="8" id="desc" placeholder="Description">${loadCharacter.characterDescription}</textarea>
					</div>
				</div>
				<div class="row" id="row2">

					<!-- Relationship list -->
					<div class="col-sm-2">
						<ul name="relationshipList" class="list-group">
							<table id="relationship">
								<tr><td>Relationship Attribute</td></tr>
								<c:if test="${not empty loadCharacter}">
									<c:forEach items="${loadCharacter.attributeList}" var="item">
										<c:if test="${item.key == 'relationship'}">

											<c:forEach items="${item.value}" var="relationshipList">
												<tr>
													<td>
														<li class='list-group-item'>
															<c:out value="${relationshipList.name.toString()}"/></li>
															<input type='hidden' name='relaAtt' id='relaAtt' value='<c:out value="${relationshipList.name.toString()}"/>'/>
													</td>
													<td>
														<img class='del' src='/storybook/resources/img/ic_highlight_off_black_48dp_2x.png' height='30' width='30' />
													</td>
												</tr>
											</c:forEach>
										</c:if>
									</c:forEach>
								</c:if>

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
								<c:if test="${not empty loadCharacter}">
									<c:forEach items="${loadCharacter.attributeList}" var="item">
										<c:if test="${item.key == 'role'}">
											<c:forEach items="${item.value}" var="roleList">
												<tr>
													<td>
														<li class='list-group-item'>
															<c:out value="${roleList.name.toString()}"/></li>
														<input type='hidden' name='relaAtt' id='roleAtt' value='<c:out value="${roleList.name.toString()}"/>'/>
													</td>
													<td>
														<img class='del' src='/storybook/resources/img/ic_highlight_off_black_48dp_2x.png' height='30' width='30' />
													</td>
												</tr>
											</c:forEach>
										</c:if>
									</c:forEach>
								</c:if>
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
								<c:if test="${not empty loadCharacter}">
									<c:forEach items="${loadCharacter.attributeList}" var="item">
										<c:if test="${item.key == 'scene'}">
											<c:forEach items="${item.value}" var="sceneList">
												<tr>
													<td>
														<li class='list-group-item'>
															<c:out value="${sceneList.name.toString()}"/></li>
														<input type='hidden' name='relaAtt' id='sceneAtt' value='<c:out value="${sceneList.name.toString()}"/>'/>
													</td>
													<td>
														<img class='del' src='/storybook/resources/img/ic_highlight_off_black_48dp_2x.png' height='30' width='30' />
													</td>
												</tr>
											</c:forEach>
										</c:if>
									</c:forEach>
								</c:if>
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
								<c:if test="${not empty loadCharacter}">
									<c:forEach items="${loadCharacter.attributeList}" var="item">
										<c:if test="${item.key == 'attribute'}">
											<c:forEach items="${item.value}" var="attributeList">
												<tr>
													<td>
														<li class='list-group-item'>
															<c:out value="${attributeList.name.toString()}"/></li>
														<input type='hidden' name='relaAtt' id='roleAtt' value='<c:out value="${attributeList.name.toString()}"/>'/>
													</td>
													<td>
														<img class='del' src='/storybook/resources/img/ic_highlight_off_black_48dp_2x.png' height='30' width='30' />
													</td>
												</tr>
											</c:forEach>
										</c:if>
									</c:forEach>
								</c:if>
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

			<div id="thumbnaillist">
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
