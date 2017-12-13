package data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Vector;
import java.io.File;
import java.io.FileNotFoundException;

import javolution.util.FastTable;


/**
 * <p>Title: Read text file and put content into memory </p>
 *
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * @author Pedro Contreras
 * @version 1.0
 */

public class ReadFile {

	public ReadFile() {}


	public Vector<String[]>    vectorRecords    = new Vector   <String[]>();
	public ArrayList<String[]> arrListRecords   = new ArrayList<String[]>();
	public ArrayList<byte[]>   byteListRecords  = new ArrayList<byte[]>();
	public FastTable<String[]> fastTableRecords = new FastTable<String[]>();
	/**
	 * Read a file and return the content in a Vector
	 * @param infile String file path
	 * @param numFields int number of fiels contained in the file
	 * @return Vector
	 */     
	public Vector<String[]> toVector(File inFile, int numFields, String separator) {
		// if the file is space separated or comma separated please change the 'line.split' code below
		try {
			String line = null;
			FileInputStream in = new FileInputStream(inFile);
			BufferedReader  br = new BufferedReader(new InputStreamReader(in));

			while ( (line = br.readLine()) != null) {    	
				String[] buffer = line.split(separator);
				if (buffer.length != numFields) {
					System.out.println("Malformed line found in " + inFile.getPath());
					System.out.println("Line was: " + line);
					System.out.println("length found was: " + buffer.length);
					System.exit( -1);
				}

				String[] row = new String[numFields];
				int count = 0;

				for (int i = 0; i < buffer.length; i++) {
					buffer[i] = buffer[i].trim();
					if (buffer[i].length() != 0) {
						row[count++] = (buffer[i]);
					}
				}

				vectorRecords.addElement(row);
			}
			in.close();

		}  catch (FileNotFoundException e) {
			System.err.println(inFile.getPath() + " does not exist.");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("IO Exception: " + e.toString());
			e.printStackTrace();
			System.exit( -1);
		}
		return vectorRecords;
	}

	/**
	 * @brief  read data file into memory using ArrayList.
	 *         Use this instead of vector for speed and efficiency (this is not sync) 
	 *         
	 * @param inFile
	 * @param numFields
	 * @param separator
	 * @return
	 */
	public ArrayList<String[]> toArrayList(File inFile, int numFields, String separator) {
		try {
			String line = null;
			FileInputStream  in = new FileInputStream(inFile);
			BufferedReader   br = new BufferedReader(new InputStreamReader(in));

			while ( (line = br.readLine()) != null) {    	
				String[] buffer = line.split(separator);

				if (buffer.length != numFields) {
					System.out.println("Malformed line found in " + inFile.getPath());
					System.out.println("line length was:        " + line.length());
					System.out.println("buffer length was:      " + buffer.length);
					System.exit( -1);
				}

				String[] row = new String[numFields];
				int count = 0;

				for (int i = 0; i < buffer.length; i++) {
					buffer[i] = buffer[i].trim();
					if (buffer[i].length() != 0) {
						row[count++] = (buffer[i]);
					}
				}
				arrListRecords.add(row);
			}
			in.close();

		}  catch (FileNotFoundException e) {
			System.err.println(inFile.getPath() + " does not exist.");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("IO Exception: " + e.toString());
			e.printStackTrace();
			System.exit( -1);
		}
		return arrListRecords;
	}


	/*
	 * @brief load data file into memory using FastTable.
	 * 
	 * @param inFile
	 * @param numFields
	 * @param separator
	 * @return
	 */
	public FastTable<String[]> toFastTable(File inFile, int numFields, String separator) {

		try {
			String line = null;
			FileInputStream  in = new FileInputStream(inFile);
			BufferedReader   br = new BufferedReader(new InputStreamReader(in));

			while ( (line = br.readLine()) != null) {  

				String[] buffer = line.split(separator);

				if (buffer.length != numFields) {
					System.out.println("Malformed line found in " + inFile.getPath());
					System.out.println("Line was: " + line+"\nLine length: '" + line.length());
					System.out.println("buffer length found was: " + buffer.length+"\nnumFields was: " + numFields);
					System.exit( -1);
				}

				String[] row = new String[numFields];
				int count = 0;

				for (int i = 0; i < buffer.length; i++) {
					buffer[i] = buffer[i].trim();
					if (buffer[i].length() != 0) {
						row[count++] = (buffer[i]);
					}
				}

				fastTableRecords.add(row);
			}
			in.close();

		}  catch (FileNotFoundException e) {
			System.err.println(inFile.getPath() + " does not exist.");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("IO Exception: " + e.toString());
			e.printStackTrace();
			System.exit( -1);
		}
		return fastTableRecords;
	}



	/**
	 * @brief clear memory when we are done with it
	 */
	public void clearVector(){
		vectorRecords.removeAllElements();	  
	}

	public void clearArrayList(){
		arrListRecords.clear();	  
	}

	public void clearFastTable(){
		//fastTableRecords.clear();
		     
	}

}
