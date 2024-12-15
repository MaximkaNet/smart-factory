package cz.cvut.fel.omo.smartfactory.factory;

import cz.cvut.fel.omo.smartfactory.entity.event.OutageEvent;
import cz.cvut.fel.omo.smartfactory.entity.event.RepairFinishedEvent;
import cz.cvut.fel.omo.smartfactory.entity.event.RepairStartedEvent;
import cz.cvut.fel.omo.smartfactory.entity.factory.Factory;
import cz.cvut.fel.omo.smartfactory.entity.factoryequipment.Machine;
import cz.cvut.fel.omo.smartfactory.entity.person.Person;
import cz.cvut.fel.omo.smartfactory.entity.person.RepairmanPool;
import cz.cvut.fel.omo.smartfactory.entity.report.ConsumptionReport;
import cz.cvut.fel.omo.smartfactory.entity.report.EventReport;
import cz.cvut.fel.omo.smartfactory.entity.report.OutagesReport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ReportTest {
    Factory factory;
    Integer ticks;
    ZonedDateTime afterSimulationTime;
    ZonedDateTime factoryFoundationTime;

    @BeforeEach
    public void setUp() {
        factory = Factory.builder()
                .setName("Factory 1")
                .setTickLength(1000 * 60 * 60) // 1 hour tick length
                .setFoundationDate(LocalDate.of(2024, 12, 4))
                .setRepairmanPool(
                        RepairmanPool.builder()
                                .addRepairman("Janko", "Third", .2f)
                                .build()
                )
                .addMachine("M", "M1", 200f, 1)
                .addMachine("M", "M2", 200f, 1)
                .addMachine("M", "M3", 200f, 1)
                .addMachine("M", "M4", 200f, 1)
                .addMachine("M", "M5", 200f, 1)
                .build();

        for (Machine machine : factory.getMachines()) {
            machine.setActualHealth(0);
            factory.getEventManager().notifyListeners(new OutageEvent(1, machine, factory.now()));
        }

        ticks = 30;
        factory.simulate(ticks);

        // added nd subtracted one minute as I want the interval to contain all events
        afterSimulationTime = ZonedDateTime.ofInstant(factory.getEventManager().getEventHistory().get(factory.getEventManager().getEventHistory().size() - 1).getGeneratedAt(), ZoneId.of("UTC")).plusMinutes(1);
        factoryFoundationTime = ZonedDateTime.ofInstant(factory.getFoundationDate(), ZoneId.of("UTC")).minusMinutes(1);
    }

    @Test
    public void OutagesReportCalculatesStatisticsForGivenTimeSpan() {
        OutagesReport outagesReport = new OutagesReport(factoryFoundationTime, afterSimulationTime, factory);
        System.out.println(outagesReport);

        assertNotEquals(Duration.ZERO, outagesReport.getAvgOutageTime());
        assertNotEquals(Duration.ZERO, outagesReport.getAvgWaitingTime());
        assertNotEquals(Duration.ZERO, outagesReport.getLongestOutageTime());
        assertNotEquals(Duration.ZERO, outagesReport.getShortestOutageTime());
        assertEquals(5, outagesReport.getOutageSourcesSorted().size());
    }

    @Test
    public void EventReportCalculatesStatisticsForGivenTimeSpan() {
        EventReport eventReport = new EventReport(factoryFoundationTime, afterSimulationTime, factory);
        System.out.println(eventReport);

        for (Optional<Person> p : eventReport.getEventCheckerMap().keySet()) {
            System.out.println(p);
        }

        assertEquals(5, eventReport.getEventSourceMap().size()); // 5 machines send events
        assertEquals(3, eventReport.getEventTypeMap().size()); // three event types at all
        assertEquals(5, eventReport.getEventTypeMap().get(OutageEvent.class).size()); // 5 outage events
        assertEquals(5, eventReport.getEventTypeMap().get(RepairStartedEvent.class).size()); // 5 repair started events
        assertEquals(5, eventReport.getEventTypeMap().get(RepairFinishedEvent.class).size()); // 5 repair finished events
        assertNotEquals(eventReport.getEvents().size(), eventReport.getEventCheckerMap().get(Optional.empty()).size()); // at lest OutageEvents should be checked by the repairmen
    }

    @Test
    // TODO: the test fails as the consumption is not yet implemented
    public void ConsumptionReportCalculatesStatisticsForGivenTimeSpan() {
        ConsumptionReport consumptionReport = new ConsumptionReport(factoryFoundationTime, afterSimulationTime, factory);
        System.out.println(consumptionReport);
    }

}
