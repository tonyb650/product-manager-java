# Project Manager - Last Practice Assignment

**Assignment Description**

For reference, see graphic: 'ProjectManagerBeltReviewGraphic.png':

***

**Database setup** 

* 3 primary tables and 1 join table 
* users
* projects
* tickets
* users_projects

**Relationships**

* OneToMany user-to-projects (as 'teamLeader' aka: creator/owner) 
* ManyToMany users-to-projects (as 'users' aka: team members)
* OneToMany user-to-tickets (as 'creator')
* OneToMany projects-to-tickets (tickets pertaining to a project)
* I found it helpful to number the relationships as I built the models to keep them straight
 
***

**Strategies Employed**

* Dashboard requires two tables, one with projects owned by currentUser, and one with projects NOT owned by currentUser. I chose to user queries to create these two separate lists rather than using logic in the .jsp files. I think this was the right solution.
* I used @Future annotation to validate the project 'dueDate' which needed to be a future date only. I attempted to do this with logic at first, but it was tricky and time-consuming.
* I also leaned heavily on this annotation for getting dates to work properly: @DateTimeFormat(pattern = "yyyy-MM-dd"). 
* I manually deleted related tickets when deleting a project. I wasn't sure if I could use 'cascade=CascadeType.ALL' to handle this. 
* When editing a project, it turns out you need to re-apply all the relationships before saving: teamLeader, users, tickets. I did this in the Controller, not the service. 
* I am still unclear on the better strategy for handling authorization and logout. For logout, is it better to invalidate session or just set session("id") to null? For authorization, I was torn between having an "isLoggedIn" boolean session attribute vs. checking if "id" attribute ==/!= null

**Service Files**
* In ProjectService, the 'create' and 'destroy' methods both have extra logic to handle relationships. 
* I'm not sure if this logic should be located in the controller or the service. I'm leaning towards that it should  have gone in the controller. In fact, I realize that I put the logic for 'edit' project in the controller so I was definitely inconsistent on this and would fix this is it was worth the time spent.
* When creating a project, we need to 1) retrieve the current user from session and then 2) assign that user as 'teamLeader' to the project then 3) save the project then 4) assign the project to the user's list of projects and finally 5) save the user with the updated list of projects.
* When destroying a project, we need to 1) remove the project from any user's 'projects' list, 2) delete any tickets pertaining to the project, and 3) delete the project itself
* In UserService, the interesting code all relates to registration and login of users. This is all boilerplate from the learn platform

**Controller**
* I think organizing the methods in the controller is helpful, I'm still working out the exact order, but I think grouping by mapping type makes the most sense.
* 1 Get - view pages
* 2 Get - other (such as join/leave team)
* 3 Put (edit)
* 4 Delete
* 5 Post
* 6 Login/register/logout logic at the very end
