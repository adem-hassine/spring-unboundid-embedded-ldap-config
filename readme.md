# LDAP Local Configuration

# 

This POC provides information on how to set up using Spring Boot embedded LDAP server configuration and Spring Data LDAP integration.

- Spring Boot Version : `3.0.5`

## **Establishing a connection in Apache Directory Studio**

To establish a connection in Apache Directory Studio for the embedded LDAP, follow these steps:

1. In the Connections section, click on "New Connection" Icon.
2. In the modal that pops up, provide a name for the connection.
3. Enter the hostname as "127.0.0.1", and select the port specified in the **[application.properties](http://application.properties/)** file (default=389).
4. Click "Next" to change the selected authentication method to "No Authentication".