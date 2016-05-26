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
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Cookie;
import com.webportal.models.*;



/**
 *
 * @author Nick
 */
public class LogIn_Servlet extends HttpServlet
{
    public LogIn_Servlet()
    {
        super();
    }
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        boolean status = false;
        HttpSession session = request.getSession();
        
        try
        {
            String connectionURL = "jdbc:oracle:thin:@//localhost:1521/XE";
            
            password = AESCrypt.encrypt(password);
            Connection connection = null;
            Statement statement = null;
            ResultSet rs = null;
            
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection(connectionURL, "system", 
                    "password");
            statement = connection.createStatement();
            String queryString = "SELECT * FROM FINANCE_WEB_USERS " +
                    "WHERE VENDORNO = '" + userName + "' AND USERPASSWORD = '" +
                    password + "'";
            rs = statement.executeQuery(queryString);
            status = rs.next();
            rs.close();
            statement.close();
            connection.close();
        }
        catch (Exception e)
        {
            System.out.println("Error connecting to database");
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
            System.out.println(e.getLocalizedMessage());
        }
        if (status)
        {
            session.setAttribute("authenticated", "true");
            session.setAttribute("username", userName);
            /*for (int i = 0; i < cookies.length; i++)
            {
                if (cookies[i].getName().equalsIgnoreCase("login"))
                {
                    cookies[i].setValue("true");
                    Cookie uID = new Cookie("uid", userName);
                    Cookie pwd = new Cookie("pwd", password);
                    response.addCookie(cookies[i]);
                    response.addCookie(uID);
                    response.addCookie(pwd);
                    break;
                }
            }*/
            getServletContext().getRequestDispatcher("/UserHome_Servlet").forward(
            request, response);
        }
        else
        {
            /*for (int i = 0; i < cookies.length; i++)
            {
                if (cookies[i].getName().equalsIgnoreCase("login"))
                {
                    cookies[i].setValue("false");
                    response.addCookie(cookies[i]);
                    response.sendRedirect("./LogIn.jsp");
                    break;
                }
            }*/
            session.setAttribute("authenticated", "false");
            response.sendRedirect("./LogIn.jsp?LogInFailed");
            //request.getRequestDispatcher("/LogIn.jsp").forward(request, 
                    //response);
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
        processRequest(request, response);
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
        processRequest(request, response);
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
