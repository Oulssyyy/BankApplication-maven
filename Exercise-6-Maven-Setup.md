# Exercise 6: Turn BankApplication into a Maven Project

## Repository: BankApplication-maven

### Maven Project Configuration

**Project Coordinates:**
```xml
<groupId>com.imt.mines</groupId>
<artifactId>bank-application</artifactId>
<version>1.0-SNAPSHOT</version>
```

### Project Structure Created

```
BankApplication-maven/
├── pom.xml
└── src/
    └── main/
        └── java/
            └── bankAccountApp/
                ├── ACHService.java
                ├── ACHServiceImpl.java
                ├── Bank.java
                ├── BankAccount.java
                ├── BankAccountApp.java
                └── Person.java
```

### Source Files Copied

✓ **6 Java files** copied from `BankApplication/jay-bank/src/main/java/bankAccountApp/`:
- ACHService.java (722 bytes)
- ACHServiceImpl.java (498 bytes)
- Bank.java (5,090 bytes)
- BankAccount.java (5,716 bytes)
- BankAccountApp.java (8,086 bytes)
- Person.java (5,834 bytes)

### Compilation Result

**Command:** `mvn compile`

**Result:** ✓ BUILD SUCCESS

**Details:**
- Compiled 6 source files with javac
- Target: Java 1.8
- Output: `target/classes/bankAccountApp/*.class`
- Build time: 1.802s
- No errors

**Warning (non-critical):**
- Bootstrap class path not set (expected for Java 8 compilation on newer JDK)

### Verification

The Maven project has been successfully created with:
- ✓ Proper Maven coordinates (com.imt.mines:bank-application:1.0-SNAPSHOT)
- ✓ Standard Maven directory structure
- ✓ All main source files copied
- ✓ Clean compilation with no errors
- ✓ Generated .class files in target/classes
