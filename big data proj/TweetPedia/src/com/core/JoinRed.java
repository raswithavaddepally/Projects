package com.mani.core;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import com.sun.jersey.core.header.Token;

public class JoinRed {
	public static class OneMapper extends Mapper<LongWritable, Text, Text, Text>
	{
		public void map(LongWritable key, Text values, Context context) throws IOException, InterruptedException
		{
			String line = values.toString();
			String Tokens[] = line.split("\t");
			
			if(Tokens.length ==1)
			{
				Text keyPart = new Text(Tokens[0]);
				Text valuePart = new Text("");
				context.write(keyPart, valuePart);
			}
			else if(Tokens.length == 2)
			{
				Text keyPart = new Text(Tokens[0]);
				Text valuePart = new Text(Tokens[1]);
				context.write(keyPart, valuePart);
			}
			else if(Tokens.length == 3)
			{
				Text keyPart = new Text(Tokens[0]);
				Text valuePart = new Text(Tokens[1] +"\t"+ Tokens[2]);
				context.write(keyPart, valuePart);
			}
			
				
		}
	}

	
	public static class RedJoinReducer extends Reducer<Text, Text, Text, Text>
	{
		public void reduce(Text key , Iterable <Text> values ,Context context)	throws IOException, InterruptedException 
		{
			String conc="";
			int count =0;
			for (Text value : values)
			{
				conc += value.toString()+ "\t";
				count++;
			}
			if(count>1)
				context.write(key, new Text(conc));
		}
	}
	
	public JoinRed(String input, String output) throws IOException, InterruptedException, ClassNotFoundException
	{		
		Configuration conf = new Configuration();
		Job job1 = new Job(conf);
		
		job1.setJarByClass(JoinRed.class);
	    job1.setMapOutputKeyClass(Text.class);
	    job1.setMapOutputValueClass(Text.class);
	    job1.setOutputKeyClass(Text.class); 
	    job1.setOutputValueClass(Text.class); 

	    job1.setMapperClass(OneMapper.class);
	    job1.setReducerClass(RedJoinReducer.class);
	
	    job1.setInputFormatClass(TextInputFormat.class); 
	    job1.setOutputFormatClass(TextOutputFormat.class);
			
	    FileInputFormat.setInputPaths(job1, new Path(input));
	    FileOutputFormat.setOutputPath(job1, new Path(output));
	   
	    job1.waitForCompletion(true);
	}	
}
