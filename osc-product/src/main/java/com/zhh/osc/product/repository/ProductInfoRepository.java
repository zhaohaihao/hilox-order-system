package com.zhh.osc.product.repository;

import com.zhh.osc.product.dataobject.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author zhh
 * @description 商品Repository
 * @date 2020-04-17 15:24
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo, String> {

    List<ProductInfo> findByProductStatus(Integer productStatus);
}
