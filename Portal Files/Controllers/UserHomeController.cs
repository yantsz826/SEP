using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using Finance_Vendor_Portal.Models;

namespace Finance_Vendor_Portal.Controllers
{
    public class UserHomeController : Controller
    {
        private VendorPortalEntities db = new VendorPortalEntities();

        //UserHome
        [HttpGet]
        public ActionResult UserHome()
        {
            string user = Request.Cookies["user"].Value;
            if (user != null)
            {
                return View(db.WEBPORTALs.Where(col => col.SUPPLIER_ACCOUNT == user));
            }
            else
            {
                return View();
            }
        }
        //UserHome
    }
}
