/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

/**
 *
 * @author husse
 */
import DB.DataAccessLayer;
import DTOs.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MultiClientServer {

    private final List<ClientHandler> clients = new ArrayList<>();

    public void startServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(5005);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket);

                ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                clients.add(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeClient(ClientHandler clientHandler) {
        clients.remove(clientHandler);
    }

    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;
        private final MultiClientServer server;
        private BufferedReader br;
        private PrintStream ps;

        public ClientHandler(Socket clientSocket, MultiClientServer server) {
            this.clientSocket = clientSocket;
            this.server = server;

            try {
                this.br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                this.ps = new PrintStream(clientSocket.getOutputStream(), true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                String request;
                while ((request = br.readLine()) != null) {
                    
                Gson gson = new Gson();
                JsonObject jsonRequest = gson.fromJson(request, JsonObject.class);
                String request_type = jsonRequest.get("requestType").getAsString();
                
                switch (request_type){
                
                    case "RegisterRequest":
                    RegisterDTO registerDTO = gson.fromJson(request, RegisterDTO.class);
                {
                    try {
                        DataAccessLayer.registerUser(registerDTO);
                    } catch (SQLException ex) {
                        Logger.getLogger(MultiClientServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                    break;
                    
                    case "LoginRequest":
                    //System.out.println(request);
                    LoginDTO loginDTO =  gson.fromJson(request, LoginDTO.class);
                {
                    try {
                         loginDTO = DataAccessLayer.login(loginDTO);
                        //System.out.println(loggedin);
                        String response = gson.toJson(loginDTO, LoginDTO.class);
                        ps.println(response);
         
                    } catch (SQLException ex) {
                        Logger.getLogger(MultiClientServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
                    break;
                    
                    case "EditPassword":
        MyProfileDTO editpass = gson.fromJson(request, MyProfileDTO.class);
        try {
            DataAccessLayer.editPassword(editpass);
            ps.println("Password updated successfully.");
        } catch (SQLException ex) {
            Logger.getLogger(MultiClientServer.class.getName()).log(Level.SEVERE, null, ex);
            ps.println("Failed to update password. Please try again.");
        }
        break;
                  
            case "EditPhone":
                MyProfileDTO editPhone = gson.fromJson(request, MyProfileDTO.class);
                try {
                    DataAccessLayer.editPhone(editPhone);
                    ps.println("Phone number updated successfully.");
                } catch (SQLException ex) {
                    Logger.getLogger(MultiClientServer.class.getName()).log(Level.SEVERE, null, ex);
                    ps.println("Failed to update phone number. Please try again.");
                }
                break;
                     
            case "EditEmail":
                MyProfileDTO editEmail= gson.fromJson(request, MyProfileDTO.class);
                try {
                    DataAccessLayer.editEmail(editEmail);
                    ps.println("Phone number updated successfully.");
                } catch (SQLException ex) {
                    Logger.getLogger(MultiClientServer.class.getName()).log(Level.SEVERE, null, ex);
                    ps.println("Failed to update phone number. Please try again.");
                }
                break;
                
                case "Myprofile":
                        System.out.println(request);
					MyProfileDTO profile;
                profile = gson.fromJson(request, MyProfileDTO.class);
                
                {
                    try {
                        profile = DataAccessLayer.getProfile(profile);
                    } catch (SQLException ex) {
                        Logger.getLogger(MultiClientServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                String response1 = gson.toJson(profile,MyProfileDTO.class );
                ps.println(response1);
                    break;
                    
                    case "FriendListRequest":
                    FriendListDTO friendListDTO = gson.fromJson(request, FriendListDTO.class);
                try {
                    ArrayList<FriendDTO> friends = 
                            DataAccessLayer.getFriends(friendListDTO.getUserName());
                    friendListDTO.setFriends(friends);
                    String response = gson.toJson(friendListDTO, FriendListDTO.class);
                    ps.println(response);
                } catch (SQLException ex) {
                    Logger.getLogger(MultiClientServer.class.getName()).log(Level.SEVERE, null, ex);
                }
                    
                    break;
                        case "getMylist":
                     System.out.println(request); 
                     MyWishItems wishitems =gson.fromJson(request, MyWishItems.class);
                      ArrayList<MyWishItems> MyWishList1 = new ArrayList<>();
                {
                    try {
                        MyWishList1=DataAccessLayer.getMyList(wishitems);
                    } catch (SQLException ex) {
                        Logger.getLogger(MultiClientServer.class.getName()).log(Level.SEVERE, null, ex);
                        }
                }
                      String Response = gson.toJson(MyWishList1, new TypeToken<ArrayList<MyWishItems>>(){}.getType());
                      ps.println(Response);
                      break;
                      
                        case "contributeRequest":
                        ContributeDTO contributeDTO = gson.fromJson(request, ContributeDTO.class);
                        String contributorName = contributeDTO.getContributor();
                        String wishingUser = contributeDTO.getWishingUser();
                        int amount = contributeDTO.getAmount();
                        String itemId = contributeDTO.getItemId();
                        int price =contributeDTO.getPrice();
                        String itemName = contributeDTO.getItemName();
                  
                {
                    try {
                        DataAccessLayer.insertContribution(contributorName,itemId, amount, wishingUser);
                        DataAccessLayer.updateContributingUserBalance(contributorName, amount);
                        DataAccessLayer.updatetCollectedAmount(wishingUser, itemId, amount); 
                        DataAccessLayer.notifyWishingUserAndContributors(contributeDTO);
                    } catch (SQLException ex) {
                        Logger.getLogger(MultiClientServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                break;
                
                 case "RemoveItem":
                        System.out.println(request);
					MyWishItems rmItem;
                rmItem = gson.fromJson(request, MyWishItems.class);
                
                {
                    try {
                        DataAccessLayer.removeItem(rmItem);
                    } catch (SQLException ex) {
                        Logger.getLogger(MultiClientServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                    break;
                            
                 case "Sendmarketitems":
                        ItemListDTO itemList = gson.fromJson(request, ItemListDTO.class);
                        ArrayList<ItemDTO> items =
                                DataAccessLayer.getItems();
                        System.out.println(items.get(0));
                        itemList.setItems(items);
                        String response = gson.toJson(itemList, ItemListDTO.class);
                        ps.println(response);
                break;
                    case "AddItem":
                        WishListDTO additem;
                        additem = gson.fromJson(request, WishListDTO.class);
                {
                    try {
                        DataAccessLayer.addToWishlist(additem);
                    } catch (SQLException ex) {
                        Logger.getLogger(MultiClientServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
				break;

                case "RemoveFriend":
                    RemoveFriendDTO removeFriendDTO = gson.fromJson(request, RemoveFriendDTO.class);
          
                {
                    try {
                        DataAccessLayer.removeFriend(removeFriendDTO);
                    } catch (SQLException ex) {
                        Logger.getLogger(MultiClientServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
                
                 case "FriendRequest":
                        System.out.println(request);
					FriendRequestListDTO friendRequestsDTO = gson.fromJson(request, FriendRequestListDTO.class);
                
                    try {
                       friendRequestsDTO = DataAccessLayer.getRequests(friendRequestsDTO.getUserName());
                       String response2 = gson.toJson(friendRequestsDTO,FriendRequestListDTO.class );
                       ps.println(response2);
                    } catch (SQLException ex) {
                        Logger.getLogger(MultiClientServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                    case "acceptFriend":
                        AcceptFriendDTO accept= gson.fromJson(request, AcceptFriendDTO.class);
                {
                    try {
                        DataAccessLayer.acceptFriend(accept);
                    } catch (SQLException ex) {
                        Logger.getLogger(MultiClientServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                         
                        break;
                    case "DeclineFriend":
                         DeclineFriendDTO decline= gson.fromJson(request, DeclineFriendDTO.class);
                {
                    try {
                        DataAccessLayer.declineFriend(decline);
                    } catch (SQLException ex) {
                        Logger.getLogger(MultiClientServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                        break;
                 case "SearchRequest":
                        SearchFriendDTO search = gson.fromJson(request, SearchFriendDTO.class);
                {
                    try {
                        search = DataAccessLayer.searchFriend(search);
                        String response2 = gson.toJson(search, SearchFriendDTO.class);
                        System.out.println("this is response 2 inside server: " + response2);
                        ps.println(response2);
                    } catch (SQLException ex) {
                        Logger.getLogger(MultiClientServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                   
                }
                    break;
                     case "AddRequest":
                         AddFriendDTO add = gson.fromJson(request , AddFriendDTO.class);
                {
                    try {
                    String Alert = DataAccessLayer.addFriend(add);  
                    ps.println(Alert);
                    } catch (SQLException ex) {
                        Logger.getLogger(MultiClientServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                    
                    break;
                    
                case "RemoveRequest":
                         AddFriendDTO remove_req = gson.fromJson(request , AddFriendDTO.class);
                {
                    try {
                     DataAccessLayer.removeRequest(remove_req);  
                    } catch (SQLException ex) {
                        Logger.getLogger(MultiClientServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }    
                        
                case "myNotifications":
                    
                     NotificationDTO myNotification =gson.fromJson(request, NotificationDTO.class);
                      ArrayList<NotificationDTO> myNotificationList = new ArrayList<>();
                {
                    try {
                        myNotificationList=DataAccessLayer.getMyNotifications(myNotification);
                        System.out.println("myNotificationList.get(0);"); 
                    } catch (SQLException ex) {
                        Logger.getLogger(MultiClientServer.class.getName()).log(Level.SEVERE, null, ex);
                        }
                }
                      String NotificationResponse = gson.toJson(myNotificationList, new TypeToken<ArrayList<NotificationDTO>>(){}.getType());
                      ps.println(NotificationResponse);
                      break;

                }
                
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    // Close resources when the client disconnects
                    br.close();
                    ps.close();
                    clientSocket.close();
                    System.out.println("Client disconnected: " + clientSocket);
                    server.removeClient(this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
