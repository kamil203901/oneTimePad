package pl.crypto;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.SecureRandom;
import java.util.Random;

class Logic {

    static String generateRandomString(int size) {
        String baseKeyLetters = "abcdefghijklmnoprstuwxyz";
        StringBuilder sb = new StringBuilder();
        Random random = new SecureRandom();

        for (int i = 0; i < size; i++) {
            char c = baseKeyLetters.charAt(random.nextInt(baseKeyLetters.length()));
            sb.append(c);
        }

        return sb.toString();
    }

    static byte[] generateRandomByte(int size) {
        Random random = new SecureRandom();
        byte[] byteKey = new byte[size];

        random.nextBytes(byteKey);

        return byteKey;
    }

    static String encryptMessage(String message, String key) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < message.length(); i++) {
            int c = (message.charAt(i) ^ key.charAt(i)) + 33;
            sb.append((char)c);
        }

        return sb.toString();
    }

    static String decryptMessage(String encryptedMessage, String key) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < encryptedMessage.length(); i++) {
            int c = (encryptedMessage.charAt(i) - 33) ^ key.charAt(i);
            sb.append((char)c);
        }

        return sb.toString();
    }

    static byte[] convertFileToByteArray(Path path) {
        byte[] fileInBytesArray = null;

        try {
            fileInBytesArray = Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileInBytesArray;
    }

    static void encryptFile(Path path, String newFilename, byte[] byteKey) {
        byte[] file = convertFileToByteArray(path);
        byte[] encryptedFileInBytes = new byte[file.length];

        for (int i = 0; i < file.length; i++) {
            encryptedFileInBytes[i] = (byte)(file[i] ^ byteKey[i]);
        }

        try {
            Files.write(new File(newFilename).toPath(), encryptedFileInBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void decryptFile(Path path, String newFilename, byte[] byteKey) {
        byte[] fileToDecrypt = new byte[byteKey.length];
        byte[] decryptedFile = new byte[byteKey.length];

        try {
            fileToDecrypt = Files.readAllBytes(path);

            for (int i = 0; i < fileToDecrypt.length; i++) {
                decryptedFile[i] = (byte)(fileToDecrypt[i] ^ byteKey[i]);
            }

            Files.write(new File(newFilename).toPath(), decryptedFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static int getFileSize(String filename) {
        int size = 0;

        try {
            size = Files.readAllBytes(new File(filename).toPath()).length;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return size;
    }

    static void saveByteArrayToFile(byte[] bytes, String filename) {
        try {
            Files.write(new File(filename).toPath(), bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
