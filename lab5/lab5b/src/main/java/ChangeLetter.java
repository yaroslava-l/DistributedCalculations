import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class ChangeLetter implements Runnable{
    StringBuilder string;
    CyclicBarrier cyclicBarrier;
    int id;
    public ChangeLetter(int id,StringBuilder string,CyclicBarrier cyclicBarrier){
        this.id=id;
        this.string = string;
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {
        Random random = new Random();
        int letterIndex = random.nextInt(7);
        char letter = string.charAt(letterIndex);
        switch (letter){
            case 'A':
                string.setCharAt(letterIndex,'C');
                System.out.println(String.format("A->C"));
                break;
            case 'B':
                string.setCharAt(letterIndex,'D');
                System.out.println(String.format("B->D"));
                break;
            case 'C':
                string.setCharAt(letterIndex,'A');
                System.out.println("C->A");
                break;
            case 'D':
                string.setCharAt(letterIndex,'B');
                System.out.println(String.format("D->B"));
                break;
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(id+" "+string);
        try {
            cyclicBarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}