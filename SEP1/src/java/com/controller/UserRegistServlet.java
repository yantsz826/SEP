package com.controller;

import com.register.UserRegister;
import com.util.ConnOracleUtility;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Chen
 */
@WebServlet(name = "UserRegistServlet", urlPatterns = {"/UserRegistServlet"})
public class UserRegistServlet extends HttpServlet {

    private ConnOracleUtility cm = null;
    
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
        String tmp_password = request.getParameter("tmp_password");
        String password = request.getParameter("password");
        String re_password = request.getParameter("re_password");
        String email = request.getParameter("email");
        
        String regist_message = "";
        String success_message = vendorID;
        
        try {
            cm = new ConnOracleUtility();
        } catch (Exception ex) {
            Logger.getLogger(UserRegistServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        UserRegister ug = new UserRegister(cm);
        
        try {
            if(ug.identifyPwd(vendorID, tmp_password, regist_message) == true) {
                if(ug.registUser(vendorID, re_password, email) == true) {
                    request.setAttribute("success_message", success_message);
                    request.getRequestDispatcher("/SuccessPage.jsp").forward(request, response);
                }
                else {
                    regist_message = "Error: Invalid vendorID or Email !";
                    request.setAttribute("regist_message", regist_message);
                    request.getRequestDispatcher("/UserRegistration.jsp").forward(request, response);
                }
            }
            else {
                regist_message = "Error: Expired Password ! Please contact administrator";
                request.setAttribute("regist_message", regist_message);
                request.getRequestDispatcher("/UserRegistration.jsp").forward(request, response);

            }
        } catch (SQLException | ParseException ex) {
            Logger.getLogger(UserRegistServlet.class.getName()).log(Level.SEVERE, null, ex);
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
