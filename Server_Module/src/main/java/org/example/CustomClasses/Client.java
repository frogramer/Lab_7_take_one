package org.example.CustomClasses;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class Client implements Serializable {
    private String login;
    private char[] password;
    private String hashedPassword;
    private boolean signedIn;
    private String salt;
    private static Client client;
    private static Client curClient = new Client();
    private Integer id;
    public Client()
    {

    }
    public Client(String login, String hashedPassword, String salt, boolean signedIn) {
        this.login = login;
        this.hashedPassword = hashedPassword;
        this.salt = salt;
        this.signedIn = signedIn;
    }
    public static void setCurClient(Client curClient1) {
        curClient = curClient1;
    }
    public static Client getCurClient() {
        return curClient;
    }
    public String getLogin1()
    {
        return this.login;
    }
    public static void setCurId(Integer id)
    {
        curClient.setId(id);
    }
    public String getPassword()
    {
        return this.hashedPassword;
    }
    public Integer getId() {
        return this.id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public static Client getClient() {
        return client;
    }
    public static void setClient(Client neededClient)
    {
        client = neededClient;
    }
    public String getLogin() {
        return this.login;
    }
    public String getHashedPassword() {
        return this.hashedPassword;
    }
    public String getSalt() {
        return this.salt;
    }
    public boolean isSignedIn() {
        return this.signedIn;
    }
    public static String hashPassword(String password, byte[] salt) {

        /*System.out.println("unhashed password:" + password);
        System.out.println("salt: " + salt);*/
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-384");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        byte[] passwordBytes = password.getBytes(StandardCharsets.UTF_8);


        byte[] hashedBytes = digest.digest(passwordBytes);


        return Base64.getEncoder().encodeToString(hashedBytes);

    }
    public static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

}

/*package org.example.CustomClasses;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class Client implements Serializable {
    private String login;
    private char[] password;
    private String hashedPassword;
    private boolean signedIn;
    private String salt;
    private static Client client;
    public static Client curClient = new Client();
    private Integer id;
    public Client()
    {

    }
    public Client(String login, String hashedPassword, String salt, boolean signedIn) {
        this.login = login;
        this.hashedPassword = hashedPassword;
        this.salt = salt;
        this.signedIn = signedIn;
    }
    public static void setCurClient(Client curClient1) {
        curClient = curClient1;
    }
    public Client getCurClient() {
        return this.curClient;
    }
    public String getLogin1()
    {
        return this.login;
    }
    public String getPassword()
    {
        return this.hashedPassword;
    }
    public Integer getId() {
        return this.id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public static Client getClient() {
        return client;
    }
    public static void setClient(Client neededClient)
    {
        client = neededClient;
    }
    public String getLogin() {
        return this.login;
    }
    public String getHashedPassword() {
        return this.hashedPassword;
    }
    public String getSalt() {
        return this.salt;
    }
    public boolean isSignedIn() {
        return this.signedIn;
    }
    public void setCurId(Integer id)
    {
        curClient.setId(id);
    }
    public static String hashPassword(String password, byte[] salt) {
        //System.out.println("unhashed password:" + password);
        //System.out.println("salt: " + salt);
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-384");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        byte[] passwordBytes = password.getBytes(StandardCharsets.UTF_8);


        byte[] hashedBytes = digest.digest(passwordBytes);


        return Base64.getEncoder().encodeToString(hashedBytes);

    }
    public static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

}
*/