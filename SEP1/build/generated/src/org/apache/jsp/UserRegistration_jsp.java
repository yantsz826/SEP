package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class UserRegistration_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!--[if lt IE 9]>\n");
      out.write("    <script src=\"http://css3-mediaqueries-js.googlecode.com/svn/trunk/css3-mediaqueries.js\"></script>\n");
      out.write("  <![endif]-->\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\" />\n");
      out.write("        <link rel=\"stylesheet\" href=\"css/style.css\" type=\"text/css\">\n");
      out.write("        <link rel=\"stylesheet\" href=\"css/register.css\" type=\"text/css\">\n");
      out.write("        <script type=\"text/javascript\">\n");
      out.write("            \n");
      out.write("            function clear() {\n");
      out.write("                document.getElementById(\"a\").value = \"\";\n");
      out.write("                document.getElementById(\"b\").value = \"\";\n");
      out.write("                document.getElementById(\"c\").value = \"\";\n");
      out.write("                document.getElementById(\"d\").value = \"\";\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            function check() {\n");
      out.write("                var pwd1 = document.getElementById(\"b\");\n");
      out.write("                var pwd2 = document.getElementById(\"c\");\n");
      out.write("                if(pwd1.value != pwd2.value) {\n");
      out.write("                    alert(\"password wrong\");\n");
      out.write("                    document.getElementById(\"c\").value = \"\";\n");
      out.write("\n");
      out.write("                    return false;\n");
      out.write("                }\n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            \n");
      out.write("            function pwdStandard() {\n");
      out.write("                var letter = 0;\n");
      out.write("                var Bletter = 0;\n");
      out.write("                var number = 0;\n");
      out.write("                var count = 0;\n");
      out.write("                var pwd = document.getElementById(\"b\").value;\n");
      out.write("                var len = pwd.length;\n");
      out.write("                for(var i = 0; i < len; i++) {\n");
      out.write("                    if(pwd[i] >= 48 && pwd[i] <=57)\n");
      out.write("                        number++;\n");
      out.write("                        console.log(number);\n");
      out.write("                    if(pwd[i] >= 65 && pwd[i] <= 90)\n");
      out.write("                        Bletter++;\n");
      out.write("                    if(pwd[i] >= 97 && pwd[i] <= 122)\n");
      out.write("                        letter++;\n");
      out.write("                    count++; \n");
      out.write("                }              \n");
      out.write("                \n");
      out.write("                if(Bletter < 1 && count < 8 && number < 2) {\n");
      out.write("                    alert(\"Note:  Password must at least 8 characters long, and at least one capital letters and two numbers\");\n");
      out.write("                    document.getElementById(\"b\").value = \"\";\n");
      out.write("\n");
      out.write("                    return false;\n");
      out.write("                }                     \n");
      out.write("            }\n");
      out.write("            \n");
      out.write("            </script>\n");
      out.write("        <title>Welcome to Vendor Portal</title>       \n");
      out.write("    </head>\n");
      out.write("    <body>    \n");
      out.write("        <div class=\"register_head\">\n");
      out.write("        </div>\n");
      out.write("        <div class=\"register_formWrap\">\n");
      out.write("            <div class=\"register_title\">\n");
      out.write("                <span>\n");
      out.write("                    <h1>Register Form</h1>\n");
      out.write("                </span>\n");
      out.write("            </div>\n");
      out.write("            ");
 String regist_message = (String)(request.getAttribute("regist_message")); 
      out.write("\n");
      out.write("            ");
 if(regist_message == null) {
                regist_message = " ";
            } 
      out.write("\n");
      out.write("            <form id=\"register_form\" action=\"");
      out.print(request.getContextPath());
      out.write("/UserRegistServlet\" method=\"post\">\n");
      out.write("                <span style = \"color:red; font-size: 13px;\"> ");
      out.print( regist_message );
      out.write(" </span>\n");
      out.write("                </br>\n");
      out.write("                <table class=\"register_table\">\n");
      out.write("                    <tr>\n");
      out.write("                        <th class=\"r_left\">\n");
      out.write("                            <label>Vendor ID: </label>\n");
      out.write("                        </th>\n");
      out.write("                        <td class=\"r_right\">\n");
      out.write("                            <input id=\"a\" type=\"text\" name=\"vendorID\" class=\"txt\" />\n");
      out.write("                        </td>\n");
      out.write("                    </tr>\n");
      out.write("                    <tr>\n");
      out.write("                        <th>\n");
      out.write("                            <label>Password: </label>\n");
      out.write("                        </th>\n");
      out.write("                        <td>\n");
      out.write("                            <input id=\"b\" type=\"password\" name=\"password\" class=\"txt\" onchange=\"pwdStandard()\"/>\n");
      out.write("                        </td>\n");
      out.write("                    </tr>\n");
      out.write("                    <tr>\n");
      out.write("                        <th>\n");
      out.write("                            <label>Re-password: </label>\n");
      out.write("                        </th>\n");
      out.write("                        <td>\n");
      out.write("                            <input id=\"c\" type=\"password\" name=\"re_password\" class=\"txt\" onchange=\"check()\"/>\n");
      out.write("                        </td>\n");
      out.write("                    </tr> \n");
      out.write("                    <tr>\n");
      out.write("                        <th>\n");
      out.write("                            <label>Email: </label>\n");
      out.write("                            \n");
      out.write("                        </th>\n");
      out.write("                        <td>\n");
      out.write("                            <input id=\"d\" type=\"text\" name=\"email\" class=\"txt\" />\n");
      out.write("                        </td>\n");
      out.write("                    </tr> \n");
      out.write("                    </br>\n");
      out.write("                    </br>\n");
      out.write("                    <tr>\n");
      out.write("                        <td colspan=\"2\">                            \n");
      out.write("                            <span class=\"half_box left\"><input class=\"btn\" type=\"submit\" value=\"Cancel\" onclick=\"history.back(-1)\"></span>\n");
      out.write("                            <span class=\"half_box right\"> <input class=\"btn\" type=\"submit\" value=\"Submit >\"></span>\n");
      out.write("                        </td>\n");
      out.write("                    </tr>\n");
      out.write("                </table>\n");
      out.write("            </form>                                       \n");
      out.write("        </div>\n");
      out.write("    </body>      \n");
      out.write("</html>");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
