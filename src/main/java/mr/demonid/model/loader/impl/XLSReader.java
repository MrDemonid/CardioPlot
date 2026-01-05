package mr.demonid.model.loader.impl;

import mr.demonid.model.loader.InputReader;
import mr.demonid.model.measurement.types.RawMeasurement;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Ридер данных из xlsx-файла.
 */
public class XLSReader implements InputReader {

    private final String fileName;


    public XLSReader(String fileName) {
        this.fileName = fileName;
    }


    /**
     * Читает файл и возвращает список замеров.
     */
    @Override
    public List<RawMeasurement> read() throws IOException {

        try (FileInputStream fis = new FileInputStream(fileName);
             Workbook wb = WorkbookFactory.create(fis)) {
            if (wb.getNumberOfSheets() == 0) {
                throw new IOException("No sheets found");
            }
            Sheet sheet = wb.getSheetAt(0);
            if (sheet.getLastRowNum() < 1) {
                throw new IOException("File is empty");
            }
            List<RawMeasurement> raw = new ArrayList<>();

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row r = sheet.getRow(i);
                if (r != null) {
                    RawMeasurement m = new RawMeasurement(
                            XlsCellReader.readLocalDateCell(r, 0),
                            XlsCellReader.readLocalTimeCell(r, 1),
                            XlsCellReader.readIntCell(r, 2),
                            XlsCellReader.readIntCell(r, 3),
                            XlsCellReader.readIntCell(r, 4),
                            XlsCellReader.readStringCell(r, 5),
                            i
                    );
                    raw.add(m);
                }
            }
            return raw;
        }
    }

}
