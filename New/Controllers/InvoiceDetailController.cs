using FinanceVendorPortal.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace FinanceVendorPortal.Controllers
{
    public class InvoiceDetailController : Controller
    {
        //
        // GET: /InvoiceDetail/

        public ActionResult InvoiceDetail()
        {
            DateTime temp; /*for compare current date and DUE_DATE*/
            String docFileName = "10167229"; /*for testing*/
            ViewBag.color = false;
            using (VendorPortalDatabaseEntities vpde = new VendorPortalDatabaseEntities())
            {
                bool found = false;

                foreach (WEBPORTAL w in vpde.WEBPORTALs.ToList<WEBPORTAL>())
                {
                    foreach (CONTACT c in vpde.CONTACTS.ToList<CONTACT>())
                    {
                        if (!found)
                        {
                            if (c.Code.Equals(w.APPROVAL_POOL) && w.DOCUMENT_FILE_NAME.Equals(docFileName))
                            {
                                ViewBag.docFileName = w.DOCUMENT_FILE_NAME;
                                ViewBag.document_Date = w.DOCUMENT_DATE;
                                ViewBag.docement_Type = w.DOCUMENT_TYPE;
                                ViewBag.narration1 = w.NARRATION1;
                                ViewBag.narration2 = w.NARRATION2;
                                ViewBag.narration3 = w.NARRATION3;
                                ViewBag.status = w.STATUS;
                                ViewBag.stage = w.STAGE;
                                ViewBag.approvel_Pool = w.APPROVAL_POOL;
                                temp = (DateTime)w.DUE_DATE;
                                ViewBag.due_Date = temp;
                                if (temp.CompareTo(DateTime.Now) < 0)
                                {
                                    ViewBag.color = true;
                                }
                                ViewBag.Area = c.Area;
                                ViewBag.Contact = c.Contact1;
                                found = true;
                            }
                        }
                    }
                }
            }
            return View();
        }

    }
}
