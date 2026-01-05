package mr.demonid.chart.exporter;

import org.jfree.chart.JFreeChart;

import java.io.IOException;


/**
 * Экспорт таблиц.
 */
public interface ChartExporter {
    byte[] toPNG(JFreeChart chart, float ptWidth, float ptHeight, int dpi) throws IOException;
}
