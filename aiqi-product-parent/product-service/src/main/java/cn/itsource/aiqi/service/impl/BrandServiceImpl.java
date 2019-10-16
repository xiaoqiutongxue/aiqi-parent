package cn.itsource.aiqi.service.impl;

import cn.itsource.aiqi.domain.Brand;
import cn.itsource.aiqi.mapper.BrandMapper;
import cn.itsource.aiqi.query.BrandQuery;
import cn.itsource.aiqi.service.IBrandService;
import cn.itsource.aiqi.util.LetterUtil;
import cn.itsource.aiqi.util.PageList;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements IBrandService {

    /**
     * 分页高级查询
     * @param query
     * @return
     */
    @Override
    public PageList<Brand> queryPage(BrandQuery query) {
        IPage<Brand> brandIPage =
                baseMapper.queryPage(new Page(query.getPage(), query.getRows()), query);
        PageList<Brand> pageList = new PageList<>(brandIPage.getTotal(),brandIPage.getRecords());
        return pageList;
    }

    @Override
    public boolean save(Brand brand) {
        //创建时间
        brand.setCreateTime(System.currentTimeMillis());
        //首字母
        String firstLetter = LetterUtil.getFirstLetter(brand.getName()).toUpperCase();
        brand.setFirstLetter(firstLetter);
        //商品类型
        brand.setProductTypeId(brand.getProductType().getId());
        return super.save(brand);
    }

    @Override
    public boolean updateById(Brand brand) {
        //修改时间
        brand.setUpdateTime(System.currentTimeMillis());
        //首字母
        String firstLetter = LetterUtil.getFirstLetter(brand.getName()).toUpperCase();
        brand.setFirstLetter(firstLetter);
        //商品类型
        brand.setProductTypeId(brand.getProductType().getId());
        return super.updateById(brand);
    }

}
