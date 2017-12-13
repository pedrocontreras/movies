package db.locator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RetrieveTest
{
    
  public static void main(String[] args) throws ClassNotFoundException
  {
    // load the sqlite-JDBC driver using the current class loader
    //Class.forName("org.sqlite.JDBC");

    Connection connection = null;
    try
    {
      // create a database connection
      connection = DriverManager.getConnection("jdbc:sqlite:/Applications/tomcat/webapps/movie_data/database/movies.sqlite3");
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(10);  // set timeout to 30 sec.
      int count = 1;
      ResultSet rs = statement.executeQuery("select title, year from movies limit 5;");
      while(rs.next())
      {
        System.out.print(count +") "+rs.getString("title")+ " ");
        System.out.print(rs.getInt("year"));
        System.out.println();
        
        if(rs.last()){
            System.out.print("THIS IS LAST");
        }
        
        count++;
      }
    }
    catch(SQLException e)
    {
      System.err.println(e.getMessage());
    }
    finally
    {
      try
      {
        if(connection != null)
          connection.close();
      }
      catch(SQLException e)
      {
        // connection close failed.
        System.err.println(e);
      }
    }
  }
  
}