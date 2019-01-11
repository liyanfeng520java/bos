package cn.itcast.bos.service;

import cn.itcast.bos.domain.base.Standard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 收派标准业务层接口
 */
public interface StandardService {
    /**
     * 保存操作
     * @param standard
     */
    void save(Standard standard);

    /**
     * 分页查询
     * @param pageable
     * @return
     */
    Page<Standard> findPageData(Pageable pageable);

    /**
     * 查询所有的标准名称,展示在添加快递员的下拉框中
     * @return
     */
    List findC_name();
}
