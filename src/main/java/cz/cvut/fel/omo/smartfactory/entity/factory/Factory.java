package cz.cvut.fel.omo.smartfactory.entity.factory;

import cz.cvut.fel.omo.smartfactory.entity.factory.factoryObserver.TactSubscriber;
import cz.cvut.fel.omo.smartfactory.entity.person.RepairmanPool;
import cz.cvut.fel.omo.smartfactory.entity.report.Report;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Factory {
    private String name;
    private RepairmanPool repairmanPool;
    private List<Report> reports;
    private Integer tactLengthMilliseconds = 500;
    private Integer currentTact = 1;
    private boolean isRunning = false;
    private List<TactSubscriber> tactSubscribers = new ArrayList<>();

    public void startFactory() {
        isRunning = true;
        new Thread(() -> {
            while (isRunning) {
                System.out.println("Tack " + currentTact + " started");
                runTact();
                // waiting for new tact
                try {
                    Thread.sleep(tactLengthMilliseconds);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                currentTact++;
            }
        }).start();
    }

    private void runTact() {
        tactSubscribers.forEach(subscriber -> subscriber.onNewTact(currentTact));
    }

    public boolean stopFactory() {
        if (!isRunning) {
            return false;
        }
        isRunning = false;
        return true;
    }
}
