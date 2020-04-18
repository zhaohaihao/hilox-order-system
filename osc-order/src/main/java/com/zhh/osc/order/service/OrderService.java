package com.zhh.osc.order.service;

import com.zhh.osc.order.dto.OrderDTO;

/**
 * @author zhh
 * @description
 * @date 2020-04-17 16:10
 */
public interface OrderService {

    /**
     * 创建订单
     * @param orderDTO
     * @return
     */
    OrderDTO create(OrderDTO orderDTO);

    /**
     * 完结订单(只能卖家操作)
     * @param orderId
     * @return
     */
    OrderDTO finish(String orderId);
}
