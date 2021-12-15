package icu.harx.dachuang.ui.controller;

import icu.harx.dachuang.entity.XiangMu;
import icu.harx.dachuang.mapper.XiangMuMapper;
import icu.harx.dachuang.ui.event.InsertStageHideEvent;
import icu.harx.dachuang.ui.service.FxmlService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

@Getter
@Controller
public class InsertController implements Initializable {

    @Autowired
    private ApplicationContext applicationContext;

    @Setter
    private Boolean isInsert;

    @FXML
    private Label lbPrompt;
    @FXML
    private TextField tfID;
    @FXML
    private ComboBox<Integer> cbGxdm;
    @FXML
    private ComboBox<String> cbGxmc;
    @FXML
    private TextField tfXmbh;
    @FXML
    private ComboBox<String> cbXmjb;
    @FXML
    private TextField tfXmmc;
    @FXML
    private ComboBox<String> cbXmlx;
    @FXML
    private TextField tfXmfzrxm;
    @FXML
    private TextField tfXmfzrxh;
    @FXML
    private TextField tfXmqtcyxx;
    @FXML
    private TextField tfZdjsxm;
    @FXML
    private ComboBox<String> cbZdjszc;
    @FXML
    private TextArea taXmjj;
    @FXML
    private ComboBox<String> cbBz;
    @FXML
    private DatePicker dpXmtjrq;

    @Autowired
    private XiangMuMapper xiangMuMapper;
    @Autowired
    private FxmlService fxmlService;

    private XiangMu makeXiangMu() {
        XiangMu xiangMu = new XiangMu();

        xiangMu.setId(Optional.ofNullable(tfID.getText()).map(Integer::parseInt).orElse(null));
        xiangMu.setGxdm(cbGxdm.getValue());
        xiangMu.setGxmc(cbGxmc.getValue());
        xiangMu.setXmbh(tfXmbh.getText());
        xiangMu.setXmjb(cbXmjb.getValue());
        xiangMu.setXmmc(tfXmmc.getText());
        xiangMu.setXmlx(cbXmlx.getValue());
        xiangMu.setXmfzrxm(tfXmfzrxm.getText());
        xiangMu.setXmfzrxh(tfXmfzrxh.getText());
        xiangMu.setXmqtcyxx(tfXmqtcyxx.getText());
        xiangMu.setZdjsxm(tfZdjsxm.getText());
        xiangMu.setXmjj(taXmjj.getText());
        xiangMu.setBz(cbBz.getValue());

        return xiangMu;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        StringConverter<Integer> integerStringConverter = new StringConverter<Integer>() {

            @Override
            public String toString(Integer object) {
                if (object == null) return null;
                return object.toString();
            }

            @Override
            public Integer fromString(String string) {
                if (string == null || string.isBlank()) return null;
                return Integer.parseInt(string.trim());
            }
        };

        cbGxdm.setConverter(integerStringConverter);
    }

    @FXML
    public void onConfirm(ActionEvent actionEvent) {
        if (isInsert == null) return;

        if (isInsert) {
            xiangMuMapper.insert(makeXiangMu());
        } else {
            xiangMuMapper.updateById(makeXiangMu());
        }

        applicationContext.publishEvent(new InsertStageHideEvent(this));
    }

    @FXML
    public void onCancel(ActionEvent actionEvent) {
        applicationContext.publishEvent(new InsertStageHideEvent(this));
    }
}
