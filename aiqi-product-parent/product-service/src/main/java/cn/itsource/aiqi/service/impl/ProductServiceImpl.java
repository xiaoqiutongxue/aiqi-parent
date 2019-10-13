package cn.itsource.aiqi.service.impl;

import cn.itsource.aiqi.domain.Product;
import cn.itsource.aiqi.mapper.ProductMapper;
import cn.itsource.aiqi.service.IProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

}
