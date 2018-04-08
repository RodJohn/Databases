public class HeapSort {


    public static void main(String[] args) {

        int[] arrOrigin = {0,5,1,3,4,2};
        MyArrayTool.show(arrOrigin);


        int length = arrOrigin.length;
        //获取下标最大的非叶子结点
        int maxLeaf = length /2- 1;
        //从最后一个非叶子结点开始
        for(int i = maxLeaf; i>=0 ; i--){
            sort(arrOrigin,i,length);
        }
        MyArrayTool.show(arrOrigin);


        for(int i = arrOrigin.length-1 ; i>=1 ;i--){
            MyArrayTool.swap(arrOrigin,i,0);
            //仅剩堆顶是无序的
            sort(arrOrigin,0,i);
        }

        MyArrayTool.show(arrOrigin);

    }


    private static void sort(int[] arr,int t,int len){
        int temp = arr[t];
        for(int i = t*2+1 ; i < len ; i = i*2 +1){
            //获取较大的分支
            if(i+1 < len && arr[i] < arr[i+1])
                i++;
            //较大分支和父节点比较
            if(arr[i] <= temp){
                break;
            }
            //如果父节点较小,子节点值赋予父节点
            arr[t] = arr[i];
            //记录父节点位置
            t = i;
        }
        //填入最后的节点
        arr[t] = temp;
    }



}
