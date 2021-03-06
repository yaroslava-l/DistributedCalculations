import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Main {
    private static Random r = new Random();

    public static void main(String[] args) throws IOException {
        DatabaseFile db = new DatabaseFile("test.txt");

         new FileWriter(String.valueOf(db));

        DatabaseController dbController = new DatabaseController(db);

        startAllRunners(dbController);
    }

    private static void startAllRunners(DatabaseController dbController) {
        Runner readUsername = new Runner(3, () -> {
            dbController.getUsername(randomPhone());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        readUsername.startAll();

        Runner readPhone = new Runner(3, () -> {
            dbController.getPhoneNumbers(randomUsername());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        readPhone.startAll();

        Runner addUser = new Runner(3, () -> {
            dbController.addRecord(randomUsername(), randomPhone());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        addUser.startAll();

        Runner removeUser = new Runner(3, () -> {
            dbController.deleteRecord(randomUsername(), randomPhone());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        removeUser.startAll();
    }

    private static String randomPhone() {
        return Integer.toString(Math.abs(r.nextInt()) % 10);
    }

    private static String randomUsername() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 1;
        return r.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}

class Runner {
    private ThreadPoolExecutor executor;
    private Runnable action;

    public Runner(int n, Runnable action) {
        this.action = action;
        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(n);
    }

    public void startAll() {
        executor.execute(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                action.run();
            }
        });
    }
}