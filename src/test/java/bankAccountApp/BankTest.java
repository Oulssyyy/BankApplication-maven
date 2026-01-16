package bankAccountApp;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

/**
 * Unit tests for Bank class
 * Increases code coverage by testing core banking operations
 */
public class BankTest {
    
    private Bank bank;
    private Person person1;
    private Person person2;
    private BankAccount account1;
    private BankAccount account2;
    
    @Before
    public void setUp() {
        bank = new Bank();
        person1 = new Person("John", 'M', 25, 70.0f);
        person2 = new Person("Jane", 'F', 30, 65.0f);
        account1 = new BankAccount(1000.0, 500.0, "2024-01-01", person1);
        account2 = new BankAccount(2000.0, 500.0, "2024-01-02", person2);
    }
    
    @Test
    public void testAddAccount_NewAccount() {
        int accountNumber = bank.addAccount(account1, 0);
        assertEquals("Account number should be 1", 1, accountNumber);
        assertEquals("Accounts loaded should be 1", 1, bank.getAccountsLoaded());
    }
    
    @Test
    public void testAddAccount_LoadExisting() {
        int result = bank.addAccount(account1, 1);
        assertEquals("Should return 1 for loaded account", 1, result);
        assertEquals("Accounts loaded should be 1", 1, bank.getAccountsLoaded());
    }
    
    @Test
    public void testAddAccount_MultipleAccounts() {
        bank.addAccount(account1, 0);
        int secondAccountNumber = bank.addAccount(account2, 0);
        assertEquals("Second account should have number 2", 2, secondAccountNumber);
        assertEquals("Should have 2 accounts loaded", 2, bank.getAccountsLoaded());
    }
    
    @Test
    public void testFindAccount_ExistingAccount() {
        int accountNumber = bank.addAccount(account1, 0);
        BankAccount found = bank.findAccount(accountNumber);
        assertNotNull("Should find the account", found);
        assertEquals("Should return correct account", account1, found);
    }
    
    @Test
    public void testFindAccount_NonExistingAccount() {
        bank.addAccount(account1, 0);
        BankAccount found = bank.findAccount(999);
        assertNull("Should not find non-existing account", found);
    }
    
    @Test
    public void testDeleteAccount() {
        int accountNumber = bank.addAccount(account1, 0);
        bank.deleteAccount(accountNumber);
        BankAccount found = bank.findAccount(accountNumber);
        assertNull("Account should be deleted", found);
    }
    
    @Test
    public void testDeleteAccount_NonExisting() {
        bank.addAccount(account1, 0);
        int initialCount = bank.getAccountsLoaded();
        bank.deleteAccount(999); // Try to delete non-existing
        // Should not throw exception, should handle gracefully
        assertEquals("Account count should remain same", initialCount, bank.getAccountsLoaded());
    }
    
    @Test
    public void testGetAverageBalance_SingleAccount() {
        bank.addAccount(account1, 0);
        double average = bank.getAverageBalance();
        assertEquals("Average should equal single account balance", 1000.0, average, 0.01);
    }
    
    @Test
    public void testGetAverageBalance_MultipleAccounts() {
        bank.addAccount(account1, 0);
        bank.addAccount(account2, 0);
        double average = bank.getAverageBalance();
        assertEquals("Average of 1000 and 2000 should be 1500", 1500.0, average, 0.01);
    }
    
    @Test
    public void testGetMaximumBalance() {
        BankAccount account3 = new BankAccount(2500.0, 500.0, "2024-01-03", person1);
        bank.addAccount(account1, 0);
        bank.addAccount(account3, 0);
        double max = bank.getMaximumBalance();
        assertEquals("Maximum should be 2500", 2500.0, max, 0.01);
    }
    
    @Test
    public void testGetMaximumBalance_SingleAccount() {
        bank.addAccount(account1, 0);
        double max = bank.getMaximumBalance();
        assertEquals("Maximum should be 1000", 1000.0, max, 0.01);
    }
    
    @Test
    public void testGetMinimumBalance() {
        BankAccount account3 = new BankAccount(500.0, 500.0, "2024-01-03", person1);
        bank.addAccount(account1, 0);
        bank.addAccount(account3, 0);
        double min = bank.getMinimumBalance();
        assertEquals("Minimum should be 500", 500.0, min, 0.01);
    }
    
    @Test
    public void testGetMinimumBalance_SingleAccount() {
        bank.addAccount(account1, 0);
        double min = bank.getMinimumBalance();
        assertEquals("Minimum should be 1000", 1000.0, min, 0.01);
    }
    
    @Test
    public void testGetAccounts() {
        bank.addAccount(account1, 0);
        bank.addAccount(account2, 0);
        ArrayList<BankAccount> accounts = bank.getAccounts();
        assertNotNull("Accounts list should not be null", accounts);
        assertEquals("Should have 2 accounts", 2, accounts.size());
        assertTrue("Should contain account1", accounts.contains(account1));
        assertTrue("Should contain account2", accounts.contains(account2));
    }
    
    @Test
    public void testGetAccounts_EmptyBank() {
        ArrayList<BankAccount> accounts = bank.getAccounts();
        assertNotNull("Accounts list should not be null", accounts);
        assertEquals("Should have 0 accounts", 0, accounts.size());
    }
    
    @Test
    public void testRegisterAccount() {
        boolean result = bank.registerAccount(123, 456, 789, 101);
        assertTrue("Register account should return true", result);
    }
    
    @Test
    public void testTransferAmount() {
        boolean result = bank.transferAmount(123, 456, 789, 101, 100.0f);
        assertTrue("Transfer amount should return true", result);
    }
    
    @Test
    public void testSetAndGetAccountsLoaded() {
        bank.setAccountsLoaded(5);
        assertEquals("Should return set value", 5, bank.getAccountsLoaded());
    }
}
