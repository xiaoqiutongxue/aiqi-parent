package cn.itsource.aiqi.controller;

import cn.itsource.aiqi.service.IProductExtService;
import cn.itsource.aiqi.domain.ProductExt;
import cn.itsource.aiqi.query.ProductExtQuery;
import cn.itsource.aiqi.util.AjaxResult;
import cn.itsource.aiqi.util.PageList;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productExt")
public class ProductExtController {
    @Autowired
    public IProductExtService productExtService;

    /**
    * 保存和修改公用的
    */
    @RequestMapping(value="/add",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody ProductExt productExt){
        try {
            if(productExt.getId()!=null){
                productExtService.updateById(productExt);
            }else{
                productExtService.save(productExt);
            }
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("保存对象失败！"+e.getMessage());
        }
    }

    /**
    * 删除对象信息
    */
    @RequestMapping(value="/delete/{id}",method=RequestMethod.DELETE)
    public AjaxResult delete(@PathVariable("id") Integer id){
        try {
            productExtService.removeById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！"+e.getMessage());
        }
    }

    //获取
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public ProductExt get(@PathVariable("id") Long id)
    {
        return productExtService.getById(id);
    }


    /**
    * 查看所有
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<ProductExt> list(){

        return productExtService.list(null);
    }


    /**
    * 分页查询数据
    */
    @RequestMapping(value = "/json",method = RequestMethod.POST)
    public PageList<ProductExt> json(@RequestBody ProductExtQuery query)
    {
        Page<ProductExt> page = new Page<ProductExt>(query.getPage(),query.getRows());
        IPage<ProductExt> ipage = productExtService.page(page);
        return new PageList<ProductExt>(ipage.getTotal(),ipage.getRecords());
    }
}
