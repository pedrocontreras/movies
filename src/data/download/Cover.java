package data.download;

import java.io.File;
import java.util.Vector;

import util.DataFile;
import data.ReadFile;

/**
 * Cover.java This download the movie covers from the URL in the movies.dat file
 *
 * @author Pedro Contreras. <br>
 * Copyright (c) 2013. All rights reserved. <br>
 * Project name: movies. <br>
 * Created on: 5 Jul 2013. <br>
 */
public class Cover {
	
	
	static final File    MOVIE_FILE = DataFile.MOVIES;
	static final String  NO_IMAGE   = DataFile.NO_IMAGE;
	static final String  COVERS_DIR = DataFile.COVERS_DIR;	
	static final String  TAB        = "\t";
	static final int     NUM_FIELDS = 21;
	
	/**
	 * @brief use to download the movie covers
	 * 
	 * @param fileOut name of the file out, here I use the imdb id
	 * @param url cover URL
	 */
	public static void getFromURL(String url, String fileOut)
	{
		URLFile uf = new URLFile();
		uf.download(url, fileOut, NO_IMAGE);		
	}
	
	/**
	 * 
	 */
	public static void download(){
		Vector<String[]> vectorRecords = new Vector<String[]> ();
		ReadFile rf = new ReadFile();
		vectorRecords = rf.toVector(MOVIE_FILE, NUM_FIELDS, TAB);
		String coverNameOut = "";
		String url = "";
        
		System.out.println("Starting");
		File cr = new File (COVERS_DIR);
		if (!cr.exists()){
			cr.mkdirs();
		}
		//we start from 1 because the first line is the data title
        for (int i = 1; i < vectorRecords.size(); i++) {
        	        	
        	coverNameOut = COVERS_DIR+vectorRecords.elementAt(i)[0]+".jpg";
        	url = vectorRecords.elementAt(i)[4];
        	        	
        	getFromURL(url, coverNameOut);
        	System.out.print(coverNameOut+" "+url);
        }
        vectorRecords.removeAllElements();
        rf.clearVector();
		
	}
	public static void main(String[] args) {
		
		download(); //done
    }
}
