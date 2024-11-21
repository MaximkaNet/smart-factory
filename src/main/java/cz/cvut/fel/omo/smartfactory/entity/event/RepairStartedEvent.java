package cz.cvut.fel.omo.smartfactory.entity.event;

import cz.cvut.fel.omo.smartfactory.entity.person.Person;

public class RepairStartedEvent extends Event{
    public RepairStartedEvent(Integer priority) {
        super(priority);
    }

    @Override
    public boolean check(Person person) {
        System.out.println("Person: " + person + " has checked repair started on: ...");
        setChecked(true);
        return true;
    }
}
