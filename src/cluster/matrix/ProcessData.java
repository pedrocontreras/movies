package cluster.matrix;

import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import util.DataFile;

 
/**
 * 
 * ProcessData.java  This process all data to obtain clusters
 *
 * @author Pedro Contreras. <br>
 * Copyright (c) 2013. All rights reserved. <br>
 * Project name: movies. <br>
 * Created on: 15 Jul 2013. <br>
 */
public class ProcessData { 
    /**
     * 
     */
    public ProcessData(){
    }

    /**
     * 
     * @param fileIn
     * @param additionVectorFileIn
     * @param randomVectorFileOut
     * @param randomProjectionFileOut
     * @throws Exception
     */
	public void run(String fileIn,
                    String additionVectorFileIn,
                    String randomVectorFileOut,
                    String randomProjectionFileOut) throws Exception {


        //timer
        long startTime = System.currentTimeMillis();
        //buffer reader for an input file
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileIn)));

        String line = "";
        

        int numOnes[] = null;
        int numRows = 0;
        int numCols = 0;

        // this vector contains the random number used by the random projection
        RandomGenerator randomgenerator = new RandomGenerator();

        PrintStream os = new PrintStream(new FileOutputStream(additionVectorFileIn), true, "UTF-8");
        boolean firstTime = true;
        while ((line = br.readLine()) != null) {

            String buffer[] = line.split("\\s");

            if (firstTime) {
                numCols = buffer.length;
                numOnes = new int[numCols];
                firstTime = false;
            }

            for (int i = 0; i < buffer.length; i++) {
                if (Integer.parseInt(buffer[i]) == 1) {
                    numOnes[i]++;
                }
            }
            numRows++;
        }
        System.out.println("Rows number: " + numRows + "; Columns number: " + numCols);

        
        //write to a file
        System.out.println("Writing additionVector to file");
        for (int i = 0; i < numOnes.length; i++) {
            os.println(numOnes[i] + " ");
        }    
        br.close();
        os.close();

        
        float rgn[] = randomgenerator.normal(2, numCols);
        float result[] = new float[numRows];

        br = new BufferedReader(new InputStreamReader(new FileInputStream(fileIn)));

        System.out.println("Normalising matrix ...");
        int count = 0;
        while ((line = br.readLine()) != null) {

            String buffer[] = line.split("\\s");
            result[count] = 0;

            for (int i = 0; i < buffer.length; i++) {
                int num = Integer.parseInt(buffer[i]);

                //here we calculate the random projected value that is stored
                //in the result[] matrix
                if (num == 1) {
                    float normalNumber = (float) num / (float) numOnes[i];
                    result[count] += (normalNumber * rgn[i]);
                } else {
                    os.print("0 ");
                }
            }
            count++;
            os.println();
        }

        br.close();
        os.close();

        System.out.println("Writing random generated numbers file");
        os = new PrintStream(new FileOutputStream(randomVectorFileOut), true, "UTF-8");
        for (int i = 0; i < rgn.length; i++) {
            os.println(rgn[i]);
        }
        os.close();

        System.out.println("Writing random projected file");
        os = new PrintStream(new FileOutputStream(randomProjectionFileOut), true, "UTF-8");

        //before store the matrix into a file to sort it
        //notice:  the first component is the index and the second of the content
        float resultArray[][] = new float[result.length][2];

        boolean firstRun = true;
        for (int i = 0; i < result.length; i++) {

            if (!firstRun) {
                resultArray[i][0] = i + 1;
                resultArray[i][1] = result[i];
            } else {
                resultArray[0][0] = 1;
                resultArray[0][1] = result[i];
            }
            firstRun = false;
        }

        Arrays.sort(result);

        //print out random project matrix into a file and count clusters
        int cluster = 1; // given any random projection > 0 always there is at least 1 cluster
        float current = result[0];

        for (int i = 0; i < result.length; i++) {
            //print to file
            os.println(result[i]);

            //count clusters
            if (current != result[i]) {
                cluster++;
            }
            current = result[i];
        }
        os.close();
        System.out.println("clusters : " + cluster);

        System.out.println("--------------------");
        for (int i = 0; i < resultArray.length; i++) {
            System.out.println((int) resultArray[i][0] + "\t" + resultArray[i][1]);
        }

        //this measures the runtime
        long stopTime = System.currentTimeMillis();
        long runTime = stopTime - startTime;
        //print put the run time
        System.out.println("Run time: " + runTime + " Milliseconds");
    }

    
    public static void main(String[] args) {
    	String OCCUR_MATRIX = DataFile.OCCUR_MATRIX;
        String BASE         = DataFile.BASE;	
    
        try {

            ProcessData p = new ProcessData();
            p.run(OCCUR_MATRIX, BASE+"additionVector.dat", BASE+"randomNumbersVector.dat", BASE+"randomProjection.dat");
            System.out.println("Process Finished file: additionVector.dat, randomNumbersVector.dat, randomProjection.dat");
            

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
