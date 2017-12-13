package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import util.DataFile;

/**
 * MovieSQLite.java
 *
 * @author Pedro Contreras. <br>
 * Copyright (c) 2013. All rights reserved. <br>
 * Project name: movies. <br>
 * Created on: 11 Aug 2013. <br>
 */
public class MovieSQLite
{
    public static String DB_CONNECTION = DataFile.DB_CONNECTION;
    public static String DB_LOCATION   = DataFile.DB_LOCATION;
 
    private static Connection inMemory = null;

    
    public static Connection getConnection()
    {
        return inMemory;
    }

    public static void setConnection(Connection conn)
    {
        MovieSQLite.inMemory = conn;
    }
    

    public static void loadToMemory() throws ClassNotFoundException{
        Class.forName("org.sqlite.JDBC"); 
        
        Connection mem    = null;
        setConnection(mem);
        try
        {
            Connection conn = DriverManager.getConnection(DB_CONNECTION);
        
            System.out.println("Loadling database into memory...");
            
            setConnection(DriverManager.getConnection("jdbc:sqlite::memory:"));
            Statement st_mem = getConnection().createStatement();
            st_mem.executeUpdate("restore from "+ DB_LOCATION);
            
            conn.close();
            System.out.println("database loaded");
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

}
