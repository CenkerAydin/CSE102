import java.util.ArrayList;

//@author Cenker Aydın
//@since 17.03.23
public class Assignment01_20210808002 {
}

class Product{
    private String Id;
    private String Name;
    private int Quantity ;
    private double Price;

    Product(String id,String name,int quantity,double price){
        this.Id=id;
        this.Name=name;
        this.Quantity=quantity;
        this.Price=price;
    }
    public String getId(){ return Id;}
    public void setId(String id){this.Id=id;}
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
        this.Price = price;
    }

    public int remaining(){
        return Quantity;
    }

    public int addToInventory(int amount){
        if (amount>0) {
             Quantity+=amount;
            return Quantity;
        }else
            return Quantity;
    }
    public double purchase(int amount){
        if (amount>Quantity || amount<0){
            return 0;
        }else{
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

    FoodProduct(String id,String name,int quantity,double price,int calories,boolean dairy,boolean peanuts,boolean eggs,boolean gluten){
    super(id, name, quantity, price);
    this.Calories =calories;
    this.Dairy =dairy;
    this.Eggs =eggs;
    this.Peanuts =peanuts;
    this.Gluten =gluten;
    }
    public int getCalories(){ return Calories;}
    public void setCalories(int calories){ this.Calories =calories; }

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
    CleaningProduct(String id, String name, int quantity, double price, boolean liquid, String whereToUse){
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
    Customer(String name){
        this.Name =name;
    }
    public String getName(){return Name;}
    public void setName(String name){ this.Name =name;}

    @Override
    public String toString(){
        return ""+getName();
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
}

class Store{
    private String Name;
    private String Website;
    private ArrayList<Product> productList;

    Store(String name,String website){
        this.Name =name;
        this.Website=website;
        this.productList=new ArrayList<Product>();
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
        return this.productList.size();
    }
    public void addProduct(Product product,int index){
        if (index<0 || index>this.productList.size()){
           this.productList.add(product);
        }else {
            this.productList.add(index, product);
        }
    }
    public void addProduct(Product product){
        this.productList.add(product);
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
