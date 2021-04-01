import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {
    GameWindow() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        this.setSize(new Dimension(1000, 800));

        Dimension dim = getToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - 500, dim.height / 2 - 350);
        DuckHuntGame panel = new DuckHuntGame(this, 988, 688);
        add(panel);
        setVisible(true);
    }
}