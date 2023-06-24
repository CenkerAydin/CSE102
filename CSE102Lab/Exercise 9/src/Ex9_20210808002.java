public class Ex9_20210808002 {

}

interface Common<T>{
    public boolean isEmpty();
    public  T peek();
    public int size();
}
interface Stack<T> extends Common<T> {
    public boolean push(T item);

    public T pop();
}
interface Node<T>{
    public final int DEFAULT_CAPACITY=2;
    public void setNext(T item);
    public T getNext();
    public double getPriority();
}
interface PriorityQueue <T> extends Common<T>{
    public final int FLEET_CAPACITY =3;
    public boolean enqueue(T item);
    public  T dequeue();
}
interface Sellable{
    public String getName();
    public double getPrice();
}

 interface Package <T>{
    public T extract();
    public boolean pack(T item);

    public boolean isEmpty();
    public double getPriority();
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

    private int itemPrice;
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
     public double getPriority() {
         throw  new UnsupportedOperationException("Not implemented");
     }

     @Override
     public String toString() {
         return super.toString()+"{"+item+"}";
     }
 }

 class Box <T extends Sellable> implements Package<T>{
    private int distanceToAddress;
    private T item;
    private boolean seal;

    public Box(){
        this.item=null;
        this.seal=false;
    }
    public Box(int distanceToAddress, T item){
        this.distanceToAddress= distanceToAddress;
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
     public double getPriority() {
         return distanceToAddress/item.getPrice();
     }

     @Override
     public String toString() {
         return getClass().getSimpleName()+"{"+item+"}Seal: "+seal;
     }

     public int getDistanceToAddress() {
        return distanceToAddress;
     }
     public void setDistanceToAddress(int distanceToAddress){
        this.distanceToAddress=distanceToAddress;
     }
 }

 class Container implements Stack<Box<?>>,Node<Container>,Comparable<Container>{
    private Box<?>[] boxes;
    private int top;
    private int size;
    private double priority;
    private Container next;


     public Container(){
        this.boxes=new Box[DEFAULT_CAPACITY];
        this.top=-1;
        this.next=null;
        this.priority=0.0;
    }

     @Override
     public boolean isEmpty() {
         if (size==0){
             return true;
         }
         return false;
     }

     @Override
     public Box<?> peek() {
         if (isEmpty())
             return null;

         return boxes[top];
     }

     @Override
     public int size() {
         return size;
     }

     @Override
     public boolean push(Box<?> item) {
        boxes[++top]=item;
        size++;
        return true;
     }

     @Override
     public Box pop() {
         if (isEmpty())
             return null;

         Box removed=boxes[top];
         boxes[top]=null;
         top--;
         size--;
         return removed;
     }


     @Override
     public void setNext(Container item) {
         this.next=item;
     }

     @Override
     public Container getNext() {
         return next;
     }

     @Override
     public double getPriority() {
        return priority;
     }

     @Override
     public int compareTo(Container o) {
        if (this.priority <o.priority){
            return -1;
        }else if (this.priority>o.priority){
            return 1;
        }else {
            int thisSumDistance=calculateSumDistance();
            int otherSumDistance=o.calculateSumDistance();
            return Integer.compare(thisSumDistance,otherSumDistance);
        }
     }
     private int calculateSumDistance(){
         int sum=0;
         for (Box<?>box:boxes){
             sum+=box.getDistanceToAddress();
         }
         return sum;
     }

     @Override
     public String toString() {
         return "Container with priority: "+priority;
     }
 }

 class CargoFleet implements PriorityQueue<Container>{
    private Container head;
    private int size;
    public CargoFleet(){
        this.head=null;
        this.size=0;
    }

     @Override
     public boolean isEmpty() {
         return head==null;
     }

     @Override
     public Container peek() {
        if (head==null){
            return null;
        }
         return head;
     }

     @Override
     public int size() {
         return size;
     }

     @Override
     public boolean enqueue(Container item) {
         if (head==null){
             head=item;
         }else{
             Container cur=head;
             while (cur.getNext()!= null){
                 cur = cur.getNext();
             }
             cur.setNext(item);
         }
         size++;
         return true;
     }

     @Override
     public Container dequeue() {
         if (isEmpty()){
             return null;
         }
         Container remove=head;
         head = (Container) head.getNext();
         remove.setNext(null);
         size--;
         return remove;
     }
 }

 class CargoCompany{
    private Container stack;
    private CargoFleet queue;
    public CargoCompany(){
        this.stack=new Container();
        this.queue=new CargoFleet();
    }

    public void add(Box<?> box){
        Container container =stack;
       if (!container.push(box)){
           if (!queue.enqueue(container)){
               ship(queue);
               stack=new Container();
               container=stack;
               container.push(box);
               queue.enqueue(container);
           }
       }
    }
    private void ship(CargoFleet fleet){
        while (!fleet.isEmpty()){
            Container container=fleet.dequeue();
            empty(container);
        }
    }
    private void empty(Container container){
        while (!container.isEmpty()){
             Box<?> box =container.pop();
            System.out.println(deliver(box));
        }
    }
     private Sellable deliver(Box<?> box){
        Box<?> extracted= (Box<?>) box.extract();
        return (Sellable) extracted;
     }

 }