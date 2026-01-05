package mr.demonid.model.loader;


import mr.demonid.model.measurement.types.RawMeasurement;

import java.io.IOException;
import java.util.List;

/**
 * Интерфейс для ввода данных в программу.
 */
public interface InputReader {
    List<RawMeasurement> read() throws IOException;
}
