<!DOCTYPE html>
<html>
<head>
	<title>Summary</title>
	<style type="text/css">
	[ng\:cloak], [ng-cloak], .ng-cloak{
		display: none;
	}

     textarea#txtSummary{
	width:100%;
    
	display:block;
	max-width:100%;
	line-height:1.5;
	padding:2%;
	border-radius:3px;
	border:1px solid #F7E98D;
	font:13px Tahoma, cursive;
	transition:box-shadow 0.5s ease;
	box-shadow:0 4px 6px rgba(0,0,0,0.1);
	font-smoothing:subpixel-antialiased;
	background:linear-gradient(#F9EFAF, #F7E98D);
	background:-o-linear-gradient(#F9EFAF, #F7E98D);
	background:-ms-linear-gradient(#F9EFAF, #F7E98D);
	background:-moz-linear-gradient(#F9EFAF, #F7E98D);
	background:-webkit-linear-gradient(#F9EFAF, #F7E98D);
}
*{
	font: 'Great Vibes', Helvetica, sans-serif;
  color: #fff;
  text-shadow: 4px 4px 3px rgba(0,0,0,0.1); 
}
#main-panel #summary-panel
{
width:77%;
float:left;
background: black;
color:white !important;

}

#main-panel #summary-section-list{
width: 18%;
float: right;
margin:10px;
}

.summary-section-wrapper{
	margin: 1% 0;
}

#summary-section-list button{
margin: 0 1%;
}
.modal-body label, .modal-header{
	color:black !important;
}
#content{
	overflow: auto;
}

[contentEditable=true]:empty:not(:focus):before{
    content:attr(data-placeholder)
}

	</style>

	
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/init.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dragula.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/sidebar.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/scene-panel.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/timeline-panel.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/site-general.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/searchbar.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/character-panel.css">
	<link href='http://fonts.googleapis.com/css?family=Great+Vibes' rel='stylesheet' type='text/css'>

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
	          <li><a href="">My Stories</a></li>
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
				   <li class='active' ><a style="border-bottom: dashed; 2px white;" href='home'>Home</a></li>
				   <li class='has-sub'><a href='#'>Characters</a>
				      <ul>
				         <li><a href='#'>Diana</a></li>
				         <li><a href='#'>Prannoy</a></li>
				         <li><a href='#'>Zhipeng</a></li>
				         <li><a href='#'>Wei</a></li>
				         <li><a href='#'>Wei</a></li>

				      </ul>
				   </li>
				   <li class='has-sub'><a href='#'>Locations</a>
				      <ul>
				         <li><a href='#'>Humber College</a></li>


				         <!-- <li><a href='#'>Location</a></li> -->
				      </ul>
				   </li>
				   <li class='has-sub'><a href='#'>Summary</a>
                    <ul id="summary-list">
                     

                     </ul>
				   </li>
				</ul>
			</div>
			<div><a id="link-add-category" href="#"><span class="glyphicon glyphicon-plus floating-left" id="add-sign"></span><span id="add-category-text">Add Category</span></a></div>
			<div class="link-playground-wrapper"><a id="link-playground" href="#">Playground</a></div>

		</div>
	</div>



	<div class="" id="content" expandable ng-controller="characterPanelController as characterPanel">
		<div id="control-panel">
			<a class="ctrl-btn pull-left" id="new-summary" href="#">Create a new Summary</a>
			<a class="ctrl-btn pull-left" id="undo" href="character.form">Character</a>
			<a class="ctrl-btn pull-left" id="redo" href="playground.form">Playground</a>
		<button class="ctrl-btn pull-right" id="saveText"  data-toggle="modal" data-target="#myModal">Save</button>
		</div>

		<div id="main-panel">
		<div id="summary-panel" style="margin:10px;padding:15px;" contentEditable="true" data-placeholder="Please Enter your summary here"></div>

          <div id="summary-section-list">
		
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
        <button type="button" class="btn btn-primary" data-dismiss="modal" id="btnSummaryNameSave">Save</button>
      </div>
    </div>
  </div>
</div>




<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-2.2.0.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/bootstrap.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-selection.js"></script>

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


jQuery('.text-select').remove();
jQuery('#content').append('<div class=\'text-select\' style=\' visibility: hidden\' ></div>');


var selectedText;
jQuery('#summary-panel').on('selectstart', function () {
        jQuery(document).one('mouseup', function() {
        	selectedText=this.getSelection();
             jQuery('.text-select').text(selectedText);
        	
        	
          });
    });







jQuery('#btnSummaryNameSave').click(function(){
     
	var summaryName = jQuery('#summaryName').val();

    var summaryText = jQuery('.text-select').text();
  jQuery('#summary-list').append('<li id='+summaryName+'><a href=\'#\' >'+summaryName+'</a></li>');


  var cloneSummaryListItem= '<div id='+summaryName+' class=\'summary-section-wrapper\'><button type=\'button\' class=\'btn btn-info\'>'+summaryName+'</button><button type=\'button\' class=\'btn btn-success btn-small\'><span class=\'glyphicon glyphicon-pencil\' aria-hidden=\'true\'></span></button><button type=\'button\' class=\'btn btn-danger btn-small\'><span class=\'glyphicon glyphicon-remove\' aria-hidden=\'true\'></span></button></div>';

        jQuery('#summary-section-list').append(cloneSummaryListItem);

  jQuery('#myModal').modal('hide');

	});



	
</script>

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/angular.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/dragula.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/apptest.js"></script>

</body>
</html>