package name.cdd.study.redis.bookshop.pojo;

public class Book
{
    private String name;
    private double price;
    private String press;
    private String picUrl;
    
    
    private String isbn;
    private int inventory;//库存
    private int sells;//销售量
    private long shelfTime;//上架时间
    
    public Book() { }
    
    public Book(String name, double price, String press, String picUrl, String isbn, int inventory, int sells, long shelfTime)
    {
        this.name = name;
        this.price = price;
        this.press = press;
        this.picUrl = picUrl;
        this.isbn = isbn;
        this.inventory = inventory;
        this.sells = sells;
        this.shelfTime = shelfTime;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public double getPrice()
    {
        return price;
    }
    public void setPrice(double price)
    {
        this.price = price;
    }
    public String getPress()
    {
        return press;
    }
    public void setPress(String press)
    {
        this.press = press;
    }
    public String getPicUrl()
    {
        return picUrl;
    }
    public void setPicUrl(String picUrl)
    {
        this.picUrl = picUrl;
    }
    public String getIsbn()
    {
        return isbn;
    }
    public void setIsbn(String isbn)
    {
        this.isbn = isbn;
    }
    public int getInventory()
    {
        return inventory;
    }
    public void setInventory(int inventory)
    {
        this.inventory = inventory;
    }
    public int getSells()
    {
        return sells;
    }
    public void setSells(int sells)
    {
        this.sells = sells;
    }
    public long getShelfTime()
    {
        return shelfTime;
    }
    public void setShelfTime(long shelfTime)
    {
        this.shelfTime = shelfTime;
    }
}
