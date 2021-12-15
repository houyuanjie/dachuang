package icu.harx.dachuang;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import icu.harx.dachuang.entity.XiangMu;
import icu.harx.dachuang.mapper.XiangMuMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
class DachuangApplicationTests {

    @Autowired
    private XiangMuMapper xiangMuMapper;

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void contextLoads() {
    }

    @Test
    void crud() {
        var wrapper = new QueryWrapper<XiangMu>();
        wrapper.lambda()
                .like(XiangMu::getBz, "推荐国家级")
                .like(XiangMu::getGxmc, "西安电子科技大学");

        Stream<XiangMu> xiangMus = xiangMuMapper.selectList(wrapper).stream();

        List<XiangMu> collect = xiangMus.filter(xiangMu -> xiangMu.getXmtjrq().isBefore(LocalDate.of(2019, 9, 10)))
                .collect(Collectors.toUnmodifiableList());

        for (XiangMu xiangMu : collect) {
            System.out.println(xiangMu);
        }
    }

    void entity() {

    }

}
