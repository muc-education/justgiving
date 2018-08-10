 # Online Fundraising

## 1. Introduction

This assignment is about the implementation of a web-based, multi-user, fundraising donations service using Java Enterprise Edition (J2EE) technologies. The system will be a very simple version of JustGiving (www.justgiving.com). Through a JSF-based web interface: 
charities should be able to:

1.register with the fundraising service

2.register specific causes for donation (e.g. to raise awareness for a specific disease, or to send doctors to a place where doctors are urgently required)

3.check their virtual online account balance and amount of money raised for specific causes

### users should be able to:

1.create accounts and profile pages describing a few things about themselves

2.create fundraising activities for which other users can donate some money (e.g. bike ride, parachute jump or cake sale)

3.donate money for other users' activities (for a specific cause)

4.check funds raised for their activities

### administrators should be able to:

1.access all charities' accounts and causes

2.access all users' accounts and fundraising activities

> Optionally, the implemented system should be deployed into the cloud (Amazon Web Services (AWS)).
After successfully completing all components of the assignment, you will have demonstrated that you can:

* design and implement user interfaces using JavaServer Faces

* design and implement business logic using Enterprise Java Beans (EJBs)

* design and implement the data tier using Java Persistence (JPA)

* design and implement a secure multi-user system

* deploy a web application into the cloud

All money mentioned in this assignment is pretend money. No connection to real sources of money should exist. 

# 2. System Architecture

## 2.1. Web Layer

The web layer consists of a set facelets (.xhtml) pages through which users (fundraisers), charities and administrators interact with the web application.

### charities should be able to:

1.register with the service: details, such as username, unique username as registered with an imaginary charity registry (e.g. MSF for Medecins Sans Frontiers), which will be verified using a REST service (see section 2.4), date of establishment, full address and a short description up to 1000 characters about the activities of the charity, should be registered

2.register specific causes for donation (e.g. to raise awareness for lung cancer, or to pay and send doctors to a place where doctors are urgently required): a charity should be able to register (username, short description up to 1000 characters) specific causes for donation. Users can only register fundraising activities for a specific cause.

3.check their virtual online accounts: the system maintains a virtual account for each charity. All donations for specific causes and for specific fundraising activities end up to this account.

4.check amount of money raised for specific causes: a charity should be able to look-up the amount of money raised for a specific cause. All relevant information, such as cause username, amount of money, users and specific user fundraising activities must be available to registered charities.

### users (fundraisers) should be able to:

1.create accounts and profile pages describing a few things about themselves: other users should be able to look up users' profile pages (where all fundraising activities are presented) by username or through a table presenting all users with links to their profile pages.

2.create fundraising activities for which other users can donate money (e.g. bike ride, parachute jump or cake sale): a user can create a fundraising activity, including a username and short description up to 1000 characters.

3.donate money for other users' activities (for a specific cause): a user should be able to look-up for current fundraising activities and donate money for a specific activity (e.g. a 10km bike ride) published by another user. Donations can be up to £500 and a user cannot donate for his/her own activities. All users, upon registration, start with an amount £10,000.

4.check funds raised by other users for their fundraising activities: a user should be able to look-up funds raised for their activities and details about donations (donating user, amount of money, date of donation)

### administrators should be able to:

1.access all charities' accounts, causes and funds raised raised for specific causes

2.access all users' accounts, fundraising activities and details about funds raised for specific activities

3.register more administrator accounts

JSF Beans must not access any persistent data from the database. They should delegate all business logic to the service layer.

## 2.2. Service Layer

The service layer consists of a set of Enterprise Java Beans (EJBs), which implement the business logic for the system. More specifically, all user interaction with the web tier should end up calling methods of EJBs, which will most often require some access to the data tier. EJBs should support J2EE transactions so that data integrity is preserved. You should utilise container-managed transactions (which are supported by the EJB container). That is, your code doesn't need to cope with opening, committing or roll-backing transactions. You will just need to annotate your EJBs with the appropriate transaction attributes (or rely on the default behaviour if that is appropriate).

The service layer is responsible for accessing the data (persistence) layer. Persistence (JPA) entity managers must be injected in your EJBs. Access to persistent data must only take place through these entity managers. 

## 2.3. Data Layer

The data layer consists of a relational database and JPA entities. To simplify deployment and configuration you must use JavaDB (aka derby-db) as your Relational Data Base Management System (RDBMS). JavaDB is an RDBMS installed with GlassFish.

You data model should be written as a set of persistence entities (JPA). Upon deployment, JPA will create the actual relational database tables for you. Access to the database must always take place through manipulating JPA entities and using JPQL queries defined in JPA entities and executed by EJBs.
An indicative schema would include the following database tables (which should be mapped to JPA entities): Charity, Fundraiser, Cause, Activity, Donation. Tables required for form-based authentication would be just like in the security lab exercise (i.e. SystemUser, SystemUserGroup). Figuring out table relations (i.e. JPA associations) should be straightforward. For example, a Fundraiser (a user) can create many activities (i.e. Activity entities) and an activity can only belong to a single fundraiser.

You must username your database WebappsDB.

Do not access the database directly using JDBC. Do not use any other external RDBMS. Use Java Persistence and JavaDB (derby-db).

## 2.4. REST Services

By (some imaginary) law all charities are registered with the Imaginary National Charity Registry (INCR). Before registering a charity with the donation fundraising service, you should check that this charity is registered with the INCR. Additionally, by the same (imaginary) law, all donations must be stored by INCR for audit purposes. 
You must implement a REST service deployed on the same server as your system and that accepts requests to verify the existence of a charity at the relative URI /charity/{charity unique username}. Remember that a charity that registers with the fundraising donations system also registers a unique abbreviation (e.g. MSF for Medecins Sans Frontier). Additionally, information (charity username, cause, date and amount) about all donations must be stored, when REST (POST) requests are issued to the relative URI /charity/donation. The REST service must accept only GET requests for the first URI and only POST requests for the second one. For the first type of requests, the REST service must always reply positively (i.e. that the charity is registered) and for the second type of requests it must store all audited donations in a singleton EJB (e.g. in array list containing Donation objects) and respond with an appropriate response.
The fundraising service must verify with INCR that the charity exists before locally accepting a charity registration request and must send information about every donation to INCR before locally storing user donations.
Writing a REST client should be straightforward. Check the following links for more information:

http://docs.oracle.com/javaee/7/tutorial/jaxrs-client001.htm 
http://stackoverflow.com/questions/2793150/using-java-net-urlconnection-to-fire-and-handle-http-requests

## 2.5. Security

The online fundraising service is a multi-user web application. Users and charities must be logged-in in order to be allowed to interact with the system. They should not be able to see other users' or charities' information nor access pages and functionality for administrators. Administrators access their own set of pages, through which they can have access to all users' and charities' information. Charities, users and administrators should be able to logout from the web application.
You will need to implement and support:

* Communication on top of HTTPS for every interaction with charities, users and administrators

* Form-based authentication (using a jdbcRealm)

* Logout functionality for users, charities and administrators

* Declarative security to restrict access to EJB methods (all methods must be annotated appropriately so that specific roles -i.e. user, charity, administrator- can call the methods)

* Upon deployment, a single administrator account (username: admin1, password:admin1) must be present. You can implement that through a singleton EJB that is instantiated upon deployment or by using a simple SQL query when the persistence unit is deployed. Only an administrator can register more administrators through the restricted admin pages. 
Charities and users must be registered with a unique email address. Your JSF pages (through some EJB-based business logic) must check that email addresses are well formatted and prohibit duplicate registrations.
You must username your JDBCRealm WebappsRealm. The JNDI username should point to the database WebappsDB, and it should be named jdbc/WebappsDB.

# 3. Mark Allocation

## 3.1. Web Layer (20%)

15% - Full marks will be given if all required .xhtml files are written and correctly connected with JSF backing beans in a way that makes sense even if no other functionality is implemented at the service and data layer. The set of correctly implemented JSF pages includes .xhtml pages required to perform security-related actions.
5% - Full marks will be given if all required conversions and validations are supported (e.g. for required fields, well-formatted email addresses, short descriptions up to 1000 characters, donations up to £500 and any other obvious functionality). This highly depends on the way you design your pages. In most cases, JSF standard validations and conversions should be enough. Full marks will be given to projects supporting full and correct page navigation by explicitly specifying navigation rules in a faces-config.xml file.
Important Note: The appearance of web pages will not be marked. If you want, you can write your own or import an existing .css file but this is not part of this assignment. You are encouraged to use JSF frameworks, such as RichFaces or PrimeFaces that enable the design of modern UIs. No extra marks will be awarded if you do that.

## 3.2. Service Layer (20%)

Full marks will be given if all required business logic is implemented in a set of Enterprise Java Beans, which must include appropriate annotations for supporting JTA transactions, if and where required.

Full marks will be given if all functionality is implemented and correctly connected with the web- and data-tiers. More specifically, full marks will be given if functionality can be tested through standard interaction with the system using the web pages. User-, charity- and administration-related functionality will be marked with 6, 6 and 3 marks, respectively. Correct functionality also means checking that various constraints are satisfied (e.g. a user cannot donate money if his/her account doesn't have that amount, etc.).

## 3.3. Persistence Layer (10%)

Full marks will be given if all access to application data is handled through JPA Entities. A correctly configured persistence.xml file is required along with annotations for transforming Plain Old Java Objects (POJOs) to JPA entities. Annotations are required to define associations among different entities (e.g. one-to-many, many-to-many), wherever this is required.

## 3.4. Security (20%)

10% - Form-based authentication

Full marks will be given if users can register, login and logout. This should be implemented using a jdbcRealm which is linked to JavaDB in order to register and authenticate users. An admin must be registered in the system when deploying (and, therefore, creating database tables)
4% - Declarative security for access control when navigating through .xhtml pages

Access to .xhtml pages must be restricted to authorised actors. You must include security constraints in the deployment descriptor.
4% - Declarative security for accessing EJB functionality

EJBs must be annotated appropriately (along with annotation-based role declarations) so that EJB functionality can be accessed my authorised actors (charities, users and admins)
2% - Initial administration registration

Upon deployment, a single administrator account (username: admin1, password:admin1) must be present. You can implement that through a singleton EJB that is instantiated upon deployment or by using a simple SQL script when the persistence unit is deployed. Only an administrator can register more administrators through the restricted admin pages.

## 3.5. REST Services (10%)

Full marks will be given if both REST services are correctly implemented on the server side (the Imaginary National Charity Registry) and accessed by the client (the fundraising donation system).

## 3.6. One out of two options (20%)

For the last 20% of the marks you can select between 2 options.

### 3.6.1 Report

Write a 1000 word report, critically assessing the strengths and limitations of your implementation utilising your understanding of the underlying technologies. The report must consider the following points:

(5%) How your design fits with the 3-tier architectural model and the model-view-controller software pattern

(5%) The strengths and weaknesses of your chosen methods for securing the application, comparing your approach to other options

(5%) How your design could be extended so that your server is not a single point of failure

(5%) How your system would deal with concurrent users accessing functionality and data

### 3.6.2 More Programming

Implement the following features which may have an impact on all three tiers of your system:

(4%) Fundraisers (regular users) can donate anonymously. If that option is enabled the user that registered the fundraising activity and charities will not be able to see the username of the person that donated the money.

(6%) A user can register their activity with multiple causes specifying percentages of donations for each cause (which may be registered by different charities).

(6%) Fundraising activities expire when the actual activity takes place. That means that no more donations can be accepted for fundraising activities after a specific date. Users should be able to see current and past activities for specific users but only donate for current activities.

(4%) All donations must be timestamped by accessing a 'remote' Thrift timestamp service (which is deployed on the same server as your system). The service should return the current date and time to your system when requested by the Enterprise Java Bean that handles the creation and storage of donations. The Thrift server can be implemented as a deployable EJB which uses a separate thread to accept timestamping requests at port 10000.

## 3.7. (Optional) Deployment on Amazon AWS (5% bonus)

You can optionally deploy your application on Amazon and get an extra 5%. The maximum mark you can get for this assignment is 100% (i.e. you will still get a mark of 100% (and not 105%) even if your submission is perfect and you deployed your application on Amazon). To do so, you must successfully deploy and run the application on an Amazon EC2 virtual machine which you should configure following the instructions published on Study Direct. You must submit screenshots of the commands you issued on the console to run Glassfish and JavaDB, the security configuration, JDBC pool and data source configuration, and screenshots of the application running on an Amazon EC2 instance where the URI of the application is shown. In order to verify that you have indeed deployed the application, I may ask you, during the marking period, to run the server and deploy your application for me to test it.
In order to get access to Amazon AWS, you must first register as a Sussex student here (no credit card is required if you select the AWS educate starter account):
 
https://www.awseducate.com/Application
 
Note: it may take several days for Amazon to verify and enable your account, so start early with the registration process. I have no control over this process.

## 3.8. Comments about marking

The coursework requires you to bring together several independent pieces of functionality. It is highly recommended that you think about the whole system design BEFORE you start implementation. Consider which parts are necessary to implement the core functionality and create easily replaceable stubs for the peripheral services.
Some parts of this assignment are independent. For example one could implement the system without any web services (sacrificing the respective marks) by just hard-coding functionality without actually accessing the REST services.
Along the same lines, one could ignore the data/persistence layer (losing 10% of the marks) by storing data in Lists and Sets appropriately in a Singleton EJB.
Some other functionality cuts through the whole system architecture vertically. That means that if, for example, the donating functionality is not implemented (nor the .xhtml files and any potentially required persistence data) marks will be removed from all three layers.
Security is mostly independent and orthogonal to the rest of the system. Implementing a system without any security support (sacrificing the respective marks) is possible if for every request, the actual user identifier (email address) that identifies the user that interacts with the system is also passed through the JSF page. 


# 4. Submission

Submission will be through the e-submission link on Study Direct. This link cannot be added in the umbrella web site, therefore you need to click on the module-specific web site (which is different for UG and PG students).
Your submission should be a zip file containing:

a zipped copy of the NetBeans project containing well formatted and documented source code (including all .java, .xhtml and all required configuration files)

a brief catalogue of the files, describing the purpose of each file

the report (if you selected this option)

screenshots for the Amazon AWS deployment as described in the relevant section

Failure to submit the source code as described in the first bullet, will result to a zero mark as I will not be able to assess your programming effort. The submitted source code must be part of a Netbeans project that I can compile and deploy locally on my own GlassFish server. Projects implemented using other technologies (e.g. jsp, Spring, MySQL Server, PHP, Play etc.) will not get any marks.
A penalty of 5% will be applied if the source code is not well-formatted and doesn't contain comments for classes and methods.
A penalty of 3% will be applied if the username of the database, jdbcRealm and context root are not WebappsDB, WebappsRealm, /webapps2018, respectively.

# 5. Plagiarism and Collusion

The coursework you submit is supposed to have been produced by you and you alone. This means that you should not:

work together with anyone else on this assignment

give code for the assignment to other students

request help from external sources

do anything that means that the work you submit for assessment is not wholly your own work, but consists in part or whole of other people’s work, presented as your own, for which you should in fairness get no credit

If you need help ask your tutor

The University considers it misconduct to give or receive help other than from your tutors, or to copy work from uncredited sources, and if any suspicion arises that this has happened, formal action will be taken. Remember that in cases of collusion (students helping each other on assignments) the student giving help is regarded by the University as just as guilty as the person receiving help, and is liable to receive the same penalty.

Also bear in mind that suspicious similarities in student code are surprisingly easy to spot and sadly the procedures for dealing with it are stressful and unpleasant. Academic misconduct also upsets other students, who do complain to us about unfairness. So please don’t collude or plagiarise.

# Append

http://docs.jboss.org/hibernate/orm/4.1/devguide/en-US/html/ch11.html#d5e2524