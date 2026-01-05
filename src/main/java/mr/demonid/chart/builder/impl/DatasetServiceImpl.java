package mr.demonid.chart.builder.impl;

import mr.demonid.chart.builder.DatasetService;
import mr.demonid.model.measurement.types.Measurement;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import java.time.LocalDateTime;
import java.util.List;


public class DatasetServiceImpl implements DatasetService {

    /**
     * Создание временного набора данных для графика давления.
     *
     * @param list Список сырых данных из файла.
     */
    @Override
    public TimeSeriesCollection buildPressureDataset(List<Measurement> list) {

        // графики давления
        TimeSeries sys = new TimeSeries("Систолическое");
        TimeSeries dia = new TimeSeries("Диастолическое");
        // для маркеров (значки, без линий)
        TimeSeries sysMark = new TimeSeries("SysMarkers");
        TimeSeries diaMark = new TimeSeries("DiaMarkers");

        for (Measurement m : list) {
            Second second = getTime(m);

            sys.addOrUpdate(second, m.systolic());
            sysMark.addOrUpdate(second, m.systolic());
            dia.addOrUpdate(second, m.diastolic());
            diaMark.addOrUpdate(second, m.diastolic());
        }
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(sys);
        dataset.addSeries(dia);
        dataset.addSeries(sysMark);
        dataset.addSeries(diaMark);
        return dataset;
    }


    /**
     * Создание временного набора данных для графика пульса.
     *
     * @param list Список сырых данных из файла.
     */
    @Override
    public TimeSeriesCollection buildPulseDataset(List<Measurement> list) {

        TimeSeries pulse = new TimeSeries("Пульс");
        // маркеры (значки, без линий)
        TimeSeries arithMarkers = new TimeSeries("Markers");
        TimeSeries pulseMarkers = new TimeSeries("PulseMarkers");

        for (Measurement m : list) {
            Second second = getTime(m);

            pulse.addOrUpdate(second, m.pulse());
            pulseMarkers.addOrUpdate(second, m.pulse());
            if (m.note().toLowerCase().contains("аритмия")) {
                arithMarkers.addOrUpdate(second, m.pulse()); // отдельная серия для аннотации
            }
        }
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(pulse);
        dataset.addSeries(pulseMarkers);
        dataset.addSeries(arithMarkers);
        return dataset;
    }

    /*
     * Извлекает дату/время из показаний и преобразует её в секунды.
     */
    private Second getTime(Measurement m) {
        LocalDateTime dt = LocalDateTime.of(m.date(), m.time());

        return new Second(
                dt.getSecond(),
                dt.getMinute(),
                dt.getHour(),
                dt.getDayOfMonth(),
                dt.getMonthValue(),
                dt.getYear()
        );
    }

}
