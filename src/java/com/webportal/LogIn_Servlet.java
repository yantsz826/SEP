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
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.webportal.models.*;
import java.util.*;
import java.sql.PreparedStatement;



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
    
    /*
    Get Log In page to display to user.
    */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        getServletContext().getRequestDispatcher("/LogIn.jsp").forward(
            request, response);
    }

    /*
    Attempt to log user in with provided credentials.
    */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        //Get user username & password
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        boolean status = false;
        HttpSession session = request.getSession();
        Date date = new Date();
        Date expDate = null;
        
        
        try
        {
            String connectionURL = "jdbc:oracle:thin:@//localhost:1521/XE";
            //Encrypt provided password
            password = AESCrypt.encrypt(password);
            Connection connection = null;
            ResultSet rs = null;
            
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection(connectionURL, "system", 
                    "password");
            //Select from users where vendor ID and password match the provided
            //details
            String queryString = "SELECT * FROM FINANCE_WEB_USERS " +
                    "WHERE VENDORNO = ? AND USERPASSWORD = ?";
            PreparedStatement pstmt = connection.prepareStatement(queryString);
            pstmt.setString(1, userName);
            pstmt.setString(2, password);
            rs = pstmt.executeQuery();
            status = rs.next();
            //If rows are returned, check if there is an expiration date on the 
            //pwd
            if (status)
            {
                expDate = rs.getDate("OUTDATE");
            }
            rs.close();
            pstmt.close();
            connection.close();
            System.out.println(status);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
            System.out.println(e.getLocalizedMessage());
        }
        /*
        If there is an expiry date, results are returned matching the provided
        details and the current date is before the expiry date, set 
        authenticated to true and redirect user to Registration page.
        */
        if (expDate != null && status && date.before(expDate))
        {
            session.setAttribute("authenticated", "true");
            session.setAttribute("username", userName);
            response.sendRedirect("./UserRegistration.jsp");
        }
        /*
        If the temporary password expiry date has already passed set 
        authenticated to false and redirect user to log in page
        */
        else if (expDate != null && date.after(expDate))
        {
            session.setAttribute("authenticated", "false");
            response.sendRedirect("./LogIn.jsp?PasswordExpired");
        }
        /*
        If there is no expiry date on the password but results are returned
        matching the provided credentials set authenticated to true and redirect
        user to home page
        */
        else if (status)
        {
            session.setAttribute("authenticated", "true");
            session.setAttribute("username", userName);
            response.sendRedirect("./UserHome_Servlet");
        }
        /*
        If the provided credentials don't return any data set authenticated to 
        false and redirect the user to the log in page
        */
        else
        {
            session.setAttribute("authenticated", "false");
            response.sendRedirect("./LogIn.jsp?LogInFailed");
        }
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
