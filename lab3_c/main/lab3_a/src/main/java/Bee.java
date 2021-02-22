public class Bee implements Runnable {

    private Pot pot;
    private volatile boolean isActive;
    private Bear bear;


    public Bee(Pot pot, Bear bear) {
        this.pot = pot;
        this.isActive = true;
        this.bear = bear;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(500);

            } catch (Exception e) {
                e.printStackTrace();
            }
            pot.addHoney();

            if (pot.isFull()) {
                bear.wakeUpBear();
            }
            isActive = false;
        }
    }

}
