public class QuickSort {


    public static void main(String[] args) {

        int[] arrOrigin = {1,0,5,4,2,3};
        MyArrayTool.show(arrOrigin);


        sort(arrOrigin,0,arrOrigin.length-1);
        MyArrayTool.show(arrOrigin);

    }


    private static void sort(int[] arr,int left,int right){
        if(left < right){
            int mid = quick(arr,left,right);
            sort(arr,left,mid-1);
            sort(arr,mid+1,right);
        }
    }


    private static int quick(int[] arr,int left,int right){
        int midIndex = 3;
        int midValue = arr[midIndex];
        MyArrayTool.swap(arr,midIndex,left);
        while(left < right){
            while(arr[right] >= midValue && right>left)
                right--;
            MyArrayTool.swap(arr,left,right);
            while (midValue >= arr[left] && right>left)
                left++;
            MyArrayTool.swap(arr,left,right);

        }
        return left;
    }

}
