package lab1.a;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.*;

public class MainWindow extends JFrame{
    ConcurentThread threads;
     JSlider slider=new JSlider();
     JButton start=new JButton("Start");
     JButton increaseThread1Priority=new JButton("Increase priority for thread1");
     JButton increaseThread2Priority=new JButton("Increase priority for thread2");
     JButton decreaseThread1Priority=new JButton("Decrease priority for thread1");
    JButton decreaseThread2Priority=new JButton("Decrease priority for thread2");
    JLabel prior1=new JLabel("Priority1: ");
    JLabel prior2=new JLabel("Priority2: ");
    Font font = new Font("Verdana", Font.PLAIN, 18);
    public void createElements(){
        slider.setSize(500,20);
        slider.setMaximum(100);
        slider.setMinimum(0);
        slider.setValue(50);
        slider.setMajorTickSpacing(10);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        start.setSize(100,40);
        decreaseThread1Priority.setSize(200,50);
        decreaseThread2Priority.setSize(200,50);
        increaseThread1Priority.setSize(200,50);
        increaseThread2Priority.setSize(200,50);
        prior1.setSize(200,40);
        prior1.setFont(font);
        prior2.setSize(200,40);
        prior2.setFont(font);
    }
public MainWindow(){
    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(610, 350);
    frame.setLayout(null);
    frame.setVisible(true);
    createElements();

    JPanel sliderpanel=new JPanel();
    JPanel stpanel=new JPanel();
    JPanel priorityinpanel1=new JPanel();
    JPanel priorityinpanel2=new JPanel();
    JPanel prioritydepanel1=new JPanel();
    JPanel prioritydepanel2=new JPanel();
    sliderpanel.setBounds(0,30,500,50);
    sliderpanel.setLayout(new BorderLayout());
    sliderpanel.add(slider);

    stpanel.setBounds(500,30,100,50);
    stpanel.setLayout(new BorderLayout());
    stpanel.add(start);
   // stpanel.add(stop,BorderLayout.AFTER_LAST_LINE);

    priorityinpanel1.setBounds(0,130,300,75);
    priorityinpanel1.setLayout(new BorderLayout());
    priorityinpanel1.add(increaseThread1Priority);
    priorityinpanel2.setBounds(300,130,300,75);
    priorityinpanel2.setLayout(new BorderLayout());
    priorityinpanel2.add(increaseThread2Priority);

    prioritydepanel1.setBounds(0,200,300,120);
    prioritydepanel1.setLayout(new BorderLayout());
    prioritydepanel1.add(prior1,BorderLayout.AFTER_LAST_LINE);
    prioritydepanel1.add(decreaseThread1Priority);
    prioritydepanel2.setBounds(300,200,300,120);
    prioritydepanel2.setLayout(new BorderLayout());
    prioritydepanel2.add(decreaseThread2Priority);
    prioritydepanel2.add(prior2,BorderLayout.AFTER_LAST_LINE);
    frame.add(stpanel);
    frame.add(sliderpanel);
    frame.add(priorityinpanel1);
    frame.add(priorityinpanel2);
    frame.add(prioritydepanel1);
    frame.add(prioritydepanel2);

    start.addActionListener((ActiveEvent)->{threads=new ConcurentThread(slider);
    prior1.setText("Priority1: "+threads.getPrior1());
    prior2.setText("Priority2: "+threads.getPrior2()); });
    decreaseThread1Priority.addActionListener((ActiveEvent)->{ threads.dec1Priority();prior1.setText("Priority1: "+threads.getPrior1()) ;});
    decreaseThread2Priority.addActionListener((ActiveEvent)->{ threads.dec2Priority();prior2.setText("Priority2: "+threads.getPrior2());});
    increaseThread1Priority.addActionListener((ActiveEvent)->{ threads.inc1Priority();prior1.setText("Priority1: "+threads.getPrior1());});
    increaseThread2Priority.addActionListener((ActiveEvent)->{ threads.inc2Priority();prior2.setText("Priority2: "+threads.getPrior2());});
}
}
