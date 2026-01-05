package mr.demonid.model.measurement.types;

import java.time.LocalDate;
import java.time.LocalTime;


/**
 * Строка измерений.
 */
public record Measurement(
        LocalDate date,
        LocalTime time,
        Integer systolic,
        Integer diastolic,
        Integer pulse,
        String note) {
}
