/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import DTOs.*;
import com.google.gson.Gson;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.derby.jdbc.ClientDriver;
import server.Main;


/**
 *
 * @author Ahmed Samy
 */
public class DataAccessLayer {
    public static String url = "jdbc:derby://localhost:1527/IWishDB";
    public static void connect() throws SQLException{
    DriverManager.registerDriver(new ClientDriver());
    //conection
    Connection con =DriverManager.getConnection(url);
    
    }
    
    public static int registerUser(RegisterDTO register) throws SQLException{
    int res=-1;
    DriverManager.registerDriver(new ClientDriver());
    Connection con =DriverManager.getConnection(url);
     PreparedStatement pst= con.prepareStatement("Insert into Users values (?,?,?,?,?,?,?)");
     pst.setString(1,register.getUsername());
     pst.setString(2,register.getFullname());
     pst.setString(3,register.getPhone());
     pst.setString(4,register.getEmail());
     pst.setString(5,register.getCreditcard());
     pst.setInt(6,register.getBalance());
     pst.setString(7,register.getPassword());
     
     res=pst.executeUpdate();
     con.close();
     return res;
      
    }
    
    public static LoginDTO login (LoginDTO loginDTO) throws SQLException{
    
    DriverManager.registerDriver(new ClientDriver());
    Connection con =DriverManager.getConnection(url);
    PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM Users "
            + "WHERE user_name = ? AND password = ?");
    preparedStatement.setString(1, loginDTO.getUsername());
    preparedStatement.setString(2, loginDTO.getPassword());
    ResultSet resultSet = preparedStatement.executeQuery();
    
    if (resultSet.next()){
        loginDTO.setBalance(resultSet.getInt("BALANCE"));
        loginDTO.setStatus("Success");
        String query = "SELECT COUNT(*) FROM NOTIFICATION WHERE USER_NAME = ? AND STATUS = 'Unread'";
        PreparedStatement noti_preparedStatement = con.prepareStatement(query);
        noti_preparedStatement.setString(1, loginDTO.getUsername());
        ResultSet noti_resultSet = noti_preparedStatement.executeQuery();
        int unreadCount = 0;
        if (noti_resultSet.next()) {
                    unreadCount = noti_resultSet.getInt(1);
                }
        loginDTO.setNotiCount(unreadCount);
        con.close();
        return loginDTO;
        
    }
    else {
        loginDTO.setStatus("Failed");
        con.close();
        return loginDTO;
    }
    
    }
     public static ArrayList getMyList (MyWishItems wishitems) throws SQLException{
     ArrayList<MyWishItems> MyWishList = new ArrayList<>();
    DriverManager.registerDriver(new ClientDriver());
    Connection con =DriverManager.getConnection(url);
    PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM WISH_LIST w , ITEMS i "
            + "WHERE w.item_id=i.item_id AND user_name = ?  ");
    preparedStatement.setString(1, wishitems.getUsername());
    ResultSet resultSet = preparedStatement.executeQuery();
    while (resultSet.next()) {
                    String itemid = resultSet.getString("ITEM_ID");
                     String itemname = resultSet.getString("ITEM_NAME");
                    String itemDescription = resultSet.getString("DESCRIPTION");
                    int price = resultSet.getInt("PRICE");
                    String category = resultSet.getString("CATEGORY");
                    int collectedAmount = resultSet.getInt("COLLECTED_AMOUNT");
                    MyWishList.add(new MyWishItems(itemid,itemname,itemDescription,price,category,collectedAmount));
                }
    con.close();
    return MyWishList ;

    
    }
     
     
    public static ArrayList<ItemDTO> getWishlist(String userName) throws SQLException{
    DriverManager.registerDriver(new ClientDriver());
    Connection con =DriverManager.getConnection(url);
    String items_query = "SELECT i.ITEM_ID, i.ITEM_NAME, i.DESCRIPTION,"
                            + " i.PRICE, i.CATEGORY, w.COLLECTED_AMOUNT FROM ITEMS i " +
                           "JOIN WISH_LIST w ON i.ITEM_ID = w.ITEM_ID WHERE w.USER_NAME = ? AND i.PRICE!=w.COLLECTED_AMOUNT ";
    PreparedStatement items_preparedStatement = con.prepareStatement(items_query);
    items_preparedStatement.setString(1, userName);
    ResultSet item_resultSet = items_preparedStatement.executeQuery();
    ArrayList<ItemDTO> wishlist = new ArrayList<>();
    while (item_resultSet.next()) {
        ItemDTO item = new ItemDTO();
        item.setItemId(item_resultSet.getString(1));
        item.setItemName(item_resultSet.getString(2));
        item.setDescription( item_resultSet.getString(3));
        item.setCollectedAmount(item_resultSet.getInt(6));
        item.setPrice(item_resultSet.getInt(4));
        item.setCategory(item_resultSet.getString(5));
        wishlist.add(item);
    }
    con.close();
    return wishlist;
    }
    
    public static ArrayList<FriendDTO> getFriends(String userName) throws SQLException {
        ArrayList<FriendDTO> friendsList = new ArrayList<>();

        String query = "SELECT USER_NAME2 FROM FRIENDS WHERE USER_NAME1 = ?";
        DriverManager.registerDriver(new ClientDriver());
        Connection con =DriverManager.getConnection(url);
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, userName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String username = resultSet.getString(1);
                    FriendDTO friend = new FriendDTO();
                    friend.setName(username);
                    ArrayList<ItemDTO> wishlist = getWishlist(username);     
                    friend.setWishlist(wishlist);
                    friendsList.add(friend);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your application's needs
        }
        con.close();
        return friendsList;
    }
    
    public static void insertContribution(String contributorName, String itemId,
            int contributionAmount, String wishingUser) throws SQLException {
        System.out.println("insertContribution");
        String insertContributionQuery = "INSERT INTO CONTRIBUTIONS (CONTRIBUTOR_NAME, "
                + "ITEM_ID, CONTRIBUTION_AMOUNT, WISHING_USER) VALUES (?, ?, ?, ?)";
         DriverManager.registerDriver(new ClientDriver());
         Connection con =DriverManager.getConnection(url);

        try (PreparedStatement preparedStatement = con.prepareStatement(insertContributionQuery)) {
            preparedStatement.setString(1, contributorName);
            preparedStatement.setString(2, itemId);
            preparedStatement.setInt(3, contributionAmount);
            preparedStatement.setString(4, wishingUser);

            preparedStatement.executeUpdate();
        }
    }

    // Update USERS table to deduct contribution amount from the contributing user
    public static void updateContributingUserBalance(String contributorName, int contributionAmount)
            throws SQLException {
        System.out.println("updateContributingUserBalance");
        String updateBalanceQuery = "UPDATE USERS SET BALANCE = BALANCE - ? WHERE USER_NAME = ?";
         DriverManager.registerDriver(new ClientDriver());
        Connection con =DriverManager.getConnection(url);
        try (PreparedStatement preparedStatement = con.prepareStatement(updateBalanceQuery)) {
            preparedStatement.setInt(1, contributionAmount);
            preparedStatement.setString(2, contributorName);

            preparedStatement.executeUpdate();
        }
    }

    // Update WISH_LIST table to update collected amount for the wishing user
    public static void updatetCollectedAmount(String wishingUser, String itemId,
            int collectedAmount) throws SQLException {
         System.out.println("updatetCollectedAmount");
        String updateWishlistQuery = "UPDATE WISH_LIST SET COLLECTED_AMOUNT = COLLECTED_AMOUNT + ? WHERE USER_NAME = ? AND ITEM_ID = ?";
         DriverManager.registerDriver(new ClientDriver());
         Connection con =DriverManager.getConnection(url);
        try (PreparedStatement preparedStatement = con.prepareStatement(updateWishlistQuery)) {
            preparedStatement.setInt(1, collectedAmount);
            preparedStatement.setString(2, wishingUser);
            preparedStatement.setString(3, itemId);

            preparedStatement.executeUpdate();
        }
    }
    
    
    public static void notifyWishingUserAndContributors(ContributeDTO contributeDTO) throws SQLException {
        System.out.println("notify contributers");
    String itemId = contributeDTO.getItemId();
    String itemName = contributeDTO.getItemName();
    String wishingUser = contributeDTO.getWishingUser();
    DriverManager.registerDriver(new ClientDriver());
    Connection con =DriverManager.getConnection(url);
     ArrayList<String> contributorNames = new ArrayList<>();

    // Check if the collected amount equals the price
    if (contributeDTO.getPrice()-contributeDTO.getCollected_amount()==contributeDTO.getAmount()) {
        // Fetch all contributors for the given item and wishing user and get contributers into an array
        String getContributorsQuery = "SELECT DISTINCT CONTRIBUTOR_NAME FROM CONTRIBUTIONS WHERE ITEM_ID = ? AND WISHING_USER = ? ";
        try ( PreparedStatement preparedStatement = con.prepareStatement(getContributorsQuery)) {
            preparedStatement.setString(1, itemId);
            preparedStatement.setString(2, wishingUser);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Notify each contributor
            while (resultSet.next()) {
                String contributorName = resultSet.getString("CONTRIBUTOR_NAME");
                contributorNames.add(resultSet.getString("CONTRIBUTOR_NAME"));
                notifySingleContributor(contributorName, itemName, wishingUser);
            }
        }
        
         //notify the wishing user 
        String notifyWishingUser ="INSERT INTO NOTIFICATION (USER_NAME, MESSAGE, STATUS) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = con.prepareStatement(notifyWishingUser)) {
            preparedStatement.setString(1, contributeDTO.getWishingUser());
            String message = "Congratulations! Your "+ itemName +" is purchased by: " +
            String.join(" , ", contributorNames);
            preparedStatement.setString(2,message);
            preparedStatement.setString(3, "Unread");
            NotificationDTO notificationDTO = new NotificationDTO();
            notificationDTO.setUsername(contributeDTO.getWishingUser());
            notificationDTO.setMessage(message);
            notificationDTO.setStatus("Unread");
            Gson gson = new Gson();
            Main.NS.notifyClients(gson.toJson(notificationDTO, NotificationDTO.class));

            preparedStatement.executeUpdate();
        }
    }
    
    else{
            String notifyWishingUser ="INSERT INTO NOTIFICATION (USER_NAME, MESSAGE, STATUS) VALUES (?, ?, ?)";
         DriverManager.registerDriver(new ClientDriver());
        try (PreparedStatement preparedStatement = con.prepareStatement(notifyWishingUser)) {
            preparedStatement.setString(1, contributeDTO.getWishingUser());
            String message = contributeDTO.getContributor()+" has contributed with "+contributeDTO.getAmount()+" on your "+contributeDTO.getItemName();
            preparedStatement.setString(2, message);
            preparedStatement.setString(3, "Unread");
            NotificationDTO notificationDTO = new NotificationDTO();
            notificationDTO.setUsername(contributeDTO.getWishingUser());
            notificationDTO.setMessage(message);
            notificationDTO.setStatus("Unread");
            Gson gson = new Gson();
            Main.NS.notifyClients(gson.toJson(notificationDTO, NotificationDTO.class));

            preparedStatement.executeUpdate();
        }
        
        
        }
}

private static void notifySingleContributor(String contributorName, String itemName, String wishingUser) throws SQLException {
    String notifyQuery = "INSERT INTO NOTIFICATION (USER_NAME, MESSAGE, STATUS) VALUES (?, ?, ?)";
    try (Connection con = DriverManager.getConnection(url);
         PreparedStatement preparedStatement = con.prepareStatement(notifyQuery)) {
        preparedStatement.setString(1, contributorName);
        String message = "Congratulations! The price for  " + itemName + " wished by " + wishingUser + " is complete.";
        preparedStatement.setString(2, message);
        preparedStatement.setString(3, "Unread");
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setUsername(contributorName);
        notificationDTO.setMessage(message);
        notificationDTO.setStatus("Unread");
        Gson gson = new Gson();
        Main.NS.notifyClients(gson.toJson(notificationDTO, NotificationDTO.class));

        preparedStatement.executeUpdate();
    }
}
    
    public static ArrayList<ItemDTO> getItems() {
        ArrayList<ItemDTO> items = new ArrayList<>();

        try (Connection con =DriverManager.getConnection(url);
             PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM items");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String itemid = resultSet.getString("item_id");
                String itemname = resultSet.getString("item_name");
                String description = resultSet.getString("description");
                int price = resultSet.getInt("price");
                String category = resultSet.getString("category");

                items.add(new ItemDTO(itemid, itemname, description, price, category));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }
    
    public static void removeItem(MyWishItems rmItem) throws SQLException{
          DriverManager.registerDriver(new ClientDriver());
          Connection con =DriverManager.getConnection(url);
          String query = "DELETE FROM WISH_LIST WHERE USER_NAME= ? AND ITEM_ID= ?" ;
          PreparedStatement preparedStatement = con.prepareStatement(query) ;
          preparedStatement.setString(1, rmItem.getUsername());
          preparedStatement.setString(2, rmItem.getItemid());
          preparedStatement.executeUpdate();
          con.close();
     }
    
   public static int removeFriend(RemoveFriendDTO removeFriendDTO) throws SQLException {
    int res = -1;

    Connection con = DriverManager.getConnection(url);

    // Delete the friend relationship from the FRIENDS table
    PreparedStatement deletePreparedStatement1 = con.prepareStatement("DELETE FROM FRIENDS WHERE (USER_NAME1 = ? AND USER_NAME2 = ?)");
    deletePreparedStatement1.setString(1, removeFriendDTO.getUsername());
    deletePreparedStatement1.setString(2, removeFriendDTO.getFriendname());
    res = deletePreparedStatement1.executeUpdate();
    
     PreparedStatement deletePreparedStatement2 = con.prepareStatement("DELETE FROM FRIENDS WHERE (USER_NAME2 = ? AND USER_NAME1 = ? )");
    deletePreparedStatement2.setString(1, removeFriendDTO.getUsername());
    deletePreparedStatement2.setString(2, removeFriendDTO.getFriendname());
    res = deletePreparedStatement2.executeUpdate();

    con.close();

    return res;
}
    
    public static int addToWishlist(WishListDTO additem) throws SQLException{
    int res=-1;
    DriverManager.registerDriver(new ClientDriver());
    Connection con =DriverManager.getConnection(url);
     PreparedStatement pst= con.prepareStatement("Insert into WISH_LIST values (?,?,0)");
     pst.setString(1,additem.getUsername());
     pst.setString(2,additem.getItemid());
     res=pst.executeUpdate();
     con.close();
     return res;
      
    }
    
    public static ArrayList<NotificationDTO> getMyNotifications(NotificationDTO myNotification) throws SQLException{
    DriverManager.registerDriver(new ClientDriver());
    Connection con =DriverManager.getConnection(url);
    String Notifications_query = "SELECT MESSAGE FROM NOTIFICATION WHERE USER_NAME =? AND STATUS = ? ";
    PreparedStatement Notifications_preparedStatement = con.prepareStatement(Notifications_query);
    Notifications_preparedStatement.setString(1, myNotification.getUsername());
    Notifications_preparedStatement.setString(2,"Unread");
    ResultSet Notifications_resultSet = Notifications_preparedStatement.executeQuery();
     ArrayList<NotificationDTO> myNotificationList = new ArrayList<>();
    while (Notifications_resultSet.next()) {
        NotificationDTO Notification = new NotificationDTO();
        Notification.setMessage(Notifications_resultSet.getString(1));
        
        myNotificationList.add(Notification);
    }
   String ReadNotifications_query = "UPDATE NOTIFICATION SET status = 'Read' WHERE USER_NAME = ? ";
    PreparedStatement ReadNotifications_preparedStatement = con.prepareStatement(ReadNotifications_query);
    ReadNotifications_preparedStatement.setString(1, myNotification.getUsername());
    ReadNotifications_preparedStatement.executeUpdate();
    con.close();
    return myNotificationList;
    }
    
    public static FriendRequestListDTO getRequests( String UserName) throws SQLException{
         DriverManager.registerDriver(new ClientDriver());
         Connection con =DriverManager.getConnection(url);
         String query = "select * from REQUESTS Where Sent_to = ? and status = 'pending'" ;
        PreparedStatement preparedStatement = con.prepareStatement(query) ;
        preparedStatement.setString(1, UserName);
        ResultSet rs = preparedStatement.executeQuery();
        FriendRequestListDTO friendRequestsDTO = new FriendRequestListDTO();
        ArrayList<FriendRequestDTO> requests = new ArrayList<>();
        while (rs.next()){
        FriendRequestDTO friend_request = new FriendRequestDTO() ;
        friend_request.setSentFrom(rs.getString("SENT_FROM"));
        friend_request.setSentTo(rs.getString("SENT_TO"));
        friend_request.setStatus(rs.getString("STATUS"));
        requests.add(friend_request);
        }
        friendRequestsDTO.setRequests(requests);
        return friendRequestsDTO;
    }
     public static  void acceptFriend(AcceptFriendDTO acceptFriendDTO) throws SQLException{
         // System.out.println("req test " + acceptFriendDTO.getSent_from());
         DriverManager.registerDriver(new ClientDriver());
          Connection con =DriverManager.getConnection(url);
          String query = "update Requests set status = 'accept' where sent_to = ? and sent_from = ?  " ;
          PreparedStatement preparedStatement = con.prepareStatement(query) ;
          preparedStatement.setString(2, acceptFriendDTO.getSent_from());
          preparedStatement.setString(1, acceptFriendDTO.getSent_to());
          preparedStatement.executeUpdate();
          String fr_query = "insert into friends values( ? , ? ), ( ? , ? )" ;
          PreparedStatement fr_preparedStatement = con.prepareStatement(fr_query) ;
          fr_preparedStatement.setString(1, acceptFriendDTO.getSent_from());
          fr_preparedStatement.setString(2, acceptFriendDTO.getSent_to());
          
          fr_preparedStatement.setString(3, acceptFriendDTO.getSent_to());
          fr_preparedStatement.setString(4, acceptFriendDTO.getSent_from());
          fr_preparedStatement.executeUpdate();
          
          String notify_query ="insert into notification values ( ? , ? , ? )";
          PreparedStatement notify_preparedStatement = con.prepareStatement(notify_query) ;
          notify_preparedStatement.setString(1, acceptFriendDTO.getSent_from());
          notify_preparedStatement.setString(2, acceptFriendDTO.getSent_to()+ " Accepted your friend request");
          notify_preparedStatement.setString(3, "Unread");
          notify_preparedStatement.executeUpdate(); 
          NotificationDTO ndto = new NotificationDTO();
          ndto.setMessage(acceptFriendDTO.getSent_to()+ " Accepted your friend request");
          ndto.setStatus("Unread");
          ndto.setUsername(acceptFriendDTO.getSent_from());
          
          Gson gson = new Gson();
          String noti = gson.toJson(ndto, NotificationDTO.class);
          Main.NS.notifyClients(noti);
          con.close();
     }
     
     public static void declineFriend(DeclineFriendDTO declineFriendDTO) throws SQLException{
          DriverManager.registerDriver(new ClientDriver());
          Connection con =DriverManager.getConnection(url);
          String query = "delete from Requests where sent_to = ? and sent_from = ?  " ;
          PreparedStatement preparedStatement = con.prepareStatement(query) ;
          preparedStatement.setString(2, declineFriendDTO.getSent_from());
          preparedStatement.setString(1, declineFriendDTO.getSent_to());
          preparedStatement.executeUpdate();
          con.close();
     }
     public static  SearchFriendDTO searchFriend(SearchFriendDTO searchFriendDTO) throws SQLException{
         System.out.println("I am in SearchFreind");
          DriverManager.registerDriver(new ClientDriver());
          Connection con =DriverManager.getConnection(url);
          String query = "select * from users where USER_NAME LIKE ? and USER_NAME != ? and USER_NAME not in (select USER_NAME2 from "
                  + "friends where USER_NAME1 = ? )";
          PreparedStatement preparedStatement = con.prepareStatement(query) ;
          System.out.println(searchFriendDTO.getUserName());
          preparedStatement.setString(1,"%" + searchFriendDTO.getUserName()+"%");
          preparedStatement.setString(2, searchFriendDTO.getMyname());
           preparedStatement.setString(3, searchFriendDTO.getMyname());
          ResultSet res = preparedStatement.executeQuery();
          ArrayList<FriendDTO> friends = new ArrayList<>();
          while (res.next()){
              String friend_name = res.getString(1);
              String request_query = "Select STATUS from REQUESTS where SENT_FROM = ? and SENT_TO = ? ";
              PreparedStatement reqStatement = con.prepareStatement(request_query) ;
              reqStatement.setString(1, searchFriendDTO.getMyname());
              reqStatement.setString(2, friend_name);
              ResultSet req_result =  reqStatement.executeQuery();
              FriendDTO friendDTO = new FriendDTO();
              friendDTO.setName(friend_name);
              if (req_result.next()) {
                  friendDTO.setSentRequest(true);
                  System.out.println("it's truuuuuuuuuuuue");
                      }
              else {friendDTO.setSentRequest(false);}
              friends.add(friendDTO);     
          }
          searchFriendDTO.setUsersList(friends);
          con.close();
          return (searchFriendDTO);
     }
     
     /*public static  SearchFriendDTO searchFriend(SearchFriendDTO searchFriendDTO) throws SQLException{
          DriverManager.registerDriver(new ClientDriver());
          Connection con =DriverManager.getConnection(url);
          String query = "select * from users where USER_NAME LIKE ?";
          PreparedStatement preparedStatement = con.prepareStatement(query) ;
          System.out.println(searchFriendDTO.getUserName());
          preparedStatement.setString(1,"%" + searchFriendDTO.getUserName() + "%");
          ResultSet res = preparedStatement.executeQuery();
          ArrayList<FriendDTO> friends = new ArrayList<>();
          while (res.next()){
              String friend_name = res.getString(1);
              FriendDTO friendDTO = new FriendDTO();
              friendDTO.setName(friend_name);
              friends.add(friendDTO);     
          }
          searchFriendDTO.setUsersList(friends);
          con.close();
          return (searchFriendDTO);
     }*/
     
     public static void removeRequest (AddFriendDTO addFriendDTO) throws SQLException{
     
     DriverManager.registerDriver(new ClientDriver());
     Connection con =DriverManager.getConnection(url);
     String query = "delete from requests where sent_from = ? and sent_to = ?";
     PreparedStatement preparedStatement = con.prepareStatement(query);
     preparedStatement.setString(1, addFriendDTO.getUserName());
     preparedStatement.setString(2, addFriendDTO.getFriendName());
     preparedStatement.executeUpdate();
     con.close();
     }
     
     public static String  addFriend(AddFriendDTO addFriendDTO) throws SQLException {
         String Alert = " Your friend request sent successfuly " ;
        DriverManager.registerDriver(new ClientDriver());
          Connection con =DriverManager.getConnection(url);
          
          String check_query = "Select STATUS from REQUESTS where SENT_FROM = ? and SENT_TO = ? ";
          PreparedStatement check_preparedStatement = con.prepareStatement(check_query) ;
          check_preparedStatement.setString(1, addFriendDTO.getUserName());
          check_preparedStatement.setString(2, addFriendDTO.getFriendName());
          ResultSet res = check_preparedStatement.executeQuery();
          
          if (res.next()){
             Alert = " Your friend request is already sent ";
          }
          else{
          String query = "insert into requests values( ? , ? , ? )";
          PreparedStatement preparedStatement = con.prepareStatement(query) ;
          preparedStatement.setString(1, addFriendDTO.getUserName());
          preparedStatement.setString(2, addFriendDTO.getFriendName());
          preparedStatement.setString(3, "pending");
          preparedStatement.executeUpdate();
          
          String notify_query ="insert into notification values ( ? , ? , ? )";
          PreparedStatement notify_preparedStatement = con.prepareStatement(notify_query) ;
          notify_preparedStatement.setString(1, addFriendDTO.getFriendName());
          notify_preparedStatement.setString(2, addFriendDTO.getUserName()+ " Sent you a friend request ");
          notify_preparedStatement.setString(3, "Unread");
          notify_preparedStatement.executeUpdate();
          con.close();
          NotificationDTO ndto = new NotificationDTO();
          ndto.setUsername(addFriendDTO.getFriendName());
          ndto.setMessage(addFriendDTO.getUserName()+ " Sent you a friend request ");
          ndto.setStatus("Unread");
          Gson gson = new Gson();
          String message = gson.toJson(ndto, NotificationDTO.class);
          Main.NS.notifyClients(message);
          
     }
          return Alert;
}
     
     private static boolean isFriendExist(String userName, String friendName) throws SQLException {
    Connection con = DriverManager.getConnection(url);
    String query = "SELECT * FROM FRIENDS WHERE USER_NAME1 = ? AND USER_NAME2 = ?";
    
    try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
        preparedStatement.setString(1, userName);
        preparedStatement.setString(2, friendName);

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            return resultSet.next();
        }
    } finally {
        con.close();
    }
    
    
}
public static MyProfileDTO getProfile(MyProfileDTO profile) throws SQLException {
    DriverManager.registerDriver(new ClientDriver());
    Connection con = DriverManager.getConnection(url);

    try {
        // Fetch user details
        String userQuery = "SELECT * FROM USERS WHERE USER_NAME = ?";
        try (PreparedStatement userStatement = con.prepareStatement(userQuery)) {
            userStatement.setString(1, profile.getUsername());
            ResultSet userResultSet = userStatement.executeQuery();

            if (userResultSet.next()) {
                // Populate user details in the profile DTO
                profile.setName(userResultSet.getString("FULL_NAME"));
                profile.setPhone(userResultSet.getString("PHONE"));
                profile.setEmail(userResultSet.getString("EMAIL"));
                profile.setCredit(userResultSet.getString("CREDIT_CARD"));
                profile.setBalance(userResultSet.getInt("BALANCE"));
                profile.setPassword(userResultSet.getString("PASSWORD"));
                // ... (set other properties as needed)
            }
        }

        // Fetch count from WISH_LIST
        String wishListCountQuery = "SELECT COUNT(*) FROM WISH_LIST WHERE USER_NAME = ?";
        try (PreparedStatement wishListStatement = con.prepareStatement(wishListCountQuery)) {
            wishListStatement.setString(1, profile.getUsername());
            ResultSet wishListResultSet = wishListStatement.executeQuery();

            if (wishListResultSet.next()) {
                // Populate wish list count in the profile DTO
                profile.setWishlist(wishListResultSet.getInt(1));
            }
        }

        // Fetch count from FRIENDS
        String friendsCountQuery = "SELECT COUNT(*) FROM FRIENDS WHERE USER_NAME1 = ? OR USER_NAME2 = ?";
        try (PreparedStatement friendsStatement = con.prepareStatement(friendsCountQuery)) {
            friendsStatement.setString(1, profile.getUsername());
            friendsStatement.setString(2, profile.getUsername());
            ResultSet friendsResultSet = friendsStatement.executeQuery();

            if (friendsResultSet.next()) {
                // Populate friends count in the profile DTO
                profile.setFriends(friendsResultSet.getInt(1));
            }
        }
    } finally {
        con.close();
    }
        return profile;
}
    public static void editPassword(MyProfileDTO myprofileDTO) throws SQLException {
    DriverManager.registerDriver(new ClientDriver());
    Connection con = DriverManager.getConnection(url);

    String updatePasswordQuery = "UPDATE Users SET password = ? WHERE user_name = ?";
    try (PreparedStatement preparedStatement = con.prepareStatement(updatePasswordQuery)) {
        preparedStatement.setString(1, myprofileDTO.getPassword());
        preparedStatement.setString(2, myprofileDTO.getUsername());

        preparedStatement.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace(); // Handle the exception according to your application's needs
    } finally {
        con.close();
    }
}
   
    public static void editPhone(MyProfileDTO myprofileDTO) throws SQLException {
        DriverManager.registerDriver(new ClientDriver());
        Connection con = DriverManager.getConnection(url);


        String updatePhoneQuery = "UPDATE Users SET PHONE = ? WHERE user_name = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(updatePhoneQuery)) {
            preparedStatement.setString(1, myprofileDTO.getPhone());
            preparedStatement.setString(2, myprofileDTO.getUsername());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
    }

    public static void editEmail(MyProfileDTO myprofileDTO) throws SQLException {
        DriverManager.registerDriver(new ClientDriver());
        Connection con = DriverManager.getConnection(url);


        String updatePhoneQuery = "UPDATE Users SET Email = ? WHERE user_name = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(updatePhoneQuery)) {
            preparedStatement.setString(1, myprofileDTO.getEmail());
            preparedStatement.setString(2, myprofileDTO.getUsername());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
    }
     
    /*public static void addFriend(AddFriendDTO addFriendDTO) throws SQLException{
          DriverManager.registerDriver(new ClientDriver());
          Connection con =DriverManager.getConnection(url);
          String query = "insert into requests values( ? , ? , ? )";
          PreparedStatement preparedStatement = con.prepareStatement(query) ;
          preparedStatement.setString(1, addFriendDTO.getUserName());
          preparedStatement.setString(2, addFriendDTO.getFriendName());
          preparedStatement.setString(3, "pending");
          preparedStatement.executeUpdate();
          
          String notify_query ="insert into notification values ( ? , ? , ? )";
          PreparedStatement notify_preparedStatement = con.prepareStatement(notify_query) ;
          notify_preparedStatement.setString(1, addFriendDTO.getFriendName());
          notify_preparedStatement.setString(2, addFriendDTO.getUserName()+ " Sent you a friend request ");
          notify_preparedStatement.setString(3, "Unread");
          notify_preparedStatement.executeUpdate();
          con.close();
    
    }*/
  
}
