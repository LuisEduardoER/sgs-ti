package main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class asdfadsf {
	public static void main(String[] args) {
		GregorianCalendar gc = new GregorianCalendar();
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			Date data = sdf.parse("22/12/2000 12:33");
			
			System.out.println(data.toString());
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
