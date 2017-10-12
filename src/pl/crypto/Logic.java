package pl.crypto;

import java.security.SecureRandom;
import java.util.Random;

class Logic {

    static String generateRandomString(int size) {
        String baseSaltLetters = "abcdefghijklmnoprstuwxyz";
        StringBuilder sb = new StringBuilder();
        Random random = new SecureRandom();

        for (int i = 0; i < size; i++) {
            char c = baseSaltLetters.charAt(random.nextInt(baseSaltLetters.length()));
            sb.append(c);
        }

        return sb.toString();
    }

    static String encryptMessage(String message, String salt) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < message.length(); i++) {
            int c = (message.charAt(i) ^ salt.charAt(i)) + 33;
            sb.append((char)c);
        }

        return sb.toString();
    }

    static String decryptMessage(String encryptedMessage, String salt) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < encryptedMessage.length(); i++) {
            int c = (encryptedMessage.charAt(i) - 33) ^ salt.charAt(i);
            sb.append((char)c);
        }

        return sb.toString();
    }
}
