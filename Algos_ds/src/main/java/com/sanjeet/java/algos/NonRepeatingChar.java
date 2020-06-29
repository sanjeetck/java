package com.sanjeet.java.algos;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class NonRepeatingChar {
	
	
	static int kthNonRepeating(String str) { 
		
		if (str == null || str.trim().equals("")) {
			return 0;
		}
		
		int n = str.length(); 
		Map<Character, Integer> mapOfChar = new HashMap<>();
		
		for(int cntr = 0; cntr < n; cntr++) {
			char c = str.charAt(cntr);
			if (mapOfChar.get(c) == null) {
				mapOfChar.put(c, 1);
			}else {
				int count = mapOfChar.get(c);
				mapOfChar.put(c, ++count);
			}
		}
		
		for(int cntr = 0; cntr < n; cntr++) {
			char c = str.charAt(cntr);
			int count = mapOfChar.get(c);
			if (count ==1) {
				return cntr;
			}			
		}
		
		return 0;
	}
	
	static int kthNonRepeatingJava8(String str) {
		
		if (str == null || str.trim().equals("")) {
			return 0;
		}
		
		Map<Character, Integer> mapOfChar =
							str.chars().boxed().collect(
							Collectors.toMap(k ->
								Character.valueOf((char) k.intValue()), v -> 1, Integer::sum));

		int n = str.length(); 
		for(int cntr = 0; cntr < n; cntr++) {
			char c = str.charAt(cntr);
			int count = mapOfChar.get(c);
			if (count ==1) {
				return cntr;
			}			
		}
		return 0;
	}

	public static void main(String[] args) {
		boolean java8Ver = false;
		String[] strTC = {"avcdadv", "bbccdaa", "ffgrgrwcv", "abcd", null, ""};
		int[] resultTC = {2, 4, 6, 0, 0, 0};
		
		int totalCases = strTC.length;
		int passedCases = 0;
		for (int cntr=0; cntr < strTC.length; cntr++) {
			if (java8Ver) {			
				if (kthNonRepeatingJava8(strTC[cntr]) == resultTC[cntr]) {
					passedCases++;
				}
			}else { 
				if (kthNonRepeating(strTC[cntr]) == resultTC[cntr]) {
					passedCases++;
				}
			}
		}
		
		if (totalCases == passedCases) {
			System.out.println("Total cases passed:(" + passedCases + "/" + totalCases + ")");
		}else {
			System.out.println("Nomber of cases failed : " + (totalCases - passedCases));
		}

	}

}
