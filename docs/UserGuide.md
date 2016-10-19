# User Guide

* [Quick Start](#quick-start)
* [Features](#features)
* [FAQ](#faq)
* [Command Summary](#command-summary)

## Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.
   
1. Download the latest `happyjimtaskmanager.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your Address Book.
3. Double-click the file to start the app. The GUI should appear in a few seconds. 

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window. 
5. Some example commands you can try:
   * **`view`** : View floating task of the day
   * **`add`**` Homework by 24 sep 6pm` : 
     adds a task named Homework to the Task Master .
   * **`delete`**` 212` : deletes the task with ID 212 shown in the current list
   * **`exit`** : exits the app
6. Refer to the [Features](#features) section below for details of each command.<br>

## HappyJimTaskMaster's GUI
 <img src="images/Ui.PNG" width="600">

1. Command box. This is where the commands are entered. Simply type in the command and press enter to execute it.
2. Result display. This is where the results of commands are shown.
3. Google calendar. Tasks entered will be synced to your google calendar.
4. Tasklist panel. This is where the tasks are displayed. 
5. Navigation bar panel. This is where the navigation categories are displayed.

## Features
### Command Format
> * Each command consists of a command word (such as add or delete), followed by other options such as DATE,TIME or [t/TAG]
> * Words in `UPPER_CASE` are the description of what kind data to input.
> * Items in `SQUARE_BRACKETS` `[items]` are optional.
> * Items with `...` after them can have multiple instances.
> * The order of parameters is fixed.

### Date and Time Format
HappyJimTaskMaster uses Natty date parser to parse date and time options. <br>

Some examples of acceptable format include:
* 21 nov 2005
* 24 sep 8pm
* jan 1st
* next thursday
* 3 days from now

For a full list of acceptable formats, please refer to http://natty.joestelmach.com/doc.jsp
### Commands
#### Viewing help : `help`
Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`

Example:
* `help` 
 
#### Adding a floating task: `add`
Adds a task to the todo list<br>
Format:`add TASK_NAME [t/TAG]...` 

> Words in `UPPER_CASE` are the parameters, items in `SQUARE_BRACKETS` are optional, 
> items with `...` after them can have multiple instances. Order of parameters are fixed. 
>  
> Tasks can have any number of tags (including 0)

Examples: <br>
* `add Homework`<br>
* `add Homework t/CS1231`

 <img src="images/addfloating1.PNG" width="600">
 <img src="images/addfloating2.PNG" width="600">
#### Adding a task with deadline: `add`
Format: `add TASK_NAME by DATE TIME [RECURRING_TYPE] [t/TAG]...`

> Words in `UPPER_CASE` are the parameters, items in `SQUARE_BRACKETS` are optional, 
> items with `...` after them can have multiple instances. Order of parameters are fixed. 
>  
> `RECURRING_TYPE` consists of daily, weekly, monthly and yearly. 
> Tasks can have only 1 `RECURRING_TYPE`.
> Tasks can have any number of tags (including 0).

Examples:
* `add Homework by 24 sep 8pm t/CS1231`
* `add Homework by 24 sep 6pm daily t/CS1231`

 <img src="images/adddeadline1.PNG" width="600">
 <img src="images/adddeadline2.PNG" width="600">
#### Adding a task with start time and end time: `add`
Format: `add TASK_NAME from DATE TIME to DATE TIME [RECURRING_TYPE] [t/TAG]...`

> Words in `UPPER_CASE` are the parameters, items in `SQUARE_BRACKETS` are optional, 
> items with `...` after them can have multiple instances. Order of parameters are fixed. 
>  
> `RECURRING_TYPE` consists of daily, weekly, monthly and yearly. 
> Tasks can have only 1 `RECURRING_TYPE`.
> Tasks can have any number of tags (including 0).

Examples:
* `add Homework from 24 sep 8pm to 25 sep 9pm t/CS1231`
* `add Homework start/24 sep 9pm end/25 sep 7pm daily t/CS1231`
<img src="images/addperiod1.PNG" width="600">
<img src="images/addperiod2.PNG" width="600">

#### View floating task of the day : `view`
Format: view /OPTION

> Words in `UPPER_CASE` are the parameters, items in `SQUARE_BRACKETS` are optional, 
> OPTION currently supports f : floating

Examples: 
* `view /f`

#### View non-floating task of the day : `view`
Format: view date/DATE

> Words in `UPPER_CASE` are the parameters, items in `SQUARE_BRACKETS` are optional,

Examples: 
* `view date/2409`
* `view date/24sep`

#### Edit tasks : `edit`
Format: `edit TASK_ID [start/EDIT_START_DATE EDIT_START_TIME end/EDIT_END_DATE EDIT_END_TIME] [tag/EDIT_TAG]...`

> Words in `UPPER_CASE` are the parameters, items in `SQUARE_BRACKETS` are optional, 
> items with `...` after them can have multiple instances. Order of parameters are fixed. 
>  
> Tasks can have any number of tags (including 0)

Examples: 
* `edit 213 start/2709 1800 end/3009 1800  t/cs2101`
* `edit 213 start/27sep 6pm end/30sep 6pm t/cs2101`

#### Delete tasks : `delete`
Format: delete TASK_ID

> Words in `UPPER_CASE` are the parameters, items in `SQUARE_BRACKETS` are optional, 

Examples:
* `Delete 212`

   <img src="images/delete1png" width="600">
   <img src="images/delete2.png" width="600">

#### Archive completed tasks : `done`
Format: done TASK_ID

> Words in `UPPER_CASE` are the parameters, items in `SQUARE_BRACKETS` are optional, 

Examples:
* `done 1`

   <img src="images/ugdone_0.png" width="600">
   <img src="images/ugdone_1.png" width="600">
   <img src="images/ugdone_2.png" width="600">
   >Completed tasks can be viewed from navigation bar on the side.

#### Block out timeslot : `block`
Format: block from [START_DATE] START_TIME to [START_DATE] START_TIME [t/TAG]

> Words in `UPPER_CASE` are the parameters, items in `SQUARE_BRACKETS` are optional, 

Examples:
* `block from today 8pm to today 9pm`

 > <img src="images/ugblock_0.png" width="600">
 > <img src="images/ugblock_1.png" width="600">

#### Undo tasks : `undo`
Format: u

> Maximum 3 undo

Examples: 
* `u`

   <img src="images/ugundo_0.png" width="600">
   <img src="images/ugundo_1.png" width="600">
   <img src="images/ugundo_2.png" width="600">

#### Redo tasks : `redo`
Format: r

> Maximum 3 redo

Examples: 
* `r`

   <img src="images/ugredo_0.png" width="600">
   <img src="images/ugredo_2.png" width="600">

#### Find tasks : `find`
Format: find [TASK_NAME] [date/DATE [TIME]] [tag/TAG]

> Words in `UPPER_CASE` are the parameters, items in `SQUARE_BRACKETS` are optional,

Examples: <br>
* `find cs2103 date/2109 <br>`
* `find date/2109 1800 tag/gigi <br>`
* `find date/21sep 6pm tag/gigi`
* `find cs2103 tag/lolo`

#### Undo tasks : `clear`
Format: clear

> clears all the tasks

Examples: 
* `clear`

#### Change directory: `cd`
Format: cd FILE_PATH

> Words in `UPPER_CASE` are the parameters, items in `SQUARE_BRACKETS` are optional,

Examples: 
* `cd data\newlist.xml`

   <img src="images/ugcd_0.png" width="600">
   <img src="images/ugcd_1.png" width="600">

#### Exiting the program : `exit`
Exits the program.<br>
Format: `exit`  

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with 
       the file that contains the data of your previous Address Book folder.

**Q**: How do i get started using the task manager?<br>
**A**: Type 'help' or any incorrect command will bring you to the help screen.
       
## Command Summary

Command | Format  
-------- | :-------- 
Add | `add TASK_NAME [t/TAG]...`
Add | `add TASK_NAME by DATE TIME [RECURRING_TYPE] [t/TAG]...`
Add | `add TASK_NAME from DATE TIME to DATE TIME [RECURRING_TYPE] [t/TAG]...`
Edit | `edit TASK_ID [from EDIT_START_DATE EDIT_START_TIME to EDIT_END_DATE EDIT_END_TIME] [by EDIT_END_DATE EDIT_END_TIME] [t/EDIT_TAG]...`
Delete | `delete TASK_ID`
Complete | `done TASK_ID`
Block | `block TASK_NAME from [START_DATE] START_TIME to [START_DATE] START_TIME [t/TAG]...`
Redo | `r`
Undo | `u`
Clear | `clear`
Change directory | `cd FILE_PATH`
Exit | `exit`
