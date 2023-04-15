import java.util.Scanner;
import java.math.BigInteger;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileInputStream;

public class Main {

    static String divNum(String num) { //문자열 3자리씩 ','로 분할
        String result = "";
        int size = num.length();
        for (int i = 0; i < size; i++) {
            if (i != 0 && (size - i) % 3 == 0)
                result += ',';
            result += num.charAt(i);
        }
        return result;
    }

    static int minimum(int i, int k) { //작은 값 반환
        if (i < k)
            return i;
        else
            return k;
    }

    static BigInteger binomial_coefficient(int n, int k) { //이항계수 반환
        //파스칼 삼각형의 한줄씩 구하여 cur에 저장
        //cur을 구하기 위한 이전 cur은 buf에 저장
        BigInteger[] buf = { BigInteger.ONE }; //초기값: 0번째 행
        BigInteger[] cur = { BigInteger.ONE }; //에러 방지 초기화
        for (int i = 1; i <= n; i++) {
            int size = minimum(i + 1, k + 1);
            cur = new BigInteger[size];

            for (int a = 0; a < size; a++) {
                if (a == 0 || a == i)
                    cur[a] = BigInteger.ONE;
                else
                    cur[a] = buf[a - 1].add(buf[a]);
            }

            buf = new BigInteger[size]; //buf에 cur 복사
            for (int a = 0; a < size; a++) {
                buf[a] = cur[a];                
            }
        }
        return cur[k];
    }
    
    public static void main(String args[]) {

        BigInteger result; //nCk 계산 결과
        int n = 1, k = 1; //nCk의 n, k
        long start, end, time; //시간 측정
        boolean isSaved = false; //파일 출력이 정상적으로 되었으면 true
        Scanner sc = new Scanner(System.in);
        System.out.println("양의 정수 n k 값 입력시 nCk 값을 구해드립니다. (음수 입력시 종료)");

        while (true) {
            n = sc.nextInt();
            k = sc.nextInt();
            if (n < 0 || k < 0)
                break;
            if (k > n) { //예외 처리
                System.out.println("k가 n보다 큽니다. n k 값을 다시 입력해주십시오.");
                continue;
            }

            start = System.nanoTime();
            result = binomial_coefficient(n, k); //nCk 계산, 시간 측정
            end = System.nanoTime();
            time = end - start;

            try { // 바이트 단위로 파일 출력
                FileOutputStream fos = new FileOutputStream(n + "C" + k + ".txt"); 
                byte[] byteFos = result.toByteArray();
                fos.write(byteFos);
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try { // 바이트 단위로 파일 입력: 저장 내용 확인
                FileInputStream fis = new FileInputStream(n + "C" + k + ".txt"); 
                byte[] byteFis = fis.readAllBytes();
                fis.close();
                BigInteger checkResult = new BigInteger(byteFis);
                if (checkResult.compareTo(result) == 0)
                    isSaved = true; // 다시 읽어온 값이 저장하려던 값과 일치하면 true
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            System.out.println("-----------------------------------");
            //System.out.println(n + "C" + k + " = " + result.toString());
            System.out.println(n + "C" + k + " = " + divNum(result.toString()));
            //System.out.println("time: " + time + "ns");
            System.out.println("time: " + divNum(Long.toString(time)) + "ns");
            System.out.println("파일 출력: " + isSaved);
            System.out.println("-----------------------------------");
        }
        sc.close();
    }
}
