package com.zhh.osc.order.server.repository;

import com.zhh.osc.order.server.dataobject.OrderMaster;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author zhh
 * @description
 * @date 2020-04-17 16:05
 */
public interface OrderMasterRepository extends JpaRepository<OrderMaster, String> {
}
