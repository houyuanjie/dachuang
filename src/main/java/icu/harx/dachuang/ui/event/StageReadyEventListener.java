package icu.harx.dachuang.ui.event;

import icu.harx.dachuang.ui.service.FxmlService;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class StageReadyEventListener implements ApplicationListener<StageReadyEvent> {

    @Autowired
    private FxmlService fxmlService;

    @Value("${spring.application.ui.title}")
    private String title;
    @Value("${spring.application.ui.main-fxml}")
    private Resource mainFxml;

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        Stage stage = event.getStage();

        try {
            Image icon = new Image("icon/admin.png");

            Parent parent = fxmlService.getParentByURL(mainFxml.getURL());
            Scene scene = new Scene(parent);

            stage.setScene(scene);
            stage.setTitle(title);
            stage.getIcons().add(icon);
            stage.sizeToScene();
            stage.show();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
