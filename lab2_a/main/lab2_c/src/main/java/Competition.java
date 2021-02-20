import java.util.concurrent.*;

public class Competition extends RecursiveTask {
    private Monk[] participants;
    private int start, end;

    Competition(Monk[] participants, int start, int end) {
        this.participants = participants;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Monk compute() {
        if ((end - start) <= 2) {
            return (participants[start].getEnergy() >participants[end - 1].getEnergy() ? participants[start] : participants[end - 1]);

        } else {
            int middle = (start + end) / 2;

            Competition subtaskA = new Competition(participants, start, middle);
            Competition subtaskB = new Competition(participants, middle, end);

            subtaskA.fork();
            subtaskB.fork();

            Monk a = (Monk) subtaskA.join();
            Monk b = (Monk) subtaskB.join();
            return (a.getEnergy() > b.getEnergy() ? a : b);
        }
    }
    }
