/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webportal.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Chen
 */
public class ConnOracleUtility {
    
    //mysql for testing
    private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";     //driver
    private static final String URL = "";  //jdbc:oracle:thin:@//localhost:1521/XE
    private static final String USER = "SYSTEM";    //admin
    private static final String PWD = "";   //pwd for Oracle 
    private Connection conn = null;
    
    public ConnOracleUtility() throws Exception {
        try {
            Class.forName(DRIVER);
            this.conn = DriverManager.getConnection(URL, USER, PWD);
        }
        catch(SQLException e) {
            throw e;
        }
    }
    
    public Connection getConnection() {
        return this.conn;
    }
    
    public void close() throws Exception {
        if(this.conn != null) {
            try {
                this.conn.close();
            }
            catch(SQLException e) {
                throw e;
            }
        }
    }        
}
