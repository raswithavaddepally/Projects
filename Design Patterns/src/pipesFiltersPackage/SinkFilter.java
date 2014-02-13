package pipesFiltersPackage;

/**
 * Implementation of SinkFilter.
 */

public class SinkFilter extends Filter{
	
	// We need to check only inputPipe.
	@Override
	public void startup() throws Exception {
        if(inputPipe == null) {
            throw new Exception("Input Pipe Must Be Provided");
        }
    }

	// Read from the pipe and print it on screen.
	@Override
    public void process() throws Exception {
        String curString = (String)inputPipe.get();
        System.out.println("Sink: " + curString);
    }
	
}
