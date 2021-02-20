import java.util.*;
import java.util.concurrent.ForkJoinPool;

public class Main {

    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();

        Monk[] participants = new Monk[20];
        Random random = new Random();
        for (int i = 0; i < participants.length; i++) {
            participants[i] = new Monk(random.nextInt(1000));
            System.out.println(participants[i].toString());
        }
        Monk winner = (Monk) pool.invoke(new Competition(participants, 0, participants.length));
        System.out.print("Winner: " + winner.toString());
    }
}
