public class Painting extends Work{

    private final paintsType paintType;
    private String format;

    public Painting(String title, paintsType paintType, String format){
        super(title);
        this.paintType = paintType;
        this.format = format;
    }

    public paintsType getPaint() {
        return paintType;
    }
    public String getFormat(){
        return format;
    }
    public void setFormat(String valueFormat){
        format = valueFormat;
    }

    public void printDaPainting(){
        System.out.println("Painting Title: " + getTitle() + "; format: " + getFormat() + "; painting type: " + getPaint());
    }
}