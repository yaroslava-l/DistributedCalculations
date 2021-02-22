public class Bear implements Runnable {

    private Pot honey;
    private volatile boolean isAwake;

    public Bear(Pot honey) {
        this.honey = honey;
        this.isAwake = false;

    }

    public synchronized void wakeUpBear() {
        isAwake = true;
        notify();
    }

    @Override
    public void run() {
        while (true) {
            synchronized (this) {
                while (!isAwake){
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                honey.eatAllHoney();
                isAwake = false;
                notifyAll();
            }
        }
    }
}
