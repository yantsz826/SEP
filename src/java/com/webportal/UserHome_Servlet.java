/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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

/**
 *
 * @author Nick
 */
public class UserHome_Servlet extends HttpServlet
{

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public UserHome_Servlet()
    {
        super();
    }
    private String getCookie(String cookieName, HttpServletRequest request)
    {
        String val = null;
        Cookie[] cookies = request.getCookies();
        
        for (int i = 0; i < cookies.length; i++)
        {
            cookies[i].getName();
            if (cookies[i].getName() == cookieName)
            {
                val = cookies[i].getValue();
            }
        }
        
        return val;
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        boolean admin = false;
        Cookie[] cookies = request.getCookies();
        
        try
        {
            String connectionURL = "jdbc:oracle:thin:@//localhost:1521/XE";
            
            Connection connection = null;
            Statement statement = null;
            ResultSet rs = null;
            
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection(connectionURL, "system", 
                    "password");
            statement = connection.createStatement();
            String queryString = "SELECT ADMINUSER FROM FINANCE_WEB_USERS " +
                    "WHERE VENDORNO = '" + getCookie("uID", request) + "'";
            rs = statement.executeQuery(queryString);
            
            if (rs.next())
            {
                admin = rs.getString("ADMINUSER").equals("1");
            }
            rs.close();
            statement.close();
            connection.close();
            if (admin)
            {
                request.setAttribute("admin", "true");
            }
            else
            {
                request.setAttribute("admin", "false");
            }
        }
        catch (Exception e)
        {
            System.out.println("Error connecting to database");
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
            System.out.println(e.getLocalizedMessage());
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        //processRequest(request, response);
        RequestDispatcher view = request.getRequestDispatcher("/UserHome.jsp");
        HttpSession session = request.getSession();
        boolean admin = false;
        
        System.out.println("AUTHENTICATED = " + session.getAttribute("authenticated"));
        if (session.getAttribute("authenticated").equals("true"))
        {
            try
            {
                String connectionURL = "jdbc:oracle:thin:@//localhost:1521/XE";

                Connection connection = null;
                Statement statement = null;
                ResultSet rs = null;
                List<List<String>> rows = new ArrayList<List<String>>();
                ArrayList<String> row;
                String[][] invoiceRows;

                Class.forName("oracle.jdbc.driver.OracleDriver");
                connection = DriverManager.getConnection(connectionURL, "system", 
                        "password");
                statement = connection.createStatement();
                String queryString = "SELECT ADMINUSER FROM FINANCE_WEB_USERS " +
                        "WHERE VENDORNO = '" + session.getAttribute("username") + "'";
                rs = statement.executeQuery(queryString);

                if (rs.next())
                {
                    admin = rs.getString("ADMINUSER").equals("1");
                }
                
                queryString = "SELECT REFERENCE, DOCUMENT_DATE, NARRATION1, STATUS, STAGE FROM WEBPORTAL " + 
                        "WHERE SUPPLIER_ACCOUNT LIKE '" + session.getAttribute("username") + "'";
                
                statement = connection.createStatement();
                rs = statement.executeQuery(queryString);
                
                while (rs.next())
                {
                    row = new ArrayList<String>();
                    for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++)
                    {
                        row.add('"' + rs.getString(i) + '"');
                    }
                    rows.add(row);
                }
                
                session.setAttribute("invoiceList", Arrays.deepToString(rows.toArray()));
                rs.close();
                statement.close();
                connection.close();
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
                System.out.println("Error connecting to database");
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

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        
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
