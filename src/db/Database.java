package db;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Sqlite.java
 *
 * @author Pedro Contreras. <br>
 * Copyright (c) 2013. All rights reserved. <br>
 * Project name: Movie. <br>
 * Created on: 25 Apr 2013. <br>
 */
public class Database {
	
	public void create(String dbLocation) throws ClassNotFoundException 
	{
				
		Connection     conn   = null;
		DBTransactions aud    = new DBTransactions();
		String         schema = aud.schema(); //1 = multiple tables; 2: most data into movie table
    
	    try{
	      // create a database connection
	    	conn = DriverManager.getConnection("jdbc:sqlite:"+dbLocation);
	    	Statement statement = conn.createStatement();
	    	statement.setQueryTimeout(5);  // set timeout to 30 sec.

	    	statement.executeUpdate("drop table if exists MOVIES"); 
	    	statement.executeUpdate(schema);
	    	System.out.println("Created DB: "+ schema);
	    	statement.close();
	    }catch(SQLException e){
	    	close(conn);
	    	System.out.println(e.getMessage());
	    }
	    
	    close(conn);
	}
	
	/**
	 * 
	 * @param dblocation
	 * @return
	 * @throws ClassNotFoundException
	 */
	public Connection open(String dblocation) throws ClassNotFoundException 
	{		
		Class.forName("org.sqlite.JDBC");
		Connection connection = null;
		File dbloc = new File(dblocation);

		if(!dbExist(dbloc)){			
			String parentFol = parentFolder(dbloc);
			File dbFolder = new File(parentFol);			
			if (!folderExit(dbFolder))
			{
				createFolder(parentFol);
			}
			create(dblocation);
		}
		
		try{
			connection = DriverManager.getConnection("jdbc:sqlite:"+dblocation);
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("DB connection open: "+ dblocation);
		return connection;		
	}
	
	
	
	
	public Connection openInMem(String dblocation) throws ClassNotFoundException 
	{		
		Class.forName("org.sqlite.JDBC");
		Connection connection = null;
		File dbloc = new File(dblocation);

		if(!dbExist(dbloc)){			
			String parentFol = parentFolder(dbloc);
			File dbFolder = new File(parentFol);
			
			if (!folderExit(dbFolder)){
				createFolder(parentFol);
			}
			create(dblocation);
		}
		
		try{
			connection = DriverManager.getConnection("jdbc:sqlite:"+dblocation);
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("DB connection open: "+ dblocation);
		return connection;		
	}
	
	
	/**
	 * 
	 * @param connection
	 * @throws ClassNotFoundException
	 */
	public void close(Connection connection) throws ClassNotFoundException
	{
		try{
	        if(connection != null){
	          connection.close();
	          System.out.println("DB connection close");
	        }
	    }catch(SQLException e){
	    	System.out.println("SQLException closing connection"+e);
	    }
		
	}
	
	/********************************************************
	 *                 Support functions                    * 
	 ********************************************************/
	
	/**
	 * 
	 * @param folder
	 * @return
	 */
	public boolean folderExit(File folder)
	{
		if (folder.exists())
		{  			
			System.out.println("Folder exists");
			return true;
		} else{  
			System.out.println("Folder does not exists");
			return false;
		}
	}  
	
	public boolean dbExist(File db)
	{
		if (db.exists())
		{  			
			//System.out.println("DB file exists");
			return true;
		} else{  
			System.out.println("DB file does not exists");
			return false;
		}
		
	}
	
	/**
	 * 
	 * @param folder
	 */
	public void createFolder(String folder)
	{
		File path = new File(folder);
		if (!path.exists())
		{
			System.out.println("Creating directory: " + path);
			path.mkdirs();
		}else{
			System.out.println("createFolder: Directory exist it won't be created.. ");
		}
		
	}
	
	/**
	 * 
	 * @param path
	 * @return
	 */
	public String parentFolder(File path)
	{
		String parent = path.getParent();
		parent.substring(parent.lastIndexOf("\\")+1,parent.length());
		
		return parent;
	}
	
	
	
}
