package cn.itcast.bos.service;

import cn.itcast.bos.domain.base.Standard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
}
