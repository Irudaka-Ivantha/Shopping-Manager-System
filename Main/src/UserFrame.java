import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class UserFrame extends JFrame {

    static double discount1;
    static double discount2;
    static double FinalTotal;
    static double Tot_Price = 0;

    private JPanel firstPanel, mainPanel, rightPanel, centerPanel, tablePanel, showProductDetailsPanel;
    private JLabel selectCategory;
    private JButton cartButton, AddToCartButton;
    private JComboBox<String> categoryMenu;
    private JTable productTable;
    private JTextArea ProductDetailsTextArea;
    private ArrayList<Product> productsGUI;
    private ShoppingCart shoppingCart;

    public UserFrame(ArrayList<Product> productDetails) {
        productsGUI = new ArrayList<>(productDetails);
        shoppingCart = new ShoppingCart();

        String[] menuItems = {"All", "Electronics", "Clothing"};
        categoryMenu = new JComboBox<>(menuItems);
        selectCategory = new JLabel("Select Product Category: ");
        cartButton = new JButton("Shopping Cart");
        cartButton.setBackground(Color.yellow);


        firstPanel = new JPanel(new BorderLayout());
        centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        mainPanel = new JPanel(new BorderLayout());
        tablePanel = new JPanel(new BorderLayout());  // Changing the layout manager to BorderLayout

        centerPanel.add(selectCategory);
        centerPanel.add(categoryMenu);
        rightPanel.add(cartButton);
        firstPanel.add(centerPanel, BorderLayout.CENTER);
        firstPanel.add(rightPanel, BorderLayout.NORTH);



        centerPanel.setBackground(Color.cyan);
        rightPanel.setBackground(Color.cyan);

        mainPanel.add(firstPanel, BorderLayout.NORTH);

        // Initializing the table model using the default table model
        DefaultTableModel tableModel = new DefaultTableModel();
        productTable = new JTable(tableModel);
        tableModel.addColumn("Product ID");
        tableModel.addColumn("Name");
        tableModel.addColumn("Category");
        tableModel.addColumn("Price(Rs. )");
        tableModel.addColumn("Number of Available Products");
        tableModel.addColumn("Info");
        productTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component TableProductRenderer = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);


                int ItemIndex = 4;
                int AvailableProducts = Integer.parseInt(table.getValueAt(row, ItemIndex).toString());

                if (AvailableProducts < 3) {
                    TableProductRenderer.setForeground(Color.RED);
                } else {
                    TableProductRenderer.setForeground(table.getForeground());
                }

                return TableProductRenderer;
            }
        });
        // Using a scroll pane to add the ability to scroll
        JScrollPane scrollPane = new JScrollPane(productTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);  // Setting the scroll pane layout to BorderLayout.CENTER

        // Adding the tablePanel to the mainPanel
        mainPanel.add(tablePanel, BorderLayout.CENTER);
        this.add(mainPanel);

        showProductDetails();


        categoryMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TableUpdater();
            }
        });

        cartButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel cart = new DefaultTableModel();
                cart.addColumn("Product");
                cart.addColumn("Quantity");
                cart.addColumn("Price");

                Map<String, Integer> productRowIndexMap = new HashMap<>(); // using hashMap to store product IDs and their related row indices

                Tot_Price = 0;
                for (Product product : shoppingCart.getProducts_List()) {
                    String productId = product.getProduct_Id();
                    int rowIndex = productRowIndexMap.getOrDefault(productId, -1);

                    if (rowIndex != -1) {
                        // if the Product is already in the cart, updating the quantity and price of the existing row
                        int quantity = (int) cart.getValueAt(rowIndex, 1) + 1;
                        double productPrice = product.getPrice();
                        double totalProductPrice = quantity * productPrice;
                        cart.setValueAt(quantity, rowIndex, 1);
                        cart.setValueAt(totalProductPrice, rowIndex, 2);

                    } else {
                        // if the Product is not in the cart adding a new row
                        String productInfo;
                        if (product instanceof Electronics) {
                            productInfo = product.getProduct_Id() + ", " + product.getProduct_Name() + ", " +
                                    ((Electronics) product).getBrand_Name() + ", " + ((Electronics) product).getWarranty_Period();
                        } else if (product instanceof Clothing) {
                            productInfo = product.getProduct_Id() + ", " + product.getProduct_Name() + ", " +
                                    ((Clothing) product).getSize() + ", " + ((Clothing) product).getColour();
                        } else {
                            productInfo = "";
                        }

                        double product_Price = product.getPrice();
                        double Total_Price_Of_Products = product_Price;
                        Object[] rowCart = {productInfo, 1, Total_Price_Of_Products};
                        cart.addRow(rowCart);

                        // Updating the total price
                        Tot_Price = Tot_Price+Total_Price_Of_Products;

                        // Storing the row index of the product in the hashmap
                        int newRowIndex = cart.getRowCount() - 1;
                        productRowIndexMap.put(productId, newRowIndex);
                        double Final_Total = Tot_Price;
                        discount1=CalculateDiscount1(Final_Total);
                        discount2=CalculateDiscount2(Tot_Price,Final_Total);
                        FinalTotal=CalculateFinalTotal(Final_Total,discount1,discount2);


                    }

                }

                // Displaying the total price after the table
                JLabel TotalPrice=new JLabel("Total: Rs. "+Tot_Price+"\n");
                JLabel Discount_on3=new JLabel("First purchase discount (10%) - "+discount2+"\n");
                JLabel Discount_on1st=new JLabel("Three items in same category discount (20%) - "+discount1+"\n");
                JLabel Final_Amount=new JLabel("Final Total: Rs. "+FinalTotal+"\n");
                JTable ShoppingCartTable = new JTable(cart);
                JScrollPane cartScroll = new JScrollPane(ShoppingCartTable);


                JPanel ProductAmountsPanel = new JPanel(new GridLayout(4, 1));
                ProductAmountsPanel.add(TotalPrice);
                ProductAmountsPanel.add(Discount_on1st);
                ProductAmountsPanel.add(Discount_on3);
                ProductAmountsPanel.add(Final_Amount);



                JPanel ShoppingCartDetails = new JPanel(new BorderLayout());
                ShoppingCartDetails.add(cartScroll, BorderLayout.CENTER);
                ShoppingCartDetails.add(ProductAmountsPanel, BorderLayout.SOUTH);


                // Creating a new frame to display the shopping cart details
                JFrame ShoppingFrame = new JFrame("Shopping Cart");
                ShoppingFrame.setLayout(new BorderLayout());
                ShoppingFrame.add(ShoppingCartDetails);
                ShoppingFrame.setSize(800,350);
                ShoppingFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                ShoppingFrame.setVisible(true);

            }
        });


    }
    private void addToShoppingCart()
    {
        int row=productTable.getSelectedRow();

        if(row != -1)
        {
            String id=productTable.getValueAt(row,0).toString();
            Product productSelected=FindProductID(id);

            if (productSelected.getNum_Of_Available_Items()>0)
            {
                productSelected.setNum_Of_Available_Items(productSelected.getNum_Of_Available_Items() -1);
                shoppingCart.AddProduct(productSelected);
                JOptionPane.showMessageDialog(this,"Product is Successfully added to the cart!");

            }
            else
            {
                JOptionPane.showMessageDialog(this,"Product is not added to the cart. Product is out of stock!");

            }
        }
    }
    private Product FindProductID(String productId)
    {
        for (Product product : productsGUI)
        {
            if(productId != null && productId.equals(product.getProduct_Id()))
            {
                return product;
            }
        }
        return null;
    }

    private void TableUpdater() {
        DefaultTableModel tableModel = (DefaultTableModel) productTable.getModel();
        tableModel.setRowCount(0);
        productsGUI.sort(Comparator.comparing(Product::getProduct_Id));

        String selectedCategory = categoryMenu.getSelectedItem().toString();
        for (Product product : productsGUI) {
            if ("All".equals(selectedCategory)) {
                Object[] AllRawDetails = {product.getProduct_Id(), product.getProduct_Name(), product.getCategory(), product.getPrice(),product.getNum_Of_Available_Items(), getProductInfo(product)};
                tableModel.addRow(AllRawDetails);

            } else if ("Electronics".equals(selectedCategory) && product instanceof Electronics) {
                Object[] ElectronicsRawDetails = {product.getProduct_Id(), product.getProduct_Name(), ((Electronics) product).getCategory(), product.getPrice(),product.getNum_Of_Available_Items(),((Electronics) product).BrandNameAndWarranty()};
                tableModel.addRow(ElectronicsRawDetails);

            } else if ("Clothing".equals(selectedCategory) && product instanceof Clothing) {
                Object[] ElectronicsRawDetails = {product.getProduct_Id(), product.getProduct_Name(), ((Clothing) product).getCategory(), product.getPrice(), product.getNum_Of_Available_Items(),((Clothing) product).SizeAndColour()};
                tableModel.addRow(ElectronicsRawDetails);

            }

        }
    }

    private void showProductDetails() {
        ProductDetailsTextArea = new JTextArea(20, 20);
        AddToCartButton = new JButton("Add to Shopping Cart");
        AddToCartButton.setBackground(Color.yellow);

        showProductDetailsPanel = new JPanel();

        showProductDetailsPanel.setLayout(new BoxLayout(showProductDetailsPanel, BoxLayout.Y_AXIS));
        showProductDetailsPanel.add(new JLabel("Selected Product-Details"));
        showProductDetailsPanel.add(new JScrollPane(ProductDetailsTextArea));

        JPanel AddToCartPanel = new JPanel();
        AddToCartPanel.add(AddToCartButton);
        showProductDetailsPanel.add(AddToCartPanel, BorderLayout.WEST);
        mainPanel.add(showProductDetailsPanel, BorderLayout.SOUTH);

        AddToCartButton.setEnabled(false);

        AddToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addToShoppingCart();
            }
        });
        productTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int row = productTable.getSelectedRow();
                AddToCartButton.setEnabled(row != -1);
                if (row != -1) {
                    String pid = productTable.getValueAt(row, 0).toString();
                    Product selectedProduct = FindProductID(pid);
                    if (selectedProduct != null) {
                        String var10000 = selectedProduct.getProduct_Id();
                        String detail = "Product ID: " + var10000 + "\nCategory: " + selectedProduct.getCategory() + "\nItems Available: " + selectedProduct.getNum_Of_Available_Items() + "\nPrice(Rs. ): " + selectedProduct.getPrice();
                        if (selectedProduct instanceof Electronics) {
                            Electronics electronics = (Electronics)selectedProduct;
                            detail = detail + "\nBrand: " + electronics.getBrand_Name() + "\nWarranty Period: " + electronics.getWarranty_Period() + " months";
                        } else if (selectedProduct instanceof Clothing) {
                            Clothing clothing = (Clothing)selectedProduct;
                            detail = detail + "\nSize: " + clothing.getSize() + "\nColor: " + clothing.getColour();
                        }

                        ProductDetailsTextArea.setText(detail);
                    }
                } else {
                    ProductDetailsTextArea.setText("");
                }
            }
        });



    }

    //method to calculate the discount price for having more than three products from the same category
    public double CalculateDiscount1(double FinalTotal){
        double Discount_Price_3products=0;
        if (shoppingCart.getProducts_List().size() >= 3) {
             Discount_Price_3products=FinalTotal*0.20;

        }
        return Discount_Price_3products;
    }

    //method to calculate the discount price at first purchase
    public double CalculateDiscount2(double TotalPrice, double FinalTotal){
        double Discount_Price_1st_purchase=0;
        if (TotalPrice > 0) {
            Discount_Price_1st_purchase=FinalTotal*0.10;
        }
        return Discount_Price_1st_purchase;
    }

    //method to calculate the final total
    public double CalculateFinalTotal(double finalTotal, double discount1, double discount2){
        finalTotal=finalTotal-discount1-discount2;
        return finalTotal;
    }

    //method to get the necessary product details
    private String getProductInfo(Product product) {
        if (product instanceof Electronics) {
            return (((Electronics) product).BrandNameAndWarranty());
        } else if (product instanceof Clothing) {
            return (((Clothing) product).SizeAndColour());
        } else {
            return "";
        }
    }


}

