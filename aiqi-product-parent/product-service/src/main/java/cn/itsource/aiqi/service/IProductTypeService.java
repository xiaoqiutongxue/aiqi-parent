package cn.itsource.aiqi.service;

import cn.itsource.aiqi.domain.ProductType;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface IProductTypeService extends IService<ProductType> {

    /**
     * 加载类型树
     * @return
     */
    List<ProductType> loadTypeTree();
}
