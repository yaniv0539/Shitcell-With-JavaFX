package header;

import app.AppController;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class HeaderController {

        @FXML
        private Button buttonUpdateCell;

        @FXML
        private Button buttonUploadXmlFile;

        @FXML
        private Label labelCellId;

        @FXML
        private Label labelOriginalValue;

        @FXML
        private Label labelVersionSelector;

        @FXML
        private Label lableFileName;

        @FXML
        private TextField textFieldCellId;

        @FXML
        private TextField textFieldFileName;

        @FXML
        private TextField textFieldOrignalValue;

        @FXML
        private TextField textFieldVersionSelector;

        private AppController mainController;

        public void setMainController(AppController mainController) {
                this.mainController = mainController;
        }

        //TEST
        public void bindCellIdTextField(StringProperty strProp) {
                textFieldCellId.textProperty().bind(strProp);
        }

        //test nor real implementation
        @FXML
        void buttonUpdateCellAction(ActionEvent event) {
                mainController.focusChanged(new SimpleBooleanProperty(),true,true);
        }

        @FXML
        void buttonUploadXmlFileAction(ActionEvent event) {

        }

        @FXML
        void textFieldFileNameAction(ActionEvent event) {

        }

        @FXML
        void textFieldOrignalValueAction(ActionEvent event) {

        }

        @FXML
        void textFieldVersionSelectorAction(ActionEvent event) {

        }

    }
