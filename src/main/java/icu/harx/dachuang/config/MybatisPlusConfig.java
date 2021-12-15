package icu.harx.dachuang.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
@MapperScan("icu.harx.dachuang.mapper")
public class MybatisPlusConfig {

    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new MetaObjectHandler() {
            @Override
            public void insertFill(MetaObject metaObject) {
                // 插入时填充项目推荐日期
                this.strictInsertFill(metaObject, "xmtjrq", LocalDate::now, LocalDate.class);
            }

            @Override
            public void updateFill(MetaObject metaObject) {
                // 暂无更新时填充...
            }
        };
    }
}
