package SQL;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SQL {

	static Connection conn;

	/**
	 * Used to connect to a database-server.
	 * @param hostname The ip of the DB-Server to connect to. Might be "localhost".
	 * @param port The port, the DB-Server runs on. Typically 3306 for MySQL and probably MariaDB.
	 * @param dbname The name of the database on which queries should run. In this case "data".
	 * @param user The username of the DB-Server. Standard is "root".
	 * @param password The password of the user. Standard is "".
	 * @return Boolean to indicate error (false) or success (true).
	 * @author finn
	 */
	public static boolean connect(String hostname, int port, String dbname, String user, String password) {
		try {
			Class.forName("org.gjt.mm.mysql.Driver").newInstance();
			String url = "jdbc:mysql://" + hostname + ":" + port + "/" + dbname;
			conn = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Used to disconnect from the database-server.
	 * @return Boolean to indicate error (false) or success (true).
	 * @author finn
	 */
	public static boolean disconnect() {
		try {
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Saves the coordinates of an obstacle to table `current`.
	 * @param X
	 * @param Y
	 * @return Boolean to indicate error (false) or success (true).
	 * @author finn
	 */
	public static boolean save(int X, int Y) {
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(
					"INSERT INTO `current` (`ID`, `TYPE`, `X`, `Y`, `TIMESTAMP`, `fetched`) VALUES (NULL, 'Sensor', '"
							+ X + "', '" + Y + "', CURRENT_TIMESTAMP, '0');");
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Saves the coordinates of an 3D obstacle to table `current3D`.
	 * @param X
	 * @param Y
	 * @param Z
	 * @return Boolean to indicate error (false) or success (true).
	 * @author finn
	 */
	public static boolean save(int X, int Y, int Z) {
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("INSERT INTO `current3D` (`ID`, `X`, `Y`, `Z`, `TIMESTAMP`, `fetched`) VALUES (NULL, '"
					+ X + "', '" + Y + "', '" + Z + "', CURRENT_TIMESTAMP, '0');");
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Saves the coordinates of a colored 3D obstacle to table `current3DColor`.
	 * @param X
	 * @param Y
	 * @param Z
	 * @param c awt.java object representing the color.
	 * @return Boolean to indicate error (false) or success (true).
	 * @author finn
	 */
	public static boolean save(int X, int Y, int Z, Color c) {
		try {
			int alpha = c.getAlpha();
			int red = c.getRed();
			int green = c.getGreen();
			int blue = c.getBlue();
			int color = (alpha << 24) | (red << 16) | (green << 8) | blue;
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(
					"INSERT INTO `current3DColor` (`ID`, `X`, `Y`, `Z`, `TIMESTAMP`, `FETCHED`, `COLOR`) VALUES (NULL, '"
							+ X + "', '" + Y + "', '" + Z + "', CURRENT_TIMESTAMP, '0', '" + color + "');");
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Saves the new position of the Robot to table `current`
	 * @param X
	 * @param Y
	 * @return Boolean to indicate error (false) or success (true).
	 * @author finn
	 */
	public static boolean updatePosition(int X, int Y) {
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(
					"INSERT INTO `current` (`ID`, `TYPE`, `X`, `Y`, `TIMESTAMP`, `FETCHED`) VALUES (NULL, 'Position', '"
							+ X + "', '" + Y + "', CURRENT_TIMESTAMP, '0');");
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Init/Reset all three tables. Deletes all previously saved data.
	 * @return
	 */
	public static boolean init() {
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("TRUNCATE `current`");
			stmt = conn.createStatement();
			stmt.executeUpdate("TRUNCATE `current3D`");
			stmt = conn.createStatement();
			stmt.executeUpdate("TRUNCATE `current3DColor`");
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
