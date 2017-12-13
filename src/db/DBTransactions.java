package db;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;



/**
 * DBTransactions.java  core method to use SQLITE database
 *
 * @author Pedro Contreras. <br>
 * Copyright (c) 2013. All rights reserved. <br>
 * Project name: Movie. <br>
 * Created on: 1 May 2013. <br>
 */
public class DBTransactions {
	public ArrayList<String[]> ArrListRecords = new ArrayList<String[]>();

	/**
	 * 
	 * @return
	 */
	public String schema()
	{
		String schema = "";

		schema  = "create table ACTORS      (MOVIE_ID INT, ACTOR_NAME   TEXT); "
				+ "create table GENRES      (MOVIE_ID INT, GENRE        TEXT); "
				+ "create table MOVIES      (MOVIE_ID INT, TITLE        TEXT, PICTURE TEXT, YEAR         INT, DIRECTOR_NAME TEXT, COUNTRY TEXT);"
				+ "create table RATINGS     (USER_ID  INT, USER_SEQ_IDX INT,  MOVIE_ID INT, MOVIE_SEQ_IDX INT, RATING       TEXT);"
				+ "create table SIMILARITY  (USER_SEQ_IDX_1 INT, USER_SEQ_IDX_2 INT,  SIMILARITY INT, DISSIMILARITY INT);"
				+ "create table USER_CLUSTER(USER_SEQ_IDX INT, RND_PROJECTION  REAL);"
				+ "PRAGMA CACHE_SIZE = 8000; ";

		return schema;
	}


	/**
	 * 
	 * @param conn
	 * @param table
	 * @param movie_id
	 * @param content
	 */
	public void addGenericRecord(Connection conn, Statement statement, int table, int movie_id, String content){
		content = content.replace("'", "''");
		String sql = "";

		if(table == 1 ){
			sql = "INSERT INTO ACTORS(MOVIE_ID, ACTOR_NAME) Values ("+movie_id+", '"+content+"');";    
		}else if(table == 2 ){
			sql = "UPDATE MOVIES SET COUNTRY = '"+content+"' WHERE movie_id = "+movie_id+";";
		}else if(table == 3){
			sql = "UPDATE MOVIES SET DIRECTOR_NAME = '"+content+"' WHERE movie_id = "+movie_id+";";
		}else if(table == 4){
			sql = "INSERT INTO GENRES(MOVIE_ID, GENRE) Values ("+movie_id+", '"+content+"');";
		}
		try{
			statement.setQueryTimeout(30);
			//System.out.println("execute: "+ sql);
			statement.executeUpdate(sql);
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}

		//System.out.println("record added:"+ sql);	
	}


	/**
	 * @brief add record to the movie table
	 * 
	 * @param conn
	 * @param statement
	 * @param movie_id
	 * @param title
	 * @param picture
	 * @param year
	 */
	public void addMovie(Connection conn, Statement statement, int movie_id, 
			String title, String picture, int year){
		title = title.replace("'", "''");
		String sql = "INSERT INTO MOVIES(MOVIE_ID, TITLE, PICTURE, YEAR) Values ("
				+ ""+movie_id+", '"+title+"', '"+picture+"', "+year+");";

		try{
			statement.setQueryTimeout(30);
			statement.executeUpdate(sql);

		}catch(SQLException e){
			System.out.println(e.getMessage());
		}

		//System.out.println("record added:"+ sql); 
	}

	/**
	 * 
	 * @param conn
	 * @param statement
	 * @param user_id
	 * @param user_id_idx
	 * @param movie_id
	 * @param movie_id_idx
	 * @param rating
	 */
	public void addRating(Connection conn, Statement statement, int user_id, 
			int user_id_idx, int movie_id, int movie_id_idx, String rating){

		String sql = "INSERT INTO RATINGS(USER_ID, USER_SEQ_IDX, MOVIE_ID, MOVIE_SEQ_IDX, RATING) Values ("
				+ ""+user_id+", '"+user_id_idx+"', '"+movie_id+"', "+movie_id_idx+", "+ rating+");";

		try{
			statement.setQueryTimeout(30);
			statement.executeUpdate(sql);

		}catch(SQLException e){
			System.out.println(e.getMessage());
		}
	}

	/**
	 * 
	 * @param conn
	 * @param statement
	 */
	public void removeAllMovies(Connection conn, Statement statement)
	{
		try{
			statement.setQueryTimeout(30);
			statement.executeUpdate("DELETE FROM MOVIES;");

		}catch(SQLException e){
			System.out.println(e.getMessage());
		}

	}

	/**
	 * If I got any time I should replace the seach.java to get data from DB
	 * put that in an array,  then populate the site.
	 * @param movie_id
	 * @return
	 */
	public ArrayList<String[]> getMovie(Connection conn, Statement statement, int movie_id){

		String[] row = new String[4];
		String   sql = "SELECT LITLE, YEAR, DIRECTOR_NAME, COUNTRY FROM MOVIES WHERE MOVIE_ID ="+movie_id+";)";
		try{
			statement.setQueryTimeout(3);  
			ResultSet rs = statement.executeQuery(sql);

			while(rs.next()){
				System.out.println("title = "+ rs.getString("title"));
				System.out.println("year = " + rs.getInt("year"));
				row[0] = rs.getString("title");
				row[1] = Integer.toString(rs.getInt("year"));
				row[2] = rs.getString("director_name");
				row[3] = rs.getString("country");

				ArrListRecords.add(row);
			}
			rs.close();
		}
		catch(SQLException e)
		{
			System.err.println(e.getMessage());
		}	

		return ArrListRecords;
	}


	/**
	 * clear the array list when we are done with it
	 */
	public void clearArrayList(){
		ArrListRecords.clear();     
	}

}
