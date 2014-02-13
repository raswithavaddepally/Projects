package com.mani.core;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.json.simple.parser.ParseException;

public class testClass {

	public static void main(String[] args)
	{
	try
	{
	FileInputStream fstream = new FileInputStream("/media/hduser/28987BC6987B90D4/BigData/Twitter/00/00.json");  

	DataInputStream in = new DataInputStream(fstream);  
	BufferedReader br = new BufferedReader(new InputStreamReader(in)); 
	String line;
	while((line = br.readLine()) != null)
	{
		JsonReader jr = new JsonReader(line);
		ArrayList<String> tags = jr.grabTweets();
		String totalTag ="";
		for(int i=0; i<tags.size(); i++)
		{
			totalTag += tags.get(i)+",";
		}
		System.out.println("tag: " + totalTag);
	}		
	
	br.close();
	}
	catch(FileNotFoundException fne)
	{
		fne.printStackTrace();
	}
	catch(IOException ioe)
	{
		ioe.printStackTrace();
	}
	catch(ParseException pe)
	{
		pe.printStackTrace();
	}
	
	
}

}
