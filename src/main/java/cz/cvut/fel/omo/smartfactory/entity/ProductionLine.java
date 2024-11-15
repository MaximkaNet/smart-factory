package cz.cvut.fel.omo.smartfactory.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class ProductionLine {
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany
    @JoinColumn
    private List<Person> people;
}
