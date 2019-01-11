package cn.itcast.bos.dao;

import cn.itcast.bos.domain.base.Courier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CourierDao extends JpaRepository<Courier,Integer>,JpaSpecificationExecutor<Courier> {
}
