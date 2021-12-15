package icu.harx.dachuang.ui.service;

import javafx.scene.Parent;

import java.net.URL;

public interface FxmlService {

    <T extends Parent> T getParentByURL(URL url);

    <T extends Parent> T getParentByURLAndSet(URL url, Object controller);
}
