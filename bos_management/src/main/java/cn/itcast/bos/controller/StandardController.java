package cn.itcast.bos.controller;


import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.domain.common.CommonCode;
import cn.itcast.bos.domain.common.ResponseResult;
import cn.itcast.bos.service.StandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/standardController")
public class StandardController {

    @Autowired
    private StandardService standardService;

    /**
     * 保存操作
     * @param standard
     * @return
     */
    @RequestMapping("/save")
    public ResponseResult save(Standard standard) {
        try {
            standardService.save(standard);
            return new ResponseResult(CommonCode.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult(CommonCode.FAIL);
        }
    }


    /**
     * 分页查询
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/pageQuery")
    public Map pageQuery(int page,int rows){
        //构架分页查询条件
        Pageable pageable = PageRequest.of(page - 1, rows);
        Page<Standard> pageData = standardService.findPageData(pageable);

        //返回客户端数据  需要total和rows
        HashMap<String,Object> result = new HashMap();
        result.put("total",pageData.getTotalElements());//总条数
        result.put("rows",pageData.getContent());
        return result;
    }
}
