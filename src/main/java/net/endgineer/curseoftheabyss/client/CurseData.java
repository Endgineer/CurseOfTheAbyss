package net.endgineer.curseoftheabyss.client;

public class CurseData {
    private static double field;

    public static void update(double field) {
        CurseData.field = field;
    }

    public static double getField() {
        return CurseData.field;
    }
}
