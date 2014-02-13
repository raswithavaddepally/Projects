package com.mani.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

import com.mani.Twitter.TwitterCount;
import com.mani.Twitter.TwitterTopWords;
import com.mani.wikipedia.WikipediaCrawl;

public class ControllerClass {

	/**
	 * @param args/media/hduser/28987BC6987B90D4/BigData/
	 */
	
	static String commonFolder = "/media/hduser/28987BC6987B90D4/BigData/Output/CommonFolder";
	static String twitterOutput;
	
	public static void main(String[] args) {
		try
		{
		String twitterInput = args[0]+"/Twitter/04082012/14";
		twitterOutput = args[1]+"/TwitterOutput/04082012/14";
		
		String wikipediaInput1 = args[0]+"/Wikipedia/04082012/13";
		String wikipediaInput2 = args[0]+"/Wikipedia/04082012/14";
		String wikipediaInput3 = args[0]+"/Wikipedia/04082012/15";
		
		String wikipediaOutput1 = args[1]+"/WikipediaOutput/04082012/13";
		String wikipediaOutput2 = args[1]+"/WikipediaOutput/04082012/14";
		String wikipediaOutput3 = args[1]+"/WikipediaOutput/04082012/15";
		//String twitterTopOutput = twitterOutput+"/top50";
		
		TwitterCount tc = new TwitterCount(twitterInput, twitterOutput);
		
		
		//TwitterTopWords twc = new TwitterTopWords(twitterOutput, twitterTopOutput);
		
		WikipediaCrawl wc1 = new WikipediaCrawl(wikipediaInput1, wikipediaOutput1);
		WikipediaCrawl wc2 = new WikipediaCrawl(wikipediaInput2, wikipediaOutput2);
		WikipediaCrawl wc3 = new WikipediaCrawl(wikipediaInput3, wikipediaOutput3);
		
		//String joinTweetinput = copyfile(twitterTopOutput);
		
		String joinTweetOutput1 = args[1]+"/joined/1413";
		String joinTweetOutput2 = args[1]+"/joined/1414";
		String joinTweetOutput3 = args[1]+"/joined/1415";
		
		// write function to copy files from wikioutput to commonfolder
		
		String join1 = copyfile(wikipediaOutput1);
		String join2 = copyfile(wikipediaOutput2);
		String join3 = copyfile(wikipediaOutput3);
		
		JoinRed rd1 = new JoinRed(commonFolder+"/1413", joinTweetOutput1);
		JoinRed rd2 = new JoinRed(commonFolder+"/1414", joinTweetOutput2);
		JoinRed rd3 = new JoinRed(commonFolder+"/1415", joinTweetOutput3);		
		
		
		//JoinClass jc = new JoinClass(/*twitterTopOutput */ twitterOutput, wikipediaOutput, joinTweetOutput);
		
		String join4 = copyfile1(joinTweetOutput1);
		String join5 = copyfile1(joinTweetOutput2);
		
		String twoJoinOutput = join5+"/ThreeJoin";
		
		JoinRed rd4 = new JoinRed(join4, twoJoinOutput);
		
		
		String threeJoinOutput  = twoJoinOutput + "/finalOut";
		
		String file1 = twoJoinOutput +"/thirdfile";
		
		File f1 = new File(joinTweetOutput3 + "/part-r-00000");
		File f2 = new File(file1);
		Files.copy(f1.toPath(),f2.toPath());
		
		JoinRed rdFinal = new JoinRed(twoJoinOutput, threeJoinOutput);
		
		Graph.drawGraph(threeJoinOutput+"/part-r-00000");
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	
	}

	private static String copyfile1(String joinTweetOutput) throws IOException {
		
		String fileName = commonFolder +"/TripleJoin";
		String fileName1;
		
		if(new File(fileName).mkdirs())
			fileName1 =fileName + "/TwoJoin1";
		else
			fileName1 =fileName + "/TwoJoin2";
	
		
		File f1 = new File(joinTweetOutput+"/part-r-00000");
		File f2 = new File(fileName1);	
		
		Files.copy(f1.toPath(), f2.toPath());
		
		return fileName;
	}

	private static String copyfile(String wikiOutput) throws IOException {
		String fileName = commonFolder +"/14" + wikiOutput.substring(wikiOutput.length()-2, wikiOutput.length());
		
		
		
		if(new File(fileName).mkdirs())
			fileName += "/wikioutput";
		
		File f1 = new File(wikiOutput+"/part-r-00000");
		File f2 = new File(fileName);
		
		Files.copy( f1.toPath(), f2.toPath());
		
	    String fileName1 = commonFolder +"/14" + wikiOutput.substring(wikiOutput.length()-2, wikiOutput.length())+"/twitteroutput";
	    
	    f1 = new File(twitterOutput+"/part-r-00000");
		f2 = new File(fileName1);
		
		Files.copy( f1.toPath(), f2.toPath());
		
		return fileName;
	}

}
