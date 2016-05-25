package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class PwdResetOne_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("        <title>Welcome to Vendor Portal</title> \n");
      out.write("        <link rel=\"stylesheet\" href=\"css/style.css\" type=\"text/css\">\n");
      out.write("        <link rel=\"stylesheet\" href=\"css/reset.css\" type=\"text/css\">\n");
      out.write("        <script type=\"text/javascript\" src=\"js/jquery.js\"></script>\n");
      out.write("\n");
      out.write("    </head>   \n");
      out.write("    <body>\n");
      out.write("        <div class=\"reset_head\">\n");
      out.write("        </div>\n");
      out.write("        <div class=\"reset_formWrap\">\n");
      out.write("            <div class=\"reset_title\">\n");
      out.write("                <span>\n");
      out.write("                    <h1>Reset Password</h1>\n");
      out.write("                </span>\n");
      out.write("            </div>      \n");
      out.write("            ");
 String error_message = (String)(request.getAttribute("error_message")); 
      out.write("\n");
      out.write("            ");
 if(error_message == null) {
                error_message = " ";
            } 
      out.write("\n");
      out.write("            <form id=\"reset_form\" action=\"");
      out.print(request.getContextPath());
      out.write("/PwdForgetServlet\" method=\"post\">\n");
      out.write("                <span style = \"color:red; font-size: 13px;\"> ");
      out.print( error_message );
      out.write(" </span>\n");
      out.write("                </br>\n");
      out.write("                </br>\n");
      out.write("                <table class=\"reset_table\">\n");
      out.write("                    <tr>    \n");
      out.write("                        <th class=\"r_left\">\n");
      out.write("                            <label>Vendor ID: </label>\n");
      out.write("                        </th>\n");
      out.write("                        <td class=\"r_right\">\n");
      out.write("                            <input type=\"hidden\" name=\"token\" value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${token}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("\"/>\n");
      out.write("                            <input id=\"input\" type=\"text\" name=\"vendorID\" class=\"txt\" /> \n");
      out.write("                        </td>   \n");
      out.write("                    </tr>\n");
      out.write("                    </br>\n");
      out.write("                    </br>\n");
      out.write("                    </br>\n");
      out.write("                    <tr>    \n");
      out.write("                        <td colspan=\"2\">     \n");
      out.write("                            <span class=\"half_box left\"><input class=\"btn\" type=\"submit\" value=\"Cancel\" onclick=\"\"></span>\n");
      out.write("                            <span class=\"half_box right\"> <input class=\"btn\" type=\"submit\" value=\"Next >>\"</span>\n");
      out.write("                        </td>\n");
      out.write("                    </tr>\n");
      out.write("                </table>\n");
      out.write("            </form>                      \n");
      out.write("        </div>\n");
      out.write("    </body>      \n");
      out.write("</html>\n");
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
