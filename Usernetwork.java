import java.util.ArrayList; 
import java.util.Date;
import java.util.Calendar;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Iterator;
public class Usernetwork {
    public static void main(String[] args) {
            UserManager userManager = new UserManager();
            Scanner scanner = new Scanner(System.in);

        //checks if any files were provided
        File folder = new File("users");
        File[] listFiles = folder.listFiles();

        if (listFiles != null) {;
            for (File file : listFiles) {
                try {
                    readFile(file.getName(), userManager);
                } catch (IOException e) {
                    System.out.println("There was an error reading file: " + file.getName());
                }
            }
        } else {
            System.out.println("No files found in the 'users' folder.");
        }
            boolean flag = true;
                while (flag) {
                    System.out.println("Type 1 to display all the users in the social network");
                    System.out.println("Type 2 to display all the posts of a user-selected individual");
                    System.out.println("Type 3 to display all the friends of a user-selected individual");
                    System.out.println("Type 4 to add a new user");
                    System.out.println("Type 5 to remove a user");
                    System.out.println("Type 6 to login to a user");
                    System.out.println("Type 7 to see if two users are friends.");

                    int pick = scanner.nextInt();
                    scanner.nextLine();
                    switch (pick) {
                case 1:
                    userManager.showUsers();
                    break;
                case 2:
                    System.out.print("Please enter the username to see all their posts:");
                    String username = scanner.nextLine();
                    userManager.showPosts(username);
                    break;
                case 3:
                    System.out.print("Please enter username to display all their friends:");
                    username = scanner.nextLine();
                    userManager.showFriends(username);
                    break;
                case 4:
                    System.out.print("Please enter username of new user: ");
                    username = scanner.nextLine();
                    UserProfile newUser = new UserProfile(username);

                    System.out.print("Please enter friends seperate by commas: ");
                    String[] friends = scanner.nextLine().split(", ");
                    for (String friendName : friends) {

                    newUser.addFriends(friendName);
                    //checks to see if the newUser's friends also include the newUser in their friend lists
                    if (UserManager.checkUsers1(friendName)){
                        if(!UserManager.eachFriends(username, friendName)){
                            UserProfile friend = UserManager.getUser(friendName);
                            friend.addFriends(username);
                            String filepath = "users/" + friendName + ".txt";
                            File file = new File(filepath);
                            file.delete();
                            try {
                                Usernetwork.writeFile(friend);
                            } catch (IOException e) {
                                System.out.println("There was an error writing user info to file.");
                            }

                        }

                    }
    }
                    System.out.print("Please enter interests seperate by commas: ");
                    String[] interests = scanner.nextLine().split(", ");
                    for (String interest : interests) {
                        newUser.addInterests(interest);
                    }

                    System.out.print("Please enter the first message post: ");
                    String post = scanner.nextLine();
                    newUser.addPosts(post, username);

                    userManager.addUser(newUser);
                    //writes the information to a new file
                    try {
                        writeFile(newUser);
                    } catch (IOException e) {
                        System.out.println("There was an error writing user info to file.");
                    }
                    UserManager.friendsExist(username);
                    break;
                case 5:
                    System.out.print("Please enter username to remove: ");
                    username = scanner.nextLine();
                    userManager.removeUser(username);
                    System.out.print("Current users: ");
                    userManager.showUsers();
                    break;
                    //part 2 implementation
                case 6:
                UserProfile user = null;
                System.out.print("Please enter username to login: ");
                    username = scanner.nextLine();
                    boolean logged = userManager.checkUsers(username);
                    if (logged){
                        user = UserManager.getUser(username);
                    }
                    while (logged){
                    System.out.println("Type 1 to create a new post");
                    System.out.println("Type 2 to see the posts of all your friends sequentially");
                    System.out.println("Type 3 to to see the posts of all your friends chronologically");
                    System.out.println("Type 4 to see your most popular friend");
                    System.out.println("Type 5 to get a friend recomendation");
                    System.out.println("Type 6 to delete your account");
                    System.out.println("Type 7 to exit");

                    int pick2 = scanner.nextInt();
                    scanner.nextLine();
                    switch (pick2) {
                        case 1:
                        System.out.print("Please enter the new post: ");
                        String post2 = scanner.nextLine();
                        user.addPosts(post2, username);
                        break;
                        case 2:
                        UserManager.showPostsFriends(user);
                        break;
                        case 3:
                        UserManager.showChronologically(user);
                        break;
                        case 4:
                        UserManager.popular(user);
                        break;
                        case 5:
                        UserManager.recommend(user);
                        break;
                        case 6:
                        UserManager.delete(user);
                        System.out.println("Account deleted");
                        logged = false;
                        break;
                        case 7:
                        String filepath = "users/" + user.getUsername() + ".txt";
                        File file = new File(filepath);
                        file.delete();
                        try {
                            writeFile(user);
                        } catch (IOException e) {
                            System.out.println("There was an error writing user info to file.");
                        }
                        logged = false;
                        break;
                        default:
                        System.out.println("Invalid choice.");
                        break;
                    }}
                    break;

                case 7:
                System.out.print("Enter the first username: ");
                String user1 = scanner.nextLine();
                System.out.print("Enter the second username: ");
                String user2 = scanner.nextLine();
                if (userManager.eachFriends(user1, user2)) {
                    System.out.println(user1 + " and " + user2 + " are friends.");
                } else {
                    System.out.println(user1 + " and " + user2 + " are not friends.");
                }
                break;
                default:
                    System.out.println("Invalid choice.");
                    break;
                    }  
                }        
            }
        //reads from files provided as arguments
            private static void readFile(String filename, UserManager userManager) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("users/"+filename))) {
            String line;
            while ((line = reader.readLine()) != null) {

                String username = line.trim();


                String[] interests = reader.readLine().split(", ");


                String[] posts = reader.readLine().split(", ");


                String[] friends = reader.readLine().split(", ");


                UserProfile user = new UserProfile(username);
                for (String interest : interests) {
                    user.addInterests(interest);
                }
                //reads post and timestamps
                for (String post : posts) {
                    String[] postandtime = post.split("-");
                    String message = postandtime[0];
                    String time = postandtime[1];



                    String[] parts = time.split(" ");
        
        String monthString = parts[1];
        int day = Integer.parseInt(parts[2]);
        String[] timeParts = parts[3].split(":");
        int hour = Integer.parseInt(timeParts[0]);
        int minute = Integer.parseInt(timeParts[1]);
        int second = Integer.parseInt(timeParts[2]);
        int year = Integer.parseInt(parts[5]);
        int month = 0;
        switch (monthString) {
            case "Jan":
                month = 0;
                break;
            case "Feb":
                month = 1;
                break;
            case "Mar":
                month = 2;
                break;
            case "Apr":
                month = 3;
                break;
            case "May":
                month = 4;
                break;
            case "Jun":
                month = 5;
                break;
            case "Jul":
                month = 6;
                break;
            case "Aug":
                month = 7;
                break;
            case "Sep":
                month = 8;
                break;
            case "Oct":
                month = 9;
                break;
            case "Nov":
                month = 10;
                break;
            case "Dec":
                month = 11;
                break;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute, second);
        Date date = calendar.getTime();
        user.addPosts2(message, date, username);
                }
                for (String friendName : friends) {
                    user.addFriends(friendName);
                }
                userManager.addUser(user);
            }
        }
        }
    
    //writes to a new file when adding a user
    static void writeFile(UserProfile user) throws IOException {
    String file = "users/" + user.getUsername() + ".txt";

    try (FileWriter writer = new FileWriter(file)) {

        writer.write(user.getUsername() + "\n");
        for (int i = 0; i < user.getInterests().size(); i++) {
                writer.write(user.getInterests().get(i));
                if (i < user.getInterests().size() - 1) {
                    writer.write(", ");
                }
            }
            writer.write("\n");

            for (int i = 0; i < user.getPosts().size(); i++) {
                writer.write(user.getPosts().get(i).toString());
                if (i < user.getPosts().size() - 1) {
                    writer.write(", ");
                }
            }
            writer.write("\n");

            for (int i = 0; i < user.getFriends().size(); i++) {
                writer.write(user.getFriends().get(i));
                if (i < user.getFriends().size() - 1) {
                    writer.write(", ");
                }
            }
            writer.write("\n");
        }
    }
}





//Doubly LinkedList class
    class DoublyLinkedList<T> implements Iterable<T>{
        class Node<T> {
        T data;
        Node<T> prev;
        Node<T> next;

        Node(T data) {
            this.data = data;
            this.prev = null;
            this.next = null;
        }
    }
    public Node<T> head=null;
    public Node<T> end=null;
    public int length = 0;
        

    // add infront of everything
    public void addInfront(T x) {
        Node<T> newNode = new Node<>(x);
        if (head == null) {
            head = newNode;
            end = newNode;
        } else {
            head.prev = newNode;
            newNode.next = head;
            head = newNode;
        }
        length++;
    }

    //add at the last
    public void addLast(T x) {
        Node<T> newNode = new Node<>(x);
        if (end == null) {
            head = newNode;
            end = newNode;
        } else {
            end.next = newNode;
            newNode.prev = end;
            end = newNode;
        }
        length++;
    }

    // add at a specific index
    public void addIndex(int index, T x) {
        if (index < 0 || index > length) throw new IndexOutOfBoundsException();
        if (index == 0) {
            addInfront(x);
            return;
        }
        if (index == length) {
            addLast(x);
            return;
        }
        Node<T> newNode = new Node<>(x);
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        newNode.next = current;
        newNode.prev = current.prev;
        current.prev.next = newNode;
        current.prev = newNode;
        length++;
    }
    public T removeFirst() {
        if (length == 0){
            return null;
        }
        T data = head.data;
        head = head.next;
        if (head != null) 
        {head.prev = null;
        }
        else 
        {end = null;
        }
        length--;
        return data;
    }

    public T removeLast() {
        if (length == 0){
            return null;}
        T data = end.data;
        end = end.prev;
        if (end != null) 
        {end.next = null;
        }
        else 
        {head = null;}
        length--;
        return data;
    }

    public T removeIndex(int index) {
        if (index < 0 || index >= length) throw new IndexOutOfBoundsException();
        if (index == 0) 
        {return removeFirst();}
        if (index == length - 1) {return removeLast();}
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        current.prev.next = current.next;
        current.next.prev = current.prev;
        length--;
        return current.data;
    }

    public T get(int index) {
        if (index < 0 || index >= length) throw new IndexOutOfBoundsException();
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }


    //returns the first node
     public Node getHead() {
            return head;
        }
    //returns the last node.
    public Node getEnd() {
            return end;
        }

    //public void printList(DoublyLinkedList<T> linkedList) {
        //Iterator<T> it = linkedList.iterator(); 
        //while(it.hasNext()){  
            //System.out.print(it.next() + " ");
        //}
        //System.out.println();
    //}
public void printList() {
    Iterator<T> iterator = this.iterator();

    if (!iterator.hasNext()) {
        System.out.println("Empty list provided");
        return;
    }

    while (iterator.hasNext()) {
        System.out.print(iterator.next() + " ");
    }
    System.out.println();
}

    @SuppressWarnings("null")
    public void reverse() {
        Node<T> current = end;
        Node<T> temp = null;
        while (current != null) {
            temp = current.prev;
            current.prev = current.next;
            current.next = temp;
            current = current.next;
        }
            head=end;
            end = current.prev;
        }
    


public int size() {
    return length;
}

@Override
public Iterator<T> iterator() {
return new Iterator<T>() {
        private Node<T> current = head;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            T data = current.data;
            current = current.next;
            return data;
        }
    };
}
    }



class UserProfile {
    private String username;
    private DoublyLinkedList<String> interests;
    private DoublyLinkedList<Post> posts;
    private DoublyLinkedList<String> friends;

    public UserProfile(String username) {
        this.username = username;
        this.interests = new DoublyLinkedList<>();
        this.posts = new DoublyLinkedList<>();
        this.friends = new DoublyLinkedList<>();
    }

    // Add and remove interests
    public void addInterests(String interest) {
        interests.addLast(interest);
    }

    public void removeInterests(String interest) {
        for (int i = 0; i < interests.size(); i++) {
            if (interests.get(i).equals(interest)) {
                interests.removeIndex(i);
                return;
            }
        }
    }

    public void addPosts(String post,String username) {
        Post message = new Post (post, username);
        posts.addLast(message);
    }
    public void addPosts2(String post, Date time, String username) {
        Post message = new Post (post, time, username);
        posts.addLast(message);
    }


    public void removePosts(String post) {
        for (int i = 0; i < posts.size(); i++) {
            if (posts.get(i).getMessage().equals(post)) {
                posts.removeIndex(i);
                return;
            }
        }
    }

    public void addFriends(String friend) {
        friends.addLast(friend);
    }

    public void removeFriends(String friend) {
        for (int i = 0; i < friends.size(); i++) {
            if (friends.get(i).equals(friend)) {
                friends.removeIndex(i);
                return;
            }
        }
    }

    public String getUsername() {
        return username;
    }

    public DoublyLinkedList<String> getInterests() {
        return interests;
    }

    public DoublyLinkedList<Post> getPosts() {
        return posts;
    }

    public DoublyLinkedList<String> getFriends() {
        return friends;
    }
}

//usermanager class
class UserManager {
    public static DoublyLinkedList<UserProfile> users = new DoublyLinkedList<>();


    public void addUser(UserProfile user) {
        users.addLast(user);
    }

    public static void removeUser(String username) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username)) {
                users.removeIndex(i);
                return;
            }
        }
    }

    public void showUsers() {
        for (UserProfile user : users) {
            System.out.println(user.getUsername());
        }
    }

    public void showPosts(String username) {
        for (UserProfile user : users) {
            if (user.getUsername().equals(username)) {
                user.getPosts().printList();
                return;
            }
        }
    }

    public void showFriends(String username) {
        for (UserProfile user : users) {
            if (user.getUsername().equals(username)) {
                user.getFriends().printList();
                return;
            }
        }
    }
    public static boolean checkUsers(String username){
        for (UserProfile user:users){
            if (user.getUsername().equals(username)){
                System.out.println("Successfully Logged In!");
                return true;
            }

        }
        System.out.println("Username doesn't exit");
        return false;
    }
    public static boolean checkUsers1(String username){
        for (UserProfile user:users){
            if (user.getUsername().equals(username)){
                return true;
            }

        }
        return false;
    }
    //returns user from username
    public static UserProfile getUser(String username){
        for (UserProfile user:users){
            if (user.getUsername().equals(username)){
                return user;}

    }
    return null;
  
}
//shows sequentially the post of friends
    public static void showPostsFriends(UserProfile user){
        DoublyLinkedList<String> friends = user.getFriends();
        for (String name: friends){
            UserProfile friend = getUser(name);
            if (friend != null){
                System.out.println("Posts from " + name + ":");
                friend.getPosts().printList();
            }

        }
    }
    //checks if two users are friends with each other
    public static boolean eachFriends(String username, String username1){
        UserProfile user  = getUser(username);
        UserProfile user1  = getUser(username1);
        boolean flag = false;
        boolean flag1 = false;
        if (user!=null && user1 != null){
            DoublyLinkedList<String> friends = user.getFriends();
            DoublyLinkedList<String> friends1 = user1.getFriends();
            for (String name: friends){
                if (name.equals(username1)){
                    flag = true;
                }
            }
            for (String name1: friends1){
                if (name1.equals(username)){
                    flag1 = true;
                }
        
            }
            if (flag && flag1){
                return true;
            }
            
        }
        return false;
    }
    //finds the most popular friend
    public static void popular (UserProfile user){
        UserProfile max=null;
        int maximum = 0;
        DoublyLinkedList<String> friends = user.getFriends();
        for (String name: friends){
            UserProfile friend = getUser(name);
            if (friend!=null){
            if (friend.getFriends().size()>maximum){
                max = friend;
                maximum = friend.getFriends().size();
            }
        }
    }
    if (max == null){
        System.out.println("No Popular Friend");
    }else{
    System.out.println(max.getUsername());
    }
}
//deletes users account
    public static void delete (UserProfile user){
    DoublyLinkedList<String> friends = user.getFriends();
    for (String name: friends){
        UserProfile friend = getUser(name);
        if (friend!=null){
        friend.removeFriends(user.getUsername());
        String filepath = "users/" + name + ".txt";
        File file = new File(filepath);
        file.delete();
        try {
            Usernetwork.writeFile(friend);
        } catch (IOException e) {
            System.out.println("There was an error writing user info to file.");
        }
    }
    }
    removeUser(user.getUsername());
    String filepath = "users/" + user.getUsername() + ".txt";
    File file = new File(filepath);
    file.delete();
}
//gives a recommendation for a friend
    public static void recommend (UserProfile user){
    DoublyLinkedList<String> friends = user.getFriends();
    for (String name: friends){
        UserProfile friend = getUser(name);
        if (friend!=null){
        DoublyLinkedList<String> friendsoffriends = friend.getFriends();
        for (String username: friendsoffriends){
        if (!username.equals(user.getUsername())){
        if (!eachFriends(username, user.getUsername())){
            System.out.println("A friend recommendation is: " + username);
            return;
        }
    }
        }
    }
    }
    System.out.println("There is no friend recommendation ");
}
//shows the posts of friends chronologically
    public static void showChronologically(UserProfile user){
    DoublyLinkedList<String> friends = user.getFriends();
    ArrayList<Post> posts2 = new ArrayList<>();
    for (String name: friends){
        UserProfile friend = getUser(name);
        if (friend!=null){
        DoublyLinkedList<Post> posts = friend.getPosts();
        for (Post post: posts){
            posts2.add(post);
        }
    }
    }
    for (int i = 0; i < posts2.size() - 1; i++) {
        for (int j = 0; j < posts2.size() - i - 1; j++) {
            if (posts2.get(j).getTime().compareTo(posts2.get(j + 1).getTime()) > 0) {
                Post temp = posts2.get(j);
                posts2.set(j, posts2.get(j + 1));
                posts2.set(j + 1, temp);
            }
        }
    }
    for (Post post : posts2) {
        System.out.println("Posts of "+post.getUsername()+":");
        System.out.println(post);

    }
}
//Checks if the NewUser has a friend in their list which has the NewUser in their own friend's list.
    public static void friendsExist(String username){
        UserProfile NewUser = getUser(username);
        for (UserProfile user: users){
            DoublyLinkedList<String> friends = user.getFriends();
            for (String friend: friends){
                if(friend.equals(username)){
                    DoublyLinkedList<String> friendsofuser = NewUser.getFriends();
                    boolean flag = false;
                    for (String friend1:friendsofuser){
                        if(friend1.equalsIgnoreCase(user.getUsername())){
                            flag = true;
                        }
                    }
                    if (flag == false){
                        NewUser.addFriends(user.getUsername());
                        String filepath = "users/" + username + ".txt";
                            File file = new File(filepath);
                            file.delete();
                            try {
                                Usernetwork.writeFile(NewUser);
                            } catch (IOException e) {
                                System.out.println("There was an error writing user info to file.");
                            }
                    }
                }
            }
        }
    }
}
//Post class
class Post{
    private Date time;
    private String message;
    private String username;
    public Post (String message, String username){
        this.time = new Date();
        this.message = message;
        this.username = username;
    }
    public Post (String message, Date time, String username){
        this.time = time;
        this.message = message;
        this.username = username;

    }
    public String getMessage(){
        return message;
    }
    public Date getTime(){
        return time;
    }
    public String getUsername(){
        return username;
    }
        @Override
    public String toString() {
        return message + "-" + time;
    }
}