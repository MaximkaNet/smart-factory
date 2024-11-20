package cz.cvut.fel.omo.smartfactory.entity.factory;

import cz.cvut.fel.omo.smartfactory.entity.person.RepairmanPool;
import cz.cvut.fel.omo.smartfactory.entity.report.Report;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Factory {
    private String name;
    private RepairmanPool repairmanPool;
    private List<Report> reports;
}
