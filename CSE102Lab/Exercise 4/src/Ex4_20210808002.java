import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Ex4_20210808002 {
}
class Computer{
    protected Cpu cpu;
    protected Ram ram;

    public Computer(Cpu cpu,Ram ram){
        this.cpu=cpu;
        this.ram=ram;
    }
    public void run(){
        int sum= ram.getValue(0,0);
        for (int i=1;i<ram.getCapacity();i++){
            sum+=ram.getValue(i,i);
        }
        cpu.compute(sum,0);
    }

    @Override
    public String toString() {
        return "Computer:"+cpu+""+ram;
    }
}

class Laptop extends Computer{
private int milliAmp;
private int battery;
public Laptop(Cpu cpu,Ram ram,int milliAmp,int battery){
    super(cpu, ram);
    this.milliAmp=milliAmp;
    this.battery=milliAmp*(30/100);
}
//.
public int batteryPercentage(){
    return battery *100/milliAmp;
}
public void charge(){
    while (batteryPercentage()<90){
        battery+=milliAmp*(2/100);
    }
}

    @Override
    public void run() {
    if (batteryPercentage()>5) {
        super.run();
        battery -=milliAmp*(3/100);
    }else charge();
    }

    @Override
    public String toString() {
      return   super.toString()+""+battery;
    }
}

class Desktop extends Computer{
private ArrayList<String> peripherals;

public Desktop(Cpu cpu,Ram ram,String[]peripherals) {
  super(cpu, ram);
   this.peripherals=new ArrayList<>(Arrays.asList(peripherals));
}
@Override
public void run(){
    int sum=ram.getValue(0,0);
    for (int i=0;i< ram.getCapacity();i++){
        for (int j=0;j< ram.getCapacity();j++){
            sum=cpu.compute(sum,ram.getValue(i,j));
        }
    }
    }
    public void plugIn(String peripheral){
    peripherals.add(peripheral);
    }
    public String plugOut(){
    return peripherals.remove(peripherals.size()-1);
    }
    public String plugOut(int index) {
        return peripherals.remove(index);
    }
    @Override
    public String toString() {
        return super.toString()+"";
    }
}


class Cpu{
private String name;
private double clock;
public Cpu(String name,double clock){
    this.name=name;
    this.clock=clock;
}
public String getName(){
    return name;
}
public  double getClock(){
    return clock;
}
public int compute(int a,int b){
    return a+b;
}

@Override
    public String toString(){
    return "CPU:"+name+""+clock+"Ghz";
}
}
class Ram{
    private String type;
    private int capacity;
    private int[][] memory;

    public Ram(String type,int capacity){
        this.type=type;
        this.capacity=capacity;
    }
    public String getType(){
        return type;
    }
    public int getCapacity(){
        return capacity;
    }
    private void initMemory(){
        int[][]memory=new int[capacity][capacity];
        Random r=new Random();
        for (int i=0;i<capacity;i++){
            for (int j=0;j<capacity;j++){
                memory[i][j]=r.nextInt(10);
            }
        }
    }
    private boolean check(int i,int j){
        if ((i>=0 && i<capacity) &&(j>=0 && j<capacity))
            return true;

        return false;
    }
    public int getValue(int i,int j){
        if (check(i,j)){
            return memory[i][j];
        }else
            return -1;
    }
    public void setValue(int i,int j,int value){
        if (check(i,j)){
            memory[i][j]=value;
        }
    }

    @Override
    public String toString() {
        return "RAM: "+ type+ ""+capacity+"GB";
    }
}
