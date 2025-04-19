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
