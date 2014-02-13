package com.mani.core;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import com.mani.wikipedia.WikipediaCrawl;
import com.mani.wikipedia.WikipediaCrawl.Map;
import com.mani.wikipedia.WikipediaCrawl.Reduce;

public class JoinClass {
	
	public static final String tweets = "part-r-00000";

		public static class MapJoinMapper extends Mapper<LongWritable, Text, Text, Text> {
			static HashMap<String, String> tweetCache = new HashMap<String, String>();

			public void setup(Context context) throws IOException,FileNotFoundException 
			{
				Path[] localFiles = DistributedCache.getLocalCacheFiles((JobConf) context.getConfiguration());
				String fileName = new Path(tweets).getName();
				System.out.println("inside mapper setup" + fileName );
				if (null != localFiles && localFiles.length>0) {
					for (Path cachePath : localFiles) {
						System.out.println("cache name" + cachePath.getName());
						if (cachePath.getName().equals(tweets)) 
						{
							BufferedReader br = new BufferedReader(new FileReader(cachePath.toString()));
							String localFileLine = "";
							System.out.println("line:" + localFileLine);
							while ((localFileLine = br.readLine()) != null) 
							{
								String[] cachedSplit = localFileLine.trim().split("\t");
								System.out.println("cached split" +  cachedSplit);
								if (cachedSplit.length >=2)
									tweetCache.put(cachedSplit[0].trim(),cachedSplit[1].trim());
							}
							break;
						}
					}
				}
				System.out.println("cache:" + tweetCache);
				System.out.println("setup done.");

			}

			public void map(LongWritable key, Text values, Context context)	throws IOException, InterruptedException 
			{			
//				FileSplit fileSplit = (FileSplit) context.getInputSplit();
//				String FileName = fileSplit.getPath().getName();
//				
//				if(FileName.toLowerCase().replaceAll("[^A-Za-z]","").equalsIgnoreCase("partr"))
//				{
					String[] splitValue = values.toString().split(" ");
					if(splitValue.length>=2)
					{
						if(tweetCache.containsKey(splitValue[0]))
							context.write(new Text(tweetCache.get(splitValue[0])), new Text(values.toString()));
					}
//				}
			}

		}

 public JoinClass(String tweetInput,String wikipediaInput, String output) throws IOException, ClassNotFoundException, InterruptedException
	{

		Configuration conf = new Configuration();
		
		Job job1 = new Job(conf);

		job1.setJarByClass(JoinClass.class);
		job1.setNumReduceTasks(0);
		
		DistributedCache.addCacheFile(new Path(tweetInput+"/part-r-00000").toUri(), job1.getConfiguration());
	

		job1.setMapperClass(MapJoinMapper.class);
		
		job1.setOutputKeyClass(Text.class);
		job1.setOutputValueClass(Text.class);
		job1.setInputFormatClass(TextInputFormat.class);
		job1.setOutputFormatClass(TextOutputFormat.class);

		FileInputFormat.setInputPaths(job1, new Path(wikipediaInput));
		FileOutputFormat.setOutputPath(job1, new Path(output));

		job1.waitForCompletion(true);
	}
}
