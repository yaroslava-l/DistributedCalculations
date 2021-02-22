import java.util.concurrent.Semaphore;

public class Customer implements Runnable {
    private Semaphore customerQueue;
    private Semaphore barberChair;
    private Barber barber;
    private int id;

    public Customer( int id, Semaphore customerQueue, Semaphore barberChair, Barber barber) {
        this.id = id;
        this.customerQueue = customerQueue;
        this.barberChair = barberChair;
        this.barber = barber;
    }

    @Override
    public void run() {
        if (customerQueue.tryAcquire()) {
            while (!barberChair.tryAcquire()) {
                barber.wakeUp();
                System.out.println("Client " + id + " waiting");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Client " + id + " set in barber chair");
            customerQueue.release();
        } else {
            System.out.println("Wait free sits");
        }
    }

}

