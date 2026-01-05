package mr.demonid.model.pdf;


/**
 * Интерфейс генерации таблиц из exel в другой формат.
 */
public interface ReportGenerator {
    void generate(String excelPath, String outFile);
}
