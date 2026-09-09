/*public class Second_04 {
   public static void main(String[] args) {
       int random_number=(int)(Math.random()*(999999-100000))+100000;
        //int random_number=(int)Math.random()*(999999-100000)+100000;
//`(int)Math.random()` 先把 0~1 的小数强转为**0**，后面全部运算结果永远 = 100000。原因：强制转换优先级高于`*`
      /* t forhead=random_number/1000;
           int h1=forhead/100;
           int h2=(forhead-h1*100) /10;
           int h3=forhead%10;
        int forlast=random_number%1000;
           int f1=forlast/100;
           int f2=(forlast-f1*100) /10;
           int f3=forlast%10;
     boolean flag=true;
       if(h1+h2+h3==f1+f2+f3){
           flag=true;
       }else flag=false;
       System.out.println(random_number);
       //要用数组的个方式写
   }
}  */
public class Second_04 {
    public static void main(String[] args) {
        int foundCount = 0;      //记录已经找到满足条件数字的个数，目标3个
        int tryCount = 0;        //记录总共随机生成尝试的次数

        //循环：找到3个符合条件数字才退出循环
        while(foundCount < 3){
            //生成[100000,999999]的6位随机整数，括号不能少！
            int random_number = (int)(Math.random()*(999999 - 100000 + 1)) + 100000;
            tryCount++; //每生成一次，尝试次数+1

            //把6位数字拆分存入int数组
            int[] digits = new int[6];
            int temp = random_number;
            //从后往前，依次取出每一位存入数组
            for(int i = 5; i >= 0; i--){
                digits[i] = temp % 10; //取末尾一位
                temp = temp / 10;     //去掉末尾一位
            }

            //计算前三位 digits[0],digits[1],digits[2] 的和
            int sumFront = digits[0] + digits[1] + digits[2];
            //计算后三位 digits[3],digits[4],digits[5] 的和
            int sumBack = digits[3] + digits[4] + digits[5];

            // 判断前三位之和是否等于后三位之和
            if(sumFront == sumBack){
                System.out.println("符合条件数字："+random_number);
                foundCount++; //找到一个，计数+1
            }
        }
        //输出全部找到之后，打印总尝试次数
        System.out.println("一共尝试生成的次数："+tryCount);

    }
}
