package cz.cvut.fel.omo.smartfactory.factory;

import cz.cvut.fel.omo.smartfactory.equipment.Machine;
import cz.cvut.fel.omo.smartfactory.equipment.Robot;
import cz.cvut.fel.omo.smartfactory.event.AbstractEvent;
import cz.cvut.fel.omo.smartfactory.event.EventType;
import cz.cvut.fel.omo.smartfactory.event.OutageEvent;
import cz.cvut.fel.omo.smartfactory.identifier.Identifier;
import cz.cvut.fel.omo.smartfactory.report.OutagesReport;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReportTest {
    @Test
    void createSimpleEventReportForGivenTimeSpan() {
        Robot robot = new Robot(Identifier.of(1, "Robot"), 100);
        Robot robot2 = new Robot(Identifier.of(2, "Robot"), 100);
        Machine machine = new Machine(Identifier.of(1, "Machine", "M"), 100);

        Instant now = LocalDateTime.of(2024, 12, 26, 12, 0)
                .atOffset(ZoneOffset.UTC)
                .toInstant();

        List<AbstractEvent> events = List.of(
                new OutageEvent(robot, 1, now), // 12:00
                new OutageEvent(robot, 1, now.plusSeconds(3600)), //13:00
                new OutageEvent(robot, 1, now.plusSeconds(2 * 3600)), // 14:00
                new OutageEvent(robot2, 1, now.plusSeconds(3 * 3600)), // 15:00
                new OutageEvent(machine, 1, now.plusSeconds(4 * 3600)) // 16:00
        );

        OutagesReport report = OutagesReport.createReport(
                events.stream()
                        .filter(event -> event.getType() == EventType.OUTAGE)
                        .map(event -> (OutageEvent) event)
                        .toList(),
                now,
                now.plusSeconds(4 * 3600)
        );

        assertEquals(robot.getId().getShortName(), report.getOutageSourcesSorted().getFirst());
        assertEquals(2, report.getOutageSourcesSorted().size());
    }
}
