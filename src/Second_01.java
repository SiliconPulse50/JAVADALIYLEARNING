public class Second_01 {
//模拟范围内的随机数据Math.random()*(max-min)+min
//剔除数据,不用在数组里面删除，只是需要标记，后续加入判断
// 计算
public static void main(String[] args) {
    final int total_count=36000;
    final double minn=15.0;
    final double maxn=35.0;
    final int remove=36000 * 5/100;

    double[] arr_random=new double[total_count];

    boolean[] isRemoved = new boolean[36000];

    for(int i=0;i<total_count;i++){
        arr_random[i]=Math.random()*(maxn-minn)+minn;
    }
////找出最小的1800个数
//    double givenmin=100.0;
//    for(int i=0;i<total_count;i++){
//        if(arr_random[i]<givenmin){
//            givenmin=arr_random[i];
//            isRemoved[i]=true;//true为被移除的
//        }
//    }
//    //找出最大的1800个数
//    double givenmax=1.0;
//    for(int i=0;i<total_count;i++){
//        if(arr_random[i]>givenmax){
//            givenmax=arr_random[i];
//            isRemoved[i]=true;//true为被移除的
//
//        }
//    }
    //  剔除最小的 remove(1800) 个
    for(int k=0;k<remove;k++){ // 循环1800次，每次剔除1个当前最小
        int minIdx = -1;
        double givenmin = Double.POSITIVE_INFINITY;//设置一个无限大的由于比较
        for(int i=0;i<total_count;i++){
            if(!isRemoved[i] && arr_random[i] < givenmin){
                givenmin = arr_random[i];
                minIdx = i;
            }
        }
        if(minIdx != -1){
            isRemoved[minIdx] = true;
        }
    }

//剔除最大的remove(1800)个
    for (int k = 0; k < remove; k++) {
        int maxIdx=-1;
        double givenmax=Double.NEGATIVE_INFINITY;
        for(int i=0;i<total_count;i++){
            if(!isRemoved[i]&&arr_random[i]>givenmax){
                givenmax=arr_random[i];
                maxIdx=i;
            }

        }
        if(maxIdx != -1){
            isRemoved[maxIdx]=true;
        }
    }

    double sum=0.0;
    for(int i=0;i<total_count;i++){
        if(!isRemoved[i]){
            sum+=arr_random[i];
        }
    }
    double average=sum/(total_count-remove*2);
    System.out.println("平均值为："+average);
double sqaure=0.0;
for(int i=0;i<total_count;i++){
    if(!isRemoved[i]){
        sqaure+=(arr_random[i]-average)*(arr_random[i]-average);
    }
}
double variance=sqaure/(total_count-remove*2);
System.out.println("方差为："+variance);


}


}
//之前的思路错误：
//遍历全部数组：
//只要遇到比 givenmin 更小的，更新 givenmin，并且**立刻把当前 i 标记为剔除**。
//只会不断更新到全局最小那一个，只有那一个 i 被置 true。
//只会删掉 1 个，不是 1800 个。