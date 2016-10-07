using SEP2.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace SEP2.Controllers
{
    public class ManageProfileController : Controller
    {
        //
        // GET: /ManageProfile/

        public ActionResult EditProfileForm()
        {
            return View();
        }

        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult EditProfileForm(FINANCE_WEB_USERS pf)
        {
            if (ModelState.IsValid)
            {
                using (SEPEntities ue = new SEPEntities())
                {
                    try
                    {
                        var newProfile = ue.FINANCE_WEB_USERS.Find(pf.VENDORNO);

                        if (TryUpdateModel(newProfile, "", new string[] { "Password", "RePassword", "Email" }))
                        {
                            if(VerifyPWD(pf.PASSWORD))
                            {   
                                try
                                {
                                    ue.SaveChanges();
                                    ModelState.Clear();
                                    pf = null;
                                    ViewBag.Message = "Successfully saved new profiles. ";
                                    ViewData["color"] = "green";
                                }
                                catch (Exception ex)
                                {
                                    ViewBag.Message = "Unable to save this new profile, please contact the administrator! ";
                                    ViewData["color"] = "red";
                                    return View(pf);
                                }
                            }
                            else 
                            {
                                ModelState.Clear();
                                pf = null;
                                ViewBag.Message = "Password does not meet the Curtin pwd standard! ";
                                ViewData["color"] = "red";
                                return View(pf);
                            }

                        }
                    }
                    catch (ArgumentNullException)
                    {
                        ModelState.Clear();
                        pf = null;
                        ViewBag.Message = "Please enter correct vendorID! ";
                        ViewData["color"] = "red";
                        return View(pf);
                    }
                }
            }
            return View(pf);
        }

        //Verify pwd in right formate [a-z] [A-Z] [0-9] && 8-12 length && 1 upcase-letter and 2 number at least
        private Boolean VerifyPWD(string tempPwd)
        {
            Console.Write(tempPwd);
            Boolean pass = false;
            int letterUp = 0;
            int number = 0;
            int min = 8;
            int max = 12;

            if (tempPwd.Length <= max && tempPwd.Length >= min && System.Text.RegularExpressions.Regex.IsMatch(tempPwd, @"([A-Z]|[a-z]|[0-9]|-|_){1,}") == true)
            {
                for (int i = 0; i < tempPwd.Length; i++)
                {
                    char c = tempPwd[i];
                    if (char.IsUpper(c))
                    {
                        Console.Write(letterUp);
                        letterUp++;
                        Console.Write(letterUp);
                    }
                    else if (char.IsDigit(c))
                    {
                        Console.Write(number);
                        number++;
                        Console.Write(letterUp);
                    }
                }
            }
            Console.Write(letterUp);
            Console.Write(number);
            if (letterUp >= 1 && number >= 2)
            {
                pass = true;
            }

            return pass;
        }

    }
}
