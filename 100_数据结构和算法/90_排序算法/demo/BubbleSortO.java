public class BubbleSortO {


    public static void main(String[] args) {

        int[] arrOrigin = {1,2,3,4,5,6};

        MyArrayTool.show(arrOrigin);

        int changeCount = 0;
        int compareCount = 0;
        int arrLen = arrOrigin.length;
        int arrMaxIndex = arrLen - 1;
        boolean orderedFlag = true;
        for(int j = 1 ; (j <= arrMaxIndex)&&orderedFlag ; j++) {
            orderedFlag = false;
            for (int i = arrMaxIndex; i >= j; i--) {
                compareCount++;
                if (arrOrigin[i] < arrOrigin[i - 1]) {
                    MyArrayTool.swap(arrOrigin,i,i-1);
                    changeCount++;
                    orderedFlag = true;
                }
            }
        }

        System.out.println("总交换次数="+changeCount);
        System.out.println("总比较次数="+compareCount);

        MyArrayTool.show(arrOrigin);
    }

}
