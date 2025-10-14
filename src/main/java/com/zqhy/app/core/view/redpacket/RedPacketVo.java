package com.zqhy.app.core.view.redpacket;

import androidx.annotation.NonNull;

/**
 * @author Administrator
 */
public class RedPacketVo {

    private double[] redPacket;
    private int totalCount;

    public RedPacketVo(@NonNull double[] redPacket) {
        this.redPacket = redPacket;
        totalCount = redPacket.length;
    }

    private int redPacketIndex = 0;
    private double lastRedPacket = 0;

    /**
     * 领红包
     *
     * @return
     */
    public boolean openRedPacket() {
        if (redPacketIndex == totalCount) {
            return false;
        }
        lastRedPacket = redPacket[redPacketIndex];
        redPacketIndex++;
        return true;
    }

    public int getRedPacketIndex() {
        return redPacketIndex;
    }

    public double getLastRedPacket() {
        return lastRedPacket;
    }

    public boolean isEmpty() {
        if (redPacketIndex == totalCount) {
            return true;
        }

        return false;
    }

    public double getOpenedRedPacketAmount() {
        double openedRedPacketAmount = 0;
        for (int i = 0; i < redPacketIndex; i++) {
            openedRedPacketAmount = ArityUtil.add(openedRedPacketAmount, redPacket[i]);
        }
        return openedRedPacketAmount;
    }

    public double getUnOpenedRedPacketAmount() {
        double unOpenedRedPacketAmount = 0;
        for (int i = redPacketIndex; i < totalCount; i++) {
            unOpenedRedPacketAmount = ArityUtil.add(unOpenedRedPacketAmount, redPacket[i]);
        }
        return unOpenedRedPacketAmount;
    }


    public String getLastRedPacketLog() {
        StringBuilder sb = new StringBuilder();

        double lastRedPacket = getLastRedPacket();

        sb.append("第")
                .append(getRedPacketIndex())
                .append("位用户获得红包")
                .append(String.valueOf(lastRedPacket))
                .append("元")
                .append("\r\n");

        if (isEmpty()) {
            double bestLucky = getBestLuckyRedPacket();
            sb.append("恭喜第")
                    .append(bestLuckIndex + 1)
                    .append("位用户成为幸运儿,获得最大红包")
                    .append(bestLucky)
                    .append("元!!!!!")
                    .append("\r\n");
        }

        return sb.toString();
    }

    public int bestLuckIndex;

    public double getBestLuckyRedPacket() {
        bestLuckIndex = ArityUtil.getMaxIndex(redPacket);
        return redPacket[bestLuckIndex];
    }
}
