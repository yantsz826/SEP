/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.register.UserRegister;
import com.util.ConnOracleUtility;
import java.io.IOException;
import java.sql.SQLException;
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
        String password = request.getParameter("password");
        String re_password = request.getParameter("re_password");
        String email = request.getParameter("email");
        
        String regist_message = "Error: Invalid vendorID or Email !";
        String success_message = vendorID;
        
        try {
            cm = new ConnOracleUtility();
        } catch (Exception ex) {
            Logger.getLogger(UserRegistServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        UserRegister ug = new UserRegister(cm);
        
        try {
            if(ug.registUser(vendorID, password, re_password, email) == true){
                request.setAttribute("success_message", success_message);
                request.getRequestDispatcher("/SuccessPage.jsp").forward(request, response);
            }
            else {

                request.setAttribute("regist_message", regist_message);
                request.getRequestDispatcher("/UserRegistration.jsp").forward(request, response);
            }
        } catch (SQLException ex) {
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
