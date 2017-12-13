package cluster.run;

import java.io.OutputStreamWriter;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.FileNotFoundException;


/**
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: RHUL</p>
 *
 * @author Pedro Contreras
 * @version 1.0
 */
public class DigitDistribution {
    
    /**       This helps to get the digist distribution data
     * 
     * @param infile String
     * @param outfile String
     * @param fields int
     */
    public void put(String infile, String outfile,  int fields) {
        String separator= "\t";
        //String separator= " ";
        String[] row = new String[fields];

        try {
            // File out
            FileOutputStream   fos = new FileOutputStream(outfile);
            OutputStreamWriter out = new OutputStreamWriter(fos, "UTF-8");

            String line = null;
            // File in
            FileInputStream in = new FileInputStream(infile);
            BufferedReader  br = new BufferedReader(new InputStreamReader(in));

            // read file line
            int countRegister = 0;
            while ((line = br.readLine()) != null) {
                countRegister++;
                String[] buffer = line.split(separator);
                if (buffer.length != fields) {
                    System.out.println("Line was:" + line);
                    System.out.println("length found was: " + buffer.length);
                    System.exit(-1);
                }

                int count = 0;
                //the buffer contains  5 fiels and these are stored in an array
                for (int i = 0; i < buffer.length; i++) {
                    buffer[i] = buffer[i].trim();
                    if (buffer[i].length() != 0) {
                        row[count++] = (buffer[i]);
                    }
                }

                //read data we need from the buffer
                String rv = row[0];

                // stores  digits distribution
                System.out.print( countRegister+ " ");
                System.out.println(rv );
                
            }
            in.close();
            out.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("IO Exception: " + e.toString());
            e.printStackTrace();
            System.exit(-1);
        }
    }


    public static void main(String[] args) {
        
    }
}