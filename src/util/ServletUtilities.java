package util;

import java.io.*;
import javax.servlet.http.*;

/**
 *
 * <p>Title: This has static methods used through the whole project</p>
 *
 * @author Pedro Contreras<br/>
 * <p>
 * Department of Computer Science  <br>
 * Royal Holloway, University of London <br>
 * http://www.cs.rhul.ac.uk
 *
 */
public class ServletUtilities {

    // menu.html path
    public static final String MENU_PATH = "gui/menu.html";

    // HTML DOCTYPE header
    public static final String DOCTYPE =  "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional //EN\">";

    /**
     * @brief Print out HTML document header
     *
     * @param out PrintWriter writer
     * @param title String  HTML document Title
     */
    public static void HTMLStart(PrintWriter out, String title) {
        out.println(DOCTYPE + "\n" +
                    "<HTML>\n" +
                    "<HEAD> \n " +
                    "   <META http-equiv=\"content-type\" content=\"text/html; charset=iso-8859-1\"> \n" +
                    "   <TITLE>" + title + "</TITLE> \n" +
                    "   <link rel=\"stylesheet\" type=\"text/css\" href=\"gui/default.css\">" +
                    "<style type=\"text/css\"> \n" +
                    "</style> \n " +
                    "<script>\n " +
                    "    function setFocus()\n " +
                    "    {document.getElementById(\"inquery\").focus();}\n " +
                    "</script>\n " +
                    "</HEAD>\n " +
                    "<BODY onload=\"setFocus()\"> \n");
    }

    /**
     * @brief  Print out the end of and HTML document
     *
     * @param out PrintWriter writer
     */
    public static void HTMLEnd(PrintWriter out) {
        out.println("</BODY> \n </HTML>");

    }


    /**
     * @brief Read a parameter with the specified name, convert it to an int,
     * and return it. Return the default value if the parameter
     * doesn't exist or if it is an illegal integer format.
     *
     * @param request HttpServletRequest
     * @param paramName String
     * @param defaultValue int
     * @return int
     */
    public static int getIntParameter(HttpServletRequest request,
                                      String paramName,
                                      int defaultValue) {
    	
        String paramString = request.getParameter(paramName);
        int paramValue;
        try {
            paramValue = Integer.parseInt(paramString);
        } catch (NumberFormatException nfe) { // Handles null and bad format
            paramValue = defaultValue;
        }
        return (paramValue);
    }

    /**
     * @brief This mehod get the value of a Cookie
     *
     * @param cookies Cookie[]
     * @param cookieName String
     * @return String
     */
    public static String getCookieValue(Cookie[] cookies,
                                        String cookieName) {
        for (int i = 0; i < cookies.length; i++) {
            Cookie cookie = cookies[i];
            if (cookieName.equals(cookie.getName())) {
                return (cookie.getValue());
            }
        }
        return (null);
    }

    /**
     *
     * @brief This method read a HTML file and print out the result
     *
     * @param out PrintWriter writer
     * @param filename String file to be printed out
     * @return boolean true if success
     */
    public static boolean PrintHTML(PrintWriter out, String filename) {

        try {
            FileInputStream fls = new FileInputStream(filename);
            BufferedReader in = new BufferedReader(new InputStreamReader(fls,
                    "UTF-8"));
            int cha;
            while ((cha = in.read()) >= 0) {
                out.write((char) cha);
            }

            in.close();

        } catch (Exception ex) {
            HTMLMessage(out, "Exception has been produced :" + ex);
            return (false);
        }

        return (true);
    }

    /**
     *
     * @brief This method print out a given message in HTML format
     *
     * @param out PrintWriter writer
     * @param message String message to print out in the browser
     */
    public static void HTMLMessageLogo(PrintWriter out, String message) {
        out.print(
                "<!DOCTYPE html PUBLIC \" - //W3C//DTD HTML 4.01 Transitional//EN \"> \n" +
                "<HTML><HEAD> \n" +
                "<META content=\"text/html; charset=iso-8859-1\" http-equiv=\"content-type\"> \n" +
                "<TITLE>Message</TITLE>" +
                "</HEAD><BODY> \n" +
                "<table style=\"width: 100%; height: 100px;\"> \n" +
                "<tbody><tr> \n" +
                "<td style=\"text-align: left;\"> \n" +
                "<div style=\"font-size: 15px; height: 100px;\">\n" +
                "<img src=\"gui/web.png\"></div> \n" +
                "</td></tr><tr><td><br><br> \n" +
                message +
                "<br></td></tr></tbody></table> \n" +
                "</BODY> \n </HTML>");
    }
    
    public static void HTMLMovieStart(PrintWriter out)
    {
        out.print(
                "<table align=\"center\">  \n" +
                "<tbody>  \n" +
                "    <td align=\"center\"> \n" +
                "        <div id=\"container\" style=\"width:610px\"; align=\"center\" > \n" +
                "            <div id=\"header\" style=\"background-color:#FFA500;\"> \n" +
                "                <hr size=1 > \n" +
                "            </div> \n");
        
    }
    public static void HTMLMovie(PrintWriter out, int mov_id, String title, int year) {
        out.print(      
                "           <div id=\"menu\" style=\"width:110px;height:170px; float:left;\"> \n" +
                "                <img src=\"../../movie_data/covers/"+mov_id+".jpg\" align=\"middle\" height=\"135\" width=\"95\"  style=\"hspace=2px\" > \n" +
                "            </div> \n" +
                "            <div id=\"content\" style=\"width:500px; height:170px; float:left;\"> \n" +
                "                <div id=\"title\"  align=\"left\" style=\"background-color:#B4DAFF;  height: 40px; margin-top: -25px;\"  > \n" +
                "                    <h4>Title: "+title+"</h4> \n" +
                "                </div> \n"+
                "                <div id=\"director\"  align=\"left\" style=\"border:1px ; height: 30px; margin-top: -10px\" > \n" +
                "                    <p> <b>Year: "+year+"</b></p> \n" +
                "                </div> \n");
    }
   
    
    public static void HTMLMovieActorsStart(PrintWriter out){
        out.print(
                "                <div id=\"actors\"  align=\"left\" style=\"border:1px ; height: 30px; margin-top: -25px\" > \n" +
                "                    <p> <b>Actors: </b>");
    }
    
    public static void HTMLMovieActor(PrintWriter out, String actor){
        out.print("<a href=\"http://localhost:8080/movies/Search?query=a:"+actor+"\">"+actor+"</a>");
    }
    
    public static void HTMLMovieActorsClose(PrintWriter out){
        out.print("                </p> \n" +
                  "                </div> \n");
    }
    
    
    public static void HTMLMovieGenreStart(PrintWriter out){
        out.print(
                "                <div id=\"Genre\"  align=\"left\" style=\"border:1px ; height: 30px; margin-top: -10px\" > \n" +
                "                    <p> <b>Genre: </b> ");
    }
    
    public static void HTMLMovieGenre(PrintWriter out, String genre){
        out.print(genre);
    }
    
    public static void HTMLMovieGenreClose(PrintWriter out){
        out.print("                </p> \n" +
                  "                </div> \n");
    }
    
    
    public static void HTMLMovieCountry(PrintWriter out, String country){
        out.print(
                "                <div id=\"country\"  align=\"left\" style=\"border:1px ; height: 30px; margin-top: -10px\" > \n" +
                "                    <p> <b>Country: </b>"+country+"</p> \n" +
                "                </div> \n");
    }
    
    public static void HTMLMovieDirector(PrintWriter out, String director){
        out.print(
                "                <div id=\"director\"  align=\"left\" style=\"border:1px ; height: 15px; margin-top: -25px\" > \n" +
                "                    <p> <b>Director: </b><a href=\"http://localhost:8080/movies/Search?query=d:"+director+"\">"+director+"</a></p> \n" +
                "                </div> \n");
    }
    
    public static void HTMLMovieClose(PrintWriter out)
    {
        out.print(
                "                </div> \n" +
                "              </div> \n" +
                "            </div> \n" +
                "        </div> \n" +
                "    </td> \n" +
                "</tbody> \n" +
                "</table>");
        
    }
    /**
     * @brief this prints out a resume of the movie information 
     * @param out
     * @param mov_id
     * @param title
     * @param year
     */
    public static void HTMLMovieShort(PrintWriter out, int mov_id, String title, int year) {
        out.print(
                "<table align=\"center\">  \n" +
                "<tbody>  \n" +
                "    <td align=\"center\"> \n" +
                "        <div id=\"container\" style=\"width:700px\"; align=\"center\" > \n" +
                "            <div id=\"header\" style=\"background-color:#FFA500;\"> \n" +
                "                <hr size=1 > \n" +
                "            </div> \n" +
                "           <div id=\"menu\" style=\"width:110px;height:150px; float:left;\"> \n" +
                "                <img src=\"../../movie_data/covers/"+mov_id+".jpg\" alt=\"movie\" align=\"middle\" height=\"135\" width=\"95\"  style=\"hspace=2px\" > \n" +
                "            </div> \n" +
                "            <div id=\"content\" style=\"width:590px; height:150px; float:left;\"> \n" +
                "                <div id=\"title\"  align=\"left\" style=\"background-color:#B4DAFF;  height: 40px; margin-top: -25px;\"  > \n" +
                "                    <h4> Title: "+title+"</h4> \n" +
                "                </div> \n" +
                "                <div id=\"year\" align=\"left\" style=\"border:1px; height: 30px; margin-top: -10px\" > \n" +
                "                    <p><b> Year: "+year+"</b></p> \n" +
                "                </div> \n" +
                "            </div> \n" +
                "        </div> \n" +
                "    </td> \n" +
                "</tbody> \n" +
                "</table>");
    }

    /**
     * @brief This method print out a simple message in HTML format
     *
     * @param out PrintWriter writer
     * @param message String  mesasage to print on the browser
     */
    public static void HTMLMessage(PrintWriter out, String message) {
        out.println("<small>" + message + "</small><br>");
    }
    
    public static void HTMLMsgSearch(PrintWriter out, String message) {
        
        out.println("<table align=\"center\">  \n" +
                    "<tbody align=\"left\">\n" +
                    "    <td align=\"left\"> \n"    + 
                    "       <b>"+ message + "</b>\n"+
                    "    </td>\n"+
                    "</tbody> \n"+
                    "</table>\n");
    }

    /**
     * @brief This method print out the end of a HTML table
     *
     * @param out PrintWriter
     * @param matches int
     * @param URL String
     */
    public static void HTMLLink(PrintWriter out, int matches, String URL) {
        out.print( matches + ".- <a href=\"" + URL + ".txt\">" + URL + "</a> <br>");
    }

    /**
     * @brief This method print out the end of a HTML table
     *
     * @param out PrintWriter
     * @param matches int
     * @param URL String
     */
    public static void HTMLLinkNoExt(PrintWriter out, int matches, String URL) {
        out.print( matches + ".- <a href=\"" + URL + "\">" + URL + "</a> <br>");
    }



    /**
     *
     * @brief This method strips the path from a filename
     *
     * @param str String
     * @return String
     */
    public static String stripPath(String str) {

        int index = -1;

        for (int i = str.length() - 1; i >= 0; i--) {
            if ((str.charAt(i) == '/') || (str.charAt(i) == '\\')) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            return (str);
        } else {
            return (str.substring(index + 1, str.length()));
        }

    }

    /**
     *
     * @brief This method get the path from a filename
     *
     * @param str String
     * @return String
     */
    public static String getPath(String str) {

        int index = -1;

        for (int i = str.length() - 1; i >= 0; i--) {
            if ((str.charAt(i) == '/') || (str.charAt(i) == '\\')) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            return ("");
        } else {
            return (str.substring(0, index+1));
        }
    }
}
