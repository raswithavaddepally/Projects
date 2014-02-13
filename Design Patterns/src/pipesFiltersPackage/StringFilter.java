package pipesFiltersPackage;

/**
 * Implementation of StringFilter. Searches for the regular expression.
 * @author Premkumar
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringFilter extends Filter{

	// Pattern to be searched.
	Pattern pattern = null;
	
	// Check if the pattern matches on input, if yes then send it through outputPipe.
	@Override
    public void process() throws Exception {
		
		// Read the string from inputPipe
        String curString = (String)inputPipe.get();
        System.out.println("curString :" +curString);
        // does it matches with pattern?
        Matcher match = pattern.matcher(curString);
        
        if(match.find())
        {
        	// Yes it matches. Send it through outputPipe.
        	System.out.println("pattern found: ");
        	outputPipe.put(match.group());        	
        }
        else
        	System.out.println("pattern not found: ");
    }
	
	// Initialize the pattern to be searched.
	public int setPattern(String regex)
	{
		pattern = Pattern.compile(regex);
		return 0;
	}
}