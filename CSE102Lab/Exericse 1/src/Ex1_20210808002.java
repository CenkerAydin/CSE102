public class Ex1_20210808002 {
    public static void main(String[] args) {
    Stock stock=new Stock("ORCL","Oracle Corporation");

    System.out.println("The percantage change is: "+stock.getChangePercent(34.5,34.35));
    }
}

class Stock{
    String symbol;
    String name;
    Double previousClosingPrice;
    Double currentPrice;
    Stock(String symbol,String name){
        this.symbol=symbol;
        this.name=name;
    }
    double getChangePercent(double previousClosingPrice,double currentPrice){
        this.previousClosingPrice=previousClosingPrice;
        this.currentPrice=currentPrice;

      double changePercent=((currentPrice-previousClosingPrice)/previousClosingPrice)*100;
        return Math.round(changePercent * 100.0) / 100.0;
    }
    }


class Fan{
    final int SLOW=1;
    final int MEDIUM=2;
    final int FAST=3;
    private int speed;
    private boolean on;
    private double radius=5;
    private String color="blue";

    Fan(){}
    Fan(double radius,String color){
        this.radius=radius;
        this.color=color;
    }

    public int getSpeed(){
        return this.speed;
    }
    public void setSpeed(int speed){
         this.speed=speed;
    }
    public boolean getOn(){
        return this.on;
    }
    public void setOn(boolean on){
        this.on=on;
    }
    public double getRadius(){
        return this.radius;
    }
    public void setRadius(double radius){
        this.radius=radius;
    }
    public String getColor(){
        return this.color;
    }
    public void setColor(String color){
        this.color=color;
    }
    public String toString(int speed,boolean on,double radius,String color){
        this.speed=speed;
        this.on=on;
        this.radius=radius;
        this.color=color;
        if (on){
            return "Fan speed is "+speed+" color is" +color+" radius is "+radius;
        }else
            return "Fan is off, color is"+color+" radius is"+radius;
    }
}
