package cluster.run;

import java.io.OutputStreamWriter;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.FileNotFoundException;

import metric.Baire;

/**
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: RHUL</p>
 *
 * @author Pedro Contreras
 * @version 1.0
 */
public class BaireToFile {

    static int precision = 2;
    
    
    public BaireToFile() {
    }

    /**
     * Takes data file and calculates Baire distance
     * @param infile String
     * @param outfile String
     * @param fields int
     */
    public void data(String infile, String outfile, int fields) {
        String[] row = new String[fields];


        try {
            // Create the tokenizer to read from a file
            // FileReader rd = new FileReader(infile);
            // StreamTokenizer st = new StreamTokenizer(rd);

            // File out
            FileOutputStream  fos = new FileOutputStream(outfile);
            OutputStreamWriter out = new OutputStreamWriter(fos, "UTF-8");

            Baire b = new Baire();

            try {
                String line = null;
                FileInputStream in = new FileInputStream(infile);
                BufferedReader  br = new BufferedReader(new InputStreamReader(in));

                int countRegister = 0;
                while ((line = br.readLine()) != null) {
                    countRegister++;
                    String[] buffer = line.split(" ");
                    if (buffer.length != fields) {
                        System.out.println("Line was:" + line);
                        System.out.println("length found was: " + buffer.length);
                        System.exit(-1);
                    }

                    int count = 0;

                    for (int i = 0; i < buffer.length; i++) {
                        buffer[i] = buffer[i].trim();
                        if (buffer[i].length() != 0) {
                            row[count++] = (buffer[i]);
                        }
                    }
                    //this if   is to print out specific baire distances,
                    //comment this when printing all the baire distances
                    int d = b.commonPrefix(row[2], row[3]);
                    //String clusterIndex = b.clusterIndex(row[2], d);

                    /**
                    if(d == 0 ){
                    	clusterIndex = "none";
                    }
                     **/
                    // if (d  == precision) {
                    out.write(countRegister + " ");   // record number
                    //out.write(row[0] + " ");        // dec
                    //out.write(row[1] + " ");        // ra
                    out.write(row[2] + " ");          // phot
                    out.write(row[3] + "  ");         // spec
                    out.write(d + " ");               // baire distace
                    //out.write(clusterIndex + " ");  // cluster index

                    out.write("\n");

                    //  }
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

        } catch (IOException e) {
        }
    }

    public static void main(String[] args) {
        long start, stop;
        double elapsed;

        for (int i = 0; i < 50; i++) {
            // start timer
             start = System.currentTimeMillis();
            BaireToFile tb = new BaireToFile();
            //System.out.println("processing file /Users/pedro/sdss/sdss-Spec_Phot-0to06.dat");
            tb.data("/Users/pedro/sdss/data/sdss-Spec_Phot-0to06.dat", "/Users/pedro/sdss/data/sdssBaireClusters.dat", 4);
            stop = System.currentTimeMillis();
            elapsed = stop - start;            
            System.out.println(elapsed/1000);
        }
    }
}
