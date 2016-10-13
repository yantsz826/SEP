using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using Finance_Vendor_Portal.Models;

namespace Finance_Vendor_Portal.Controllers
{
    public class LogInController : Controller
    {
        private VendorPortalEntities db = new VendorPortalEntities();

        //LogIn
        [HttpGet]
        public ActionResult LogIn()
        {
            if (Request.Cookies["auth"] != null)
            {
                if (Request.Cookies["auth"].Value == "true")
                {
                    return RedirectToAction("UserHome", "UserHome");
                    //return View("../UserHome/UserHome");
                }
                else
                {
                    return View();
                }
            }
            else
            {
                return View();
            }
        }
        //LogIn
        [HttpPost]
        public ActionResult LogIn(FINANCE_WEB_USERS model)
        {
            try
            {
                FINANCE_WEB_USERS user = db.FINANCE_WEB_USERS.Find(model.VENDOR_NUMBER);

                if (user == null)
                {
                    ViewBag.LoginMsg = "The Vendor Number does not exist in the user database. Check the number and try again.";

                    return View();
                }
                else if (user.USER_PASSWORD != model.USER_PASSWORD)
                {
                    ViewBag.LoginMsg = "The password does not match the password on record. Please check the password and try again.";

                    return View();
                }
                else if (user.USER_PASSWORD == model.USER_PASSWORD)
                {
                    HttpContext.Response.Cookies.Add(
                        new HttpCookie("user", user.USER_PASSWORD)
                    );
                    HttpContext.Response.Cookies.Add(
                        new HttpCookie("admin", user.ADMIN_USER.ToString().ToLower())
                    );
                    return RedirectToAction("UserHome", "UserHome" );
                }
                else
                {
                    ViewBag.LoginMsg = "The system failed to log the user in. Please try again later.";

                    return View();
                }
            }
            catch (Exception ex)
            {
                ViewBag.ERROR = "" + ex.Message;

                return View();
            }
        }
        //LogOut
        [HttpGet]
        public ActionResult LogOut()
        {
            var userCookie = Request.Cookies["user"];
            if (userCookie != null)
            {
                userCookie.Expires = DateTime.Now.AddDays(-1);
                Response.Cookies.Add(userCookie);

                return RedirectToAction("LogIn");
            }
            else
            {
                return RedirectToAction("LogIn");
            }
        }
    }
}
