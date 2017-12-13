package util;

import java.io.File;

/**
 * Global.java  CONSTANSTS USED ACROSS THE WHOLE APPLICATION
 *
 * @author Pedro Contreras
 */
public interface DataFile {
	
	static String DBLOCATION        = "/Users/pedro/Downloads/MovieLens/database/movies.sqlite3";
	static String DB_DEPLOYED       = "/Applications/tomcat/webapps/movie_data/database/movies.sqlite3";
	static String DB_CONNECTION     = "jdbc:sqlite:/Applications/tomcat/webapps/movie_data/database/movies.sqlite3";
	static String DB_LOCATION       = "/Applications/tomcat/webapps/movie_data/database/movies.sqlite3";
	static String NO_IMAGE          = "/Users/pedro/Downloads/MovieLens/hetrec2011/covers_id/no_image.jpg";
    static String URM_OUT           = "/Users/pedro/Downloads/MovieLens/hetrec2011/processed/user_ratedmovies_out.dat";
    static String SIM_OUT           = "/Users/pedro/Downloads/MovieLens/hetrec2011/processed/user_similarity_out.dat";
    static String URM_OUT_TEST      = "/Users/pedro/Downloads/MovieLens/hetrec2011/processed/user_ratedmovies_out_test.dat"; 
    static String USERS_REIDX       = "/Users/pedro/Downloads/MovieLens/hetrec2011/processed/users_reindexed.dat";
    static String DISTANCES         = "/Users/pedro/Downloads/MovieLens/hetrec2011/processed/user_similarity_out.dat";
    static String OCCUR_MATRIX      = "/Users/pedro/Downloads/MovieLens/hetrec2011/processed/occurrence.dat";
    static String OCCUR_MATRIX_TEST = "/Users/pedro/Downloads/MovieLens/hetrec2011/processed/occurrence_test.dat";
	static String COVERS_DIR        = "/Users/pedro/Downloads/MovieLens/hetrec2011/covers_id/";	
	static String BASE              = "/Users/pedro/Downloads/MovieLens/vector_results/";

	static File   MOVIES            = new File("/Users/pedro/Downloads/MovieLens/hetrec2011/movies.dat");	
	static File   MOVIE_ACTORS      = new File("/Users/pedro/Downloads/MovieLens/hetrec2011/movie_actors.dat");
	static File   MOVIE_COUNTRIES   = new File("/Users/pedro/Downloads/MovieLens/hetrec2011/movie_countries.dat");
	static File   MOVIE_DIRECTORS   = new File("/Users/pedro/Downloads/MovieLens/hetrec2011/movie_directors.dat");
	static File   MOVIE_GENRES      = new File("/Users/pedro/Downloads/MovieLens/hetrec2011/movie_genres.dat");
	static File   RATINGS           = new File("/Users/pedro/Downloads/MovieLens/hetrec2011/user_ratedmovies.dat");
	static File   RATINGS_REIDX     = new File("/Users/pedro/Downloads/MovieLens/hetrec2011/processed/user_ratedmovies_out.dat");	
	static File   RATINGS_TEST      = new File("/Users/pedro/Downloads/movies_db/hetrec2011/user_ratedmovies_test.dat");
   
}
