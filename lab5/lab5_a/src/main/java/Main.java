
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Main {
    private static int numberOfSoldats = 200;
    private static int dataForSoldats  = 3;

    public static void main(String[] args) {
        Boolean[] recruits = new Boolean[numberOfSoldats  * dataForSoldats ];
        CyclicBarrier barrier = new CyclicBarrier(numberOfSoldats , new Barrier(recruits));
        Thread[] workers = new Thread[numberOfSoldats ];

        for (int i = 0; i < numberOfSoldats ; ++i) {
            workers[i] = new Thread(new RecruitsThread(barrier, recruits,i * dataForSoldats, (i + 1) * dataForSoldats));
        }

        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < recruits.length; ++i) {
            recruits[i] = (random.nextInt() % 2 == 0? false: true);
        }

        System.out.println(recruits);
        for (Thread worker : workers)
            worker.start();
    }
}

class RecruitsThread implements Runnable {

    private final CyclicBarrier barrier;
    private final Boolean[] recruits;
    private final int start, end;

    RecruitsThread(CyclicBarrier barrier, Boolean[] recruit, int start, int end) {
        this.barrier = barrier;
        this.recruits = recruit;
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        while (!isDone(recruits)) {
            for (int i = start; i < end; ++i) {
                if (i == 0)
                    continue;

                Boolean sold1 = recruits[i - 1];
                Boolean sold2 = recruits[i];
                if (sold1 != sold2 && sold1 == true) {
                    recruits[i - 1]=recruits[i - 1]?false :true;
                    recruits[i]=recruits[i]?false :true;
                }
            }

            try {
                //System.out.println("finished sorting");
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }


    private boolean isDone(Boolean[] recruits) {
        for (int i = 0; i < recruits.length - 1; i++) {
            Boolean sold1 = recruits[i];
            Boolean sold2 = recruits[i+1];
            if (sold1 != sold2 && sold1 == true)
                return false;
        }
        return true;
    }
}