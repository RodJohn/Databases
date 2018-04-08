public class BinarySearch {


    public static void main(String[] args) {
        int[] arr = {1, 2, 5, 7, 8, 10};
        MyArrayTool.show(arr);
        System.out.println(arr[search(arr, 1)]);
    }

    private static int search(int[] arr, int key) {

        int lowIndex = 0;
        int highIndex = arr.length - 1;

        while ( lowIndex < highIndex) {
            int midIndex = (highIndex + lowIndex) / 2;
            if (arr[midIndex] > key) {
                highIndex = midIndex - 1;
            } else if (arr[midIndex] < key) {
                lowIndex = midIndex + 1;
            } else {
                return midIndex;
            }
        }

        return -1;
    }


}