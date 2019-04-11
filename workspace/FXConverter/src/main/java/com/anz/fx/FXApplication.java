package com.anz.fx;

import java.math.BigDecimal;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

import com.anz.fx.exception.CurrencyMappingException;
import com.anz.fx.util.Converter;
import com.anz.fx.util.FXConstants;

@ComponentScan
@SpringBootApplication
@EnableAutoConfiguration
public class FXApplication implements CommandLineRunner {
	
    private static final Logger LOGGER = LoggerFactory.getLogger(FXApplication.class);

	@Autowired
	private Converter converter;
	
	public static void main(String[] args) {
		
		new SpringApplicationBuilder(FXApplication.class).web(false).run(args);
	}

	public void run(String... args) throws Exception {

		LOGGER.info("Starting the FXConverter app");
		System.out.println(System.getProperty("line.separator"));
		System.out.println("-----------------------------------");
		System.out.println("Welcome to FXConverter App ");
		System.out.println("Type EXIT to exit from app");
		System.out.println("-----------------------------------");
		System.out.println(System.getProperty("line.separator"));
		
		
		Scanner scanner = new Scanner(System.in);
		String input = scanner.nextLine();
		while(!FXConstants.EXIT.equalsIgnoreCase(input)) {
			try {
				input = input.trim().replaceAll("( )+", " ");
				String[] inputArray = input.split(" ");
				BigDecimal convertedValue = converter.processConversion(inputArray[0], inputArray[1], inputArray[3]);
				System.out.println(inputArray[0] + " " + inputArray[1] + " = " + inputArray[3] + " " + convertedValue);
			} catch(CurrencyMappingException e) {
				System.out.println(e.getMessage());
				LOGGER.info(e.getMessage());
			} catch(Exception e) {
				System.out.println("Error while processing request for :" + input + " Please try again");
				LOGGER.info("Error while processing request for :" + input + " Please try again", e);
			}
			input = scanner.nextLine();
		}
		
		scanner.close();
		System.exit(0);
		System.out.println("EXITING the FXConverter app");
		LOGGER.info("EXITING the FXConverter app");
	}
}
