# Social-Network-Analysis
A Java program that simulates a system that efficiently handles operations like managing user profiles, friend lists, and analyzing user interactions. 

SOCIAL NETWORK ANALYSIS:

A Java program that simulates a system that efficiently handles operations like managing user profiles, friend lists, and analyzing user interactions. 

Key Features:

Displays all the users in the social network

Displays all the posts of a user-selected individual 

Displays all the friends of a user-selected individual

Adds a new user (Asks for their name, their interests, their friends, and ask them to post their first message). Writes out the new user's information to a file with their name as the name of the file (For example, if the entered name is Dory, then the file should be saved as Dory.txt).  

Removes a user 

Login as a user - They only need to type in their name. There is no need to authenticate with a password. If the user does not exist, then show an appropriate message. If the user exists, inform them of successfully logging in.

Create a post (add it to their list of posts)

See the posts of all their friends

Check if two users are friends - Allow them to enter two names and check to see if they are friends with each other. Show a message accordingly if they are / are not friends. 

See the posts of all your friends - Sequentially (Show posts of the friends in the order that they are stored in that user's friends linked list) 

Show the most popular friend - Identify which friend has the most number of friends and display their username

Write all changes to the user's file when exiting the program. That will ensure any new posts added by the user show up in their list of posts. 

Chronological display of posts (Based on the timestamp of each post - you need to modify the posts to be a post object that contains a string for the post and the time stamp for each post.

Use the Date object in Java for the timestamp. You can compare two Date objects using the compareTo() method.) 

Recommend a friend - Analyzing friends lists of your friends.

Delete a user's account - Remove the user from the User Manager and go through all their friend's lists and remove the user from those lists as well.  

Instructions:
Follow prompts for user inputs.
Reads in the text files from users folder.
