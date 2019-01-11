package cn.itcast.bos.service.impl;

import cn.itcast.bos.dao.CourierDao;
import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.service.CourierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class CourierServiceImpl implements CourierService {

    @Autowired
    private CourierDao courierDao;

    /**
     * 保存操作
     * @param courier
     */
    @Override
    public void save(Courier courier) {
        courierDao.save(courier);
    }


    /**
     * 分页查询
     * @param pageable
     * @return
     */
    @Override
    public Page<Courier> findPageData(Specification<Courier> specification,Pageable pageable) {
        return courierDao.findAll(specification,pageable);
    }
}
