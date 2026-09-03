# WhatsApp Analyzer

A Java Swing application that analyzes exported WhatsApp chat logs (`.txt` format) and generates analytics such as:

- **Messages per sender**
- **Word usage per sender**
- **Words per Message per sender**
- **Time and Weekday of chat activity**
- **Use of chosen words**

Charts are rendered using [JFreeChart](https://www.jfree.org/jfreechart/).

---

## 🚀 Features
- File selector to choose your exported WhatsApp `.txt` file
- Interactive charts for messages and word counts
- Built with **Maven** for easy dependency management

---

## 🛠 Requirements
- Java 11 or later (JDK)
- Maven 3.x

---

## 📦 Build

Clone the repository:

```bash
https://github.com/Jan133-ai/whatsApp-analyzer.git
cd whatsapp-analyzer
```

Compile and package the project:

```bash
mvn clean package
```

This will create a JAR file in the target/ directory.

---

## ▶️ Run

Run the app with Maven:

```bash
mvn exec:java -Dexec.mainClass="Gui"
```

---

## 📜 License

This project is licensed under the MIT License
