import java.util.ArrayList;
import java.util.Random;

public class Ex7_20210808002 {
}

interface Damageable{
    public void takeDamage(int damage);
    public void takeHealing(int healing);
    public boolean isAlive();
}
interface Caster{
    public void castSpell(Damageable target);
    public void learnSpell(Spell spell);


    void levelUp();
}
interface Combat extends Damageable{
    void attack(Damageable target);
    void lootWeapon(Weapon weapon);

    void levelUp();
}

interface Useable {
    public int use();
}

class Spell implements Useable{
    private int minHeal;
    private int maxHeal;
    private String name;

    public Spell(String name,int minHeal,int maxHeal) {
        this.minHeal = minHeal;
        this.maxHeal = maxHeal;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private int heal(){
        Random r=new Random();
        return r.nextInt((maxHeal-minHeal)+1)+minHeal;
    }
    @Override
    public int use() {
    return heal();
    }
}

class Weapon implements Useable{
   private int minDamage;
   private int maxDamage;
   private String name;

   public Weapon(String name,int minDamage,int maxDamage){
       this.name=name;
       this.minDamage=minDamage;
       this.maxDamage=maxDamage;
   }

   private int attack(){
       Random r=new Random();
       return r.nextInt((maxDamage-minDamage)+1)+minDamage;
   }
    @Override
    public int use() {
        return attack();
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class Attributes{
     private int strength;
     private int intelligence;


     public Attributes(){
         this.strength=3;
         this.intelligence=3;
     }
     public Attributes(int strength,int intelligence){
         this.strength=strength;
         this.intelligence=intelligence;
     }
     public void increaseStrength(int amount){
         strength+=amount;
     }
     public void increaseIntelligence(int amount){
         intelligence+=amount;
     }

    public int getStrength() {
        return strength;
    }

    public int getIntelligence() {
        return intelligence;
    }

    @Override
    public String toString() {
        return "Attributes[Strength="+strength+",intelligence="+intelligence+"]";
    }
}

 abstract  class Character implements Comparable<Character>{
    private String name;
    protected int level;
    protected Attributes attributes;
    protected int health;

    Character(String name,Attributes attributes){
        this.name=name;
        this.attributes=attributes;
    }

     public  abstract void levelUp() ;



     @Override
    public int compareTo(Character o){
        if (this.level<o.level){
            return -1;
        }else if (this.level>o.level){
            return 1;
        }else{
            return 0;
        }
    }

     public String getName() {
         return name;
     }

     public int getLevel() {
         return level;
     }

     @Override
     public String toString() {
         return getClass().getSimpleName()+"LvL: "+level+"-"+attributes;
     }
 }

 abstract class PlayableCharacter extends Character implements Damageable{
       private boolean inParty;
       private Party party;
     PlayableCharacter(String name, Attributes attributes) {
         super(name, attributes);
         this.inParty=false;
         this.party=null;
     }

     @Override
     public void takeDamage(int damage) {
         health -=damage;
     }

     @Override
     public void takeHealing(int healing) {
         health +=healing;
     }

     @Override
     public boolean isAlive() {
         if (health>0)
             return true;

         return false;
     }

     public boolean isInParty(){
        return inParty;
     }
     public void joinParty(Party party){
        if (inParty){
            System.out.println(getName()+" is in a party.");
            return;
        }
        try {
            party.addCharacter(this);
            this.party=party;
            inParty=true;
            System.out.println(getName()+ " joined the party.");
        } catch (PartyLimitReachedException e) {
            System.out.println(e.getMessage());
        } catch (AlreadyInPartyException e) {
            System.out.println(e.getMessage());
        }
     }
     public void quitParty(){
        if (!inParty){
            System.out.println(getName()+" is not in a party");
            return;
        }
        try {
            party.removeCharacter(this);
            inParty=false;
        } catch (CharacterIsNotInPartyException e) {
            System.out.println(e.getMessage());
        }
     }
 }
 abstract class NonPlayableCharacter extends Character{

     NonPlayableCharacter(String name,Attributes attributes) {
         super(name,attributes);
     }
 }

 class Merchant extends NonPlayableCharacter{

     Merchant(String name ) {
       super(name,new Attributes(0,0));
     }
     @Override
     public void levelUp() {
     }
 }

 class Skeleton extends NonPlayableCharacter implements Combat{

     Skeleton(String name, Attributes attributes) {
         super(name, attributes);
     }

     @Override
     public void takeDamage(int damage) {
        health -=damage;
     }

     @Override
     public void takeHealing(int healing) {
        this.health -=healing;
     }

     @Override
     public boolean isAlive() {
         if (health>0)
             return true;
         return false;
     }

     @Override
     public void levelUp() {
         this.level +=1;
         this.attributes.increaseStrength(1);
         this.attributes.increaseIntelligence(1);
     }

     @Override
     public void attack(Damageable target) {
        int damage=attributes.getStrength()+attributes.getStrength();
        target.takeDamage(damage);
     }

     @Override
     public void lootWeapon(Weapon weapon) {

     }
 }

 class Warrior extends PlayableCharacter implements Combat{
    private Useable weapon;
     Warrior(String name) {
         super(name,new Attributes(4,2));
         this.health=35;
     }
     @Override
     public void takeDamage(int damage) {
        health -=damage;
     }

     @Override
     public void takeHealing(int healing) {
        health +=healing;
     }

     @Override
     public boolean isAlive() {
         if (health>0)
             return true;
         return false;
     }

     @Override
     public void attack(Damageable target) {
        int damage=attributes.getStrength()+ weapon.use();
        target.takeDamage(damage);
     }

     @Override
     public void lootWeapon(Weapon weapon) {

     }

     @Override
     public void levelUp() {
        this.attributes.increaseStrength(2);
        this.attributes.increaseIntelligence(1);
        this.level +=1;
     }
 }

 class Cleric extends PlayableCharacter implements Caster{

    private Useable spell;

     Cleric(String name) {
         super(name, new Attributes(2,4));
         this.health=25;
     }

     @Override
     public void takeDamage(int damage) {
        health -=damage;
     }

     @Override
     public void takeHealing(int healing) {
        health +=healing;
     }
     @Override
     public boolean isAlive() {
         if (health>0)
             return true;
         return false;
     }

     @Override
     public void castSpell(Damageable target) {
         int spellPower=spell.use()*attributes.getIntelligence();
         target.takeHealing(spellPower);
     }

     @Override
     public void learnSpell(Spell spell) {
        this.spell=spell;
     }

     @Override
     public void levelUp() {
        this.attributes.increaseIntelligence(2);
        this.attributes.increaseStrength(1);
        this.level +=1;
     }
 }

 class Paladin extends PlayableCharacter implements Combat,Caster{
    private Useable weapon;
    private Useable spell;
     Paladin(String name) {
         super(name, new Attributes());
         this.health=30;
     }

     @Override
     public void takeDamage(int damage) {
        health -=damage;
     }

     @Override
     public void takeHealing(int healing) {
        health +=healing;
     }

     @Override
     public boolean isAlive() {
         if (health>0)
             return true;
         return false;
     }

     @Override
     public void castSpell(Damageable target) {
         int spellPower=spell.use()*attributes.getIntelligence();
         target.takeHealing(spellPower);
     }

     @Override
     public void learnSpell(Spell spell) {
        this.spell=spell;
     }

     @Override
     public void attack(Damageable target) {
        int damage=attributes.getStrength()+ weapon.use();
        target.takeDamage(damage);
     }

     @Override
     public void lootWeapon(Weapon weapon) {
         this.weapon=weapon;
     }

     @Override
     public void levelUp() {
        if ((getLevel() &2)==0){
            this.attributes.increaseStrength(2);
            this.attributes.increaseIntelligence(1);
        }else {
            this.attributes.increaseIntelligence(2);
            this.attributes.increaseStrength(1);
        }
         this.level +=level;

     }
 }
 class Party{
    final int partyLimit=8;
    private ArrayList<Combat>fighters;
    private ArrayList<Caster> healers;
    private int mixedCount;

     public Party(ArrayList<Combat> fighters, ArrayList<Caster> healers, int mixedCount) {
         this.fighters = new ArrayList<>();
         this.healers = new ArrayList<>();
         this.mixedCount = mixedCount;
     }

     public void addCharacter(PlayableCharacter character)throws PartyLimitReachedException,AlreadyInPartyException{
         if (fighters.size()+healers.size()>=partyLimit){
             throw new PartyLimitReachedException("Party reached its limit.");
         }
         if (fighters.contains(character)|| healers.contains(character)){
             throw new AlreadyInPartyException("Character is already in party");
         }
         if (character instanceof Combat){
             fighters.add((Combat) character);
         }else if (character instanceof Caster){
             healers.add((Caster) character);
             if (character instanceof Paladin){
                 mixedCount +=1;
             }
         }
     }

     public void removeCharacter(PlayableCharacter character)throws CharacterIsNotInPartyException{
        if (fighters.contains(character)){
            fighters.remove(character);
        }else if (healers.contains(character)){
            healers.remove(character);
            if (character instanceof Paladin){
                mixedCount -=1;
            }
        }else {
            throw new CharacterIsNotInPartyException("Character is not in the party.");
        }
     }
     public void partyLevelUp(){
         for (Combat fighter: fighters){
             fighter.levelUp();
         }
         for (Caster healer:healers){
             if (!(healer instanceof  Paladin)){
                 healer.levelUp();
             }
         }
     }


 }
 class Barrel implements Damageable{
        private int health=30;
        private int capacity=10;

     public Barrel(int health, int capacity) {
         this.health = health;
         this.capacity = capacity;
     }
     public void explode(){
         System.out.println("Explodes");
     }
     public void repair(){
         System.out.println("Repairing");
     }
     @Override
     public void takeDamage(int damage) {
    if (health<=0){
        explode();
        }
     }

     @Override
     public void takeHealing(int healing) {
         repair();
     }

     @Override
     public boolean isAlive() {
         if (health>0)
             return true;
         return false;
     }
 }
 class TrainingDummy implements Damageable{
    private int health=25;

     @Override
     public void takeDamage(int damage) {
        health -=damage;
     }

     @Override
     public void takeHealing(int healing) {
        health +=healing;
     }

     @Override
     public boolean isAlive() {
         if (health >0)
             return true;
         return false;
     }
 }
 class PartyLimitReachedException extends Exception{
    public PartyLimitReachedException(String message){
        super(message);
    }
 }
 class AlreadyInPartyException extends Exception{
    public AlreadyInPartyException(String message){
        super(message);
    }
 }
 class CharacterIsNotInPartyException extends Exception{
    public CharacterIsNotInPartyException(String message){
        super(message);
    }
 }
