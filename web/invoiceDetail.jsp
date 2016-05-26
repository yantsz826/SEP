<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.sql.*"%>
<%@ page import="java.io.*"%>
<%@page import="com.webportal.*"%>
<html>
<head>
<script type="text/javascript" src="/bootstrap-3.3.6-dist/js/bootstrap.min.js"></script>
<link type="text/css" rel="stylesheet"
	href="/bootstrap-3.3.6-dist/css/bootstrap.css" />
<title>Invoice detail</title>
</head>
<body>
	<h2 style="text-align: center;">Invoice details</h2>
	<div class="row" style="background-color:#F8FC95; color:black; padding:10px;">
		<div class="col-xs-6 col-md-4">Reference</div>
		<div class="col-xs-12 col-sm-6 col-md-8"><%=request.getAttribute("REFERENCE")%></div>
	</div>
	<div class="row" style="background-color:#F8FC95; color:black; padding:10px;">
		<div class="col-xs-6 col-md-4">Document Date</div>
		<div class="col-xs-12 col-sm-6 col-md-8"><%=request.getAttribute("DOCUMENT_DATE")%></div>
	</div>
	<div class="row" style="background-color:#F8FC95; color:black; padding:10px;">
		<div class="col-xs-6 col-md-4">Amount</div>
		<div class="col-xs-12 col-sm-6 col-md-8"><%=request.getAttribute("AMOUNT")%></div>
	</div>
	<div class="row" style="background-color:#F8FC95; color:black; padding:10px;">
		<div class="col-xs-6 col-md-4">Outstanding Amount</div>
		<div class="col-xs-12 col-sm-6 col-md-8"><%=request.getAttribute("OUTSTANDING_AMOUNT")%></div>
	</div>
	<div class="row" style="background-color:#F8FC95; color:black; padding:10px;">
		<div class="col-xs-6 col-md-4">Allocated Amount</div>
		<div class="col-xs-12 col-sm-6 col-md-8"><%=request.getAttribute("ALLOCATED_AMOUNT")%></div>
	</div>
	<div class="row" style="background-color:#F8FC95; color:black; padding:10px;">
		<div class="col-xs-6 col-md-4">Document Type</div>
		<div class="col-xs-12 col-sm-6 col-md-8"><%=request.getAttribute("DOCUMENT_TYPE")%></div>
	</div>
	<div class="row" style="background-color:#F8FC95; color:black; padding:10px;">
		<div class="col-xs-6 col-md-4">Narration1</div>
		<div class="col-xs-12 col-sm-6 col-md-8"><%=request.getAttribute("NARRATION1")%></div>
	</div>
	<div class="row" style="background-color:#F8FC95; color:black; padding:10px;">
		<div class="col-xs-6 col-md-4">Narration2</div>
		<div class="col-xs-12 col-sm-6 col-md-8"><%=request.getAttribute("NARRATION2")%></div>
	</div>
	<div class="row" style="background-color:#F8FC95; color:black; padding:10px;">
		<div class="col-xs-6 col-md-4">Narration3</div>
		<div class="col-xs-12 col-sm-6 col-md-8"><%=request.getAttribute("NARRATION3")%></div>
	</div>
	<div class="row" style="background-color:#F8FC95; color:black; padding:10px;">
		<div class="col-xs-6 col-md-4">Status</div>
		<div class="col-xs-12 col-sm-6 col-md-8"><%=request.getAttribute("STATUS")%></div>
	</div>
	<div class="row" style="background-color:#F8FC95; color:black; padding:10px;">
		<div class="col-xs-6 col-md-4">Stage</div>
		<div class="col-xs-12 col-sm-6 col-md-8"><%=request.getAttribute("STAGE")%></div>
	</div>
    <div class="row" style="background-color:#F8FC95; color:black; padding:10px;">
        <div class="col-xs-6 col-md-4">Approval pool</div>
        <div class="col-xs-12 col-sm-6 col-md-8"><%=request.getAttribute("APPROVAL_POOL")%></div>
    </div>
    <%if((Boolean)request.getAttribute("COLOR"))
      {%>
      <div class="row" style="background-color:#F8FC95; color:red; padding:10px;">
    <%}
      else
      {%>
      <div class="row" style="background-color:#F8FC95; color:black; padding:10px;">
      <%
      } %>      
        <div class="col-xs-6 col-md-4">Due date</div>
        <div class="col-xs-12 col-sm-6 col-md-8"><%=request.getAttribute("DUE_DATE")%></div>
    </div>
        <div class="row" style="background-color:#F8FC95; color:black; padding:10px;">
        <div class="col-xs-6 col-md-4">Area</div>
        <div class="col-xs-12 col-sm-6 col-md-8"><%=request.getAttribute("AREA")%></div>
    </div>
        <div class="row" style="background-color:#F8FC95; color:black; padding:10px;">
        <div class="col-xs-6 col-md-4">Contact</div>
        <div class="col-xs-12 col-sm-6 col-md-8"><%=request.getAttribute("CONTACT")%></div>
    </div>
    
    
	<!--<TABLE>
		<TR>
			<TD><FORM ACTION="welcome_to_database_query.jsp" method="get">
					<button type="submit">&#60;-- back</button>
				</FORM></TD>
		</TR>
	</TABLE>-->
</body>
</html>