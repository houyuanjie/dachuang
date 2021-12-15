package icu.harx.dachuang.ui.service;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.net.URL;

@Service
public class FxmlServiceImpl implements FxmlService {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    @SneakyThrows
    public <T extends Parent> T getParentByURL(URL url) {
        FXMLLoader loader = new FXMLLoader(url);

        loader.setControllerFactory(applicationContext::getBean);
        return loader.load();
    }

    @Override
    @SneakyThrows
    public <T extends Parent> T getParentByURLAndSet(URL url, Object controller) {
        FXMLLoader loader = new FXMLLoader(url);

        loader.setController(controller);
        return loader.load();
    }
}
