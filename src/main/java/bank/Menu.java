package bank;

import java.util.Scanner;

import javax.security.auth.login.LoginException;

import bank.exceptions.AmountException;

public class Menu {
  
  private Scanner scanner;

  public static void main(String[] args) {
    System.out.println("Welcome to Globe Bank International!");

    Menu menu = new Menu();
    menu.scanner = new Scanner(System.in);

    Customer customer = menu.authenticateUser();

    if(customer != null) {
      Account account = DataSource.getAccount(customer.getAccountId());
      menu.showMenu(customer, account);
    }


    menu.scanner.close();
  }

  private Customer authenticateUser() {
    System.out.println("Please Enter Your Username");
    String username = scanner.next();

    System.out.println("Please Enter Your Password");
    String password = scanner.next();

    Customer customer = null;
    try {
      customer = Authenticator.login(username, password);
    } catch(LoginException e) {
      System.out.println("There was an error: " + e.getMessage());
    }

    return customer;
  }

  private void showMenu(Customer customer, Account account) {

    int selection = 0;

    while (selection != 4 && customer.isAuthenticated()) {
      System.out.println("=============================================\nPlease Select One of the Following Options: \n1: Deposit\n2: Withdraw\n3: Check Balance\n4: Exit\n=============================================");
      
      selection = scanner.nextInt();
      double amount = 0;

      switch (selection) {
        case 1:
        System.out.println("How Much Would You Like to Deposit?");
        amount = scanner.nextDouble();
        try {
          account.deposit(amount);
        } catch(AmountException e) {
          System.out.println(e.getMessage());
          System.out.println("Please Try Again");
        }
        break;

        case 2:
        System.out.println("How Much Would You Like to Withdraw?");
        amount = scanner.nextDouble();
        try {
          account.withdraw(amount);
        } catch(AmountException e) {
          System.out.println(e.getMessage());
          System.out.println("Please Try Again");
        }
        break;

        case 3:
        System.out.println("Current Balance: " + account.getBalance());
        break;

        case 4:
        Authenticator.logout(customer);
        System.out.println("Thanks for Banking at Globe Bank International");
        break;

        default:
        System.out.println("Invalid Option, Please Try Again.");
        break;
      }


    }
  }
}