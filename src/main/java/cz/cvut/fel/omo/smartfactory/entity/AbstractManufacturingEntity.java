package cz.cvut.fel.omo.smartfactory.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Inheritance
public abstract class AbstractManufacturingEntity {
    @Id
    @GeneratedValue
    private Long id;
}
