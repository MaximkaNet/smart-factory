package cz.cvut.fel.omo.smartfactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.cvut.fel.omo.smartfactory.entity.factory.Factory;
import cz.cvut.fel.omo.smartfactory.entity.person.repairmanPool.RepairmanPool;

public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println("Hello world!");

        // Load factory configuration
//        Factory factory = buildFactory("factory_config.json");
        Factory factory = Factory.builder()
                .setName("My factory")
                .setTickLength(100)
                .setRepairmanPool(
                        RepairmanPool.builder().build()
                )
                .build();
        // Simulate 100 ticks
        factory.simulate(100);

        // Or alternative implementation of simulation
//        while (true) {
//            // sleep
//            Thread.sleep(factory.getTickMs());
//            factory.simulate();
//        }

        // Create report
//        String jsonReport = factory.createOutageReport();

        // Restore factory
        factory.restore("29-11-2024 09:00");
        factory.restore("29-11-2024 10:00");

        // Conduct an inspection of the factory
        factory.inspect();
    }

    public static Factory buildFactory() {
//        FactoryBuilder builder = new FactoryBuilder("Skoda");
//        return builder.build();
        return null;
    }

    public static Factory buildFactory(String config) throws Exception {

        ObjectMapper mapper = new ObjectMapper();

//        byte[] configData = Files.readAllBytes(Path.of(config));

//        JsonNode node = mapper.readTree(configData);

//        node.
//        return new Factory("Test");
        return null;
    }

}
