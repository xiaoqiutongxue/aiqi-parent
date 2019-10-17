package cn.itsource.aiqi.service.impl;

import cn.itsource.aiqi.domain.ProductComment;
import cn.itsource.aiqi.mapper.ProductCommentMapper;
import cn.itsource.aiqi.service.IProductCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品评价 服务实现类
 * </p>
 *
 * @author xiaoqiu
 * @since 2019-10-17
 */
@Service
public class ProductCommentServiceImpl extends ServiceImpl<ProductCommentMapper, ProductComment> implements IProductCommentService {

}
