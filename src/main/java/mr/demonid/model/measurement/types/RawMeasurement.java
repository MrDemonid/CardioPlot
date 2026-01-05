package mr.demonid.model.measurement.types;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;


public record RawMeasurement (
    Optional<LocalDate> date,
    Optional<LocalTime> time,
    Optional<Integer> systolic,
    Optional<Integer> diastolic,
    Optional<Integer> pulse,
    Optional<String> note,
    int row) {
}
