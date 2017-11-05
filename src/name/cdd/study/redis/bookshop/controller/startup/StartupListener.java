package name.cdd.study.redis.bookshop.controller.startup;

import static name.cdd.study.redis.bookshop.framework.CommonUtil.toTimeLong;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;

import name.cdd.study.redis.bookshop.framework.RedisPoolUtil;
import name.cdd.study.redis.bookshop.pojo.Book;
import redis.clients.jedis.Jedis;

@Component
public class StartupListener implements ApplicationContextAware
{
    /**
     * 系统启动时，把所有的图书信息都装载到redis数据库中
     * @param books 
     */
    @Override
    public void setApplicationContext(ApplicationContext arg0) throws BeansException
    {
        List<Book> books = readBooksFromDB();
        
        Jedis jedis = RedisPoolUtil.getJedis();
        loadAllBooks(jedis, books);
        setByShelfTime(jedis, books);
        setByPrice(jedis, books);
        RedisPoolUtil.returnResource(jedis);
        
        System.out.println("Intialized, all books loaded.");
    }

    private void loadAllBooks(Jedis jedis, List<Book> books){
        Map<String, String> map = new HashMap<>();
//        for(Book book : books)
//        {
//            map.put(book.getIsbn(), JSONObject.toJSONString(book));
//        }
        
        books.stream().forEach(book -> map.put(book.getIsbn(), JSONObject.toJSONString(book)));
        
        jedis.hmset("allBookList",map);   
    }
    
    //按照上架时间进行排序显示
    private void setByShelfTime(Jedis jedis, List<Book> books){
//        for(Book book : books)
//        {
//            jedis.zadd("shelfTimeByBook", book.getShelfTime(), book.getIsbn());
//        }
        books.stream().forEach(book -> jedis.zadd("shelfTimeByBook", book.getShelfTime(), book.getIsbn()));
    }
    
    //按照价格排序
    private void setByPrice(Jedis jedis, List<Book> books){
//        for(Book book : books)
//        {
//            jedis.zadd("priceByBook", book.getPrice(), book.getIsbn());
//        }
        
        books.stream().forEach(book -> jedis.zadd("priceByBook", book.getPrice(), book.getIsbn()));
    }

    private List<Book> readBooksFromDB()
    {
        return mockBooks();
    }

    private List<Book> mockBooks()
    {
        return Lists.newArrayList(
            new Book("水浒传", 50.60, "人民文学出版社","shuihu.jpg", "9787020008742", 2500, 200, toTimeLong("2009/09/11")),
            new Book("三国演义", 24.50, "人民文学出版社", "sanguo.jpg", "9787020008728", 3500, 350, toTimeLong("2006/01/01")),
            new Book("红楼梦 ", 37.10, "中华书局出版社", "honglou.jpg", "9787020002207", 2100, 45, toTimeLong("2008/07/01")),
            new Book("演员的自我修养", 34.80, "华中科技大学出版社", "yanyuan.jpg", "9787568006804", 5000, 1500, toTimeLong("2017/09/15"))
        );
    }
}
