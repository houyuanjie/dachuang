package icu.harx.dachuang.ui.controller;

import icu.harx.dachuang.entity.XiangMu;
import icu.harx.dachuang.mapper.XiangMuMapper;
import icu.harx.dachuang.service.XiangMuService;
import icu.harx.dachuang.ui.event.InsertStageHideEvent;
import icu.harx.dachuang.ui.service.FxmlService;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;


@Controller
public class MainController implements Initializable, ApplicationListener<InsertStageHideEvent> {

    @FXML
    private TextField tfSearch;
    @FXML
    private Pagination pagination;

    @Autowired
    private XiangMuMapper xiangMuMapper;
    @Autowired
    private XiangMuService xiangMuService;
    @Autowired
    private FxmlService fxmlService;

    @Value("${spring.application.ui.rows-per-page}")
    private Integer rowsPerPage;
    @Value("${spring.application.ui.insert-title}")
    private String insertTitle;
    @Value("${spring.application.ui.insert-fxml}")
    private Resource insertFxml;

    @Autowired
    private InsertController insertController;

    private Stage insertStage;

    private TableView<XiangMu> tvXiangMu;
    private XiangMu selectedXiangMu;
    private ObservableList<XiangMu> xiangMuObservableList;
    private FilteredList<XiangMu> xiangMuFilteredObservableList;

    private void initInsertStage() {
        try {
            Image icon = new Image("icon/popup.png");

            Parent insertParent = fxmlService.getParentByURL(insertFxml.getURL());
            Scene insertScence = new Scene(insertParent);

            insertStage = new Stage();
            insertStage.setScene(insertScence);
            insertStage.getIcons().add(icon);
            insertStage.setTitle(insertTitle);

            insertStage.sizeToScene();
            insertStage.initModality(Modality.APPLICATION_MODAL);
            insertStage.hide();

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * ?????? InsertStage ????????????
     */
    private void cleanInsertStage() {
        insertController.getTfID().setText(null);
        insertController.getCbGxdm().setValue(null);
        insertController.getCbGxmc().setValue(null);
        insertController.getTfXmbh().setText(null);
        insertController.getCbXmjb().setValue(null);
        insertController.getTfXmmc().setText(null);
        insertController.getCbXmlx().setValue(null);
        insertController.getTfXmfzrxm().setText(null);
        insertController.getTfXmfzrxh().setText(null);
        insertController.getTfXmqtcyxx().setText(null);
        insertController.getTfZdjsxm().setText(null);
        insertController.getCbZdjszc().setValue(null);
        insertController.getTaXmjj().setText(null);
        insertController.getCbBz().setValue(null);
        insertController.getDpXmtjrq().setValue(null);
    }

    /**
     * ??? XiangMu ???????????? InsertStage
     */
    private void fillInsertStageWith(XiangMu xiangMu) {
        if (xiangMu == null) return;

        insertController.getTfID().setText(xiangMu.getId().toString());
        insertController.getCbGxdm().setValue(xiangMu.getGxdm());
        insertController.getCbGxmc().setValue(xiangMu.getGxmc());
        insertController.getTfXmbh().setText(xiangMu.getXmbh());
        insertController.getCbXmjb().setValue(xiangMu.getXmjb());
        insertController.getTfXmmc().setText(xiangMu.getXmmc());
        insertController.getCbXmlx().setValue(xiangMu.getXmlx());
        insertController.getTfXmfzrxm().setText(xiangMu.getXmfzrxm());
        insertController.getTfXmfzrxh().setText(xiangMu.getXmfzrxh());
        insertController.getTfXmqtcyxx().setText(xiangMu.getXmqtcyxx());
        insertController.getTfZdjsxm().setText(xiangMu.getZdjsxm());
        insertController.getCbZdjszc().setValue(xiangMu.getZdjszc());
        insertController.getTaXmjj().setText(xiangMu.getXmjj());
        insertController.getCbBz().setValue(xiangMu.getBz());
        insertController.getDpXmtjrq().setValue(xiangMu.getXmtjrq());
    }

    /**
     * ????????? TableView
     */
    private void initTableView() {
        tvXiangMu = new TableView<>();

        TableColumn<XiangMu, Number> tcId = new TableColumn<>("Id");
        TableColumn<XiangMu, String> tcBh = new TableColumn<>("??????");
        TableColumn<XiangMu, String> tcMc = new TableColumn<>("??????");
        TableColumn<XiangMu, String> tcGxmc = new TableColumn<>("????????????");
        TableColumn<XiangMu, String> tcFzr = new TableColumn<>("?????????");
        TableColumn<XiangMu, String> tcZdjs = new TableColumn<>("????????????");
        TableColumn<XiangMu, String> tcBz = new TableColumn<>("??????");

        TableColumn<XiangMu, String> tcTjrq = new TableColumn<>("????????????");
        TableColumn<XiangMu, String> tcLx = new TableColumn<>("??????");
        TableColumn<XiangMu, String> tcJb = new TableColumn<>("??????");
        TableColumn<XiangMu, String> tcZc = new TableColumn<>("??????");
        TableColumn<XiangMu, Number> tcGxdm = new TableColumn<>("??????");
        TableColumn<XiangMu, String> tcXh = new TableColumn<>("??????");
        TableColumn<XiangMu, String> tcQtcy = new TableColumn<>("????????????");
        TableColumn<XiangMu, String> tcJj = new TableColumn<>("??????");


        tvXiangMu.setOnMouseClicked(event -> selectedXiangMu = tvXiangMu.getSelectionModel().getSelectedItem());

        tvXiangMu.setPlaceholder(new Label("?????????????????????, ????????????????????????????????????"));
        tvXiangMu.getColumns().add(tcId);
        tvXiangMu.getColumns().add(tcBh);
        tvXiangMu.getColumns().add(tcMc);
        tvXiangMu.getColumns().add(tcLx);
        tvXiangMu.getColumns().add(tcJb);
        tvXiangMu.getColumns().add(tcGxmc);
        tvXiangMu.getColumns().add(tcGxdm);
        tvXiangMu.getColumns().add(tcFzr);
        tvXiangMu.getColumns().add(tcXh);
        tvXiangMu.getColumns().add(tcZdjs);
        tvXiangMu.getColumns().add(tcZc);
        tvXiangMu.getColumns().add(tcQtcy);
        tvXiangMu.getColumns().add(tcBz);
        tvXiangMu.getColumns().add(tcTjrq);
        tvXiangMu.getColumns().add(tcJj);


        tcId.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getId()));
        tcBh.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getXmbh()));
        tcMc.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getXmmc()));
        tcGxmc.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getGxmc()));
        tcFzr.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getXmfzrxm()));
        tcZdjs.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getZdjsxm()));
        tcBz.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getBz()));

        tcTjrq.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getXmtjrq() == null ? null : cell.getValue().getXmtjrq().format(DateTimeFormatter.ISO_LOCAL_DATE)));
        tcLx.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getXmlx()));
        tcJb.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getXmjb()));
        tcZc.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getZdjszc()));
        tcGxdm.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getGxdm()));
        tcXh.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getXmfzrxh()));
        tcQtcy.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getXmqtcyxx()));
        tcJj.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getXmjj()));
    }

    /**
     * ????????? ObservableList
     */
    private void initObservableList() {
        // [Database] -> xiangMuList -> xiangMuObservableList -> xiangMuFilteredList -> xiangMuSortedList
        xiangMuObservableList = FXCollections.observableArrayList(xiangMuMapper.selectList(null));
        xiangMuFilteredObservableList = new FilteredList<>(xiangMuObservableList, xiangMu -> true);
    }

    /**
     * ???????????????
     */
    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTableView();
        initObservableList();
        initInsertStage();

        tfSearch.textProperty().addListener((observable, oldValue, newValue) -> updateList(newValue));
        pagination.setPageFactory(this::updatedTableView);

        updateComboBox();
    }

    private void updateList(String search) {
        // ????????????
        xiangMuFilteredObservableList.setPredicate(xiangMu -> {
            if (search.isBlank()) {
                return true;
            } else return xiangMuService.propertiesContains(xiangMu, search);
        });

        // ????????????
        pagination.setPageCount((xiangMuFilteredObservableList.size() + rowsPerPage) / rowsPerPage);
    }

    private TableView<XiangMu> updatedTableView(Integer pageIndex) {
        // ???????????????????????????
        int from = pageIndex * rowsPerPage;
        int to = Integer.min(from + rowsPerPage, xiangMuFilteredObservableList.size());

        // ????????????
        pagination.setPageCount((xiangMuFilteredObservableList.size() + rowsPerPage) / rowsPerPage);

        // ????????????
        List<XiangMu> xiangMuListInPage = xiangMuFilteredObservableList.subList(from, to);
        SortedList<XiangMu> xiangMuPagedSortedFilteredObservableList = new SortedList<>(FXCollections.observableArrayList(xiangMuListInPage));
        xiangMuPagedSortedFilteredObservableList.comparatorProperty().bind(tvXiangMu.comparatorProperty());

        // ?????? tvXiangMu
        tvXiangMu.setItems(xiangMuPagedSortedFilteredObservableList);

        return tvXiangMu;
    }

    private void updateComboBox() {
        insertController.getCbGxdm().setItems(FXCollections.observableArrayList(xiangMuService.allPropertyDistinct(XiangMu::getGxdm, xiangMuObservableList)));
        insertController.getCbGxmc().setItems(FXCollections.observableArrayList(xiangMuService.allPropertyDistinct(XiangMu::getGxmc, xiangMuObservableList)));
        insertController.getCbXmjb().setItems(FXCollections.observableArrayList(xiangMuService.allPropertyDistinct(XiangMu::getXmjb, xiangMuObservableList)));
        insertController.getCbXmlx().setItems(FXCollections.observableArrayList(xiangMuService.allPropertyDistinct(XiangMu::getXmlx, xiangMuObservableList)));
        insertController.getCbZdjszc().setItems(FXCollections.observableArrayList(xiangMuService.allPropertyDistinct(XiangMu::getZdjszc, xiangMuObservableList)));
        insertController.getCbBz().setItems(FXCollections.observableArrayList(xiangMuService.allPropertyDistinct(XiangMu::getBz, xiangMuObservableList)));
    }

    @FXML
    private void onRefresh(ActionEvent actionEvent) {
        xiangMuObservableList.setAll(xiangMuMapper.selectList(null));
        updateList(tfSearch.getText());
        updatedTableView(pagination.getCurrentPageIndex());
        updateComboBox();
    }

    @FXML
    private void onInsert(ActionEvent actionEvent) {
        cleanInsertStage();
        insertController.setIsInsert(true);
        insertController.getLbPrompt().setText("????????????");
        insertController.getTfID().setEditable(true);
        insertController.getTfID().setDisable(false);
        insertStage.showAndWait();
    }

    @FXML
    private void onModify(ActionEvent actionEvent) {
        fillInsertStageWith(selectedXiangMu);
        insertController.setIsInsert(false);
        insertController.getLbPrompt().setText("????????????");
        insertController.getTfID().setEditable(false);
        insertController.getTfID().setDisable(true);
        insertStage.showAndWait();
    }

    @FXML
    private void onDelete(ActionEvent actionEvent) {
        xiangMuMapper.deleteById(selectedXiangMu);
        onRefresh(null);
    }

    @Override
    public void onApplicationEvent(InsertStageHideEvent event) {
        insertStage.hide();
        onRefresh(null);
    }
}
