import java.util.*;
import java.util.Random;
    public class Main {
        private int trackWeight = 0;
        private int prevTruckWeight = 0;
        private int storageWeight = 600;
        private int takenWeight = 0;
        private int price = 0;
        private Random random;
        private int mutex = 1;
        private boolean full = false;

        public static void main(String[] args) {

            Thread Thread1 = new Thread(new thread1());
            Thread Thread2 = new Thread(new thread2());
            Thread Thread3 = new Thread(new thread3());

            Thread1.start();
            Thread2.start();
            Thread3.start();
        }


}
