package br.com.compasso.clientes.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {
	
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	public static String converteLocalDateToString(LocalDate localDate) {
		return localDate.format(formatter);
	}

	public static LocalDate converteStringToLocalDate(String date) {
		return LocalDate.parse(date, formatter);
	}
	
	private DateUtils() {}
	
}
