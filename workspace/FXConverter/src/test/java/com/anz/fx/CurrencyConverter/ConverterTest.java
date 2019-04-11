package com.anz.fx.CurrencyConverter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.anz.fx.config.ConfigReader;
import com.anz.fx.dto.FxMatrixDto;
import com.anz.fx.exception.CurrencyMappingException;
import com.anz.fx.util.Converter;

public class ConverterTest {
	
	Converter converter;
	ConfigReader configReader;
	
	@Before
	public void init() {
		converter = new Converter();
		configReader = new ConfigReader();
		converter.setConfigReader(configReader);
		
		HashMap<String, Integer> currencyPrecisionMap = new HashMap<String, Integer>();
		currencyPrecisionMap.put("AUD", 2); currencyPrecisionMap.put("CAD", 2); currencyPrecisionMap.put("CNY", 2);
		currencyPrecisionMap.put("CZK", 2); currencyPrecisionMap.put("DKK", 2); currencyPrecisionMap.put("EUR", 2);
		currencyPrecisionMap.put("GBP", 2); currencyPrecisionMap.put("JPY", 0); currencyPrecisionMap.put("NOK", 2);
		currencyPrecisionMap.put("NZD", 2); currencyPrecisionMap.put("USD", 2); 
		
		HashMap<String, BigDecimal> currencyRatesMap = new HashMap<String, BigDecimal>();
		currencyRatesMap.put("AUDUSD", new  BigDecimal("0.8371")); currencyRatesMap.put("CADUSD", new  BigDecimal("0.8711"));
		currencyRatesMap.put("USDCNY", new  BigDecimal("6.1715")); currencyRatesMap.put("EURUSD", new  BigDecimal("1.2315"));
		currencyRatesMap.put("GBPUSD", new  BigDecimal("1.5683")); currencyRatesMap.put("NZDUSD", new  BigDecimal("0.775"));
		currencyRatesMap.put("USDJPY", new  BigDecimal("119.95")); currencyRatesMap.put("EURCZK", new  BigDecimal("27.6028"));
		currencyRatesMap.put("EURDKK", new  BigDecimal("7.4405")); currencyRatesMap.put("EURNOK", new  BigDecimal("8.6651"));
		
		HashMap<String, List<FxMatrixDto>> currencyMatixMap = new HashMap<String, List<FxMatrixDto>>();
		setEntriesToMatrix(currencyMatixMap);
		List<FxMatrixDto> fxMatrixDtoList;
		fxMatrixDtoList = currencyMatixMap.get("AUD");
		addMatrixDto(fxMatrixDtoList, "AUD", "1:1");
		addMatrixDto(fxMatrixDtoList, "CAD", "USD");
		addMatrixDto(fxMatrixDtoList, "CNY", "USD");
		addMatrixDto(fxMatrixDtoList, "CZK", "USD");
		addMatrixDto(fxMatrixDtoList, "DKK", "USD");
		addMatrixDto(fxMatrixDtoList, "EUR", "USD");
		addMatrixDto(fxMatrixDtoList, "GBP", "USD");
		addMatrixDto(fxMatrixDtoList, "JPY", "USD");
		addMatrixDto(fxMatrixDtoList, "NOK", "USD");
		addMatrixDto(fxMatrixDtoList, "NZD", "USD");
		addMatrixDto(fxMatrixDtoList, "USD", "D");
		
		fxMatrixDtoList = currencyMatixMap.get("CAD");
		addMatrixDto(fxMatrixDtoList, "AUD", "USD");
		addMatrixDto(fxMatrixDtoList, "CAD", "1:1");
		addMatrixDto(fxMatrixDtoList, "CNY", "USD");
		addMatrixDto(fxMatrixDtoList, "CZK", "USD");
		addMatrixDto(fxMatrixDtoList, "DKK", "USD");
		addMatrixDto(fxMatrixDtoList, "EUR", "USD");
		addMatrixDto(fxMatrixDtoList, "GBP", "USD");
		addMatrixDto(fxMatrixDtoList, "JPY", "USD");
		addMatrixDto(fxMatrixDtoList, "NOK", "USD");
		addMatrixDto(fxMatrixDtoList, "NZD", "USD");
		addMatrixDto(fxMatrixDtoList, "USD", "D");
		
		fxMatrixDtoList = currencyMatixMap.get("CNY");
		addMatrixDto(fxMatrixDtoList, "AUD", "USD");
		addMatrixDto(fxMatrixDtoList, "CAD", "USD");
		addMatrixDto(fxMatrixDtoList, "CNY", "1:1");
		addMatrixDto(fxMatrixDtoList, "CZK", "USD");
		addMatrixDto(fxMatrixDtoList, "DKK", "USD");
		addMatrixDto(fxMatrixDtoList, "EUR", "USD");
		addMatrixDto(fxMatrixDtoList, "GBP", "USD");
		addMatrixDto(fxMatrixDtoList, "JPY", "USD");
		addMatrixDto(fxMatrixDtoList, "NOK", "USD");
		addMatrixDto(fxMatrixDtoList, "NZD", "USD");
		addMatrixDto(fxMatrixDtoList, "USD", "D");
		
		fxMatrixDtoList = currencyMatixMap.get("CZK");
		addMatrixDto(fxMatrixDtoList, "AUD", "USD");
		addMatrixDto(fxMatrixDtoList, "CAD", "USD");
		addMatrixDto(fxMatrixDtoList, "CNY", "USD");
		addMatrixDto(fxMatrixDtoList, "CZK", "1:1");
		addMatrixDto(fxMatrixDtoList, "DKK", "EUR");
		addMatrixDto(fxMatrixDtoList, "EUR", "Inv");
		addMatrixDto(fxMatrixDtoList, "GBP", "USD");
		addMatrixDto(fxMatrixDtoList, "JPY", "USD");
		addMatrixDto(fxMatrixDtoList, "NOK", "EUR");
		addMatrixDto(fxMatrixDtoList, "NZD", "USD");
		addMatrixDto(fxMatrixDtoList, "USD", "EUR");
		
		fxMatrixDtoList = currencyMatixMap.get("DKK");
		addMatrixDto(fxMatrixDtoList, "AUD", "USD");
		addMatrixDto(fxMatrixDtoList, "CAD", "USD");
		addMatrixDto(fxMatrixDtoList, "CNY", "USD");
		addMatrixDto(fxMatrixDtoList, "CZK", "EUR");
		addMatrixDto(fxMatrixDtoList, "DKK", "1:1");
		addMatrixDto(fxMatrixDtoList, "EUR", "Inv");
		addMatrixDto(fxMatrixDtoList, "GBP", "USD");
		addMatrixDto(fxMatrixDtoList, "JPY", "USD");
		addMatrixDto(fxMatrixDtoList, "NOK", "EUR");
		addMatrixDto(fxMatrixDtoList, "NZD", "USD");
		addMatrixDto(fxMatrixDtoList, "USD", "EUR");
		
		fxMatrixDtoList = currencyMatixMap.get("EUR");
		addMatrixDto(fxMatrixDtoList, "AUD", "USD");
		addMatrixDto(fxMatrixDtoList, "CAD", "USD");
		addMatrixDto(fxMatrixDtoList, "CNY", "USD");
		addMatrixDto(fxMatrixDtoList, "CZK", "D");
		addMatrixDto(fxMatrixDtoList, "DKK", "D");
		addMatrixDto(fxMatrixDtoList, "EUR", "1:1");
		addMatrixDto(fxMatrixDtoList, "GBP", "USD");
		addMatrixDto(fxMatrixDtoList, "JPY", "USD");
		addMatrixDto(fxMatrixDtoList, "NOK", "D");
		addMatrixDto(fxMatrixDtoList, "NZD", "USD");
		addMatrixDto(fxMatrixDtoList, "USD", "D");
		
		fxMatrixDtoList = currencyMatixMap.get("GBP");
		addMatrixDto(fxMatrixDtoList, "AUD", "USD");
		addMatrixDto(fxMatrixDtoList, "CAD", "USD");
		addMatrixDto(fxMatrixDtoList, "CNY", "USD");
		addMatrixDto(fxMatrixDtoList, "CZK", "USD");
		addMatrixDto(fxMatrixDtoList, "DKK", "USD");
		addMatrixDto(fxMatrixDtoList, "EUR", "USD");
		addMatrixDto(fxMatrixDtoList, "GBP", "1:1");
		addMatrixDto(fxMatrixDtoList, "JPY", "USD");
		addMatrixDto(fxMatrixDtoList, "NOK", "USD");
		addMatrixDto(fxMatrixDtoList, "NZD", "USD");
		addMatrixDto(fxMatrixDtoList, "USD", "D");
		
		fxMatrixDtoList = currencyMatixMap.get("JPY");
		addMatrixDto(fxMatrixDtoList, "AUD", "USD");
		addMatrixDto(fxMatrixDtoList, "CAD", "USD");
		addMatrixDto(fxMatrixDtoList, "CNY", "USD");
		addMatrixDto(fxMatrixDtoList, "CZK", "USD");
		addMatrixDto(fxMatrixDtoList, "DKK", "USD");
		addMatrixDto(fxMatrixDtoList, "EUR", "USD");
		addMatrixDto(fxMatrixDtoList, "GBP", "USD");
		addMatrixDto(fxMatrixDtoList, "JPY", "1:1");
		addMatrixDto(fxMatrixDtoList, "NOK", "USD");
		addMatrixDto(fxMatrixDtoList, "NZD", "USD");
		addMatrixDto(fxMatrixDtoList, "USD", "Inv");
		
		fxMatrixDtoList = currencyMatixMap.get("NOK");
		addMatrixDto(fxMatrixDtoList, "AUD", "USD");
		addMatrixDto(fxMatrixDtoList, "CAD", "USD");
		addMatrixDto(fxMatrixDtoList, "CNY", "USD");
		addMatrixDto(fxMatrixDtoList, "CZK", "EUR");
		addMatrixDto(fxMatrixDtoList, "DKK", "EUR");
		addMatrixDto(fxMatrixDtoList, "EUR", "Inv");
		addMatrixDto(fxMatrixDtoList, "GBP", "USD");
		addMatrixDto(fxMatrixDtoList, "JPY", "USD");
		addMatrixDto(fxMatrixDtoList, "NOK", "1:1");
		addMatrixDto(fxMatrixDtoList, "NZD", "USD");
		addMatrixDto(fxMatrixDtoList, "USD", "EUR");
		
		fxMatrixDtoList = currencyMatixMap.get("NZD");
		addMatrixDto(fxMatrixDtoList, "AUD", "USD");
		addMatrixDto(fxMatrixDtoList, "CAD", "USD");
		addMatrixDto(fxMatrixDtoList, "CNY", "USD");
		addMatrixDto(fxMatrixDtoList, "CZK", "USD");
		addMatrixDto(fxMatrixDtoList, "DKK", "USD");
		addMatrixDto(fxMatrixDtoList, "EUR", "USD");
		addMatrixDto(fxMatrixDtoList, "GBP", "USD");
		addMatrixDto(fxMatrixDtoList, "JPY", "USD");
		addMatrixDto(fxMatrixDtoList, "NOK", "USD");
		addMatrixDto(fxMatrixDtoList, "NZD", "1:1");
		addMatrixDto(fxMatrixDtoList, "USD", "D");
		
		fxMatrixDtoList = currencyMatixMap.get("USD");
		addMatrixDto(fxMatrixDtoList, "AUD", "Inv");
		addMatrixDto(fxMatrixDtoList, "CAD", "Inv");
		addMatrixDto(fxMatrixDtoList, "CNY", "Inv");
		addMatrixDto(fxMatrixDtoList, "CZK", "EUR");
		addMatrixDto(fxMatrixDtoList, "DKK", "EUR");
		addMatrixDto(fxMatrixDtoList, "EUR", "Inv");
		addMatrixDto(fxMatrixDtoList, "GBP", "Inv");
		addMatrixDto(fxMatrixDtoList, "JPY", "D");
		addMatrixDto(fxMatrixDtoList, "NOK", "EUR");
		addMatrixDto(fxMatrixDtoList, "NZD", "Inv");
		addMatrixDto(fxMatrixDtoList, "USD", "1:1");
		
		configReader.setCurrencyMatixMap(currencyMatixMap);
		configReader.setCurrencyPrecisionMap(currencyPrecisionMap);
		configReader.setCurrencyRatesMap(currencyRatesMap);
	}
	
	private void setEntriesToMatrix(HashMap<String, List<FxMatrixDto>> currencyMatixMap) {
		
		String[] matrixData = {"AUD","CAD","CNY", "CZK", "DKK", "EUR", "GBP", "JPY", "NOK", "NZD",	"USD"};
		for (int i = 0 ; i < matrixData.length; i ++) {
			currencyMatixMap.put(matrixData[i], new  ArrayList<FxMatrixDto>());
		}
	}
	
	private void addMatrixDto(List<FxMatrixDto> fxMatrixDtoList, String currencyName, String currencyRef) {
		FxMatrixDto fxMatrixDto = new FxMatrixDto();
		fxMatrixDto.setCurrencyName(currencyName);
		fxMatrixDto.setCurrencyRef(currencyRef);
		fxMatrixDtoList.add(fxMatrixDto);
	}
	
	//Error because of  invalid mapping for CNY to USD as D. correct value should be - Inv
	@Test(expected = Exception.class)
	public void testProcessConversion_CNY_TO_CAD() throws Exception{
		converter.processConversion("CNY", "1.00", "CAD");
	}
	
	//Error because of  invalid mapping for USD to CNY as Inv. correct value should be - D
	@Test (expected = Exception.class)
	public void testProcessConversion_CZK_TO_CNY() throws Exception{
		converter.processConversion("CZK", "1.00", "CNY");
	}
	
	@Test (expected = CurrencyMappingException.class)
	public void testProcessConversion_XXX_TO_CNY() throws Exception{
		converter.processConversion("XXX", "1.00", "CNY");
	}
	
	@Test
	public void testProcessConversion_AUD_TO_AUD() throws Exception{
		BigDecimal convertedValue = converter.processConversion("AUD", "1.00", "AUD");
		Assert.assertEquals(convertedValue.toString(), "1.00");
	}
	
	@Test
	public void testProcessConversion_CAD_TO_AUD() throws Exception{
		BigDecimal convertedValue = converter.processConversion("CAD", "1.00", "AUD");
		Assert.assertEquals(convertedValue.toString(), "1.04");
	}
	
	@Test
	public void testProcessConversion_CZK_TO_DKK() throws Exception{
		BigDecimal convertedValue = converter.processConversion("CZK", "1.00", "DKK");
		Assert.assertEquals(convertedValue.toString(), "0.27");
	}
	
	@Test
	public void testProcessConversion_DKK_TO_GBP() throws Exception{
		BigDecimal convertedValue = converter.processConversion("DKK", "1.00", "GBP");
		Assert.assertEquals(convertedValue.toString(), "0.11");
	}
	
	@Test
	public void testProcessConversion_EUR_TO_GBP() throws Exception{
		BigDecimal convertedValue = converter.processConversion("EUR", "1.00", "GBP");
		Assert.assertEquals(convertedValue.toString(), "0.79");
	}
	
	@Test
	public void testProcessConversion_GBP_TO_NOK() throws Exception{
		BigDecimal convertedValue = converter.processConversion("GBP", "1.00", "NOK");
		Assert.assertEquals(convertedValue.toString(), "11.03");
	}
	
	@Test
	public void testProcessConversion_JPY_TO_USD() throws Exception{
		BigDecimal convertedValue = converter.processConversion("JPY", "1.00", "USD");
		Assert.assertEquals(convertedValue.toString(), "0.01");
	}
	
	@Test
	public void testProcessConversion_USD_TO_JPY() throws Exception{
		BigDecimal convertedValue = converter.processConversion("USD", "1.00", "JPY");
		Assert.assertEquals(convertedValue.toString(), "119");
	}

}
