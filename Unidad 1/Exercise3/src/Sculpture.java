public class Sculpture extends Work{
    private final typesMaterials typeMaterial;
    private final typesStyles typeStyle;


    public Sculpture(String title, typesMaterials tMaterial, typesStyles tStyle) {
        super(title);
        this.typeMaterial = tMaterial;
        this.typeStyle = tStyle;
    }

    public typesMaterials getMaterial(){
        return typeMaterial;
    }
    public typesStyles getStyle(){
        return typeStyle;
    }

    public void printSclpture(){
        System.out.println("Sculpture Title: " + getTitle() + "; material: " + getMaterial() + "; style: " + getStyle());
    }
}