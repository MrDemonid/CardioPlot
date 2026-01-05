package mr.demonid.chart.exporter.impl;

import lombok.AllArgsConstructor;
import mr.demonid.chart.exporter.ChartExporter;
import mr.demonid.chart.services.ChartScaleService;
import mr.demonid.util.UnitConverter;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


@AllArgsConstructor
public class ChartExporterImpl implements ChartExporter {

    private final ChartScaleService scaleService;


    /**
     * Конвертация графика в PNG-файл с заданной плотностью детализации (DPI).
     * График будет изменен под новую детализацию.
     *
     * @param chart    График.
     * @param ptWidth  Ширина создаваемого изображения в pt (1/72 inch).
     * @param ptHeight Высота создаваемого изображения в pt (1/72 inch).
     * @param dpi      Плотность (точек на дюйм).
     */
    @Override
    public byte[] toPNG(JFreeChart chart, float ptWidth, float ptHeight, int dpi) throws IOException {
        scaleChartElements(chart, dpi);
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            ChartUtils.writeChartAsPNG(
                    out,
                    chart,
                    UnitConverter.pointsToPixels(ptWidth, dpi),
                    UnitConverter.pointsToPixels(ptHeight, dpi)
            );
            return out.toByteArray();
        }
    }


    /**
     * Масштабирование графика под новую плотность печати.
     * @param chart График.
     * @param dpi   Плотность печати в точках на дюйм.
     */
    private void scaleChartElements(JFreeChart chart, int dpi) {
        float scale = UnitConverter.dpiScale(dpi);

        scaleService.scaleSeriesStrokes(chart, scale);
        scaleService.scaleChartFonts(chart, scale);
        scaleService.scaleGridLines(chart, scale);
        scaleService.scaleMarkerLines(chart, scale);
        scaleService.scaleSeriesShapes(chart, scale);
    }

}
