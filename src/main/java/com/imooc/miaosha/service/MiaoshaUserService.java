package com.imooc.miaosha.service;

import com.imooc.miaosha.dao.MiaoshaUserDao;
import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.domain.User;
import com.imooc.miaosha.exception.GlobalException;
import com.imooc.miaosha.redis.MiaoshaUserKey;
import com.imooc.miaosha.redis.RedisService;
import com.imooc.miaosha.result.CodeMsg;
import com.imooc.miaosha.util.MD5Util;
import com.imooc.miaosha.util.UUIDUtil;
import com.imooc.miaosha.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
public class MiaoshaUserService {
    public static final String COOKIE_NAME_TOKEN = "token";

    @Autowired(required = false)
    MiaoshaUserDao miaoshaUserDao;
    @Autowired
    RedisService redisService;

    public MiaoshaUser getById(Long id) {
        //取缓存
       MiaoshaUser miaoshaUser= redisService.get(MiaoshaUserKey.getById,""+id,MiaoshaUser.class);
       if(miaoshaUser!=null){
            return  miaoshaUser;
       }
        //取数据库
        miaoshaUser =miaoshaUserDao.getById(id);
        if(miaoshaUser!=null){
            redisService.set(MiaoshaUserKey.getById,""+id,miaoshaUser);
        }
        return miaoshaUser;
    }
    public boolean updatePassWord(String token, long id,String passWordNew){
        //去user
        MiaoshaUser user=getById(id);
        if(user==null){
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        //更新数据库
        MiaoshaUser toBeUpdate=new MiaoshaUser();
        toBeUpdate.setId(id);
        toBeUpdate.setTpassword(MD5Util.formPassToDBPass(passWordNew,user.getSalt()));
        miaoshaUserDao.update(toBeUpdate);
       //处理缓存
        redisService.delete(MiaoshaUserKey.getById,""+id);
        user.setTpassword(toBeUpdate.getTpassword());
        redisService.set(MiaoshaUserKey.token,token,user);
        return  true;

    }


    public MiaoshaUser getByToken(HttpServletResponse response, String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        } else {
            MiaoshaUser miaoshaUser = redisService.get(MiaoshaUserKey.token, token, MiaoshaUser.class);
            if (miaoshaUser != null) {
                //延长有效期
                addCookie(response,token, miaoshaUser);
            }
            return miaoshaUser;

        }

    }

    public boolean login(HttpServletResponse response, LoginVo loginVo) {
        if (loginVo == null) {
            throw new GlobalException(CodeMsg.SERVICE_ERROR);
        }
        String mobile = loginVo.getMobile();
        String formpass = loginVo.getPassword();
        //判断手机号是否存在
        MiaoshaUser miaoshaUser = miaoshaUserDao.getById(Long.parseLong(mobile));
        if (miaoshaUser == null) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        //验证密码
        String dbPass = miaoshaUser.getTpassword();
        String saltDB = miaoshaUser.getSalt();
        String calcPass = MD5Util.formPassToDBPass(formpass, saltDB);
        if (!calcPass.equals(dbPass)) {
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        //生成一个Token
        String token = UUIDUtil.uuid();
        addCookie(response,token, miaoshaUser);
        return true;
    }

    private void addCookie(HttpServletResponse response, String token,MiaoshaUser miaoshaUser) {
        redisService.set(MiaoshaUserKey.token, token, miaoshaUser);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
