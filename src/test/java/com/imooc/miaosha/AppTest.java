package com.imooc.miaosha;

import static org.junit.Assert.assertTrue;

import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.redis.GoodsKey;
import com.imooc.miaosha.vo.GoodsVo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Unit test for simple App.
 */
@Controller
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;
    @Test
    @RequestMapping(value = "/redis_test", produces = "text/html")
    @ResponseBody
    public String list(HttpServletRequest request, HttpServletResponse response, Model model, MiaoshaUser miaoshaUser) {
        model.addAttribute("user", miaoshaUser);
        WebContext webContext = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
        //手动渲染
        String html = thymeleafViewResolver.getTemplateEngine().process("goods_list", webContext);

        return html;
    }
}
