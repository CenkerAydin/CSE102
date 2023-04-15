public class Test02 {
    public static void main(String[] args) {
        Store s=new Store("Migros","www.migros.com.tr");

        Customer c=new Customer("CSE 102");

        ClubCustomer cc=new ClubCustomer("Club CSE 102","05551234567");
        s.addCustomer(cc);

        Product  p =new Product(123456L,"Computer",20,1000.00);
        FoodProduct fp=new FoodProduct(456789L,"Snickers",100,2,250,true,true,true,false);
        CleaningProduct cp=new CleaningProduct(31654L,"Mop",28,99,false,"Multi-Room");

        s.addProduct(p);
        s.addProduct(fp);
        s.addProduct(cp);


        System.out.println(s.getInventorySize());
        System.out.println(s.getProduct("Shoes"));

        System.out.println(cp.purchase(2));
        s.getProduct("Computer").addToInventory(3);
        //System.out.println(fp.purchase(200));

        c.addToCart(p,2);
        c.addToCart(s.getProduct("Snickers"),-2);
        c.addToCart(s.getProduct("Snickers"),1);
        System.out.println("Total Due - "+c.getTotalDue());
        System.out.println("\n\n Receipt:\n"+c.receipt());

        System.out.println("After paying: "+c.pay(2000));
        System.out.println("After paying: "+c.pay(2020));
        System.out.println("Total Due - "+c.getTotalDue());
        System.out.println("\n\nReceipt 1:\n"+c.receipt());
        Customer c2=s.getCustomer("05551234568");
        cc.addToCart(s.getProduct("Snickers"),2);
        cc.addToCart(s.getProduct("Snickers"),1);
        System.out.println("\n\n Receipt2:\n"+cc.receipt());

        Customer c3=s.getCustomer("05551234567");
        cc.addToCart(s.getProduct("Snickers"),10);
        System.out.println("\n\n Receipt3:\n"+c3.receipt());

        System.out.println(((ClubCustomer)c3).pay(26,false));
        c3.addToCart(s.getProduct(31654L),3);
        System.out.println(c3.getTotalDue());
        System.out.println(c3.receipt());
        System.out.println(cc.pay(3*99,false));

        c3.addToCart(s.getProduct(31654L),3);
        System.out.println(c3.getTotalDue());
        System.out.println(c3.receipt());
        System.out.println(cc.pay(3*99,true));


    }
}
