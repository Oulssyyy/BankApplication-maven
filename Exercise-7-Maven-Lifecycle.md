# Exercise 7: Maven Lifecycle and Test Dependencies

## Test Dependencies Added

Added to `pom.xml`:
```xml
<dependencies>
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.12</version>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.mockito</groupId>
        <artifactId>mockito-core</artifactId>
        <version>3.2.0</version>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.hamcrest</groupId>
        <artifactId>hamcrest-library</artifactId>
        <version>1.3</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```

## Maven Lifecycle Commands Analysis

### 1. `mvn clean`

**Phases Executed:**
- `clean:clean` (default-clean)

**Actions:**
- Deletes the entire `target/` directory

**Files/Folders After Execution:**
- `target/` - **DELETED** (no longer exists)

**Purpose:** Removes all build artifacts to ensure a fresh build

---

### 2. `mvn test`

**Phases Executed (in order):**
1. `resources:resources` (default-resources)
2. `compiler:compile` (default-compile)
3. `resources:testResources` (default-testResources)
4. `compiler:testCompile` (default-testCompile)
5. `surefire:test` (default-test)

**Actions:**
- Compiled 6 source files to `target/classes`
- Attempted to compile test sources (none found)
- Ran tests (none found, no tests to run)

**New Files/Folders in target/:**
```
target/
├── classes/               (compiled .class files)
├── generated-sources/     (empty)
└── maven-status/         (build tracking metadata)
```

**Result:** BUILD SUCCESS, but no tests executed (no test sources exist yet)

---

### 3. `mvn package`

**Phases Executed (in order):**
1. `resources:resources` (default-resources)
2. `compiler:compile` (default-compile) - nothing to compile (up to date)
3. `resources:testResources` (default-testResources)
4. `compiler:testCompile` (default-testCompile)
5. `surefire:test` (default-test)
6. `jar:jar` (default-jar) - **NEW PHASE**

**Actions:**
- Used cached compiled classes
- Created JAR file containing all compiled classes

**New Files/Folders in target/:**
```
target/
├── classes/
├── generated-sources/
├── maven-archiver/              (NEW - JAR metadata)
├── maven-status/
└── bank-application-1.0-SNAPSHOT.jar  (NEW - 14,218 bytes)
```

**Result:** BUILD SUCCESS, JAR created successfully

---

### 4. `mvn verify`

**Phases Executed (in order):**
1. `resources:resources` (default-resources)
2. `compiler:compile` (default-compile)
3. `resources:testResources` (default-testResources)
4. `compiler:testCompile` (default-testCompile)
5. `surefire:test` (default-test)
6. `jar:jar` (default-jar)

**Actions:**
- Same as `mvn package` (no integration tests configured)
- Would run integration tests if present
- Would verify package integrity

**Files/Folders:**
- Same as `mvn package` (no additional files created)

**Result:** BUILD SUCCESS

---

## Hypothesis: How is `verify` different from `test` and `package`?

### Maven Build Lifecycle Phases (in order):
1. validate
2. compile
3. **test** ← Runs unit tests
4. **package** ← Creates JAR/WAR
5. integration-test
6. **verify** ← Runs integration tests & checks
7. install
8. deploy

### Differences:

**`mvn test`:**
- Stops after running **unit tests**
- Does NOT create JAR
- Purpose: Quick feedback for unit testing during development

**`mvn package`:**
- Runs unit tests THEN creates the **JAR/WAR file**
- Stops before integration tests
- Purpose: Build deployable artifact

**`mvn verify`:**
- Runs everything in `package` PLUS **integration tests** and **quality checks**
- Verifies the package is valid and meets quality standards
- Purpose: Full verification before install/deploy

### Observed Behavior:
In our case, `verify` behaved identically to `package` because:
- No integration tests are configured
- No quality check plugins (e.g., failsafe, checkstyle) are configured

### When They Differ:
`verify` becomes different when you add:
- Integration tests (using maven-failsafe-plugin)
- Code coverage checks
- Static analysis tools
- Package integrity validations

**Summary:** `verify` is the most comprehensive pre-deployment check, `package` creates the artifact, and `test` provides fast feedback during development.
