package com.task3.task3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;


@SpringBootApplication
public class Task3Application {


	public static void main(String[] args) {

		Connection conn;

		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/task3", "root", "");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		Scanner scLog;
		try {
			scLog = new Scanner(new File("D:\\job_projects\\task3\\src\\main\\resources\\logins.csv"));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}

		Scanner scPost;
		try {
			scPost = new Scanner(new File("D:\\job_projects\\task3\\src\\main\\resources\\postings.csv"));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}

		sendLogins(conn, scLog);
		sendPostings(conn, scPost);
		add(conn);

		SpringApplication.run(Task3Application.class, args);
	}

	public static void sendLogins(Connection conn, Scanner sc)  {

		sc.useDelimiter("\n");


		PreparedStatement delet;
		try {
			delet = conn.prepareStatement("DROP TABLE `task3`.`logins`");
			delet.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}


		PreparedStatement create;
		try {
			create = conn.prepareStatement("CREATE TABLE `task3`.`logins` (" +
					"  `idlogins` int unsigned NOT NULL AUTO_INCREMENT, " +
					"  `Application` varchar(100) DEFAULT NULL, " +
					"  `AppAccountName` varchar(45) DEFAULT NULL, " +
					"  `IsActive` boolean DEFAULT NULL, " +
					"  `JobTitle` varchar(45) DEFAULT NULL, " +
					"  `Department` varchar(50) DEFAULT NULL, " +
				"  PRIMARY KEY (`idlogins`) " +
					");");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		try {
			create.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		boolean alreadyCreated = true;

		while (sc.hasNext()) {
			String s = sc.next();
			if(alreadyCreated) { alreadyCreated= false; continue;}
			String[] row = s.split(",\t");

			try {
				PreparedStatement stmt = conn.prepareStatement("INSERT INTO logins (Application, AppAccountName, IsActive, JobTitle, Department) VALUES (?, ?, ?, ?, ?)");
				stmt.setString(1, row[0]);
				stmt.setString(2, row[1]);
				stmt.setBoolean(3, toBoolean(row[2]));
				stmt.setString(4, row[3]);
				stmt.setString(5, row[4]);

				stmt.executeUpdate();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}


		}
	}

	public static void sendPostings(Connection conn, Scanner sc)  {

		try {
			PreparedStatement delet = conn.prepareStatement("DROP TABLE `task3`.`posting`");
			delet.executeUpdate();
			PreparedStatement create = conn.prepareStatement("CREATE TABLE `task3`.`posting` ( " +
					"  idpostings INT UNSIGNED NOT NULL AUTO_INCREMENT, " +
					"  mat_Doc BIGINT(10) NULL, " +
					"  item INT NULL, " +
					"  doc_Date DATE NULL, " +
					"  pstngdate DATE NULL," +
					"  material_Description VARCHAR(100) NULL, " +
					"  quantity INT NULL," +
					"  bun VARCHAR(10) NULL, " +
					"  amountLC FLOAT NULL, " +
					"  crcy VARCHAR(3) NULL, " +
					"  username VARCHAR(45) NULL, " +
					"  PRIMARY KEY (idpostings));");
			create.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}





		boolean alreadyCreated = true;
		sc.useDelimiter("\n");
		while (sc.hasNext()) {
			String s = sc.next();
			if(s.equals("\r")) continue;
			String[] row = s.split(";\t");

			if(alreadyCreated) { alreadyCreated= false; continue;}
			try {



				PreparedStatement stmt = conn.prepareStatement("INSERT INTO posting (mat_Doc, item, doc_Date, pstngdate, material_Description, quantity, bun, amountLC, crcy, username) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
				stmt.setLong(1, Long.parseLong(row[0]));
				stmt.setInt(2, Integer.parseInt(row[1]));
				DateFormat format = new SimpleDateFormat("dd.MM.yyyy");

				java.util.Date utilDate;

				try {
					utilDate = format.parse(row[2]);
				} catch (ParseException e) {
					throw new RuntimeException(e);
				}
				java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
				stmt.setDate(3, sqlDate);


				try {
					utilDate = format.parse(row[3]);
				} catch (ParseException e) {
					throw new RuntimeException(e);
				}
				sqlDate = new java.sql.Date(utilDate.getTime());
				stmt.setDate(4, sqlDate);

				stmt.setString(5, row[4]);
				stmt.setInt(6, Math.abs( Integer.parseInt(row[5])));
				stmt.setString(7, row[6]);
				stmt.setDouble(8, Math.abs( Double.parseDouble(row[7].replace(",", "."))));
				stmt.setString(9, row[8]);
				stmt.setString(10, row[9]);
				stmt.executeUpdate();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}


		}
	}

	public static boolean toBoolean(String value) {
		if (value == null) {
			return false;
		}
		String trimmed = value.trim();
		if (trimmed.isEmpty()) {
			return false;
		}
		return switch (trimmed.toLowerCase()) {
			case "true", "yes", "1" -> true;
			default -> false;
		};
	}


	public static void add(Connection conn)  {
		try {
			PreparedStatement delet = conn.prepareStatement("ALTER TABLE `task3`.`posting` ADD COLUMN authdelivery BOOLEAN;");
			delet.executeUpdate();

			delet = conn.prepareStatement("UPDATE `task3`.`posting` set authdelivery = false;");
			delet.executeUpdate();

			delet = conn.prepareStatement("UPDATE `task3`.`posting` set username=trim(username);");
			delet.executeUpdate();

			delet = conn.prepareStatement("UPDATE `task3`.`posting` set username=trim(TRAILING '\\r' FROM username);");
			delet.executeUpdate();

			delet = conn.prepareStatement("UPDATE `task3`.`logins` set Department=trim(TRAILING '\\r' FROM Department);");
			delet.executeUpdate();

			delet = conn.prepareStatement("UPDATE `task3`.`logins` set AppAccountName=trim(AppAccountName);");
			delet.executeUpdate();

			PreparedStatement update = conn.prepareStatement("UPDATE `task3`.`posting`" +
					" SET authdelivery = true" +
					" WHERE username In (" +
					"  SELECT something.username FROM (SELECT `username` FROM posting " +
					"  INNER JOIN logins ON posting.username = logins.AppAccountName" +
					"  WHERE logins.IsActive = 1" +
					") AS `something`" +
					");");
			update.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
