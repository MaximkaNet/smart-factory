package cz.cvut.fel.omo.smartfactory.entity.event;

import cz.cvut.fel.omo.smartfactory.entity.person.Person;

public class RepairFinishedEvent extends Event{
    public RepairFinishedEvent(Integer priority) {
        super(priority);
    }

    @Override
    public boolean check(Person person) {
        System.out.println("Person: " + person + " has checked repair finished on: ...");
        setChecked(true);
        return true;
    }
}
