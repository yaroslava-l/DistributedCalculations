
import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) {
        Semaphore customerQueue = new Semaphore(10);
        Semaphore barberChair = new Semaphore(1);
        new Thread(new BarberShop(customerQueue, barberChair)).start();
    }

    static class BarberShop implements Runnable {
        private Semaphore customerQueue;
        private Semaphore barberChair;

        public BarberShop(Semaphore customerQueue, Semaphore barberChair) {
            this.customerQueue = customerQueue;
            this.barberChair = barberChair;
        }


        @Override
        public void run() {
            Barber barber = new Barber(barberChair);
            Thread barberThread = new Thread(barber);
            barberThread.start();

            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Thread thread = new Thread(new Customer(i, customerQueue, barberChair, barber));
                thread.start();
            }
        }
    }
}
