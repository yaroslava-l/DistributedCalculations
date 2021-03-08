import java.util.ArrayList;

public class Controller implements Runnable{

        private ArrayList<StringBuilder> strings;
        private Boolean isDone;
    public Controller(ArrayList<StringBuilder>strings,Boolean condition){
            this.strings = strings;
            this.isDone = condition;
        }

        @Override
        public void run() {
            int[]sum = new int[4];

            for (int i=0;i<4;i++) {
                StringBuilder s= strings.get(i);
                for (int k = 0; k < s.length(); k++) {
                    if(s.charAt(k) == 'A' || s.charAt(k) == 'B'){
                        sum[i]++;
                    }
                }
            }

            if((sum [0]==sum [1] &&sum [0]==sum [2])||
                    (sum [0]==sum [2] &&sum [0]==sum [3])||
                    (sum [0]==sum [1] &&sum [0]==sum [3])||
                    (sum [1]==sum [2] &&sum [2]==sum [3])){
                System.out.println("Done!");
                System.out.println(strings);
                isDone = true;
            }
        }
    }
