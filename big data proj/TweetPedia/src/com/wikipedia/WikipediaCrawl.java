package com.mani.wikipedia;


import java.io.IOException;
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

        
public class WikipediaCrawl {
        
 public static class Map extends Mapper<LongWritable, Text, Text, IntWritable> {
	 private Text page = new Text();
	 private IntWritable count = new IntWritable();
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException  {
        String line = value.toString();
        
        String[] split = line.split(" ");
               
        int indexOfColon = split[1].indexOf(":");
        
        if(indexOfColon == -1)
        	indexOfColon =0;
        
        String pageTitle = split[1].substring(indexOfColon);
        int visitCount = Integer.parseInt(split[2]);
        
        pageTitle = pageTitle.replaceAll("\\%[0-9A-Za-z]+", "");
        pageTitle = pageTitle.replaceAll("[!?.,()$&%;*+'-]+", "");
        pageTitle = pageTitle.replaceAll("[/_]+", " ");
        pageTitle = splitFunction(pageTitle);
        
        
        String[] words = pageTitle.split(" ");
        for(int i=0; i<words.length; i++)
        {
        	if(words[i].length() > 2)
           	page.set(words[i].toLowerCase());
           	count.set(visitCount);
            context.write(page, count);
        }
        
        
    }
    public String splitFunction(String line)
    {
		String capitalPattern = "[A-z][a-z]+";
		
		Pattern p = Pattern.compile(capitalPattern);
		
		Matcher match = p.matcher(line);
		String spaced = "";
		while(match.find())
		{
			spaced += match.group()+ " ";
		}
		
		return spaced;
    }
 } 
        
 public static class Reduce extends Reducer<Text, IntWritable, Text, IntWritable> {

    public void reduce(Text key, Iterable<IntWritable> values, Context context) 
      throws IOException, InterruptedException {
    	
    	int sum = 0;
    	if(!key.equals(new Text("")))
        for (IntWritable val : values) {
            sum += val.get();
        }
    	String keyPair = key.toString();
        context.write(new Text(keyPair), new IntWritable(sum));
        
    }
 }
 
        
 public WikipediaCrawl(String input, String output) throws Exception {
    Configuration conf = new Configuration();
        
    Job job = new Job(conf, "PageCounts");
    
    job.setJarByClass(WikipediaCrawl.class);
    
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
