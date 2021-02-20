
public class Ivanov implements Runnable{

    private void Ivanov() {
        if (mutex == 1) {
            mutex = 0;
            int w = random.nextInt(500);

            mutex = 1;
        }

    }
    @Override
    public void run() {
        while (!full) {
            Ivanov();
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        while (mutex == 0) {}
        storageWeight += takenWeight;
        takenWeight = 0;
        storageBar.setValue(storageWeight);
        takenBar.setValue(takenWeight);
        takenBar.setString(String.valueOf(takenWeight));
        storageBar.setString(String.valueOf(storageWeight));
    }
}

