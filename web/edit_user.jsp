<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.sql.*"%>
<%@ page import="java.io.*"%>
<%@page import="com.webportal.*"%>
<%@page import="javax.servlet.http.Cookie" %>
<html>
<head>
<title>Edit User</title>
<meta name="keywords" content="HTML,CSS,XML,JavaScript">
<script type="text/javascript" src="js/jquery-1.12.0.min.js"></script>
<script type="text/javascript" src="DataTables/datatables.min.js"></script>
<script type="text/javascript" src="js/EditUser.js"></script>
<link type="text/css" rel="stylesheet" href="DataTables/datatables.min.css" />

<script type="text/javascript">

var jsArr = new Array(<%=((Integer)request. getAttribute("count"))%>);  
<%  
for (int i=0; i <((Integer)request.getAttribute("count")); i++) {  
%>  
jsArr[<%= i %>] = new Array(5);
jsArr[<%= i %>][0] = '<%=((Object[])request.getAttribute("displayAc"))[i]%>';  
jsArr[<%= i %>][1] = '<%=((Object[])request.getAttribute("displayEmail"))[i]%>'; 
<%
if(((Integer)(((Object[])request.getAttribute("admin"))[i])) == 0)
{
%>
    jsArr[<%= i %>][2] = '<input name ="setAdmin" value="admin" type="checkbox"><button name="submitAdmin" type="submit" value="<%=((Object[])request.getAttribute("account"))[i]%>">submit</button>'; 
<%
}
else
{
%>
    jsArr[<%= i %>][2] = '<input name ="setAdmin" value="admin" type="checkbox" checked><button name="submitAdmin" type="submit" value="<%=((Object[])request.getAttribute("account"))[i]%>">submit</button>';  
<%
}
%>
jsArr[<%= i %>][3] = '<button name="Reset" type="submit" value="<%=((Object[])request.getAttribute("account"))[i]%>">Reset</button>'; 
jsArr[<%= i %>][4] = '<button name="Edit" type="submit" value="<%=((Object[])request.getAttribute("account"))[i]%>">Edit</button><button name="submitBut" type="submit" value="<%=((Object[])request.getAttribute("account"))[i]%>">submit</button><button name="cancel" type="submit" value="True">cancel</button>'; 
jsArr[<%= i %>][5] = '<button name="Delete" type="submit" value="<%=((Object[])request.getAttribute("account"))[i]%>">Delete</button>'; 
<%}%> 
$(document).ready(function() {
    $('#myTable').DataTable( {
        data: jsArr,
        columns: [
            { title: "Supplier Account" },
            { title: "Email" },
            { title: "Admin" },
            { title: "Reset Password" },
            { title: "Edit user" },
            { title: "Delete user" }
        ]
    } );
} );
</script>
</head>
<body>
    <%if(request.getAttribute("resetPWD")!= null) 
      {%>
          <script type="text/javascript">
              alert("<%=request.getAttribute("resetPWD")%>");
          </script>
     <%} %>
	<h2 style="text-align: center;">User table</h2>
	<form action="UserEdit_Servlet" method="post">
    <table id="addUser">
        <TR>
            <TD>Supplier Account: <input type="text" name="addUserID"></TD>
            <TD>Email: <input type="text" name="addEmail"></TD>
            <TD><input type="submit" name="AddUser" value="Add user"></TD>
        <TR>
    </table>
    </form>
	<form action="UserEdit_Servlet" method="post">
	<table id="myTable" class="display" width="100%"></table>
	</form>
	<table>
		<TR>
			<!--<TD><FORM ACTION="welcome_to_database_query.jsp" method="get">
					<button type="submit">&#60;-- back</button>
				</FORM></TD>-->
		</TR>
	</table>
	
</body>
</html>