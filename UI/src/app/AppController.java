package app;

import commands.CommandsController;
import engine.api.Engine;
import engine.impl.EngineImpl;
import header.HeaderController;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import modelUI.api.EffectiveValuesPoolProperty;
import modelUI.api.EffectiveValuesPoolPropertyReadOnly;
import modelUI.api.FocusCellProperty;
import modelUI.impl.EffectiveValuesPoolPropertyImpl;
import modelUI.impl.FocusCellPropertyImpl;
import ranges.RangesController;
import sheet.SheetController;
import sheet.cell.api.Cell;
import sheet.cell.api.CellGetters;
import sheet.coordinate.api.Coordinate;
import sheet.coordinate.api.CoordinateGetters;
import sheet.coordinate.impl.CoordinateFactory;

import java.util.Map;

public class AppController {

    @FXML private BorderPane appBorderPane;
    @FXML private ScrollPane headerComponent;
    @FXML private HeaderController headerComponentController;
    @FXML private ScrollPane commandsComponent;
    @FXML private CommandsController commandsComponentController;
    @FXML private ScrollPane rangesComponent;
    @FXML private RangesController rangesComponentController;

    private SimpleBooleanProperty isFileSelected;
    private ScrollPane sheetComponent;
    private SheetController sheetComponentController;
    private Stage primaryStage;


    private FocusCellProperty cellInFocus;
    private EffectiveValuesPoolProperty effectiveValuesPool;
    private Engine engine;

    public AppController() {
        engine = EngineImpl.create();
        this.isFileSelected = new SimpleBooleanProperty(false);
        this.cellInFocus = new FocusCellPropertyImpl();
        effectiveValuesPool = new EffectiveValuesPoolPropertyImpl();
    }

    @FXML
    public void initialize() {
        if (headerComponentController != null && commandsComponentController != null && rangesComponentController != null) {
            headerComponentController.setMainController(this);
            commandsComponentController.setMainController(this);
            rangesComponentController.setMainController(this);

            headerComponentController.init();
            commandsComponentController.init();
            rangesComponentController.init();
        }
    }

    public boolean isFileSelected() {
        return isFileSelected.get();
    }

    public SimpleBooleanProperty isFileSelectedProperty() {
        return isFileSelected;
    }

    public FocusCellProperty getCellInFocus() {
        return cellInFocus;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void uploadXml(String path)
    {
        engine.readXMLInitFile(path);
        isFileSelected.set(true);
        setEffectiveValuesPoolProperty();
        setSheet();
    }

    private void setSheet() {
        sheetComponentController = new SheetController();
        sheetComponentController.setMainController(this);
        sheetComponent = sheetComponentController.getInitializedSheet(engine.getSheetStatus().getLayout().getRows(),
                engine.getSheetStatus().getLayout().getColumns());
        appBorderPane.setCenter(sheetComponent);
    }


    private void  setEffectiveValuesPoolProperty() {

        Map<CoordinateGetters,CellGetters> map = engine.getSheetStatus().getActiveCells();

        for(int row = 0; row < engine.getSheetStatus().getLayout().getRows(); row++) {
            for(int column = 0; column < engine.getSheetStatus().getLayout().getColumns(); column++) {
              Coordinate coordinate = CoordinateFactory.createCoordinate(row,column);
              CellGetters cell = map.get(coordinate);
                if(cell != null){
                    effectiveValuesPool.addEffectiveValuePropertyAt(coordinate, cell.getEffectiveValue().toString());
                }
                else {
                    effectiveValuesPool.addEffectiveValuePropertyAt(coordinate, "");
                }
            }
        }
    }

    public void focusChanged(boolean newValue, Coordinate coordinate) {
        if (newValue)
        {
            Cell cell = engine.getSheetStatus().getCell(coordinate);
            cellInFocus.setCoordinate(coordinate.toString());

            if (cell != null) {
                cellInFocus.setOriginalValue(cell.getOriginalValue());
                cellInFocus.setLastUpdateVersion(String.valueOf(cell.getVersion()));
            } else {
                cellInFocus.setOriginalValue("");
                cellInFocus.setLastUpdateVersion("");
            }


        }
    }

    public EffectiveValuesPoolPropertyReadOnly getEffectiveValuesPool() {
        return effectiveValuesPool;
    }

    public void changeColumnWidth(Integer newValue) {
    }

    public void changeRowHeight(Integer newValue) {
    }

    public void alignCells(Pos pos) {
    }

    public void updateCell() {
        engine.updateCellStatus(cellInFocus.getCoordinate().get(), cellInFocus.getOriginalValue().get());
        setEffectiveValuesPoolProperty();
    }
}
