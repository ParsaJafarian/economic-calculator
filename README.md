#Compound Interest Calculator JavaFX Project

##How to run the application 

1. `cd {your directory}`
2. `git clone {repo_url}`
3. Run main.java file in your IDE of choice

##Introduction

This app has the main goal of calculating the compound growth of the user's money. For instance, a user enters 10000$ as a starting investment and 
wants to see how much this investment would be after 10 years with an interest rate of 5%. Moreover, this program displays the growth of the investment
every year through two illustrations:

* Table 
* Graph

The user can then export these illustrations into an excel file. 

##Program Structure

The program is organized into the following classes

* Controller
* FormUtils
* Table
* Graph 
* MenuBarUtils
* Row

The Controller class is the intermediate communicator between the index.fxml and the other classes. 
Note that any Class ending with Utils denotes a utility class that can never be instantiated. These classes are only used to organize certain methods.
In the next parts, a brief overview of each class will be given.

###Table Class

The table class contains two attributes: a TableView and a Pagination. Thanks to the Table class, the tableView and pagination can interact with each other 
without being accessible to outside classes such as Controller. The Row class is declared under the Table class because a table is made of multiple rows.

###FormUtils

FormUtils contains all methods that validate or invalidate the Form section of the program (the section where the  user enters their input).

###MenuBarUtils

MenuBarUtils initializes the menu bar of the program. This class is important because it gives the option to the user to export the table and graph to excel.
Thus, there is communication between a Table & Graph with this class. 

###Graph

//TODO

###UML Diagram

##How git was Used

This git repository is split into two branches: main & parsa. The parsa branch is named after one of the developers of this project (Parsa Jafarian). 
This naming convention is used to determine which branch is assigned to which team member. Mark Rudko, the other developer, at the time of the project development, 
was unfamiliar with git commands and branching. Thus, Parsa split off a branch where he would work on his parts alone and leave Mark onto his duties on main. 
We didn't use pull requests because it would've been taken some time to teach it to Mark. In addition, this is a small team made of two people who communicate
with each other on a day to day basis. 
