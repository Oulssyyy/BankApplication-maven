# Exercise 9: Code Coverage Analysis with JaCoCo

## Objective
Increase test coverage by identifying low-coverage classes, writing tests for uncovered methods, and verifying coverage improvements using JaCoCo.

## Initial Setup

### 1. Added JaCoCo Plugin to pom.xml
```xml
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.11</version>
    <executions>
        <execution>
            <goals>
                <goal>prepare-agent</goal>
            </goals>
        </execution>
        <execution>
            <id>report</id>
            <phase>test</phase>
            <goals>
                <goal>report</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

### 2. Generated Initial Coverage Report
Command: `mvn clean test`

The JaCoCo report is generated at: `target/site/jacoco/index.html`

## Initial Coverage Analysis (Before)

| Class | Instruction Coverage | Branch Coverage | Lines Covered | Methods Covered | Status |
|-------|---------------------|-----------------|---------------|-----------------|--------|
| **BankAccountApp** | 0% | 0% | 0/134 | 0/2 | ❌ No tests |
| **Bank** | 0% | 0% | 0/93 | 0/14 | ❌ No tests |
| **BankAccount** | 26% | 75% | 25/90 | 6/21 | ⚠️ Partial |
| **Person** | 8% | 0% | 9/80 | 1/23 | ❌ Minimal |
| **ACHServiceImpl** | 0% | n/a | 0/3 | 0/3 | ❌ No tests |
| **Overall Project** | **8%** | **8%** | **34/400** | **7/63** | ❌ Very Low |

### Target for Improvement
**Bank class** was selected because:
- 0% coverage (significantly below 50% threshold)
- 14 methods available for testing
- Core business logic for managing multiple accounts
- Good opportunity for meaningful coverage increase

## Test Development

### Created BankTest.java with 18 Unit Tests

#### Test Categories:

1. **Account Management (7 tests)**
   - `testAddAccount_NewAccount`: Verifies new account creation with account number assignment
   - `testAddAccount_LoadExisting`: Tests loading pre-existing accounts (isLoadAccount=1)
   - `testAddAccount_MultipleAccounts`: Validates sequential account number generation
   - `testFindAccount_ExistingAccount`: Tests finding account by account number
   - `testFindAccount_NonExistingAccount`: Verifies null return for missing accounts
   - `testDeleteAccount`: Tests account deletion
   - `testDeleteAccount_NonExisting`: Ensures graceful handling of deleting non-existent accounts

2. **Statistical Operations (6 tests)**
   - `testGetAverageBalance_SingleAccount`: Average calculation with one account
   - `testGetAverageBalance_MultipleAccounts`: Average of multiple account balances
   - `testGetMaximumBalance`: Finds highest balance across accounts
   - `testGetMaximumBalance_SingleAccount`: Maximum with single account
   - `testGetMinimumBalance`: Finds lowest balance across accounts
   - `testGetMinimumBalance_SingleAccount`: Minimum with single account

3. **Data Access & Operations (5 tests)**
   - `testGetAccounts`: Retrieves all accounts in the bank
   - `testGetAccounts_EmptyBank`: Validates empty list for new bank
   - `testRegisterAccount`: Tests ACH registration
   - `testTransferAmount`: Tests ACH transfer operation
   - `testSetAndGetAccountsLoaded`: Validates account counter management

## Test Execution Results

### Command
```bash
mvn clean test
```

### Output
```
Tests run: 29, Failures: 0, Errors: 0, Skipped: 0
- BankAccountTest: 11 tests (previously existing)
- BankTest: 18 tests (newly added)
```

**All tests passed successfully! ✅**

## Final Coverage Analysis (After)

| Class | Instruction Coverage | Branch Coverage | Lines Covered | Methods Covered | Change |
|-------|---------------------|-----------------|---------------|-----------------|--------|
| **BankAccountApp** | 0% | 0% | 0/134 | 0/2 | No change |
| **Bank** | **66%** ⬆️ | **72%** ⬆️ | **63/93** ⬆️ | **12/14** ⬆️ | **+66%** 🎯 |
| **BankAccount** | 27% | 75% | 28/90 | 8/21 | +1% |
| **Person** | 8% | 0% | 9/80 | 1/23 | No change |
| **ACHServiceImpl** | 71% | n/a | 2/3 | 2/3 | +71% |
| **Overall Project** | **25%** ⬆️ | **25%** ⬆️ | **102/400** ⬆️ | **23/63** ⬆️ | **+17%** |

### Key Improvements

1. **Bank class**: 0% → **66%** (+66 percentage points)
   - Instruction coverage increased from 0 to 243 covered instructions
   - Branch coverage: 0% → 72% (26 of 36 branches covered)
   - Methods covered: 0 → 12 out of 14 methods
   - Lines covered: 0 → 63 out of 93 lines

2. **ACHServiceImpl**: Indirectly tested through Bank's transferAmount method
   - Coverage increased from 0% to 71%

3. **Overall project**: 8% → **25%** (+17 percentage points)
   - Total tests increased from 11 to 29 tests
   - Lines covered: 34 → 102 (+68 lines)
   - Methods covered: 7 → 23 (+16 methods)

## Uncovered Methods in Bank Class

Even after our tests, 2 methods remain at 0% coverage:

1. **`saveAccounts(Bank accManager)`**: File I/O operation - requires file system mocking
2. **`convertToText()`**: Serialization method - needs integration with file operations

These methods involve file system operations which are typically tested through integration tests rather than unit tests.

## Lessons Learned

1. **JaCoCo Integration**: Seamlessly integrated with Maven's test phase
2. **Coverage Metrics**: Instruction and branch coverage provide different insights
3. **Test Strategy**: Focus on core business logic first (Bank operations)
4. **Constructor Patterns**: Required understanding actual constructors:
   - `Person(String name, char gender, int age, float height)`
   - `BankAccount(double balance, double withdrawLimit, String dateCreated, Person holder)`
5. **Private Methods**: Cannot test `setBalance()` directly; used constructor-set values instead
6. **Indirect Coverage**: Testing Bank methods also increased ACHServiceImpl coverage

## Verification

To view the coverage report:
```bash
# Open in browser
start target\site\jacoco\index.html

# Or navigate to specific class report
start target\site\jacoco\bankAccountApp\Bank.html
```

## Next Steps (Future Improvements)

1. Increase BankAccount coverage from 27% to >80% by testing remaining methods
2. Add tests for Person class (currently 8% coverage)
3. Create integration tests for file I/O operations (saveAccounts, convertToText)
4. Consider adding tests for BankAccountApp (main application class)
5. Target overall project coverage of >80% for production readiness

## Conclusion

✅ **Exercise 9 Completed Successfully**

- **Goal**: Increase coverage for a class with <50% coverage
- **Result**: Bank class improved from 0% to 66% coverage
- **Method**: Added 18 comprehensive unit tests covering core banking operations
- **Impact**: Overall project coverage increased from 8% to 25%
- **Verification**: All 29 tests pass with JaCoCo reporting enabled
