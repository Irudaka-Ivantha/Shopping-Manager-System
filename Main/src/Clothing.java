public class Clothing extends Product{
    private int Size;
    private String Colour;

    public Clothing(String product_Id,String category, String product_Name, int num_Of_Available_Items, double price, int size, String colour) {
        super(product_Id, category,product_Name, num_Of_Available_Items, price);
        Size = size;
        Colour = colour;
    }

    public int getSize() {
        return Size;
    }

    public void setSize(int size) {
        Size = size;
    }

    public String getColour() {
        return Colour;
    }

    public void setColour(String colour) {
        Colour = colour;
    }


    @Override
    public String toString() {
        return  "Product Id= "+ getProduct_Id()+", "+
                "Product Category= "+getCategory()+", "+
                "Product Name= "+getProduct_Name()+", "+
                "Number of available products= "+getNum_Of_Available_Items()+", "+
                "Price of the Product= "+getPrice()+", "+
                "Size= " + getSize()+", "+
                "Colour= " + getColour();
    }
    public String SizeAndColour(){
        String SizeAndColour=(getSize()+", "+getColour());
        return SizeAndColour;
    }
}
