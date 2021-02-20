
import java.util.Random;
public class Monk {

    enum Temple {
        GUANIN, GUANYAN
    }

    private Temple temple;
    private int energy;

    Monk(int energy) {
        Random random= new Random();
        this.temple = random.nextBoolean() ? Monk.Temple.GUANYAN : Monk.Temple.GUANIN;
        this.energy = energy;
    }

    public int getEnergy() {
        return energy;
    }

    @Override
    public String toString() {
        return "Temple "
                + (temple == Temple.GUANIN ? "GUAN-IN" : "GUAN-YAN")
                + ", " + energy + " energy";
    }

}
