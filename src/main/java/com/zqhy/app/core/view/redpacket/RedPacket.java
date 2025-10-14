package com.zqhy.app.core.view.redpacket;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 模拟微信红包生成，以分为单位
 *
 * @author Administrator
 * @date 2018/8/17
 */

public class RedPacket {
    private static final boolean isDebug = true;

    /**
     * 生成红包最小值 1分
     */
    private static final int MIN_MONEY = 1;


    /**
     * 生成红包最大值 200元
     */
    private static final int MAX_MONEY = 200 * 100;

    /**
     * 小于最小值
     */
    private static final int LESS = -1;

    /**
     * 大于最大值
     */
    private static final int MORE = -2;

    /**
     * 正常值
     */
    private static final int OK = 1;


    /**
     * 最大的红包是平均值的 TIMES 倍，防止某一次分配红包较大
     */
    private static final double TIMES = 2.1F;

    private int recursiveCount = 0;

    private List<Integer> splitRedPacket(int money, int count) {
        List<Integer> moneys = new LinkedList<>();
        if (!checkMoneyValidity(money, count)) {
            return moneys;
        }
        //计算出最大红包
        int max = (int) ((money / count) * TIMES);
        max = max > MAX_MONEY ? MAX_MONEY : max;
        if (isDebug) {
            System.out.println("max = " + max);
        }

        for (int i = 0; i < count; i++) {
            //随机获取红包
            int redPacket = randomRedPacket(money, MIN_MONEY, max, count - i);
            moneys.add(redPacket);
            //总金额每次减少
            money -= redPacket;
        }
        return moneys;
    }

    private boolean checkMoneyValidity(int money, int count) {
        return money >= MIN_MONEY * count;
    }


    /**
     * @param totalMoney 剩余金额
     * @param minMoney   最小值金额
     * @param maxMoney   最大值金额
     * @param count      剩余红包个数
     * @return
     */
    private int randomRedPacket(int totalMoney, int minMoney, int maxMoney, int count) {
        //只有一个红包直接返回
        if (count == 1) {
            return totalMoney;
        }

        if (minMoney == maxMoney) {
            return minMoney;
        }
        //如果最大金额大于了剩余金额 则用剩余金额 因为这个 money 每分配一次都会减小
        maxMoney = maxMoney > totalMoney ? totalMoney : maxMoney;


        double random = Math.random();

        int redPacket = (int) (random * maxMoney + minMoney);
        int lastMoney = totalMoney - redPacket;
        int status = checkMoney(lastMoney, count - 1);

        if (isDebug) {
            StringBuilder sb = new StringBuilder();

            sb.append("random = ").append(random).append("\r\n");
            sb.append("redPacket = ").append(redPacket).append("\r\n");
            sb.append("lastMoney = ").append(lastMoney).append("\r\n");
            sb.append("status = ").append(status).append("\r\n");

            System.out.println(sb.toString());
        }

        //正常金额
        if (OK == status) {
            return redPacket;
        }

        //如果生成的金额不合法 则递归重新生成
        if (LESS == status) {
            recursiveCount++;
            if (isDebug) {
                System.out.println("LESS  recursiveCount==" + recursiveCount);
            }
            return randomRedPacket(totalMoney, minMoney, redPacket, count);
        }

        if (MORE == status) {
            recursiveCount++;
            if (isDebug) {
                System.out.println("MORE  recursiveCount==" + recursiveCount);
            }
            return randomRedPacket(totalMoney, redPacket, maxMoney, count);
        }
        return redPacket;
    }

    /**
     * 校验剩余的金额的平均值是否存在 最小值和最大值这个范围内
     *
     * @param lastMoney
     * @param count
     * @return
     */
    private static int checkMoney(int lastMoney, int count) {
        double avg = lastMoney / count;
        if (avg < MIN_MONEY) {
            return LESS;
        }

        if (avg > MAX_MONEY) {
            return MORE;
        }
        return OK;
    }


    /***************************************************************************************************************************************************************************************************/

    public static void main(String[] args) {
        /*RedPacket redPacket = new RedPacket();
        int moneyOfCent = 20000, people = 5;
        int moneyOfYuan = moneyOfCent / 100;


        printTestRedPacket(redPacket, moneyOfCent, people);
        printTestRedPacket(redPacket, moneyOfCent, people);
        printTestRedPacket(redPacket, moneyOfCent, people);
        printTestRedPacket(redPacket, moneyOfCent, people);
        printTestRedPacket(redPacket, moneyOfCent, people);

        printTestRedPacket2(moneyOfYuan, people);
        printTestRedPacket2(moneyOfYuan, people);
        printTestRedPacket2(moneyOfYuan, people);
        printTestRedPacket2(moneyOfYuan, people);
        printTestRedPacket2(moneyOfYuan, people);*/

//        wx();


        for (int i = 0; i < 80; i++) {
            String a = "<item\n" +
                    "        android:drawable=\"@drawable/";

            String item = "xhl00";
            if (i < 10) {
                item += "0" + i;
            } else {
                item += i;
            }

            String b = "\"\n" +
                    "        android:duration=\"@integer/duration_main_float\" />";

            String result = a + item + b;

            System.out.println(result);
        }

    }

    private static void wx() {
        long sum = 707829217;
        int 根号sum = 26605;
        List<Long> numberList = findPrime(根号sum);
        for (Long a : numberList) {
            if (sum % a == 0) {
                System.out.println("a = " + a);
                int b = (int) (sum / a);
                System.out.println("b = " + b);
                if (a > b) {
                    System.out.println("此女微信id = NY" + String.valueOf(a) + String.valueOf(b));
                } else {
                    System.out.println("此女微信id = NY" + String.valueOf(b) + String.valueOf(a));
                }
            }
        }
    }


    private static List<Long> findPrime(long param) {
        List<Long> list = new ArrayList<>();
        for (long i = 2; i < param; i++) {
            boolean isPrime = true;
            // 注意两个循环的起始值均为2，都为2时，不进入第二个循环，即默认2为素数
            for (int j = 2; j < i; j++) {
                if (i % j == 0) {
                    isPrime = false;
                    break;
                }
            }
            if (isPrime) {
                list.add(i);
                System.out.println("this is a prime " + i);
            }
        }
        return list;
    }


    /**
     * @param redPacket
     * @param moneyOfCent
     * @param count
     */
    private static void printTestRedPacket(RedPacket redPacket, int moneyOfCent, int count) {
        List<Integer> redPackets = redPacket.splitRedPacket(moneyOfCent, count);
        float bestLucky = 0;
        for (Integer red : redPackets) {
            float redPacketItem = red / 100f;
            if (redPacketItem > bestLucky) {
                bestLucky = redPacketItem;
            }
            System.out.println(redPacketItem);
        }
        System.out.println("bestLucky is " + bestLucky);
        System.out.println("==========================================================================================================================================================================");
    }

    /**
     * @param moneyOfYuan
     * @param count
     * @return
     */
    public static double[] getRedPackets(float moneyOfYuan, int count) {
        RedPacket redPacket = new RedPacket();
        int moneyOfCent = (int) (moneyOfYuan * 100);
        List<Integer> redPackets = redPacket.splitRedPacket(moneyOfCent, count);
        if (redPackets == null) {
            return null;
        }
        double[] doubles = new double[count];
        for (int i = 0; i < redPackets.size(); i++) {
            doubles[i] = redPackets.get(i) / 100d;
        }
        return doubles;
    }

    /**
     * @param moneyOfYuan 单位元
     * @param count
     */
    private static void printTestRedPacket2(int moneyOfYuan, int count) {
        double[] doubles = ArityUtil.getRedPackets(moneyOfYuan, count);
        double bestLucky = 0;
        for (double d : doubles) {
            if (d > bestLucky) {
                bestLucky = d;
            }
            System.out.println(d);
        }
        System.out.println("bestLucky is " + bestLucky);
        System.out.println("==========================================================================================================================================================================");
    }

    /**
     * @param moneyOfYuan
     * @param count
     * @return
     */
    public static double[] getRedPackets2(float moneyOfYuan, int count) {
        return ArityUtil.getRedPackets(moneyOfYuan, count);
    }


}

