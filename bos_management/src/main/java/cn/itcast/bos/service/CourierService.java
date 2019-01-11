package cn.itcast.bos.service;


import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.domain.base.Standard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

/**
 * 快递员设置业务层接口
 */
public interface CourierService {
    /**
     * 保存操作
     * @param courier
     */
    void save(Courier courier);


    /**
     * 分页查询
     * @param pageable
     * @return
     */
    Page<Courier> findPageData(Specification<Courier> specification,Pageable pageable);
}
