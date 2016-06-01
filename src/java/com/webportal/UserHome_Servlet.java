/*
Author: Nicholas Lawrence
Student: 17075930
Last edited: 30/05/2016
Purpose: GET & POST for user Log In
 */
package com.webportal;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.Cookie;
import java.util.*;
import java.sql.PreparedStatement;

public class UserHome_Servlet extends HttpServlet
{
    public UserHome_Servlet()
    {
        super();
    }
    /*
    Get Log in page to display to user. Retrieve invoices belonging to vendor 
    from database and display to user.
    */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        RequestDispatcher view = request.getRequestDispatcher("/UserHome.jsp");
        HttpSession session = request.getSession();
        boolean admin = false;
        
        /*
        If session is not authenticated, redirect user to log in page.
        */
        if (session.getAttribute("authenticated").equals("true"))
        {
            try
            {
                String connectionURL = "jdbc:oracle:thin:@//localhost:1521/XE";

                Connection connection = null;
                ResultSet rs = null;
                List<List<String>> rows = new ArrayList<List<String>>();
                ArrayList<String> row;
                String[][] invoiceRows;

                Class.forName("oracle.jdbc.driver.OracleDriver");
                connection = DriverManager.getConnection(connectionURL, "system", 
                        "password");
                //Determine if user has admin permissions
                String queryString = "SELECT ADMINUSER FROM FINANCE_WEB_USERS" +
                        " WHERE VENDORNO = ?";
                PreparedStatement pstmt = connection.prepareStatement(queryString);
                pstmt.setString(1, (String) session.getAttribute("username"));
                rs = pstmt.executeQuery();

                if (rs.next())
                {
                    admin = rs.getString("ADMINUSER").equals("1");
                }
                //Select all invoices belonging to the user's vendor number
                queryString = "SELECT REFERENCE, DOCUMENT_DATE, NARRATION1, STATUS, STAGE FROM WEBPORTAL " + 
                        "WHERE SUPPLIER_ACCOUNT LIKE ?";
                pstmt = connection.prepareStatement(queryString);
                pstmt.setString(1, (String)session.getAttribute("username"));
                rs = pstmt.executeQuery();
                
                /*
                While there are more rows in the result set, add data to a list
                of strings.
                */
                while (rs.next())
                {
                    row = new ArrayList<String>();
                    for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++)
                    {
                        row.add('"' + rs.getString(i) + '"');
                    }
                    rows.add(row);
                }
                //Set invoice list to session attribute
                session.setAttribute("invoiceList", Arrays.deepToString(rows.toArray()));
                rs.close();
                pstmt.close();
                connection.close();
                //If admin, set admin attribute
                if (admin)
                {
                    session.setAttribute("admin", "true");
                    request.setAttribute("admin", "true");
                }
                else
                {
                    session.setAttribute("admin", "false");
                    request.setAttribute("admin", "false");
                }
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
                System.out.println(e.getStackTrace());
                System.out.println(e.getLocalizedMessage());
            }
            view.forward(request, response);
        }
        else
        {
            response.sendRedirect("./LogIn.jsp?LoginFailed");
        }
    }
    /*
    Set selected invoice reference to session parameter and redirect user to
    invoiceDetail page to display in depth detail.
    */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        session.setAttribute("invoiceReference", request.getParameter("invRef"));
        response.sendRedirect("./invoiceDetail");
        
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo()
    {
        return "Short description";
    }// </editor-fold>
}
