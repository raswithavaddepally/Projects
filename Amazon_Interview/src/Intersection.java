import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class Intersection {
	
	
	public static List<Integer> intersection(List<Integer> a, List<Integer> b)
	{
		Map<Integer, Integer> aMap = new HashMap<Integer, Integer>();
		Map<Integer, Integer> bMap = new HashMap<Integer, Integer>();
		
		List<Integer> result = new LinkedList<Integer>();
		for(Integer aValue : a)
		{
			aMap.put(aValue, aValue);
		}
	
		for(Integer bValue : b)
		{
			bMap.put(bValue, bValue);
		}
		
		for (Entry<Integer, Integer> entry : aMap.entrySet())
		{
		    
			if(bMap.containsKey(entry.getKey()))
			{
				if(!result.contains(entry.getKey()))
					
				{
					result.add(entry.getKey());
				}
			}
		}
		return result;
	}

}
