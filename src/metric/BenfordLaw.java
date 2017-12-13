package metric;

import java.io.*;
import java.util.*;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import data.*;

/**
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: RHUL</p>
 * @author Pedro Contreras
 * @version 1.0
 */
public class BenfordLaw {

    public BenfordLaw() {
    }

    /**
     *
     *
     * @param inFile File- data input file
     * @param fieldNumber int- number of columns in the input file
     * @param column int- column to analyse, if column = 2 we are working with
     *                    spectrometric values, photometric = 3
     * @param presicion int- the presicion to be analyse, e.g. if 5 we consider until the 5th
     *                      decimal position
     * @param outFile File-  data output file, this is a matrix with columns from 0 to 9
     *                      and rows equivalent to each decimal position
     */

    public void getFrequencies(File inFile,
                               int fieldNumber,
                               int column,
                               int precision,
                               File outFile,
                               String separator) {

        int len = 0;
        String number = null;
        //we store our result in an array where columns are the number from cero to 9
        //and the rows are the decimal positions, e.g. first row correspont to the
        //first decimal position
        int[][] freq = new int[precision][10];

        // File out
        FileOutputStream fos = null;
        //read file
        Vector<String[]> vectorData = new Vector<String[]> ();

        //System.out.println("Working now....");
        ReadFile rf = new ReadFile();
        vectorData = rf.toVector(inFile, fieldNumber, separator);

        //now the data is in a vector so we can start to analyse the content
        for (int i = 0; i < vectorData.size(); i++) {
            //retrieve the decimal part of a number
            number = decimalPart(vectorData.elementAt(i)[column]);
	    //System.out.println(number);
            //deal with the number's length
            if (number.length() < precision) {
                len = number.length();
            }
            else {
                len = precision;
            }
            //count decimal ocurrences for all decimal positions of a number
            for (int j = 0; j < len; j++) {
                switch (number.charAt(j)) {
                    case '0':
                        freq[j][0] = freq[j][0] + 1;
                        break;
                    case '1':
                        freq[j][1] = freq[j][1] + 1;
                        break;
                    case '2':
                        freq[j][2] = freq[j][2] + 1;
                        break;
                    case '3':
                        freq[j][3] = freq[j][3] + 1;
                        break;
                    case '4':
                        freq[j][4] = freq[j][4] + 1;
                        break;
                    case '5':
                        freq[j][5] = freq[j][5] + 1;
                        break;
                    case '6':
                        freq[j][6] = freq[j][6] + 1;
                        break;
                    case '7':
                        freq[j][7] = freq[j][7] + 1;
                        break;
                    case '8':
                        freq[j][8] = freq[j][8] + 1;
                        break;
                    case '9':
                        freq[j][9] = freq[j][9] + 1;
                        break;
                }
            }
        }

        //read result and write them to a file
        try {
            fos = new FileOutputStream(outFile);
            OutputStreamWriter out = new OutputStreamWriter(fos, "UTF-8");

            for (int i = 0; i < precision; i++) {
                int sum = 0;
                int f = 0;
                for (int j = 0; j < 10; j++) {
                    f = freq[i][j];
                    sum = sum + f;
                    out.write(freq[i][j] + " ");
                    System.out.print(freq[i][j] + " ");
                }
                System.out.print(" = " + sum);
                out.write(" = " + sum);
                out.write("\n");
                System.out.println();
            }
            out.close();

        }
        catch (FileNotFoundException fnfe) {
            System.out.print("FileNotFoundException " + fnfe);
        }
        catch (IOException ioe) {
            System.out.print("IOException " + ioe);
        }
        //System.out.println("Results in file: "+ outFile);
    }


    /**
     * We take a number and strip the integer part leaving only the decimal part
     * @param number String - number to extract the decimal part
     * @return String - decimal part
     */
    private String decimalPart(String number) {
        String[] tmp = new String[2];
        tmp = number.split("\\.");
        return tmp[1];
    }


    public static void main(String[] args) throws UnsupportedEncodingException {
        File inFile = new File("/Users/pedro/Downloads/sdss-Spec_Phot-0to06.dat");
        int fieldNumber = 4;
        int precision = 10;
        //if column = 2 we are working with spectrometric values, photometric = 3
       // int column = 0;
        File outFile = new File("/Users/pedro/Downloads/digit-dist.dat");

        BenfordLaw bl = new BenfordLaw();
        // we try 10 vector in one run
        for (int column = 2; column < 4; column++) {
            System.out.println(precision +" digit of precision.  Vector No. "+ (column+1) );
            bl.getFrequencies(inFile, fieldNumber, column, precision, outFile, " ");
            System.out.println();
        }
    }
}
