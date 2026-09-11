package Circletest;

// 圆形类
public class Circle {
    double r; //半径

    //构造，给半径赋值
    public Circle(double rr){
        r = rr;
    }

    //算面积
    public double getArea(){
        return Math.PI * r * r;
    }

    //算周长
    public double getPerimeter(){
        return 2 * Math.PI * r;
    }

    //输出圆信息
    public String toString(){
        return "半径="+r+"，面积="+getArea()+"，周长="+getPerimeter();
    }

    //测试
    public static void main(String[] args) {
        Circle c1 = new Circle(3);
        System.out.println(c1);
    }
}
