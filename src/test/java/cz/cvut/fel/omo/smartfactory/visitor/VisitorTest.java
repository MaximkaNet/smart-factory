package cz.cvut.fel.omo.smartfactory.visitor;

import cz.cvut.fel.omo.smartfactory.equipment.Machine;
import cz.cvut.fel.omo.smartfactory.equipment.Robot;
import cz.cvut.fel.omo.smartfactory.factory.Factory;
import cz.cvut.fel.omo.smartfactory.productionunit.ProductionUnitManager;
import cz.cvut.fel.omo.smartfactory.repair.RepairmenPool;
import cz.cvut.fel.omo.smartfactory.worker.Worker;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class VisitorTest {

    @Test
    public void DirectorVisitsAllEntities() {
        // Mock classes
        Worker mockedWorker = mock(Worker.class);
        Machine mockedMachine = mock(Machine.class);
        Robot mockedRobot = mock(Robot.class);

        // Mock behavior for getNext()
        when(mockedMachine.getNext()).thenReturn(mockedRobot);
        when(mockedRobot.getNext()).thenReturn(mockedWorker);

        // Set up ProductionUnitManager with mocks
        ProductionUnitManager productionUnitManager = new ProductionUnitManager();
        productionUnitManager.getAvailableUnits()
                .computeIfAbsent("W", key -> new ArrayList<>())
                .add(mockedWorker);

        productionUnitManager.getAvailableUnits()
                .computeIfAbsent("R", key -> new ArrayList<>())
                .add(mockedRobot);

        productionUnitManager.getAvailableUnits()
                .computeIfAbsent("M", key -> new ArrayList<>())
                .add(mockedMachine);

        // Set up other dependencies
        RepairmenPool pool = new RepairmenPool();
        pool.createRepairman("Repairman 1", 1f);

        Factory factory = Factory.builder()
                .setName("Test factory")
                .setRepairmenPool(pool)
                .setProductionUnitManager(productionUnitManager)
                .build();

        factory.addOrder("Test order 1", 10, new ArrayList<>(Arrays.asList("M", "R", "W")));

        // Visitor visits factory
        Visitor director = new Visitor("Director");
        director.visitFactory(factory.getTreeIterator());

        // Verify accept method calls
        verify(mockedWorker).acceptVisitor(any(Visitor.class));
        verify(mockedMachine).acceptVisitor(any(Visitor.class));
        verify(mockedRobot).acceptVisitor(any(Visitor.class));

        // Verify the order
        InOrder inOrder = inOrder(mockedWorker, mockedMachine, mockedRobot);
        inOrder.verify(mockedMachine).acceptVisitor(any(Visitor.class));
        inOrder.verify(mockedRobot).acceptVisitor(any(Visitor.class));
        inOrder.verify(mockedWorker).acceptVisitor(any(Visitor.class));
    }

    @Test
    public void InspectorVisitsAllEntities() {
        // Mock classes
        Worker mockedWorker = mock(Worker.class);
        Machine mockedMachine = mock(Machine.class);
        Robot mockedRobot = mock(Robot.class);

        // Mock behavior for getNext()
        when(mockedMachine.getNext()).thenReturn(mockedRobot);
        when(mockedRobot.getNext()).thenReturn(mockedWorker);

        // Mock the usage
        when(mockedMachine.getActualHealth()).thenReturn(1000f);
        when(mockedMachine.getMaximumHealth()).thenReturn(2000f);
        when(mockedRobot.getActualHealth()).thenReturn(800f);
        when(mockedRobot.getMaximumHealth()).thenReturn(2000f);

        // Set up ProductionUnitManager with mocks
        ProductionUnitManager productionUnitManager = new ProductionUnitManager();
        productionUnitManager.getAvailableUnits()
                .computeIfAbsent("W", key -> new ArrayList<>())
                .add(mockedWorker);

        productionUnitManager.getAvailableUnits()
                .computeIfAbsent("R", key -> new ArrayList<>())
                .add(mockedRobot);

        productionUnitManager.getAvailableUnits()
                .computeIfAbsent("M", key -> new ArrayList<>())
                .add(mockedMachine);

        // Set up other dependencies
        RepairmenPool pool = new RepairmenPool();
        pool.createRepairman("Repairman 1", 1f);

        Factory factory = Factory.builder()
                .setName("Test factory")
                .setRepairmenPool(pool)
                .setProductionUnitManager(productionUnitManager)
                .build();

        factory.addOrder("Test order 1", 10, new ArrayList<>(Arrays.asList("M", "R", "W")));

        // Visitor visits factory
        Visitor inspector = new Visitor("Inspector");
        inspector.visitFactory(factory.getUsageIterator());

        // Verify accept method calls
        verify(mockedMachine).acceptVisitor(any(Visitor.class));
        verify(mockedRobot).acceptVisitor(any(Visitor.class));

        // Verify the order
        InOrder inOrder = inOrder(mockedWorker, mockedMachine, mockedRobot);
        inOrder.verify(mockedRobot).acceptVisitor(any(Visitor.class));
        inOrder.verify(mockedMachine).acceptVisitor(any(Visitor.class));
    }
}
