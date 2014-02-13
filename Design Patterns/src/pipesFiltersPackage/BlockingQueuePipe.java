package pipesFiltersPackage;

// Implementation of BlockingQueuePipe.
 

import java.nio.channels.Pipe;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

// Extends pipe to use BlockingQueue
public class BlockingQueuePipe extends Pipe{
	
    BlockingQueue<Object> queue = new LinkedBlockingQueue<Object>();

    // Insert an item into BlockingQueue.
    public void put(Object item) throws Exception {
        queue.put(item);
    }

    // Get an item from the BlockingQueue.
    public Object get() throws Exception {
        // take() method will block if the queue is empty
        Object item = queue.take();
        return item;
    }

	@Override
	public SinkChannel sink() {
		// pipe is implemented using BlockingQueue so use get and put
		return null;
	}

	@Override
	public SourceChannel source() {
		// pipe is implemented using BlockingQueue so use get and put
		return null;
	}

}
