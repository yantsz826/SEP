/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webportal.util;

import java.util.Random;

/**
 *
 * @author Chen
 */
public class StandardPwdUtility {
   //pwd length
    private final int max = 12;
    private final int min = 8;
       
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
