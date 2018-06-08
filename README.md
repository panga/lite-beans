# LiteBeans Project

**LiteBeans** is implementation of the `java.beans` package based on the [Apache Harmony](https://github.com/apache/harmony) project.

## Problem

The [java.desktop](https://docs.oracle.com/javase/9/docs/api/java.desktop-summary.html) module introduced in JDK 9 encapsulates all AWT, Swing, Image and Sound packages from Java standard libraries.

In addition to that, it contains the `java.beans` package with helper classes to interact with *Java Beans*, specifically bean introspection and property change listeners.

Due to its tight dependency with AWT and Swing, it cannot be easily removed from `java.desktop` module, causing any thirdparty library that uses `java.beans` package to be dependent of all `java.desktop` classes.

The extra overhead of `java.desktop` module adds `12mb` of size into the JVM installation and increases memory usage.

## Solution

Implemented `java.beans` package with only `java.base` module dependency based on [Apache Harmony](https://github.com/apache/harmony) project source code.

The implementation is a subset of official JDK `java.beans` classes, it does not implement the full interface.

However, it adds enough classes to be used by Object Mapping (xml, json) libraries and Dependency Injection (Spring, CDI) frameworks in a transparent way.

The total size of **LiteBeans** library is less than `150kb`.

## Usage

### Using as a `java.beans` alternative

1. Add Maven Dependency

```xml
<dependency>
    <groupId>com.github.panga</groupId>
    <artifactId>lite-beans</artifactId>
    <version>1.0.0</version>
</dependency>
```

2. Find all ocurrencies of `import java.beans` and replace with `import lite.beans`.

### Using as a module patch (hacking JPMS)

1. Add Maven Dependency or download the JAR (notice the classifier)

```xml
<dependency>
    <groupId>com.github.panga</groupId>
    <artifactId>lite-beans</artifactId>
    <version>1.0.0</version>
    <classifier>patch</classifier>
</dependency>
```

2. Run your application in a minimal JRE without `java.desktop` module:

```bash
java \
    --patch-module java.base=lite-beans-1.0.0-patch.jar \
    --add-exports java.base/java.beans=acme.myapp \
    --module-path target/modules --module acme.myapp \
```

*Note: See usage in https://github.com/panga/hammock-jpms example.*

## Contributors

* Leonardo Zanivan <panga@apache.org>

## License

[Apache License 2.0](LICENSE.txt)