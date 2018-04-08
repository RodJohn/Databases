public class MyArrayTool {

    int array[] ;

    private MyArrayTool(){}


    public static void show(int[] array){
        if(array != null){
            for (int i : array) {
                System.out.print(i+" ");
            }
            System.out.println();
        }

    }

    public static void swap(int[] arr,int a,int b){
        if(arr != null && a!=b){
            arr[a] = arr[a]+arr[b];
            arr[b] = arr[a]-arr[b];
            arr[a] = arr[a]-arr[b];
        }

    }


}
