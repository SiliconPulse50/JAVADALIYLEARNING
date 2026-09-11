package ProductReviewtest;

//商品类
public class Product {
    String productId; //商品编号
    String name; //名字
    double price; //价格
    String category; //分类
    int stock; //库存

    //构造方法
    public Product(String id,String n,double p,String cate,int s){
        productId = id;
        name = n;
        price = p;
        category = cate;
        stock = s;
    }

    //判断有没有货
    public boolean isAvailable(){
        return stock > 0;
    }

    //打折，rate折扣率，0.8就是8折
    public void applyDiscount(double rate){
        price = price * rate;
    }

    //打印商品信息
    public String toString(){
        return "编号:"+productId+" 名称:"+name+" 价格:"+price+" 类别:"+category+" 库存:"+stock;
    }

    //测试
    public static void main(String[] args) {
        Product p1 = new Product("p001","耳机",199,"数码",10);
        System.out.println(p1);
        System.out.println("是否有货："+p1.isAvailable());
        p1.applyDiscount(0.8); //打8折
        System.out.println("打折后："+p1);
    }
}
