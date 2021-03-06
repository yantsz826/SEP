package com.webportal.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class InvoiceDetail_Servlet
 */
@WebServlet("/invoiceDetail")
public class invoiceDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public invoiceDetail() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		RequestDispatcher view = request.getRequestDispatcher("/invoiceDetail.jsp");
        HttpSession session = request.getSession();
        try
        {
            /*
             * Create string of connection url within specified format with
             * machine name, port number and database name. Here machine name id
             * localhost and database name is student.
             */
            String connectionURL = "jdbc:oracle:thin:@//localhost:1521/XE";
            // declare a connection by using Connection interface
            Connection connection = null;
            /*
             * declare object of Statement interface that is used for executing
             * sql statements.
             */
            Statement statement = null;
            // declare a resultset that uses as a table for output data from tha
            // table.
            ResultSet rs = null;
            // Load JBBC driver "com.mysql.jdbc.Driver"
            Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
            /*
             * Create a connection by using getConnection() method that takes
             * parameters of string type connection url, user name and password
             * to connect to database.
             */
            connection = DriverManager.getConnection(connectionURL, "SYSTEM", "password");
            /*
             * createStatement() is used for create statement object that is
             * used for sending sql statements to the specified database.
             */
            statement = connection.createStatement();
            // sql query to retrieve values from the secified table.
            /*String reference = (String)request.getAttribute("attributeName");*/
            Date temp1;
            Date temp2 = new Date();
            request.setAttribute("COLOR", false);
            String reference = session.getAttribute("invoiceReference").toString();
            DateFormat format = new SimpleDateFormat("YYYY-mm-dd", Locale.ENGLISH);
            String QueryString = "SELECT REFERENCE, DOCUMENT_DATE, AMOUNT, OUTSTANDING_AMOUNT,ALLOCATED_AMOUNT,DOCUMENT_TYPE,NARRATION1,NARRATION2,NARRATION3, STATUS, STAGE, APPROVAL_POOL, DUE_DATE, CODE, AREA, CONTACT  from WEBPORTAL, CONTACTS WHERE CODE = APPROVAL_POOL AND REFERENCE = ? ";
            PreparedStatement pstmt = connection.prepareStatement( QueryString );
            pstmt.setString( 1, reference); 
            rs = pstmt.executeQuery();
            rs.next();
            request.setAttribute("REFERENCE", rs.getString(1));
            request.setAttribute("DOCUMENT_DATE", rs.getString(2));	
            request.setAttribute("AMOUNT", rs.getString(3));	
            request.setAttribute("OUTSTANDING_AMOUNT", rs.getString(4));	
            request.setAttribute("ALLOCATED_AMOUNT", rs.getString(5));	
            request.setAttribute("DOCUMENT_TYPE", rs.getString(6));	
            request.setAttribute("NARRATION1", rs.getString(7));	
            request.setAttribute("NARRATION2", rs.getString(8));	
            request.setAttribute("NARRATION3", rs.getString(9));	
            request.setAttribute("STATUS", rs.getString(10));	
            request.setAttribute("STAGE", rs.getString(11));
            request.setAttribute("APPROVAL_POOL", rs.getString(12));
            request.setAttribute("DUE_DATE", rs.getString(13));
            /*compare the due date is it after current date*/
            if(rs.getString(13) != null)
            {
                temp1 = format.parse(rs.getString(13)); 
                if(temp1.compareTo(temp2) < 0)
                {
                    request.setAttribute("COLOR", true);
                }
            }
            request.setAttribute("AREA", rs.getString(15));
            request.setAttribute("CONTACT", rs.getString(16));
            session.setAttribute("authenticated", "true");
            session.setAttribute("username", session.getAttribute("username"));
            // close all the connections.
            rs.close();
            statement.close();
            connection.close();
        } 
        catch (Exception ex)
        {

            System.out.println("Unable to connect to database.");
            System.out.println(ex);
        }
        view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
        @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        HttpSession session = request.getSession();
        session.setAttribute("authenticated", "true");
        session.setAttribute("username", session.getAttribute("username"));
		doGet(request, response);
	}

}

