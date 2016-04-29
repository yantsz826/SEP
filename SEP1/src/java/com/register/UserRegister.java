/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.register;

import com.util.ConnMysqlUtility;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Chen
 */
public class UserRegister {
    private ConnMysqlUtility conn = null;    
    private Statement stmt = null;
    //private PreparedStatement pstmt = null;
    private ResultSet rs = null;
    
    public UserRegister(ConnMysqlUtility conn) {
        this.conn = conn;
    }
    
    public boolean registUser(String vendorID, String pwd, String re_pwd) throws SQLException {
        boolean regist = false;      
        
        if(identifyVendorID(vendorID) == true & identifyPwd(pwd, re_pwd) == true)
        {
            try {
                conn.getConnection();
                stmt = this.conn.getConnection().createStatement();
                String sql = "UPDATE user SET password='" + re_pwd + "'" + "where vendor_id='" + vendorID + "'";

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
            String sql = "select vendor_id from user where vendor_id='" + vendorID + "'";
            rs = stmt.executeQuery(sql);

            while(rs.next()) 
            {
                if(rs.getString("vendor_id") != null) 
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
    
}
