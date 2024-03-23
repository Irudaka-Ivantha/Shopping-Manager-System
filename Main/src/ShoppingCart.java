import java.util.ArrayList;

public class ShoppingCart {
    ArrayList<Product>Products_List;
    public ShoppingCart() {
        Products_List = new ArrayList<>();
    }
    public ArrayList<Product> getProducts_List() {
        return Products_List;
    }

    public void setProducts_List(ArrayList<Product> products_List) {
        Products_List = products_List;
    }
    public void AddProduct(Product product_list){
        this.Products_List.add(product_list);
    }
    public void RemoveProduct(Product product_list){
        this.Products_List.remove(product_list);
    }
    public double CalculateTotal(){
        double total_cost = 0;
        for(Product product : Products_List){
            total_cost += product.getPrice();
        }
        return total_cost;
    }


}
