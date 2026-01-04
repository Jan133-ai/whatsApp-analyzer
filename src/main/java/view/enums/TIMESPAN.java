package view.enums;

public enum TIMESPAN {
    LAST30DAYS("Last 30 days"), LASTYEAR("Last year"), ALL("Since begin")
    ;
    private String text;

    TIMESPAN(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}