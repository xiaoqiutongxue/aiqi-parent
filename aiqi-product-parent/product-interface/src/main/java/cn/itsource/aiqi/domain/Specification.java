package cn.itsource.aiqi.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_specification")
public class Specification implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 规格名称
     */
    @TableField("specName")
    private String specName;

    @TableField("isSku")
    private Integer isSku;

    @TableField("product_type_id")
    private Long productTypeId;

    /**
     * 显示属性的值
     */
    @TableField(exist = false)
    private String value;

    /**
     * sku属性的值
     */
    @TableField(exist = false)
    private List<String> options = new ArrayList<>();


}
