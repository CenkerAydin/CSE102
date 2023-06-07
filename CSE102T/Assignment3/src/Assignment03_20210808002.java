import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//@author CENKER AYDIN
//@since 25.05.2023
public class Assignment03_20210808002 {
}

class Product{
    private Long Id;
    private String Name;
    private double Price;

    Product(Long id,String name,double price){
        if (price<0){
            throw new InvalidPriceException();
        }
        this.Id=id;
        this.Name=name;
        this.Price=price;
    }
    public Long getId(){ return Id;}
    public void setId(Long id){this.Id=id;}
    public String getName() {
        return Name;
    }
    public void setName(String name) {
        this.Name = name;
    }
    public double getPrice() {
        return Price;
    }
    public void setPrice(double price) {
        if (price<0){
            throw new InvalidPriceException();
        }else
            this.Price = price;
    }

    @Override
    public String toString(){
        return "{"+getId()+"}"+"-{"+getName()+"}@{"+getPrice()+"}";
    }

    public boolean equals(Object object) {
        if (object instanceof Product) {
            Product o1 = (Product) object;
            return Math.abs(this.Price - o1.Price) < 0.001;
        }
        return false;
    }
}

class FoodProduct extends Product{
    private int Calories;
    private boolean Dairy;
    private boolean Eggs;
    private boolean Peanuts;
    private boolean Gluten;

    FoodProduct(Long id,String name,int quantity,double price,int calories,boolean dairy,boolean peanuts,boolean eggs,boolean gluten){
        super(id, name, price);
        if (calories<0){
            throw new InvalidAmountException(calories);
        }
        this.Calories =calories;
        this.Dairy =dairy;
        this.Eggs =eggs;
        this.Peanuts =peanuts;
        this.Gluten =gluten;
    }
    public int getCalories(){ return Calories;}
    public void setCalories(int calories){
        if (calories<0){
            throw new InvalidAmountException(calories);
        }else
            this.Calories =calories; }

    public boolean containsDairy(){
        return Dairy;
    }
    public boolean containsEggs(){
        return Eggs;
    }
    public boolean containsPeanuts(){
        return Peanuts;
    }
    public boolean containsGluten(){
        return Gluten;
    }


}
class CleaningProduct extends Product{
    private boolean Liquid;

    private String WhereToUse;
    CleaningProduct(Long id, String name, int quantity, double price, boolean liquid, String whereToUse){
        super(id, name, price);
        this.Liquid =liquid;
        this.WhereToUse =whereToUse;
    }
    public boolean isLiquid() {
        return Liquid;
    }
    public String getWhereToUse() {
        return WhereToUse;
    }
    public void setWhereToUse(String whereToUse) {
        this.WhereToUse = whereToUse;
    }
}
class Customer{
    private int points;
    private String Name;
    //protected ArrayList<ProductCount>carts;
    private Map<Store, ArrayList<Product>> carts;
    private double totalDue;
    Customer(String name){
        this.Name =name;
        this.carts=new HashMap<>();
        this.totalDue=0.0;
    }
    public String getName(){return Name;}
    public void setName(String name){ this.Name =name;}
    public void addToCart(Store store,Product product,int count){
      /*  try {
            double subtotal=product.purchase(count);
            carts.add(new ProductCount(product,count));
            totalDue +=subtotal;
        }catch (InvalidAmountException e){
            System.out.println("ERROR: "+e.toString());
        }

       */
        if (!carts.containsKey(store)){
            throw new StoreNotFoundException(store.getName());
        }
        boolean productAvailable =false;
        int availableQuantity=store.remaining(product);

        if (availableQuantity >=count) {
            ArrayList<Product> products = carts.get(store);
            for (int i=0;i<count ;i++){
                products.add(product);
            }
        }else {
            throw new InsufficientFundsException();
        }


    }

    public String receipt(Store store) {
        if (!carts.containsKey(store)){
            throw  new StoreNotFoundException(store.getName());
        }

        StringBuilder recbuild =new StringBuilder();
        recbuild.append(store.getName()).append("\n");
        ArrayList<Product >products =carts.get(store);
        double total =0.0;

        for (Product product:products){
            recbuild.append(products.toString()).append("\n");
            total +=product.getPrice();
        }
        recbuild.append("Total Due - ").append(total);
        return recbuild.toString();
    }

    public double getTotalDue(Store store){
        if (!carts.containsKey(store)){
            throw new StoreNotFoundException(store.getName());
        }
        ArrayList<Product> products =carts.get(store);
        double total =0.0;
        for (Product product:products){
            total +=product.getPrice();
        }
        return total;
    }


    public double pay(Store store ,double amount,boolean usePoints) {
        if (!carts.containsKey(store)) {
            throw new StoreNotFoundException(store.getName());
        }
        double totalDue = getTotalDue(store);
        if (usePoints) {
            int customerPoints = store.getCustomerPoints(this);
            if (!carts.containsKey(store)){
                setPoints(0);
            }
            if (customerPoints > 0) {
                double pointsDisc = customerPoints *(customerPoints/100.0);
                if (amount >= (totalDue - pointsDisc)) {
                    double remainingAmount = amount - (totalDue - pointsDisc);
                    return remainingAmount;
                } else {
                    throw new InsufficientFundsException();
                }
            }
        }

        if (amount >= totalDue) {
            System.out.println("Thank you ");
            double change = amount - totalDue;
            return change;
        } else {
            throw new InsufficientFundsException();
        }
    }

    @Override
    public String toString(){
        return ""+getName();
    }

    public void setPoints(int points) {
        this.points=points;
    }
    public int getPoints() {
        return points;
    }




}
class ProductCount {
    private Product product;
    private int count;

    public ProductCount(Product product, int count) {
        this.product = product;
        this.count = count;
    }
    public Product getProduct() {
        return product;
    }
    public int getCount() {
        return count;
    }
}

class Store{
    private String Name;
    private String Website;
    private int Quantity;
    private Map<Product,Integer> inventory;
    private ArrayList<Customer> customers;
    Store(String name,String website){
        this.Name =name;
        this.Website=website;
        this.inventory=new HashMap<>();
        this.customers=new ArrayList<>();

    }
    public int getCount(){
        return inventory.size();
    }

    public void addCustomer(Customer customer){
        customers.add(customer);
        customer.setPoints(0);
    }
    public int getProductCount(Product product){
        if (!inventory.containsKey(product)){
            throw  new ProductNotFoundException(product.getName());
        }
        return inventory.getOrDefault(product,0);
    }
    public int getCustomerPoints(Customer customer) {
        for (Customer customer1 :customers){
            if (customer1.equals(customer)){
                return customer1.getPoints();
            }
        }
        throw new CustomerNotFoundException();
    }
    public void removeProduct(Product product){
        if (inventory.containsKey(product)){
            inventory.remove(product);
        }else {
            throw  new ProductNotFoundException("Product not found: " + product);
        }
    }
    public int remaining(Product product){
        if(!inventory.containsKey(product)){
            throw  new ProductNotFoundException("Product not found: " + product.getId()+ " - " + product.getName());
        }
        return inventory.get(product);
    }
    public void  addToInventory(Product product,int amount){
        if (amount<0){
            throw new InvalidAmountException(amount);
        }
        if (!inventory.containsKey(product)){
            inventory.put(product,amount);
        }else {
            inventory.put(product, inventory.getOrDefault(product,0)+amount);
        }
    }
    public double purchase(Product product,int amount){
        if (amount<0 || amount> getProductCount(product)){
            throw new InvalidAmountException(amount);
        }
        if (!inventory.containsKey(product)){
            throw new ProductNotFoundException("Product not found: " + product.getId() + " - " + product.getName());
        }
        double total=product.getPrice()*amount;
        int remaining=getProductCount(product)-amount;
        inventory.put(product,remaining);

        return total;

    }
    public String getName() {
        return Name;
    }
    public void setName(String name) {
        this.Name = name;
    }
    public int getQuantity(){
        return  Quantity;
    }
    public void setQuantity(int quantity){
        this.Quantity=quantity;
    }
    public String getWebsite() {
        return Website;
    }
    public void setWebsite(String website) {
        this.Website = website;
    }
    public int getInventorySize(){
        return customers.size();
    }

}
class CustomerNotFoundException extends IllegalArgumentException{
    private String phone;

    private Customer customer;

    @Override
    public String toString() {
        return "CustomerNotFoundException: Name-"+ customer.getName() ;
    }
}

class InsufficientFundsException extends RuntimeException{
    private double total;
    private double payment;
    @Override
    public String toString() {
        return "InsufficientFundsException: "+total+" due,but only "+payment+" given";
    }
}
class InvalidAmountException extends RuntimeException{
    private int amount;
    private int quantity;
    public InvalidAmountException(int amount){
        super();
        this.amount=amount;
    }
    public InvalidAmountException(int amount,int quantity){
        this.amount=amount;
        this.quantity=quantity;
    }

    @Override
    public String toString() {
        if (quantity==0){
            return "InvalidAmountException: "+amount;
        }else
            return "InvalidAmountException: "+amount+" was required,but only "+quantity+" remaining";
    }
}
class StoreNotFoundException extends IllegalArgumentException{
    private String name;

    public StoreNotFoundException(String name) {
    }

    @Override
    public String toString(){
        return "StoreNotFoundException: "+name;
    }
}
class InvalidPriceException extends RuntimeException{
    private double price;

    @Override
    public String toString() {
        return "InvalidPriceException: "+price;
    }
}
class ProductNotFoundException extends IllegalArgumentException{
    private Long ID;
    private String name;
    Product product;
    public ProductNotFoundException(Long ID){
        super();
        this.ID=ID;
        this.name=null;
    }
    public ProductNotFoundException(String name){
        super();
        this.ID=0L;
        this.name=name;
    }

    @Override
    public String toString() {
        if (name==null){
            return "ProductNotFoundException: ID- "+ID;
        }else
            return "ProductNotFoundException: Name- "+name;
    }
}