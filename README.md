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


#### Implementation
- The '`DelegatingFilterProxy`' added by web.xml and added 2 filters:
    - for all requests with name '`allTestFilter`'
    - for booking section with name '`bookingFilter`'
   
- Configured access control via java configuration instead namespace in xml.
    
    - The access security configuration placed to `WebSecurityConfig` class which extended _`WebSecurityConfigurerAdapter`_
    
    - The authentication provided by `AuthProvider`
    
    - For password encoding used the  `BCryptPasswordEncoder`
    
    - The role of users represent as enum `UserRole`
    
- Added custom login page
    - Configured in `WebSecurityConfig` and add reference to view in custom controller `IndexController`
    - Logout filter added as `customLogoutHandler` bean and implemented in `CustomLogoutHandler` with writing message to log

- Configured Remember-Me authentication
    - Configuration in `WebSecurityConfig`
    

#### Test USERS:
- User with ‘admin’ access:
    - Login: j.smith@test.com 
    - Password: 	John
- User with only ‘REGISTERED’ access:
    - Login: 	Petr.Mach@test.com
    - Password: 	Petr


##### All details on: [SCA-37-task](https://github.com/ivanina-ilya/SCA-37-task)

