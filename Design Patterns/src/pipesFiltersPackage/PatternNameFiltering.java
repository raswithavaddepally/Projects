package pipesFiltersPackage;

/**
 * This is the integration class.
 * Demonstrates complete functionality of Filtering.
 * SourceFilter reads names from a file and sends them to filter through pipe.
 * StringFilter reads the names from pipe and applies the pattern on them,
 * if there is match then name is forwarded to SinkFilter.
 * SinkFilter prints the filtered names on the screen.
 * 
 * @author Premkumar
 */

import java.nio.channels.Pipe;

public class PatternNameFiltering {

	public static void main(String args[]) throws Exception {

		// Create pipes required.
		Pipe p1 = new BlockingQueuePipe();
		Pipe p2 = new BlockingQueuePipe();

		// Create SourceFilter and initialize it with pipe.
		SourceFilter sourcef = new SourceFilter();
		sourcef.setOutputPipe(p1);

		// Create a StingFilter and set input and output pipes.
		StringFilter filtStr = new StringFilter();
		filtStr.setInputPipe(p1);
		filtStr.setOutputPipe(p2);

		// Set pattern to be searched
		// Reference : http://docs.oracle.com/javase/tutorial/essential/regex/index.html
		String strtype = "1*0";
		int isset = filtStr.setPattern(strtype);
		System.out.println("isset :" +isset);

		// Create a SinkFilter and set pipe to receive filtered data.
		SinkFilter sinkf = new SinkFilter();
		sinkf.setInputPipe(p2);

		// Make sure everything is initialized properly.
		sourcef.startup();
		filtStr.startup();
		sinkf.startup();

		// Create threads and Run the filters using FilterRunner.
		new Thread(new FilterRunner(sourcef)).start();
		new Thread(new FilterRunner(filtStr)).start();
		new Thread(new FilterRunner(sinkf)).start();
	}

}
