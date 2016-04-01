<%@ taglib prefix="sping" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>Home</title>
    <jsp:include page="head.jsp"/>
    <style>
        #brand-name {
            color: white;

        }

        #collapseBtn {
            background-color: grey;
            color: white;
        }

        #collapseBtn:hover {
            background-color: #333;
            color: grey;
        }

        #myCtrl ul li {
            margin-right: 10px;
            margin-left: 15px;
        }
    </style>
</head>

<body style="background-color: white;">

<div class="container-fluid" id="main-wrapper" style="padding:0;">
    <jsp:include page="nav_top.jsp"/>


    <div class="row" id="content-wrapper" style="margin:0;">
        <div class="container">
            <nav class="navbar navbar-inverse col-lg-12" style="background-color:white; " id="header-wrapper">
                <div class="navbar-header">
                    <button id="collapseBtn" type="button" class="navbar-toggle" data-toggle="collapse"
                            data-target="#myCtrl"
                            style="color: grey;">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                </div>
                <div class="collapse navbar-collapse" id="myCtrl" style="padding-left: 0;">
                    <ul class="nav navbar-nav navbar-right">
                        <button type="button" class="btn btn-danger">Delete</button>
                        <button type="button" data-toggle="modal" data-target="#myModal" class="btn btn-primary">Create
                            New
                            Project
                        </button>
                    </ul>
                </div>
            </nav>
        </div>
        <div class="container">
            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                <table id="storyList" class="table table-bordered ">
                    <tr>
                        <th id="check-all"></th>
                        <th>Project Name</th>
                        <th>Description</th>
                        <th>Last Modified</th>
                        <th>Actions</th>
                    </tr>
                </table>
            </div>
        </div>
    </div>

    <script type="text/javascript"
            src="${pageContext.request.contextPath}/resources/js/library/jquery-2.2.0.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/library/bootstrap.js"></script>

    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">Project Details</h4>
                </div>
                <div class="modal-body">
                    <form method="post" action="#">
                        <div class="form-group">
                            <label for="projectName">Name</label>
                            <input type="text" class="form-control" required="required" id="projectName"
                                   placeholder="Project Name">
                        </div>

                        <div class="form-group">
                            <label for="description">Description</label>
                            <textarea class="form-control" id="description" rows="5"
                                      placeholder="Description"></textarea>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" id="btnProjectCreate">Create</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>

                </div>
            </div>
        </div>
    </div>


    <script type="text/javascript">
        jQuery("#btnProjectCreate").on("click", function () {

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

                    var finalDate = (d.getDate()) + "-" + (d.getMonth() + 1) + "-" + (d.getFullYear());
                    jQuery("#storyList").append("<tr><td><input type='checkbox'></td>" +
                            "<td><a href='summary'  style='color:black;'>" + jQuery('#projectName').val() + "</a></td>" +
                            "<td>" + jQuery('#description').val() + "</td>" +
                            "<td>" + finalDate + "</td>" +
                            "<td><a href='getXML' class='btn btn-primary' title='Generate XML'> GET XML </a></td>" +
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
