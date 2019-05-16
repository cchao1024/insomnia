package me.cchao.insomnia.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

import javax.validation.Valid;

import me.cchao.insomnia.api.bean.req.PageDTO;
import me.cchao.insomnia.api.controller.FileController;
import me.cchao.insomnia.api.domain.FallImage;
import me.cchao.insomnia.api.service.FallService;
import me.cchao.insomnia.common.RespListBean;

/**
 * @author cchao
 * @version 2019-05-16.
 */
@Controller
@RequestMapping("admin/fall_image")
public class FallImageController {

    @Autowired
    FallService mFallService;
    @Autowired
    FileController mFileController;

    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size, Map<String, Object> map) {

        RespListBean<FallImage> bean = mFallService.getImageByPage(PageDTO.of(page, size));

        map.put("data", bean.getData());
        map.put("listBean", bean);
        map.put("curPage", bean.getCurPage());
        map.put("totalPage", bean.getTotalPage());
        map.put("pageSize", size);
        return new ModelAndView("image/list", map);
    }

    @RequestMapping("/add")
    public ModelAndView add(Map<String, Object> map) {
        return new ModelAndView("image/add", map);
    }

    /**
     * 保存新增
     *
     * @param form          表单
     * @param bindingResult 验证
     * @return
     */
    @PostMapping("/save")
    public ModelAndView save(@Valid FallImage form, @RequestParam("file") MultipartFile[] uploadingFiles,
                             BindingResult bindingResult,
                             Map<String, Object> map) {
        if (bindingResult.hasErrors()) {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "admin/fall_image/save");
            return new ModelAndView("common/error", map);
        }
        try {
            // 上传图片
            String relativePath = mFileController.upload(uploadingFiles).getData().getRelativeUrl();
            form.setSrc(relativePath);
            // 提交保存
            mFallService.save(form);
        } catch (Exception e) {
            map.put("msg", "文件上传失败");
            map.put("url", "admin/fall_image/save");
            return new ModelAndView("common/error", map);
        }

        map.put("url", "/admin/fall_image/list");
        return new ModelAndView("common/success", map);
    }

    /*
     *//**
     * 商品上架
     *
     * @param productId
     * @param map
     * @return
     *//*
    @GetMapping("/on_sale")
    public ModelAndView onSale(@RequestParam("productId") String productId,
                               Map<String, Object> map) {
        try {
            productService.onSale(productId);
        } catch (SellException e) {
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView("common/error", map);
        }
        map.put("url", "/sell/seller/product/list");
//        map.put()
        return new ModelAndView("common/success", map);
    }

    @GetMapping("/off_sale")
    public ModelAndView offSale(@RequestParam("productId") String productId,
                                Map<String, Object> map) {
        try {
            productService.offSale(productId);
        } catch (SellException e) {
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView("common/error", map);
        }
        map.put("url", "/sell/seller/product/list");
//        map.put()
        return new ModelAndView("common/success", map);
    }

    @GetMapping("index")
    public ModelAndView index(@RequestParam(value = "productId", required = false) String productId,
                              Map<String, Object> map) {
        if (!StringUtils.isEmpty(productId)) {
            ProductInfo productInfo = productService.findOne(productId);
            map.put("productInfo", productInfo);
        }

        //查询所有的类目
        List<ProductCategory> categoryList = categoryService.findAll();
        map.put("categoryList", categoryList);
        return new ModelAndView("product/index", map);
    }

    *//**
     * 保存/更新
     *
     * @param form
     * @param bindingResult
     * @return
     *//*
    @PostMapping("/save")
//    @CachePut(cacheNames = "product",key = "123")
    @CacheEvict(cacheNames = "product", key = "123")
    public ModelAndView save(@Valid ProductForm form,
                             BindingResult bindingResult,
                             Map<String, Object> map) {
        if (bindingResult.hasErrors()) {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "sell/seller/product/index");
            return new ModelAndView("common/error", map);
        }

        ProductInfo productInfo = new ProductInfo();
        try {
            //如果productId不为空，说明是已有商品
            if (!StringUtils.isEmpty(form.getProductId())) {
                productInfo = productService.findOne(form.getProductId());
            } else {
                form.setProductId(KeyUtil.genUniqueKey());
            }
            BeanUtils.copyProperties(form, productInfo);
            productService.save(productInfo);
        } catch (SellException e) {
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/product/index");
            return new ModelAndView("common/error", map);
        }
        map.put("url", "/sell/seller/product/list");
        return new ModelAndView("common/success", map);
    }*/
}
