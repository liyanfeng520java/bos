package cn.itcast.bos.dao;

import cn.itcast.bos.domain.base.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface AreaDao extends JpaRepository<Area,String>,JpaSpecificationExecutor<Area>{


    /**
     * 查询最大的id    nativeQuery : 使用本地sql的方式查询
     * @return
     */
    @Query(value = "select max(c_id) from t_area",nativeQuery = true)
    public String findMaxId();
}
