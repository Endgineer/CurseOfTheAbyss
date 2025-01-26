package net.endgineer.curseoftheabyss.client;

public class StrainsData {
    private static double progress_numbness;
    private static double progress_deprivation;

    public static void update(double progress_numbness, double progress_deprivation) {
        StrainsData.progress_numbness = progress_numbness;
        StrainsData.progress_deprivation = progress_deprivation;
    }

    public static double getDeprivationProgress() {
        return StrainsData.progress_deprivation;
    }

    public static double getNumbnessProgress() {
        return StrainsData.progress_numbness;
    }
}
