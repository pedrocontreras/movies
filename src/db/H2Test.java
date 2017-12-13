package db;

import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.ResultSet;  
import java.sql.Statement;  


/**
 * H2Test.java
 *
 * @author Pedro Contreras. <br>
 * Copyright (c) 2013. All rights reserved. <br>
 * Project name: movies. <br>
 * Created on: 10 Aug 2013. <br>
 */
public class H2Test
{

    public H2Test(){
        
    }
    /**
     * @param args
     */
    public static void main(String[] args) throws ClassNotFoundException
    {
        Connection connection = null;  
        ResultSet resultSet   = null;  
        Statement statement   = null;  
  
        try {  
            Class.forName("org.h2.Driver");  
            connection = DriverManager.getConnection("jdbc:h2:~/Downloads/MovieLens/database/movies", "guest", "guest");  
            statement  = connection.createStatement();  
            resultSet  = statement.executeQuery("SELECT TITLE FROM MOVIES");
            
            while (resultSet.next()) {  
                System.out.println("TITLE:"  + resultSet.getString("TITLE"));  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                resultSet.close();  
                statement.close();  
                connection.close();  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
        

    }

}
