import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;


public class BankAccount {

	private String accountNumber;
	private double accountValue;
	private Scanner kb = new Scanner(System.in);
	
	public BankAccount(String actNum, double initialDeposit) {
		accountNumber = actNum;
		accountValue = initialDeposit; 
	}
	
	public BankAccount() {
		this("123-ABC",0.0);
	}

	
	public void transferMoney(ArrayList<BankAccount> bankAcct){
		System.out.println("Please enter the other account number.");
		String acctNum; 
		int acctIdx = -1;
		while (acctIdx < 0) {
			acctNum = kb.nextLine(); 
			if (acctNum == "") // Deals w/ loose newline in input stream and user hitting enter
				continue;
			acctIdx = findAcct(bankAcct,acctNum);
			if (acctIdx < 0)
				System.out.println("We could not locate this account number. Please reenter.");
		}
		
		
		System.out.println("Do you wish to: \n1. Transfer to an account\n2. Transfer from account");
		int choice = 0;
		boolean badChoice = ((choice != 1) && (choice != 2));
		while (badChoice) {
			choice = readInt();
			badChoice = ((choice != 1) && (choice != 2));
			if (!badChoice)
				break;
			else 
				System.out.println("You must enter '1' or '2'");
			
		}
		
		BankAccount otherAccount = bankAcct.get(acctIdx);
		
		double transferAmt;
		if (choice == 1) {
			System.out.println("How much money would you like transferred?");
			transferAmt = readNumber();
			while (transferAmt > accountValue) {
				System.out.println("Insufficient funds. Please reenter");
				transferAmt = readNumber();
			}
			transferHub(otherAccount,transferAmt,"to");
		}
		else {
			System.out.println("How much money would you like transferred?");
			transferAmt = readNumber();
			while (transferAmt > otherAccount.accountValue) {
				System.out.println("Insufficient funds. Please reenter");
				transferAmt = readNumber();
			}
			transferHub(otherAccount,transferAmt,"from");
		}
	}
	
	/**
	 * This method returns the index of the bank account with the corresponding acctNum
	 * If the acctNum is not found it returns -1 
	 * @param bankAcct
	 * @param acctNum
	 * @return
	 */
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

	private void transferHub(BankAccount otherAct, double amt, String path) {
		if (path.equalsIgnoreCase("to")) {
			otherAct.accountValue += amt;
			this.accountValue -= amt;
		}
		else {
			otherAct.accountValue -= amt;
			this.accountValue += amt;
		}
			
	}

	public void withdrawMoney() {
		System.out.print("Please enter the amount you'd like to withdraw: ");
		double value = readNumber();
		while (value > accountValue) {
			System.out.println(dollarFormat(value) + " is larger than your account balance: " 
					+ dollarFormat(accountValue));
			System.out.println("Please re-enter the amount to withdraw.");
			value = readNumber();
		}
		accountValue -= value;
	}
	
	public void depositMoney() {
		System.out.print("Please enter the amount to deposit: ");
		double value = readNumber();
		accountValue += value;
	}
	
	public void checkBalance() {
		System.out.println(accountNumber + " your balance is " + dollarFormat(accountValue));
	}
	
	public boolean checkAccountNumber(String account) {
		//Tested
		return account.equals(accountNumber);
	}
	
	public double getAccountValue() {
		return accountValue;
	}
	
	public String getAccountNumber() {
		return accountNumber;
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
	
	private static String dollarFormat(double amt) {
		int dollars = (int) amt ;
		int cents = (int) Math.round((amt % dollars)/0.01);
		String stringCent;
		if (cents >= 100) {
			dollars++;
			cents-=100;
		}
		if (cents < 10)
			stringCent = ".0" + cents;
		else
			stringCent = "." + cents;
		String dols = "$ " + dollars + stringCent;
		return dols;
	}
	
	public static void main(String[] args) {
		BankAccount test1 = new BankAccount("134-AQW",100);
		BankAccount test2 = new BankAccount("123-ALE",5);
		BankAccount test3 = new BankAccount("WAH",1001.2);
		ArrayList<BankAccount> allBankAccounts = new ArrayList <>();
		allBankAccounts.add(test1);
		allBankAccounts.add(test2);
		allBankAccounts.add(test3);
		test1.transferMoney(allBankAccounts);
		for (int i = 0; i < allBankAccounts.size(); i++) {
			allBankAccounts.get(i).checkBalance();
		}
	}

}

