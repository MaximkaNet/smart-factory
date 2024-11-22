package cz.cvut.fel.omo.smartfactory.entity.event;

import cz.cvut.fel.omo.smartfactory.entity.person.Person;
import cz.cvut.fel.omo.smartfactory.entity.person.Repairman;

public class OutageEvent extends Event implements Comparable<OutageEvent> {
    public OutageEvent(Integer priority) {
        super(priority);
    }

    public void repair(Person person) {
        if (person.getClass() != Repairman.class){
            return;
        }
        Repairman repairman = (Repairman) person;
        repairman.startRepair(this);
    }

    @Override
    public boolean check(Person person) {
        if (person.getClass() != Repairman.class){
            return false;
        }
        System.out.println("Repairman: " + person + " checked: " + this);
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
