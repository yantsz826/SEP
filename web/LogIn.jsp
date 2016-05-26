<%-- 
    Document   : LogIn
    Created on : 10/05/2016, 6:10:00 PM
    Author     : Nick
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.webportal.*"%>
        
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link type="text/css" rel="stylesheet" href="css/LogIn.css">
        <script type="text/javascript" src="js/jquery-1.12.0.min.js"></script>
        <script type="text/javascript" src="js/jquery.cookie.js"></script>
        <script type="text/javascript" src="js/LogIn.js"></script>
    </head>
    <body>
        <div id="loginpage">
            <p id="vendorTitle">Vendor Portal</p>
            <p id="vendorDesc">Your gateway to staying up to date on all of your invoices</p>
            <hr />
            <div id="logincontainer">
                <p id="inputTitle">Vendor Login</p>
                <form name="LogInForm" action="LogIn_Servlet">
                    
                    <div class="inputdiv">
                        <span id="vidlbl" class="rTxt">Vendor ID:</span>
                        <input id="vidTxt" name="userName" class="rTxt" />
                    </div>
                    <div class="inputdiv">
                        <span id="pwdlbl" class="rTxt">Password:</span>
                        <input id="pwdTxt" name="password" class="rTxt" autoComplete="off" />
                    </div>
                    <button id="loginbtn" class="btnTxt" type="submit">Log In</button>
                </form>
                <button id="forgotpwdbtn" class="btnTxt" type="button">Forgot Your Password?</button>
            </div>
        </div>
    </body>
</html>
