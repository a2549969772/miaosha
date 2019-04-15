package com.imooc.miaosha.service;

import com.imooc.miaosha.dao.GoodsDao;
import com.imooc.miaosha.domain.MiaoshaGoods;
import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.domain.OrderInfo;
import com.imooc.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsService {

    @Autowired(required = false)
    GoodsDao goodsDao;
    public List<GoodsVo> listGoodsVo(){
        return  goodsDao.listGoodsVo();
    }
    public GoodsVo getGoodsByGoodsId(long goodsId){
        return  goodsDao.getGoodsByGoodsId(goodsId);
    }

   public void reduceStock(GoodsVo goodsVo){
            MiaoshaGoods miaoshaGoods=new MiaoshaGoods();
       miaoshaGoods.setGoodsId(goodsVo.getId());

       goodsDao.reduceStock(miaoshaGoods);
   }
}
