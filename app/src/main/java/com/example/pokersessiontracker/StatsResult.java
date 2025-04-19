// Steven Gaona, CIS165DA 20747, STE2342585 //

package com.example.pokersessiontracker;

public class StatsResult {
    public int totalProfit;
    public int winsOrCashes;
    public int lossesOrROI;

    public StatsResult(int totalProfit, int winsOrCashes, int lossesOrROI) {
        this.totalProfit = totalProfit;
        this.winsOrCashes = winsOrCashes;
        this.lossesOrROI = lossesOrROI;
    }
}
