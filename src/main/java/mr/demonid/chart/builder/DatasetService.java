package mr.demonid.chart.builder;

import mr.demonid.model.measurement.types.Measurement;
import org.jfree.data.time.TimeSeriesCollection;

import java.util.List;


/**
 * Построение временных диаграмм по списку показателей.
 */
public interface DatasetService {
    TimeSeriesCollection buildPressureDataset(List<Measurement> list);
    TimeSeriesCollection buildPulseDataset(List<Measurement> list);
}