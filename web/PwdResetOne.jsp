<%-- 
    Document   : PwdResetOne.jsp
    Created on : 10/04/2016, 1:25:54 PM
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
        <script type="text/javascript" src="js/jquery-1.12.0.min.js"></script>

    </head>   
    <body>
        <div class="reset_head">
        </div>
        <div class="reset_formWrap">
            <div class="reset_title">
                <span>
                    <h1>Reset Password</h1>
                </span>
            </div>      
            <% String error_message = (String)(request.getAttribute("error_message")); %>
            <% if(error_message == null) {
                error_message = " ";
            } %>
            <form id="reset_form" action="./PwdForgetServlet" method="post">
                <span style = "color:red; font-size: 13px;"> <%= error_message %> </span>
                </br>
                </br>
                <table class="reset_table">
                    <tr>    
                        <th class="r_left">
                            <label>Vendor ID: </label>
                        </th>
                        <td class="r_right">
                            <input type="hidden" name="token" value="${token}"/>
                            <input id="input" type="text" name="vendorID" class="txt" /> 
                        </td>   
                    </tr>
                    </br>
                    </br>
                    </br>
                    <tr>    
                        <td colspan="2">     
                            <span class="half_box left"><input class="btn" type="submit" value="Cancel" onclick=""></span>
                            <span class="half_box right"> <input class="btn" type="submit" value="Next >>"</span>
                        </td>
                    </tr>
                </table>
            </form>                      
        </div>
    </body>      
</html>
