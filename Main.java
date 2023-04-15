import java.util.Scanner;
import java.math.BigInteger;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileInputStream;

public class Main {

    static String divNum(String num) { //���ڿ� 3�ڸ��� ','�� ����
        String result = "";
        int size = num.length();
        for (int i = 0; i < size; i++) {
            if (i != 0 && (size - i) % 3 == 0)
                result += ',';
            result += num.charAt(i);
        }
        return result;
    }

    static int minimum(int i, int k) { //���� �� ��ȯ
        if (i < k)
            return i;
        else
            return k;
    }

    static BigInteger binomial_coefficient(int n, int k) { //���װ�� ��ȯ
        //�Ľ�Į �ﰢ���� ���پ� ���Ͽ� cur�� ����
        //cur�� ���ϱ� ���� ���� cur�� buf�� ����
        BigInteger[] buf = { BigInteger.ONE }; //�ʱⰪ: 0��° ��
        BigInteger[] cur = { BigInteger.ONE }; //���� ���� �ʱ�ȭ
        for (int i = 1; i <= n; i++) {
            int size = minimum(i + 1, k + 1);
            cur = new BigInteger[size];

            for (int a = 0; a < size; a++) {
                if (a == 0 || a == i)
                    cur[a] = BigInteger.ONE;
                else
                    cur[a] = buf[a - 1].add(buf[a]);
            }

            buf = new BigInteger[size]; //buf�� cur ����
            for (int a = 0; a < size; a++) {
                buf[a] = cur[a];                
            }
        }
        return cur[k];
    }
    
    public static void main(String args[]) {

        BigInteger result; //nCk ��� ���
        int n = 1, k = 1; //nCk�� n, k
        long start, end, time; //�ð� ����
        boolean isSaved = false; //���� ����� ���������� �Ǿ����� true
        Scanner sc = new Scanner(System.in);
        System.out.println("���� ���� n k �� �Է½� nCk ���� ���ص帳�ϴ�. (���� �Է½� ����)");

        while (true) {
            n = sc.nextInt();
            k = sc.nextInt();
            if (n < 0 || k < 0)
                break;
            if (k > n) { //���� ó��
                System.out.println("k�� n���� Ů�ϴ�. n k ���� �ٽ� �Է����ֽʽÿ�.");
                continue;
            }

            start = System.nanoTime();
            result = binomial_coefficient(n, k); //nCk ���, �ð� ����
            end = System.nanoTime();
            time = end - start;

            try { // ����Ʈ ������ ���� ���
                FileOutputStream fos = new FileOutputStream(n + "C" + k + ".txt"); 
                byte[] byteFos = result.toByteArray();
                fos.write(byteFos);
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try { // ����Ʈ ������ ���� �Է�: ���� ���� Ȯ��
                FileInputStream fis = new FileInputStream(n + "C" + k + ".txt"); 
                byte[] byteFis = fis.readAllBytes();
                fis.close();
                BigInteger checkResult = new BigInteger(byteFis);
                if (checkResult.compareTo(result) == 0)
                    isSaved = true; // �ٽ� �о�� ���� �����Ϸ��� ���� ��ġ�ϸ� true
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            System.out.println("-----------------------------------");
            //System.out.println(n + "C" + k + " = " + result.toString());
            System.out.println(n + "C" + k + " = " + divNum(result.toString()));
            //System.out.println("time: " + time + "ns");
            System.out.println("time: " + divNum(Long.toString(time)) + "ns");
            System.out.println("���� ���: " + isSaved);
            System.out.println("-----------------------------------");
        }
        sc.close();
    }
}

// import java.text.DecimalFormat
//
// static String divInt(String inputString) {
// DecimalFormat decFormat = new DecimalFormat("###,###");
// String num = decFormat.format(inputString);
// return num;
// }

// BigInteger value = new BigInteger("1234567");
// byte[] bytes = value.toByteArray();
// value = new BigInteger(1, bytes);
// System.out.println(value.toString());

// BigInteger value = new BigInteger("1234567");
// byte[] bytes = value.toByteArray();
// for( int i = 0; i < bytes.length; i++)
// System.out.println(bytes[i]);

/// !!!!!
// for(

// int i = 0;i<m1;i+=chunkSize)
// {
// for (int j = 0; j < n2; j += chunkSize) {
// for (int k = 0; k < n1; k += chunkSize) {
// // ���� ����
// int[][] buffer = new int[chunkSize][chunkSize];

// // ���� ������ ���� ���� ����
// for (int x = i; x < Math.min(i + chunkSize, m1); x++) {
// for (int y = j; y < Math.min(j + chunkSize, n2); y++) {
// for (int z = k; z < Math.min(k + chunkSize, n1); z++) {
// buffer[x - i][y - j] += a[x][z] * b[z][y];
// }
// }
// }

// // ��� ��Ŀ� ���� ����
// for (int x = i; x < Math.min(i + chunkSize, m1); x++) {
// for (int y = j; y < Math.min(j + chunkSize, n2); y++) {
// c[x][y] += buffer[x - i][y - j];
// }
// }
// }
// }
// }

/// !!!!!

// BigInteger[][] arr = new BigInteger[n + 1][k + 1];
// int bufferSize = 100;for(
// int i = 0;i<=n;i+=bufferSize)
// {
// for (int j = 0; j <= minimum(i, k); j += bufferSize) {
// BigInteger[][] buffer = new BigInteger[bufferSize][bufferSize];

// for (int a = i; a <= minimum(i + bufferSize - 1, n); a++) {
// for (int b = j; j <= minimum(i == j ? a - i : bufferSize, k); b++) {
// if (j == 0 || j == i)
// arr[a-i][b-j] = BigInteger.ONE;
// else
// arr[a-i][b-j] = ///////////arr[i - 1][j - 1].add(arr[i - 1][j]);
// }
// }

// static BigInteger binomial_coefficient(int n, int k) { // ���װ�� ��ȯ
// BigInteger[][] arr = new BigInteger[n + 1][k + 1];
// for (int i = 0; i <= n; i++) {
// for (int j = 0; j <= minimum(i, k); j++) {
// if (j == 0 || j == i)
// arr[i][j] = BigInteger.ONE;
// else
// arr[i][j] = arr[i - 1][j - 1].add(arr[i - 1][j]);
// }
// }
// return arr[n][k];
// }