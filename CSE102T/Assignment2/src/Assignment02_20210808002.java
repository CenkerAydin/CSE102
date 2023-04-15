import java.util.ArrayList;

//@author Cenker Aydın
//@since 04.04.2023
public class Assignment02_20210808002 {
}

class Product{
private Long Id;
private String Name;
private int Quantity;
private double Price;

    Product(Long id,String name,int quantity,double price){
        if (price<0){
            throw new InvalidPriceException();
        }
        if (quantity<0){
            throw new InvalidAmountException(quantity);
        }
        this.Id=id;
        this.Name=name;
        this.Quantity=quantity;
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
    public int getQuantity() {
        return Quantity;
    }
    public void setQuantity(int quantity) {
        this.Quantity = quantity;
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

    public int remaining(){
        return Quantity;
    }

    public int addToInventory(int amount){
        if (amount<0){
            throw new InvalidAmountException(amount);
        }
        if (amount>0) {
            Quantity+=amount;
            return Quantity;
        }else
            return Quantity;
    }
    public double purchase(int amount){
        if (amount<0){
            throw new InvalidAmountException(amount);
        }else if (amount>Quantity){
            throw new InvalidAmountException(amount,Quantity);
        }
        else{
            Quantity-=amount;
            double totalPrice=Price*amount;
            return totalPrice;
        }
    }
    @Override
    public String toString(){
        return "Product "+ getName() +" has "+ remaining() +" remaining";
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
        super(id, name, quantity, price);
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
        super(id, name, quantity, price);
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
    private String Name;
    protected ArrayList<ProductCount>carts;
    private double totalDue;
    Customer(String name){
        this.Name =name;
        this.carts=new ArrayList<>();
        this.totalDue=0.0;

    }
    public String getName(){return Name;}
    public void setName(String name){ this.Name =name;}
    public void addToCart(Product product,int count){
        try {
        double subtotal=product.purchase(count);
        carts.add(new ProductCount(product,count));
       totalDue +=subtotal;
        }catch (InvalidAmountException e){
            System.out.println("ERROR: "+e.toString());
        }
    }

    public String receipt() {
        String res = "";
        for (ProductCount product : carts) {
            double totalProduct =  product.getProduct().getPrice()*product.getCount();
            res += product.getProduct().getName() + " - " + product.getProduct().getPrice() + " X " + product.getCount() + " = " + totalProduct + "\n";
        }
        res += "Total Due - " + totalDue + "\n";
        return res;
    }

    public double getTotalDue(){
        return totalDue;
    }
    public double pay(double amount){
        if (amount >=totalDue){
            System.out.println("Thank you for shopping with us");
            double change =amount-totalDue;
            carts.clear();
            totalDue=0.0;
            return change;
        }else
            throw new InsufficientFundsException();
    }

    @Override
    public String toString(){
        return ""+getName();
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

class ClubCustomer extends Customer{
    private String Phone;
    private int Points;

    ClubCustomer(String name,String phone){
        super(name);
        this.Phone =phone;
        this.Points =0;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        this.Phone = phone;
    }

    public int getPoints() {
        return Points;
    }
    public void addPoints(int points){
        if (points<0){
        }else
            this.Points +=points;
    }
    @Override
    public String toString(){
        return getName() +" has "+getPoints()+" points";
    }

   public double pay(double amount, boolean usePoints) {
      if (usePoints) {
          int pointsToUse = this.Points;
          double discountAmount = pointsToUse * 0.01;
          if (amount < discountAmount) {
              discountAmount = amount;
              pointsToUse = (int)(discountAmount / 0.01);
          }
          amount -= discountAmount;

          // Reduce customer's points to 0
          this.Points -= pointsToUse;
          if (this.Points < 0) {
              this.Points = 0;
          }
      }

      double finalAmount = super.pay(amount);
      if (finalAmount != -1) {
          int earnedPoints = (int)(finalAmount);
          this.Points += earnedPoints;
      }

      return finalAmount;
  }
    }

class Store{
    private String Name;
    private String Website;
    private ArrayList<Product> productList;
    private ArrayList<ClubCustomer> clubCustomer;

    Store(String name,String website){
        this.Name =name;
        this.Website=website;
        this.productList=new ArrayList<Product>();
        this.clubCustomer=new ArrayList<ClubCustomer>();
    }
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getWebsite() {
        return Website;
    }

    public void setWebsite(String website) {
        this.Website = website;
    }
    public int getInventorySize(){
        return productList.size();
    }
    public void addProduct(Product product,int index){
        if (index<0 || index>productList.size()){
            productList.add(product);
        }else {
            productList.add(index, product);
        }
    }
    public void addProduct(Product product){
       productList.add(product);
    }
    public Product getProduct(Long ID){
        for(Product product: productList) {
            if (product.getId().equals(ID)) {
                return product;
            }
    }
        throw new ProductNotFoundException(ID);
    }

    public Product getProduct(String name){
        for (Product product:productList){
            if (product.getName().equals(name)){
                return product;
            }
        }
        throw new ProductNotFoundException(name);
    }
    public void addCustomer(ClubCustomer customer){
        clubCustomer.add(customer);
    }
    public ClubCustomer getCustomer(String phone){
        for (ClubCustomer customer:clubCustomer){
            if (customer.getPhone().equals(phone)){
                return customer;
            }
        }
        throw new CustomerNotFoundException();
    }
    public void  removeProduct(Long ID){
        for (Product product:productList){
            if (product.getId().equals(ID))
                productList.remove(product);
        }
        throw new ProductNotFoundException(ID);
    }
    public void removeProduct(String name){
        for (Product product:productList){
            if (product.getName().equals(name))
                productList.remove(product);
        }
    }
    public void removeCustomer(String phone){
        for (ClubCustomer customer:clubCustomer){
            if (customer.getPhone().equals(phone))
                clubCustomer.remove(customer);
        }
    }

    public Product getProduct(int index){
        if (index<0 || index>getInventorySize()){
            return null;
        }
        return this.productList.get(index);
    }
    public int getProductIndex(Product p){
        if (!productList.contains(p))
            return -1;

        return this.productList.indexOf(p);
    }
}
class CustomerNotFoundException extends IllegalArgumentException{
    private String phone;
    @Override
    public String toString() {
        return "CustomerNotFoundException: " +phone;
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