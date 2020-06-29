package com.sanjeet.solutions.others;

import java.util.Scanner; 

public class RegEx {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String s = scan.nextLine();
        
        if (s.trim().length() > 0) {
	        String newS = s.replaceAll("^[ ]+", "");
	        newS = newS.replaceAll("[^a-zA-Z0-9]+", " ");
	        newS = newS.replaceAll("$[ ]+", "");
	        
	        String[] result = newS.split("\\W",0);        
	        System.out.println(result.length);
	        
	        for (String str: result) {
	            System.out.println(str);
	        }
        }else {
        	System.out.println(s.trim().length());
        }
        
        scan.close();
    }
}