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
   > <img src="images/Ui.JPG" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window. 
5. Some example commands you can try:
   * **`list`** : Lists the active tasks
   * **`add`**` Homework by 24 sep 6pm : 
     adds a task named `Homework` to the Task Master .
   * **`delete`**` 2` : deletes the task with index 2 shown in the current list
   * **`exit`** : exits the app
6. Refer to the [Features](#features) section below for details of each command.<br>


## Features

> **Command Format**
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * The order of parameters is fixed.
> * Tasks can have any number of tags (including 0)

#### Viewing help : `help`
Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`

Example:
* `help` 
 
#### Adding a floating task: `add`
Adds a task to the task master<br>
Format:`add TASK_NAME [t/TAG]...` 


Examples: <br>
* `add Homework`<br>
* `add Homework tag/CS1231`

#### Adding a task with deadline: `add`
Format: `add TASK_NAME by [DATE] TIME [RECURRING_TYPE] [t/TAG]...`
  
> `RECURRING_TYPE` consists of daily, weekly, monthly, yearly and none. 
> Tasks can have only 1 `RECURRING_TYPE`.
> Tasks can have any number of tags (including 0).
> If not specified, by default `RECURRING_TYPE` is none.

Examples:
* `add Homework by today 8pm t/CS1231`
* `add Homework by 6pm daily t/CS1231`

#### Adding a task with start time and end time: `add`
Format: `add TASK_NAME from [DATE] [TIME] to DATE TIME [RECURRING_TYPE] [t/TAG]...`
 
Examples:
* `add Homework from 24 sep 8pm to 25 sep 9pm t/CS1231`
* `add Homework from 9pm to 10pm daily t/CS1231`

#### List all active tasks : `list`
Format: list

Examples: 
* `list`

#### Edit tasks : `edit`
Format: `edit TASK_ID [from EDIT_START_DATE EDIT_START_TIME to EDIT_END_DATE EDIT_END_TIME] || by [DATE TIME] [RECURRINGTYPE] [tag/EDIT_TAG]...`

Examples: 
* `edit 2 by 4pm t/cs2101`
* `edit 2 new name from 5pm to 6pm daily t/cs2101`

#### Delete tasks : `delete`
Format: delete TASK_ID

Examples:
* `delete 2`

#### Archive completed tasks : `done`
Format: done TASK_ID

Examples:
* `done 1`

> Completed tasks can be viewed from navigation bar on the side.

#### Block out time slot : `block`
Format: block from [START_DATE] START_TIME to [START_DATE] START_TIME [t/TAG]

Examples:
* `block from today 8pm to today 9pm`

#### Undo tasks : `undo`
Format: u

> Maximum 3 undo

Examples: 
* `u`

#### Redo tasks : `redo`
Format: r

> Maximum 3 redo

Examples: 
* `r`


#### Find tasks : `find`
Format: find [TASK_NAME] [TYPE] [from START_DATE TIME to END_DATE TIME] || [by DATE TIME] [t/TAGS]


Examples: <br>
* `find cs2103 by 9pm <br>`
* `find from 23 oct 6pm to 25 oct 7pm t/exam <br>`
* `find t/cs2103`
* `find cs2103`

> `-C` denotes completed tasks; `-F` denotes floating tasks.

#### View one week's agenda : `view`
Format: view DATE [TIME]

Examples: 
* `view next monday`



#### Change directory: `cd`
Format: cd FILE_PATH

Examples: 
* `cd data\newlist.xml`

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
Find | `find [TASK_NAME] [by DATE TIME] [t/TAG]...`
Clear | `clear`
View | `view DATE [TIME]`
Change directory | `cd FILE_PATH`
Exit | `exit`
