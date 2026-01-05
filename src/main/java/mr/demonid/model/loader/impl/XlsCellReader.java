package mr.demonid.model.loader.impl;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public class XlsCellReader {

    /**
     * Чтение ячейки с целочисленными данными.
     */
    static Optional<Integer> readIntCell(Row row, int column) {
        Cell cell = row.getCell(column);
        if (cell == null || cell.getCellType() == CellType.BLANK) {
            return Optional.empty();
        }
        try {
            return Optional.of((int) cell.getNumericCellValue());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /**
     * Чтения ячейки со строковыми данными.
     */
    static Optional<String> readStringCell(Row row, int column) {
        Cell cell = row.getCell(column);
        if (cell == null || cell.getCellType() == CellType.BLANK) {
            return Optional.empty();
        }
        return Optional.of(cell.getStringCellValue());
    }

    /**
     * Чтение ячейки с вещественными данными.
     */
    static Optional<Float> readFloatCell(Row row, int column) {
        Cell cell = row.getCell(column);
        if (cell == null || cell.getCellType() == CellType.BLANK) {
            return Optional.empty();
        }
        try {
            return Optional.of((float) cell.getNumericCellValue());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /**
     * Чтение даты из ячейки.
     */
    static Optional<LocalDate> readLocalDateCell(Row row, int column) {
        Cell cell = row.getCell(column);
        if (cell == null || cell.getCellType() == CellType.BLANK) {
            return Optional.empty();
        }
        try {
            return Optional.of(row.getCell(column).getLocalDateTimeCellValue().toLocalDate());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /**
     * Чтение времени из ячейки.
     */
    static Optional<LocalTime> readLocalTimeCell(Row row, int column) {
        Cell cell = row.getCell(column);
        if (cell == null || cell.getCellType() == CellType.BLANK) {
            return Optional.empty();
        }
        try {
            return Optional.of(row.getCell(column).getLocalDateTimeCellValue().toLocalTime());
        } catch (Exception e) {
            return Optional.empty();
        }
    }


}
