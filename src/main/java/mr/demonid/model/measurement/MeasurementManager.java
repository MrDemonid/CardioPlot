package mr.demonid.model.measurement;

import lombok.Getter;
import mr.demonid.model.loader.InputReader;
import mr.demonid.model.measurement.types.ChartBounds;
import mr.demonid.model.measurement.types.Measurement;
import mr.demonid.model.measurement.types.MonthMeasurements;
import mr.demonid.model.measurement.types.RawMeasurement;

import java.io.IOException;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


/**
 * Загрузка данных из XLSX файла.
 * Столбцы: дата (day.month), время (hh:mm), верхнее давление, нижнее давление, пульс, примечание
 */
@Getter
public class MeasurementManager implements Iterable<MonthMeasurements> {

    private Map<YearMonth, List<Measurement>> measures;
    private ChartBounds boundsPress = new ChartBounds(0, 0);;
    private ChartBounds boundsPulse = new ChartBounds(0, 0);;


    public MeasurementManager(InputReader inputReader) throws IOException {
        init(inputReader);
    }


    private void init(InputReader inputReader) throws IOException {
        List<RawMeasurement> raw = inputReader.read();
        List<Measurement> items = MeasurementValidator.validate(raw);
        calcBounds(items);
        measures = groupByMonth(items);
    }


    @Override
    public Iterator<MonthMeasurements> iterator() {
        return new MeasureIterator();
    }

    private class MeasureIterator implements Iterator<MonthMeasurements> {
        private final Iterator<Map.Entry<YearMonth, List<Measurement>>> entryIterator;

        public MeasureIterator() {
            this.entryIterator = measures.entrySet().iterator();
        }

        @Override
        public boolean hasNext() {
            return entryIterator.hasNext();
        }

        @Override
        public MonthMeasurements next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements");
            }
            Map.Entry<YearMonth, List<Measurement>> entry = entryIterator.next();
            return new MonthMeasurements(entry.getKey(), entry.getValue());
        }
    }


    /**
     * Разбивка списка измерений по месяцам и годам.
     * @param list Список измерений за всё время.
     */
    private Map<YearMonth, List<Measurement>> groupByMonth(List<Measurement> list) {
        return list.stream().collect(Collectors.groupingBy(m -> YearMonth.from(m.date()),
                TreeMap::new,
                Collectors.toList()));
    }


    private void calcBounds(List<Measurement> items) {
        IntSummaryStatistics pressureStats = items.stream()
                .filter(m -> m.systolic() != null && m.diastolic() != null)
                .flatMapToInt(m -> IntStream.of(m.systolic(), m.diastolic()))
                .filter(v -> v > 0)
                .summaryStatistics();

        IntSummaryStatistics pulseStats = items.stream()
                .filter(m -> m.pulse() != null)
                .mapToInt(Measurement::pulse)
                .filter(v -> v > 0)
                .summaryStatistics();

        this.boundsPress = new ChartBounds(
                pressureStats.getMin(),
                pressureStats.getMax()
        );
        this.boundsPulse = new ChartBounds(
                pulseStats.getMin(),
                pulseStats.getMax()
        );
    }

}
