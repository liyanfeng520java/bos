package cn.itcast.bos.service;

import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.domain.base.Courier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface AreaService {
    /**
     * 导入区域数据
     * @param areas
     */
    void savaBatch(List areas);

    /**
     * 分页查询
     * @param pageable
     * @return
     */
    Page<Area> findPageData(Specification<Area> spec,Pageable pageable);


    /**
     * 保存操作
     * @param area
     */
    void save(Area area);


    /**
     * 删除区域信息
     * @param id
     */
    void delBatch(String id);
}
