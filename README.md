## Cinema application

This is simple basic application for provide some test task from Course SpringMVC #37

##### NOTE:
* README file with installation and configuration details here: [README-T1](https://github.com/ivanina-ilya/SCA-37-task/blob/master/README-T1.md)

#### Original task

1. Configure Spring Security for ticket booking web application - add DelegatingFilterProxy to web.xml

2. Configure access control via security namespace. All application operations should be accessible to users with role RESGISTERED_USER only. Getting booked tickets for particular event should be accessible only to users with role BOOKING_MANAGER. Add two new fields to User entity - password and roles. Roles field should contain comma-separated list of user roles. All users in database should have REGISTERED_USER role by default. Create several test users with additional BOOKING_MANAGER role. 

3. Implement form-based login via security namespace, add custom login page, configure DAOAuthenticationProvider and UserDetailsService to load user data from database. Configure logout filter.

4. Configure Remember-Me authentication.

5. Implement password encoding during authentication. 




##### All details on: [SCA-37-task](https://github.com/ivanina-ilya/SCA-37-task)

