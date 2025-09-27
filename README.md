# 🚀 GJG Maven Plugin

[![Maven Central](https://img.shields.io/maven-central/v/io.github.kaffamobile.tools.maven/gjg-maven-plugin.svg)](https://search.maven.org/artifact/io.github.kaffamobile.tools.maven/gjg-maven-plugin)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Java Version](https://img.shields.io/badge/Java-8%2B-blue)](https://www.java.com)

**Maven plugin for packaging Java applications with the GJG Launcher** - a modern, reliable alternative to Launch4j that actually works with special characters in arguments.

---

## 🎯 Why GJG Maven Plugin?

If you've ever encountered Launch4j crashing with quotes or special characters in `JAVA_OPTS`, you know the pain. This plugin integrates [GJG Launcher](https://github.com/kaffamobile/gjg) - a lightweight Go-based wrapper that **just works**.

### ✨ Key Features

- ✅ **No more crashes** with special characters in arguments
- 🎨 **Windows metadata** support (icon, version info, copyright)
- 📦 **Multi-architecture** support (x64, x86, ARM64)
- 🔧 **Simple configuration** via Maven
- 🚫 **Zero runtime dependencies** - generates standalone executables
- 🐧 **Cross-platform builds** - Works on Linux/Mac with Wine

---

## 📦 Installation

Add the plugin to your `pom.xml`:

```xml
<plugin>
    <groupId>io.github.kaffamobile.tools.maven</groupId>
    <artifactId>gjg-maven-plugin</artifactId>
    <version>0.0.1</version>
    <executions>
        <execution>
            <phase>package</phase>
            <goals>
                <goal>package</goal>
            </goals>
            <configuration>
                <!-- Your configuration here -->
            </configuration>
        </execution>
    </executions>
</plugin>
```

---

## 🔧 Configuration

### Basic Example

```xml
<plugin>
    <groupId>io.github.kaffamobile.tools.maven</groupId>
    <artifactId>gjg-maven-plugin</artifactId>
    <version>0.0.1</version>
    <configuration>
        <outputFile>${project.build.directory}/${project.artifactId}.exe</outputFile>
        <jarFile>${project.artifactId}.jar</jarFile>
        <jvmArgs>
            <arg>-Xmx512m</arg>
            <arg>-Dfile.encoding=UTF-8</arg>
        </jvmArgs>
    </configuration>
</plugin>
```

### Full Configuration with All Options

```xml
<configuration>
    <!-- Output executable path (required) -->
    <outputFile>${project.build.directory}/myapp.exe</outputFile>
    
    <!-- JAR file to launch (optional, defaults to same name as exe) -->
    <jarFile>myapp.jar</jarFile>
    
    <!-- Java directory (optional, uses PATH if not specified) -->
    <javaDir>jre/jdk-17</javaDir>
    
    <!-- Architecture: amd64, 386, or arm64 (optional, defaults to amd64) -->
    <arch>amd64</arch>
    
    <!-- JVM arguments -->
    <jvmArgs>
        <arg>-Xms256m</arg>
        <arg>-Xmx2048m</arg>
        <arg>-Dmy.property=value with spaces</arg>
    </jvmArgs>
    
    <!-- Application arguments -->
    <appArgs>
        <arg>--server.port=8080</arg>
        <arg>--profile=production</arg>
    </appArgs>
    
    <!-- Environment variables -->
    <env>
        <MY_VAR>some value</MY_VAR>
        <ANOTHER_VAR>another value</ANOTHER_VAR>
    </env>
    
    <!-- Windows metadata (all optional) -->
    <icon>src/main/resources/icon.ico</icon>
    <fileDescription>My Application</fileDescription>
    <productName>MyApp Pro</productName>
    <productVersion>1.0.0.0</productVersion>
    <companyName>My Company Inc.</companyName>
    <copyright>© 2024 My Company Inc.</copyright>
</configuration>
```

---

## 🚀 Usage Examples

### Example 1: Simple Desktop Application

```xml
<configuration>
    <outputFile>${project.build.directory}/${project.artifactId}.exe</outputFile>
    <jarFile>${project.artifactId}.jar</jarFile>
    <icon>src/main/resources/app.ico</icon>
    <productName>My Desktop App</productName>
    <jvmArgs>
        <arg>-Xmx1024m</arg>
    </jvmArgs>
</configuration>
```

### Example 2: Server Application with Embedded JRE

```xml
<configuration>
    <outputFile>${project.build.directory}/server.exe</outputFile>
    <jarFile>server-all.jar</jarFile>
    <javaDir>runtime/jre-17</javaDir>
    <jvmArgs>
        <arg>-server</arg>
        <arg>-Xms512m</arg>
        <arg>-Xmx4096m</arg>
        <arg>-Dspring.profiles.active=prod</arg>
    </jvmArgs>
    <appArgs>
        <arg>--port=8080</arg>
    </appArgs>
</configuration>
```

### Example 3: Complex Enterprise Application

```xml
<configuration>
    <outputFile>${staging.dir}/${project.artifactId}.exe</outputFile>
    <jarFile>${project.artifactId}.jar</jarFile>
    <javaDir>jre/adoptium-17</javaDir>
    <arch>amd64</arch>
    
    <!-- Complex JVM configuration -->
    <jvmArgs>
        <arg>-Xms256m</arg>
        <arg>-Xmx2048m</arg>
        <arg>-XX:+UseG1GC</arg>
        <arg>-Djava.library.path=./native</arg>
        <arg>-Dfile.encoding=UTF-8</arg>
        <arg>-Djna.library.path=./libs</arg>
        <!-- This would crash Launch4j! But works fine with GJG -->
        <arg>-Dmy.path="C:\Program Files\My App"</arg>
    </jvmArgs>
    
    <!-- Windows branding -->
    <icon>branding/corporate.ico</icon>
    <fileDescription>Enterprise Resource Planning System</fileDescription>
    <productName>ERP System Professional</productName>
    <productVersion>2.5.0.0</productVersion>
    <companyName>Enterprise Corp</companyName>
    <copyright>© 2024 Enterprise Corp. All rights reserved.</copyright>
</configuration>
```

---

## 📁 Generated Files

The plugin generates two files:

1. **`myapp.exe`** - The launcher executable
2. **`myapp.gjg.conf`** - Configuration file (must be in the same directory)

### Expected Directory Structure

```
application/
├── myapp.exe           # Generated launcher
├── myapp.gjg.conf      # Generated configuration
├── myapp.jar           # Your Java application
├── libs/               # Optional: native libraries
└── jre/                # Optional: embedded JRE
    └── bin/
        └── javaw.exe
```

---

## 🔄 Migration from Launch4j

Migrating from Launch4j is straightforward:

### Launch4j Configuration
```xml
<plugin>
    <groupId>com.akathist.maven.plugins.launch4j</groupId>
    <artifactId>launch4j-maven-plugin</artifactId>
    <configuration>
        <headerType>gui</headerType>
        <jar>myapp.jar</jar>
        <outfile>myapp.exe</outfile>
        <jre>
            <path>jre</path>
            <opts>
                <opt>-Xmx1024m</opt>
                <!-- This breaks Launch4j! -->
                <opt>-Dpath="C:\Program Files"</opt>
            </opts>
        </jre>
    </configuration>
</plugin>
```

### GJG Plugin Configuration (equivalent)
```xml
<plugin>
    <groupId>io.github.kaffamobile.tools.maven</groupId>
    <artifactId>gjg-maven-plugin</artifactId>
    <configuration>
        <outputFile>myapp.exe</outputFile>
        <jarFile>myapp.jar</jarFile>
        <javaDir>jre</javaDir>
        <jvmArgs>
            <arg>-Xmx1024m</arg>
            <!-- Works perfectly! -->
            <arg>-Dpath="C:\Program Files"</arg>
        </jvmArgs>
    </configuration>
</plugin>
```

---

## 🐧 Cross-Platform Builds

The plugin works on Linux and macOS for cross-compilation to Windows:

### Requirements for Linux/Mac

- **Wine** must be installed (for setting Windows metadata)
- Install Wine:
  ```bash
  # Ubuntu/Debian
  sudo apt-get install wine
  
  # macOS
  brew install wine-stable
  ```

### Verification

```bash
# Check if Wine is available
wine --version
```

If Wine is not available, the plugin will still generate the executable but without icon and metadata.

---

## 🐛 Troubleshooting

### Issue: "Icon file not found"

The plugin searches for the icon file in multiple locations. Make sure your icon path is either:
- Absolute path: `/full/path/to/icon.ico`
- Relative to project base directory: `src/main/resources/icon.ico`
- Relative to current directory: `./icon.ico`

### Issue: "Java executable not found"

If `javaDir` is not specified, ensure Java is in your system PATH:
```bash
java -version
javaw -version  # Windows only
```

### Issue: Executable doesn't start

Run with debug mode to see what's happening:
```bash
myapp.exe --gjg-debug
```

Check the generated `.gjg.conf` file to ensure paths are correct.

---

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 🙏 Acknowledgments

- [GJG Launcher](https://github.com/kaffamobile/gjg) - The Go-based launcher that powers this plugin
- [rcedit](https://github.com/electron/rcedit) - For Windows executable resource editing
- [Launch4j](http://launch4j.sourceforge.net/) - For the inspiration (and the bugs that motivated this project)
