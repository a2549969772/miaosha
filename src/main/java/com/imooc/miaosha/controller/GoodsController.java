package com.imooc.miaosha.controller;

import com.imooc.miaosha.domain.Goods;
import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.domain.User;
import com.imooc.miaosha.redis.RedisService;
import com.imooc.miaosha.service.GoodsService;
import com.imooc.miaosha.service.MiaoshaUserService;
import com.imooc.miaosha.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/goods")
public class GoodsController {
    private static Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    RedisService redisService;
    @Autowired
    MiaoshaUserService miaoshaUserService;
    @Autowired
    GoodsService goodsService;

    //    @RequestMapping("/to_list")
//    public String list(HttpServletResponse response, Model model, @CookieValue(value = MiaoshaUserService.COOKIE_NAME_TOKEN, required = false) String cookieToken,
//                          @RequestParam(value = MiaoshaUserService.COOKIE_NAME_TOKEN, required = false) String paramToken) {
//        if (StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) {
//            return "login";
//        }
//        String token = StringUtils.isEmpty(paramToken) ? cookieToken : paramToken;
//        MiaoshaUser miaoshaUser = miaoshaUserService.getByToken(response,token);
//        model.addAttribute("user", miaoshaUser);
//        return "goods_list";
//    }
    @RequestMapping("/to_list")
    public String list(Model model, MiaoshaUser miaoshaUser) {
        model.addAttribute("user", miaoshaUser);
        //查询商品列表
        List<GoodsVo> goodsVos = goodsService.listGoodsVo();
        model.addAttribute("goodsList", goodsVos);
        return "goods_list";
    }

    @RequestMapping("/to_detail/{goodsId}")
    public String detail(Model model, MiaoshaUser miaoshaUser,
                         @PathVariable("goodsId") long goodsId) {
        //snowflake 用于ID 有时间看
        model.addAttribute("user",miaoshaUser);
        GoodsVo goodsVo = goodsService.getGoodsByGoodsId(goodsId);
        model.addAttribute("goods",goodsVo);
        long startAt = goodsVo.getStartDate().getTime();
        long endAt = goodsVo.getEndDate().getTime();
        long now = System.currentTimeMillis();
        int miaoshaStatus = 0;
        int remainSeconds = 0;
        if (now < startAt) {//秒杀没有开始
            miaoshaStatus = 0;
            remainSeconds = (int) ((startAt - now) / 1000);
        } else if (now > endAt) {//已经结束
            miaoshaStatus = 2;
            remainSeconds = -1;
        } else {
            miaoshaStatus = 1;
            remainSeconds = 0;
        }
        model.addAttribute("miaoshaStatus",miaoshaStatus);
        model.addAttribute("remainSeconds",remainSeconds);

        return "goods_detail";

    }

}
