public class ShellSort {


    public static void main(String[] args) {

        int[] arrOrigin = {6,4,1,3,5,2};
        MyArrayTool.show(arrOrigin);

        int arrLen = arrOrigin.length;
        //gap为组内元素的间隔距离,依次减小
        //也就是组数
        for(int gap = arrLen/2 ; gap > 0 ; gap /=2 ){
            //从第一组开始排序
            for (int i = 0 ; i < gap ;i++){
                //对组内数据插入排序
                for(int j = i + gap ;j < arrLen  ;j+=gap) {
                    for(int m = j; m>0 ; m -= gap){
                        if (arrOrigin[m-gap]>arrOrigin[m]){
                            MyArrayTool.swap(arrOrigin,m-gap,m);
                        }else{
                            break;
                        }
                    }
                }
            }
        }

        MyArrayTool.show(arrOrigin);

    }

}
