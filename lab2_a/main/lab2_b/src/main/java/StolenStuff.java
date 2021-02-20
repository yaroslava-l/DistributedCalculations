import java.util.Random;

public class StolenStuff {
    int id=0;
    int price;

    public StolenStuff() {
        id++;
        Random random=new Random();
        price = random.nextInt(500);
    }

    public int getId() { return id; }

    public int getPrice() { return price; }
}
