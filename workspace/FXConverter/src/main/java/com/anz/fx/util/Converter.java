package com.anz.fx.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.anz.fx.config.ConfigReader;
import com.anz.fx.dto.FxMatrixDto;
import com.anz.fx.exception.CurrencyMappingException;

@Component
public class Converter {

	private static final Logger LOGGER = LoggerFactory.getLogger(Converter.class);
	
	@Autowired
	private ConfigReader configReader;
	
	public ConfigReader getConfigReader() {
		return configReader;
	}

	public void setConfigReader(ConfigReader configReader) {
		this.configReader = configReader;
	}

	public BigDecimal processConversion(String baseCurrency, String basePrice, String targetCurrency) throws CurrencyMappingException, Exception {

		BigDecimal convertedValue = convert(baseCurrency, basePrice, targetCurrency);
		
		HashMap<String, Integer> currencyPrecisionMap = configReader.getCurrencyPrecisionMap();
		Integer precisionValue = currencyPrecisionMap.get(targetCurrency.trim().toUpperCase());
		if(precisionValue != 0) {
			convertedValue = convertedValue.setScale(precisionValue, RoundingMode.HALF_UP);
		} else {
			convertedValue = convertedValue.setScale(precisionValue, RoundingMode.FLOOR);
		}
		
		return convertedValue;
	}

	private BigDecimal convert(String baseCurrency, String basePrice, String targetCurrency) throws Exception {

		HashMap<String, List<FxMatrixDto>> currencyMatixMap = configReader.getCurrencyMatixMap();

		List<FxMatrixDto> srcFxMatrixDtoList = currencyMatixMap.get(baseCurrency.trim().toUpperCase());
		List<FxMatrixDto> targetFxMatrixDtoList = currencyMatixMap.get(targetCurrency.trim().toUpperCase());
		if (null == srcFxMatrixDtoList || null == targetFxMatrixDtoList) {
			throw new CurrencyMappingException("Unable to find rate for " + baseCurrency + "/" + targetCurrency);
		}

		for (FxMatrixDto fxMatrixDto : srcFxMatrixDtoList) {
			if (targetCurrency.trim().toUpperCase().equals(fxMatrixDto.getCurrencyName())) {

				if (fxMatrixDto.getCurrencyRef().trim().equalsIgnoreCase("1:1")) {

					BigDecimal basePriceValue = new BigDecimal(basePrice);
					return basePriceValue;
					
				} else if (fxMatrixDto.getCurrencyRef().equalsIgnoreCase("D")) {
					
					return calcByDirectValue(baseCurrency, basePrice, targetCurrency);
					
				} else if (fxMatrixDto.getCurrencyRef().equalsIgnoreCase("Inv")) {
					
					return calcByInvValue(baseCurrency, basePrice, targetCurrency);
					
				} else {
					
					BigDecimal baseCrossValue = convert(baseCurrency, basePrice, fxMatrixDto.getCurrencyRef());
					BigDecimal targetCrossValue = convert(fxMatrixDto.getCurrencyRef(),	baseCrossValue.toString(), targetCurrency);

					return targetCrossValue;
				}
			}
		}

		return null;
	}

	private BigDecimal calcByDirectValue(String baseCurrency, String basePrice, String targetCurrency) {

		HashMap<String, BigDecimal> currencyRatesMap = configReader.getCurrencyRatesMap();

		BigDecimal unitValue = currencyRatesMap.get(baseCurrency.toUpperCase() + targetCurrency.toUpperCase());
		BigDecimal basePriceValue = new BigDecimal(basePrice);

		return unitValue.multiply(basePriceValue);
	}

	private BigDecimal calcByInvValue(String baseCurrency, String basePrice, String targetCurrency) {

		HashMap<String, BigDecimal> currencyRatesMap = configReader.getCurrencyRatesMap();

		BigDecimal unitValue = currencyRatesMap.get(targetCurrency.toUpperCase() + baseCurrency.toUpperCase());
		BigDecimal invertedUnitValue = new BigDecimal(1).divide(unitValue, 10, RoundingMode.HALF_UP);
		BigDecimal basePriceValue = new BigDecimal(basePrice);

		return basePriceValue.multiply(invertedUnitValue);
	}
	
}