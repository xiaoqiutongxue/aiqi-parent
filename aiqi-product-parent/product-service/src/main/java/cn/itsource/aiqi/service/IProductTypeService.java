package cn.itsource.aiqi.service;

import cn.itsource.aiqi.domain.ProductType;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 商品目录 服务类
 * </p>
 *
 * @author xiaoqiu
 * @since 2019-10-12
 */
public interface IProductTypeService extends IService<ProductType> {

    /**
     * 加载类型树
     * @return
     */
    List<ProductType> loadTypeTree();

    void genHomePage();
}
