public class Date {
    private int day;
    private int month;
    private int year;

    public Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public void setDay(int day) {
        if (day < 0 || day > 31){
            throw new IllegalArgumentException();
        }
        this.day = day;
    }

    public void setYear(int year) {
        if (year < 0 || year > 2026){
            throw new IllegalArgumentException();
        }
        this.year = year;
    }

    public void setMonth(int month) {
        if (day < 1 || day > 12){
            throw new IllegalArgumentException();
        }
        this.month = month;
    }
}
