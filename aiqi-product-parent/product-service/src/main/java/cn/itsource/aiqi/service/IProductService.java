package cn.itsource.aiqi.service;

import cn.itsource.aiqi.domain.Product;
import cn.itsource.aiqi.query.ProductQuery;
import cn.itsource.aiqi.util.PageList;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
