/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webportal.register;

import com.webportal.models.AESCrypt;
import com.webportal.util.ConnOracleUtility;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Chen
 */
public class UserRegister {
    private ConnOracleUtility conn = null;    
    private Statement stmt = null;
    private ResultSet rs = null;
    
    private Pattern pattern;
    private Matcher matcher;
    private static final String EMAIL_PATTERN = 
		"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    
    
    
    public UserRegister(ConnOracleUtility conn) {
        this.conn = conn;
    }
    
    
    
    public boolean registUser(String vendorID, String re_pwd, String email) throws SQLException, ParseException, Exception {
        boolean regist = false;      
        
        //identifyPwd to identify vendor_ID & tem-pwd user given for validating the user account
        if(identifyVendorID(vendorID) == true && identifyEmail(email) == true)
        {
            try {
                conn.getConnection();
                stmt = this.conn.getConnection().createStatement();
                re_pwd = AESCrypt.encrypt(re_pwd);
                String sql = "update FINANCE_WEB_USERS set USERPASSWORD='" + re_pwd + "'" + ", EMAILADDR='" + email + "'" + ", OUTDATE=" + "null" + " where VENDORNO='" + vendorID + "'";   // need to change table name and fields
                if(stmt.executeUpdate(sql) > 0) {
                    regist = true;
                }      
            }
            catch (SQLException e) {
                throw e;
            } 
            finally {
                stmt.close();
                try {
                    this.conn.close();
                } catch (Exception ex) {
                    Logger.getLogger(UserRegister.class.getName()).log(Level.SEVERE, null, ex);
                }
            }            
        }
                
        return regist;
    }
    
    
    
    
    private boolean identifyVendorID(String vendorID) throws SQLException {
        boolean pass = false;
  
        try {
            conn.getConnection();
            stmt = this.conn.getConnection().createStatement();
            String sql = "select VENDORNO from FINANCE_WEB_USERS where VENDORNO='" + vendorID + "'";   //
            rs = stmt.executeQuery(sql);

            while(rs.next()) 
            {
                pass = vendorID.equals(rs.getString("VENDORNO"));
            } 
        } 
        catch (SQLException e) {
            throw e;
        }
                   
        return pass;
    }

    
    
    
    private boolean identifyEmail(String email) {
        boolean pass = false;
        
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        pass = matcher.matches();
        
        
        return pass;
    }
    
}
