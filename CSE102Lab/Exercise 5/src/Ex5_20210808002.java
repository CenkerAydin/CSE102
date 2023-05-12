
import java.util.ArrayList;


public class Ex5_20210808002 {
}

class Account{
    private String accountNumber;
    private double balance;
      Account(String accountNumber,double balance)throws InsufficientFundsException{
         this.accountNumber=accountNumber;
         if (balance<0){
             throw new InsufficientFundsException(balance);
         }else
             this.balance=balance;
     }
    public String getAccountNumber() {
        return accountNumber;
    }
    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) throws InvalidTransactionException {
        if (amount < 0) {
            throw new InvalidTransactionException(amount);
        }
        balance+=amount;
     }
    public void withDraw(double amount)throws InvalidTransactionException,InsufficientFundsException{
         if (amount<0){
             throw new InvalidTransactionException(amount);
         }
         if (balance<amount){
             throw new InsufficientFundsException(balance,amount);
         }
         balance -=amount;
    }
    @Override
    public String toString(){
         return "Account: "+accountNumber+",Balance: "+balance;
    }

}

class Customer{
    private String name;
    private ArrayList<Account> accounts;
    Customer(String name){
        this.name=name;
        accounts=new ArrayList<Account>();
    }
    public Account getAccount(String accountNumber)throws AccountNotFoundException{
    for (Account account:accounts){
        if (account.getAccountNumber().equals(accountNumber)){
            return account;
        }
    }
    throw new AccountNotFoundException(accountNumber);
    }
    public void addAccount(Account account) throws AccountAlreadyExistException{
        try {
            getAccount(account.getAccountNumber());
            throw  new AccountAlreadyExistException(account.getAccountNumber());
        }catch (AccountNotFoundException e){
            accounts.add(account);
            System.out.println("Added account: "+account.getAccountNumber()+" with "+account.getBalance());
        }
    }
    public void removeAccount(String accountNumber) throws AccountNotFoundException{
        accounts.remove(getAccount(accountNumber));
    }
    public void transfer(String fromAccount,String toAccount,double amount) throws InvalidTransactionException {
        try{
            Account from=getAccount(fromAccount);
            Account to=getAccount(toAccount);
            from.withDraw(amount);
            to.deposit(amount);
        } catch (InvalidTransactionException e) {
            throw new InvalidTransactionException(e,"cannot transfer funds from account "+fromAccount+"to account" +toAccount);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder();
        sb.append("Customer ").append(name+":\n");
        for (Account account:accounts){
            sb.append("\t").append(account.toString());
        }
        return sb.toString();
    }
}

class InsufficientFundsException extends RuntimeException{
    public InsufficientFundsException(double balance){
        super("Wrong balance: "+balance);
    }
    public InsufficientFundsException(double balance,double amount){
        super("Required amount is "+amount+" but only "+ balance+" remaining");
    }
}

class AccountAlreadyExistException extends RuntimeException{
    public AccountAlreadyExistException(String accountNumber){
        super("Account number "+accountNumber+" already exists");
    }
}

class AccountNotFoundException extends RuntimeException{
    public AccountNotFoundException(String accountNumber){
        super("Account number "+accountNumber+ " already exists");
    }
}

class InvalidTransactionException extends Exception{
    public InvalidTransactionException(double amount){
        super("Invalid amount: "+amount);
    }
    public InvalidTransactionException(InvalidTransactionException e,String message){
        super(message +":\n\t "+e.getMessage());
    }
}
