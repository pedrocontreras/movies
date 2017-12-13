package gui;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;



/**
 * Query.java
 *
 * @author Pedro Contreras. <br>
 * Copyright (c) 2013. All rights reserved. <br>
 * Project name: movies. <br>
 * Created on: 18 Jul 2013. <br>
 */
public class Query
{
    public Query(){
    }
    
      
    
    /**
     * @brief here we do the queries to find the closest user and the recommended movies
     * 
     * @param out
     * @param user_id
     */
    public void getRecommentedMovies(Connection conn, Statement  stmt, PrintWriter out, String user_id)
    {
        int uid = Integer.parseInt(user_id);
        if( uid<1 || uid > 2113){
            util.ServletUtilities.HTMLMessage(out, "Value out of range: Value should be in the range [1, 2113]");
            return;
        }
        
        int    user_seq_idx = 0;
        String rdm_projection = "";
        String sql_rnd_pojection ="SELECT RND_PROJECTION FROM USER_CLUSTER WHERE USER_SEQ_IDX ="+user_id+";"; 
        
        ArrayList<Integer> arrListMovieId = new ArrayList<Integer> ();
       
        try {
            
            ResultSet rs = stmt.executeQuery(sql_rnd_pojection);            
            rdm_projection = Double.toString(rs.getDouble("RND_PROJECTION"));
            
            String sql_closest_user = "SELECT USER_SEQ_IDX FROM USER_CLUSTER ORDER BY ABS(RND_PROJECTION - "+rdm_projection+") LIMIT 2;";
        
            rs = stmt.executeQuery(sql_closest_user);
            rs.next();rs.next();
            user_seq_idx = rs.getInt("USER_SEQ_IDX");
            
            String sql  = "SELECT  U1.MOVIE_ID, U1.RATING "
                        + "FROM (SELECT * FROM RATINGS  WHERE USER_SEQ_IDX ="+user_seq_idx+") U1  "
                        + "LEFT JOIN (SELECT * FROM RATINGS  WHERE USER_SEQ_IDX = "+user_id+") "
                        + "U2 ON U1.MOVIE_SEQ_IDX = U2.MOVIE_SEQ_IDX "
                        + "WHERE  U2.MOVIE_SEQ_IDX IS NULL ORDER BY U1.RATING DESC LIMIT 25;";
            
            rs = stmt.executeQuery(sql);
            
            int mov_id = 0;
            while(rs.next()){
                mov_id = rs.getInt("U1.MOVIE_ID");
                arrListMovieId.add(mov_id);
            }
            
            
            for (int i = 0; i <arrListMovieId.size(); i++){
                String val = arrListMovieId.get(i).toString();
                printMovie(conn,  stmt, out,  val  );
            }
            arrListMovieId.clear();
            
            if(stmt != null){
                stmt.close();
            }
                    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    
 
    
    
    /** 
     * @brief  retrieves additional information from one movie 
     * @param out
     * @param webQuery
     */
    public void printMovie(Connection conn, Statement  stmt, PrintWriter out, String webQuery){
        
        String   sqlMovie    = "SELECT MOVIE_ID, TITLE, YEAR FROM MOVIES WHERE MOVIE_ID ="+webQuery+" ORDER BY YEAR DESC LIMIT 10;";
        String   sqlDirector = "SELECT DIRECTOR_NAME FROM MOVIES WHERE MOVIE_ID ="+webQuery+";";
        String   sqlCountry  = "SELECT COUNTRY FROM MOVIES WHERE MOVIE_ID ="+webQuery+";";
        String   sqlActors   = "SELECT ACTOR_NAME FROM ACTORS WHERE MOVIE_ID ="+webQuery+" ORDER BY ACTOR_NAME ASC LIMIT 7;";
        String   sqlGenres   = "SELECT GENRE FROM GENRES WHERE MOVIE_ID ="+webQuery+" ORDER BY GENRE ASC LIMIT 5;";
       
        try {
            stmt = conn.createStatement();
            stmt.setQueryTimeout(30);
            ResultSet rs = null;     
            
            util.ServletUtilities.HTMLMovieStart(out);
            rs = stmt.executeQuery(sqlMovie);
            while(rs.next()){
                util.ServletUtilities.HTMLMovie(out,rs.getInt("movie_id"),rs.getString("title"), rs.getInt("year")); 
            }
            
            rs = stmt.executeQuery(sqlDirector);                    
            while(rs.next()){
                util.ServletUtilities.HTMLMovieDirector(out,rs.getString("director_name")); 
            }
            
            rs = stmt.executeQuery(sqlCountry);                    
            while(rs.next()){
                util.ServletUtilities.HTMLMovieCountry(out,rs.getString("country")); 
            }
            
            rs = stmt.executeQuery(sqlActors);
            util.ServletUtilities.HTMLMovieActorsStart(out);
            while(rs.next()){
            		
            	util.ServletUtilities.HTMLMovieActor(out,rs.getString("actor_name"));
            	out.print(", ");
            	
            }
            util.ServletUtilities.HTMLMovieActorsClose(out);
            
            rs = stmt.executeQuery(sqlGenres);
            util.ServletUtilities.HTMLMovieGenreStart(out);
            while(rs.next()){
                util.ServletUtilities.HTMLMovieGenre(out,rs.getString("genre"));
            	out.print(", ");
            }
            util.ServletUtilities.HTMLMovieGenreClose(out);
                        
            util.ServletUtilities.HTMLMovieClose(out);

            stmt.close();
                    
        } catch (SQLException e) {
            e.printStackTrace();
        }
       
    }
    
    /**
     * 
     * @param conn
     * @param stmt
     * @param out
     * @param actor_name
     */
    public void getMoviesFromActor(Connection conn, Statement  stmt, PrintWriter out, String actor_name)
    {
       
        String   sqlMoviesFromActor   = "SELECT MOVIE_ID FROM ACTORS WHERE ACTOR_NAME LIKE '"+actor_name+"';";
        
        ArrayList<Integer> arrListMovieId = new ArrayList<Integer> ();

        try {
            
            ResultSet rs = stmt.executeQuery(sqlMoviesFromActor);            
            
            int mov_id = 0;
            while(rs.next())
            {
                mov_id = rs.getInt("MOVIE_ID");
                arrListMovieId.add(mov_id);
            }
            
            for (int i = 0; i <arrListMovieId.size(); i++)
            {
                String val = arrListMovieId.get(i).toString();
                printMovie(conn,  stmt, out,  val  );
            }
            
            arrListMovieId.clear();
            
            if(stmt != null){ stmt.close(); }
                    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void getMoviesFromDirector(Connection conn, Statement  stmt, PrintWriter out, String director_name)
    {
       
        String   sqlMoviesFromActor   = "SELECT MOVIE_ID FROM MOVIES WHERE DIRECTOR_NAME LIKE '"+director_name+"' ORDER BY YEAR DESC;";
        
        ArrayList<Integer> arrListMovieId = new ArrayList<Integer> ();

        try {
            
            ResultSet rs = stmt.executeQuery(sqlMoviesFromActor);            
            
            int mov_id = 0;
            while(rs.next())
            {
                mov_id = rs.getInt("MOVIE_ID");
                arrListMovieId.add(mov_id);
            }
            
            for (int i = 0; i <arrListMovieId.size(); i++)
            {
                String val = arrListMovieId.get(i).toString();
                printMovie(conn,  stmt, out,  val  );
            }
            
            arrListMovieId.clear();
            
            if(stmt != null){ stmt.close(); }
                    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
