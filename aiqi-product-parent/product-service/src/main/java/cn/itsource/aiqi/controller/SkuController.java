package cn.itsource.aiqi.controller;

import cn.itsource.aiqi.service.ISkuService;
import cn.itsource.aiqi.domain.Sku;
import cn.itsource.aiqi.query.SkuQuery;
import cn.itsource.aiqi.util.AjaxResult;
import cn.itsource.aiqi.util.PageList;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sku")
public class SkuController {
    @Autowired
    public ISkuService skuService;

    /**
    * 保存和修改公用的
    */
    @RequestMapping(value="/add",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody Sku sku){
        try {
            if(sku.getId()!=null){
                skuService.updateById(sku);
            }else{
                skuService.save(sku);
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
            skuService.removeById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！"+e.getMessage());
        }
    }

    //获取
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Sku get(@PathVariable("id") Long id)
    {
        return skuService.getById(id);
    }


    /**
    * 查看所有
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<Sku> list(){

        return skuService.list(null);
    }


    /**
    * 分页查询数据
    */
    @RequestMapping(value = "/json",method = RequestMethod.POST)
    public PageList<Sku> json(@RequestBody SkuQuery query)
    {
        Page<Sku> page = new Page<Sku>(query.getPage(),query.getRows());
        IPage<Sku> ipage = skuService.page(page);
        return new PageList<Sku>(ipage.getTotal(),ipage.getRecords());
    }
}
