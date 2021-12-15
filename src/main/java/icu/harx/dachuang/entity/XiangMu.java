package icu.harx.dachuang.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 项目实体类<p>设置了数据库主键自增</p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("dcxmb")
public class XiangMu {

    /**
     * 数据库主键 id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 高校代码
     */
    private Integer gxdm;

    /**
     * 高校名称
     */
    private String gxmc;

    /**
     * 项目编号
     */
    private String xmbh;

    /**
     * 项目级别 { 省级 }
     */
    private String xmjb;

    /**
     * 项目名称
     */
    private String xmmc;

    /**
     * 项目类型 { 创新训练项目, 创业训练项目, 创业实践项目 }
     */
    private String xmlx;

    /**
     * 项目负责人姓名
     */
    private String xmfzrxm;

    /**
     * 项目负责人学号
     */
    private String xmfzrxh;

    /**
     * 项目全体成员信息
     */
    private String xmqtcyxx;

    /**
     * 指导教师姓名
     */
    private String zdjsxm;

    /**
     * 指导教师职称
     */
    private String zdjszc;

    /**
     * 项目简介
     */
    private String xmjj;

    /**
     * 备注
     */
    private String bz;

    /**
     * 项目推荐日期
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDate xmtjrq;
}
