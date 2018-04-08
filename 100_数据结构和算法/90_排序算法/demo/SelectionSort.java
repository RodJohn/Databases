public class SelectionSort {


    public static void main(String[] args) {

        int[] arrOrigin = {5,4,3,2,1,0};

        MyArrayTool.show(arrOrigin);

        int changeCount = 0;
        int compareCount = 0;
        int arrLen = arrOrigin.length;
        int arrMaxIndex = arrLen - 1;

        for(int i =0 ;i < arrMaxIndex ; i++){
            for(int j = i ; j<= arrMaxIndex ;j++){
                compareCount++;
                if (arrOrigin[i] > arrOrigin[j]) {
                    int temp = arrOrigin[i];
                    arrOrigin[i] = arrOrigin[j];
                    arrOrigin[j] = temp;
                    changeCount++;
                }
            }
        }

        System.out.println("总交换次数="+changeCount);
        System.out.println("总比较次数="+compareCount);

        MyArrayTool.show(arrOrigin);

    }

}
