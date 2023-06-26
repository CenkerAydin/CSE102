import java.util.HashMap;
import java.util.Map;

public class Ex11_20210808002 {

    //1
    public static int numOfTriplets(int[]arr,int sum){
        int count=0;

        for (int i=0;i<arr.length-2;i++){
            for (int j=i+1;j<arr.length-1;j++){
                for (int k=j+1;k<arr.length;k++){
                    int tripleSum=arr[i]+arr[j]+arr[k];
                    if (tripleSum< sum){
                        count++;
                    }
                }
            }
        }
        return count;
    }
    //2
    public static int partition(int[] arr,int low,int high){
        int pivot=arr[high];
        int i=low-1;
        for (int j=low;j<high;j++){
            if (arr[j]<=pivot){
                i++;
                int temp= arr[i];
                arr[i]=arr[j];
                arr[j]=temp;
            }
        }
        int temp=arr[i+1];
        arr[i+1]=arr[high];
        arr[high]=temp;
        return i+1;
    }
    public static int kthSmallest(int[] arr, int k) {
        int low = 0;
        int high = arr.length - 1;

        while (true) {
            int pIndex = partition(arr, low, high);

            if (pIndex == k - 1) {
                return arr[pIndex];
            } else if (pIndex < k - 1) {
                low = pIndex + 1;
            } else {
                high = pIndex - 1;
            }
        }
    }
    //3
    public static String subSequence(String str){
        int[] dp=new int[str.length()];
        int[] prev=new int[str.length()];
        int maxL=0;
        int maxIndex=0;

        for (int i=0;i<str.length();i++){
            dp[i]=1;
            prev[i]=-1;

            for (int j=0;j<i;j++){
                if (str.charAt(j)< str.charAt(i)&& dp[j]+1 > dp[i]){
                    dp[i]=dp[j]+1;
                    prev[i]=j;
                }
            }
            if (dp[i]>maxL){
                maxL=dp[i];
                maxIndex=i;
            }
        }

        StringBuilder stringBuilder=new StringBuilder();
        int index=maxIndex;
        while (index>=0){
            stringBuilder.insert(0,str.charAt(index));
        }
        int n=str.length();
        int timeC=n*n;
        System.out.println("Time Complexity: "+timeC+")");

        return stringBuilder.toString();
    }
    //4
    public static int isSubString(String str1,String str2){
        int n=str1.length();
        int m=str2.length();

        for (int i=0;i<n-m;i++){
            int j;
            for ( j=0;j<m;j++){
                if (str1.charAt(i+j)!=str2.charAt(j));
                break;
            }
            if (j==m){
                return i;
            }
        }
        return -1;
    }
//5
    public static void findRepeats(int[]arr,int n){
        Map<Integer,Integer> count=new HashMap<>();

        for (int num:arr){
            count.put(num,count.getOrDefault(num,0)+1);
        }

        for (Map.Entry<Integer,Integer> entry: count.entrySet()){
            if (entry.getValue()>n){
                System.out.println(entry.getKey());
            }
        }
    }

}
