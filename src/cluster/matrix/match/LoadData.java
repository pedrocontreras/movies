package cluster.matrix.match;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Vector;
import java.io.File;
import java.io.FileNotFoundException;



/**
 * 
 * LoadData.java  TESTTING
 *
 * @author Pedro Contreras. <br>
 * Copyright (c) 2013. All rights reserved. <br>
 * Project name: movies. <br>
 * Created on: 28 Jul 2013. <br>
 */
public class LoadData {

    public LoadData() {}

    /*
     * We store the file in this array, do not forget to clear up after using it
     */
    public ArrayList<String[]> ArrListRecords = new ArrayList<String[]>();
    
    /**
     * @brief  this method loads the occurence matrix into an array.
     * 
     * @param dataFile
     * @param numFields
     * @return
     */
    public  ArrayList<String[]> toArrayList(String dataFile) {
        //numFields   = numFields+1; // to handle the String null terminator
        File inFile = new File(dataFile);
          
        try {
            String  line = null;
              
            FileInputStream in = new FileInputStream(inFile);
            BufferedReader  br = new BufferedReader(new InputStreamReader(in));

            
            while ( (line = br.readLine()) != null) {
                
                String[] buffer = line.split("");
                int lineLength = line.length()+1;  // +1 to handle the String null terminator

                if (buffer.length != lineLength) {
                    System.out.println("Malformed line found in " + inFile.getPath());
                    System.out.println("Line was: " + line);
                    System.out.println("length found was: " + buffer.length);
                    System.exit( -1);
                }
                
                String[] row = new String[lineLength];
                int count = 0;
                
                for (int i = 0; i < buffer.length; i++) {
                    buffer[i] = buffer[i].trim();
                    if (buffer[i].length() != 0) {
                        row[count++] = (buffer[i]);
                    }
                }
                
                ArrListRecords.add(row);
          }
          
          System.out.println();
          in.close();
        
        }  catch (FileNotFoundException e) {
            System.err.println(inFile.getPath() + " does not exist.");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("IO Exception: " + e.toString());
            e.printStackTrace();
            System.exit( -1);
        }
        return ArrListRecords;
    }
  
    /**
    * @brief clear ArrayList when we are done with it
    */
    public void clearArrayList(){
        ArrListRecords.clear();     
    }
    
    
    public Vector<Integer> getUserRatedMovies(String occurrenceFile, int userIdx)
    {
        LoadData ld = new LoadData();
        ArrayList<String[]>     ar = new ArrayList<String[]>(); 
        Vector<Integer>   movieIdx = new Vector<Integer>();
        
        try {
            
            ar = ld.toArrayList(occurrenceFile);

        } catch (Exception ex) {
            System.out.println(ex);
        }
        
        int j = 0;
        while(ar.get(userIdx)[j] != null)
        {
            if((ar.get(userIdx)[j].compareToIgnoreCase("1")==0))
            {
                System.out.print("("+ (userIdx)+"," +(j+1)+ ")");
                movieIdx.add(j+1);
            }
            j++;
        }
        System.out.println();
        
        ld.clearArrayList();
        ar.clear();
        
        return  movieIdx;
    }
    /**
     * 
     * @param args
     */
    public static void main(String[] args) {
        
        LoadData ld = new LoadData();
        Vector<Integer>   movieIdx;// = new Vector<Integer>();
        String occur = "/Users/pedro/Downloads/movies_db/hetrec2011/processed/occurrence_test.dat";
        
        movieIdx = ld.getUserRatedMovies(occur,5);
        
        for (int i = 0; i < movieIdx.size(); i++)
        {
            System.out.print(movieIdx.get(i)+" ");
        }
        
        
        /**
        LoadData ld = new LoadData();
        ArrayList<String[]> ar = new ArrayList<String[]>(); 
        try {
            
            String occur = "/Users/pedro/Downloads/movies_db/hetrec2011/processed/occurence_test.dat";
            ar = ld.toArrayList(occur);

        } catch (Exception ex) {
            System.out.println(ex);
        }
        
        
        for (int i = 0; i < ar.size(); i++)
        {
            int j = 0;
            while(ar.get(i)[j] != null)
            {
                if((ar.get(i)[j].compareToIgnoreCase("1")==0))
                {
                    System.out.print("("+ (i+1)+"," +(j+1)+ ")");
                }
                j++;
            }
            System.out.println();
        }
        
        ld.clearArrayList();
        ar.clear();
        */
    }

}
