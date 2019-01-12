package cn.itcast.bos.service.impl;

import cn.itcast.bos.dao.AreaDao;
import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaDao areaDao;


    /**
     * 批量导入区域数据
     * @param areas
     */
    @Override
    public void savaBatch(List areas) {
        areaDao.saveAll(areas);
    }


    /**
     * 分页查询+条件查询
     * @param pageable
     * @return
     */
    @Override
    public Page<Area> findPageData(Specification<Area> spec, Pageable pageable) {
        return areaDao.findAll(spec,pageable);
    }
}
