import java.util.ArrayList;
import java.util.concurrent.CyclicBarrier;

public class Main {
    static volatile Boolean isDone = false;
    public static void main(String[]args) throws InterruptedException {
        ArrayList<StringBuilder> strings = new ArrayList<>();
        strings.add(new StringBuilder("ABCADCD"));
        strings.add(new StringBuilder("CAABCDC"));
        strings.add(new StringBuilder("ACCCBBD"));
        strings.add(new StringBuilder("AAAACBB"));

        CyclicBarrier cyclicBarrier = new CyclicBarrier(4,new Controller(strings,isDone));

        while (isDone.booleanValue() == false){
            new Thread(new ChangeLetter(1,strings.get(0),cyclicBarrier)).start();
            new Thread(new ChangeLetter(2,strings.get(1),cyclicBarrier)).start();
            new Thread(new ChangeLetter(3,strings.get(2),cyclicBarrier)).start();
            new Thread(new ChangeLetter(4,strings.get(3),cyclicBarrier)).start();
            Thread.sleep(500);
        }
    }
}