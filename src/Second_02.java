import java.util.Scanner;
public class Second_02 {
    public static void main(String[] args) {
        //输入字符串
        //元音字母往后移动，保持原有相对顺序
        Scanner sc=new Scanner (System.in);//输入
        System.out.println("请输入一个字符串");
        String str=sc.next();
        //字符串转为char数组
        char [] chars=str.toCharArray();
        //获取字符实际长度
        int len=chars.length;

        //yuanyin数组：存储元音字符在原数组中的下标，最多20个
        int [] yuanyin=new int [20];
        //初始化数组全部赋值-1，-1代表无效位置
        for (int i = 0; i < 20; i++) {
            yuanyin[i]=-1;
        }
        //vCount：记录一共找到多少个元音
        int vCount = 0;

        //遍历原字符数组，收集所有元音的下标
        for(int i=0;i<len;i++){
            //判断是否为元音，大小写都判断
            if (chars[i]=='a'||chars[i]=='e'||chars[i]=='i'||chars[i]=='o'||chars[i]=='u'
                    ||chars[i]=='A'||chars[i]=='E'||chars[i]=='I'||chars[i]=='O'||chars[i]=='U'){
                yuanyin[vCount] = i; //保存元音对应的原数组下标
                vCount++; //元音计数+1
            }
        }

        //创建结果字符数组，和原数组长度一样
        char[] res = new char[len];
        //pos：res数组当前存放的位置指针
        int pos = 0;

        //第一轮：把所有非元音放到res前面，保持原有相对顺序
        for(int i=0;i<len;i++){
            //判断当前字符不是元音
            if (!(chars[i]=='a'||chars[i]=='e'||chars[i]=='i'||chars[i]=='o'||chars[i]=='u'
                    ||chars[i]=='A'||chars[i]=='E'||chars[i]=='I'||chars[i]=='O'||chars[i]=='U')){
                res[pos] = chars[i];
                pos++; //指针后移
            }
        }

        //第二轮：根据yuanyin保存的下标，取出元音放到res数组末尾，保持原来顺序
        for(int k=0;k < vCount;k++){
            int originIndex = yuanyin[k]; //拿到元音在原数组的下标
            res[pos] = chars[originIndex]; //取出元音字符放入结果
            pos++;
        }

        String resultStr = new String(res);
        System.out.println("处理后的字符串："+resultStr);

        sc.close();
    }
}
