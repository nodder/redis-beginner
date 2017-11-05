package name.cdd.study.redis.bookshop.controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;

import name.cdd.study.redis.bookshop.framework.RedisPoolUtil;
import name.cdd.study.redis.bookshop.pojo.Book;
import redis.clients.jedis.Jedis;

@Controller
@RequestMapping(value = "/views")
public class BookShopController
{
    @RequestMapping(value = "/querybook", method = RequestMethod.POST)
    @ResponseBody
    public List<Book> queryAllBooks(HttpServletRequest request, Model model)
    {
        Jedis redis = RedisPoolUtil.getJedis();
        
        try
        {
            Set<String> isbns = redis.zrevrange("shelfTimeByBook", 0, -1);//默认查询按照上架时间排序
//            
//            List<Book> result = Lists.newArrayList();
//            for(String isbn : isbns)
//            {
//                result.add(JSONObject.parseObject(redis.hget("allBookList", isbn), Book.class));
//            }
//            
//            return result;
            
            return isbns.stream()
                    .map(isbn -> redis.hget("allBookList", isbn))
                    .map(jsonStr -> JSONObject.parseObject(jsonStr, Book.class))
                    .collect(Collectors.toList());
        }
        finally
        {
            RedisPoolUtil.returnResource(redis);
        }        
    }
    
    @RequestMapping(value = "/querybookbyprice", method = RequestMethod.POST)
    @ResponseBody
    public List<Book> queryBookByPrice(HttpServletRequest request, Model model)
    {
        boolean isAscend = request.getParameter("ascend").equals("true");
        
        Jedis redis = RedisPoolUtil.getJedis();
        
        try
        {
            Set<String> isbns = isAscend ? redis.zrange("priceByBook", 0, -1) 
                                         : redis.zrevrange("priceByBook", 0, -1);
            
            List<Book> result = Lists.newArrayList();
            for(String isbn : isbns)
            {
                result.add(JSONObject.parseObject(redis.hget("allBookList", isbn), Book.class));
            }
            
            return result;
        }
        finally
        {
            RedisPoolUtil.returnResource(redis);
        }        
    }
    
}
