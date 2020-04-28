package com.changgou.search.controller;

import com.changgou.search.service.ESManagerService;
import com.changgou.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private ESManagerService esManagerService;

    @Autowired
    private SearchService searchService;

    /**
     * 搜索页面   http://localhost:9009/search/list?keywords=手机&brand=三星&spec_颜色=粉色&
     * 由于页面是thymeleaf 完成的 属于服务器内页面渲染 跳转页面
     *
     * @param searchMap
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("/list")
    public String search(@RequestParam Map<String, String> searchMap, Model model) throws Exception {

        //特殊符号处理
        handlerSearchMap(searchMap);

        //执行查询返回值
        Map<String, Object> resultMap = searchService.search(searchMap);

        model.addAttribute("searchMap", searchMap);
        model.addAttribute("result", resultMap);
        return "search";
    }

    /**
     * 对搜索入参带有特殊符号进行处理
     *
     * @param searchMap
     */
    private void handlerSearchMap(Map<String, String> searchMap) {
        if (null != searchMap) {
            Set<Map.Entry<String, String>> entries = searchMap.entrySet();
            for (Map.Entry<String, String> entry : entries) {
                if (entry.getKey().startsWith("spec_")) {
                    searchMap.put(entry.getKey(), entry.getValue().replace("+", "%2B"));
                }
            }
        }

    }
}