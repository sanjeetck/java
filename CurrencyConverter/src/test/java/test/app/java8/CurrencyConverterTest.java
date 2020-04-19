package test.app.java8;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.math.BigDecimal;
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
	
	
	/*
	 * @Test public void testCurrencyConversion() throws IOException {
	 * Stream<String> stream = currConverter.getDataStream(); Currency baseCurrency
	 * = Currency.getInstance("USD"); Currency finalCurrency =
	 * Currency.getInstance("EUR"); Stream<Transaction> transactionStream =
	 * Transaction.getTransactionStream(stream);
	 * currConverter.calculate(transactionStream, baseCurrency, finalCurrency);
	 * 
	 * 
	 * assertEquals(10145709240540253390.4016,
	 * transactionStream.skip(1).findFirst().get().getCalculatedAmount(), 0.1); }
	 */

	@Test
	public void testBasicCurrencyConversionUSD() {
		Currency transactionCurrency = Currency.getInstance("GBP");		
		BigDecimal transactionAmount = new BigDecimal(456.85014);		
		assertEquals(new BigDecimal(755.6301).setScale(2, BigDecimal.ROUND_DOWN), 
				transactionAmount.multiply(CurrencyUtil.getUSDExchangeRatesMap().get(transactionCurrency)).setScale(2, BigDecimal.ROUND_DOWN));		
	}
	
	@Test
	public void testBasicCurrencyConversionEUR() {
		Currency transactionCurrency = Currency.getInstance("EUR");		
		BigDecimal transactionAmountUSD = new BigDecimal(755.6301);		
		assertEquals(new BigDecimal(559.6952).setScale(2, BigDecimal.ROUND_DOWN), 
				transactionAmountUSD.multiply(CurrencyUtil.getNonUSDExchangeRatesMap().get(transactionCurrency)).setScale(2, BigDecimal.ROUND_DOWN));		
	}
	
	@Test 
	public void testOneTransactionByCountryAndRating() throws IOException {
		Currency baseCurrency = Currency.getInstance("USD"); 
		Currency finalCurrency = Currency.getInstance("EUR"); 
		
		Stream<String> stream = currConverter.getDataStream(); 
		Stream<Transaction> transactionStream = Transaction.getTransactionStream(stream); 
		
		Map<String, Map<String, BigDecimal>> mappedByCountryNRating =
				currConverter.calculate(transactionStream, baseCurrency, finalCurrency); 
		
		currConverter.printResults(mappedByCountryNRating);
		assertEquals(new BigDecimal(559.6952).setScale(2, BigDecimal.ROUND_DOWN),
					mappedByCountryNRating.get("London").get("A").setScale(2, BigDecimal.ROUND_DOWN));
	}
	 

}
