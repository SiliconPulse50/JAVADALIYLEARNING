import static java.lang.Math.sqrt;

public class Second_03 {
    public static void main(String[] args) {
        //判断是素数而且是回文数100-9999
        // boolean flag_sushu=true;
        // 错误点每循环一次，flag标签要进行重置
        int account=0;
        for(int num=100;num<=9999;num++){
            //判断是素数
            boolean flag_sushu=true;//修改
            for(int i=2;i<=sqrt(num);i++){
                //121这类特殊11*11=121所以要添加=
                if(num%i==0) {
                    flag_sushu=false;
                    break;
                }

            }
            //如果不是素数就不用做回文判断
            if(!flag_sushu){
                continue;
            }
            //判断是回文数
            int len=0;
          int num2=num;
          int num3=num;//在创建一个副本，因为num2在求位数的时候已经变成0了
           while(num2!=0){
               len++;
               num2=num2/10;
           }
           //创建数组保存每一位数字
            int [] num_copy=new int [len];
           for(int j=len-1;j>=0;j--){
               num_copy[j]=num3 %10;
               num3=num3/10;

           }
            boolean flag_huiwen =true;
            for(int x=0,y=len-1;x<y;x++,y--){
                if(num_copy[x]!=num_copy[y]){
                   flag_huiwen=false;
                   break;
                }
            }



          if(flag_huiwen&&flag_sushu){
              System.out.print(num+"\t");
              account++;
              if(account %5==0){
                  System.out.println();
              }
          }

        }
        System.out.println("\n总共有："+account+"个");

    }
}
/*
* import static java.lang.Math.sqrt;

public class Second_03 {
    public static void main(String[] args) {
        // 找出100~9999之间，既是素数又是回文数，统计个数，每行输出5个
        int account = 0;   // 统计符合条件数字总个数

        // num遍历范围100 ~ 9999
        for (int num = 100; num <= 9999; num++) {

            // ========= 1.试除法判断素数，只循环到sqrt(num) =========
            boolean flag_sushu = true; // 先假设当前num是素数，每轮重新初始化！
            // i从2循环到sqrt(num)，注意 <=
            for (int i = 2; i <= sqrt(num); i++) {
                // 如果取模等于0，说明可以整除，不是素数
                if (num % i == 0) {
                    flag_sushu = false;
                    break; // 已经确定不是素数，可以提前跳出循环
                }
            }

            // 如果不是素数，直接跳过本次，不用做回文判断
            if (!flag_sushu) {
                continue;
            }

            // ========= 2.数组方式判断回文数 =========
            int temp = num;       // 复制num，不破坏原始数字
            int countDigit = 0;   // 记录数字一共有多少位
            int t = temp;
            // 第一步：统计位数
            while (t != 0) {
                countDigit++;
                t = t / 10; // 除以10去掉末尾一位
            }

            // 创建数组保存每一位数字
            int[] num_copy = new int[countDigit];
            for (int j = countDigit - 1; j >= 0; j--) {
                num_copy[j] = temp % 10; // 取出末尾数字存入数组
                temp = temp / 10;
            }

            boolean flag_huiwen = true;
            // 数组首位和末尾对比，判断回文
            for (int x = 0, y = countDigit - 1; x < y; x++, y--) {
                if (num_copy[x] != num_copy[y]) {
                    flag_huiwen = false;
                    break;
                }
            }

            // ========= 3.既是素数又是回文数 =========
            if (flag_huiwen) {
                System.out.print(num + "\t"); //打印数字
                account++;
                // 每输出5个就换行
                if (account % 5 == 0) {
                    System.out.println();
                }
            }
        }
        // 输出总数量
        System.out.println("\n100~9999中既是素数又是回文数总个数：" + account);
    }
}
*/