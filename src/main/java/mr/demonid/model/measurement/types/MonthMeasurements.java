package mr.demonid.model.measurement.types;

import java.time.YearMonth;
import java.util.List;


/**
 * Измерения за месяц.
 * @param month        Год/месяц.
 * @param measurements Список измерений.
 */
public record MonthMeasurements(YearMonth month, List<Measurement> measurements) {
}