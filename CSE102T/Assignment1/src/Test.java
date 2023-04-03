public class Test {
    public static void main(String[] args) {
        Store s=new Store("Migros","www.migros.com.tr");

        Customer c=new Customer("CSE 102");
        System.out.println(c);

        ClubCustomer cc=new ClubCustomer("Club CSE 102","05551234567");
        cc.addPoints(20);
        cc.addPoints(30);
        System.out.println(cc.getPhone());
        System.out.println(cc);

        Product p=new Product("1234","Customer",20,1000.00);
        FoodProduct fp=new FoodProduct("3456","Snickers",100,2,250,true,true,true,false);
        CleaningProduct cp=new CleaningProduct("5678","Mop",28,99,false,"Multi-room");

        s.addProduct(p);
        s.addProduct(fp);

        for (int i=0;i<s.getInventorySize();i++)
            System.out.println(s.getProduct(i));
        s.addProduct(cp);

        s.addProduct(new Product("4321","Iphone",50,99.00));
        System.out.println(s.getProductIndex(new FoodProduct("8888","Apples",500,1,50,false,false,false,false)));
        System.out.println(cp.purchase(2));
        if (fp.containsGluten())
            System.out.println("My wife cannot eat or drink "+ fp.getName());
        else
            System.out.println("My wife can eat or drink "+ fp.getName());

        if (fp.containsPeanuts())
            System.out.println("My friend cannot eat or drink "+fp.getName());
        else
            System.out.println("My friend can eat or drink "+fp.getName());
        s.getProduct(0).addToInventory(3);
        for (int i=0;i<s.getInventorySize();i++){
            Product cur=s.getProduct(i);
            System.out.println(cur);
            for (int j=i+1;j> s.getInventorySize();i++)
                if (cur.equals(s.getProduct(j)))
                    System.out.println(cur.getName()+" is the same price as "+s.getProduct(j).getName());
        }
    }
}
