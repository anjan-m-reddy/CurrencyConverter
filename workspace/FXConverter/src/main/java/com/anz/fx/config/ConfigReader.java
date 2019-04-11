package com.anz.fx.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.anz.fx.dto.FxMatrixDto;
import com.anz.fx.util.FXConstants;

@Component
public class ConfigReader implements ApplicationListener<ContextRefreshedEvent> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigReader.class);

	private HashMap<String, Integer> currencyPrecisionMap = new HashMap<String, Integer>();
	private HashMap<String, BigDecimal> currencyRatesMap = new HashMap<String, BigDecimal>();
	private HashMap<String, List<FxMatrixDto>> currencyMatixMap = new HashMap<String, List<FxMatrixDto>>();

	@Override
	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
		LOGGER.info("Start reading the config info");
		try {
			Resource resource = new ClassPathResource(FXConstants.FILE_NAME);
			InputStream excelFileInputStream = resource.getInputStream();
			Workbook workbook = new XSSFWorkbook(excelFileInputStream);

			initCurrencyMatixMap(currencyMatixMap, workbook);
			initRates(currencyRatesMap, workbook);
			initPrecision(currencyPrecisionMap, workbook);
			
			excelFileInputStream.close();
			LOGGER.info("End reading the config info");
		} catch (FileNotFoundException e) {
			LOGGER.info("File error reading the config info", e);
		} catch (IOException e) {
			LOGGER.info("Exception reading the config info", e);
		}

	}

	public HashMap<String, Integer> getCurrencyPrecisionMap() {
		return currencyPrecisionMap;
	}

	public HashMap<String, BigDecimal> getCurrencyRatesMap() {
		return currencyRatesMap;
	}

	public HashMap<String, List<FxMatrixDto>> getCurrencyMatixMap() {
		return currencyMatixMap;
	}
	
	public void setCurrencyPrecisionMap(HashMap<String, Integer> currencyPrecisionMap) {
		this.currencyPrecisionMap = currencyPrecisionMap;
	}

	public void setCurrencyRatesMap(HashMap<String, BigDecimal> currencyRatesMap) {
		this.currencyRatesMap = currencyRatesMap;
	}

	public void setCurrencyMatixMap(HashMap<String, List<FxMatrixDto>> currencyMatixMap) {
		this.currencyMatixMap = currencyMatixMap;
	}

	private void initCurrencyMatixMap(HashMap<String, List<FxMatrixDto>> currencyMatixMap,
			Workbook workbook) throws IOException {

		ArrayList<String> currSeqList = new ArrayList<String>();
		
		Sheet sheetCurrMatrix = (Sheet) workbook.getSheet(FXConstants.SHEET_CURR_MATRIX);
		Iterator<Row> iterator = sheetCurrMatrix.iterator();

		while (iterator.hasNext()) {

			Row currentRow = iterator.next();
			if (currentRow.getRowNum() == 0) {

				Iterator<Cell> cellIterator = currentRow.iterator();
				while (cellIterator.hasNext()) {

					Cell cell = cellIterator.next();
					if (!cell.getStringCellValue().trim().equals("")) {
						currencyMatixMap.put(cell.getStringCellValue().trim().toUpperCase(),
								new ArrayList<FxMatrixDto>());
						currSeqList.add(cell.getStringCellValue().trim().toUpperCase());
					}
				}

			} else {
				Iterator<Cell> cellIterator = currentRow.iterator();
				List<FxMatrixDto> fxMatrixDtoList = null;
				String currencyName = null;
				String currencyRef = null;
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					if (cell.getColumnIndex() == 0) {
						currencyName = cell.getStringCellValue();
						fxMatrixDtoList = currencyMatixMap.get(currencyName.trim().toUpperCase());
					} else {
						currencyRef = cell.getStringCellValue();
						FxMatrixDto fxMatrixDto = new FxMatrixDto();
						fxMatrixDto.setCurrencyName(currSeqList.get(cell.getColumnIndex() - 1));
						fxMatrixDto.setCurrencyRef(currencyRef);

						fxMatrixDtoList.add(fxMatrixDto);
					}
				}

			}
		}

		
	}

	private void initRates(HashMap<String, BigDecimal> currencyRatesMap, Workbook workbook)
			throws IOException {

		Sheet sheetCurrMatrix = (Sheet) workbook.getSheet(FXConstants.SHEET_RATES);
		Iterator<Row> iterator = sheetCurrMatrix.iterator();

		while (iterator.hasNext()) {

			Row currentRow = iterator.next();
			if (currentRow.getRowNum() != 0) {
				Iterator<Cell> cellIterator = currentRow.iterator();
				String currencyNameMap = null;
				String currencyUnitValue = null;
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					if (cell.getColumnIndex() == 0) {
						currencyNameMap = cell.getStringCellValue();
					} else {
						DataFormatter dataFormatter = new DataFormatter(); 
						currencyUnitValue = dataFormatter.formatCellValue(cell);
					}

				}

				currencyRatesMap.put(currencyNameMap, new BigDecimal(currencyUnitValue));
			}

		}

	}

	private void initPrecision(HashMap<String, Integer> currencyPrecisionMap, Workbook workbook)
			throws IOException {

		Sheet sheetCurrMatrix = (Sheet) workbook.getSheet(FXConstants.SHEET_PRECISION);
		Iterator<Row> iterator = sheetCurrMatrix.iterator();

		while (iterator.hasNext()) {

			Row currentRow = iterator.next();
			if (currentRow.getRowNum() != 0) {
				Iterator<Cell> cellIterator = currentRow.iterator();
				String currencyName = null;
				String precisionValue = null;
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					if (cell.getColumnIndex() == 0) {
						currencyName = cell.getStringCellValue();
					} else {
						DataFormatter dataFormatter = new DataFormatter();
						precisionValue = dataFormatter.formatCellValue(cell);
					}

				}

				currencyPrecisionMap.put(currencyName, new Integer(precisionValue));
			}

		}
	}
}
