package cn.itsource.aiqi.service.impl;

import cn.itsource.aiqi.ProductApplicaiton;
import cn.itsource.aiqi.domain.ProductType;
import cn.itsource.aiqi.service.IProductTypeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProductApplicaiton.class)
public class ProductTypeServiceTest {

    @Autowired
    private IProductTypeService productTypeService;

    @Test
    public void loadTypeTree() {

        List<ProductType> productTypes = productTypeService.loadTypeTree();

    }
}