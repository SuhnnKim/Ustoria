<%@ taglib prefix="sping" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
	<title>Home</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/init.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dragula.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/sidebar.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/scene-panel.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/timeline-panel.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/site-general.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/searchbar.css">

</head>

<body style="background-color: white;">

<div class="container-fluid" id="main-wrapper" style="padding:0;">
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
	



 <div class="row" id="header-wrapper">
 	<div class="col-md-4">
  	<button type="button" class="btn btn-primary">Delete</button>
  </div>
  <div class="col-md-4"></div>
  <div class="col-md-4">
  	<button type="button" data-toggle="modal" data-target="#myModal" class="btn btn-primary">Create New Project</button>
  </div>
</div>

<div class="row" id="content-wrapper" style="margin:0;">
<table id="storyList" class="table table-bordered">
 <tr>
 	<th id="check-all"><input type="checkbox"></th>
 	<th>Project Name</th>
 	<th>Description</th>
 	<th>Last Modified</th>
 </tr>

</table>

</div>	
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-2.2.0.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/bootstrap.js"></script>

<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Please enter the Project Name</h4>
      </div>
      <div class="modal-body">
       <form class="form-inline">
  <div class="form-group" >
    <label for="projectName">Name</label>
    <input type="text" id="projectName" class="form-control" id="projectName" placeholder="Project Name">
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
        jQuery("#storyList").append("<tr><td><input type='checkbox'></td>" +
                "<td><a href='summary.form' style='color:black;'>"+jQuery("#projectName").val()+"</a></td>" +
                "<td>Description</td>" +
                "<td>Time</td>" +
                "</tr>");

        jQuery('#myModal').modal('hide');

    });


</script>

</body>
</html>
