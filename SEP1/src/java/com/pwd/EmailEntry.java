/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pwd;

/**
 *
 * @author Chen
 */
public class EmailEntry {
    private String host;
    private String port;
    private String userName;
    private String password;
    private String toEmail;
    private String subject;
    private String message;
    
    public EmailEntry(String host, String port, String userName, String password, String toEmail, String subject, String message) {
        this.host = host;
        this.port = port;
        this.userName = userName;
        this.password = password;
        this.toEmail = toEmail;
        this.subject = subject;
        this.message = message;
    }

    /**
     * @return the host
     */
    public String getHost() {
        return host;
    }

    /**
     * @return the port
     */
    public String getPort() {
        return port;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @return the toEmail
     */
    public String getToEmail() {
        return toEmail;
    }
    
}
