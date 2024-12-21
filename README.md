## SMART Task Tracker

<img width="1204" alt="PriorityView" src="https://github.com/user-attachments/assets/905a4dec-39ad-4a6a-9a1c-9015d2f519a6" />

<img width="1196" alt="AddTask" src="https://github.com/user-attachments/assets/44e4b385-6965-442c-ac4a-b5aa7493779f" />

## Setup
- To set up the project:

1. Right click the project folder from the project explorer view.
2. Select Build Path > Configure Build Path option from the menu.
3. Under the Java Build Path option, go to "Libraries" tab.
4. On clicking the "Modulepath" option, select the "Add External JARs" button present on the right side in the dialog box.
5. Navigate to the "lib" sub-folder & select "Credentials.jar" file.
6. Click "Open" button.
7. Now, again clicking the "Modulepath" option, select the "Add Library" button present on the right side in the dialog box.
8. Select USer Library > Java FX > Next > Apply & Close.


- To run the project:

1. Right click the project folder from the project explorer view.
2. Select Run As > Run Configurations....
3. Create a new configuration by clicking the "New launch configuration" icon present in the left side menu on the top.
4. Now on the right side of the new configuration the got created, give a name to the configuration.
5. On the "Main" tab, the selected Project should be selected as hacker-huskies. If the correct project is not selected, use the "Browse..." 
   button to select the correct project.
6. For the "Main class", use the "Search..." button & select "Main - application" and click "OK".
7. Now, on the "Arguments" tab, un-select the selected check boxes & add "--add-modules javafx.controls,javafx.fxml" to "VM Arguments" text area.
8. Click "Apply" & then "Run".
9. The project should now be running and the login screen should be visible. Enter the username as "admin" & password as "Pass@123" & click "Login".
10. The home page will be visible on successful login.
