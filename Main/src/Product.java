public abstract  class Product {
    private String Product_Id;
    private  String Product_Name;
    private int Num_Of_Available_Items;
    private double Price;
    private String category;
    public Product(String product_Id,String category, String product_Name, int num_Of_Available_Items, double price) {
        Product_Id = product_Id;
        Product_Name = product_Name;
        Num_Of_Available_Items = num_Of_Available_Items;
        Price = price;
        this.category=category;
    }

    public Product() {
    }

    public String getProduct_Id() {
        return Product_Id;
    }

    public void setProduct_Id(String product_Id) {
        Product_Id = product_Id;
    }

    public String getProduct_Name() {
        return Product_Name;
    }

    public void setProduct_Name(String product_Name) {
        Product_Name = product_Name;
    }

    public int getNum_Of_Available_Items() {
        return Num_Of_Available_Items;
    }

    public void setNum_Of_Available_Items(int num_Of_Available_Items) {
        Num_Of_Available_Items = num_Of_Available_Items;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return  "Product_Id='" + Product_Id+", " +
                "Product Category= "+getCategory()+", "+
                " Product_Name='" + Product_Name + ", " +
                " Num_Of_Available_Items=" + Num_Of_Available_Items +", " +
                " Price=" + Price +", " ;
    }
}
