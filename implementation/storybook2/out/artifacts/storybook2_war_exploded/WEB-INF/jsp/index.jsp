<%@ taglib prefix="sping" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
	<title></title>
	<sping:url value="/resources/css/bootstrap.css" var="bootstrap"/>
	<link rel="stylesheet" href="${bootstrap}">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/init.css">
	<style type="text/css">
		table { border-collapse:separate; border-spacing: 2px; }
		/*.row-gap:before{content: " ";min-height: 20px;}*/
		.plus-gap{margin-bottom: 20px;}
		#brand-logo img{
			height:300px;

		}
		#brand-name{
			color:#337ab7;
			font-size:54px;
		}
		#brand-wrapper{
			float: left;
			position: absolute;
			right: 24%;
			top: 28%;
		}
		<%--.big-logo{--%>
			<%--background-image: url('${pageContext.request.contextPath}/resources/img/main_logo.png');--%>
			<%--height: 300px;--%>
			<%--background-size: contain; --%>
			<%--background-repeat: no-repeat;--%>
		<%--}--%>
	</style>
</head>
<body>
<nav class="navbar navbar-default">
  <div class="container" >
	  <form action="j_spring_security_check" method="post">
		<div class="navbar-header col-lg-5">
			<div class="col-md-6"><img src="${pageContext.request.contextPath}/resources/img/small-logo.png" height="100" alt="Logo"/></div>
			<div id="brand-wrapper" class="col-md-6"><p id="brand-name">Ustoria</p></div>

		</div>
    
		<table class="nav navbar-nav navbar-right col-lg-5" >
			<tr>
				<td><label for="email-login">Email:</label></td>
				<td><label for="password-login">Password:</label></td>
				<td></td>
			</tr>
			<tr>
				<td><input type="email" id="email-login" name="email-login" class="form-control"></td>
				<td><input type="password" id="password-login" name="password-login" class="form-control"></td>
				<td><input type="submit" value="Login" class="btn btn-primary"/></td>
			</tr>
			<%--<tr>--%>
				<%--<td><label><input type="checkbox" name="keep-login" >Keep me logged in</label></td>--%>
				<%--<td><a href="#">Forgot your password?</a></td>--%>
				<%--<td></td>--%>
			<%--</tr>--%>
		</table>
	  </form>
  </div>
</nav>


<div class="container" id="index-container">
	<div class="col-lg-6 big-logo" id="brand-logo">
		<img src="${pageContext.request.contextPath}/resources/img/writing-logo.png" title="logo" />
	</div>
	<div class="col-lg-6">
		<form action="signup.form">
			<h1>Sign Up</h1>
			<div class="row hey">
				<div class="col-lg-6 plus-gap"><input type="text" name="fname" placeholder="First Name" class="form-control"></div>
				<div class="col-lg-6 plus-gap"><input type="text" name="lname" placeholder="Last Name" class="form-control"></div>
				<div class="col-lg-12 plus-gap"><input type="email" name="email-signup" placeholder="Your Email" class="form-control"></div>
				<div class="col-lg-12 plus-gap"><input type="password" name="password-signup" placeholder="Password" class="form-control"></div>
				<div class="col-lg-12 plus-gap"><input type="submit" value="Sign up" class="btn btn-primary"/></div>
			</div>
		</form>
	</div>
</div>

<jsp:include page="footer.jsp" />

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/library/jquery-2.2.0.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/library/bootstrap.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/library/angular.js"></script>
</body>
</html>