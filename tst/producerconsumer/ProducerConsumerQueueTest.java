package producerconsumer;

import org.junit.Assert;
import org.junit.Test;

public class ProducerConsumerQueueTest {
    
    @Test
    public void test_producerConsumerQueue_even() throws InterruptedException {
        final ProducerConsumerQueue<String> producerConsumerQueue = new ProducerConsumerQueue<>(10);

        int numToProduce = 10000;
        int numToConsume = 10000;

        Thread producerThreadA = new Thread(new Producer(producerConsumerQueue, numToProduce), "ThreadA");
        Thread producerThreadB = new Thread(new Producer(producerConsumerQueue, numToProduce), "ThreadB");
        Thread producerThreadC = new Thread(new Producer(producerConsumerQueue, numToProduce), "ThreadC");
        Thread producerThreadD = new Thread(new Producer(producerConsumerQueue, numToProduce), "ThreadD");

        Thread consumerThreadE = new Thread(new Consumer(producerConsumerQueue, numToConsume), "ThreadE");
        Thread consumerThreadF = new Thread(new Consumer(producerConsumerQueue, numToConsume), "ThreadF");
        Thread consumerThreadG = new Thread(new Consumer(producerConsumerQueue, numToConsume), "ThreadG");
        Thread consumerThreadH = new Thread(new Consumer(producerConsumerQueue, numToConsume), "ThreadH");

        producerThreadA.start();
        producerThreadB.start();
        producerThreadC.start();
        producerThreadD.start();
        consumerThreadE.start();
        consumerThreadF.start();
        consumerThreadG.start();
        consumerThreadH.start();

        producerThreadA.join();
        producerThreadB.join();
        producerThreadC.join();
        producerThreadD.join();
        consumerThreadE.join();
        consumerThreadF.join();
        consumerThreadG.join();
        consumerThreadH.join();

        Assert.assertEquals(0, producerConsumerQueue.size());
    }

    @Test
    public void test_producerConsumerQueue_moreProducers() throws InterruptedException {
        final ProducerConsumerQueue<String> producerConsumerQueue = new ProducerConsumerQueue<>(10);

        int numToProduce = 10001;
        int numToConsume = 10000;

        Thread producerThreadA = new Thread(new Producer(producerConsumerQueue, numToProduce), "ThreadA");
        Thread producerThreadB = new Thread(new Producer(producerConsumerQueue, numToProduce), "ThreadB");
        Thread producerThreadC = new Thread(new Producer(producerConsumerQueue, numToProduce), "ThreadC");
        Thread producerThreadD = new Thread(new Producer(producerConsumerQueue, numToProduce), "ThreadD");

        Thread consumerThreadE = new Thread(new Consumer(producerConsumerQueue, numToConsume), "ThreadE");
        Thread consumerThreadF = new Thread(new Consumer(producerConsumerQueue, numToConsume), "ThreadF");
        Thread consumerThreadG = new Thread(new Consumer(producerConsumerQueue, numToConsume), "ThreadG");
        Thread consumerThreadH = new Thread(new Consumer(producerConsumerQueue, numToConsume), "ThreadH");

        producerThreadA.start();
        producerThreadB.start();
        producerThreadC.start();
        producerThreadD.start();
        consumerThreadE.start();
        consumerThreadF.start();
        consumerThreadG.start();
        consumerThreadH.start();

        producerThreadA.join();
        producerThreadB.join();
        producerThreadC.join();
        producerThreadD.join();
        consumerThreadE.join();
        consumerThreadF.join();
        consumerThreadG.join();
        consumerThreadH.join();

        Assert.assertEquals(4, producerConsumerQueue.size());
    }

    @Test
    public void test_producerConsumerQueue_moreConsumers() throws InterruptedException {
        final ProducerConsumerQueue<String> producerConsumerQueue = new ProducerConsumerQueue<>(10);

        int numToProduce = 10000;
        int numToConsume = 10010;

        Thread producerThreadA = new Thread(new Producer(producerConsumerQueue, numToProduce), "ThreadA");
        Thread producerThreadB = new Thread(new Producer(producerConsumerQueue, numToProduce), "ThreadB");
        Thread producerThreadC = new Thread(new Producer(producerConsumerQueue, numToProduce), "ThreadC");
        Thread producerThreadD = new Thread(new Producer(producerConsumerQueue, numToProduce), "ThreadD");

        Thread consumerThreadE = new Thread(new Consumer(producerConsumerQueue, numToConsume), "ThreadE");
        Thread consumerThreadF = new Thread(new Consumer(producerConsumerQueue, numToConsume), "ThreadF");
        Thread consumerThreadG = new Thread(new Consumer(producerConsumerQueue, numToConsume), "ThreadG");
        Thread consumerThreadH = new Thread(new Consumer(producerConsumerQueue, numToConsume), "ThreadH");

        producerThreadA.start();
        producerThreadB.start();
        producerThreadC.start();
        producerThreadD.start();
        consumerThreadE.start();
        consumerThreadF.start();
        consumerThreadG.start();
        consumerThreadH.start();

        producerThreadA.join();
        producerThreadB.join();
        producerThreadC.join();
        producerThreadD.join();
        consumerThreadE.join(1000);
        consumerThreadF.join(1000);
        consumerThreadG.join(1000);
        consumerThreadH.join(1000);

        Assert.assertEquals(0, producerConsumerQueue.size());
    }

    public class Producer implements Runnable {

        private ProducerConsumerQueue<String> producerConsumerQueue;
        private int numToProduce;

        public Producer(ProducerConsumerQueue<String> producerConsumerQueue, int numToProduce) {
            this.producerConsumerQueue = producerConsumerQueue;
            this.numToProduce = numToProduce;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < numToProduce; i++) {
                    producerConsumerQueue.enqueue(String.format("<%s-%s>", Thread.currentThread().getName(), i));
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public class Consumer implements Runnable {

        private ProducerConsumerQueue<String> producerConsumerQueue;
        private int numToConsume;

        public Consumer(ProducerConsumerQueue<String> producerConsumerQueue, int numToConsume) {
            this.producerConsumerQueue = producerConsumerQueue;
            this.numToConsume = numToConsume;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < numToConsume; i++) {
                    producerConsumerQueue.dequeue();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
