package engine.api;

import engine.version.manager.api.VersionManagerGetters;
import sheet.api.SheetGetters;
import sheet.cell.api.CellGetters;
import sheet.range.api.Range;
import sheet.range.api.RangeGetters;
import sheet.range.boundaries.api.Boundaries;

import java.util.List;
import java.util.Set;

public interface Engine {

    void readXMLInitFile(String filename);
    SheetGetters getSheetStatus();
    CellGetters getCellStatus(SheetGetters sheet, String cellName);
    CellGetters getCellStatus(String cellName);
    CellGetters getCellStatus(int row, int col);
    CellGetters getCellStatus(SheetGetters sheet, int row, int col);
    void updateCellStatus(String cellName, String value);
    VersionManagerGetters getVersionsManagerStatus();
    void addRange(String from, String to);
    RangeGetters getRange(String name);
    Set<RangeGetters> getRanges();
    void deleteRange(String name);
    javafx.concurrent.Task<Boolean> loadFileTask(String path);
    SheetGetters filter(Boundaries boundaries, String column, List<String> values);
    void exit();
}
