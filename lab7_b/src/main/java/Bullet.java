import javax.swing.*;
import java.awt.*;

public class Bullet extends Thread {
    private int x;
    private int y;

    private int dy = 10;

    private int labelWidth = 50;
    private int labelHeight = 50;

    private DuckHuntGame panel;
    private JLabel bulletLabel;
    private Hunter hunter;

    Bullet(DuckHuntGame panel, Hunter hunter, int x, int y) {
        this.panel = panel;
        this.hunter = hunter;
        this.x = x;
        this.y = y;
        this.bulletLabel = new JLabel(new ImageIcon("src/main/resources/bullet.png"));
        bulletLabel.setSize(new Dimension(labelWidth, labelHeight));
        bulletLabel.setLocation(x - labelWidth / 2, y - labelHeight / 2);
    }

    @Override
    public void run() {
        hunter.addBullet(1);
        panel.add(bulletLabel);
        while (!isInterrupted()) {
            if (y < 0)
                break;
            y -= dy;
            bulletLabel.setLocation(x - labelWidth / 2, y - labelHeight / 2);

            for (Duck duck : panel.ducks)
                if (x > duck.x && x < duck.x + duck.labelWidth && y > duck.y && y < duck.y + duck.labelHeight) {
                    duck.interrupt();
                    this.interrupt();
                    break;
                }
            try {
                sleep(30);
            } catch (InterruptedException e) {
                break;
            }
        }
        panel.remove(bulletLabel);
        panel.repaint();
        hunter.addBullet(-1);
    }
}