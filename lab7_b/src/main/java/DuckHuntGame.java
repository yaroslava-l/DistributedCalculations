import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.ConcurrentLinkedQueue;

public class DuckHuntGame extends JPanel {
    int width;
    int height;

    private DuckHuntGame panel;
    private ImageIcon background;
    private int maxDucks;
    ConcurrentLinkedQueue<Duck> ducks;
    private Hunter hunter;
    private GameWindow mainFrame;

    DuckHuntGame(GameWindow mainFrame, int width, int height) {
        setBackground(Color.black);
        this.mainFrame = mainFrame;
        panel = this;
        background = new ImageIcon("src/main/resources/background2.png");
        maxDucks = 8;
        ducks = new ConcurrentLinkedQueue<>();
        hunter = null;
        this.width = width;
        this.height = height;

        setLayout(null);
        setSize(width, height);
        GameMouseAdapter mouseAdapter = new GameMouseAdapter();
        addMouseListener(mouseAdapter);
        Game game = new Game();
        game.start();
    }

    class Game extends Thread {
        @Override
        public void run() {
            if (hunter == null) {
                hunter = new Hunter(mainFrame, panel);
                hunter.start();
            }

            while (!isInterrupted()) {
                if (ducks.size() < maxDucks) {
                    Duck duck = new Duck(width, height, panel);
                    ducks.add(duck);
                    duck.start();
                }
                try {
                    sleep(50);
                } catch (InterruptedException ignored) {
                }
            }
        }
    }

    class GameMouseAdapter extends MouseAdapter {
        @Override
        public void mouseReleased(MouseEvent e) {
            for (Duck duck : ducks)
                if (e.getX() >= duck.x && e.getX() <= duck.x + duck.labelWidth &&
                        e.getY() >= duck.y && e.getY() <= duck.y + duck.labelHeight)
                    duck.interrupt();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background.getImage(), 0, 0, width, height, null);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(width, height);
    }

    @Override
    public Dimension getMaximumSize() {
        return new Dimension(width, height);
    }
}