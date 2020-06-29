package app.java8;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.Currency;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import app.java8.domain.Transaction;
import app.java8.util.CurrencyUtil;

public class CurrencyConverter {
	
	private static DecimalFormat df2 = new DecimalFormat("#.####");
	private final static BigDecimal CONSTANT_ONE = new BigDecimal(1);
	private Stream<String> stream;
	
	public Stream<String> getDataStream() throws IOException {
		stream = Files.lines(Paths.get("src\\test\\resources\\FILE.DAT")) ;
		return stream;
	}

	public Map<String, Map<String, Double>> calculateAvg(Stream<Transaction> transactionStream, Currency baseCurrency, Currency resultCurrency) {
		 Map<String, Map<String, Double>> mappedByCountryNRating =
				transactionStream
						.filter(transaction -> !transaction.getCreditRating().isEmpty())
						.map(transaction -> {  
								// Calculate the transaction in USD
								calculateInBaseCurrency(transaction);
										
								// Get the transaction in result currency 
								calculateInResultCurrency(resultCurrency, transaction);
								return transaction;
						})
						.collect( Collectors.groupingBy(Transaction::getCountryOrCity, 
								Collectors.groupingBy(Transaction::getCreditRating,     		
										Collectors.mapping(Transaction::getResultCurrencyAmount, 
												Collectors.averagingDouble(BigDecimal::doubleValue)))));																							

		return mappedByCountryNRating;
	}
	
	public Map<String, Map<String, BigDecimal>> calculateTotal(Stream<Transaction> transactionStream, Currency baseCurrency, Currency resultCurrency) {
		 Map<String, Map<String, BigDecimal>> mappedByCountryNRating =
				transactionStream
						.filter(transaction -> !transaction.getCreditRating().isEmpty())
						.map(transaction -> {  
								// Calculate the transaction in USD
								calculateInBaseCurrency(transaction);
										
								// Get the transaction in result currency 
								calculateInResultCurrency(resultCurrency, transaction);
								return transaction;
						})
						.collect( Collectors.groupingBy(Transaction::getCountryOrCity, 
								Collectors.groupingBy(Transaction::getCreditRating,     			
										Collectors.mapping(Transaction::getResultCurrencyAmount, 												
												Collectors.reducing(BigDecimal.ZERO, BigDecimal::add)))));												

		return mappedByCountryNRating;
	}
	
	public <T extends Number> void printResults(Map<String, Map<String, T>> mappedByCountryNRating, Currency currency) {
		
		mappedByCountryNRating.forEach( 
	 			(key,map) -> { System.out.println("Country: " + key + " CreditRatings: " + map.keySet() );	 					
	 							map.keySet().forEach( (key2) -> {	 									
	 								System.out.println(" for CreditRating: " + key2 
	 										+ " Value (in "+ currency + "): " + df2.format(map.get(key2)));
	 							});
	 					});
	}

	private void calculateInResultCurrency(Currency resultCurrency, Transaction transaction) {
		transaction.setResultCurrencyAmount(
				transaction.getBaseCurrencyAmount().multiply(
						CurrencyUtil.getNonUSDExchangeRatesMap().getOrDefault(resultCurrency, CONSTANT_ONE))
		);
	}

	private void calculateInBaseCurrency(Transaction transaction) {
		transaction.setBaseCurrencyAmount(transaction.getAmount().multiply(
		CurrencyUtil.getUSDExchangeRatesMap().getOrDefault(transaction.getTransactedCurrency(), CONSTANT_ONE)));
	}
}
