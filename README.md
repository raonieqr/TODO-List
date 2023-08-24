# TODO-List 
This is a simple Todo List application written in Java that allows you to manage your tasks. The application provides the following features:

Create a Task  
View Tasks by Status  
View Tasks by Priority  
View All Tasks  
View Tasks by Category  

## Create a Task  
To create a new task, follow these steps:  

Choose option 1 from the main menu.  
Enter the task details, such as title, description, status, priority, and category.  
The task will be added to the list of tasks.  

## New Feature: Task Alarm Notification

The new Task Alarm Notification feature enables the application to monitor the task list continuously and alert users when it is time to complete a task. 

## Testing

The functionality of the TODO-List application has been tested using JUnit tests. The tests cover various aspects of task management, ensuring the reliability and correctness of the implemented features.

### TaskTest

The `TaskTest` class includes several test methods to verify the behavior of the `Task` class:

#### `testAddTask`

This test ensures that a task can be successfully added to the list of tasks.


#### `testReadTask`

This test verifies that the properties of a task can be read correctly.

#### `testEditTask`

This test ensures that a task's properties can be successfully edited.


#### `testDeleteTask`

This test validates that a task can be successfully deleted from the list of tasks.
