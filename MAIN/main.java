package MAIN;

import java.awt.Color;

public class main {

	public static void main(String[] args) {
		SQL.SQL.connect("localhost", 3306, "data", "root", "");
		SQL.SQL.init();
		SQL.SQL.save(10, 20);
		SQL.SQL.disconnect();
	}

}
