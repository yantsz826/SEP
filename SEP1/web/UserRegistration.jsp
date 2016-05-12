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
        <script type="text/javascript">
            
            function clear() {
                document.getElementById("a").value = "";
                document.getElementById("b").value = "";
                document.getElementById("c").value = "";
                document.getElementById("d").value = "";
            }
            
            function check() {
                var pwd1 = document.getElementById("b");
                var pwd2 = document.getElementById("c");
                if(pwd1.value != pwd2.value) {
                    alert("password wrong");
                    document.getElementById("c").value = "";

                    return false;
                }
            }
            
            
            function pwdStandard() {
                var letter = 0;
                var Bletter = 0;
                var number = 0;
                var count = 0;
                var pwd = document.getElementById("b").value;
                var len = pwd.length;
                for(var i = 0; i < len; i++) {
                    if(pwd[i] >= 48 && pwd[i] <=57)
                        number++;
                        console.log(number);
                    if(pwd[i] >= 65 && pwd[i] <= 90)
                        Bletter++;
                    if(pwd[i] >= 97 && pwd[i] <= 122)
                        letter++;
                    count++; 
                }              
                
                if(Bletter < 1 && count < 8 && number < 2) {
                    alert("password must at least 8 characters long, and at least one capital letters and two numbers");
                    document.getElementById("b").value = "";

                    return false;
                }                     
            }
            
            </script>
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
            <% String regist_message = (String)(request.getAttribute("regist_message")); %>
            <% if(regist_message == null) {
                regist_message = " ";
            } %>
            <form id="register_form" action="<%=request.getContextPath()%>/UserRegistServlet" method="post">
                <span style = "color:red; font-size: 13px;"> <%= regist_message %> </span>
                </br>
                <table class="register_table">
                    <tr>
                        <th class="r_left">
                            <label>Vendor ID: </label>
                        </th>
                        <td class="r_right">
                            <input id="a" type="text" name="vendorID" class="txt" />
                        </td>
                    </tr>
                    <tr>
                        <th>
                            <label>Password: </label>
                        </th>
                        <td>
                            <input id="b" type="password" name="password" class="txt" onchange="pwdStandard()"/>
                        </td>
                    </tr>
                    <tr>
                        <th>
                            <label>Re-password: </label>
                        </th>
                        <td>
                            <input id="c" type="password" name="re_password" class="txt" onchange="check()"/>
                        </td>
                    </tr> 
                    <tr>
                        <th>
                            <label>Email: </label>
                            
                        </th>
                        <td>
                            <input id="d" type="text" name="email" class="txt" />
                        </td>
                    </tr> 
                    </br>
                    </br>
                    <tr>
                        <td colspan="2">                            
                            <span class="half_box left"><input class="btn" type="submit" value="Cancel" onclick="history.back(-1)"></span>
                            <span class="half_box right"> <input class="btn" type="submit" value="Submit >"></span>
                        </td>
                    </tr>
                </table>
            </form>                                       
        </div>
    </body>      
</html>