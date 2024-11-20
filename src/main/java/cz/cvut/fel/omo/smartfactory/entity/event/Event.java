package cz.cvut.fel.omo.smartfactory.entity.event;

import cz.cvut.fel.omo.smartfactory.entity.person.Person;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
abstract public class Event {
    private boolean isChecked;
    private Integer priority;

    public boolean check(Person person){
        // TODO
        return false;
    }
}
