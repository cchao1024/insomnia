package com.example.demo2.web;

import com.example.demo2.Constant;
import com.example.demo2.dao.FallImageRepository;
import com.example.demo2.entity.FallImage;
import com.example.demo2.entity.global.RespBean;
import com.example.demo2.entity.global.RespListBean;
import com.example.demo2.util.SortHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/fallimage")
public class FallImageController {

    @Autowired
    FallImageRepository mFallImageRepository;

    @RequestMapping(value = "/")
    public RespListBean<FallImage> getImageByPage(@RequestParam(value = "page", defaultValue = "1") int page
            , @RequestParam(value = "pageSize", defaultValue = "30") int pageSize) {
        Page<FallImage> data = mFallImageRepository.findAll(PageRequest.of(page, pageSize, SortHelper.basicSortId()));
        RespListBean<FallImage> respListBean = new RespListBean<>();
        respListBean.setCurPage(page);
        respListBean.setTotalPage(data.getTotalPages());
        respListBean.setData(data.getContent());
        return respListBean;
    }

    @RequestMapping(value = "/add")
    public RespBean postFallImage(@ModelAttribute FallImage fallImage) {
        if (fallImage == null) {
            return RespBean.of(Constant.RespCode.Error, "参数不能为空");
        }
        mFallImageRepository.save(fallImage);

        return RespBean.of(Constant.RespCode.Suc, "Success");
    }
}
