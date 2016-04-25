/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pwd;

import java.sql.*;
import java.util.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import com.util.ConnMysqlUtility;

/**
 *
 * @author Chen
 */
public class PwdForget {       
    private ConnMysqlUtility conn = null;    
    private Statement stmt = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;
    
    //pwd length
    private final int max = 12;
    private final int min = 8;
    

    public PwdForget(ConnMysqlUtility conn) {
        this.conn = conn;
    }
    
    public boolean identifyUser(String vendorID, String name) throws SQLException {
        boolean pass = false;    
        
        try {
            conn.getConnection();
            stmt = this.conn.getConnection().createStatement();
            String sql = "select * from user where vendor_id=? and name=?";
            pstmt = this.conn.getConnection().prepareStatement(sql);
            pstmt.setString(1, vendorID);
            pstmt.setString(2, name);
            
            rs = pstmt.executeQuery();

            while (rs.next()) {
               
                pass = vendorID.equals(rs.getString("vendor_id")) & name.equals(rs.getString("name"));
                
            }
        } 
        catch (SQLException e) {
            throw e;
        }
        finally {
            rs.close();
            pstmt.close();
            stmt.close();
        }
        
        return pass;
    }
    
    public void sendUserEmail(String host, String port, String userName, String password, String toEmail, String subject, String message) throws MessagingException {
                  
        Properties prop = System.getProperties();
            
        prop.put("mail.smtp.host", host);
        prop.put("mail.smtp.port", port);
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        prop.put("mail.smtp.socketFactory.port", port);
        prop.put("mail.smtp.socketFactory.fallback", "false");

        Authenticator auth = new Authenticator() {
            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        };

        Session session = Session.getDefaultInstance(prop, auth);

        Message msg = new MimeMessage(session);

        msg.setFrom(new InternetAddress(userName));

        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));

        msg.setContent(message, "text/html");

        msg.setSubject(subject);

        Transport transport = session.getTransport("smtp");

        transport.connect("smtp.gmail.com", userName, password);

        transport.sendMessage(msg, msg.getAllRecipients());
    }   
    
    public String getRegisteredEmail(String vendorID, String name) throws SQLException {
        String email = null;
       
        try {
            conn.getConnection();
            stmt = this.conn.getConnection().createStatement();
            String sql = "select email from user where vendor_id='" + vendorID + "' and name='" + name + "'";
            rs = stmt.executeQuery(sql);   
            
            //extract data from result set
            while (rs.next()) {
                email = rs.getString("email");
            }
        } 
        catch (SQLException e) {
            throw e;
        } 
        finally {
            rs.close();
            stmt.close();
        }
        
        return email;
    }
          
    //!!!!!ISSUE!!!!   what about two functions work ConnMysqlUtility conn same time ???
    //close ConnMysqlUtility conn here when pwd forget function finish
    //save the user temp password & outDate
    public boolean saveChangeToDB(String outDate, String vendorID, String name, String password) throws SQLException {
        boolean pass = false;
        //
        try {
            conn.getConnection();
            stmt = this.conn.getConnection().createStatement();
            String sql = "UPDATE user SET outdate='" + outDate + "' ,password='" + password + "' WHERE vendor_id='" + vendorID + "' AND name='" + name + "'";

            //System.out.println("SQL-----------!!!!!!!!!!!!!!!!!!!!!-----: " + sql);
            
            if(stmt.executeUpdate(sql) > 0) {
                pass = true;
            }
            //System.out.println("->" + pass);
           
        }
        catch (SQLException e) {
            throw e;
        } 
        finally {
            stmt.close();
            try {
                this.conn.close();
            } catch (Exception ex) {
                Logger.getLogger(PwdForget.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return pass;  
    }
    
    public String getOutDate(int field, int value) {
        Date date = new Date();
        GregorianCalendar gc = new GregorianCalendar();
        //MM -> month; mm -> mins
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        gc.setTime(date);
        gc.add(field, value);
        gc.set(gc.get(Calendar.YEAR),gc.get(Calendar.MONTH),gc.get(Calendar.DATE));
        //System.out.println("date -------------------->>>>" + gc.getTime());
        String temp = sf.format(gc.getTime());
        //System.out.println("temp -------------------->>>>" + temp);
        //temp = gc.getTime().toString();
        
        return temp;
    }
    
    //at least 8 characters long, at least one capital letters and two numbers. This is a standard Curtin requirement.
    public String pwdGenerator() {
        String pwd = null;
        Random rd = new Random();
        //8 - 12 random number
        int pwdLength = rd.nextInt((max - min) + 1) + min;         
        
        while(pwd == null) {
            int letterUp = 0;
            int number = 0;
            String temp = "";
            temp = getRandomCode(pwdLength);        
            for(int i = 0; i < temp.length(); i++) {
                char c = temp.charAt(i);
                if(Character.isUpperCase(c))
                    letterUp++;
                else if(Character.isDigit(c))
                    number++;
            } 
            if(letterUp >= 1 && number >= 2) {
                pwd = temp;
            }
        }
        
        return pwd;
    } 
    
    private String getRandomCode(int length) {       
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuilder sb = new StringBuilder();
        
        for(int i = 0; i < length; i++) {
            //nextInt(10)   0-9
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }

        return sb.toString();
    } 
    
}
