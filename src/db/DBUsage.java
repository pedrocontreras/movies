package db;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import util.DataFile;
import data.ReadFile;

/**
 * Sqlite.java
 *
 * @author Pedro Contreras. <br>
 * Copyright (c) 2013. All rights reserved. <br>
 * Project name: MOVIE. <br>
 * Created on: 25 Apr 2013. <br>
 */


public class DBUsage
{ 
	static final String DBLOCATION      = DataFile.DBLOCATION;	

	static final File   MOVIE_ACTORS    = DataFile.MOVIE_ACTORS;
	static final File   MOVIE_COUNTRIES = DataFile.MOVIE_COUNTRIES;
	static final File   MOVIE_DIRECTORS = DataFile.MOVIE_DIRECTORS;
	static final File   MOVIE_GENRES    = DataFile.MOVIE_GENRES;
	static final File   MOVIES          = DataFile.MOVIES;
	static final File   RATINGS_REIDX   = DataFile.RATINGS_REIDX;

	static final String SEPARATOR       = "\t";
	/**
	 * ---------------------------------------------------------------------------------------
	 * These methods here are to populate the database
	 * --------------------------------------------------------------------------------------- 
	 */

	/**
	 * 
	 * @param conn
	 * @param stmt
	 */
	public static void populateMovies(Connection conn, Statement stmt){
		DBTransactions  mdb  = new DBTransactions();

		System.out.println("Populating MOVIES table");

		ArrayList<String[]> arrListRecords = new ArrayList<String[]> ();
		ReadFile rf = new ReadFile();
		arrListRecords = rf.toArrayList(MOVIES, 21, SEPARATOR);

		int movie_id, year = 0;
		String title, picture = "";
		for (int i = 1; i < arrListRecords.size(); i++) {
			movie_id = Integer.parseInt(arrListRecords.get(i)[0]);
			title    = arrListRecords.get(i)[1];
			picture  = arrListRecords.get(i)[4];
			year     = Integer.parseInt(arrListRecords.get(i)[5]);
			mdb.addMovie(conn, stmt, movie_id, title, picture, year);
		}

		arrListRecords.clear();
		rf.clearArrayList();
		System.out.println("Finish!");
	}

	/**
	 * 
	 * @param conn
	 * @param stmt
	 */
	public static void populateRating(Connection conn, Statement stmt){
		DBTransactions  mdb  = new DBTransactions();

		System.out.println("Populating RATING table");

		ArrayList<String[]> arrListRecords = new ArrayList<String[]> ();
		ReadFile rf = new ReadFile();
		arrListRecords = rf.toArrayList(RATINGS_REIDX, 5, SEPARATOR);

		int user_id, user_id_idx, movie_id, movie_id_idx = 0;
		String rating = "";

		for (int i = 0; i < arrListRecords.size(); i++) {
			user_id      = Integer.parseInt(arrListRecords.get(i)[0]);
			user_id_idx  = Integer.parseInt(arrListRecords.get(i)[1]);
			movie_id     = Integer.parseInt(arrListRecords.get(i)[2]);
			movie_id_idx = Integer.parseInt(arrListRecords.get(i)[3]);
			rating       = arrListRecords.get(i)[4];
			mdb.addRating(conn, stmt, user_id, user_id_idx, movie_id, movie_id_idx, rating);
		}

		arrListRecords.clear();
		rf.clearArrayList();
		System.out.println("Finish!");
	}





	/**
	 * 
	 * @param conn
	 * @param stmt
	 */
	public static void populateActors(Connection conn, Statement stmt){
		DBTransactions  mdb  = new DBTransactions();

		System.out.println("Populating ACTORS table");

		ArrayList<String[]> arrListRecords = new ArrayList<String[]> ();
		ReadFile rf = new ReadFile();
		arrListRecords = rf.toArrayList(MOVIE_ACTORS, 4, SEPARATOR);

		int movie_id = 0;
		String field = "";

		for (int i = 1; i < arrListRecords.size(); i++) {
			movie_id = Integer.parseInt(arrListRecords.get(i)[0]);
			field    = arrListRecords.get(i)[2];
			mdb.addGenericRecord(conn, stmt, 1, movie_id, field);
		}

		arrListRecords.clear();
		rf.clearArrayList();
		System.out.println("Finish!");
	}

	/**
	 * @brief notice that first we need to add the movies then we can add the countries
	 *        to the database     
	 * 
	 * @param conn
	 * @param stmt
	 */
	public static void populateCountries(Connection conn, Statement stmt){
		DBTransactions  mdb  = new DBTransactions();

		System.out.println("Adding countries to the MOVIES table");

		ArrayList<String[]> arrListRecords = new ArrayList<String[]> ();
		ReadFile rf = new ReadFile();
		arrListRecords = rf.toArrayList(MOVIE_COUNTRIES, 2, SEPARATOR);

		int movie_id = 0;
		String field = "";
		for (int i = 1; i < arrListRecords.size(); i++) {
			movie_id = Integer.parseInt(arrListRecords.get(i)[0]);
			field    = arrListRecords.get(i)[1];
			mdb.addGenericRecord(conn, stmt, 2, movie_id, field);
		}

		arrListRecords.clear();
		rf.clearArrayList();
		System.out.println("Finish!");
	}

	/**
	 * @brief notice that first we need to add the movies then we can add the countries
	 *        to the database
	 * 
	 * @param conn
	 * @param stmt
	 */
	public static void populateDirectors(Connection conn, Statement stmt){
		DBTransactions  mdb  = new DBTransactions();

		System.out.println("Adding directors to the MOVIES table");

		ArrayList<String[]> arrListRecords = new ArrayList<String[]> ();
		ReadFile rf = new ReadFile();
		arrListRecords = rf.toArrayList(MOVIE_DIRECTORS, 3, SEPARATOR);

		int movie_id = 0;
		String field = "";
		for (int i = 1; i < arrListRecords.size(); i++) {
			movie_id = Integer.parseInt(arrListRecords.get(i)[0]);
			field    = arrListRecords.get(i)[2];
			mdb.addGenericRecord(conn, stmt, 3, movie_id, field);
		}

		arrListRecords.clear();
		rf.clearArrayList();
		System.out.println("Finish!");
	}

	/**
	 * 
	 * @param conn
	 * @param stmt
	 */
	public static void populateGenres(Connection conn, Statement stmt){
		DBTransactions  mdb  = new DBTransactions();

		System.out.println("Populating GENRES table");

		ArrayList<String[]> arrListRecords = new ArrayList<String[]> ();
		ReadFile rf = new ReadFile();
		arrListRecords = rf.toArrayList(MOVIE_GENRES, 2, SEPARATOR);

		int movie_id = 0;
		String field = "";
		for (int i = 1; i < arrListRecords.size(); i++) {
			movie_id = Integer.parseInt(arrListRecords.get(i)[0]);
			field    = arrListRecords.get(i)[1];
			mdb.addGenericRecord(conn, stmt, 4, movie_id, field);
		}

		arrListRecords.clear();
		rf.clearArrayList();
		System.out.println("Finish!");
	}
	/**
	 * ---------------------------------------------------------------------------------------
	 * end populate
	 * --------------------------------------------------------------------------------------- 
	 */

	public static void removeAllMovies(Connection conn, Statement stmt){
		DBTransactions  mdb  = new DBTransactions();
		mdb.removeAllMovies(conn, stmt);

	}



	/**
	 * @brief run  rebuild all the SQLITE database
	 * @param conn
	 * @param stmt
	 * @throws ClassNotFoundException
	 */
	public static void run(Connection conn, Statement stmt) throws ClassNotFoundException
	{
	    
	    //populateMovies   (conn, stmt);
		//populateActors   (conn, stmt);
		//populateGenres   (conn, stmt);
		//populateDirectors(conn, stmt);
		//populateCountries(conn, stmt);
		populateRating   (conn, stmt);
		//removeAllMovies  (conn, stmt);


	}

	public static void main(String[] args) throws ClassNotFoundException
	{
		Database   db   = new Database();
		Connection conn = db.open(DBLOCATION);

		try {
			Statement stmt = conn.createStatement();
			run(conn, stmt);
			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try{
				if(conn != null){
					System.out.println("DB connection close");
					conn.close();
				}
			}catch(SQLException e){
				System.err.println(e);
			}
		}
	}



}
