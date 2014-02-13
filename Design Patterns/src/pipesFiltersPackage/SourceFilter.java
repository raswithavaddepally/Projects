package pipesFiltersPackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class SourceFilter extends Filter {
	
	// Input file.
	File file = new File("Names.txt");
	BufferedReader infile = null;
	
	public SourceFilter()
	{
		try {
			// Create Buffered reader to read the input file.
			infile = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException ex) {
			System.err.println(" SourceFilter -- Exception Caught: " + ex.getMessage());
		}
	}
	
	// Make sure we have valid outputPipe.
	@Override
	public void startup() throws Exception {
        if(outputPipe == null) {
            throw new Exception("Output Pipe Must Be Implemented");
        }
    }

	// Read names from the file and send them through pipe.
	@Override
    public void process() throws Exception {		
		// Read each name until we reach end of file
		String curLine = infile.readLine();
		if(curLine != null)
		{
			// Insert it into Pipe.
			outputPipe.put(curLine);
		}
		else
		{
			// We reached End of the file, stop reading and exit.
			infile.close();
			Thread curThread = Thread.currentThread();
			curThread.stop();
		}			
    }
}
