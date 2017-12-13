package data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import data.ReadFile;
import util.DataFile;
/**
 * ParseHetrec.java parses file to get processes files in a way that we can
 *                  use later on. 
 *
 * @author Pedro Contreras. <br>
 * Copyright (c) 2013. All rights reserved. <br>
 * Project name: movies. <br>
 * Created on: 4 Jul 2013. <br>
 */
public class ParseHetrec {
	
	static final File    RATINGS           = DataFile.RATINGS;
    static final File    RATING_TEST       = DataFile.RATINGS_TEST;
    static final File    MOVIES            = DataFile.MOVIES;
    
    static final String  URM_OUT           = DataFile.URM_OUT;
    static final String  URM_OUT_TEST      = DataFile.URM_OUT_TEST;
    static final String  USERS_REIDX       = DataFile.USERS_REIDX;
    static final String  DISTANCES_OUT     = DataFile.DISTANCES;
    static final String  OCCUR_MATRIX      = DataFile.OCCUR_MATRIX;
    static final String  OCCUR_MATRIX_TEST = DataFile.OCCUR_MATRIX_TEST;
    static final String  NO_IMAGE          = DataFile.NO_IMAGE;
    static final String  COVERS_DIR        = DataFile.COVERS_DIR;
	
    static final String  TAB = "\t";
	
	
	/**
	 * @brief   Get the ratings from the user_ratedmovies.dat file. 
	 *          Creates a file called user_ratedmovies_out.dat with five columns:
	 *          user_id, userseq_id, movie_id, movieseq_id, rating
	 * 
	 *           userseq_id and movieseq_id are sequential indexes for users and the 
	 *           movies these are needed because id are not sequential. 
	 */
	public static void getUserRatedMovies(){
	
	    int  ratedMoviesFields    = 9;
	    int  moviesFields         = 21;
	    //read user_ratedmovies.dat
	    ArrayList<String[]> arrListUserRates = new ArrayList<String[]> ();
        ReadFile rf = new ReadFile();
        arrListUserRates = rf.toArrayList(RATINGS, ratedMoviesFields, TAB);
        
        //read movies.dat
        ArrayList<String[]> arrListMovies = new ArrayList<String[]> ();
        ReadFile mov = new ReadFile();
        arrListMovies = mov.toArrayList(MOVIES, moviesFields, TAB);
        
        
        try {
            File ro = new File(URM_OUT);
            if (!ro.exists()) {
                    ro.createNewFile();
            }
            FileWriter     fw = new FileWriter(ro.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
                
            System.out.println("Starting");

            int     u_index   = 1;
            String  u_pre     = "";
            String  u_current = "";
            boolean firstRun  = true;


            for (int i = 1; i < arrListUserRates.size(); i++) {
                
                u_pre     =  arrListUserRates.get(i-1)[0];
                u_current =  arrListUserRates.get(i)[0];
                
                if ((u_pre.compareToIgnoreCase(u_current)) != 0  && !firstRun){
                    u_index++;                    
                }
                
                bw.write(arrListUserRates.get(i)[0]);     //user id
                bw.write("\t"+u_index);                   //user sequential
                bw.write("\t"+arrListUserRates.get(i)[1]);//movie id
                
                for (int j = 1; j < arrListMovies.size(); j++) {

                    if(arrListUserRates.get(i)[1].compareToIgnoreCase(arrListMovies.get(j)[0]) == 0 ){
                        bw.write("\t"+j);                //movie sequential
                        break;
                    }
                }
                
                bw.write("\t"+arrListUserRates.get(i)[2]);//rating  
                bw.write("\n");
                
                //dirty way to handle the last record
                if(i == (arrListUserRates.size()-1)){
                    u_index++;                    
                }
                if(firstRun) {
                    firstRun =false;
                }
            }
                   
            bw.close();
            fw.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        arrListUserRates.clear();
        arrListMovies.clear();
        rf.clearArrayList();
        mov.clearArrayList();
        System.out.println("File out: "+URM_OUT);
        System.out.println("Finish!");
	}
	
	
	/**
     * @brief  users id are not sequencial, here I'm generating a new file with a new index.
     *         The file is called users_reindexed.dat has two columns new_index \t old_index 
     *         new_index: sequential id
     *         old_index: hetrec movie index
     * 
     *         user_ratedmovies.dat file is to create this index
     */
    public static void reindexUserRatings(){
        int     numFields    = 9;
        ArrayList<String[]> arrListRecords = new ArrayList<String[]> ();
        
        ReadFile rf = new ReadFile();        
        arrListRecords = rf.toArrayList(RATINGS, numFields, TAB);
        
        try {
            File ro = new File(USERS_REIDX);
            if (!ro.exists()) {
                    ro.createNewFile();
            }
            FileWriter     fw = new FileWriter(ro.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
                
            System.out.println("Starting");
            int index      = 0;
            String pre     = "";
            String current = "";
                    
            for (int i = 2; i < arrListRecords.size(); i++) {
                pre     =  arrListRecords.get(i-1)[0];
                current =  arrListRecords.get(i)[0];
                
                if ((pre.compareToIgnoreCase(current)) != 0){
                    index++;
                    bw.write(Integer.toString(index));        
                    bw.write("\t"+ Integer.valueOf(pre));
                    bw.write("\n");
                }
                //dirty way to handle the last record
                if(i == (arrListRecords.size()-1)){
                    index++;
                    bw.write(Integer.toString(index));        
                    bw.write("\t"+ Integer.valueOf(pre));
                    bw.write("\n");
                }
            }
            
            bw.close();
            fw.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        arrListRecords.clear();
        rf.clearArrayList();
        System.out.println("Finish!");
    }
    
    
    /**
     * @brief  This takes a file with user_id, movie_id, rating.  And produces a binary
     *         Array, which later on will be the bases for Random Projection and 
     *         Baire sorting.
     * 
     *         We are using the hetrec2011-movielens-2k dataset. Thus,
     *         we have: 2113 users 10197 movies
     * 
     * @param users_number
     * @param movies_number
     */
    
    public static void getBinaryMatrix(int users_number, int movies_number){
        int  numFields    = 5;
        File load_ratings = new File(URM_OUT);
        
        ArrayList<String[]> arrListRecords = new ArrayList<String[]> ();
        ReadFile rf = new ReadFile();
        arrListRecords = rf.toArrayList(load_ratings, numFields, TAB);
        
        System.out.println("Starting");        
        int user_id, movie_id= 0;
        
        try {
            File ro = new File(OCCUR_MATRIX);
            if (!ro.exists()) {
                    ro.createNewFile();
            }
        
            FileWriter     fw = new FileWriter(ro.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            int cursor = 0;
            user_id    = Integer.parseInt(arrListRecords.get(0)[1]);
            movie_id   = Integer.parseInt(arrListRecords.get(0)[3]);
            
            for (int u = 1; u < (users_number+1); u++) {
                for (int m = 1; m < (movies_number+1); m++) {
                	
                    if(movie_id == m && user_id == u ){
                        
                        if((cursor+1) == arrListRecords.size()){
                            bw.write("1");
                            continue;
                        }
                        user_id    = Integer.parseInt(arrListRecords.get(cursor)[1]);
                        movie_id   = Integer.parseInt(arrListRecords.get(cursor)[3]);
                        bw.write("1");
                        
                        //update cursor
                        cursor++;
                        
                        user_id    = Integer.parseInt(arrListRecords.get(cursor)[1]);
                        movie_id   = Integer.parseInt(arrListRecords.get(cursor)[3]);
   
                    }else{
                        bw.write("0");
                    }
                }
                bw.write("\n");
            }
            bw.close();
            fw.close();
            System.out.println("File writen: "+OCCUR_MATRIX);
        } catch (IOException e) {
            e.printStackTrace();
        }

        arrListRecords.clear();
        rf.clearArrayList();
        System.out.println("\nFinish!");

    }
        
    /**
     * @brief this gets the similarity and dissimilarity distances.
     *        We also can calculate this using sqlite but that is way
     *        too slow
     * 
     * @param users_number  number of users
     * @param movies_number number of movies
     */
    public static void getSimilarityMatrix(int users_number, int movies_number){
        int  numFields  = (movies_number+1);
        char curr = ' ';
        char next = ' ';
        
        File load_occur = new File(OCCUR_MATRIX);

        ArrayList<String[]> arrListRecords = new ArrayList<String[]>();
        ReadFile rf = new ReadFile();
        arrListRecords = rf.toArrayList(load_occur, numFields, "");
        try {
            File d_out = new File(DISTANCES_OUT);
            if (!d_out.exists()) {
                    d_out.createNewFile();
            }
            FileWriter     fw = new FileWriter(d_out.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
        
	        for (int u = 0; u < (users_number-1); u++) {
	            for (int u2 = (u+1); u2 < (users_number); u2++) {
	             
	                int  similarity    = 0;
	                int  dissimilarity = 0;
	                
	                for (int m = 0; m < (movies_number); m++) {
	                    curr = arrListRecords.get(u)[m].charAt(0);
	                    next = arrListRecords.get(u2)[m].charAt(0);
	                    
	                    if (curr == '1'  &&  next== '1') {
	                        similarity++;//; = similarity+1;
	                    }
	                    if ((curr == '0' &&  next=='1') || (curr == '1' &&  next == '0')){
	                        dissimilarity++;//= dissimilarity+1;
	                    }
	                }
	                bw.write((u+1)+"\t"+(u2+1)+"\t"+similarity+"\t"+dissimilarity+"\n");
	                //System.out.println((u+1)+"\t"+(u2+1)+"\t"+similarity+"\t"+dissimilarity);
	                similarity    = 0;
	                dissimilarity = 0;
	            }
	            System.out.println("User "+u+" done");
	        }
        	bw.close();
        	fw.close();
	        System.out.println("File writen: "+DISTANCES_OUT);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
        
        arrListRecords.clear();
        rf.clearArrayList();
    }
    
    public static void getDuplicatedMovies(int movies_number){
        ArrayList<String[]> arrListRecords = new ArrayList<String[]> ();
        ReadFile rf = new ReadFile();
        arrListRecords = rf.toArrayList(MOVIES, 21, TAB);

        int movie_id, movie_id_b;
        String title, title_b;
        for (int i = 1; i < arrListRecords.size(); i++) {
            movie_id = Integer.parseInt(arrListRecords.get(i)[0]);
            title    = arrListRecords.get(i)[1];
            
            for (int j = (i+1) ; j < arrListRecords.size(); j++) {
                movie_id_b = Integer.parseInt(arrListRecords.get(j)[0]);
                title_b    = arrListRecords.get(j)[1];
                
                if (title == null || title_b == null ){
                    continue;
                }
                if (title.compareToIgnoreCase(title_b) == 0){
                    System.out.print(title);
                    System.out.print(":  "+ movie_id +" "+ movie_id_b);
                    arrListRecords.get(j)[1] = null;
                    System.out.println();
                }
                
            }
            
        }

        arrListRecords.clear();
        rf.clearArrayList();
        
    }
    
 
	
	
	/**
	 * @brief main Use to run method when needed
	 */
	public static void main(String[] args) {
	    /**
	     * HETREC data set
         * ratings  855583
         * users      2113 
         * movies    10197 
	     */
	    
	    long startTime = System.currentTimeMillis();
	    //getUserRatedMovies();      
	    //reindexUserRatings();
	    //getBinaryMatrix(2113,10197);
	    //getBinaryMatrix(5,1000);
	    
        //getSimilarityMatrix(2113, 10197);
	    getDuplicatedMovies(10197);
        
        long stopTime = System.currentTimeMillis();
        long runTime  = stopTime - startTime;
        
        System.out.print("\nRun time: " + runTime + " Milliseconds");
    
    }
}
