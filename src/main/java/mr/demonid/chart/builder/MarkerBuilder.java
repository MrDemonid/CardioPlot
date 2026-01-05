package mr.demonid.chart.builder;

import org.jfree.chart.JFreeChart;


/**
 * Настройка маркеров в таблице.
 */
public interface MarkerBuilder {
    void setPressureMarkers(JFreeChart chart);
    void setPulseMarkers(JFreeChart chart);
}
