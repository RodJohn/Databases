public class BubbleSort {


    public static void main(String[] args) {

        int[] arrOrigin = {0,1,2,3,5,4};

        MyArrayTool.show(arrOrigin);

        int changeCount = 0;
        int compareCount = 0;
        int arrLen = arrOrigin.length;
        int arrMaxIndex = arrLen - 1;
        for(int j = 1 ; (j <= arrMaxIndex); j++) {
            for (int i = arrMaxIndex; i >= j; i--) {
                compareCount++;
                if (arrOrigin[i] < arrOrigin[i - 1]) {
                    int temp = arrOrigin[i];
                    arrOrigin[i] = arrOrigin[i - 1];
                    arrOrigin[i - 1] = temp;
                    changeCount++;
                }
            }
        }

        System.out.println("总交换次数="+changeCount);
        System.out.println("总比较次数="+compareCount);

        MyArrayTool.show(arrOrigin);

    }

}
