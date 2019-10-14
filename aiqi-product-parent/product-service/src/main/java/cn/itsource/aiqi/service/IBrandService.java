package cn.itsource.aiqi.service;

import cn.itsource.aiqi.domain.Brand;
import cn.itsource.aiqi.query.BrandQuery;
import cn.itsource.aiqi.util.PageList;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 品牌信息 服务类
 * </p>
 *
 * @author xiaoqiu
 * @since 2019-10-12
 */
public interface IBrandService extends IService<Brand> {

    /**
     * 分页高级查询
     * @param query
     * @return
     */
    PageList<Brand> queryPage(BrandQuery query);
}
