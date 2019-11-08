package com.example.mhbesample.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Base64;
import android.webkit.URLUtil;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StaticUtil {


    /*public static void showPasswordIconTouch(boolean isPasswordShown, MotionEvent motionEvent, EditText editText)
    {
        final int DRAWABLE_LEFT = 0;
        final int DRAWABLE_TOP = 1;
        final int DRAWABLE_RIGHT = 2;
        final int DRAWABLE_BOTTOM = 3;
        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            if (isPasswordShown)
            {
                if (motionEvent.getRawX() >= (editText.getRight() - editText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                    // TODO Show password again
                    editText.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.vector_black_eye,0);
                    editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                isPasswordShown = false;
            }
            else
            {
                if (motionEvent.getRawX() >= (editText.getRight() - editText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                    // TODO Show password again
                    editText.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.vector_black_eye_off,0);
                    editText.setInputType(InputType.TYPE_CLASS_TEXT);
                }
                isPasswordShown = true;
            }
        }
    }*/


    /**
     *This is a utility method which facilitate to validate password pattern.
     * @param password is a String value which will must be matched with the password pattern mentioned.
     * @return it will return true if password pattern is valid otherwise false.
     */
        public static boolean isValidPassword(final String password) {
            Pattern pattern;
            Matcher matcher;
            final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
            pattern = Pattern.compile(PASSWORD_PATTERN);
            matcher = pattern.matcher(password);

            return matcher.matches();

        }

        /**
         * Convert byte array to hex string
         * @param bytes
         * @return
         */
        public static String bytesToHex(byte[] bytes) {
            StringBuilder sbuf = new StringBuilder();
            for(int idx=0; idx < bytes.length; idx++) {
                int intVal = bytes[idx] & 0xff;
                if (intVal < 0x10) sbuf.append("0");
                sbuf.append(Integer.toHexString(intVal).toUpperCase());
            }
            return sbuf.toString();
        }

        /**
         * Get utf8 byte array.
         * @param str
         * @return  array of NULL if error was found
         */
        public static byte[] getUTF8Bytes(String str) {
            try { return str.getBytes(StandardCharsets.UTF_8); } catch (Exception ex) { return null; }
        }

        /**
         * Load UTF8withBOM or any ansi text file.
         * @param filename
         * @return
         * @throws java.io.IOException
         */
        public static String loadFileAsString(String filename) throws java.io.IOException {
            final int BUFLEN=1024;
            BufferedInputStream is = new BufferedInputStream(new FileInputStream(filename), BUFLEN);
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream(BUFLEN);
                byte[] bytes = new byte[BUFLEN];
                boolean isUTF8=false;
                int read,count=0;
                while((read=is.read(bytes)) != -1) {
                    if (count==0 && bytes[0]==(byte)0xEF && bytes[1]==(byte)0xBB && bytes[2]==(byte)0xBF ) {
                        isUTF8=true;
                        baos.write(bytes, 3, read-3); // drop UTF8 bom marker
                    } else {
                        baos.write(bytes, 0, read);
                    }
                    count+=read;
                }
                return isUTF8 ? new String(baos.toByteArray(), StandardCharsets.UTF_8) : new String(baos.toByteArray());
            } finally {
                try{ is.close(); } catch(Exception ex){}
            }
        }

        /**
         * Returns MAC address of the given interface name.
         * @param interfaceName eth0, wlan0 or NULL=use first interface
         * @return  mac address or empty string
         */
        public static String getMACAddress(String interfaceName) {
            try {
                List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
                for (NetworkInterface intf : interfaces) {
                    if (interfaceName != null) {
                        if (!intf.getName().equalsIgnoreCase(interfaceName)) continue;
                    }
                    byte[] mac = intf.getHardwareAddress();
                    if (mac==null) return "";
                    StringBuilder buf = new StringBuilder();
                    for (int idx=0; idx<mac.length; idx++)
                        buf.append(String.format("%02X:", mac[idx]));
                    if (buf.length()>0) buf.deleteCharAt(buf.length()-1);
                    return buf.toString();
                }
            } catch (Exception ex) { } // for now eat exceptions
            return "";
        /*try {
            // this is so Linux hack
            return loadFileAsString("/sys/class/net/" +interfaceName + "/address").toUpperCase().trim();
        } catch (IOException ex) {
            return null;
        }*/
        }

        /**
         * Get IP address from first non-localhost interface
         * @param useIPv4  true=return ipv4, false=return ipv6
         * @return  address or empty string
         */
        public static String getIPAddress(boolean useIPv4) {
            try {
                List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
                for (NetworkInterface intf : interfaces) {
                    List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                    for (InetAddress addr : addrs) {
                        if (!addr.isLoopbackAddress()) {
                            String sAddr = addr.getHostAddress();
                            //boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                            boolean isIPv4 = sAddr.indexOf(':')<0;

                            if (useIPv4) {
                                if (isIPv4)
                                    return sAddr;
                            } else {
                                if (!isIPv4) {
                                    int delim = sAddr.indexOf('%'); // drop ip6 zone suffix
                                    return delim<0 ? sAddr.toUpperCase() : sAddr.substring(0, delim).toUpperCase();
                                }
                            }
                        }
                    }
                }
            } catch (Exception ex) { } // for now eat exceptions
            return "";
        }


    /**
     * This is a method which will return a single string result, which is appended all String inputs with the delimiter as input.
     * e.g. Like you can separate a name by " " OR You can append combination of addresses by "," etc.
     * @param delimiter is a {@link CharSequence} value used to append string inputs.
     * @param strings can be a String array to be appended.
     * @return
     */
    public static String joinStringByToken(String delimiter, String... strings) {
        ArrayList<String> list = new ArrayList<>();
        for (String s : strings
                ) {
            if (s != null && !s.equalsIgnoreCase("null"))
                list.add(s);
        }
        return TextUtils.join(delimiter, list);
    }


    /**
     * this method facilitates conversion of a String into Base64 value
     * @param string
     * @return
     */
    public static String base64String(String string) {
        String base64 = "";
        base64 = Base64.encodeToString(String.valueOf(string)
                .getBytes(StandardCharsets.UTF_8), Base64.NO_WRAP);
        return base64;
    }

    /**
     * @param url is the path of media file
     * @return
     */
    public static boolean isImageFile(String url) {
        String mimeType = URLConnection.guessContentTypeFromName(url);
        return mimeType != null && mimeType.startsWith("image");
    }

    /**
     * @param url is the path of media file
     * @return
     */
    public static void isVideoFile(final String url) {

        if (URLUtil.isValidUrl(url)) {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    URLConnection connection = null;
                    try {
                        connection = new URL(url).openConnection();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String contentType = connection.getHeaderField("Content-Type");
                    boolean ifImage = contentType.startsWith("image/");

                    if (!ifImage) {
                        // Check host of url if youtube exists
                        Uri uri = Uri.parse(url);
                        if ("www.youtube.com".equals(uri.getHost())) {
                            boolean isYoutube = true;
                        }
                    }
                }
            };
            thread.start();
        }

        /*String mimeType = URLConnection.guessContentTypeFromName(url);
        return mimeType != null && mimeType.startsWith("video");*/
    }

    public static String encrypt(String input) {
        // This is base64 encoding, which is not an encryption
        return Base64.encodeToString(input.getBytes(), Base64.DEFAULT);
    }

    public static String decrypt(String input) {
        return new String(Base64.decode(input, Base64.DEFAULT));
    }

    private static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;

        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }

    private static String getOS() {
        return "android";
    }

    private static String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

}
