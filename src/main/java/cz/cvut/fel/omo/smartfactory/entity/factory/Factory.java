package cz.cvut.fel.omo.smartfactory.entity.factory;

import cz.cvut.fel.omo.smartfactory.entity.event.EventFacade;
import cz.cvut.fel.omo.smartfactory.entity.factory.factoryObserver.TactSubscriber;
import cz.cvut.fel.omo.smartfactory.entity.person.Person;
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
    private List<Report> reports;
    private Integer tactLengthMilliseconds = 500;
    private Integer currentTact = 1;
    private boolean isRunning = false;
    private List<Person> people;
    private RepairmanPool repairmanPool;
    private List<TactSubscriber> tactSubscribers = new ArrayList<>();
    private EventFacade eventFacade;

    public Factory(String name) {
        this.name = name;
        eventFacade = new EventFacade(this);
    }

    public void startFactory() {
        isRunning = true;
        new Thread(() -> {
            while (isRunning) {
                System.out.println("Tact " + currentTact + " started");
                runTact();
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
        repairmanPool.executeRepairs();
    }

    public boolean stopFactory() {
        if (!isRunning) {
            return false;
        }
        isRunning = false;
        return true;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{"
                + "name=" + name
                + ", isRunning=" + isRunning
                + ", tactLengthMilliseconds=" + tactLengthMilliseconds
                + ", currentTact=" + currentTact + "}";
    }
}
