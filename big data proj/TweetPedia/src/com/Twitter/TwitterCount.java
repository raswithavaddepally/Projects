package com.mani.Twitter;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
        
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.json.simple.parser.ParseException;

import com.mani.core.JsonReader;
        
public class TwitterCount {
        
 public static class Map extends Mapper<LongWritable, Text, Text, IntWritable> {
    private final static IntWritable one = new IntWritable(1);
    private Text word = new Text();
        
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException  {
        String line = value.toString();
        
        try
        {
        	
        	ArrayList<String> tags;
        	JsonReader jr = new JsonReader(line);
        	tags = jr.grabTweets();
        	
        	for(int i=0; i<tags.size();i++)
        	{
        		ArrayList<String> individualWords = splitFunction(tags.get(i));
        		for(int j=0; j<individualWords.size(); j++)
        		{
        			if(individualWords.get(j).length() > 2)
        				word.set(individualWords.get(j).toLowerCase());
        			context.write(word, one);
        		}
        	}
        
        
        }
        catch(ParseException pe)
        {
        	pe.printStackTrace();
        }
        
    }
    public ArrayList<String> splitFunction(String line)
    {
		String capitalPattern = "[A-z][a-z]+";
		
		Pattern p = Pattern.compile(capitalPattern);
		
		Matcher match = p.matcher(line);
		ArrayList<String>  splittag =  new ArrayList<String>();
		while(match.find())
		{
			splittag.add(match.group().replaceAll("_", ""));
		}
		
		return splittag;
    }
 } 
        
 public static class Reduce extends Reducer<Text, IntWritable, Text, NullWritable> {

    public void reduce(Text key, Iterable<IntWritable> values, Context context) 
      throws IOException, InterruptedException {
        
        context.write(key, null);
    }
 }
 
        
 public TwitterCount(String input, String output) throws Exception {
    Configuration conf = new Configuration();
        
    Job job = new Job(conf, "TwitterCount");
    job.setJarByClass(TwitterCount.class);
    
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(IntWritable.class);
        
    job.setMapperClass(Map.class);
    job.setReducerClass(Reduce.class);
        
    job.setInputFormatClass(TextInputFormat.class);
    job.setOutputFormatClass(TextOutputFormat.class);
        
    FileInputFormat.addInputPath(job, new Path(input));
    FileOutputFormat.setOutputPath(job, new Path(output));
        
    job.waitForCompletion(true);   
    
 }
        
}
