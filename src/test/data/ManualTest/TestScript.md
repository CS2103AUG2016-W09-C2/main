﻿<!---@@author A0147967J-->
------
# Testscript
------
> Note: 
 - 1. The actual date and time displayed by the app depends on the day the test is performed.
 - 2. Please follow the given order to perform the tests to ensure a less tedious experience.
------
## Before: Load SampleData
------
> **Instructions:**
 - 1. Create a folder called `data` at the same directory as `happyjimtaskmaster.jar` executable.
 - 2. Copy and paste `SampleData.xml` into that folder.
 - 3. Run `happyjimtaskmaster.jar`.

------
## 0. Help Command
------
### 0.1 Open help window
> **Command:** `help`
**Result:**
 - Result display panel posts message:
    `Opened help window.`
 - Help window pops up and shows user guide.

------
## 1. Add Command
------
### 1.1 Add a floating task
> **Command:** `add read A Song of Ice and Fire t/reading`
**Result:**
 - Result display panel posts message:
    `New floating task added: read A Song of Ice and Fire Tags: [reading]
	\nRecurring: NONE`
 - TaskList panel navigates to and displays newly added task card.
 - Note: yellow background for floating tasks.

### 1.2 Add a deadline
> **Command:** `add business report by tomorrow 6pm t/assignment`
**Result:**
 - Result display panel posts message:
    `New non-floating task added: business report Tags: [assignment]
	\nRecurring: NONE`
 - TaskList panel navigates to and displays newly added task card.
 - Note: red background for deadline tasks.

### 1.3 Add a normal non-floating task
> **Command:** `add see JC friends from sat 6.30pm to 8pm`
**Result:**
 - Result display panel posts message:
    `New non-floating task added: see JC friends Tags: 
	\nRecurring: NONE`
 - TaskList panel navigates to and displays newly added task card.
 - Note: blue background for time slot tasks.
 
### 1.4 Add a recurring task
#### 1.4.1 Without recurring period
> **Command:** `add jogging from 9pm to 10pm daily`
**Result:**
 - Result display panel posts message:
    `New non-floating task added: jogging Tags: 
	\nRecurring: DAILY always`.
 - Agenda panel displays multiple instances of the newly added task on a daily basis.
 - TaskList panel navigates to and displays newly added task card.
 - Note: blue background for time slot tasks.
 
#### 1.4.1 With recurring period
> **Command:** `add tennis training from 7pm to 7.30pm weekly repeat 3`
**Result:**
 - Result display panel posts message:
    `New non-floating task added: tennis training Tags: 
	\nRecurring: WEEKLY repeat 3 times`.
 - Agenda panel displays an instance of the newly added task on a weekly basis.
 - TaskList panel navigates to and displays newly added task card. There are three instances in total.
 - Note: blue background for time slot tasks.

------
## 2. Block Command
------
### 2.1 Block a time slot
> **Command:** `block from tomorrow 4pm to 6pm t/meeting`
**Result:**
 - Result display panel posts message:
    `Timeslot blocked: BLOCKED SLOT Tags: [meeting]
	\nRecurring: NONE`
 - TaskList panel navigates to and displays newly added task card.
 - Note: brown background for a blocked slot.
 
### 2.2 Verifies blocked slot
> **Command:** `add afternoon tea from tomorrow 5.30pm to 6.30pm`
**Result:**
 - Result display panel posts warning message:
    `This timeslot is already blocked or overlapped with existing tasks.`
 - Text remains in the command box.
 - Command box background turns orange to show warning.
 
------
## 3. Delete Command
------
### 3.1 Delete task
> **Command:** `delete (index)`
 - index: the second last index in the list, which is an instance of `tennis training`.
**Result:**
 - Result display panel posts message:
    `Deleted Task: tennis training Tags: 
	\nRecurring: WEEKLY repeat 3 times
	\nFrom: [Formatted date of three weeks later] 07.00PM To: [Formatted date of three weeks later] 07.30PM`
 - TaskList panel removes all occurrences of the task. 
 - Agenda panel removes all occurrences of the task.
 
------
## 4. Complete Command
------
### 4.1 Archive non-recurring task
> **Command:** `done (index)`
 - index: the fourth last index in the list, which is `business report`.
**Result:**
 - Result display panel posts message:
    `Completed Task: business report Tags: [assignment]
	\nRecurring: NONE
	\nBy: [Formatted date of tomorrow] 06.00PM`
 - TaskList panel hides the newly archived task card. 
 - Agenda panel updates if it is a time slot task, the background change from blue to green to indicate type change.
 - Note: green background indicates archived task.
 
### 4.2 Archive recurring task
> **Command:** `done (index)`
 - index: the second last index in the list, which is `jogging`.
**Result:**
 - Result display panel posts message:
    `Completed Task: jogging Tags: 
	\nRecurring: DAILY always
	\nFrom: [Formatted date of today] 09.00PM To: [Formatted date of today] 10.00PM`
 - TaskList panel hides the newly archived task card, appends the next at the end of list. 
 - Agenda panel updates, the background change from blue to green to indicate type change.
 - Note: green background indicates archived task.

------
## 5. Find Command
------
### 5.1 Find by name
> **Command:** `find cs2103t`
**Result:**
 - Result display panel posts message:
    `3 tasks listed!`
 - TaskList panel lists all tasks whose name contains cs2103t.
 - There should be 3 tasks in total.
 - Note: Archived tasks are also displayed.
 
### 5.2 Find by tags
> **Command:** `find t/reading t/western`
**Result:**
 - Result display panel posts message:
    `5 tasks listed!`
 - TaskList panel lists all tasks whose tags contains the given ones.
 - There should be 5 tasks in total. 
 - Note: Archived tasks are also displayed.
 
### 5.2 Find by deadline
> **Command:** `find by tomorrow 23.59`
**Result:**
 - Result display panel posts message:
    `[number of listed tasks] tasks listed!`
 - TaskList panel lists all deadline tasks due before given time. 
 - There should be at least 4 tasks, actual number depends on the day the test is perfromed.
 - Note: Archived tasks are also displayed.
 
### 5.3 Find by time slot
> **Command:** `find from 10 nov to 10 dec`
**Result:**
 - Result display panel posts message:
    `23 tasks listed!`
 - TaskList panel lists all non-floating tasks start after given start time and due before given end time. 
 - There should be at least 23 tasks, actual number depends on the day the test is perfromed.
 - Note: Archived tasks are also displayed.
 
### 5.4 Find by task type
#### 5.4.1 Find archived tasks
> **Command:** `find -C`
**Result:**
 - Result display panel posts message:
    `12 tasks listed!`
 - TaskList panel lists all archived tasks.
 - Note: If the test is performed followed by given order, there should be exactly 12 tasks.
 
#### 5.4.2 Find floating tasks
> **Command:** `find -F`
**Result:**
 - Result display panel posts message:
    `10 tasks listed!`
 - TaskList panel lists all floating tasks.
 - Note: If the test is performed followed by given order, there should be exactly 10 tasks.
 
### 5.5 Find by multiple attributes
> **Command:** `find read t/western`
**Result:**
 - Result display panel posts message:
    `1 tasks listed!`
 - TaskList panel lists all tasks with given attributes.
 - Note: If the test is performed followed by given order, there should be exactly 1 task.

------
## 6. Edit Command
------
### 6.1 Edit name of task
> **Before:** `find a song`
**Command:** `edit 1 read Harry Potter`
**Result:**
 - Result display panel posts message:
    `Edit Task: read Harry Potter Tags: [reading]
	\nRecurring: NONE`
 - Note: TaskList panel updates and shows nothing, because current filter is still `find a song`.
 - To see the edited one, type eg. `find harry`.
 
 
### 6.2 Edit time of task
#### 6.2.1 Edit a floating task to a deadline
> **Command:** `edit 1 by next monday 11pm`
**Result:**
 - Result display panel posts message:
    `Edit Task: read Harry Potter Tags: [reading]
	\nRecurring: NONE
	\nBy: [Formatted date of next monday] 11.00PM`
 - TaskList panel navigates to newly edited task card with task date changed.
 - Task card background changes from yellow to red to indicate type change.
 - Note: Similarly: 
 - 1. convert the original floating task to a time slot, 
 - 2. or convert the deadline to a time slot, and vice versa,
 - are also supported.
 
#### 6.2.2 Edit the time slot for a recurring task with recurring period specified
> **Before:** `find civ`
**Command:** `edit 7 from 9 nov 10pm to 10.30pm`
**Result:**
 - Result display panel posts message:
    `Edit Task: read civ encyclopedia Tags: 
	\nRecurring: DAILY repeat 9 times
	\nFrom: Wed, Nov 9 10.00PM To: Wed, Nov 9 10.30PM`
 - TaskList panel navigates to newly edited task card with task date changed.
 - Agenda panel displays the newly edited task in new position.
 - Note: Only the selected instance of the recurring task will be affected.
 
#### 6.2.3 Edit the time slot for a recurring task without recurring period specified
> **Before:** `find jogging`
**Command:** `edit 2 from tomorrow 9.30pm to 10pm`
**Result:**
 - Result display panel posts message:
    `Edit Task: jogging Tags: 
	\nRecurring: DAILY always
	\nFrom: [Formatted date of tomorrow] 09.30PM To: [Formatted date of tomorrow] 10.00PM`
 - TaskList panel navigates to newly edited task card with task date changed.
 - Agenda panel displays the newly edited task in new position.
 - Note: All instances of the recurring task will be affected.
 
### 6.3 Edit recurring type of task
> **Command:** `edit 2 none`
**Result:**
 - Result display panel posts message:
    `Edit Task: jogging Tags: 
	\nRecurring: NONE
	\nFrom: [Formatted date of tomorrow] 09.30PM To: [Formatted date of tomorrow] 10.00PM`
 - TaskList panel navigates to newly edited task card with task recurring type changed. 
 - Agenda panel updates that no instances of the old daily task displayed.
 - The archived instance will not be affected.
 
### 6.4 Edit tags of task
> **Before:** `find cs2103t` 
**Command:** `edit 2 t/urgent t/assignment`
**Result:**
 - Result display panel posts message:
    `Edit Task: cs2103t demo Tags: [assignment][urgent]
	\nRecurring: NONE
	\nFrom: Wed, Nov 9 09.00AM To: Wed, Nov 9 10.00AM`
 - TaskList panel navigates to newly edited task card with task tag changed.

 
### 6.5 Edit multiple attributes
> **Before:** `find blocked`
**Command:** `edit 2 urgent meeting from tomorrow 5.30pm to 6.30pm`
**Result:**
 - Result display panel posts message:
    `Edit Task: urgent meeting Tags: [meeting]
	\nRecurring: NONE
	\nFrom: Tue, Nov 8 05.30PM To: Tue, Nov 8 06.30PM` 
 - Agenda panel updates to display the new slot.
 - Background changes from brown to blue to indicate type change.
 - TaskList panel will only show one other blocked slot, because current filter is still `find blocked`.
 - To see the edited copy, type eg. `find urgent meeting`.

------
## 7. List Command
------
### 7.1 List all active tasks
> **Command:** `list`
**Result:**
 - Result display panel posts message:
    `Listed all tasks`
 - TaskList panel lists all tasks that are not archived.

------
## 8. View Command
------
### 8.1 View agenda of the day
> **Command:** `view 15 nov`
**Result:**
 - Result display panel posts message:
    `Agenda Updated to Week specified by: [Formatted date of 15 nov depends on system language]`
 - TaskList panel lists all deadline tasks that due before and by 15 nov.
 - Agenda updates to the week of 15 nov.
 
------
## 9. Undo/Redo Command
------
### 9.1 Undo/Redo commands that modifies data
> **Command:** 
1. `add play CS from 2am to 3am`
2. `u`
3. `r`
**Result:**
1a.
- Result display panel posts message:
    `New non-floating task added: play CS Tags: 
	\nRecurring: NONE`
 - TaskList panel navigates to and displays the newly added task.
 - Agenda panel displays the newly added task.
 2a.
 - Result display panel posts message:
    `Undo successfully`
 - TaskList panel removes the newly added task.
 - Agenda panel removes the newly added task.
 3a.
 - Result display panel posts message:
    `New non-floating task added: play CS Tags: 
	\nRecurring: NONE`
 - TaskList panel navigates to and displays the newly added task.
 - Agenda panel displays the newly added task.

### 9.2 Undo/Redo commands that mutates list/agenda view
#### 9.2.1 Undo/Redo commands that mutates list view
> **Command:** 
1. `find -C`
2. `u`
3. `r`
**Result:**
1a.
- Result display panel posts message:
    `12 tasks listed!`
 - TaskList panel displays all archived tasks.
 2a.
 - Result display panel posts message:
    `Undo successfully`
 - TaskList panel resets to previous display.
 3a.
 - Result display panel posts message:
    `12 tasks listed!`
 - TaskList panel displays all archived tasks.

#### 9.2.2 Undo/Redo commands that mutates agenda view
> **Command:** 
1. `view 15 nov`
2. `u`
3. `r`
**Result:**
1a.
- Result display panel posts message:
    `Agenda Updated to Week specified by: [Formatted date of 15 nov depends on system language]`
 - TaskList panel lists all deadline tasks that due on 15 nov.
 - Agenda updates to the week of 15 nov.
 2a.
 - Result display panel posts message:
    `Undo successfully.`
 - TaskList panel resets to previous display.
 - Agenda panel resets to previous display.
 3a.
 - Result display panel posts message:
    `Agenda Updated to Week specified by: [Formatted date of 15 nov depends on system language]`
 - TaskList panel lists all deadline tasks that due on 15 nov.
 - Agenda updates to the week of 15 nov.
 
###9.3 Undo reaches maximum times
> **Command:** 
1. Enter `u` 3 times.
2. Enter `u`
**Result:**
 - Result display panel posts warning message:
    `No command to undo.`
 - Text stays, command box background turns orange to show warning.
 - Note: Same case for Redo.
 
------
## 10. Select Command
------
### 10.1 Select a task
> **Command:** `select (index)`
 - index: Any number within range of tasklist.
**Result:**
 - Result display panel posts message, which is the detailed information of the task.
 - TaskList panel navigates to and focuses the task card.
 - Note: If key in invalid index number, result display will post `The task index provided is invalid`.

------
## 11. Change Directory Command
------
### 11.1 Changes the default directory of the app
> **Command:** `cd filepath`
 - filepath: use `newfile.xml` as example.
**Result:**
 - Result display panel posts message:
    `Alert: This operation is irreversible.
	\nFile path successfully changed to : newfile.xml`
 - Status Foot Bar updates to show the new location.
 - Open the folder and the exported file should be there.
 - Reboot the app, it will load the file under the new path.
 - Note: If key in invalid path, result display will post `Wrong file type/Invalid file path detected.`.
 
------
## 12. Navigation Bar Utilities
------
### 12.1 Click Today
> **Action:** click `Today`
**Result:**
 - Result display panel posts message:
    `[number of tasks] tasks listed!`
 - TaskList panel lists all deadlines due today.
 - Agenda panel shows all tasks of this week.
 
### 12.2 Click Tasks
> **Action:** click `Tasks`
**Result:**
 - Result display panel posts message:
    `Listed all tasks`
 - TaskList panel lists all active tasks.

### 12.3 Click Deadlines
> **Action:** click `Deadlines`
**Result:**
 - Result display panel posts message:
    `[number of tasks] tasks listed!`
 - TaskList panel lists all deadlines due before and by today.
 
### 12.4 Click Floating
> **Action:** click `Floating`
**Result:**
 - Result display panel posts message:
    `[number of tasks] tasks listed!`
 - TaskList panel lists all floating tasks.
 
### 12.5 Click Incoming Deadlines
> **Action:** click `Incoming Deadlines`
**Result:**
 - Result display panel posts message:
    `[number of tasks] tasks listed!`
 - TaskList panel lists all deadlines due before and by next week today.
 
### 12.6 Click Completed
> **Action:** click `Completed`
**Result:**
 - Result display panel posts message:
    `[number of tasks] tasks listed!`
 - TaskList panel lists all archived tasks.
 
------
## 13. Clear Command
------
### 13.1 Delete all data
> **Command:** `clear`
**Result:**
 - Result display panel posts message:
    `Task list has been cleared!`
 - TaskList panel removes all tasks.
 - Agenda panel removes all tasks.

------
## 14. Exit Command
------
### 14.1 Exit the app
> **Command:** `exit`
**Result:**
 - HappyJimTaskMaster closes and quits.

------
## End
------ 
