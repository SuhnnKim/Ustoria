<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<title>Summary</title>
	<style type="text/css">
		[ng\:cloak], [ng-cloak], .ng-cloak{
			display: none;
		}

		.highlight{
			color:yellow;
		}
		#summary-panel::selection {
			color: #e14b3b; /* WebKit/Blink Browsers */

			font-size: 20px;

		}
		#summary-panel::-moz-selection {
			color: #e14b3b; /* Gecko Browsers */

			font-size: 20px;

		}

		#main-panel #summary-panel
		{
			width:80%;
			float:left;

		}

		#main-panel #summary-section-list{
			width: 20%;
			float: right;

		}

		.summary-section-wrapper{
			margin: 1% 0;
		}

		#summary-section-list button{
			margin: 0 1%;
		}
		#content{
			overflow: auto !important;
		}

		[contentEditable=true]:empty:not(:focus):before{
			content:attr(data-placeholder)
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


	jQuery('.text-select').remove();
	jQuery('#content').append('<div class=\'text-select\' style=\' visibility: hidden\' ></div>');


	var selectedText;
	jQuery('#summary-panel').on('selectstart', function () {
		jQuery(document).one('mouseup', function() {

			selectedText=this.getSelection();

			jQuery('.text-select').text(selectedText);


		});
	});

	// Summary Section

	jQuery('#btnSummaryNameSave').click(function(){


		var summaryName = jQuery('#summaryName').val();

		var summaryText = jQuery('.text-select').html();




		jQuery.ajax({
			method : "POST",
			url : 'AddSummarySection',
			data : {
				summaryName : summaryName,
				summaryContent: summaryText
			},
			success : function(responseText) {

				jQuery('#summary-list').empty();
				jQuery('#summary-section-list').empty();
				jQuery.each(JSON.parse(responseText), function(idx, obj) {
					jQuery('#summary-list').append('<li id='+obj.name+'><a href=\'#\' >'+obj.name+'</a></li>');

					var cloneSummaryListItem= '<div id='+obj.name+' class=\'summary-section-wrapper\'><button type=\'button\' class=\'btn btn-info summary-section-button\'>'+obj.name+'</button><button type=\'button\' class=\'btn btn-success btn-small\'><span class=\'glyphicon glyphicon-pencil\' aria-hidden=\'true\'></span></button><button type=\'button\' class=\'btn btn-danger btn-small\'><span class=\'glyphicon glyphicon-remove\' aria-hidden=\'true\'></span></button></div>';

					jQuery('#summary-section-list').append(cloneSummaryListItem);


				});



				jQuery('#myModal').modal('hide');

			}
		});
	});


	// New Summary

	jQuery('#save-story').on('click',function(){


		var summaryText = jQuery('#summary-panel').html();

		jQuery.ajax({
			method : "POST",
			url : 'SaveSummary',
			data : {
				summaryData : summaryText
			},
			success : function(responseText) {
				jQuery('#summary-panel').html(" ");

				jQuery('#summary-panel').html(responseText);
			}
		});
	});

	jQuery('.summary-section-button').on('click',function(){


		jQuery('#summary-panel').removeHighlight();
		var summaryText = jQuery(this).text();

		jQuery.ajax({
			method : "POST",
			url : 'getSummarySectionText',
			data : {
				summaryData : summaryText
			},
			success : function(responseText) {
              // var htmlText = jQuery.parseHTML(responseText);
                htmlText = jQuery.parseHTML(responseText);
                //alert(typeof htmlText);
                var finalText="";
                jQuery.each( htmlText, function( k,v ) {
                    alert("Key "+k);
                    alert("Value "+ v.nodeValue);
                    //finalText+=v.nodeName;
                });
               // alert(finalText);
                //jQuery('#summary-panel').highlight(finalText);


				//jQuery('#summary-panel').html(responseText);
			}
		});
	});

    function convertToHtml(plainText){
        return jQuery(this).html(plainText)
    }


	jQuery('div[contenteditable]').keydown(function(e) {
		// trap the return key being pressed
		if (e.keyCode === 13) {
			// insert 2 br tags (if only one br tag is inserted the cursor won't go to the next line)
			document.execCommand('insertHTML', false, '<br><br>');
			// prevent the default behaviour of return key pressed
			return false;
		}
	});

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
