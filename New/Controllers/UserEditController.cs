using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Data.SqlClient;
using System.Configuration;
using FinanceVendorPortal.Models;
using System.Data;
using System.Net.Mail;
using System.Text;

namespace FinanceVendorPortal.Controllers
{
    public class UserEditController : Controller
    {
        //
        // GET: /UserEdit/

        public ActionResult UserEdit()
        {
            ViewBag.Message = "User Edit Page";
            /*Reset password*/
            if (Request.Form["Reset"] != null)
            {
                String userID = Request.Form["Reset"];
                PwdUtility pwdReset = new PwdUtility();
                using (VendorPortalDatabaseEntities vpde = new VendorPortalDatabaseEntities())
                {
                    String newPWD = pwdReset.pwdGenerator();
                    String pwd = newPWD;
                    ViewBag.resetPWD = newPWD;
                    newPWD = AESCrypt.encrypt(newPWD);
                    var userToResetPW = vpde.FINANCE_WEB_USERS.Find(userID);
                    userToResetPW.PASSWORD = newPWD;
                    vpde.Entry(userToResetPW).State = EntityState.Modified;
                    vpde.SaveChanges();
                    ModelState.Clear();
                    String a = pwdReset.getRegisteredEmail(userID);
                    pwdReset.sendUserEmail(pwd, a, pwdReset.getOutDate().ToString());
                }

            }

            /*Delete user*/
            if (Request.Form["Delete"] != null)
            {
                String userID = Request.Form["Delete"];
                using (VendorPortalDatabaseEntities vpde = new VendorPortalDatabaseEntities())
                {
                    var userToDelete = vpde.FINANCE_WEB_USERS.Find(userID);
                    vpde.FINANCE_WEB_USERS.Remove(userToDelete);
                    vpde.SaveChanges();
                    ModelState.Clear();
                }
            }
            /*Edit user*/
            if (Request.Form["submitBut"] != null)
            {
                string a = Request.Form["editUserID"];
                if ((Request.Form["editUserID"] != null) && (Request.Form["editEmail"] != null))
                {
                    String userID = Request.Form["submitBut"];
                    String newUserID = Request.Form["editUserID"];
                    String newEmail = Request.Form["editEmail"];

                    using (VendorPortalDatabaseEntities vpde = new VendorPortalDatabaseEntities())
                    {
                        var userToEdit = vpde.FINANCE_WEB_USERS.Find(userID);
                        FINANCE_WEB_USERS newUser = new FINANCE_WEB_USERS(newUserID, userToEdit.PASSWORD, newEmail, userToEdit.ADMINUSER, userToEdit.OUTDATE);
                        vpde.FINANCE_WEB_USERS.Remove(userToEdit);
                        vpde.FINANCE_WEB_USERS.Add(newUser);
                        vpde.SaveChanges();
                        ModelState.Clear();
                    }
                }
            }
            /*add user*/
            if (Request.Form["AddUser"] != null)
            {
                try
                {
                    if ((Request.Form["addUserID"] != null) && (Request.Form["addEmail"] != null))
                    {
                        String newUserID = Request.Form["addUserID"];
                        String newEmail = Request.Form["addEmail"];
                        PwdUtility pwdReset = new PwdUtility();
                        String password = pwdReset.pwdGenerator();
                        String pwd = password;
                        password = AESCrypt.encrypt(password);
                        DateTime outDate = pwdReset.getOutDate();
                        using (VendorPortalDatabaseEntities vpde = new VendorPortalDatabaseEntities())
                        {

                            FINANCE_WEB_USERS newUser = new FINANCE_WEB_USERS(newUserID, password, newEmail, false, outDate);
                            vpde.FINANCE_WEB_USERS.Add(newUser);
                            vpde.SaveChanges();
                            ModelState.Clear();
                        }
                        pwdReset.sendUserEmail(pwd, newEmail, outDate.ToString());

                    }
                }
                catch (Exception ex)
                {
                }
            }

            /*change admin*/
            if (Request.Form["submitAdmin"] != null)
            {
                bool setToOne = false;
                String userID = Request.Form["submitAdmin"];
                using (VendorPortalDatabaseEntities vpde = new VendorPortalDatabaseEntities())
                {
                    var userToUpdate = vpde.FINANCE_WEB_USERS.Find(userID);
                    if (Request.Form["setAdmin" + userID] == null)
                    {
                        if (userToUpdate.ADMINUSER)
                        {
                            setToOne = false;
                        }
                        else
                        {
                            setToOne = true;
                        }
                    }
                    else if (Request.Form["setAdmin" + userID].Equals("admin"))
                    {
                        if (!userToUpdate.ADMINUSER)
                        {
                            setToOne = true;
                        }
                        else
                        {
                            setToOne = false;
                        }
                    }
                    userToUpdate.ADMINUSER = setToOne;
                    vpde.Entry(userToUpdate).State = EntityState.Modified;
                    vpde.SaveChanges();
                    ModelState.Clear();
                }
            }

            /*Display all user*/
            int count = 0;
            List<String> account = new List<String>();
            List<String> email = new List<String>();
            List<bool> admin = new List<bool>();
            List<bool> changeDisplay = new List<bool>();
            using (VendorPortalDatabaseEntities vpde = new VendorPortalDatabaseEntities())
            {
                try
                {
                    foreach (FINANCE_WEB_USERS user in vpde.FINANCE_WEB_USERS.ToList<FINANCE_WEB_USERS>())
                    {
                        if ((Request.Form["edit"] != null) && (user.VENDORNO.Equals(Request.Form["edit"])))
                        {
                            changeDisplay.Add(true);
                        }
                        else
                        {
                            changeDisplay.Add(false);
                        }
                        account.Add(user.VENDORNO);
                        email.Add(user.EMAILADDR);
                        admin.Add((bool)user.ADMINUSER);
                        count++;
                    }
                }
                catch (Exception ex)
                {
                    Console.WriteLine(ex.ToString());
                }
            }
            ViewBag.account = account.ToArray();
            ViewBag.email = email.ToArray();
            ViewBag.admin = admin.ToArray();
            ViewBag.displayChange = changeDisplay.ToArray();
            ViewBag.count = count;



            return View();
        }

    }
}
