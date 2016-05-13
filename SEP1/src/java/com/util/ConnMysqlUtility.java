/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

import java.sql.*;

/**
 *
 * @author Chen
 */
public class ConnMysqlUtility {
    
    //mysql for testing
    private static final String DRIVER = "com.mysql.jdbc.Driver"; 
    private static final String URL = "jdbc:mysql://localhost:3306/samp_db";
    private static final String USER = "root";
    private static final String PWD = "";
    private Connection conn = null;
    
    public ConnMysqlUtility() throws Exception {
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
