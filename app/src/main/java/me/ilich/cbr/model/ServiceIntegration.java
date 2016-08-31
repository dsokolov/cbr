package me.ilich.cbr.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ServiceIntegration {

    private static String streamToString(InputStream inputStream) throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
        return result.toString("Windows-1251");
    }

    public String daily() throws IntegrationException {
        try {
            URL url = new URL("http://www.cbr.ru/scripts/XML_daily.asp");
            //URL url = new URL("http://ya.ru");
            //URL url = new URL("http", "cbr.ru", 80, "scripts/XML_daily.asp");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setInstanceFollowRedirects(true);
            connection.setConnectTimeout(4000);
            connection.setReadTimeout(2000);
            connection.setRequestMethod("GET");
            //connection.connect();
            int code = connection.getResponseCode();
            if (code == HttpURLConnection.HTTP_OK) {
                return streamToString(connection.getInputStream());
            } else {
                throw new IntegrationException();
            }
        } catch (IOException e) {
            throw new IntegrationException(e);
        }
    }

    public static class IntegrationException extends Exception {

        public IntegrationException(Exception e) {
            super(e);
        }

        public IntegrationException() {
            super();
        }

    }

}
