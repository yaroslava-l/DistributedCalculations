import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class Main {
    static Semaphore semaphore = new Semaphore(1);

    public static void main(String[] args) {
        int numberBee=5;
        Pot honey = new Pot(10);
        Bear bear = new Bear(honey);
        new Thread(bear).start();

        ExecutorService executors = Executors.newFixedThreadPool(numberBee);
        for (int i = 0; i < numberBee; i++) {
            Runnable bee = new Bee(honey,bear);
            executors.execute(bee);
        }

        executors.shutdown();
    }
       // for(int i=0;i<numberBee;i++)
       // new Thread(new Bee(honey,bear)).start();

    }

