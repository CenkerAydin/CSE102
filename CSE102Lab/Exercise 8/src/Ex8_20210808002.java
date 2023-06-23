public class Ex8_20210808002 {

}

interface Sellable{
    public String getName();
    public double getPrice();
}

 interface Package <T>{
    public T extract();
    public boolean pack(T item);

    public boolean isEmpty();
 }

 interface Wrappable extends Sellable{
 }

 abstract class Product implements Sellable{
    private String name;
    private double price;

    public  Product(String name,double price){
    this.name=name;
    this.price=price;
    }
     @Override
     public String toString() {
         return getClass().getSimpleName() +"("+name+","+price+")";
     }
 }

 class Mirror extends Product{
    private int width;
    private int heigth;

     public Mirror(int width,int heigth) {
         super("mirror",2.0);
         this.width=width;
         this.heigth=heigth;
     }

     public int getArea(){
         return width*heigth;
     }

     public <T> T reflect(T item){
         System.out.println(item);
        return item;
     }

     @Override
     public String getName() {
         return null;
     }

     @Override
     public double getPrice() {
         return 0;
     }
 }

 class Paper extends Product implements Wrappable{
    private String note;

    public Paper(String note){
        super("A4",3.0);
        this.note=note;
    }

    public String getNote(){
        return note;
    }
    public void setNote(String note){
        this.note=note;
    }
     @Override
     public String getName() {
         return null;
     }

     @Override
     public double getPrice() {
         return 0;
     }
 }

 class Matroschka <T extends Wrappable> extends Product implements Wrappable,Package<T>{
    private T item;
     public Matroschka(T item) {
         super("Doll",5+ item.getPrice());
         this.item=item;
     }

     @Override
     public String getName() {
         return null;
     }

     @Override
     public double getPrice() {
         return 0;
     }

     @Override
     public T extract() {
        if (isEmpty()){
            return null;
        }else {
            T extracted=item;
            item=null;
            return extracted;
        }
     }


     @Override
     public boolean pack(T newItem) {
         if (isEmpty()){
             item= (T) newItem;
             return true;
         }
         return false;
     }
     @Override
     public boolean isEmpty() {
         if (item==null){
             return true;
         }else
             return false;
     }

     @Override
     public String toString() {
         return super.toString()+"{"+item+"}";
     }
 }

 class Box <T extends Sellable> implements Package<T>{
    private T item;
    private boolean seal;

    public Box(){
        this.item=null;
        this.seal=false;
    }
    public Box(T item){
        this.item=item;
        this.seal=true;
    }
     @Override
     public T extract() {
        T extracted =item;
        item=null;
        seal=false;
        return extracted;
     }

     @Override
     public boolean pack(T newItem) {
         if (isEmpty()){
             item=newItem;
             seal=true;
             return true;
         }
         return false;
     }
     @Override
     public boolean isEmpty() {
         if (item==null){
             return true;
         }else
             return false;
     }

     @Override
     public String toString() {
         return getClass().getSimpleName()+"{"+item+"}Seal: "+seal;
     }
 }