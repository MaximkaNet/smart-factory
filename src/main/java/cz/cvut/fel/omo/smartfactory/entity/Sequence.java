package cz.cvut.fel.omo.smartfactory.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Sequence {
    @Id
    @GeneratedValue
    private Long id;
}
