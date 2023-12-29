package com.example.layeredarchitecture.controller;

import com.example.layeredarchitecture.Dao.DAOFactory;
import com.example.layeredarchitecture.Dao.custom.CustomerDAO;
import com.example.layeredarchitecture.Dao.custom.QuereyDAO;
import com.example.layeredarchitecture.DTO.CusOrderOTO;
import com.example.layeredarchitecture.DTO.CustomerDTO;
import com.example.layeredarchitecture.bo.BOFactory;
import com.example.layeredarchitecture.bo.custom.CustomerBO;
import com.example.layeredarchitecture.bo.custom.impl.CustomerBoImpl;
import com.example.layeredarchitecture.bo.custom.impl.QuereyBOImpl;
import com.example.layeredarchitecture.view.tdm.CusOrderTM;
import com.jfoenix.controls.JFXComboBox;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SearchOrderFormController implements Initializable {

    @FXML
    public TableView tbleCusOrderDetails;

    @FXML
    private JFXComboBox cmbCusId;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private AnchorPane root;

    CustomerBoImpl customerBO = (CustomerBoImpl) BOFactory.getBoFactory().getBo(BOFactory.BOTypes.CUSTOMER);

    QuereyBOImpl quereyBO = (QuereyBOImpl) BOFactory.boFactory.getBo(BOFactory.BOTypes.QUEREY);
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadAllCustomers();
        setCellValues();

    }

    private void setCellValues() {
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colId.setCellValueFactory(new PropertyValueFactory<>("Oid"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    private void loadAllCustomers() {
        try {
            ArrayList<CustomerDTO> dto = customerBO.getAllCustomer();
            ObservableList<String> names = FXCollections.observableArrayList();

            for (CustomerDTO List: dto) {
                names.add(List.getId());
            }

            cmbCusId.setItems(names);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void navigateToHome(MouseEvent event) throws IOException {
        URL resource = this.getClass().getResource("/com/example/layeredarchitecture/main-form.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.root.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        Platform.runLater(() -> primaryStage.sizeToScene());

    }

    public void cmbCusIdOnAction(ActionEvent event) {
        if (cmbCusId.getValue() == null) {
            new Alert(Alert.AlertType.ERROR,"Please Select Customer").show();
        }

        String id = (String) cmbCusId.getValue();

        try {
            ArrayList<CusOrderOTO> dtoList = quereyBO.getAll(id);

            ObservableList<CusOrderTM> obList = FXCollections.observableArrayList();


            for (CusOrderOTO list: dtoList) {
                obList.add(new CusOrderTM(list.getName(), list.getAddress(), list.getOid(),list.getDate()));
            }

            tbleCusOrderDetails.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }
}
