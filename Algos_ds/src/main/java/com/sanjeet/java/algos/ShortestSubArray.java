package com.sanjeet.java.algos;

public class ShortestSubArray {
		
	static int shortestSubArray(int[] arrOfNumbers, int target) { 
		
		int currentSum=0;
		int n = arrOfNumbers.length;
		int min_length = arrOfNumbers.length + 1;
		int startCntr = 0, endCntr = 0;
	
		while (endCntr < n) {
			
			while (currentSum <= target && endCntr < n) {
				currentSum += arrOfNumbers[endCntr++];				
			}
			
			while (currentSum > target && startCntr < n) {						
				if ((endCntr - startCntr) < min_length) {
					min_length = (endCntr - startCntr);
				}				
				currentSum -= arrOfNumbers[startCntr++];
			}			
		}
		System.out.print("Smallest sub-array of: " + min_length);
		return min_length == arrOfNumbers.length + 1? -1 : min_length;
	}

	public static void main(String[] args) {
		
		int[][] inputTC = { {1,2,3,4}, 
							{1,2,3,4}, 
							{1,5,7,13,3,9,10},
							{1,3,5,7,9,11,13}
						};
		int[] input2TC = { 6, 
						   12, 
						   21,
						   20};
		int[] resultTC = { 2, 
						   -1, 
						   3,
						   2};
		
		int totalCases = inputTC.length;
		int passedCases = 0;
		for (int cntr=0; cntr < inputTC.length; cntr++) {
			if (shortestSubArray(inputTC[cntr], input2TC[cntr]) == resultTC[cntr]) {
				System.out.println(" :: case passed : "+ cntr);
				passedCases++;
			}
		}
		
		if (totalCases == passedCases) {
			System.out.println("Total cases passed:(" + passedCases + "/" + totalCases + ")");
		}else {
			System.out.println("Nomber of cases failed : " + (totalCases - passedCases));
		}

	}

}
