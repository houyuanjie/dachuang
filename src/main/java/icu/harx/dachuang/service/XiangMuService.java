package icu.harx.dachuang.service;

import icu.harx.dachuang.entity.XiangMu;

import java.util.List;
import java.util.function.Function;

public interface XiangMuService {

    <T> List<T> allPropertyDistinct(Function<XiangMu, T> f, List<XiangMu> xiangMuList);

    List<String> propertiesOf(XiangMu xiangMu);

    boolean propertiesContains(XiangMu xiangMu, CharSequence chars);
}
