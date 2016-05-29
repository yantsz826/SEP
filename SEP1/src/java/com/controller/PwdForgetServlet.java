package com.controller;

import com.pwd.EmailEntry;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.pwd.PwdForget;
import com.util.ConnOracleUtility;

/**
 *
 * @author Chen
 */
@WebServlet(name = "PwdForgetServlet", urlPatterns = {"/PwdForgetServlet"})
public class PwdForgetServlet extends HttpServlet {
    
    private ConnOracleUtility cm = null;
    private final String host = "smtp.gmail.com";             //gmail host for testing  "smtp.gmail.com"
    private final String port = "587";            //port for testing 
    
    //any mail account -> sender
    private final String username = "";   //sender email
    private final String password = "";   //sender email pwd
    private final String subject = "Temporary Password";
    
    //1->year, 2->month, 3->week, 5->day
    private final int field5 = 5;
    

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            
        String vendorID = request.getParameter("vendorID");
        //error message to pwd reset page
        String errorMessage = "Error: Invalid vendor ID !";

        try {
            cm = new ConnOracleUtility();
        } catch (Exception ex) {
            Logger.getLogger(PwdForgetServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        PwdForget pf = new PwdForget(cm);
        
        try {
            if(pf.identifyUser(vendorID) == true) {
                String dateString;   
                
                dateString = pf.getOutDate(field5, 1);
                //email content
                String pwd = pf.pwdGenerator();
                String message = "Dear user," + "<br><br>" + "A password reset has been requested for the account registered" 
                        + "to this email address on the Curtin Finance Vendor Portal." 
                        + "<br>"
                        + "If you have not requested this, please contact Curtin Finance on (08) 1234 5678."
                        + "<br>"
                        + "If you have requested this, you can now register your account (https://finance.curtin.edu.au/VendorPortal)"
                        + "using the temporary password below. This password will be valid for 12 hours before you will need to request a new one."
                        + "<br>"
                        + "Temporary Password: " + pwd
                        + "<br>"
                        + "Note the temporary password is only avaiable before " + dateString + "." 
                        + "<br><br><br>" + "Best Regards," + "<br><br>" + "Curtin Finance";
                
                //get user email
                String toEmail = pf.getRegisteredEmail(vendorID);
                //email message to pwd reset page two
                String emailMessage = toEmail;
                
                if(pf.saveChangeToDB(dateString, vendorID, pwd, new EmailEntry(host, port, username, password, toEmail, subject, message)) == true) {
                    request.setAttribute("email_message", emailMessage);
                    request.getRequestDispatcher("/PwdResetTwo.jsp").forward(request, response);
                    cm.close();
                }
                else {
                    request.getRequestDispatcher("/ErrorMessagePage.jsp").forward(request, response);
                    cm.close();
                }
                
                cm.close();
            }
            else {
                request.setAttribute("error_message", errorMessage);
                request.getRequestDispatcher("/PwdResetOne.jsp").forward(request, response);
            }
        } catch (SQLException | MessagingException | ServletException | IOException ex) {
            Logger.getLogger(PwdForgetServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(PwdForgetServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
