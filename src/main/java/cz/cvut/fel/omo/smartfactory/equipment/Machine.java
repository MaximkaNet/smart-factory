package cz.cvut.fel.omo.smartfactory.equipment;

import cz.cvut.fel.omo.smartfactory.CommonMaterial;
import cz.cvut.fel.omo.smartfactory.identifier.Identifier;
import cz.cvut.fel.omo.smartfactory.utils.JobUtils;
import cz.cvut.fel.omo.smartfactory.visitor.FactoryVisitor;

/**
 * The machine
 */
public final class Machine extends AbstractEquipment {

    private final float JOB_STEP = JobUtils.stepDuration(2);

    /**
     * Create factory equipment
     *
     * @param id     The equipment name
     * @param health The maximum health
     */
    public Machine(Identifier id, float health) {
        super(id, health);
    }

    @Override
    public void process(long dt) {
        setActualHealth(-0.2f * dt + getActualHealth());
        usageTime += dt;

        jobProgress += JOB_STEP;

        this.getUsageConsumer().accept(1.0f); // One usage per call this method
        this.getElectricityConsumer().accept(20.0f); // 20 kW/hour
        this.getOilConsumer().accept(1.0f); // 1 liter/hour
        this.getMaterialConsumer().accept(new CommonMaterial());
    }

    @Override
    public void acceptVisitor(FactoryVisitor visitor) {
        visitor.visitMachine(this);
    }
}
