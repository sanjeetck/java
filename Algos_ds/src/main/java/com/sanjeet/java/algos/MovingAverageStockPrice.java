package com.sanjeet.java.algos;

import java.util.Arrays;

public class MovingAverageStockPrice {
		
	static double[] movingAvgStockPrice(double[] arrOfStockPrices, int movingWindow) { 
		
		double[] averagePrice = new double[arrOfStockPrices.length];
		int n = arrOfStockPrices.length;		
		int startCntr = 0, endCntr = 1;		
		
		while (endCntr <= n) {			
			if ( (endCntr - startCntr -1) < movingWindow) {				
				averagePrice[endCntr-1] = avgPrice(arrOfStockPrices, startCntr, endCntr);
				endCntr++;
			}else {
				startCntr++;
				averagePrice[endCntr-1] = avgPrice(arrOfStockPrices, startCntr, endCntr);
				endCntr++;
			}		
		}		
		return averagePrice;
	}
	
	private static double avgPrice(double[] arrOfStockPrices, int start, int end) {
		double sum=0;		
		for (int i = start; i < end; i++) {
			sum += arrOfStockPrices[i];
		}
		return sum/(end-start);
	}

	public static void main(String[] args) {
		
		double[][] inputTC = { {100.0, 102.0, 104.0, 103.0, 103.0, 103.0, 103.0, 103.0, 103.0},
							   {100.0, 102.0, 104.0, 103.0, 103.0, 103.0, 103.0, 103.0, 103.0}
						};
		int[] input2TC = { 5,
						   3
						 };
		double[][] resultTC = { {100.0, 101.0, 102.0, 102.25, 102.4, 103, 103.2, 103.0, 103.0},
								{100.0, 101.0, 102.0, 103, 103.33333333333333, 103, 103.0, 103.0, 103.0} 
						 };
		
		int totalCases = inputTC.length;
		int passedCases = 0;
		for (int cntr=0; cntr < inputTC.length; cntr++) {
			double[] result = movingAvgStockPrice(inputTC[cntr], input2TC[cntr]);
			if ( Arrays.equals(result, resultTC[cntr]) ) {
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
