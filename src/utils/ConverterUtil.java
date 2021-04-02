package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConverterUtil {
	public static boolean containsIllegals(String toExamine) {
//	    Pattern pattern = Pattern.compile("[~#@*+%{}<>\\[\\]|\"\\_^]");
	    Pattern pattern = Pattern.compile("[\\/:*?\"<>|]");
	    Matcher matcher = pattern.matcher(toExamine);
	    return matcher.find();
	}
	public static void main(String[] args) {
		System.out.println(containsIllegals("hola"));
		
	}

}
