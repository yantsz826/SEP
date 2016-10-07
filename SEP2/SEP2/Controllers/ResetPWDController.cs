using SEP2.DefinedException;
using SEP2.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace SEP2.Controllers
{
    public class ResetPWDController : Controller
    {
        private PwdUtility rpd;
        //
        // GET: /ResetPWD/

        public ResetPWDController() 
        {
            rpd = new PwdUtility();
        }

        public ActionResult ForgotPWD() 
        {
            return View();
        }

        [HttpPost]
        public ActionResult ResetAndEmail(string vendorID)
        {
            string id = vendorID;

            try
            {
                if (rpd.identifyUser(id) == false)
                {
                    ViewBag.Message = "Please enter correct vendorID! ";
                    ViewData["color"] = "red";
                    return View("ForgotPWD");
                }
                string pwd = rpd.pwdGenerator();
                string email = rpd.getRegisteredEmail(id);
                if (rpd.sendUserEmail(pwd, email, rpd.getOutDate().ToString()) == false)
                {
                    ViewBag.Message = "Please enter correct vendorID! ";
                    ViewData["color"] = "red";
                    return View("ForgotPWD");
                }
                rpd.saveChangeToDB(rpd.getOutDate(), id, pwd);
                ViewBag.Message = "Success. ";
                ViewData["color"] = "green";
            }
            catch(EmailException ex)
            {
                Console.Write("email wrong");
            }
            catch(UserInfoException ei)
            {
                Console.Write("user info wrong");
            }

            return View();
        }

    }
}
