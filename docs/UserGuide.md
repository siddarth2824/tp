---
layout: page
title: User Guide
---

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------
## 1. Introduction

### 1.1 About Fine$$e
Welcome to Fine$$e!

Looking for an all-in-one solution to help you develop good financial habits? Look no further!

Fine$$e is an integrated platform fully customized for tertiary and university students with the aim of helping you track your finances effectively.
Fine$$e allows you to keep track of your incomes, expenses and savings with a few simple commands.
Furthermore, to help you cultivate good financial habits Fine$$e allows you to set expense limits and savings goals.

All your important information will be displayed on our sleek Graphical User Interface (GUI).
Fine$$e is optimized for users who are fast typists and are comfortable working on the Command Line Interface (CLI).

If you want to better manage your finances and at the same time increase your savings by cultivating good financial habits, then look no further as Fine\$\$e is definitely for you.

Explore our User Guide to find out more about Fine\$\$e’s amazing features.

### 1.2 Navigating the User Guide
The aim of the User Guide is to provide you with all of the necessary information required for you to utilize Fine$$e.
We understand that a Command Line Interface (CLI) might not be the easiest to use, hence we have ensured that the information provided is concise and easily readable.

If you require help on setting up Fine$$e, you can go to the [Section 2. "Quick Start"](#2-quick-start) section.

If you want to find out more about the various features that Fine$$s has to offer, you can go to [Section 4. "Features"](#4-features) section.

If you want to have an overview of Fine$$e's commands, you can visit the [Section 5. "Command Summary"](#5-command-summary) section.

Do take note of the following symbols and formatting used throughout this document:

[Table showing the various symbol/formats that we will be using]

--------------------------------------------------------------------------------------------------------------------
## 2. Quick start
This section will show you the various components that make up Fine$$e's user interface.
You can also follow our step-by-step guide on how to install Fine$$e and get it to work on your computer.
Let's get started!

### 2.1 Layout of Fine$$e's Interface

[To be added]

### 2.2 Installation

1. Ensure you have Java `11` or above installed in your Computer.

1. Download the latest `finesse.jar` from [here](https://github.com/AY2021S1-CS2103T-W16-3/tp/releases).

1. Copy the file to the folder you want to use as the *home folder* for Fine$$e.

1. Double-click the file to start the app.
The GUI similar to the below should appear in a few seconds, but without any sample data.
   ![Ui](images/Ui.png)

1. Type a command in the command box and press Enter to execute it. e.g. typing `help` and pressing Enter will bring up the help message.
   Some example commands you can try:

   * `list`: Lists all transactions on the current list.

   * `add-expense t/Bubble Tea a/5 d/03/10/2020 c/Food & Beverage`:
   Adds an expense with the title `Bubble Tea`, amount `5`, date `03/10/2020` and category `Food & Beverage` to the finance tracker.

   * `delete 3`: Deletes the 3rd transaction shown in the current list.

   * `exit`: Exits the app.

1. Refer to the [Features](#features) below for the details of each command.

> :bulb: If you have any questions, you can check out our [Section 7. FAQ](#7-faq).

--------------------------------------------------------------------------------------------------------------------
## 3. Overview of Features
[To be added]

--------------------------------------------------------------------------------------------------------------------
## 4. Features
This section aims to provide you with in-depth details on Fine\$\$e's unique features and also give you example usages of the features.

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add-expense t/TITLE`, `TITLE` is a parameter which can be used as `add t/Bubble Tea`.

* Items in square brackets are optional.<br>
  e.g `t/TITLE [c/CATEGORY]` can be used as `t/Bubble Tea c/Food & Beverage` or as `t/Bubble Tea`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[c/CATEGORY]…​` can be used as `  ` (i.e. 0 times), `c/Food & Beverage`, `c/Food & Beverage c/Tea` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `t/TITLE a/AMOUNT`, `a/AMOUNT t/TITLE` is also acceptable.

</div>

### 4.1 Viewing Help
Shows a message with instructions on how to access the user guide.

Format: `help`

Expected Outcome:
```$xslt
Refer to the user guide: https://ay2021s1-cs2103t-w16-3.github.io/tp/UserGuide.html.
Please copy the url and paste it in your favourite browser to view all valid commands.
```
### 4.2 Tabs
[Brief Overview of this feature]

#### 4.2.1 Switch Tab
Switches the current tab on Fine$$e.

Format: `tab INDEX`
- Switches to the tab corresponding to the specified `INDEX`. The index **must be either 1, 2, 3 or 4**.
    - Index 1 switches to the Overview Tab
    - Index 2 switches to the Incomes Tab
    - Index 3 switches to the Expenses Tab
    - Index 4 switches to the Analytics Tab

Example:
- `tab 3`

Example Usage:
- `tag 1`

Expected Outcome:
```$xslt
Switched to overview tab.
```

### 4.3 Expense
[Overview of feature]

#### 4.3.1 Add Expense

Adds an expense to the finance tracker.

Format: `add-expense t/TITLE a/AMOUNT d/DATE [c/CATEGORY]...`

* `AMOUNT` should be a number with 0 or 2 decimal places, with an optional `$` in front.
* `DATE` should be in `dd/mm/yyyy` format.

Examples:
* `add-expense t/Bubble Tea a/5 d/03/10/2020 c/Food & Beverage`
* `adde t/Taxi Home from School a/$13.50 d/10/10/2020 c/Transport c/School`

#### 4.3.2 Edit Expense

Edits an expense in the finance tracker.

Format:

Shortcut:

[Description of parameters]

Examples:

Example Usage:

Expected Outcome:

#### 4.3.3 Delete Expense

Deletes the specified expense from the finance tracker.

Format:

Shortcut:

[Description of parameters]

Examples:

Example Usage:

Expected Outcome:

#### 4.3.4 List Expense

Shows a list of all the expenses in the finance tracker.

Format:

Shortcut:

[Description of parameters]

Examples:

Example Usage:

Expected Outcome:


#### 4.3.5 Find Expense

[Explain what the command does]

Format:

Shortcut:

[Description of parameters]

Examples:

Example Usage:

Expected Outcome:

### 4.4 Income

[Summary of feature]

#### 4.4.1 Add Income

Adds an income to the finance tracker.

Format: `add-income t/TITLE a/AMOUNT d/DATE [c/CATEGORY]...`

* `AMOUNT` should be a number with 0 or 2 decimal places, with an optional `$` in front.
* `DATE` should be in `dd/mm/yyyy` format.

Examples:
* `add-income t/Internship a/560 d/03/10/2020 c/Work`
* `addi t/Angpao money a/$20 d/25/01/2020 c/CNY c/Gift`

#### 4.4.2 Edit Income

Edits an income in the finance tracker.

Format:

Shortcut:

[Description of parameters]

Examples:

Example Usage:

Expected Outcome:

#### 4.4.3 Delete Income

Deletes the specified income from the finance tracker.

Format:

Shortcut:

#### 4.4.4 List Income

Shows a list of all the incomes in the finance tracker.

Format:

Shortcut:

[Description of parameters]

Examples:

Example Usage:

Expected Outcome:

#### 4.4.5 Find Income

[Explain what the command does]

Format:

Shortcut:

[Description of parameters]

Examples:

Example Usage:

Expected Outcome:

### 4.5 Frequent Expense

[Summary of feature]

#### 4.5.1 Add Frequent Expense

[Explain what the command does]

Format:

Shortcut:

[Description of parameters]

Examples:

Example Usage:

Expected Outcome:

#### 4.5.2 Edit Frequent Expense

[Explain what the command does]

Format:

Shortcut:

[Description of parameters]

Examples:

Example Usage:

Expected Outcome:

#### 4.5.3 Delete Frequent Expense

[Explain what the command does]

Format:

Shortcut:

[Description of parameters]

Examples:

Example Usage:

Expected Outcome:

#### 4.5.4 Convert Frequent Expense

[Explain what the command does]

Format:

Shortcut:

[Description of parameters]

Examples:

Example Usage:

Expected Outcome:

### 4.6 Frequent Income

[Summary of feature]

#### 4.6.1 Add Frequent Income

[Explain what the command does]

Format:

Shortcut:

[Description of parameters]

Examples:

Example Usage:

Expected Outcome:

#### 4.6.2 Edit Frequent Income

[Explain what the command does]

Format:

Shortcut:

[Description of parameters]

Examples:

Example Usage:

Expected Outcome:

#### 4.6.3 Delete Frequent Income

[Explain what the command does]

Format:

Shortcut:

[Description of parameters]

Examples:

Example Usage:

Expected Outcome:

#### 4.6.4 Convert Frequent Income

[Explain what the command does]

Format:

Shortcut:

[Description of parameters]

Examples:

Example Usage:

Expected Outcome:

### 4.7 Expense Limit

[Summary of feature]

#### 4.7.1 Set Expense Limit

[Explain what the command does]

Format:

Shortcut:

[Description of parameters]

Examples:

Example Usage:

Expected Outcome:

### 4.8 Savings Goal

[Summary of feature]

#### 4.8.1 Set Savings Goal

[Explain what the command does]

Format:

Shortcut:

[Description of parameters]

Examples:

Example Usage:

Expected Outcome:

### 4.9 Analytics

[Summary of feature]

[Sub section for your commands]

### 4.10 Exiting the Program
Exits the program.

Format: `exit`

### 4.11 Saving the data

Fine$$e data is saved in the hard disk automatically after any command that changes the data.
There is no need to save manually.

--------------------------------------------------------------------------------------------------------------------
## 5. Command Summary

Action | Format | Examples
------|------|--------
Add Expense | `add-expense t/TITLE a/AMOUNT d/DATE [c/CATEGORY]...` <br> `adde t/TITLE a/AMOUNT d/DATE [c/CATEGORY]...` <br> (On Expenses tab) `add t/TITLE a/AMOUNT d/DATE [c/CATEGORY]...` | `add-expense t/Bubble Tea a/5 d/03/10/2020 c/Food & Beverage` <br> `adde t/Taxi Home from School a/$13.50 d/10/10/2020 c/Transport c/School`
Add Income | `add-income t/TITLE a/AMOUNT d/DATE [c/CATEGORY]...` <br> `addi t/TITLE a/AMOUNT d/DATE [c/CATEGORY]...` <br> (On Income tab) `add t/TITLE a/AMOUNT d/DATE [c/CATEGORY]...` | `add-income t/Internship a/560 d/03/10/2020 c/Work` <br> `addi t/Angpao money a/$20 d/10/10/2020 c/CNY c/Gift`
List | `list`
Find | `find KEYWORD [MORE_KEYWORDS]` | `find milk` <br> `find tea coffee`
Edit | `edit INDEX [t/TITLE] [a/AMOUNT] [d/DATE] [c/CATEGORY]…​`| `edit 1 t/Brunch d/22/09/2020` <br> `edit 2 a/500 c/`
Delete | `delete INDEX` | `delete 1`
Tab | `tab INDEX` | `tab 2`
Help | `help`
Exit | `exit`

--------------------------------------------------------------------------------------------------------------------
## 6. Glossary

[To be added]

--------------------------------------------------------------------------------------------------------------------
## 7. FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app on the other computer and overwrite the empty data file it creates with the file that contains the data of your previous Fine$$e finance tracker in the home folder.
