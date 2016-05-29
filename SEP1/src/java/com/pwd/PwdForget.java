package com.pwd;

import java.sql.*;
import java.util.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import com.util.ConnOracleUtility;
import javax.mail.Authenticator;
import javax.mail.MessagingException;

/**
 *
 * @author Chen
 */
public class PwdForget {       
    private ConnOracleUtility conn = null;    
    private Statement stmt = null;
    private ResultSet rs = null;
    
    //pwd length
    private final int max = 12;
    private final int min = 8;
    

    
    public PwdForget(ConnOracleUtility conn) {
        this.conn = conn;
    }
    
    
    
    public boolean identifyUser(String vendorID) throws SQLException {
        boolean pass = false;    
        
        try {
            conn.getConnection();
            stmt = this.conn.getConnection().createStatement();
            String sql = "select * from FINANCE_WEB_USERS where VENDORNO='" + vendorID + "'"; 
          
            rs = stmt.executeQuery(sql); 

            while (rs.next()) {
                
                pass = vendorID.equals(rs.getString("VENDORNO"));
                
            }
        } 
        catch (SQLException e) {
            throw e;
        }

        return pass;
    }
    
    
    
    
    public void sendUserEmail(EmailEntry eentry) throws MessagingException {
                  
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", eentry.getHost());
        props.put("mail.smtp.port", eentry.getPort());  


        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(eentry.getUserName(), eentry.getPassword());
            }
        };
        Session session = Session.getDefaultInstance(props, auth);
        Message msg = new MimeMessage(session);
        
        msg.setFrom(new InternetAddress(eentry.getUserName()));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(eentry.getToEmail()));
        msg.setContent(eentry.getMessage(), "text/html");
        msg.setSubject(eentry.getSubject());
        
        Transport.send(msg);
        
    }   
    
    
    
    public String getRegisteredEmail(String vendorID) throws SQLException {
        String email = null;
       
        try {
            conn.getConnection();
            stmt = this.conn.getConnection().createStatement();
            String sql = "select EMAILADDR from FINANCE_WEB_USERS where VENDORNO='" + vendorID + "'";
            rs = stmt.executeQuery(sql);   
            
            //extract data from result set
            while (rs.next()) {
                email = rs.getString("EMAILADDR");
            }
        } 
        catch (SQLException e) {
            throw e;
        } 
        
        return email;
    }
          
    
    
    //save the user temp password & outDate
    public boolean saveChangeToDB(String outDate, String vendorID, String password, EmailEntry eentry) throws SQLException, MessagingException {
        boolean pass = false;

        try {
            conn.getConnection();
            stmt = this.conn.getConnection().createStatement();
            String sql = "UPDATE FINANCE_WEB_USERS SET OUTDATE='" + outDate + "' ,USERPASSWORD='" + password + "' WHERE VENDORNO='" + vendorID + "'";

            
            if(stmt.executeUpdate(sql) > 0) {
                pass = true;
                if(pass == true)
                    this.sendUserEmail(eentry);
            }
           
        }
        catch (SQLException e) {
            throw e;
        } 

        return pass;  
    }
    
    
    
    public String getOutDate(int field, int value) {
        Date date = new Date();
        GregorianCalendar gc = new GregorianCalendar();
        //MM -> month; mm -> mins
        SimpleDateFormat sf = new SimpleDateFormat("dd-MMM-yyyy");
        gc.setTime(date);
        gc.add(field, value);
        gc.set(gc.get(Calendar.YEAR),gc.get(Calendar.MONTH),gc.get(Calendar.DATE));
        String temp = sf.format(gc.getTime());
        
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
