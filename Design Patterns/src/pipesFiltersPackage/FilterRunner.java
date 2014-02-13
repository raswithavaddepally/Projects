package pipesFiltersPackage;

/**
 * Implementation of FilterRunner.
 * This class provides mechanism for continuously executing filter process().
 */

public class FilterRunner implements Runnable {
	private Filter filter; 
    
	// Initialize the filter.
    public FilterRunner(Filter filter) {
        this.filter = filter;
    }

    // Continuously run the process (filter) until something is available at inputPipe.
    // process() filters the input and forwards filtered output to the outputPipe.
    public void run() {
        while(true) {
            try {
                filter.process();
            } catch(Exception ex) {           	
                System.err.println("FilterRunner -- Exception Caught: " + ex.getMessage());
            }
        }
    }

}
