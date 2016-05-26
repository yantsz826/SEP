/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webportal.controller;

import com.webportal.pwd.EmailEntry;
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
import com.webportal.pwd.PwdForget;
import com.webportal.util.ConnOracleUtility;

/**
 *
 * @author Chen
 */
@WebServlet(name = "PwdForgetServlet", urlPatterns = {"/PwdForgetServlet"})
public class PwdForgetServlet extends HttpServlet {
    
    private ConnOracleUtility cm = null;
    private final String host = "smtp.gmail.com";             //gmail host for testing  "smtp.gmail.com"
    private final String port = "465";            //port for testing   "465", for TLS/STARTTLS 587
    
    //any mail account -> sender
    private final String username = "";   //sender email
    private final String password = "";   //sender email pwd
    private final String subject = "Temporary Password";
    
    //1->year, 2->month, 3->week, 5->day
    private final int field1 = 1;
    private final int field2 = 2;
    private final int field3 = 3;
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
        
        /**
         *  judge if it submitted before, so we need have token at previous page
         *  create token, and set token like request.getSession().setAttribute("token", token);
         *  String token = TokenProccessor.getInstance().makeToken();
         *  
         */
        boolean bo = isRepeatSubmit(request);
        if(bo != true) {
            request.getSession().removeAttribute("token");
        }      

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
                //get outdate； field3 -> week
                dateString = pf.getOutDate(field5, 1);
                //email content
                String pwd = pf.pwdGenerator();
                String message = "This is your temporary passoword ' " + pwd
                        + " ' for your account ' " + vendorID + " '.<br>" + "Please"
                        + " note the temporary password is only avaiable before " + dateString + "." +
                        "<br><br><br>" + "Best Regards," + "<br><br>" + "Curtin University";
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


    
    private boolean isRepeatSubmit(HttpServletRequest request) {
        String client_token = request.getParameter("token");

        if(client_token==null){
            return true;
        }

        String server_token = (String) request.getSession().getAttribute("token");

        if(server_token==null){
            return true;
        }

        if(!client_token.equals(server_token)){
            return true;
        }

        return false;
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
