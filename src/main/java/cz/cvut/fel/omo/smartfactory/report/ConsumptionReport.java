package cz.cvut.fel.omo.smartfactory.report;

/**
 * Consumption report
 */
public class ConsumptionReport implements Report {

    private final float oilConsumed;
    private final float oilTotalPrice;

    private final float electricityConsumed;
    private final float electricityTotalPrice;

    private final long materialConsumed;
    private final float materialTotalPrice;

    /**
     * Consumption report
     *
     * @param oilConsumed           The amount of consumed oil
     * @param oilTotalPrice         The total price of consumed oil
     * @param electricityConsumed   The amount of consumed electricity
     * @param electricityTotalPrice The total price of consumed electricity
     * @param materialConsumed      The amount of consumed material
     * @param materialTotalPrice    The total price of consumed material
     */
    public ConsumptionReport(float oilConsumed, float oilTotalPrice, float electricityConsumed, float electricityTotalPrice, long materialConsumed, float materialTotalPrice) {
        this.oilConsumed = oilConsumed;
        this.oilTotalPrice = oilTotalPrice;
        this.electricityConsumed = electricityConsumed;
        this.electricityTotalPrice = electricityTotalPrice;
        this.materialConsumed = materialConsumed;
        this.materialTotalPrice = materialTotalPrice;
    }

    @Override
    public String toString() {
        return "Total consumption report: " +
                System.lineSeparator() +
                "- Material: " + materialConsumed + " pieces" + "/" + materialTotalPrice + "$" +
                System.lineSeparator() +
                "- Oil: " + oilConsumed + " l" + "/" + oilTotalPrice + "$" +
                System.lineSeparator() +
                "- Electricity: " + electricityConsumed + " kw" + "/" + electricityTotalPrice + "$"
                + System.lineSeparator();
    }

    @Override
    public String exportJson() {
        return "";
    }
}
