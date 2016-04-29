<%-- 
    Document   : UserRegistration.jsp
    Created on : 25/04/2016, 1:50:57 PM
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
        <link rel="stylesheet" href="css/style.css" type="text/css">
        <link rel="stylesheet" href="css/register.css" type="text/css">
        <title>Welcome to Vendor Portal</title>       
    </head>   
    <body>    
        <div class="register_head">
        </div>
        <div class="register_formWrap">
            <div class="register_title">
                <span>
                    <h1>Register Form</h1>
                </span>
            </div>
            
            <!-- <% String message = (String)(request.getAttribute("message")); %> -->
            
            <form id="register_form" action="<%=request.getContextPath()%>/UserRegistServlet" method="post">
                <table class="register_table">
                    <tr>
                        <th class="r_left">
                            <label>Vendor ID: </label>
                        </th>
                        <td class="r_right">
                            <input id="" type="text" name="vendorID" class="txt" />
                        </td>
                    </tr>
                    <tr>
                        <th>
                            <label>Password: </label>
                        </th>
                        <td>
                            <input id="" type="password" name="password" class="txt" />
                        </td>
                    </tr>
                    <tr>
                        <th>
                            <label>Re-Password: </label>
                        </th>
                        <td>
                            <!-- <span style="color:red;font-weight:bold">< % message % ></span> -->
                            <input id="" type="password" name="re_password" class="txt" />
                        </td>
                    </tr>                  
                    <tr>
                        <td colspan="2">                            
                            <span class="half_box left"><input class="btn" type="submit" value="Cancel" onClick=""></span>
                            <span class="half_box right"> <input class="btn" type="submit" value="Submit >>"></span>
                        </td>
                    </tr>
                </table>
            </form>                                       
        </div>
    </body>      
</html>