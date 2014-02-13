package pipesFiltersPackage;

/**
 * Implementation of Filter.
 * @author Premkumar
 */

import java.nio.channels.Pipe;

// This class receives the Objects from inputPipe and forwards them to outputPipe.
public class Filter {
	BlockingQueuePipe inputPipe = null;
	BlockingQueuePipe outputPipe = null;
	
	// Function to make sure pipes are set properly.
	public void startup() throws Exception
	{
		if(inputPipe == null) {
            throw new Exception("Input Pipe Must Be Provided");
        }
		else if(outputPipe == null) {
            throw new Exception("Output Pipe Must Be Provided");
        }
	}
	
	// Execute the filter functionality.
    public void process() throws Exception
    {
    	Object item = inputPipe.get();
    	outputPipe.put(item);    	
    }

    // Function to set inputPipe.
    public void setInputPipe(Pipe pout)
    {
    	inputPipe = (BlockingQueuePipe) pout;
    }

    // Function to set outputPipe.
    public void setOutputPipe(Pipe pout)
    {
    	outputPipe = (BlockingQueuePipe) pout;
    }
    	
}
