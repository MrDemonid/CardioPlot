package mr.demonid.chart.services;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryMarker;
import org.jfree.chart.plot.IntervalMarker;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.ui.Layer;
import org.jfree.data.xy.XYDataset;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;


public class ChartScaleService {


    /**
     * Масштабируем линии, которыми рисуется график.
     *
     * @param chart График.
     * @param scale Коэффициент масштабирования.
     */
    public void scaleSeriesStrokes(JFreeChart chart, float scale) {
        XYPlot plot = chart.getXYPlot();
        XYItemRenderer base = plot.getRenderer();

        if (base instanceof XYLineAndShapeRenderer renderer) {

            XYDataset ds = plot.getDataset();
            if (ds != null) {
                int seriesCount = ds.getSeriesCount();

                for (int series = 0; series < seriesCount; series++) {
                    Stroke original = renderer.getSeriesStroke(series);

                    if (original instanceof BasicStroke bs) {
                        renderer.setSeriesStroke(series, scaleStroke(bs, scale));
                    }
                }
            }
        }
    }


    /**
     * Масштабирование значков в Renderer.
     *
     * @param chart График.
     * @param scale Коэффициент масштабирования.
     */
    public void scaleSeriesShapes(JFreeChart chart, float scale) {
        XYPlot plot = chart.getXYPlot();
        if (plot.getDataset() == null || plot.getDataset().getSeriesCount() == 0) {
            return;
        }
        int seriesCount = plot.getDataset().getSeriesCount();

        XYItemRenderer base = plot.getRenderer();
        if (!(base instanceof XYLineAndShapeRenderer renderer))
            return;

        for (int series = 0; series < seriesCount; series++) {
            Shape original = renderer.getSeriesShape(series);
            if (original != null) {
                Shape scaled = scaleShape(original, scale);
                renderer.setSeriesShape(series, scaled);
            }
        }
    }


    /**
     * Масштабирование маркерных линий в RangeMarkers и DomainMarkers.
     *
     * @param chart График.
     * @param scale Коэффициент масштабирования.
     */
    public void scaleMarkerLines(JFreeChart chart, float scale) {
        XYPlot plot = chart.getXYPlot();
        // Ось Y
        scaleMarkerCollection(plot.getRangeMarkers(Layer.FOREGROUND), scale);
        scaleMarkerCollection(plot.getRangeMarkers(Layer.BACKGROUND), scale);
        // Ось X
        scaleMarkerCollection(plot.getDomainMarkers(Layer.FOREGROUND), scale);
        scaleMarkerCollection(plot.getDomainMarkers(Layer.BACKGROUND), scale);
    }


    /**
     * Масштабирование линий сетки графика.
     *
     * @param chart График.
     * @param scale Коэффициент масштабирования.
     */
    public void scaleGridLines(JFreeChart chart, float scale) {
        XYPlot plot = chart.getXYPlot();
        Stroke grid = plot.getDomainGridlineStroke();
        plot.setDomainGridlineStroke(scaleStroke(grid, scale));

        Stroke range = plot.getRangeGridlineStroke();
        plot.setRangeGridlineStroke(scaleStroke(range, scale));
    }


    /**
     * Масштабирование всех фонтов графика.
     *
     * @param chart График.
     * @param scale Коэффициент масштабирования.
     */
    public void scaleChartFonts(JFreeChart chart, float scale) {
        Function<Font, Font> scaleFont = f -> f.deriveFont(f.getSize2D() * scale);

        // Заголовок
        chart.getTitle().setFont(scaleFont.apply(chart.getTitle().getFont()));

        // Субтитры
        if (chart.getSubtitleCount() > 0) {
            List<?> subtitles = chart.getSubtitles();
            for (Object o : subtitles) {
                if (o instanceof TextTitle tt) {
                    tt.setFont(scaleFont.apply(tt.getFont()));
                }
            }
        }
        XYPlot plot = chart.getXYPlot();
        // Ось X
        ValueAxis domain = plot.getDomainAxis();
        domain.setTickLabelFont(scaleFont.apply(domain.getTickLabelFont()));
        domain.setLabelFont(scaleFont.apply(domain.getLabelFont()));
        // Ось Y
        ValueAxis range = plot.getRangeAxis();
        range.setTickLabelFont(scaleFont.apply(range.getTickLabelFont()));
        range.setLabelFont(scaleFont.apply(range.getLabelFont()));
        // Легенда (если есть)
        if (chart.getLegend() != null) {
            chart.getLegend().setItemFont(scaleFont.apply(chart.getLegend().getItemFont()));
        }
    }


    /*
     * Масштабирование одной линии (сетки или маркерной).
     */
    private Stroke scaleStroke(Stroke stroke, float scale) {
        if (stroke instanceof BasicStroke bs) {
            return new BasicStroke(
                    bs.getLineWidth() * scale,
                    bs.getEndCap(),
                    bs.getLineJoin(),
                    bs.getMiterLimit(),
                    scaleArray(bs.getDashArray(), scale),
                    bs.getDashPhase() * scale
            );
        }
        return stroke;
    }

    /*
     * Масштабирование шаблона линии.
     * @param arr Исходный массив шаблона линии.
     */
    private float[] scaleArray(float[] arr, float scale) {
        if (arr == null)
            return null;
        float[] result = new float[arr.length];

        for (int i = 0; i < arr.length; i++)
            result[i] = arr[i] * scale;

        return result;
    }


    /*
     * Масштабирование значка (фигуры).
     */
    private Shape scaleShape(Shape shape, float scale) {
        if (shape == null)
            return null;
        var at = AffineTransform.getScaleInstance(scale, scale);
        return at.createTransformedShape(shape);
    }

    private void scaleMarkerCollection(Collection<?> markers, float scale) {
        if (markers == null)
            return;

        for (Object o : markers) {
            if (o instanceof ValueMarker vm) {
                Stroke old = vm.getStroke();
                if (old != null) {
                    vm.setStroke(scaleStroke(old, scale));
                }
            } else if (o instanceof IntervalMarker im) {
                Stroke old = im.getStroke();
                if (old != null) {
                    im.setStroke(scaleStroke(old, scale));
                }
            } else if (o instanceof CategoryMarker cm) {
                Stroke old = cm.getStroke();
                if (old != null) {
                    cm.setStroke(scaleStroke(old, scale));
                }
            }
        }
    }

}
