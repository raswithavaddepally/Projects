package com.mani.Twitter;

import java.io.IOException;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.TreeMap;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;




public class TwitterTopWords {
	public static class Map extends Mapper<LongWritable, Text, Text, IntWritable> {
	 
	    private TreeMap<Integer, Text> repToRecordMap = new TreeMap<Integer, Text>();
	    
	    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException  {
	        String line = value.toString();
	        StringTokenizer tokenizer = new StringTokenizer(line, "\t");
            String word = "";
            int Count = 0;
            if (tokenizer.hasMoreTokens()) 
                word = tokenizer.nextToken();
            
            if (tokenizer.hasMoreTokens()) 
                Count = Integer.parseInt(tokenizer.nextToken());
            
            repToRecordMap.put(Count, new Text(Count + "\t" + word));
          
            if (repToRecordMap.size() > 10000) 
                repToRecordMap.remove(repToRecordMap.firstKey());		
		}
		
		protected void cleanup(Context context) throws IOException, InterruptedException
		{
			for (Text t : repToRecordMap.values()) 
                context.write(new Text(t.toString().split("\t")[1]), new IntWritable(Integer.parseInt(t.toString().split("\t")[0])));
            
		} 
	}

	public static class Reduce extends Reducer<Text, IntWritable, Text, NullWritable> {
		private TreeMap<Integer, Text> repToRecordMap = new TreeMap<Integer, Text>();
        private Text outputValue = new Text();

	    public void reduce(Text key, Iterator<IntWritable> values, Context context) 
	      throws IOException, InterruptedException {
	        
	    	while (values.hasNext()) 
       	 {
                String[] valueStr = values.next().toString().split("\t");
                //String visitorName = valueStr[1];
                String visitorName = key.toString();
                int visitorCount = Integer.parseInt(valueStr[0]);
                outputValue.set("" + visitorCount + "\t" + visitorName);
                repToRecordMap.put(visitorCount, outputValue);
            
                if (repToRecordMap.size() > 10000)
                    repToRecordMap.remove(repToRecordMap.firstKey());
                
            }
        }
        
        protected void cleanup(Context context) throws IOException, InterruptedException
        {
       	 for (Text t : repToRecordMap.values()) 
                context.write(new Text(t.toString().split("\t")[1]),NullWritable.get());// new IntWritable(Integer.parseInt(t.toString().split("\t")[0])));
        }
	  }
	 
	
	
	

	
	public TwitterTopWords(String input, String output) throws IOException, ClassNotFoundException, InterruptedException
	{
		
		Configuration conf = new Configuration();
        
        Job job = new Job(conf, "TwitterTopWords");
        job.setJarByClass(TwitterTopWords.class);
    
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
