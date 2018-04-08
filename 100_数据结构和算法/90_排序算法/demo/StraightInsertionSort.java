public class StraightInsertionSort {


    public static void main(String[] args) {

//        int[] arrOrigin = {1,2,3,4,5,6};
        int[] arrOrigin = {6,5,4,3,2,1};

        MyArrayTool.show(arrOrigin);

        int changeCount = 0;
        int compareCount = 0;
        int arrLen = arrOrigin.length;
        int arrMaxIndex = arrLen - 1;

        for(int i = 1 ;i <= arrMaxIndex ; i ++){
            for(int j = i; j>0 ; j--){
                compareCount++;
                if (arrOrigin[j-1]>arrOrigin[j]){
                    MyArrayTool.swap(arrOrigin,j-1,j);
                    changeCount++;
                }else{
                    break;
                }
            }
        }

        System.out.println("总交换次数="+changeCount);
        System.out.println("总比较次数="+compareCount);

        MyArrayTool.show(arrOrigin);

    }

}
