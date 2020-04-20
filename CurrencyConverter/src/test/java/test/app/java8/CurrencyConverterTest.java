package test.app.java8;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Currency;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

import app.java8.CurrencyConverter;
import app.java8.domain.Transaction;
import app.java8.util.CurrencyUtil;

public class CurrencyConverterTest {
	CurrencyConverter currConverter; 
	private static DecimalFormat df2 = new DecimalFormat("#.####");
	
	@Before
	public void setUp() throws Exception {
		// Check if App exists
		currConverter = new CurrencyConverter();
	}
	
	@Test
	public void testIfFileLoaded() throws IOException {
		Stream<String> stream = currConverter.getDataStream();
		assertEquals(true, stream.findFirst().isPresent());
	}
	
	@Test
	public void testIfExchangeMapLoaded() {
		Map<Currency, BigDecimal> usdCurrencyMap = CurrencyUtil.getUSDExchangeRatesMap();
		assertEquals(true, usdCurrencyMap.size() > 0);
	}
	
	@Test
	public void testExcludeHeader() throws IOException {
		Stream<String> stream = currConverter.getDataStream().skip(1);
		//TODO: Replace it will regex of numbers
		assertEquals("2300", (stream.findFirst().get().split("\t", -1))[0]);		
	}
	
	@Test
	public void testCreateRecord() throws IOException {
		Stream<String> stream = currConverter.getDataStream().skip(1);
		Transaction t = new Transaction(stream.findFirst().get());
		assertEquals("2300", t.getCompanyCode());		
	}
	
	@Test
	public void testRecordList() throws IOException {
		Stream<String> stream = currConverter.getDataStream();
		Stream<Transaction> transactionStream = Transaction.getTransactionStream(stream); 
		assertEquals(19, transactionStream.count());		
	}
	
	@Test
	public void testBasicCurrencyConversionUSD() {
		Currency transactionCurrency = Currency.getInstance("GBP");		
		BigDecimal transactionAmount = new BigDecimal(456.85014);		
		assertEquals(df2.format(BigDecimal.valueOf(755.6301).doubleValue()), 
				df2.format(transactionAmount.multiply(
						CurrencyUtil.getUSDExchangeRatesMap().get(transactionCurrency)).doubleValue()));		
	}
	
	@Test
	public void testBasicCurrencyConversionEUR() {
		Currency transactionCurrency = Currency.getInstance("EUR");		
		BigDecimal transactionAmountUSD = BigDecimal.valueOf(755.6301);		
		assertEquals(df2.format(BigDecimal.valueOf(559.6952).doubleValue()), 
				df2.format(transactionAmountUSD.multiply(
						CurrencyUtil.getNonUSDExchangeRatesMap().get(transactionCurrency)).doubleValue()));		
	}
	
	@Test 
	public void testOneTransactionByCountryAndRating() throws IOException {
		Currency baseCurrency = Currency.getInstance("USD"); 
		Currency finalCurrency = Currency.getInstance("EUR"); 
		
		Stream<String> stream = currConverter.getDataStream(); 
		Stream<Transaction> transactionStream = Transaction.getTransactionStream(stream); 
		
		Map<String, Map<String, Double>> mappedByCountryNRating =
				currConverter.calculateAvg(transactionStream, baseCurrency, finalCurrency); 
		
		currConverter.printResults(mappedByCountryNRating, finalCurrency);
		assertEquals(df2.format((BigDecimal.valueOf(559.6952).doubleValue())),
					df2.format(mappedByCountryNRating.get("London").get("A")));
	}
	
	@Test 
	public void testMultiTransactionAvergeByCountryAndRating() throws IOException {
		Currency baseCurrency = Currency.getInstance("USD"); 
		Currency finalCurrency = Currency.getInstance("EUR"); 
		
		Stream<String> stream = currConverter.getDataStream(); 
		Stream<Transaction> transactionStream = Transaction.getTransactionStream(stream); 
		
		Map<String, Map<String, Double>> mappedByCountryNRating =
				currConverter.calculateAvg(transactionStream, baseCurrency, finalCurrency); 
		
		currConverter.printResults(mappedByCountryNRating, finalCurrency);
		assertEquals(df2.format((BigDecimal.valueOf(885647944.1227).doubleValue())),
				df2.format(mappedByCountryNRating.get("NOR").get("A")));
	}
	
	@Test 
	public void testMultiTransactionTotalByCountryAndRating() throws IOException {
		Currency baseCurrency = Currency.getInstance("USD"); 
		Currency finalCurrency = Currency.getInstance("EUR"); 
		
		Stream<String> stream = currConverter.getDataStream(); 
		Stream<Transaction> transactionStream = Transaction.getTransactionStream(stream); 
		
		Map<String, Map<String, BigDecimal>> mappedByCountryNRating =
				currConverter.calculateTotal(transactionStream, baseCurrency, finalCurrency); 
		
		currConverter.printResults(mappedByCountryNRating, finalCurrency);
		assertEquals(df2.format(BigDecimal.valueOf(1771295888.2455).doubleValue()),
					df2.format(mappedByCountryNRating.get("NOR").get("A")));
	}
	 

}
