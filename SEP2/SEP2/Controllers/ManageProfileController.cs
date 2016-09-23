using System;
using MvcApplication1.Models;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace MvcApplication1.Controllers
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
        public ActionResult EditProfileForm(Profile pf) 
        {
            if(ModelState.IsValid)
            {
                using (UserEntities ue = new UserEntities()) 
                {
                    try
                    {
                        var newProfile = ue.Profiles.Find(pf.VendorID);

                        if (TryUpdateModel(newProfile, "", new string[] { "Password", "RePassword", "Email"}))
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
                                ViewBag.Message = "Unable to save this new profile, please contact the administrator ! ";
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

        //Verify pwd in right formate [a-z] [A-Z] [0-9] && 8-12 length && 2 upcase-letter and 1 number at least
        private Boolean VerifyPWD(string tempPwd)
        {
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
                        letterUp++;
                    }
                    else if (char.IsDigit(c))
                    {
                        number++;
                    }
                }
            }

            if (letterUp >= 1 && number >= 2)
            {
                pass = true;
            }

            return pass;
        }
    }
}
