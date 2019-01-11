package cn.itcast.bos.service.impl;

import cn.itcast.bos.dao.StandardDao;
import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.service.StandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class StandardServiceImpl implements StandardService {

    @Autowired
    private StandardDao standardDao;

    /**
     * 保存操作
     *
     * @param standard
     */
    @Override
    public void save(Standard standard) {
        standardDao.save(standard);
    }


    /**
     * 分页查询
     * @param pageable
     * @return
     */
    @Override
    public Page<Standard> findPageData(Pageable pageable) {
        return standardDao.findAll(pageable);
    }


    /**
     * 查询所有的标准名称,展示在添加快递员的下拉框中
     * @return
     */
    @Override
    public List findC_name() {
        return standardDao.findAll();
    }
}
