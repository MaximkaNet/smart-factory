package cz.cvut.fel.omo.smartfactory.entity.event;

import cz.cvut.fel.omo.smartfactory.entity.person.Person;
import cz.cvut.fel.omo.smartfactory.entity.person.Repairman;

import java.util.Optional;

public class OutageEvent extends Event implements Comparable<OutageEvent> {
    public OutageEvent(Integer priority) {
        super(priority);
    }

    @Override
    public boolean check(Person person) {
        if (person.getClass() != Repairman.class){
            return false;
        }
        Repairman repairman = (Repairman) person;
        repairman.repair(abstractManufacturingEntity);
        System.out.println("Repairman: " + person + " repaired(checked): " + this);
        isChecked = true;
        return true;
    }

    @Override
    public int compareTo(OutageEvent o) {
        int priorityComparison = Integer.compare(o.getPriority(), this.priority);
        if (priorityComparison != 0) {
            return priorityComparison;
        }
        return generatedAt.compareTo(o.getGeneratedAt());
    }

    @Override
    public String toString() {
        return "OutageEvent{" + "priority=" + priority + " generatedAt=" + generatedAt + '}';
    }
}
