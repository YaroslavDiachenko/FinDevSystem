# FinDevSystem

Is a back-end RESTfull server application for staff management and financial accounting. As a management instrument it allows to create daily events and assign relevant  employees. FinDevSystem stores in database information about all employees and events they took part in. Financial component provides for calculation of employees' monthly salaries and scheduled auto mailing of payslips. 

## Access
There are available following levels of access:
- **administrator** (unlimited access including controlling access roles, assigning moderators, etc)
- **moderator** (creating and editing employees and events data)
- **user** (viewing own data)

### Events
Moderator creates events on daily basis. System allows to add one or more free employees as participants of the event. Employees can have the following statuses:
- **free** (availeble to take part in event)
- **busy** (taking part in another event already)
- **on sick leave** (not available)
- **on vacation** (not available)

Each event contains its type, date and duration (in hours).
![events_create](images/events_create.png)

### Income query
Each employee at the beginning of each month receives on mail pdf payslip containing  salary amount for previous month and listed events the employee took part in accordingly. See sample

Moderator and administrator can request income amount of an employee indicating 

### Employee
Upon registration each employee receives an e-mail notification.

![mail1](images/mail1.png)

Information about employee:
- first name
- last name
- e-mail
- department
- position
- hourly rate
- status

At the beginning of each month an employee receives on mail pdf payslip containing  salary amount for previous month and listed events the employee took part in accordingly.

![mail2](images/mail2.png)

Additionally, each employee has access in the system to his data and income per any period. Employee's salary is calculated based on hourly rate, time spent and events types.

> Note: Use the following date format: `yyyy-MM-dd`

### Moderator
Moderator registers new employees in the system:

![sw_post](images/sw_post.png) `/employees`

![employee_create](images/employee_create.png)

Creates new events:

![sw_post](images/sw_post.png) `/events`

![event_create](images/event_create.png)

Can view information about each employee:

![sw_get](images/sw_get.png) `/employees/{employeeId}`

![employee_getbyid](images/employee_getbyid.png)

Can view income of each employee per specified period of time:

![sw_get](images/sw_get.png) `/income?firstName={firstName}&lastName={lastName}&dateFrom={dateFrom}&dateTo={dateTo}`


Link to web server: [FinDevSystem](https://findevsystem.herokuapp.com/swagger-ui.html)


## Technical overview
- Java
- MySQL
- Spring Boot
- Maven
- Git


- Heroku
- ClearDB
- Swagger

