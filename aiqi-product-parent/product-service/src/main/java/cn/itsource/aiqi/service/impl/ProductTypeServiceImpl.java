package cn.itsource.aiqi.service.impl;

import cn.itsource.aiqi.client.RedisClient;
import cn.itsource.aiqi.client.StaticPageClient;
import cn.itsource.aiqi.domain.ProductType;
import cn.itsource.aiqi.mapper.ProductTypeMapper;
import cn.itsource.aiqi.service.IProductTypeService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ProductTypeServiceImpl extends ServiceImpl<ProductTypeMapper, ProductType> implements IProductTypeService {

    @Autowired
    private RedisClient redisClient;
    @Autowired
    private StaticPageClient staticPageClient;

    /**
     * 静态化商城首页
     */
    @Override
    public void genHomePage() {
        //先根据product.type.vm模板生成product.type.vm.html
        String templatePath = "E:\\JetBrains\\IdeaProjects\\aiqi-parent\\aiqi-product-parent\\product-service\\src\\main\\resources\\template\\product.type.vm";//模板路径
        String targetPath = "E:\\JetBrains\\IdeaProjects\\aiqi-parent\\aiqi-product-parent\\product-service\\src\\main\\resources\\template\\product.type.vm.html";
        List<ProductType> productTypes = loadTypeTree();
        staticPageClient.generateStaticPage(templatePath,targetPath,productTypes);

        //再根据home.vm生成html.html
        templatePath = "E:\\JetBrains\\IdeaProjects\\aiqi-parent\\aiqi-product-parent\\product-service\\src\\main\\resources\\template\\home.vm";
        targetPath = "E:\\JetBrains\\IdeaProjects\\aiqi-web-parent\\ecommerce\\home.html";
        Map<String,Object> model = new HashMap<>();
        model.put("staticRoot","E:\\JetBrains\\IdeaProjects\\aiqi-parent\\aiqi-product-parent\\product-service\\src\\main\\resources\\");
        staticPageClient.generateStaticPage(templatePath,targetPath,model);
    }

    /**
     * 加载类型树
     * @return
     */
    @Override
    public List<ProductType> loadTypeTree() {
        //先查询Redis中的数据  java对象存储到redis中的方案-json字符串
        String productTypesStr = redisClient.get("productTypes");
        System.out.println("查询Redis.....");

        //解决缓存穿透问题代码   同步代码块，双重验证，在代码块外部判断redis中是否有值
        //代码块中再次后取redis的数据进行判断
        List<ProductType> productTypes = null;
        if(StringUtils.isEmpty(productTypesStr)){
            synchronized (this){
                productTypesStr = redisClient.get("productTypes");
                if(StringUtils.isEmpty(productTypesStr)){
                    //查询数据库
                    productTypes = loadTypeTreeLoop2();
                    System.out.println("查询数据库..........");
                    //把数据缓存到redis中-json字符串
                    productTypesStr = JSON.toJSONString(productTypes);//TODO 转json字符串(productTypes);
                    redisClient.set("productTypes", productTypesStr);
                }else{
                    productTypes = JSONArray.parseArray(productTypesStr,ProductType.class);
                }
                return productTypes;
            }
        }
        //返回给前端 -- json字符串转java对象
        productTypes = JSONArray.parseArray(productTypesStr,ProductType.class);//TODO 转java集合(productTypesStr);
        return productTypes;

    }


    /**
     * 方式一：递归
     * 不好：每次递归都要发送一次sql，效率太低，递归可读性太差，还可能会造成栈溢出
     * 能不适用递归尽量不使用递归
     * @param parentId
     * @return
     */
    private List<ProductType> loadTypeTreeRecursive(Long parentId){
        List<ProductType> productTypes =
                baseMapper.selectList(new QueryWrapper<ProductType>().eq("pid",parentId));
        if(productTypes==null){
            return null;
        }
        for (ProductType productType : productTypes) {
            List<ProductType> children = loadTypeTreeRecursive(productType.getId());
            if(children!=null){
                productType.setChildren(children);
            }
        }
        return productTypes;
    }

    /**
     * 循环一
     * @return
     */
    private List<ProductType> loadTypeTreeLoop(){
        //先查询所有
        List<ProductType> allProductTypes = baseMapper.selectList(null); //1000
        //再组装数据
        List<ProductType> firstLevelTypes = new ArrayList<>();
        //  1000
        for (ProductType productType : allProductTypes) {

            if(productType.getPid()==0){
                //如果你是一级类型，则放入firstLevelTypes
                firstLevelTypes.add(productType);
            }else{
                //如果不是，放入父类型的children属性中   1000
                for (ProductType parent : allProductTypes) {
                    if(parent.getId().equals(productType.getPid())){
                        parent.getChildren().add(productType);
                    }
                }
            }

        }
        return firstLevelTypes;
    }

    /**
     * 循环二
     * @return
     */
    private List<ProductType> loadTypeTreeLoop2(){
        //先查询所有
        List<ProductType> allProductTypes = baseMapper.selectList(null); //1000
        //再组装数据
        List<ProductType> firstLevelTypes = new ArrayList<>();

        Map<Long,ProductType> productTypeMap = new HashMap<>();

        //将所有的productType存入map中
        for (ProductType productType : allProductTypes) {
            productTypeMap.put(productType.getId(),productType);
        }

        //再循环组装数据
        for (ProductType productType : allProductTypes) {
            //如果是一级
            if(productType.getPid()==0){
                firstLevelTypes.add(productType);
            }else{
                //如果不是一级
                ProductType parent = productTypeMap.get(productType.getPid());
                parent.getChildren().add(productType);
            }
        }
        return firstLevelTypes;
    }

    //重写增删改操作，同步redis
    @Override
    public boolean save(ProductType entity) {
        boolean result = super.save(entity);
        synchronizedOption();
        return result;
    }

    @Override
    public boolean removeById(Serializable id) {
        boolean result = super.removeById(id);
        synchronizedOption();
        return result;
    }

    @Override
    public boolean updateById(ProductType entity) {
        boolean result = super.updateById(entity);
        synchronizedOption();;
        return result;
    }

    /**
     * 增删改的同步操作
     */
    private void synchronizedOption(){
        //同步redis的数据
        List<ProductType> productTypes = loadTypeTreeLoop2();
        String productTypesStr = JSON.toJSONString(productTypes);
        redisClient.set("productTypes",productTypesStr);
        //生成home.html静态页面
        genHomePage();
    }
}
