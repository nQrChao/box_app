package com.zqhy.app.core.view.redpacket;

import java.math.BigDecimal;

/**
 * @author Administrator
 * @date 2018/8/17
 */

public class ArityUtil {
    private static final boolean isDebug = true;

    private static final int DEF_DIV_SCALE = 10;

    private ArityUtil() {
    }

    /**
     * 加法
     *
     * @param d1
     * @param d2
     * @return
     */
    public static double add(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return b1.add(b2).doubleValue();

    }

    /**
     * 减法
     *
     * @param d1
     * @param d2
     * @return
     */
    public static double sub(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return b1.subtract(b2).doubleValue();

    }

    /**
     * 乘法
     *
     * @param d1
     * @param d2
     * @return
     */
    public static double mul(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return b1.multiply(b2).doubleValue();

    }

    /**
     * 除法
     *
     * @param d1
     * @param d2
     * @return
     */
    public static double div(double d1, double d2) {
        return div(d1, d2, DEF_DIV_SCALE);
    }

    /**
     * 除法
     *
     * @param d1
     * @param d2
     * @param scale
     * @return
     */
    public static double div(double d1, double d2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();

    }

    /**
     * 获得数组中最大值的索引
     *
     * @param arr
     * @return
     */
    public static int getMaxIndex(double[] arr) {
        double max = arr[0];
        int index = 0;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
                index = i;
            }
        }
        return index;
    }

    /**
     * 获得数组中最小值的索引
     *
     * @param arr
     * @return
     */
    public static int getMinIndex(double[] arr) {
        double min = arr[0];
        int index = 0;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < min) {
                min = arr[i];
                index = i;
            }
        }
        return index;
    }


    /**
     * @param M 金额
     * @param N 数量
     * @return
     */
    public static double[] getRedPackets(float M, int N) {
        double[] randoms = new double[N];
        double sum = 0;
        //获得一个长度为N的数组，里面的随机数是0——>1
        for (int i = 0; i < N; i++) {
            double node = Math.random();
            //取得两位有效数字，
            node = (double) Math.round(node * 100) / 100;

            //对数组进行求和，不直接相加，防止精度误差。
            sum = ArityUtil.add(sum, node);
            randoms[i] = node;
            if (isDebug) {
                System.out.println("randoms[" + i + "] = " + randoms[i]);
            }
        }
        if (isDebug) {
            System.out.println("sum = " + sum);
            System.out.println("----------------------------------");
        }

        //计算真正需要的数组，原理：array*M/sum(array), 最后array 的和就是M ，不过对于double需要调整。
        double sum2 = 0;
        for (int i = 0; i < N; i++) {
            randoms[i] = ArityUtil.div(ArityUtil.mul(randoms[i], M), sum);
            randoms[i] = (double) Math.round(randoms[i] * 100) / 100;
            if (isDebug) {
                System.out.println("randoms[" + i + "] = " + randoms[i]);
            }
            sum2 = ArityUtil.add(sum2, randoms[i]);
        }
        //修正，防止double计算出现的误差
        double modify = ArityUtil.sub(M, sum2);
        if (modify > 0) {
            //如果误差为正则把它加在最小值上，sum2的结果为99.8，就可以把0.2加到数组中最小的那个值上。
            randoms[ArityUtil.getMinIndex(randoms)] = ArityUtil.add(randoms[ArityUtil.getMinIndex(randoms)], modify);
        } else {
            //如果误差为负值则把误差加到最大值上，比如sum2的结果为100.02，就可以把0.02加到数组中最大的那个值上去。
            randoms[ArityUtil.getMaxIndex(randoms)] = ArityUtil.add(randoms[ArityUtil.getMaxIndex(randoms)], modify);
        }
        if (isDebug) {
            System.out.println("sum2 = " + sum2);
            System.out.println("----------------------------------");
        }
        //输出调整后的数组，就是十个红包里，每个红包获得的钱数，求和验证，结果是M
        double sum3 = 0;
        for (int i = 0; i < N; i++) {
            //这里的输出结果就是每个人抢到的红包，这里的随机数是同时产生的，
            if (isDebug) {
                System.out.println("randoms[" + i + "] = " + randoms[i]);
            }
            sum3 = ArityUtil.add(sum3, randoms[i]);
        }
        if (isDebug) {
            System.out.println("sum3 = " + sum3);
        }
        return randoms;
    }
}
