package icu.harx.dachuang.service;

import icu.harx.dachuang.entity.XiangMu;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class XiangMuServiceImpl implements XiangMuService {

    @Override
    public <T> List<T> allPropertyDistinct(Function<XiangMu, T> mapper, List<XiangMu> xiangMuList) {
        if (xiangMuList == null) return List.of();

        return xiangMuList.stream()
                .flatMap(xiangMu -> Stream.ofNullable(xiangMu).map(mapper))
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<String> propertiesOf(XiangMu xiangMu) {
        if (xiangMu == null) return List.of();

        Stream<Stream<String>> stringStreamStream = Stream.of(
                Stream.ofNullable(String.valueOf(xiangMu.getId())),
                Stream.ofNullable(String.valueOf(xiangMu.getGxdm())),
                Stream.ofNullable(xiangMu.getGxmc()),
                Stream.ofNullable(xiangMu.getXmbh()),
                Stream.ofNullable(xiangMu.getXmjb()),
                Stream.ofNullable(xiangMu.getXmmc()),
                Stream.ofNullable(xiangMu.getXmlx()),
                Stream.ofNullable(xiangMu.getXmfzrxm()),
                Stream.ofNullable(xiangMu.getXmfzrxh()),
                Stream.ofNullable(xiangMu.getXmqtcyxx()),
                Stream.ofNullable(xiangMu.getZdjsxm()),
                Stream.ofNullable(xiangMu.getZdjszc()),
                Stream.ofNullable(xiangMu.getXmjj()),
                Stream.ofNullable(xiangMu.getBz())
        );

        Stream<String> stringStream = stringStreamStream.flatMap(x -> x);

        return stringStream.collect(Collectors.toUnmodifiableList());
    }

    @Override
    public boolean propertiesContains(XiangMu xiangMu, CharSequence chars) {
        if (chars == null || chars.toString().isBlank()) return true;

        String[] words = chars.toString()
                .replaceAll("[,.;，、；]", " ")
                .split("\\s+");

        Stream<CharSequence> wordStream = Arrays.stream(words);

        return wordStream.allMatch(word ->
                propertiesOf(xiangMu).stream()
                        .anyMatch(property -> property.contains(word))
        );
    }
}
