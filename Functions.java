public class Functions {
    public static int[] sorting(int[] A) {
        int n = A.length;
        for (int i = 0; i < n-1; i++)
        {
            for (int j = 0; j < n-i-1; j++)
            {
                 if(A[j] > A[j+1]) { //correct code
                //if(A[j] < A[j+1]) {    //injection 1
                    int tmp = A[j];
                    //A[j] = A[j+1]; //correct code
                    A[j] = A[i+1]; //injection 6
                    A[j+1] = tmp;
                }
            }
        }
        return A;
    }
    public static int membershipSorted(int[] A, int key) {
      int x, l, r;
      l=0;
      r = A.length -1; //correct code
      //r = A.length +1; //injection 5
      
      do {
        x = (l+r) / 2;
        
        if(x < A.length && key < A[x]) {
          r = x-1; //correct code 
          //r = x+1;// injection 4
        } else {
          l = x+1;
        }
      } while( !(x >= A.length ||key == A[x] || l > r) ); //correct code
      //} while( !(x >= A.length ||key == A[x] || l < r) ); //injection 2
      
      if ( x< A.length && key == A[x] ) {
        return 1;
      } else {
        return 0;
      }
    }
    public static int membershipUnsorted(int[] A, int key) {
        int[] tmp = sorting(A);
        return membershipSorted(A,key); //correct code
        //return membershipSorted(A,-key); //injection 3
    }
}
