package com.jdkhome.blzo.ex.utils.coder;

import java.util.Base64;
import java.util.Random;
import java.util.Stack;

/**
 * 数字加密编码/解码器
 * linkji独创。。。
 */
public class NumberEncoder {

    /**
     * 需求 ：
     * 数字 转 ASX1DD3 这样的字符串，并且要能够反转
     * 1. 数字转成 6进制
     * 2. (0,1,2,3,4,5) => (F,E,D,C,B,A) 进行凯撒转换
     * 3. 凯撒后的字符串，当成16进制数字处理
     * 嗯试试效果先
     */

    private static char[] array = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
            .toCharArray();
    private static String numStr = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    // 凯撒加密
    private static char[] caesar = "FEDCBA".toCharArray();
    // 凯撒解密
    private static char[] uncaesar = "543210".toCharArray();

    // 打乱顺序的array 作为base64之后的凯撒字典
    private static char[] caesar64 = "cviQ14YHTmtLMq6hJVkrG0WpxyRwjI8uo73dnNKDPOzbfe9lgXC2ZFs5SEaUAB"
            .toCharArray();
    private static String caesar64Str = "cviQ14YHTmtLMq6hJVkrG0WpxyRwjI8uo73dnNKDPOzbfe9lgXC2ZFs5SEaUAB";

    private static String _10_to_N(long number, int N) {
        Long rest = number;
        Stack<Character> stack = new Stack<Character>();
        StringBuilder result = new StringBuilder(0);
        while (rest != 0) {
            stack.add(array[new Long((rest % N)).intValue()]);
            rest = rest / N;
        }
        for (; !stack.isEmpty(); ) {
            result.append(stack.pop());
        }
        return result.length() == 0 ? "0" : result.toString();

    }

    // 其他进制转为10进制，按权展开
    private static long N_to_10(String number, int N) {
        char ch[] = number.toCharArray();
        int len = ch.length;
        long result = 0;
        if (N == 10) {
            return Long.parseLong(number);
        }
        long base = 1;
        for (int i = len - 1; i >= 0; i--) {
            int index = numStr.indexOf(ch[i]);
            result += index * base;
            base *= N;
        }

        return result;
    }

    // 6进制凯撒
    private static String caesar6Encode(String str) {
        StringBuffer result = new StringBuffer();

        char ch[] = str.toCharArray();
        for (char c : ch) {
            result.append(caesar[c - '0']);
        }

        return result.toString();
    }

    private static String caesar6Decode(String str) {
        StringBuffer result = new StringBuffer();

        char ch[] = str.toCharArray();
        for (char c : ch) {
            result.append(uncaesar[c - 'A']);
        }

        return result.toString();
    }


    //=========base64 => 凯撒 ======== 编码解码
    private static String caesarBase64Encode(String str) {
        StringBuffer result = new StringBuffer();

        char ch[] = str.toCharArray();
        for (char c : ch) {
            result.append(caesar64[numStr.indexOf(c)]);
        }

        return result.toString();
    }

    private static String caesarBase64Decode(String str) {
        StringBuffer result = new StringBuffer();

        char ch[] = str.toCharArray();
        for (char c : ch) {
            result.append(array[caesar64Str.indexOf(c)]);
        }

        return result.toString();
    }


    //========随机补位算法======HG
    private static String coverEncode(String str) {
        StringBuilder sb = new StringBuilder(str);
        //随机补位
        switch (sb.length() % 3) {
            case 1:
                sb.insert(Math.abs(new Random().nextInt()) % (sb.length() + 1), "H");
            case 2:
                sb.insert(Math.abs(new Random().nextInt()) % (sb.length() + 1), "G");
            default:
                break;
        }
        return sb.toString();
    }

    private static String coverDecode(String str) {

        return str.replaceAll("G", "")
                .replaceAll("H", "");
    }


    //=======最终转换编码器========
    public static String encode(Integer num) {

        String caesarBase64 = caesarBase64Encode(
                Base64.getEncoder().encodeToString(
                        coverEncode(
                                caesar6Encode(
                                        _10_to_N(num, 6)
                                )
                        ).getBytes()
                )
        );

        return caesarBase64;
    }

    public static Integer decode(String str) {

        Integer num_10 = (int) N_to_10(
                caesar6Decode(
                        coverDecode(
                                new String(Base64.getDecoder().decode(
                                        caesarBase64Decode(str)
                                ))
                        )
                ), 6);
        return num_10;
    }

    public static void main(String[] args) {

        for (int i = 0; i < 300; i++) {
            String encode = encode(i);
            System.out.println(encode);
            Integer decode = decode(encode);
            System.out.println(decode);
            System.out.printf("===\n");
        }
    }

}
