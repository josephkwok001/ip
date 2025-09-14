# Waguri User Guide

![Screenshot](/docs/Ui.png)

This is a bot called that acts like a task management chatbot. 
There are several methods that users can use, which includes: todo, deadline, event, delete, list, mark, unmark, bye, due, find, archieve, help.

## Adding todo

A todo task is a task that has no time limit set to ti.The format to add a deadline is as follows:

    todo "task_name" 

Example

    todo 100 pushups

## Adding deadlines

A deadline is a task that has a time limit to it. The format to add a deadline is as follows:

    deadline "task_name" /by "time"

Example

    deadline cs2103t ip /by tonight

## Adding Event

An event is a task that has a time period to it (i.e. starts from ... and ends at ...) 
The format to add a event is as follows:

    event "task_name" /from "time1" /to "time2"

Example

    event hackathon /from Sept 18 2025 /to Sept 19 2025


## Viewing tasks

Viewing the task users have inputed can be done by using the list function. The list function lists out the task that users have added.
The format of the list function is simply:

    list

## Delete/Mark/Unmark task

Users can delete a task or mark a task as completed or unmark as task to be incomplete. 
The format to mark a task is as follows:

    delete index_number
    mark index_number
    unmark index_number

Example

    delete 3
    mark 1
    unmark 2

## Due/Find task

Users can find task by searching for the name of the task. Users can also get tasks that are due on a certain date.
The format to find a task is:

    find task_name

The format to get due task is:

    due time

Example of find/due tasks

    find cs2103t ip

    due Sep 18 2025

