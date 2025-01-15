package cz.cvut.fel.omo.smartfactory.equipment;

import cz.cvut.fel.omo.smartfactory.CommonMaterial;
import cz.cvut.fel.omo.smartfactory.identifier.Identifier;
import cz.cvut.fel.omo.smartfactory.utils.JobUtils;
import cz.cvut.fel.omo.smartfactory.visitor.FactoryVisitor;

/**
 * The robot
 */
public final class Robot extends AbstractEquipment {

    private final float JOB_STEP = JobUtils.stepDuration(1);

    /**
     * Create factory equipment
     *
     * @param id     The equipment name
     * @param health The maximum health
     */
    public Robot(Identifier id, float health) {
        super(id, health);
    }

    @Override
    public void process(long dt) {
        setActualHealth(-0.5f * dt + getActualHealth());
        usageTime += dt;

        this.jobProgress += JOB_STEP;

        this.getUsageConsumer().accept(1.0f); // One usage per call this method
        this.getElectricityConsumer().accept(10.0f); // 10 kW/hour
        this.getOilConsumer().accept(0.2f); // 0.2 liter/hour
        this.getMaterialConsumer().accept(new CommonMaterial());
    }

    @Override
    public void acceptVisitor(FactoryVisitor visitor) {
        visitor.visitRobot(this);
    }
}
