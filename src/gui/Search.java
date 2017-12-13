package gui;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import db.MovieSQLite;
import util.ServletUtilities;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


/**
 *
 * <p>Title: Servlet that print out the documents annotations</p>
 *
 * @author Pedro Contreras<br/>
 * <p>
 * Department of Computer Science  <br>
 * Royal Holloway, University of London <br>
 * http://www.cs.rhul.ac.uk
 *
 */


public class Search extends HttpServlet {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * Constructor
     */
    public Search() {
    }

    /**
     * Implements the user interface form to capture the query and stemming
     * language
     *
     * @param  request HttpServletRequest
     * @param  response HttpServletResponse
     * @throws IOException
     * @throws ServletException
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException {
        
        Connection conn   = MovieSQLite.getConnection();
        Statement  st_mem = null;
        try
        {
            st_mem = conn.createStatement();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        String   webQuery = "";
        PrintWriter   out = response.getWriter();
   
        // Print the start of a HTML file
        ServletUtilities.HTMLStart(out, "Search");
        String fileFullPath = this.getServletContext().getRealPath( ServletUtilities.MENU_PATH);     
         
        fileFullPath = this.getServletContext().getRealPath("gui/search.html");
         
        ServletUtilities.PrintHTML(out, fileFullPath);
        //captures the query and language in the form
        webQuery = request.getParameter("query");
   
        //QueryH2 qu = new QueryH2();
        Query qu = new Query();
        
        long startTime = System.currentTimeMillis();
        if (webQuery != null) {
            
        	if (webQuery.startsWith("test")) {
        		util.ServletUtilities.HTMLMsgSearch(out, "THIS IS A TEST");
        	}
        	else if (webQuery.startsWith("a:"))
            {        	    
                webQuery = webQuery.substring(2, webQuery.length()).trim();
                util.ServletUtilities.HTMLMsgSearch(out, webQuery +" has featured on: ");
                qu.getMoviesFromActor(conn , st_mem, out,  webQuery);   
            }
        	else if (webQuery.startsWith("d:"))
            {
                webQuery = webQuery.substring(2, webQuery.length()).trim();
                util.ServletUtilities.HTMLMsgSearch(out, webQuery +" has directed: ");
                qu.getMoviesFromDirector(conn , st_mem, out,  webQuery);   
            }
        	
        	/** ------------------------------------------------------------*/
            else if (webQuery.startsWith("u:"))
            {
                webQuery = webQuery.substring(2, webQuery.length()).trim(); 
                util.ServletUtilities.HTMLMessage(out, webQuery +" has rated: ");
                qu.getRecommentedMovies(conn , st_mem, out,  webQuery);   
            } 
        	/** ------------------------------------------------------------*/
                       	
        }else {
            //query key no supported     
            System.out.println(webQuery);
        }
        long stopTime = System.currentTimeMillis();
        long runTime  = stopTime - startTime;
        util.ServletUtilities.HTMLMsgSearch(out, "\nRun time: " + runTime + " Milliseconds");
        
        // Print the end of a HTML file
        ServletUtilities.HTMLEnd(out); 
  
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws
        IOException, ServletException {
        doGet(request, response);
    }
    
    

}
