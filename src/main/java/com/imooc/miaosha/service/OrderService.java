package com.imooc.miaosha.service;

import com.imooc.miaosha.dao.MiaoshaOrderDao;
import com.imooc.miaosha.domain.MiaoshaOrder;
import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.domain.OrderInfo;
import com.imooc.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class OrderService {
    @Autowired(required = false)
    MiaoshaOrderDao miaoshaOrderDao;

    public MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(long userId, long goodsId) {
        return miaoshaOrderDao.getMiaoshaOrderByUserIdGoodsId(userId, goodsId);
    }
@Transactional
    public OrderInfo createOrder(MiaoshaUser miaoshaUser, GoodsVo goodsVo){
        OrderInfo orderInfo=new OrderInfo();
        orderInfo.setCreateDate(new Date());
        orderInfo.setDeliveryAddId(0L);
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsId(goodsVo.getId());
        orderInfo.setGoodsName(goodsVo.getGoodsName());
        orderInfo.setGoodsPrice(goodsVo.getMiaoshaPrice());
        orderInfo.setOrderChannel(1);
        orderInfo.setStatus(0);
        orderInfo.setUserId(miaoshaUser.getId());
        long orderId=  miaoshaOrderDao.insert(orderInfo);
        MiaoshaOrder miaoshaOrder=new MiaoshaOrder();
        miaoshaOrder.setGoodsId(goodsVo.getId());
        miaoshaOrder.setOrderId(orderId);
        miaoshaOrder.setUserId(miaoshaUser.getId());

        miaoshaOrderDao.insertMiaoshaOrder(miaoshaOrder);
        return orderInfo;
    }

}
