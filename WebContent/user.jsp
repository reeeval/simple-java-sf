<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<link type="text/css" href="jquery-ui-1.12.1/jquery-ui.css" rel="stylesheet" />
		<link type="text/css" href="http://getbootstrap.com/dist/css/bootstrap.min.css" rel="stylesheet" />
		<link type="text/css" href="https://getbootstrap.com/docs/3.3/examples/jumbotron-narrow/jumbotron-narrow.css" rel="stylesheet">
		<link type="text/css" href="jquery-datatable/css/jquery.dataTables.css" rel="stylesheet" />
		
		<script type="text/javascript" src="js/jquery_and_datatables_1_12_4.js"></script>
		<script type="text/javascript" src="jquery-ui-1.12.1/jquery-ui.js"></script>
		
		<style type="text/css">
			.jumbotron {
				padding: 0rem 0rem !important;
			}
			
			.container {
				max-width: 80%!important;
			}
			
			.fullWidthTable {
	        	width: 100%!important;
	        }
		</style>
		
		<title>Add New User</title>
	</head>
	<body>
		<script>
	        $(function() {
	            $('input[name=dob]').datepicker({
	            	dateFormat: 'dd/mm/yy',
	                changeMonth: true,
	                changeYear: true,
	                yearRange: '1970:2017'
	            });
	        });
	    </script>
	    
	    <div class="container">
	    	<div class="header clearfix">
	    		<nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
	    			<div class="collapse navbar-collapse" id="navbarCollapse">
	    				<ul class="navbar-nav mr-auto">
							<li class="nav-item active">
								<a class="nav-link" href="http://localhost:8080/SimpleJspServletDB">Home</a>
		                    </li>
						</ul>
						<form class="form-inline mt-2 mt-md-0">
							<input class="form-control mr-sm-2" type="text" placeholder="Search" aria-label="Search">
							<button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
						</form>
	    			</div>
	    		</nav>
	    		<br/><br/><br/>
				<h3 class="text-muted">New Contact</h3>
	    	</div>
	    	
	    	<div style="max-width: 50%!important">
	    		<form method="POST" action='UserController' name="frmAddUser">
			    	User ID : <input class="form-control" type="text" readonly="readonly" name="userid"
			    		value="<c:out value="${user.userId}" />" /> <br />
			    	First Name : <input class="form-control"
			            type="text" name="firstName"
			            value="<c:out value="${user.firstName}" />" /> <br />
		            Last Name : <input class="form-control"
			            type="text" name="lastName"
			            value="<c:out value="${user.lastName}" />" /> <br />
		            DOB : <input class="form-control"
			            type="text" name="dob" placeholder="DD/MM/YYYY"
			            value="<fmt:formatDate pattern="dd/MM/yyyy" value="${user.dob}" />" /> <br />
		            Email : <input class="form-control" type="text" name="email"
		            	value="<c:out value="${user.email}" />" /> <br />
		            	
		            <input class="btn btn-success" type="submit" value="Submit" />
		            <br/><br/>
			    </form>
	    	</div>
	    	
	    	<footer class="footer">
	            <p>&copy; Simple Private Inc. 2017</p>
	        </footer>
	    </div>
	    
	</body>
</html>