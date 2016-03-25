<%@ taglib prefix="sping" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
	<title>Home</title>
    <jsp:include page="head.jsp" />
    <style>
        #brand-name{
            color:white;

        }
        #collapseBtn{
            background-color: grey;
            color: white;
        }
        #collapseBtn:hover{
            background-color: #333;
            color: grey;
        }
        #myCtrl ul li{
            margin-right: 10px;
            margin-left: 15px;
        }
    </style>
</head>

<body style="background-color: white;">

<div class="container-fluid" id="main-wrapper" style="padding:0;">
    <jsp:include page="nav_top.jsp" />
  <%--<nav class="navbar navbar-default">--%>
	  <%--<div class="container-fluid">--%>
	    <%--<div class="navbar-header">--%>
            <%--<h2 id="brand-name">Ustoria</h2>--%>
            <%--&lt;%&ndash;<img src="${pageContext.request.contextPath}/resources/img/logo_2.png" alt="Home"/>&ndash;%&gt;--%>
	    <%--</div>--%>
		<%--<!-- <a href="#">My Projects</a> -->--%>
	    <%--<ul class="nav navbar-nav navbar-right">--%>
	      <%----%>
	      <%--<li class="dropdown">--%>
	        <%--<a class="dropdown-toggle" data-toggle="dropdown" href="#">${email}--%>
	        <%--<span class="caret"></span></a>--%>
	        <%--<ul class="dropdown-menu">--%>
	          <%--<li><a href="#">My Stories</a></li>--%>
	          <%--<li><a href="#">Profile</a></li>--%>
	          <%--<li><a href="#">Log Out</a></li> --%>
	        <%--</ul>--%>
	      <%--</li>--%>

	    <%--</ul>--%>
	    <%--<form><input class="navbar-right" id="searchbar" type="search" name="search" placeholder="search" ></form>--%>
	  <%--</div>--%>
	<%--</nav>--%>
	



    <%--<div class="row" id="header-wrapper">--%>
        <%--<div class="container" style="padding: 0;">--%>
            <%--<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myCtrl"--%>
                    <%--style="color:white !important;opacity: 1;background-color: grey;">--%>
                <%--<span class="icon-bar"></span>--%>
                <%--<span class="icon-bar"></span>--%>
                <%--<span class="icon-bar"></span>--%>
            <%--</button>--%>
            <%--<div class="collapse navbar-collapse" id="myCtrl">--%>
                <%--<button type="button" class="btn btn-primary">Delete</button>--%>
                <%--<button type="button" class="btn btn-primary">Generate XML</button>--%>
                <%--<button type="button" class="btn btn-primary">Download XML</button>--%>
            <%--</div>--%>
            <%--<div class=" col-lg-6 col-md-4 col-sm-4 col-xs-12 ">--%>
                <%--<button type="button" data-toggle="modal" data-target="#myModal" class="btn btn-primary pull-right">Create New Project</button>--%>
            <%--</div>--%>
        <%--</div>--%>
    <%--</div>--%>
    <nav class="navbar navbar-inverse col-lg-12" style="background-color:white; " id="header-wrapper">

            <div class="navbar-header">
                <button id="collapseBtn" type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myCtrl" style="color: grey;">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
            </div>
            <div class="collapse navbar-collapse" id="myCtrl" style="padding-left: 0;">
                <ul class="nav navbar-nav">
                    <li><button type="button" class="btn btn-primary">Delete</button></li>
                    <li><button type="button" class="btn btn-primary">Generate XML</button></li>
                    <li><button type="button" class="btn btn-primary">Download XML</button></li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <button type="button" data-toggle="modal" data-target="#myModal" class="btn btn-primary">Create New Project</button>
                </ul>
            </div>

    </nav>
    <div class="contsiner">
    <div class="row col-lg-12" id="content-wrapper" style="margin:0;">

        <table id="storyList" class="table table-bordered col-lg-12 col-md-12 col-sm-12 col-xs-12">
            <tr>
                <th id="check-all"></th>
                <th>Project Name</th>
                <th>Description</th>
                <th>Last Modified</th>
            </tr>
        </table>

    </div>
    </div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/library/jquery-2.2.0.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/library/bootstrap.js"></script>

<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">Please enter the Project Name</h4>
            </div>
            <div class="modal-body">
                <form method="post" action="#">
                    <div class="form-group" >
                        <label for="projectName">Name</label>
                        <input type="text" class="form-control" required="required" id="projectName" placeholder="Project Name">
                    </div>

                    <div class="form-group" >
                        <label for="description">Description</label>
                        <textarea class="form-control" id="description" rows="5" placeholder="Description"></textarea>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" id="btnProjectCreate">Create</button>
            </div>
        </div>
    </div>
</div>


<script type="text/javascript">
    jQuery("#btnProjectCreate").on("click", function() {

//        var d = new Date();
//
//        var finalDate = (d.getDate())+"-"+(d.getMonth() + 1)+"-"+(d.getFullYear());
//        var projectName = jQuery("#projectName").val();
//        var title="/Ustoria/summary/"+projectName;
//        jQuery("#storyList").append("<tr><td><input type='checkbox'></td>" +
//                "<td><a href='#' id='projectLink' style='color:black;'>"+projectName+"</a></td>" +
//                "<td>"+jQuery("#description").val()+"</td>" +
//                "<td>"+finalDate+"</td>" +
//                "</tr>");




                jQuery.ajax({
                    type: 'POST',
                    url: 'CreateStory',
                    data: {
                        projectName: jQuery('#projectName').val(),
                        projectDesc: jQuery('#description').val()
                    },
                    success: function (responseText) {
                        var d = new Date();

                        var finalDate = (d.getDate())+"-"+(d.getMonth() + 1)+"-"+(d.getFullYear());
                        jQuery("#storyList").append("<tr><td><input type='checkbox'></td>" +
                "<td><a href='summary'  style='color:black;'>"+jQuery('#projectName').val()+"</a></td>" +
                "<td>"+jQuery('#description').val()+"</td>" +
                "<td>"+finalDate+"</td>" +
                "</tr>");
                        jQuery('#myModal').modal('hide');


                        //$('#ajaxGetUserServletResponse').text(responseText);
                    }
                });


       // jQuery('#myModal').modal('hide');

    });


</script>

</body>
</html>
