package algorithms.impl;

/**
 * Created by le on 5/11/17.
 */

public class NxnMatrix {

    public static void main(String[] args) {
        int[][] a = {{0, 1, 2, 3}, {4, 5, 6, 7}, {8, 9, 10, 11}, {12, 13, 14, 15}};
        int[][] b = {{0, 1, 2, 3, 4}, {5, 6, 7, 8, 9}, {10, 11, 12, 13, 14}, {15, 16, 17, 18, 19},
                {20, 21, 22, 23, 24}};
        System.out.println("");
        printMatrix(4, 4, a);
        System.out.println("");
        printMatrix(5, 5, b);
        System.out.println("");
        printMatrix2(4, a);
        System.out.println("");
        printMatrix2(5, b);
    }

    /**
     * 打印n*n矩阵可转化为:打印(n-2)*(n-2)矩阵+最外围的第n圈
     * @param n
     * @param m
     * @param a
     * @return
     */
    public static int[] printMatrix(int n, int m, int[][] a) {
        if (m == 1) {
            System.out.print(a[n / 2][n / 2] + ",");
            return new int[]{n / 2, n / 2 + 1};
        } else if (m == 2) {
            System.out.print(a[n / 2 - 1][n / 2] + ",");
            System.out.print(a[n / 2 - 1][n / 2 - 1] + ",");
            System.out.print(a[n / 2][n / 2 - 1] + ",");
            System.out.print(a[n / 2][n / 2] + ",");
            return new int[]{n / 2, n / 2 + 1};
        } else {
            int[] ij = printMatrix(n, m - 2, a);
            int i = ij[0];
            int j = ij[1];
            int step = m - 1;
            for (int k = 0; k < step * 4; k++) {
                System.out.print(a[i][j] + ",");
                if (k < step - 1) {
                    i--;
                } else if (k < (2 * step - 1)) {
                    j--;
                } else if (k < (3 * step - 1)) {
                    i++;
                } else {
                    j++;
                }
            }
            return new int[]{i, j};
        }
    }

    public static void printMatrix2(int n, int[][] a) {
        int m;
        int i;
        int j;
        if (n % 2 == 0) {
            m = 2;
            i = n / 2 - 1;
            j = n / 2;
        } else {
            m = 1;
            i = n / 2;
            j = n / 2;
        }


        while (m <= n) {
            if (m == 1) {
                System.out.print(a[i][j] + ",");
                j++;
                m += 2;
            } else if (m == 2) {
                System.out.print(a[i][j] + ",");
                System.out.print(a[i][j - 1] + ",");
                System.out.print(a[i + 1][j - 1] + ",");
                System.out.print(a[i + 1][j] + ",");
                i++;
                j++;
                m += 2;
            } else {
                int step = m - 1;
                for (int k = 0; k < step * 4; k++) {
                    System.out.print(a[i][j] + ",");
                    if (k < step - 1) {
                        i--;
                    } else if (k < (2 * step - 1)) {
                        j--;
                    } else if (k < (3 * step - 1)) {
                        i++;
                    } else {
                        j++;
                    }
                }
                m += 2;
            }
        }
    }
}
