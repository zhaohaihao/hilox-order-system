package com.hilox.order.controller;

import com.hilox.order.converter.OrderForm2OrderDTOConverter;
import com.hilox.order.dto.OrderDTO;
import com.hilox.order.enums.ResultEnum;
import com.hilox.order.exception.SellException;
import com.hilox.order.form.OrderForm;
import com.hilox.order.service.BuyerService;
import com.hilox.order.service.OrderService;
import com.hilox.order.utils.ResultVOUtil;
import com.hilox.order.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单Controller
 * Created by Hilox on 2018/12/21 0021.
 */
@Controller
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private BuyerService buyerService;

    /**
     * 创建订单
     */
    @PostMapping("/create")
    @ResponseBody
    public ResultVO<Map<String, String>> create(@Valid OrderForm orderForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【创建订单】参数不正确, orderForm={}", orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }

        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("【创建订单】购物车不能为空");
            throw new SellException(ResultEnum.CART_EMPTY);
        }

        OrderDTO orderResult = orderService.create(orderDTO);

        Map<String, String> map = new HashMap<>();
        map.put("orderId", orderResult.getId());

        return ResultVOUtil.success(map);
    }

    /**
     * 订单列表
     */
    @GetMapping("/list")
    @ResponseBody
    public ResultVO<List<OrderDTO>> list(@RequestParam("openid") String openid,
                                         @RequestParam(value = "page", defaultValue = "0") Integer page,
                                         @RequestParam(value = "size", defaultValue = "10") Integer size) {
        if (StringUtils.isEmpty(openid)) {
            log.error("【查询订单列表】openid为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        PageRequest pageRequest = new PageRequest(page, size);
        Page<OrderDTO> orderDTOPage = orderService.findList(openid, pageRequest);
        return ResultVOUtil.success(orderDTOPage.getContent());
    }

    /**
     * 订单详情
     */
    @GetMapping("/detail")
    @ResponseBody
    public ResultVO<OrderDTO> detail(@RequestParam("openid") String openid,
                                     @RequestParam("orderId") String orderId) {
        OrderDTO orderDTO = buyerService.findOrderOne(openid, orderId);
        return ResultVOUtil.success(orderDTO);
    }

    /**
     * 取消订单
     */
    @PostMapping("/cancel")
    @ResponseBody
    public ResultVO cancel(@RequestParam("openid") String openid,
                                     @RequestParam("orderId") String orderId) {
        buyerService.cancelOrder(openid, orderId);
        return ResultVOUtil.success();
    }
}
