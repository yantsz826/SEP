/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.register;

import com.util.ConnOracleUtility;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    
    
    
    public boolean registUser(String vendorID, String pwd, String re_pwd, String email) throws SQLException {
        boolean regist = false;      
        
        if(identifyVendorID(vendorID) == true & identifyPwd(pwd, re_pwd) == true & identifyEmail(email) == true)
        {
            try {
                conn.getConnection();
                stmt = this.conn.getConnection().createStatement();
                String sql = "UPDATE USERTABLE SET PWD='" + re_pwd + "'" + ",EMAIL='" + email + "'" + "where SUPPLER_ACCOUNT='" + vendorID + "'";   // need to change table name and fields
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
    
    
    
    
    public boolean identifyVendorID(String vendorID) throws SQLException {
        boolean pass = false;
  
        try {
            conn.getConnection();
            stmt = this.conn.getConnection().createStatement();
            String sql = "select SUPPLER_ACCOUNT from user where SUPPLER_ACCOUNT='" + vendorID + "'";   //
            rs = stmt.executeQuery(sql);

            while(rs.next()) 
            {
                if(rs.getString("SUPPLER_ACCOUNT") != null)     //
                {
                    pass = true;
                }
            } 
        } 
        catch (SQLException e) {
            throw e;
        }
        finally 
        {
            rs.close();
            stmt.close();
        }
                   
        return pass;
    }
    
    
    
    
    public boolean identifyPwd(String pwd, String re_pwd) {
        boolean pass = false;
        
        if(pwd.equals(re_pwd) == true) 
        {
            pass = true;
        }
        
        return pass;
    }
    
    
    
    
    public boolean identifyEmail(String email) {
        boolean pass = false;
        
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        pass = matcher.matches();
        
        
        return pass;
    }
    
}
