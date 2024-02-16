# Recrip

A Web Project On Travel Recommendation Site.

Tools Used:
- FrontEnd: Vue.js
- BackEnd: Spring Boot
- RDBMS: RDS
- Storage: S3
- Computing & Deployment: EC2
- DNS: Route 53
- Domain: Gabia

Miscellaneous Implements:
- Utilized KakaoMap API for mapping
- Web Scrapping used on news site to collect newest articles

Functionalities:
- Login
- Map Interaction (Marker, Popup)
- Course Selection, Management
- Review for Courses
- File I/O
- Nested Comments
- And many more...


Main menu:
![image](https://github.com/govltjsdnd24/Recrip_Private/assets/38126462/06fbf67c-0a5c-45f5-a9a7-c10156c26bfe)
In the main menu, there is a ranking board of tourist sites that get updated according to the data in the database. 
The ranking is decided by number of views, add to carts, and multiple factors.
There are also trending posts displayed on the main menu which is calculated similarly.
On the bottom, there is a grid containing news articles about travels that are collected from a news website.


Bulletin Board:
![image](https://github.com/govltjsdnd24/Recrip_Private/assets/38126462/1dd0865b-ef09-4a3b-8fce-e1d87dec35cc)
Bulletin board functions as a community where one can post comments and files at the same time. Each post has views, likes, and comments.
Comments can be added if you are logged in, and updated or deleted if you are the owner of the comment.
Also, nested comments are available for up to the depth of 5.


Review Forum:
![image](https://github.com/govltjsdnd24/Recrip_Private/assets/38126462/f44a77ae-6a22-4abe-893b-495a198d3582)
Review Forum is similar to the Bulletin Board, except that they can only be created via Course Management page and that they accept images 
instead of other files. Purpose wise, Review Forum serves a purpose of evaluating trip courses, whilst Bulletin Board accepts all sorts of posts.


Map:
![image](https://github.com/govltjsdnd24/Recrip_Private/assets/38126462/a522cdfa-1f9e-4c5a-82ba-269858f107e0)
Map for selecting your tourist sites. One can filter the result by using the dropdown menu and the text box.
When you select the desired site, it gets added to the cart, and from the cart, you can determined if they get to the confirmed course list.
If the user desires, he/she can print out the path among the course (the most ideal one).


Course Management:
![image](https://github.com/govltjsdnd24/Recrip_Private/assets/38126462/7a63870e-cfcb-413d-995b-2dc9f001d161)
One can manage their courses that they created at the Map page. Each site is displayed according to the order that they were selected.
One can make reviews about the course that one made.


Course Review:
![image](https://github.com/govltjsdnd24/Recrip_Private/assets/38126462/165f2376-10c3-43dd-abb1-4f827821e24b)
This page is followed by the Course Management page. One can leave rating, title, and content for their review of the course.




