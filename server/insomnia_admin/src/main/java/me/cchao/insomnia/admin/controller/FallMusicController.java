package me.cchao.insomnia.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

import javax.validation.Valid;

import me.cchao.insomnia.api.bean.req.PageDTO;
import me.cchao.insomnia.api.controller.FileController;
import me.cchao.insomnia.api.domain.FallMusic;
import me.cchao.insomnia.api.service.FallService;
import me.cchao.insomnia.api.util.Printer;
import me.cchao.insomnia.api.bean.resp.RespListBean;

/**
 * @author cchao
 * @version 2019-05-16.
 */
@Controller
@RequestMapping("admin/fall_music")
public class FallMusicController {

    @Autowired
    FallService mFallService;
    @Autowired
    FileController mFileController;

    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size, Map<String, Object> map) {

        RespListBean<FallMusic> bean = mFallService.getMusicByPage(PageDTO.of(page, size));

        map.put("data", bean.getData());
        map.put("listBean", bean);
        map.put("curPage", bean.getCurPage());
        map.put("totalPage", bean.getTotalPage());
        map.put("pageSize", size);
        return new ModelAndView("music/list", map);
    }

    @RequestMapping("/add")
    public ModelAndView add(Map<String, Object> map) {
        return new ModelAndView("music/add", map);
    }

    /**
     * 保存新增
     *
     * @param form          表单
     * @param bindingResult 验证
     */
    @PostMapping("/save")
    public ModelAndView save(@Valid FallMusic form, BindingResult bindingResult,
                             Map<String, Object> map) {
        if (bindingResult.hasErrors()) {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "admin/fall_music/save");
            return new ModelAndView("common/error", map);
        }
        FallMusic result = mFallService.save(form);

        map.put("msg", "保存成功 生成id" + result.getId());
        map.put("url", "/admin/fall_music/list");
        return new ModelAndView("common/success", map);
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public String delete(@RequestParam long id) {
        // 提交保存
        mFallService.deleteMusic(id);
        Printer.print("delete fall_music suc " + id);
        return "redirect:/admin/fall_music/list";
    }
}
