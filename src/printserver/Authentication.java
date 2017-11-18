package printserver;

import javax.xml.bind.DatatypeConverter;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.security.*;
import java.io.*;
import java.util.Random;
import java.util.UUID;


class Authentication {
    private MessageDigest md;
    private File file;
    private static final String FILENAME = "PasswordTable.txt";

    Authentication() throws NoSuchAlgorithmException {
        md = MessageDigest.getInstance("SHA-256");
        file = new File(FILENAME);
    }

    //Just used to create a password table, user creation isn't intended.
    void storeLoginInfo(String username, String password) {
        try {
            byte[] randomByte = new byte[16];
            Random random = new Random();
            random.nextBytes(randomByte);

            //Password are hashed since file.
            String passwordSaltHash = DatatypeConverter.printHexBinary(getSHA256Hash((password + DatatypeConverter.printHexBinary(randomByte).toLowerCase()).getBytes()));

            //Salt doesn't have to be hashed.
            String salt = DatatypeConverter.printHexBinary(randomByte).toLowerCase();

            Files.write(file.toPath(), (username + " " + passwordSaltHash + " " + salt + "\n").getBytes(), StandardOpenOption.APPEND);
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
    }

    String login(String username, String password) throws IOException {
        if(verifyLogin(username,password))
            return generateToken();
        else
            return null;
    }

    private boolean verifyLogin(String username, String password) throws IOException {
        try {
            String res = Files.lines(file.toPath()).filter(s -> s.contains(username)).findFirst().orElse(null);
            if(res != null) {
            String[] lineContents = res.split("\\s+");
            String inputPasswordHash = DatatypeConverter.printHexBinary(getSHA256Hash((password+lineContents[2]).getBytes()));
            return lineContents[1].equals(inputPasswordHash);
            } else
                return false;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return false;
    }

    private byte[] getSHA256Hash(byte[] bytes) throws NoSuchAlgorithmException, IOException {
        md.update(bytes);

        byte[] digest = md.digest();
        return digest;
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }

}