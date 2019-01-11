package cn.itcast.bos.controller;


import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.domain.common.CommonCode;
import cn.itcast.bos.domain.common.ResponseResult;
import cn.itcast.bos.service.CourierService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/courierController")
public class CourierController {

    @Autowired
    private CourierService courierService;


    /**
     * 保存操作
     *
     * @param courier
     * @return
     */
    @RequestMapping("/save")
    public ResponseResult save(Courier courier) {
        try {
            courierService.save(courier);
            return new ResponseResult(CommonCode.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult(CommonCode.FAIL);
        }
    }

    /**
     * 分页查询+条件查询
     *
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/pageQuery")
    public Map pageQuery(int page, int rows, Courier courier) {
        //构架分页查询条件
        Pageable pageable = PageRequest.of(page - 1, rows);

        //构建查询条件
        Specification<Courier> spec = new Specification<Courier>() {
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {

                //cb:构建查询，添加查询方式   like：模糊匹配
                //root：从实体Customer对象中按照custName属性进行查询
                //isNotBlank(str) 等价于 str != null && str.length > 0 && str.trim().length > 0
                Path courierNum = root.get("courierNum");//快递员工号
                Path company = root.get("company");//单位
                Path type = root.get("type");//取件员类型

                List<Predicate> list = new ArrayList<>();//储存查询条件

                //构造查询
                //快递员工号
                //单表查询 isNotBlank(str) 等价于str!=null && str.length>0 && str.trim().length > 0
                if (StringUtils.isNotBlank(courier.getCourierNum())) {
                    Predicate p1 = cb.equal(courierNum.as(String.class), courier.getCourierNum());
                    list.add(p1);
                }
                //单位
                if (StringUtils.isNotBlank(courier.getCompany())) {
                    Predicate p2 = cb.like(company.as(String.class), courier.getCompany());
                    list.add(p2);
                }
                //取件员类型
                if (StringUtils.isNotBlank(courier.getType())) {
                    Predicate p3 = cb.equal(type.as(String.class), "%" + courier.getType() + "%");
                    list.add(p3);
                }

                //多表查询
                Join<Courier, Standard> standardJoin = root.join("standard", JoinType.INNER);
                if (courier.getStandard() != null && StringUtils.isNotBlank(courier.getStandard().getName())) {
                    Predicate p4 = cb.like(standardJoin.get("name").as(String.class), "%" + courier.getStandard().getName() + "%");
                    list.add(p4);
                }
                //将查询条件组合到一起
                return cb.and(list.toArray(new Predicate[0]));
            }
        };

        Page<Courier> pageData = courierService.findPageData(spec, pageable);

        //返回客户端数据  需要total和rows
        HashMap<String, Object> result = new HashMap();
        result.put("total", pageData.getTotalElements());//总条数
        result.put("rows", pageData.getContent());
        return result;
    }

    /**
     * 删除快递员
     * @param list
     * @return
     */
    @RequestMapping("/delBatch")
    public ResponseResult pageQuery(@RequestBody List<Courier> list){
        System.out.println(list);
        return null;
    }
}
