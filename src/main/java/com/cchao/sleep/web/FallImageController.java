package com.cchao.sleep.web;

import com.cchao.sleep.dao.FallImage;
import com.cchao.sleep.Constant;
import com.cchao.sleep.repository.FallImageRepository;
import com.cchao.sleep.json.RespBean;
import com.cchao.sleep.json.RespListBean;
import com.cchao.sleep.util.SortHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/fallimage")
public class FallImageController {

    @Autowired
    FallImageRepository mFallImageRepository;

    @RequestMapping(value = "/getByPage")
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
    public RespBean postFallImage(@ModelAttribute FallImage fallImage, @RequestParam(value = "key") String key) {
        if (StringUtils.isEmpty(key) || !key.equalsIgnoreCase("cc")) {
            return RespBean.of(Constant.Code.Error, "权限验证失败");
        }
        if (fallImage == null) {
            return RespBean.of(Constant.Code.Error, Constant.Msg.Empty_Param);
        }
        fallImage.setAdd_time(System.currentTimeMillis()+"");
        mFallImageRepository.save(fallImage);

        return RespBean.suc();
    }
}
