package com.webportal.controller;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.webportal.models.PwdReset;
import com.webportal.models.AESCrypt;

/**
 * Servlet implementation class UserEdit_Servlet
 */
@WebServlet("/UserEdit")
public class UserEdit extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserEdit()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// TODO Auto-generated method stub
		/* some sql to get the information */
		RequestDispatcher view = request.getRequestDispatcher("/edit_user.jsp");
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
			//connection = DriverManager.getConnection(connectionURL, "SYSTEM", "A08261993");
                        connection = DriverManager.getConnection(connectionURL, "SYSTEM", "password");
			/*
			 * createStatement() is used for create statement object that is
			 * used for sending sql statements to the specified database.
			 */
			statement = connection.createStatement();
			// sql query to retrieve values from the secified table.
			String QueryString = "SELECT VENDORNO, EMAILADDR, ADMINUSER from FINANCE_WEB_USERS";
			rs = statement.executeQuery(QueryString);
			int count = 0;
			List<String> account = new ArrayList<String>();
			List<String> displayAc = new ArrayList<String>();
			List<String> email = new ArrayList<String>();
			List<String> displayEmail = new ArrayList<String>();
			List<Integer> admin = new ArrayList<Integer>();
			while (rs.next())
			{
				displayAc.add(rs.getString(1));
				account.add(rs.getString(1));
				email.add(rs.getString(2));
				displayEmail.add(rs.getString(2));
				admin.add(Integer.valueOf(rs.getString(3)));
				count++;
			}
			request.setAttribute("editUserID", null);
			request.setAttribute("editEmail", null);
			request.setAttribute("setAdmin", null);
			request.setAttribute("submitAdmin", null);
			request.setAttribute("submitBut", null);
			request.setAttribute("resetPWD", null);
			request.setAttribute("Reset", null);
			request.setAttribute("Delete", null);
			request.setAttribute("AddUser", null);
			request.setAttribute("account", account.toArray());
			request.setAttribute("email", email.toArray());
			request.setAttribute("displayAc", displayAc.toArray());
			request.setAttribute("displayEmail", displayEmail.toArray());
			request.setAttribute("admin", admin.toArray());
			request.setAttribute("count", count);
			// close all the connections.
			rs.close();
			statement.close();
			connection.close();
		} catch (Exception ex)
		{
			
			System.out.println("Unable to connect to database.");
			System.out.println(ex);
		}

		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{	
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
			//connection = DriverManager.getConnection(connectionURL, "SYSTEM", "A08261993");
                        connection = DriverManager.getConnection(connectionURL, "SYSTEM", "password");
			/*
			 * createStatement() is used for create statement object that is
			 * used for sending sql statements to the specified database.
			 */
			statement = connection.createStatement();
			
			/*Reset password¡@*/
			if(request.getParameter("Reset") != null)
			{
				String userID = request.getParameter("Reset");
				PwdReset pwdReset = new PwdReset();
				String newPWD = pwdReset.pwdGenerator();
				request.setAttribute("resetPWD", newPWD);		
				newPWD = AESCrypt.encrypt(newPWD); 
				String updateString = "UPDATE FINANCE_WEB_USERS SET USERPASSWORD='"+newPWD+"'"+" WHERE VENDORNO='" +userID+"'";
				statement.executeQuery(updateString);
				request.setAttribute("Reset", null);				
			}
			
			/*delete user*/
			if(request.getParameter("Delete") != null)
			{
				String userID = request.getParameter("Delete");
				String deleteString = "DELETE FROM FINANCE_WEB_USERS WHERE VENDORNO='" +userID+"'";
				statement.executeQuery(deleteString);
				request.setAttribute("Delete", null);
			}
			/*edit user*/
			if(request.getParameter("submitBut") != null)
			{
				if((request.getParameter("editUserID")!= null)&&(request.getParameter("editEmail")!= null))
				{
					String userID = request.getParameter("submitBut");
					String newUserID = request.getParameter("editUserID");
					String newEmail = request.getParameter("editEmail");
					String updateString = "UPDATE FINANCE_WEB_USERS SET VENDORNO='"+newUserID+"', EMAILADDR='"+newEmail+"' WHERE VENDORNO='" +userID+"'";
					statement.executeQuery(updateString);
				}
				request.setAttribute("submitBut", null);	
			}
			/*add user*/
			
			if(request.getParameter("AddUser") != null)
			{
				if((request.getParameter("addUserID")!= null)&&(request.getParameter("addEmail")!= null))
				{
					String newUserID = request.getParameter("addUserID");
					String newEmail = request.getParameter("addEmail");
					PwdReset pwdReset = new PwdReset();
					String password = pwdReset.pwdGenerator();		
					password = AESCrypt.encrypt(password); /* or should be default? password = "Abcd1234"*/
					String addString = "INSERT INTO FINANCE_WEB_USERS VALUES ('"+newUserID+"','"+password+"','"+newEmail+"',0)";
					statement.executeQuery(addString);
				}
				request.setAttribute("AddUser", null);
				
			}
			/*change admin*/
			if(request.getParameter("submitAdmin") != null)
			{
				String userIDS = request.getParameter("submitAdmin");
				String checkString = "SELECT ADMINUSER FROM FINANCE_WEB_USERS WHERE VENDORNO='" +userIDS+"'";
				rs = statement.executeQuery(checkString);
				rs.next();
				if(request.getParameter("setAdmin") == null)
				{
					if(rs.getString(1).equals("1"))
					{
						String setAdmin = "UPDATE FINANCE_WEB_USERS SET ADMINUSER=0 WHERE VENDORNO='" +userIDS+"'";
						statement.executeQuery(setAdmin);
					}
					else
					{
						String setAdmin = "UPDATE FINANCE_WEB_USERS SET ADMINUSER=1 WHERE VENDORNO='" +userIDS+"'";
						statement.executeQuery(setAdmin);
					}
					request.setAttribute("setAdmin", null);					
				}
				else if(request.getParameter("setAdmin").equals("admin"))
				{
					if(rs.getString(1).equals("0"))
					{
						String setAdmin = "UPDATE FINANCE_WEB_USERS SET ADMINUSER=1 WHERE VENDORNO='" +userIDS+"'";
						statement.executeQuery(setAdmin);
					}
					else
					{
						String setAdmin = "UPDATE FINANCE_WEB_USERS SET ADMINUSER=0 WHERE VENDORNO='" +userIDS+"'";
						statement.executeQuery(setAdmin);
					}
					request.setAttribute("setAdmin", null);
				}
				request.setAttribute("submitAdmin", null);
			}
			// sql query to retrieve values from the secified table.
			String QueryString = "SELECT VENDORNO, EMAILADDR, ADMINUSER from FINANCE_WEB_USERS";
			rs = statement.executeQuery(QueryString);
			int count = 0;
			List<String> account = new ArrayList<String>();
			List<String> displayAc = new ArrayList<String>();
			List<String> email = new ArrayList<String>();
			List<String> displayEmail = new ArrayList<String>();
			List<Integer> admin = new ArrayList<Integer>();
			while (rs.next())
			{
				if((request.getParameter("Edit") != null)&&(rs.getString(1)).equals(request.getParameter("Edit")))
				{
					request.setAttribute("Edit", null);
					request.setAttribute("editUserID", null);
					request.setAttribute("editEmail", null);
					displayAc.add("<input type=\"text\" name=\"editUserID\" value=\""+rs.getString(1)+"\">");
					displayEmail.add("<input type=\"text\" name=\"editEmail\" value=\""+rs.getString(2)+"\">");
				}
				else
				{
					displayAc.add(rs.getString(1));
					displayEmail.add(rs.getString(2));
				}
				account.add(rs.getString(1));
				email.add(rs.getString(2));
				admin.add(Integer.valueOf(rs.getString(3)));
				count++;
			}
			request.setAttribute("account", account.toArray());
			request.setAttribute("email", email.toArray());
			request.setAttribute("displayAc", displayAc.toArray());
			request.setAttribute("displayEmail", displayEmail.toArray());
			request.setAttribute("admin", admin.toArray());
			request.setAttribute("count", count);
			// close all the connections.
			rs.close();
			statement.close();
			connection.close();
		} catch (Exception ex)
		{
			
			System.out.println("Unable to connect to database.");
			System.out.println(ex);
		}
		
		RequestDispatcher view = request.getRequestDispatcher("/edit_user.jsp");
		view.forward(request, response);
	}

}
