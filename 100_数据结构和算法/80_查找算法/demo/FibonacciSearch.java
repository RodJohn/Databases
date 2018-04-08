public class FibonacciSearch {


    public static void main(String[] args) {
        int[] arr = {1, 2, 5, 7, 8, 10};
        System.out.println(arr[search(arr, 1)]);
    }

    private static int search(int[] arr, int key) {

        MyArrayTool.show(arr);
        int[] fiboArr = makeFiboArray(arr);
        int[] filledArr = makeFilledArray(arr, fiboArr);

        int lowIndex = 0;
        int hignIndex = arr.length;
        int k = fiboArr.length - 1;
        int midIndex = 0;

        while (lowIndex <= hignIndex) {
            //获取分割位置
            midIndex = lowIndex + fiboArr[k - 1] - 1;
            if (key < filledArr[midIndex]) {
                //排除右边
                hignIndex = midIndex - 1;
                k -= 1;
            } else if (key > filledArr[midIndex]) {
                lowIndex = k + 1;
                k -= 2;
            } else {
                return midIndex;
            }
        }

        return midIndex;
    }

    private static int[] makeFilledArray(int[] arr, int[] fiboArr) {
        if (arr.length == fiboArr[fiboArr.length - 1]) {
            return arr;
        } else {
            int[] filledArr = new int[fiboArr[fiboArr.length - 1]];
            for (int i = 0; i < arr.length; i++) {
                filledArr[i] = arr[i];
            }
            for (int i = arr.length; i < filledArr.length; i++) {
                filledArr[i] = arr[arr.length - 1];
            }
            MyArrayTool.show(filledArr);
            return filledArr;
        }
    }

    private static int[] makeFiboArray(int[] arr) {
        int fblenght = 2;
        int first = 1, sec = 1, third = 2;
        while (third < arr.length) {
            third = first + sec;
            first = sec;
            sec = third;
            fblenght++;
        }

        int[] fb = new int[fblenght];
        fb[0] = 1;
        fb[1] = 1;
        for (int i = 2; i < fblenght; i++) {
            fb[i] = fb[i - 1] + fb[i - 2];
        }
        MyArrayTool.show(fb);
        return fb;
    }


}