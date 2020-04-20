package app.java8.domain;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.stream.Stream;

public class Transaction {	
	private String companyCode;
	private String account;	
	private String city;	
	private String country;	
	private String creditRating;	
	private java.util.Currency transactedCurrency;	
	private BigDecimal amount;
	private BigDecimal baseCurrencyAmount;
	private BigDecimal resultCurrencyAmount;
	
	public Transaction(String string) {
		String[] s = string.split("\t", -1);
		
		this.companyCode = s[0];
		this.account = s[1];
		this.city = s[2];
		this.country = s[3];
		this.creditRating = s[4].toUpperCase();
		this.transactedCurrency = Currency.getInstance(s[5]);
		//this.amount = Double.parseDouble(s[6]); //new BigDecimal
		this.amount = new BigDecimal(s[6]); //new BigDecimal
	}

	
	public static Transaction createTransaction(String transaction) {		
		Transaction t = new Transaction(transaction);
		return t;
	}
	
	public static Stream<Transaction> getTransactionStream(Stream<String> stream) {
		Stream<Transaction> transactionStream = stream.skip(1)  // skipping the header
				.map(transactionString -> {
					Transaction transaction = Transaction.createTransaction(transactionString);			
		      	  	return transaction;
		          });
		return transactionStream;
	}
	
	public String getCountryOrCity() {
		if (getCountry().isEmpty()) {
			return getCity();
		}
		return getCountry();
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCreditRating() {
		return creditRating;
	}

	public void setCreditRating(String creditRating) {
		this.creditRating = creditRating;
	}

	public java.util.Currency getTransactedCurrency() {
		return transactedCurrency;
	}

	public void setTransactedCurrency(java.util.Currency transactedCurrency) {
		this.transactedCurrency = transactedCurrency;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getBaseCurrencyAmount() {
		return baseCurrencyAmount;
	}


	public void setBaseCurrencyAmount(BigDecimal calculatedAmount) {
		this.baseCurrencyAmount = calculatedAmount;
	}


	public BigDecimal getResultCurrencyAmount() {
		return resultCurrencyAmount;
	}


	public void setResultCurrencyAmount(BigDecimal resultCurrencyAmount) {
		this.resultCurrencyAmount = resultCurrencyAmount;
	}


	@Override
	public String toString() {
		return "Transaction [companyCode=" + companyCode + ", account=" + account + ", city=" + city + ", country="
				+ country + ", creditRating=" + creditRating + ", transactedCurrency=" + transactedCurrency
				+ ", amount=" + amount + ", baseCurrencyAmount=" + baseCurrencyAmount + ", resultCurrencyAmount="
				+ resultCurrencyAmount + "]";
	}



	
}
