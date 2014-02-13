import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class testResults {
	
	public class listValues
	{
		ArrayList<Integer> scores = new ArrayList<Integer>();
	}
 	
	
	Map<Integer, Double> calculateFinalScores(List<TestResult> results)
	{
		Map<Integer, Double> finalResults = new HashMap<Integer, Double>();
		
		Map<Integer, listValues> intermediate = new HashMap<Integer, listValues>();
		
		Iterator<TestResult> itr = results.iterator();
		
		while(itr.hasNext())
		{
			TestResult tr = itr.next();
			int stdid =  tr.studentId;
			int score = tr.testScore;
			
				intermediate.get(stdid).scores.add(score);
		}
		
		for (Entry<Integer, listValues> entry : intermediate.entrySet())
		{
			Collections.sort(entry.getValue().scores);
			int sum = 0;
			for(int i=0; i<5; i++)
			{
				
				
				sum += entry.getValue().scores.get(i);
				
			}
			double average = sum/5;
			finalResults.put(entry.getKey(),average);
		}
			
		
			return finalResults;
		
	}
}
