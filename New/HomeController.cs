using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Data.SqlClient;
using System.Configuration;
using WebPortal.Models;
using System.Data;

namespace WebPortal.Controllers
{
    public class HomeController : Controller
    {
        public ActionResult Index()
        {
            ViewBag.Message = "Modify this template to jump-start your ASP.NET MVC application.";

            return View();
        }

        public ActionResult About()
        {
            ViewBag.Message = "Your app description page.";

            return View();
        }

        public ActionResult Contact()
        {
            ViewBag.Message = "Your contact page.";

            return View();
        }

        public ActionResult UserEdit()  
        {
            ViewBag.Message = "User Edit Page";
            String cs = ConfigurationManager.ConnectionStrings["DefaultConnection"].ConnectionString;
            SqlCommand cmd = new SqlCommand();
            SqlDataReader reader;
            using (SqlConnection con = new SqlConnection(cs))
            {
                /*Reset password*/
                if (Request.Form["Reset"] != null)
                {
                    String userID = Request.Form["Reset"];
                }

                /*Delete user*/
                if (Request.Form["Delete"] != null)
                {
                    String userID = Request.Form["Delete"];
                    cmd.CommandText = "DELETE FROM FINANCE_WEB_USERS WHERE VENDORNO= @userID";
                    SqlParameter userIDParam = new SqlParameter("@userID", SqlDbType.VarChar, 30);
                    userIDParam.Value = userID;
                    cmd.Parameters.Add(userIDParam);
                    cmd.CommandType = CommandType.Text;
                    cmd.Connection = con;
                    con.Open();
                    cmd.ExecuteNonQuery();
                    con.Close();
                }
                /*Edit user*/
                if (Request.Form["submitBut"] != null)
                {
                    if ((Request.Form["editUserID"] != null) && (Request.Form["editEmail"] != null))
                    {
                        String userID = Request.Form["submitBut"];
                        String newUserID = Request.Form["editUserID"];
                        String newEmail = Request.Form["editEmail"];
                        cmd.CommandText = "UPDATE FINANCE_WEB_USERS SET VENDORNO= @newUserID, EMAILADDR= @newEmail WHERE VENDORNO= @userID ";
                        SqlParameter newUserIDParam = new SqlParameter("@newUserID", SqlDbType.VarChar, 30);
                        SqlParameter newEmailParam = new SqlParameter("@newEmail", SqlDbType.VarChar, 100);
                        SqlParameter userIDParam = new SqlParameter("@userID", SqlDbType.VarChar, 30);
                        newUserIDParam.Value = newUserID;
                        newEmailParam.Value = newEmail;
                        userIDParam.Value = userID;

                        cmd.Parameters.Add(newUserIDParam);
                        cmd.Parameters.Add(newEmailParam);
                        cmd.Parameters.Add(userIDParam);
                        cmd.CommandType = CommandType.Text;
                        cmd.Connection = con;
                        con.Open();
                        cmd.ExecuteNonQuery();
                        con.Close();
                    }
                }
                /*add user*/
                if (Request.Form["AddUser"] != null)
                {
                    if ((Request.Form["addUserID"] != null) && (Request.Form["addEmail"] != null))
                    {
                        String newUserID = Request.Form["addUserID"];
                        String newEmail = Request.Form["addEmail"];
                        
                    }
                }

                /*change admin*/
                if (Request.Form["submitAdmin"] != null)
                {
                    String userID = Request.Form["submitAdmin"];
                    cmd.CommandText = "SELECT ADMINUSER from FINANCE_WEB_USERS WHERE VENDORNO= @userID";
                    SqlParameter userIDParam = new SqlParameter("@userID", SqlDbType.VarChar, 30);
                    bool setToOne = false;
                    userIDParam.Value = userID;
                    cmd.Parameters.Add(userIDParam);
                    cmd.CommandType = CommandType.Text;
                    cmd.Connection = con;
                    con.Open();
                    using (reader = cmd.ExecuteReader())
                    {
                        if (reader.HasRows)
                        {
                            reader.Read();
                            if (Request.Form["setAdmin"] == null)
                            {
                                if (reader.GetOrdinal("ADMINUSER") == 1)
                                {
                                    setToOne = false;                                
                                }
                                else
                                {
                                    setToOne = true;
                                }
                            }
                            else if (Request.Form["setAdmin"].Equals("admin"))
                            {
                                if (reader.GetOrdinal("ADMINUSER") == 0)
                                {
                                    setToOne = true;
                                }
                                else
                                {
                                    setToOne = false;
                                }
                            }
                        }
                    }
                    con.Close();
                    con.Open();
                    if(setToOne)
                        cmd.CommandText = "UPDATE FINANCE_WEB_USERS SET ADMINUSER = '1' WHERE VENDORNO='" + userID + "'";  
                    else
                        cmd.CommandText = "UPDATE FINANCE_WEB_USERS SET ADMINUSER = '0' WHERE VENDORNO='" + userID + "'";  
                    cmd.CommandType = CommandType.Text;
                    cmd.Connection = con;
                    cmd.ExecuteNonQuery();
                    con.Close();
                }

                /*Display all user*/
                cmd.CommandText = "SELECT VENDORNO, EMAILADDR, ADMINUSER from FINANCE_WEB_USERS";
                cmd.CommandType = CommandType.Text;
                cmd.Connection = con;

                con.Open();

                int count = 0;
                List<String> account = new List<String>();
                List<String> displayAc = new List<String>();
                List<String> email = new List<String>();
                List<String> displayEmail = new List<String>();
                List<int> admin = new List<int>();

                using (reader = cmd.ExecuteReader())
                {
                    if (reader.HasRows)
                    {
                        while (reader.Read())
                        {
                            if ((Request.Form["edit"] != null) && (reader.GetString(0).Equals(Request.Form["edit"])))
                            {
                                displayAc.Add("<input type=\"text\" name=\"editUserID\" value=\"" + reader.GetString(0) + "\">");
                                displayEmail.Add("<input type=\"text\" name=\"editEmail\" value=\"" + reader.GetString(1) + "\">");
                            }
                            else
                            {
                                displayAc.Add(reader.GetString(0));
                                displayEmail.Add(reader.GetString(1));
                            }
                            account.Add(reader.GetString(0));
                            email.Add(reader.GetString(1));
                            admin.Add(reader.GetOrdinal("ADMINUSER"));
                            count++;
                        }
                    }
                    else   
                    {
                        Console.WriteLine("Unable to connect to database.");
                    }
                }
                ViewBag.account = account.ToArray();
                ViewBag.email = email.ToArray();
                ViewBag.displayAc = displayAc.ToArray();
                ViewBag.displayEmail = displayEmail.ToArray();
                ViewBag.admin = admin.ToArray();
                ViewBag.count = count;

                con.Close();
            }


            return View();
        }

        public ActionResult InvoiceDetail()
        {
            String cs = ConfigurationManager.ConnectionStrings["DefaultConnection"].ConnectionString;
            SqlCommand cmd = new SqlCommand();
            SqlDataReader reader;
            DateTime temp; /*for compare current date and DUE_DATE*/
            String docFileName = "10167229"; /*for testing*/
            ViewBag.color = false;
            using (SqlConnection con = new SqlConnection(cs))
            {
                cmd.CommandText = "SELECT DOCUMENT_FILE_NAME, DOCUMENT_DATE,DOCUMENT_TYPE,NARRATION1,NARRATION2,NARRATION3, STATUS, STAGE, APPROVAL_POOL, DUE_DATE, CODE, AREA, CONTACT  from WEBPORTAL, CONTACTS WHERE CODE = APPROVAL_POOL AND DOCUMENT_FILE_NAME = @docFileName ";
                SqlParameter docFileNameParam = new SqlParameter("@docFileName", SqlDbType.VarChar, 50);
                docFileNameParam.Value = docFileName;
                cmd.Parameters.Add(docFileNameParam);
                cmd.CommandType = CommandType.Text;
                cmd.Connection = con;

                con.Open();

                using (reader = cmd.ExecuteReader())
                {
                    if (reader.HasRows)
                    {
                        reader.Read();
                        ViewBag.docFileName = reader.GetString(0);
                        ViewBag.document_Date = reader.GetDateTime(1);
                        ViewBag.docement_Type = reader.GetString(2);
                        ViewBag.narration1 = reader.GetString(3);
                        ViewBag.narration2 = reader.GetString(4);
                        ViewBag.narration3 = reader.GetString(5);
                        ViewBag.status = reader.GetString(6);
                        ViewBag.stage = reader.GetString(7);
                        ViewBag.approvel_Pool = reader.GetString(8);
                        ViewBag.due_Date = reader.GetDateTime(9);
                        temp = reader.GetDateTime(9);
                        if (temp.CompareTo(DateTime.Now) < 0)
                        {
                            ViewBag.color = true;
                        }
                        ViewBag.Area = reader.GetString(11);
                        ViewBag.Contact = reader.GetString(12);

                    }
                    else
                    {
                        Console.WriteLine("Unable to connect to database.");
                    }
                }
                con.Close();
            }
            return View();
        }
    }
}
