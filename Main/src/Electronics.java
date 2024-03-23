public class Electronics extends Product{
    private String Brand_Name;
    private int Warranty_Period;
    public Electronics() {
        super();
    }
    public Electronics(String product_Id, String category, String product_Name, int num_Of_Available_Items, double price,String brand_Name, int warranty_Period) {
        super(product_Id,category, product_Name, num_Of_Available_Items, price);
        Brand_Name = brand_Name;
        Warranty_Period = warranty_Period;
    }

    public String getBrand_Name() {
        return Brand_Name;
    }

    public void setBrand_Name(String brand_Name) {
        Brand_Name = brand_Name;
    }

    public int getWarranty_Period() {
        return Warranty_Period;
    }

    public void setWarranty_Period(int warranty_Period) {
        Warranty_Period = warranty_Period;
    }

    @Override
    public String toString() {
        return  "Product Id= "+ getProduct_Id()+", "+
                "Product Category= "+getCategory()+", "+

                "Product Name= "+getProduct_Name()+", "+
                "Number of available products= "+getNum_Of_Available_Items()+", "+
                "Price of the Product= "+getPrice()+", "+

                "Brand Name= " + getBrand_Name()+", " +
                "Warranty Period= " + getWarranty_Period() ;
    }
    public String BrandNameAndWarranty(){
        String BrandNameAndWarranty=(getBrand_Name()+", "+String.valueOf(getWarranty_Period())+" Weeks of warranty");
        return BrandNameAndWarranty;
    }

}
