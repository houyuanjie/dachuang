package icu.harx.dachuang;

import icu.harx.dachuang.ui.MainJFXApplication;
import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DachuangApplication {

    public static void main(String... args) {
        Application.launch(MainJFXApplication.class, args);
    }
}
