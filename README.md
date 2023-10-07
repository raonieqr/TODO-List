# TODO-List 
This is a simple Todo List application written in Java that allows you to manage your tasks. The application provides the following features:

Create a Task  
View Tasks by Status  
View Tasks by Priority  
View All Tasks  
View Tasks by Category  

## Create a Task  
To create a new task, follow these steps:  

- Choose option 1 from the main menu.  
- Enter the task details, such as title, description, status, priority, and category.  
- The task will be added to the list of tasks.  

## Task Alarm Notification

- The new Task Alarm Notification feature enables the application to monitor the task list continuously and alert users when it is time to complete a task.

## Stacks
**Backend**
-  Java
 
**Frontend**
- HTML5: The standard markup language for structuring and creating web application content.

- CSS3: Used to style and visually enhance the application, ensuring a pleasing user experience.

- JavaScript: The programming language used to add interactivity and dynamic functionality to the application.

- LocalStorage: Utilized to store data locally in the browser, allowing candidates to register and store profile and application information.

- DOM Manipulation: Manipulating the Document Object Model (DOM) is used to interact with HTML elements and dynamically update page content.

- Event Listeners: Event listeners are used to detect user interactions, such as button clicks or links, and trigger corresponding actions.

- Async Functions: Asynchronous functions are used to handle asynchronous operations, such as retrieving and storing data in LocalStorage.

## Testing

- The functionality of the TODO-List application has been tested using JUnit tests. The tests cover various aspects of task management, ensuring the reliability and correctness of the implemented features.

# CI/CD
## Testing with GitHub Actions:

- Automation: Test automation with GitHub Actions ensures that tests run automatically every time someone creates a PR. This eliminates the need for time-consuming and error-prone manual testing.

- Rapid Feedback: Automated tests provide instant feedback to developers on the quality of the code they are contributing. This helps identify problems early.

- Continuous Integration: Continuous integration with testing helps ensure that new or modified code does not break existing functionality.

- Better Collaboration: When a PR is created, test results are displayed directly on the PR page. This helps reviewers understand the impact of changes and makes it easier to make informed decisions.

- Quality Assurance: Running automated tests is a best practice to ensure software quality and reduce bugs and issues in production.

## Gradle
  - Gradle was added to take care of dependency injections, testing and running the program.
    The commands can be used: gradle test to run the tests, gradle build to build the project and gradle run to run
