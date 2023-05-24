# About application

This application allows user to add new bank users, create two types of bank accounts, perform money deposit withdrawal and perform money transfers. The application has simple command line interface. Type a number to perform an acction.

Uses Java 8, Maven, Maven Jar Plugin

![obraz](https://user-images.githubusercontent.com/87314459/203964323-9712c9ea-7e7f-4a30-84f2-682a7d745aba.png)

# Available functions

1 - Show list of clients  
2 - Add/remove a client  
3 - Show list of bank accounts  
4 - Add/remove bank account  
5 - Transaction history  
6 - Deposit money  
7 - Withdraw money  
8 - Perform money transfer  
9 - Serach engine  
10 - Save/load from file  
11 - Exit  

# How to run application

Using any command line interface perform tasks listed below.

1. Clone the repository  
2. Compile the project using Maven  
```
mvn compile
```
3. Package the project using Maven  
```
mvn clean package
```
4. Run the Jar file  
```
java -jar .\target\Bank-1.0-SNAPSHOT.jar
```

# Application extensions

I created two more applicationg which are using mostly unchanged backend and implement UI

## UI with Lanterna library

Github link to the project
```
https://github.com/DawidPerkowskiGit/DP_KCK_BankAPP
```

## Android application

Github link to the project
```
https://github.com/DawidPerkowskiGit/BankAPP_Android
```
