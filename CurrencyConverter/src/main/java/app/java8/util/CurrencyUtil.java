package app.java8.util;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

public class CurrencyUtil {

	private final static Map<Currency, BigDecimal>  usdExchangeRatesMap = new HashMap<>();
	static { 
		usdExchangeRatesMap.put(Currency.getInstance("GBP"), BigDecimal.valueOf(1.654));
		usdExchangeRatesMap.put(Currency.getInstance("CHF"), BigDecimal.valueOf(1.10));
		usdExchangeRatesMap.put(Currency.getInstance("EUR"), BigDecimal.valueOf(1.35));
	}
	
	public static Map<Currency, BigDecimal> getUSDExchangeRatesMap() {
		return usdExchangeRatesMap;
	}

	private final static Map<Currency, BigDecimal>  nonUSDExchangeRatesMap = new HashMap<>();
	static { 
		nonUSDExchangeRatesMap.put(Currency.getInstance("GBP"), BigDecimal.valueOf(0.6045));
		nonUSDExchangeRatesMap.put(Currency.getInstance("CHF"), BigDecimal.valueOf(0.9090));
		nonUSDExchangeRatesMap.put(Currency.getInstance("EUR"), BigDecimal.valueOf(0.7407));
	}
	
	public static Map<Currency, BigDecimal> getNonUSDExchangeRatesMap() {
		return nonUSDExchangeRatesMap;
	}

}
