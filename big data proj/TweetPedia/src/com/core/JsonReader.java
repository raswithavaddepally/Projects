package com.mani.core;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonReader {
	
	String line;
	
	public JsonReader(String tweet)
	{
		this.line = tweet;
	}
	
	public ArrayList<String> grabTweets() throws ParseException
	{	
		ArrayList<String> tagsList = new ArrayList<String>();
		JSONObject json = (JSONObject)new JSONParser().parse(line);
		
		JSONObject json1 = (JSONObject) json.get("entities");
		
		if(json1 != null && json1.get("hashtags")!=null)
		{
			JSONArray tagsArray =   (JSONArray) json1.get("hashtags");
			Iterator<JSONObject> iterator = tagsArray.iterator();
			while(iterator.hasNext())
			{
				JSONObject hashtags  = iterator.next();
				 tagsList.add(hashtags.get("text").toString());
			}
		}
		return tagsList;
	}
	public ArrayList<String> grabTweetTexts() throws ParseException
	{
		ArrayList<String> tagsList = new ArrayList<String>();
		JSONObject json = (JSONObject)new JSONParser().parse(line);
		
		JSONObject json1 = (JSONObject) json.get("entities");
		
		if(json1 != null && json1.get("text")!=null)
		{
			JSONArray tagsArray =   (JSONArray) json1.get("text");
			Iterator<JSONObject> iterator = tagsArray.iterator();
			while(iterator.hasNext())
			{
				JSONObject hashtags  = iterator.next();
				 tagsList.add(hashtags.get("text").toString());
			}
		}
		
		
		return tagsList;
		
		
	}

}
