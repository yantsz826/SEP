<%-- 
    Document   : PwdResetTwo.jsp
    Created on : 22/04/2016, 12:26:46 PM
    Author     : Chen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!--[if lt IE 9]>
    <script src="http://css3-mediaqueries-js.googlecode.com/svn/trunk/css3-mediaqueries.js"></script>
  <![endif]-->
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <title>Welcome to Vendor Portal</title> 
        <link rel="stylesheet" href="css/style.css" type="text/css">
        <link rel="stylesheet" href="css/reset.css" type="text/css">
        <script type="text/javascript" src="js/jquery-1.12.0.js"></script>
    </head>   
    <body>
        <div class="reset_head">
        </div>
        <div class="reset_formWrap">
            <div class="reset_title">
                </br>
                </br>         
                </br>         
                <% String email_message = (String)(request.getAttribute("email_message")); %>
                <span style="font-size: 140%">
                    The new password has been successfully sent to user registered email </br></br>->&nbsp;<%= email_message %></br>
                </span>                       
        </div>
    </body>      
</html>



