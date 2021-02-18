package b;

import javax.swing.*;

public class ConcurentThread {
    private JSlider slider;
    private Thread thread1 ;
    private Thread thread2 ;

    ConcurentThread(JSlider slider) {
        this.slider = slider;
        thread1 = new Thread(() -> {
            while (true) {
                {
                    int slidervalue = slider.getValue();
                    while (slidervalue != 10) {
                        if (slidervalue > 10)
                            slider.setValue(slidervalue--);
                        else
                            slider.setValue(slidervalue++);
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }}

                }
            }
        }, "Threead1");
        thread2 = new Thread(() -> {
            while (true) {
                {
                    int slidervalue = slider.getValue();
                    while (slidervalue != 90) {
                        if (slidervalue > 90)
                            slider.setValue(slidervalue--);
                        else
                            slider.setValue(slidervalue++);
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        }, "Threead2");
        thread1.setDaemon(true);
        thread2.setDaemon(true);
        thread1.start();
        thread2.start();
    }

    public String getPrior1(){Integer threadprior=thread1.getPriority();
        return threadprior.toString();};
    public String getPrior2(){Integer threadprior=thread2.getPriority();
        return threadprior.toString();};
    public void inc1Priority() { thread1.setPriority(thread1.getPriority()+1); }
    public void dec1Priority() { thread1.setPriority(thread1.getPriority()-1); }
    public void inc2Priority() { thread2.setPriority(thread2.getPriority()+1); }
    public void dec2Priority() { thread2.setPriority(thread2.getPriority()-1); }
}

