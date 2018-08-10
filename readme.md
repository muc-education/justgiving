# Online Fundraising Project Introduce

I did not finished all the task in my project code. Pertualar, user could not do donate by web. I just finished donate dao service to do this work. 
I choose the primefaces framework to design jsf. But it had happened some bug.

## Finished Task

* register with the fundraising service

* user could create accounts and profile pages describing a few things about themselves

* design and finished dao layer. and build some service to use dao. could save data to the derby database.

* config https mode replace http mode. design SHA-256 for passoword ( in edu.study.giya.base.AuthenticationUtils )

* design and implement a few interfaces using JavaServer Faces

* design and implement some business logic using Enterprise Java Beans 

* finished design and implement the data tier using Java Persistence

* design and implement a secure multi-user system

## UnFinished Task

* register specific causes for donation (e.g. to raise awareness for a specific disease, or to send doctors to a place where doctors are urgently required)

* check their virtual online account balance and amount of money raised for specific causes

* create fundraising activities for which other users can donate some money (e.g. bike ride, parachute jump or cake sale)

* donate money for other users' activities (for a specific cause)

* check funds raised for their activities

* administrators access all charities' accounts and causes

* administrators access all users' accounts and fundraising activities

# My code

## Java code

### in package: edu.study.qjya.base

in this package, I specify a exception class to implements system exceptin actions and design AuthenticationUtils to encrypt user password.

### in package: controller

in this package, I put the web layer service code to this package. I desigin UserInfo class to include Charities, Users,administrators. And define Role to classify them.
ActivityController mean the controller of  the jsf page viewer.

### in package: service

in this package. I put the dao proxy class to this layer. To do this, I want make it easy to change work.

### in package: dao

in this package, I put the all jpa operation at this lay. and design the reflect mode to do persisting work.

### other package.

com.github.adminfaces.template and org.primefaces.showcase is the source code of primefaces commpent.

## Jsf and other code

### resources

this is a maven project. so I put the resources to this directory. such as, persistence.xml, log4j.properties, and other xml config.

### webapp

in this directory, I put the jsf page to this directory.

### test

this is a junit test package.
