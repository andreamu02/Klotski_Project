package org.klotski.coding;

// IMPORT I/O LIBRARIES
import java.io.*;
import java.util.Scanner;
import java.util.Vector;

/**
 * <em>UsernameCollector</em> reads a list of users in users.txt file, placed in resources/users_record.<br>
 * It stores every user and can select one of them.<br>
 * It is also possible to add a new user and write it in the txt file,<br>
 * he stores it in a {@link Vector}.<br>
 * It also gives the list of users, writing before the last used user.
 * @see Vector
 *
 * @version 1.0
 * @since 1.0
 * @author Andrea Mutti
 * @author Alessandro Girlanda
 * @author Alba Casanica
 * @author Matteo Meneghin
 */
public class UsernameCollector {
    /**
     * The vector of the users, where the first position gets the last selected user
     */
    private Vector<String> users;
    /**
     * The selected user.<br>
     * It works well with {@link org.klotski.graphics.LoginPage LoginPage} that<br>
     * gives a visual interface to log in and select a user
     */
    private String selectedUser;

    /**
     * Constructor that tries to read users.txt with {@link UsernameCollector#readUsers()}.<br>
     * If there is no "users.txt" file, it tries to create one.
     * @throws RuntimeException If an unpredictable error occurred
     */
    public UsernameCollector() {
        if (!readUsers()){
            try{
                // IF USERS.TXT DOES NOT EXISTS IT CREATES ONE
                File file = new File("src/main/resources/users_record/users.txt");
                file.createNewFile();
                // RE-CALLS OF THE FUNCTION TO STORE AN EMPTY VECTOR
                if(!readUsers()){
                    throw new RuntimeException("An unpredictable error occurred");
                }
            }catch (IOException e){
                throw new RuntimeException("An unpredictable error occurred 1");
            }
        }
    }

    /**
     * Function called by the constructor to collect every user and store them in a {@link Vector}
     * @return true if no {@link IOException} occurred, false otherwise
     */
    private boolean readUsers() {
        try {
            // TRY TO READ USERS.TXT AND ADD USERS IN THE VECTOR
            selectedUser = null;
            FileReader fileReader = new FileReader("src/main/resources/users_record/users.txt");
            Scanner read = new Scanner(fileReader);
            users = new Vector<>();
            while(read.hasNext()){
                users.add(read.next().toUpperCase());
            }
            read.close();
            fileReader.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    /**
     * Adds one user into the vector and writes it into users.txt.<br>
     * It writes the names uppercase and checks if the file is correct
     * @param user The username to add
     * @throws IllegalArgumentException If the file is in a non-correct written method
     */
    public void addUser(String user) {
        try {
            // ADD USERS WRITING THEM IN THE FILE
            FileWriter fileWriter = new FileWriter("src/main/resources/users_record/users.txt", true);
            if(users.size()==0){
                // THE FIRST ONE WITHOUT "\N" PARAMS
                fileWriter.write(user.toUpperCase());
            }else{
                fileWriter.write("\n" + user.toUpperCase());
            }
            // ADD USERS IN THE VECTOR IN FIRST POSITION
            users.add(0, user.toUpperCase());
            selectedUser = user;
            fileWriter.close();
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Selects the given user as The selected one.<br>
     * If that user is not written in the file it writes it and adds it in the vector, in the first position.<br>
     * It also changes the position of a user in the Vector and places it in the first position.
     * @param selectedUser The username to select
     * @throws IllegalArgumentException If the file is in a non-correct written method, or the user is not in the vector
     */
    public void setSelectedUser(String selectedUser) {
        // IF THE USER IS NOT IN THE VECTOR, THROWS AN EXCEPTION
        if (!users.contains(selectedUser)) {
            throw new IllegalArgumentException();
        }
        this.selectedUser = selectedUser;
        try {
            if (users.indexOf(selectedUser) != 0) {
                // IF USERS IS NOT IN THE FIRST POSITION, REMOVES IT AND ADDS IT IN THE FIRST POSITION, ALSO WRITES IT
                users.remove(selectedUser);
                users.add(0, selectedUser.toUpperCase());
                FileWriter file = new FileWriter("src/main/resources/users_record/users.txt");
                for (int i = 0; i < users.size(); i++) {
                    if (users.size() - 1 == i) {
                        file.write(users.get(i).toUpperCase());
                    } else {
                        file.write(users.get(i).toUpperCase() + "\n");
                    }

                }
                file.close();
            }
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Get the vector of users
     * @return A String-Vector contains all the users
     */
    public Vector<String> getUsers() {
        return users;
    }

    /**
     * Get the selected user
     * @return The selected user
     */
    public String getSelectedUser() {
        return selectedUser;
    }

    /**
     * Boolean function to see if the vector is void
     * @return If the user size is major than 0
     */
    public boolean hasUsers() {
        return users.size() != 0;
    }
}
