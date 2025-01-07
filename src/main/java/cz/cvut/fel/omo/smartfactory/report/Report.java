package cz.cvut.fel.omo.smartfactory.report;

/**
 * Report interface
 */
public interface Report {
    /**
     * Export report to json
     *
     * @return JSON string
     */
    String exportJson();
}
