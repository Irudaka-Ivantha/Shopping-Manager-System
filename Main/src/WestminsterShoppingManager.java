import java.io.*;
import java.util.*;

public class WestminsterShoppingManager implements ShoppingManager {
    Scanner Input = new Scanner(System.in);
    private ArrayList<Product> ProductDetails = new ArrayList<>();
    public WestminsterShoppingManager() {
    }

    //Console Menu
    public void ConsoleMenu() {
        int Exit_Menu = 3;
        while (Exit_Menu == 3) {


            try {
                System.out.println("\nEnter 1 if you are a manager and enter 2 if you are a customer: ");
                int ManagerOrCustomer=Input.nextInt();
                Input.nextLine();
                if (ManagerOrCustomer==1) {
                    System.out.println("""
                                    
                            -------------------------------------------------
                            Please select an option\s
                            1) Add a product\s
                            2) Delete a product\s
                            3) View product list\s
                            4) Save to file\s
                            5) Read from the file\s
                                 0) Quit\s
                            -------------------------------------------------
                            """);

                    System.out.println("Enter option: ");
                    int Option = Input.nextInt();
                    Input.nextLine();
                    switch (Option) {
                        case 0:
                            System.out.println("Program terminated!");
                            Exit_Menu = 0;
                            break;
                        case 1:
                            AddProducts();
                            break;
                        case 2:
                            DeleteProduct();
                            break;
                        case 3:
                            PrintProductList();
                            break;
                        case 4:
                            SaveFile();
                            break;
                        case 5:
                            ReadFile();
                            break;
                    }
                }
                else if(ManagerOrCustomer==2){
                    //press 2 to open the GUI
                    UserInterface.OpenGUI(ProductDetails);
                }


            } catch (Exception e) {
                System.out.println("Enter a valid input!");
                Input.nextLine();

            }
        }
    }

    public ArrayList<Product> getProductDetails() {
        return ProductDetails;
    }

    public void setProductDetails(ArrayList<Product> productDetails) {
        ProductDetails = productDetails;
    }

    //Add products method
    @Override
    public void AddProducts() {
        System.out.println("Enter the category of the product you want to add (Electronics/ Clothing): ");
        String Product_Category = Input.nextLine().toLowerCase().replace(" ", "");

        if (Product_Category.equals("electronics")) {
            System.out.println("Enter product ID: ");
            String product_id = Input.nextLine().toLowerCase();
            System.out.println("Enter product name: ");
            String product_name = Input.nextLine();
            System.out.println("Enter number of available products: ");
            int remaining_products = Input.nextInt();
            System.out.println("Enter product price: ");
            double product_price = Input.nextDouble();
            System.out.println("Enter the brand name: ");
            String brand_name = Input.next();
            System.out.println("Enter the warranty period: ");
            int warranty_period = Input.nextInt();
            if (ProductDetails.size() <= 50) {//checking the number of items in the arraylist
                Electronics electronics = new Electronics(product_id, "Electronics", product_name, remaining_products,
                        product_price,   brand_name, warranty_period);
                ProductDetails.add(electronics); //adding electronic products to the arraylist
            } else if (ProductDetails.size() > 50) {
                System.out.println("You cannot add more products. The quantity has reached the limit!");
            }
        } else if (Product_Category.equals("clothing")) {
            System.out.println("Enter product ID: ");
            String product_id = Input.nextLine().toLowerCase();
            System.out.println("Enter product name: ");
            String product_name = Input.nextLine();
            System.out.println("Enter number of available products: ");
            int remaining_products = Input.nextInt();
            System.out.println("Enter product price: ");
            double product_price = Input.nextDouble();
            System.out.println("Enter the size: ");
            int size = Input.nextInt();
            System.out.println("Enter the colour: ");
            String colour = Input.next();
            if (ProductDetails.size() <= 50) {
                Clothing clothing = new Clothing(product_id, "Clothing", product_name, remaining_products, product_price,
                          size, colour);
                ProductDetails.add(clothing); //adding clothing products to the arraylist
            } else if (ProductDetails.size() > 50) {
                System.out.println("You cannot add more products. The quantity has reached the limit!");
            }
        } else {
            System.out.println("Enter the valid category!");
        }
    }

    @Override
    public void DeleteProduct() {
        System.out.println("Enter the category of the product you want to delete (Electronics/ Clothing): ");
        String deleteProductCategory = Input.nextLine().toLowerCase().replace(" ", ""); //removing spaces and lowering the case

        if (deleteProductCategory.equals("electronics")) {
            System.out.println("Enter the Id of the product you want to delete: ");
            String deleteProductId = Input.nextLine().toLowerCase();
            Iterator<Product> electronicsIterator = ProductDetails.iterator(); //creating an iterator to iterate through the arraylist
            while (electronicsIterator.hasNext()) {
                Product removeProduct = electronicsIterator.next();
                if (removeProduct.getProduct_Id().equals(deleteProductId)) {
                    electronicsIterator.remove();
                    System.out.println("The Electronic product was successfully removed!");
                }
            }
        } else if (deleteProductCategory.equals("clothing")) {
            System.out.println("Enter the Id of the product you want to delete: ");
            String deleteProductId = Input.nextLine().toLowerCase();
            Iterator<Product> clothingIterator = ProductDetails.iterator();
            while (clothingIterator.hasNext()) {
                Product removeProduct = clothingIterator.next();
                if (removeProduct.getProduct_Id().equals(deleteProductId)) {
                    clothingIterator.remove();
                    System.out.println("The Clothing product was successfully removed!");
                }
            }
        } else {
            System.out.println("Enter a valid product category!");
        }
        System.out.println("Number of available products: " + ProductDetails.size());
    }

    //method to print the products to the console
    @Override
    public void PrintProductList() {
        System.out.println("\n--------------Printing the Product Details--------------\n");
        if (ProductDetails.isEmpty()) {
            System.out.println("There are no any products to view!");
        } else {
            Collections.sort(ProductDetails, Comparator.comparing(Product::getProduct_Id)); //sorting the array alphabetically based on the product id
            for (int i = 0; i < ProductDetails.size(); i++) {
                System.out.println(ProductDetails.get(i));
            }
            System.out.println();
        }

    }

    //method to save the data in the arraylist to the file
    @Override
    public void SaveFile() {
        try {
            FileWriter writeToFile = new FileWriter("WestminsterShoppingManager", true);
            BufferedWriter bufferedWriter = new BufferedWriter(writeToFile); //using bufferwriter and filewriter to write to the file.
            for (int i = 0; i < ProductDetails.size(); i++) {
                bufferedWriter.write(String.valueOf((ProductDetails.get(i))));
                bufferedWriter.write("\n");
            }


            bufferedWriter.flush();
            System.out.println("Saved to the file successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //Method to read the data in the file and print them on the console
    public void ReadFile() throws IOException {
        try {
            FileReader readFile = new FileReader("WestminsterShoppingManager");
            BufferedReader bufferedReader = new BufferedReader(readFile);
            String FileData;
            while ((FileData = bufferedReader.readLine()) != null) {
                System.out.println(FileData);
                String[] getProductValues = FileData.split(", ");

                String ProductId = getProductValues[0].substring(getProductValues[0].indexOf("=") + 2);
                    String ProductCategory= getProductValues[1].substring(getProductValues[1].indexOf("=") + 2);
                    if (ProductCategory.equals("Electronics")){ // adding the data in the file back to the arraylist
                        Electronics AddElectronics = getAddElectronics(getProductValues, ProductId, ProductCategory);
                        ProductDetails.add(AddElectronics);
                    }
                    else if (ProductCategory.equals("Clothing")){ // adding the data in the file back to the arraylist
                        Clothing AddClothing = getAddClothing(getProductValues, ProductId, ProductCategory);
                        ProductDetails.add(AddClothing);
                    }
                }
            bufferedReader.close();
            readFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Clothing getAddClothing(String[] getProductValues, String ProductId, String ProductCategory) {
        String ClothingProductName= getProductValues[2].substring(getProductValues[2].indexOf("=") + 2);
        int ClothingNumberOfAvailableProducts= Integer.parseInt(getProductValues[3].substring(getProductValues[3].indexOf("=") + 2));
        double ClothingPriceOfProduct= Double.parseDouble(getProductValues[4].substring(getProductValues[4].indexOf("=") + 2));

        int ClothingProductSize= Integer.parseInt(getProductValues[5].substring(getProductValues[5].indexOf("=") + 2));
        String ClothingProductColour= getProductValues[6].substring(getProductValues[6].indexOf("=") + 2);

        Clothing AddClothing=new Clothing(ProductId, ProductCategory,ClothingProductName,
                ClothingNumberOfAvailableProducts,ClothingPriceOfProduct,
                ClothingProductSize,ClothingProductColour);
        return AddClothing;
    }

    private static Electronics getAddElectronics(String[] getProductValues, String ProductId, String ProductCategory) {
        String ElectronicProductName= getProductValues[2].substring(getProductValues[2].indexOf("=") + 2);
        int ElectronicNumberOfAvailableProducts= Integer.parseInt(getProductValues[3].substring(getProductValues[3].indexOf("=") + 2));
        double ElectronicPriceOfProduct= Double.parseDouble(getProductValues[4].substring(getProductValues[4].indexOf("=") + 2));

        String ElectronicProductBrand= getProductValues[5].substring(getProductValues[5].indexOf("=") + 2);
        int ElectronicWarrantyPeriod= Integer.parseInt(getProductValues[6].substring(getProductValues[6].indexOf("=") + 2));
        Electronics AddElectronics=new Electronics(ProductId, ProductCategory,ElectronicProductName,
                ElectronicNumberOfAvailableProducts,ElectronicPriceOfProduct,
                ElectronicProductBrand,ElectronicWarrantyPeriod);
        return AddElectronics;
    }
}
