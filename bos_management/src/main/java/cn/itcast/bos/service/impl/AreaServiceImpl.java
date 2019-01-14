package cn.itcast.bos.service.impl;

import cn.itcast.bos.dao.AreaDao;
import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.service.AreaService;
import org.apache.commons.lang3.StringUtils;
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


    /**
     * 保存操作
     * @param area
     */
    @Override
    public void save(Area area) {
        //如果传递的数据中没有id,保存之前获取当前数据库中最大的id
        if(StringUtils.isBlank(area.getId())){
            String maxId = areaDao.findMaxId();
            //设置当前添加的数据的id为最大id + 1
            area.setId(Integer.parseInt(maxId)+1+"");
        }
        areaDao.save(area);
    }

    /**
     * 删除区域信息
     * @param id
     */
    @Override
    public void delBatch(String id) {
        areaDao.deleteById(id);
    }
}
