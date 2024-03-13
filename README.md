# MyWish <font size="26">⭐️</font> : A Wishlist Java Application

## Overview
Make a Wish is a Java-based desktop application designed to streamline the creation and management of wish lists. It enables users to add friends, create personal wish lists, view friends' wish lists, and contribute towards purchasing items from friends' wish lists. The application includes a robust notification service to keep users informed of various activities within the platform.

## Key Features
- User Registration and Sign-in
- Adding and Removing Friends
- Accepting and Declining Friend Requests
- Adding and Deleting Items from Wish List
- Viewing Friends' Wish Lists
- Contributing to Friends' Wish Lists
- Recharging Balance Using Credit Cards

## Notification Service
- Users receive notifications for friend requests, contributions to wish list items, and completion of wish list items.
- Notifications are sent for sending or accepting friend requests, contributing to friends' wish list items, when an item that a user contributed to is collected by the owner, and when the item is fully contributed and ready to collect.

## Technical Details
- **Database:** Derby Database (Java DB) with JDBC support.
- **Client Features:** Implemented in Java 8 with JavaFX.
- **Server Features:** Handling client connections and requests.
- **Development Tools:** Apache NetBeans, Object Type Definition (OTD), JavaScript Object Notation (JSON), Scene Builder, CSS.

## Getting Started
To run the application, follow these steps:

1. HTTPS clone the Repo: `git clone https://github.com/jumaa0/MyWish-Application.git` 


2. Open the project in Apache NetBeans.
3. Configure the Derby Database connection:
   - Open the Services window (Ctrl+5).
   - Right-click on Java DB > Properties.
   - Set the Java DB Installation to your Java DB installation directory.
   - Set the Database Location to the location where you want to store the database files.
4. Run the SQL script to create the database schema and tables (found in the `db` directory of the project).
5. Update the database connection URL in the Java code to point to your Derby Database instance.
6. Build and run the application in Apache NetBeans.

## Video Demonstration

https://user-images.githubusercontent.com/jumaa0/MyWish-Application/6359a892e7efee8f09ee605603587fa713fc141d/DEMO./DEMO.Video.mp4



[Link to Video Demonstration](DEMO./DEMO.Video.mp4)

## Usage
- Register or sign in to your account.
- Add friends and view their wish lists.
- Create and manage your wish list.
- Contribute towards purchasing items from friends' wish lists.

## Contribution
Contributions are welcome! Please open an issue to discuss your ideas or submit a pull request with your changes.

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
