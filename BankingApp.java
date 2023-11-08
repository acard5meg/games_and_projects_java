import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.ArrayList;
//import java.util.NumberFormatException;

public class BankingApp {

	private Scanner kb = new Scanner(System.in);
	
	public void appControl(ArrayList<BankAccount> allAccts) {
		BankAccount accountToUse = enterApp(allAccts);
		while (true) {
			options();
			switchStatement(allAccts, accountToUse);
		}
	}
	
	private void switchStatement(ArrayList<BankAccount> allAccts, BankAccount personalAcct) {
		int choice = readInt();

		switch (choice) {
			case 1: personalAcct.checkBalance();
					break;
			case 2: personalAcct.depositMoney();
					break;
			case 3: personalAcct.withdrawMoney();
					break;
			case 4: personalAcct.transferMoney(allAccts);
					break;
			case 5: printAllBalances(allAccts);
					break;
			case 6: System.exit(0);
					break;
			default: System.out.println("We're sorry that is not an option.");
					break;
		}
	}
	
	private void options() {
		//Tested
		System.out.println("Please enter: ");
		System.out.println("1. Check balance.");
		System.out.println("2. Deposit money.");
		System.out.println("3. Withdraw money.");
		System.out.println("4. Transfer money between accounts.");
		System.out.println("5. Show all account balances.");
		System.out.println("6. Exit app.");
	}
	
	/**
	 * Validates the user has an account with the bank
	 */
	private BankAccount enterApp(ArrayList<BankAccount> allAccts) {
		//Tested
		System.out.print("Welcome to your banking app. ");
		System.out.println("Please enter your account number.");
		String accountNumber = kb.nextLine();
		int accountFound = findAcct(allAccts,accountNumber); 
		if (accountFound > 0) {
			System.out.println("Welcome " + accountNumber);
			System.out.println("What would you like to do today?");
			return allAccts.get(accountFound);
		}
		else {
			System.out.println("We're sorry, you do not have an account with us.");
			System.exit(0);
			return null;
		}
	}
	
	private int findAcct(ArrayList<BankAccount> bankAcct, String acctNum) {
		int acctFound = -1;
		for (int i = 0; i < bankAcct.size(); i++) {
			if (bankAcct.get(i).checkAccountNumber(acctNum)) {
				acctFound = i;
				break;
			}
		}
		return acctFound;
	}
	
	private void printAllBalances(ArrayList<BankAccount> allAccts) {
		for (int i = 0; i < allAccts.size(); i++) {
			allAccts.get(i).checkBalance();
		}
	}
	
	
	/**
	 * Reads user input double value and validates it's a positive value
	 * @return
	 */
	private double readNumber() {
		// Tested
		double value = 0.0;
		while (true) {
			try {
				value = kb.nextDouble();
				if (value >= 0)
					return value;
				else
					System.out.println("Cannot enter a negative number");
			}
			catch (InputMismatchException e) {
				System.out.print("We apologize " + kb.nextLine());
				System.out.println(" is an invalid input.");
			}
		}
	}
	
	/**
	 * Reads user input int value and validates it's a positive value 
	 * @return
	 */
	private int readInt() {
		// Tested
		int value = 0;
		while (true) {
			try {
				value = kb.nextInt();
				if (value >= 0)
					return value;
				else
					System.out.println("Cannot enter a negative number");
			}
			catch (InputMismatchException e) {
				System.out.print("We apologize " + kb.nextLine());
				System.out.println(" is an invalid input.");
			}
		}
	}
	
//	private int readInt() {
//		// Tested
//		String value;
//		while (true) {
//			try {
//				value = kb.nextLine();
//				int newVal = Integer.parseInt(value);
//				if (newVal >= 0)
//					return newVal;
//				else
//					System.out.println("Cannot enter a negative number");
//			}
//			catch (NumberFormatException e) {
//				System.out.print("We apologize " + kb.nextLine());
//				System.out.println(" is an invalid input.");
//			}
//		}
//	}

	
	public static void main(String[] args) {
		BankAccount test1 = new BankAccount("134-AQW",100);
		BankAccount test2 = new BankAccount("123-ALE",5);
		BankAccount test3 = new BankAccount("WAH",1001.2);
		ArrayList<BankAccount> allBankAccounts = new ArrayList <>();
		allBankAccounts.add(test1);
		allBankAccounts.add(test2);
		allBankAccounts.add(test3);

		BankingApp finalTest = new BankingApp();
		finalTest.appControl(allBankAccounts);
	}
}