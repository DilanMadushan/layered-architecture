package com.example.layeredarchitecture.controller;

import com.example.layeredarchitecture.Dao.custom.*;
import com.example.layeredarchitecture.Dao.custom.impl.CustomerDAOImpl;
import com.example.layeredarchitecture.Dao.custom.impl.ItemDAOImpl;
import com.example.layeredarchitecture.Dao.custom.impl.OrderDAOImpl;
import com.example.layeredarchitecture.Dao.custom.impl.OrderDetailsDAOImpl;
import com.example.layeredarchitecture.db.DBConnection;
import com.example.layeredarchitecture.model.CustomerDTO;
import com.example.layeredarchitecture.model.ItemDTO;
import com.example.layeredarchitecture.model.OrderDTO;
import com.example.layeredarchitecture.model.OrderDetailDTO;
import com.example.layeredarchitecture.view.tdm.OrderDetailTM;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



public class PlaceOrderFormController {

    public AnchorPane root;
    public JFXButton btnPlaceOrder;
    public TextField txtCustomerName;
    public TextField txtDescription;
    public TextField txtQtyOnHand;
    public JFXButton btnSave;
    public TableView<OrderDetailTM> tblOrderDetails;
    public TextField txtUnitPrice;
    public JFXComboBox<String> cmbCustomerId;
    public JFXComboBox<String> cmbItemCode;
    public TextField txtQty;
    public Label lblId;
    public Label lblDate;
    public Label lblTotal;
    private String orderId;

    private CustomerDAO customerDAO = new CustomerDAOImpl();
    private ItemDAO itemDAO = new ItemDAOImpl();
    private OrderDAO orderDAO = new OrderDAOImpl();
    private OrderDetailsDAO orderDetailsDAO = new OrderDetailsDAOImpl();

    public void initialize() throws SQLException, ClassNotFoundException {

        tblOrderDetails.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("code"));
        tblOrderDetails.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("description"));
        tblOrderDetails.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("qty"));
        tblOrderDetails.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        tblOrderDetails.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("total"));
        TableColumn<OrderDetailTM, Button> lastCol = (TableColumn<OrderDetailTM, Button>) tblOrderDetails.getColumns().get(5);

        lastCol.setCellValueFactory(param -> {
            Button btnDelete = new Button("Delete");

            btnDelete.setOnAction(event -> {
                tblOrderDetails.getItems().remove(param.getValue());
                tblOrderDetails.getSelectionModel().clearSelection();
                calculateTotal();
                enableOrDisablePlaceOrderButton();
            });

            return new ReadOnlyObjectWrapper<>(btnDelete);
        });

        orderId = generateNewOrderId();
        lblId.setText("Order ID: " + orderId);
        lblDate.setText(LocalDate.now().toString());
        btnPlaceOrder.setDisable(true);
        txtCustomerName.setFocusTraversable(false);
        txtCustomerName.setEditable(false);
        txtDescription.setFocusTraversable(false);
        txtDescription.setEditable(false);
        txtUnitPrice.setFocusTraversable(false);
        txtUnitPrice.setEditable(false);
        txtQtyOnHand.setFocusTraversable(false);
        txtQtyOnHand.setEditable(false);
        txtQty.setOnAction(event -> btnSave.fire());
        txtQty.setEditable(false);
        btnSave.setDisable(true);

        cmbCustomerId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            enableOrDisablePlaceOrderButton();

            if (newValue != null) {
                try {
                    /*Search Customer*/
                    CustomerDTO dto = customerDAO.search(newValue);
                    try {
                        if (!existCustomer(newValue + "")) {
//                            "There is no such customer associated with the id " + id
                            new Alert(Alert.AlertType.ERROR, "There is no such customer associated with the id " + newValue + "").show();
                        }

                        if (dto != null) {
                            txtCustomerName.setText(dto.getName());
                        }
                    } catch (SQLException e) {
                        new Alert(Alert.AlertType.ERROR, "Failed to find the customer " + newValue + "" + e).show();
                    }

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                txtCustomerName.clear();
            }
        });


        cmbItemCode.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newItemCode) -> {
            txtQty.setEditable(newItemCode != null);
            btnSave.setDisable(newItemCode == null);

            if (newItemCode != null) {

                /*Find Item*/
                try {
                    if (!existItem(newItemCode + "")) {
//                        throw new NotFoundException("There is no such item associated with the id " + code);
                    }
//                    Connection connection = DBConnection.getDbConnection().getConnection();
//                    PreparedStatement pstm = connection.prepareStatement("SELECT * FROM Item WHERE code=?");
//                    pstm.setString(1, newItemCode + "");
//                    ResultSet rst = pstm.executeQuery();
//                    rst.next();
//                    ItemDTO item = new ItemDTO(newItemCode + "", rst.getString("description"), rst.getBigDecimal("unitPrice"), rst.getInt("qtyOnHand"));
//

                    ItemDTO dto = itemDAO.search(newItemCode);

                    if (dto != null) {
                        txtDescription.setText(dto.getDescription());
                        txtUnitPrice.setText(dto.getUnitPrice().setScale(2).toString());

//                      txtQtyOnHand.setText(tblOrderDetails.getItems().stream().filter(detail-> detail.getCode().equals(item.getCode())).<Integer>map(detail-> item.getQtyOnHand() - detail.getQty()).findFirst().orElse(item.getQtyOnHand()) + "");
                        Optional<OrderDetailTM> optOrderDetail = tblOrderDetails.getItems().stream().filter(detail -> detail.getCode().equals(newItemCode)).findFirst();
                        txtQtyOnHand.setText((optOrderDetail.isPresent() ? dto.getQtyOnHand() - optOrderDetail.get().getQty() : dto.getQtyOnHand()) + "");
                    }

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            } else {
                txtDescription.clear();
                txtQty.clear();
                txtQtyOnHand.clear();
                txtUnitPrice.clear();
            }
        });

        tblOrderDetails.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedOrderDetail) -> {

            if (selectedOrderDetail != null) {
                cmbItemCode.setDisable(true);
                cmbItemCode.setValue(selectedOrderDetail.getCode());
                btnSave.setText("Update");
                txtQtyOnHand.setText(Integer.parseInt(txtQtyOnHand.getText()) + selectedOrderDetail.getQty() + "");
                txtQty.setText(selectedOrderDetail.getQty() + "");
            } else {
                btnSave.setText("Add");
                cmbItemCode.setDisable(false);
                cmbItemCode.getSelectionModel().clearSelection();
                txtQty.clear();
            }

        });

        loadAllCustomerIds();
        loadAllItemCodes();
    }

    private boolean existItem(String code) throws SQLException, ClassNotFoundException {
//        Connection connection = DBConnection.getDbConnection().getConnection();
//        PreparedStatement pstm = connection.prepareStatement("SELECT code FROM Item WHERE code=?");
//        pstm.setString(1, code);
//        return pstm.executeQuery().next();
        return itemDAO.exist(code);

    }

    boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
//        Connection connection = DBConnection.getDbConnection().getConnection();
//        PreparedStatement pstm = connection.prepareStatement("SELECT id FROM Customer WHERE id=?");
//        pstm.setString(1, id);
//        return pstm.executeQuery().next();
        return customerDAO.exist(id);
    }

    public String generateNewOrderId() {
        try {
            return orderDAO.genarateId();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new order id").show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "OID-001";

    }

    private void loadAllCustomerIds() {
        try {
//            Connection connection = DBConnection.getDbConnection().getConnection();
//            Statement stm = connection.createStatement();
//            ResultSet rst = stm.executeQuery("SELECT * FROM Customer");

            ObservableList<String> ids = FXCollections.observableArrayList();
            ArrayList<CustomerDTO> dto = customerDAO.getAll();

            for (CustomerDTO set: dto) {
                ids.add(set.getId());
            }

            if (ids != null) {
                cmbCustomerId.setItems(ids);
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load customer ids").show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadAllItemCodes() {
        try {
            /*Get all items*/
//            Connection connection = DBConnection.getDbConnection().getConnection();
//            Statement stm = connection.createStatement();
//            ResultSet rst = stm.executeQuery("SELECT * FROM Item");

            ArrayList<ItemDTO> codes = itemDAO.getAll();

            ObservableList<String> codeSet = FXCollections.observableArrayList();

            for (ItemDTO set: codes) {
                codeSet.add(set.getCode());
            }

            if (codes != null) {
                cmbItemCode.setItems(codeSet);
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void navigateToHome(MouseEvent event) throws IOException {
        URL resource = this.getClass().getResource("/com/example/layeredarchitecture/main-form.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.root.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        Platform.runLater(() -> primaryStage.sizeToScene());
    }

    public void btnAdd_OnAction(ActionEvent actionEvent) {
        if (!txtQty.getText().matches("\\d+") || Integer.parseInt(txtQty.getText()) <= 0 ||
                Integer.parseInt(txtQty.getText()) > Integer.parseInt(txtQtyOnHand.getText())) {
            new Alert(Alert.AlertType.ERROR, "Invalid qty").show();
            txtQty.requestFocus();
            txtQty.selectAll();
            return;
        }

        String itemCode = cmbItemCode.getSelectionModel().getSelectedItem();
        String description = txtDescription.getText();
        BigDecimal unitPrice = new BigDecimal(txtUnitPrice.getText()).setScale(2);
        int qty = Integer.parseInt(txtQty.getText());
        BigDecimal total = unitPrice.multiply(new BigDecimal(qty)).setScale(2);

        boolean exists = tblOrderDetails.getItems().stream().anyMatch(detail -> detail.getCode().equals(itemCode));

        if (exists) {
            OrderDetailTM orderDetailTM = tblOrderDetails.getItems().stream().filter(detail -> detail.getCode().equals(itemCode)).findFirst().get();

            if (btnSave.getText().equalsIgnoreCase("Update")) {
                orderDetailTM.setQty(qty);
                orderDetailTM.setTotal(total);
                tblOrderDetails.getSelectionModel().clearSelection();
            } else {
                orderDetailTM.setQty(orderDetailTM.getQty() + qty);
                total = new BigDecimal(orderDetailTM.getQty()).multiply(unitPrice).setScale(2);
                orderDetailTM.setTotal(total);
            }
            tblOrderDetails.refresh();
        } else {
            tblOrderDetails.getItems().add(new OrderDetailTM(itemCode, description, qty, unitPrice, total));
        }
        cmbItemCode.getSelectionModel().clearSelection();
        cmbItemCode.requestFocus();
        calculateTotal();
        enableOrDisablePlaceOrderButton();
    }

    private void calculateTotal() {
        BigDecimal total = new BigDecimal(0);

        for (OrderDetailTM detail : tblOrderDetails.getItems()) {
            total = total.add(detail.getTotal());
        }
        lblTotal.setText("Total: " + total);
    }

    private void enableOrDisablePlaceOrderButton() {
        btnPlaceOrder.setDisable(!(cmbCustomerId.getSelectionModel().getSelectedItem() != null && !tblOrderDetails.getItems().isEmpty()));
    }

    public void txtQty_OnAction(ActionEvent actionEvent) {
    }

    public void btnPlaceOrder_OnAction(ActionEvent actionEvent) {
        boolean b = saveOrder(orderId, LocalDate.now(), cmbCustomerId.getValue(),
                tblOrderDetails.getItems().stream().map(tm -> new OrderDetailDTO(tm.getCode(), tm.getQty(), tm.getUnitPrice())).collect(Collectors.toList()));

        if (b) {
            new Alert(Alert.AlertType.INFORMATION, "Order has been placed successfully").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Order has not been placed successfully").show();
        }

        orderId = generateNewOrderId();
        lblId.setText("Order Id: " + orderId);
        cmbCustomerId.getSelectionModel().clearSelection();
        cmbItemCode.getSelectionModel().clearSelection();
        tblOrderDetails.getItems().clear();
        txtQty.clear();
        calculateTotal();
    }

    public boolean saveOrder(String orderId, LocalDate orderDate, String customerId, List<OrderDetailDTO> orderDetails) {

        /*Transaction*/
        Connection connection = null;

        try {

            connection = DBConnection.getDbConnection().getConnection();
            connection.setAutoCommit(false);

//            boolean isOrderSaved = orderDAO.saveOrder(orderId,orderDate,customerId);
//
//            if (isOrderSaved) {
//                boolean isODetailsSaved= orderDetailsDAO.saveOredrDetils(orderId,orderDetails);
//
//                if (isODetailsSaved) {
//                    boolean isItemUpdate = itemDAO.updateProducts(orderDetails);
//
//                    if (isItemUpdate) {
//                        return true;
//
//                    }else {
//                        return false;
//                    }
//                }else {
//                    return false;
//                }
//            }else {
//                return false;
//            }
            boolean isOrderSaved = orderDAO.save(new OrderDTO(orderId,orderDate,customerId));


            boolean isODetailsSaved= false;

            if (isOrderSaved) {
                for (OrderDetailDTO dto: orderDetails) {
                    dto.setOrderId(orderId);
                    isODetailsSaved= orderDetailsDAO.save(dto);
                }


                boolean isItemUpdate = false;

                if (isODetailsSaved) {
                    for (OrderDetailDTO dto: orderDetails) {

                        ItemDTO item = itemDAO.search(dto.getItemCode());
                        item.setQtyOnHand(item.getQtyOnHand() - dto.getQty());

                        isItemUpdate = itemDAO.update(new ItemDTO(item.getCode(),item.getDescription(),item.getUnitPrice(), item.getQtyOnHand()));
                    }

                    if (isItemUpdate) {
                        connection.commit();
                        connection.setAutoCommit(true);
                        return true;

                    }else {
                        connection.rollback();
                        connection.setAutoCommit(true);
                        return false;
                    }
                }else {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }
            }else {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
