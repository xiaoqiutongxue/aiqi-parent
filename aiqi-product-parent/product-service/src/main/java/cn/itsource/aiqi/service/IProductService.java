package cn.itsource.aiqi.service;

import cn.itsource.aiqi.domain.Product;
import cn.itsource.aiqi.domain.Specification;
import cn.itsource.aiqi.query.ProductQuery;
import cn.itsource.aiqi.util.PageList;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 商品 服务类
 * </p>
 *
 * @author xiaoqiu
 * @since 2019-10-17
 */
public interface IProductService extends IService<Product> {

    PageList<Product> queryPage(ProductQuery query);
    /**
     * 根据商品ID查询商品的显示属性
     * @param productId
     * @return
     */
    List<Specification> getViewProperties(Long productId);
    /**
     * 保存显示属性
     * @param productId 商品编号
     * @param specifications 显示属性
     * @return
     */
    void saveViewProperties(Long productId, List<Specification> specifications);
    /**
     * 根据商品ID查询商品的Sku属性
     * @param productId
     * @return
     */
    List<Specification> getSkuProperties(Long productId);
}
