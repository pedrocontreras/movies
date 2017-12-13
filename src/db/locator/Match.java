package db.locator;

import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;

import util.DataFile;
import db.Database;


/**
 *
 * <p>Title: Servlet that print out the documents annotations</p>
 *
 * @author Pedro Contreras <br/>
 * <p>
 * Department of Computer Science  <br/>
 * Royal Holloway, University of London <br/>
 * http://www.cs.rhul.ac.uk
 * <p>
 *
 */
public class Match {
    
    public Match() {
    }
    
    public ArrayList<String[]> ArrListRecords = new ArrayList<String[]>();
    static final   String dblocation   = DataFile.DB_DEPLOYED;
    
    /**
     * 
     * @param webQuery
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public  ArrayList<String[]>  getUserMovie(String webQuery) throws SQLException, ClassNotFoundException {
    
        String[] row    = new String[2];
        String   sql    = "SELECT TITLE, YEAR FROM MOVIES WHERE MOVIE_ID ="+webQuery+";)";        
        Database db     = new Database();
        
        Class.forName("org.sqlite.JDBC");       
        
        Connection conn = db.open(dblocation);
        
        try {
            
            Statement stmt = conn.createStatement();
            stmt.setQueryTimeout(3);
                 
            ResultSet rs = stmt.executeQuery(sql);
                          
            while(rs.next()){
                row[0] =  rs.getString("title");
                row[1] =  Integer.toString(rs.getInt("year"));
                ArrListRecords.add(row);
            }            
            stmt.close();
                
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally
        {
            try{
                if(conn != null){
                    System.out.println("DB connection close");
                    conn.close();
                }
            }catch(SQLException e){
                System.err.println(e);
            }
        }
        return ArrListRecords;
    }
    
    
    /**
     * clear the array list when we are done with it
     */
    public void clearArrayList(){
          ArrListRecords.clear();     
      }

    
     public static void main(String[] args) throws SQLException, ClassNotFoundException {
         
         long startTime = System.currentTimeMillis();
         ArrayList<String[]> arrListMatch = new ArrayList<String[]>();
         
         Match m      = new Match();
         arrListMatch = m.getUserMovie("5");
         System.out.println("result array size: "+arrListMatch.size());
         
         for (int i = 0; i < arrListMatch.size(); i++) {
             System.out.println(arrListMatch.get(i)[0]);
             System.out.println(arrListMatch.get(i)[1]);
         }
         arrListMatch.clear();
         m.clearArrayList();
         
         //This measure the time that takes the whole query
         long stopTime = System.currentTimeMillis();
         long runTime = stopTime - startTime;
         //print put the run time
         System.out.println("Run time: " + (runTime) + " Milliseconds");

     }
    
}
