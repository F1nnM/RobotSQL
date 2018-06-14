package MAIN;

import SQL.SQL;

public class main {

	public static void main(String[] args) {
		SQL.setErrorReporting(SQL.ERROR_PRINT);
		boolean success = true;
		success = success & SQL.connect("localhost", 3306, "data", "root", "");
		success = success & SQL.init();
		success = success & SQL.disconnect();
		System.out.println(success);
	}

}
