package ProductReviewtest;
//商品评价类
public class ProductReview {
    String productName; //商品名
    int score; //评分1~5
    String comment; //评价内容
    String reviewDate; //评价日期

    //构造
    public ProductReview(String pname,int s,String com,String date){
        productName = pname;
        score = s;
        comment = com;
        reviewDate = date;
    }

    //getter setter
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public String getReviewDate() {
        return reviewDate;
    }
    public void setReviewDate(String reviewDate) {
        this.reviewDate = reviewDate;
    }

    //判断是否好评，>=4星为true
    public boolean isPositive(){
        return score >=4;
    }

    //输出评价信息
    public String toString(){
        return "商品："+productName+" 评分："+score+" 评价："+comment+" 日期："+reviewDate;
    }

    //测试
    public static void main(String[] args) {
        ProductReview rev1 = new ProductReview("耳机",4,"音质不错","2026-09-10");
        System.out.println(rev1);
        System.out.println("是否好评："+rev1.isPositive());
    }
}
