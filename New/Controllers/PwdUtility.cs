using System;
using System.Collections.Generic;
using System.Data;
using System.Net.Mail;
using System.Linq;
using System.Web;
using FinanceVendorPortal.Models;
using System.Text;
using System.Web.Mvc;
using FinanceVendorPortal.DefinedException;

namespace FinanceVendorPortal.Controllers
{
    public class PwdUtility
    {
        //pwd length
        private const int min = 8;
        private const int max = 12;

        public Boolean sendUserEmail(string password, string email, string outdate)
        {
            Boolean pass = false;

            try
            {
                System.Net.Mail.MailMessage Msg = new System.Net.Mail.MailMessage();
                Msg.Subject = "Password Recovery";
                Msg.From = new MailAddress("curtinsep@gmail.com", "support");
                Msg.To.Add(new MailAddress(email, "client"));
                Msg.Body = "Dear user," + "<br><br>" + "A password reset has been requested for the account registered"
                        + "to this email address on the Curtin Finance Vendor Portal."
                        + "<br>"
                        + "If you have not requested this, please contact Curtin Finance on (08) 1234 5678."
                        + "<br>"
                        + "If you have requested this, you can now register your account (https://finance.curtin.edu.au/VendorPortal) "
                        + "using the temporary password below. This password will be valid for 12 hours before you will need to request a new one."
                        + "<br>"
                        + "Temporary Password: " + password
                        + "<br>"
                        + "Note the temporary password is only avaiable before " + outdate + "."
                        + "<br><br><br>" + "Best Regards," + "<br><br>" + "Curtin Finance";
                Msg.IsBodyHtml = true;
                SmtpClient smtp = new SmtpClient();
                smtp.Host = "smtp.gmail.com";
                smtp.Port = 587;
                smtp.EnableSsl = true;
                smtp.Credentials = new System.Net.NetworkCredential("curtinsep@gmail.com", "ICantRemember99Password");
                smtp.Send(Msg);
                pass = true;
            }
            catch (Exception ex)
            {
                throw new EmailException("Failed to send email! ");
            }

            return pass;
        }

        public string getRegisteredEmail(string vendorID)
        {
            string email = "";
            using (VendorPortalDatabaseEntities ue = new VendorPortalDatabaseEntities())
            {
                var Profile = ue.FINANCE_WEB_USERS.Find(vendorID);
                if (Profile != null)
                {
                    email = Profile.EMAILADDR;
                }
            }
            return email;
        }

        public DateTime getOutDate()
        {
            DateTime outDate = DateTime.Now;
            return outDate.Add(new System.TimeSpan(1, 0, 0, 0)); //day, hour, min, sec
        }

        public string pwdGenerator()
        {
            string pwd = null;
            Random rd = new Random();
            //8 - 12 random number
            int pwdLength = rd.Next(min, max);
            while (pwd == null)
            {
                int letterUp = 0;
                int number = 0;
                string temp = "";
                temp = getRandomCode(pwdLength);
                for (int i = 0; i < temp.Length; i++)
                {
                    char c = temp[i];
                    if (Char.IsUpper(c))
                        letterUp++;
                    else if (Char.IsDigit(c))
                        number++;
                }
                if (letterUp >= 1 && number >= 2)
                {
                    pwd = temp;
                }
            }

            return pwd;
        }

        private string getRandomCode(int length)
        {
            String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
            Random random = new Random();
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < length; i++)
            {
                //nextInt(10) 0-9
                int number = random.Next(0, 62);
                sb.Append(str[number]);
            }

            return sb.ToString();
        }
    }
}