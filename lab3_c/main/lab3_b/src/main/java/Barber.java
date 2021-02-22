import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
public class Barber implements Runnable {

    private Semaphore barberChair;
    private AtomicBoolean isSleeping = new AtomicBoolean(true);

    public Barber(Semaphore barberChair) {
        this.barberChair = barberChair;
    }

    public void wakeUp() {
        if (isSleeping.get()) {
            isSleeping.set(false);
            barberChair.release();
            System.out.println("Barber is woken up");
        }
    }

    @Override
    public void run() {
        while (true) {
            if (isSleeping.get()) {
                System.out.println("Barber is sleeping");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {}
            } else {
                if (barberChair.tryAcquire()) {
                    isSleeping.set(true);
                } else {
                    System.out.println("Barber is working");
                    barberChair.release();
                }
            }
        }
    }
}
