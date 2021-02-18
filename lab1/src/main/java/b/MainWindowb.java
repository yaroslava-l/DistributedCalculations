package b;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicInteger;


public class MainWindowb extends JFrame {
    private JSlider slider = new JSlider();
    private JButton start1 = new JButton("Start1");
    private JButton stop1 = new JButton("Stop1");
    private JButton start2 = new JButton("Start2");
    private JButton stop2 = new JButton("Stop2");
    //private JLabel lathread1=new JLabel();
    //private JLabel lathread2=new JLabel();

    private AtomicInteger semaphore = new AtomicInteger(0);
    private Thread thread1, thread2;

    public void createElement() {
        slider.setSize(300, 20);
        slider.setMaximum(100);
        slider.setMinimum(0);
        slider.setValue(50);
        slider.setMajorTickSpacing(10);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        start1.setSize(150, 40);
        stop1.setSize(150, 40);
        start2.setSize(150, 40);
        stop2.setSize(150, 40);
    }

    public MainWindowb() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(null);
        frame.setVisible(true);
        createElement();

        JPanel sliderpanel = new JPanel();
        JPanel start1panel = new JPanel();
        JPanel start2panel = new JPanel();
        JPanel stop1panel = new JPanel();
        JPanel stop2panel = new JPanel();
        sliderpanel.setBounds(50, 30, 300, 50);
        sliderpanel.setLayout(new BorderLayout());
        sliderpanel.add(slider);

        start1panel.setBounds(50, 80, 150, 50);
        start1panel.setLayout(new BorderLayout());
        start1panel.add(start1);

        start2panel.setBounds(200, 80, 150, 50);
        start2panel.setLayout(new BorderLayout());
        start2panel.add(start2);

        stop1panel.setBounds(50, 150, 150, 50);
        stop1panel.setLayout(new BorderLayout());
        stop1panel.add(stop1);

        stop2panel.setBounds(200, 150, 150, 50);
        stop2panel.setLayout(new BorderLayout());
        stop2panel.add(stop2);

        frame.add(sliderpanel);
        frame.add(start1panel);
        frame.add(start2panel);
        frame.add(stop1panel);
        frame.add(stop2panel);
        //frame.add(lathread1);
        //frame.add(lathread2);
        start1.addActionListener(e -> {
            if (semaphore.compareAndSet(0,1)) {
                stop2.setEnabled(false);
                thread1 = new Thread(() -> {
                    while (!thread1.isInterrupted()||(slider.getValue() != 10)) {
                        if (slider.getValue() > 10)
                            slider.setValue(slider.getValue()-1);
                        else
                            slider.setValue(slider.getValue()+1);
                    }

                });
                thread1.setPriority(1);
                thread1.start();
            }
            else
                JOptionPane.showMessageDialog(this, "Work another thread");
        });
        start2.addActionListener(e -> {
            if (semaphore.compareAndSet(0,1)) {
                stop1.setEnabled(false);
                thread2 = new Thread(() -> {
                    while ( !thread2.isInterrupted()||(slider.getValue() != 90)) {
                        if (slider.getValue() > 90)
                            slider.setValue(slider.getValue()-1);
                        else
                            slider.setValue(slider.getValue()+1);

                        }});
                thread2.setPriority(10);
                thread2.start();
            }
            else
                JOptionPane.showMessageDialog(this, "Work another thread");
        });
        stop1.addActionListener(e -> {
            if (thread1 != null && thread1.isAlive()) {
                thread1.interrupt();
                //Boolean st=thread1.isAlive();
                //lathread1.setText(st.toString());
                semaphore.set(0);
                stop2.setEnabled(true);
            }
        });
        stop2.addActionListener(e->{
            if (thread2 != null && thread2.isAlive()) {
                thread2.interrupt();
                //Boolean st=thread2.isAlive();
                //lathread2.setText(st.toString());
                semaphore.set(0);
                stop1.setEnabled(true);
            }});
    }
}
