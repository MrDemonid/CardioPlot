package mr.demonid.model.measurement;

import mr.demonid.model.measurement.types.Measurement;
import mr.demonid.model.measurement.types.RawMeasurement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;


public class MeasurementValidator {

    private static final Logger log = LogManager.getLogger(MeasurementValidator.class.getName());


    public static List<Measurement> validate(List<RawMeasurement> rawData) {
        return rawData.stream()
                .map(MeasurementValidator::validateMeasurement)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    private static Optional<Measurement> validateMeasurement(RawMeasurement raw) {

        if (raw.date().isEmpty()) {
            log.error("Date is empty at row: {}", raw.row());
            return Optional.empty();
        }
        if (raw.time().isEmpty()) {
            log.error("Time is empty at row: {}", raw.row());
            return Optional.empty();
        }
        if (raw.systolic().isEmpty() || raw.systolic().get() <= 30) {
            log.error("Systolic is empty at row: {}", raw.row());
            return Optional.empty();
        }
        if (raw.diastolic().isEmpty() || raw.diastolic().get() <= 20) {
            log.error("Diastolic is empty at row: {}", raw.row());
            return Optional.empty();
        }

        Integer pulse = raw.pulse()
                .filter(p -> p >= 20)
                .orElse(null);

        return Optional.of(new Measurement(
                raw.date().get(),
                raw.time().get(),
                raw.systolic().get(),
                raw.diastolic().get(),
                pulse,
                raw.note().orElse("")
        ));
    }

}
