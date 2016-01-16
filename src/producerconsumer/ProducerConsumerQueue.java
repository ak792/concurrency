package producerconsumer;


import java.util.LinkedList;
import java.util.concurrent.Semaphore;

//solves producer-consumer problem
//problem statement: given N producers and M consumers, devise a fixed-size queue such that the producers make work, put it in the
//queue, and the consumers do it
public class ProducerConsumerQueue<T> {

    private final LinkedList<T> queue;

    private final Semaphore isNotEmptySemaphore;
    private final Semaphore isNotFullSemaphore;

    public ProducerConsumerQueue(final int maxSize) {
        queue = new LinkedList<>();

        isNotEmptySemaphore = new Semaphore(0); //to start, it is empty. so we should currently be locked

        isNotFullSemaphore = new Semaphore(maxSize); //we can add maxSize elements until we need to lock
    }

    public void enqueue(final T newElem) throws InterruptedException {
        //if is full, then block
        //once it's not full, grab a permit
        isNotFullSemaphore.acquire();

        synchronized (this) {
//            System.out.println("enqueuing " + newElem);
            queue.addLast(newElem);

            //let's tell isNotEmpty that we added another element and add another permit to it
            isNotEmptySemaphore.release();
        }
    }

    public T dequeue() throws InterruptedException {
        //if empty, then block
        //once not empty, grab a permit
        isNotEmptySemaphore.acquire();

        synchronized (this) {
            final T res = queue.removeFirst();

//            System.out.println("dequeueing " + res);


            //let's tell isNotFull that we removed another element and add another permit to it
            isNotFullSemaphore.release();

            return res;
        }
    }

    public synchronized int size() {
        return queue.size();
    }

}
