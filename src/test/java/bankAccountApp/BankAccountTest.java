package bankAccountApp;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for BankAccount class
 * Testing deposit and withdraw business logic
 */
public class BankAccountTest {
    
    private BankAccount account;
    private Person accountHolder;
    
    @Before
    public void setUp() throws Exception {
        // Create a person for the account holder
        accountHolder = new Person("John Doe", 'M', 30, 175.0f);
        
        // Create a bank account with initial balance 1000 and withdraw limit 500
        account = new BankAccount(1000.0, 500.0, "2026-01-16", accountHolder);
    }
    
    // ===== DEPOSIT TESTS =====
    
    @Test
    public void testDepositMoney_HappyPath() {
        // Given: Account with balance 1000
        double initialBalance = account.getBalance();
        
        // When: Deposit 100
        account.depositMoney(100.0);
        
        // Then: Balance should increase by 100
        assertEquals("Balance should increase by deposit amount", 
                     initialBalance + 100.0, account.getBalance(), 0.01);
    }
    
    @Test
    public void testDepositMoney_MultipleDeposits() {
        // Given: Account with balance 1000
        double initialBalance = account.getBalance();
        
        // When: Multiple deposits
        account.depositMoney(50.0);
        account.depositMoney(75.0);
        account.depositMoney(25.0);
        
        // Then: Balance should reflect all deposits
        assertEquals("Balance should increase by total deposits", 
                     initialBalance + 150.0, account.getBalance(), 0.01);
    }
    
    @Test
    public void testDepositMoney_NegativeAmount() {
        // Given: Account with balance 1000
        double initialBalance = account.getBalance();
        
        // When: Attempt to deposit negative amount
        account.depositMoney(-50.0);
        
        // Then: Balance should remain unchanged (negative deposits rejected)
        assertEquals("Balance should not change for negative deposit", 
                     initialBalance, account.getBalance(), 0.01);
    }
    
    @Test
    public void testDepositMoney_Zero() {
        // Given: Account with balance 1000
        double initialBalance = account.getBalance();
        
        // When: Deposit zero
        account.depositMoney(0.0);
        
        // Then: Balance should remain unchanged
        assertEquals("Balance should not change for zero deposit", 
                     initialBalance, account.getBalance(), 0.01);
    }
    
    // ===== WITHDRAW TESTS =====
    
    @Test
    public void testWithdrawMoney_HappyPath() {
        // Given: Account with balance 1000 and withdraw limit 500
        double initialBalance = account.getBalance();
        
        // When: Withdraw 100 (within limit and balance)
        boolean result = account.withdrawMoney(100.0);
        
        // Then: Withdrawal succeeds and balance decreases
        assertTrue("Withdrawal should succeed", result);
        assertEquals("Balance should decrease by withdrawal amount", 
                     initialBalance - 100.0, account.getBalance(), 0.01);
        assertEquals("Amount withdrawn should be tracked", 
                     100.0, account.getAmountWithdrawn(), 0.01);
    }
    
    @Test
    public void testWithdrawMoney_ExceedsBalance() {
        // Given: Account with balance 1000
        double initialBalance = account.getBalance();
        
        // When: Attempt to withdraw more than balance
        boolean result = account.withdrawMoney(1500.0);
        
        // Then: Withdrawal fails and balance unchanged
        assertFalse("Withdrawal should fail when exceeding balance", result);
        assertEquals("Balance should remain unchanged", 
                     initialBalance, account.getBalance(), 0.01);
    }
    
    @Test
    public void testWithdrawMoney_ExceedsWithdrawLimit() {
        // Given: Account with withdraw limit 500
        double initialBalance = account.getBalance();
        
        // When: Attempt to withdraw more than limit (but less than balance)
        boolean result = account.withdrawMoney(600.0);
        
        // Then: Withdrawal fails and balance unchanged
        assertFalse("Withdrawal should fail when exceeding withdraw limit", result);
        assertEquals("Balance should remain unchanged", 
                     initialBalance, account.getBalance(), 0.01);
    }
    
    @Test
    public void testWithdrawMoney_ExceedsDailyLimit() {
        // Given: Account with withdraw limit 500, already withdrawn 300
        account.withdrawMoney(300.0);
        double balanceAfterFirst = account.getBalance();
        
        // When: Attempt second withdrawal that would exceed daily limit
        boolean result = account.withdrawMoney(250.0);
        
        // Then: Withdrawal fails and balance unchanged from previous
        assertFalse("Withdrawal should fail when exceeding daily limit", result);
        assertEquals("Balance should remain unchanged after failed withdrawal", 
                     balanceAfterFirst, account.getBalance(), 0.01);
        assertEquals("Amount withdrawn should not increase", 
                     300.0, account.getAmountWithdrawn(), 0.01);
    }
    
    @Test
    public void testWithdrawMoney_NegativeAmount() {
        // Given: Account with balance 1000
        double initialBalance = account.getBalance();
        
        // When: Attempt to withdraw negative amount
        boolean result = account.withdrawMoney(-50.0);
        
        // Then: Withdrawal fails and balance unchanged
        assertFalse("Withdrawal should fail for negative amount", result);
        assertEquals("Balance should remain unchanged", 
                     initialBalance, account.getBalance(), 0.01);
    }
    
    @Test
    public void testWithdrawMoney_AtExactLimit() {
        // Given: Account with withdraw limit 500
        double initialBalance = account.getBalance();
        
        // When: Withdraw exactly at limit (but implementation checks < not <=)
        boolean result = account.withdrawMoney(500.0);
        
        // Then: Based on code, this should fail (withdrawAmount < withdrawLimit)
        assertFalse("Withdrawal at exact limit should fail due to < check", result);
        assertEquals("Balance should remain unchanged", 
                     initialBalance, account.getBalance(), 0.01);
    }
    
    @Test
    public void testWithdrawMoney_JustBelowLimit() {
        // Given: Account with withdraw limit 500
        double initialBalance = account.getBalance();
        
        // When: Withdraw just below limit
        boolean result = account.withdrawMoney(499.0);
        
        // Then: Withdrawal should succeed
        assertTrue("Withdrawal below limit should succeed", result);
        assertEquals("Balance should decrease", 
                     initialBalance - 499.0, account.getBalance(), 0.01);
    }
}
