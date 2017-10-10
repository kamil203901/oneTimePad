package pl.crypto;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Logic {

    static byte[] charsToBytes(char[] chars) {
        byte[] bytes = new byte[chars.length];

        for (int i = 0; i < chars.length; i++) {
            bytes[i] = (byte) chars[i];
        }

        return bytes;
    }

    static byte charToByte(char charV) {
        return (byte) charV;
    }

    static String asciiToBinary(String asciiString) {

        byte[] bytes = asciiString.getBytes();
        StringBuilder binary = new StringBuilder();
        for (byte b : bytes) {
            int val = b;
            for (int i = 0; i < 8; i++) {
                binary.append((val & 128) == 0 ? 0 : 1);
                val = val << 1;
            }
            // binary.append(' ');
        }
        return binary.toString();
    }

    static String asciiCharToBinary(char asciiChar) {

        byte byteChar = (byte) asciiChar;
        StringBuilder binary = new StringBuilder();

        int val = byteChar;
        for (int i = 0; i < 8; i++) {
            binary.append((val & 128) == 0 ? 0 : 1);
            val = val << 1;
        }


        return binary.toString();
    }

    static List<Integer> binaryToIntegers(String binaryString) {
        List<String> strings = new ArrayList<>();
        int index = 0;

        while (index < binaryString.length() - 1) {
            strings.add(binaryString.substring(index, index + 8));
            index += 8;
        }

        List<Integer> integers = new ArrayList<>();
        for (String string : strings) {
            integers.add(Integer.valueOf(string, 2));
        }

        return integers;
    }

    static char[] generateRandomString(int size) {
        char[] chars = "abcdefghijklmnoprstuwxyz".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < size; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }

        String output = sb.toString();

        return output.toCharArray();
    }

    static String encryptMessage(String message, char[] salt) {
        StringBuilder sb = new StringBuilder();

        char[] code = message.toCharArray();

        for (int i = 0; i < message.length(); i++) {
            int c = (code[i] ^ salt[i]);
            sb.append(asciiCharToBinary((char)c));
        }
        String output = sb.toString();

        return output;
    }


    static String decryptMessage(String encryptedMessage, char[] salt) {
        List<Integer> messageList = new ArrayList<>(binaryToIntegers(encryptedMessage));
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < messageList.size(); i++) {
            int c = (messageList.get(i) ^ salt[i]);
            sb.append((char) c);
        }
        String message = sb.toString();

        return message;
    }


//    public static void main(String[] args) {
//
//        char[] salt = generateRandomString(4);
//        System.out.println(salt);
//
//        System.out.println(encryptOrDecryptMessage("0000", salt));
//
//        char[] constSalt = "ibdg".toCharArray();
//        System.out.println(encryptOrDecryptMessage("YRTW", "ibdg".toCharArray()));
//
//    }
}
