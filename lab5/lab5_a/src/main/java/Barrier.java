
public class Barrier implements Runnable {
    private final Boolean[] recruits;

    Barrier(Boolean[] recruits) {
        this.recruits = recruits;
    }

    @Override
    public void run() {
        boolean finished = true;
        for (int i = 1; i < recruits.length && finished; ++i) {
            Boolean sold1 = recruits[i - 1];
            Boolean sold2 = recruits[i];
            if (sold1 != sold2)
                finished = false;
        }

        for (int i = 0; i < recruits.length ; i++) {
            System.out.print(recruits[i] + " ");
        }
        System.out.println();
    }
}