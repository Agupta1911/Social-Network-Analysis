import java.util.ArrayList; 
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Iterator;
public class network{
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
                    
    }

                    System.out.print("Please enter interests seperate by commas: ");
                    String[] interests = scanner.nextLine().split(", ");
                    for (String interest : interests) {
                        newUser.addInterests(interest);
                    }

                    System.out.print("Please enter the first message post: ");
                    String post = scanner.nextLine();
                    newUser.addPosts(post);

                    userManager.addUser(newUser);
                    //writes the information to a new file
                    try {
                        writeFile(newUser);
                    } catch (IOException e) {
                        System.out.println("There was an error writing user info to file.");
                    }
                    break;
                case 5:
                    System.out.print("Please enter username to remove: ");
                    username = scanner.nextLine();
                    userManager.removeUser(username);
                    break;
                default:
                    System.out.println("Invalid choice.");
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
                for (String post : posts) {
                    user.addPosts(post);
                }


                for (String friendName : friends) {
                    user.addFriends(friendName);
                }


                userManager.addUser(user);
            }
        }
        }
    
    //writes to a new file when adding a user
    private static void writeFile(UserProfile user) throws IOException {
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
                writer.write(user.getPosts().get(i));
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
    private DoublyLinkedList<String> posts;
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

    public void addPosts(String post) {
        posts.addLast(post);
    }

    public void removePosts(String post) {
        for (int i = 0; i < posts.size(); i++) {
            if (posts.get(i).equals(post)) {
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

    public DoublyLinkedList<String> getPosts() {
        return posts;
    }

    public DoublyLinkedList<String> getFriends() {
        return friends;
    }
}

//usermanager class
class UserManager {
    public DoublyLinkedList<UserProfile> users = new DoublyLinkedList<>();


    public void addUser(UserProfile user) {
        users.addLast(user);
    }

    public void removeUser(String username) {
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
}
