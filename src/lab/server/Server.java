package lab.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static String outLogFile = "out.log";
    private static String errLogFile = "err.log";
    private static int port = 8080;

    private static ServerSocket serverSocket;
    private static Logger logger;

    public static void main(String[] args) {
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
        } catch (UnsupportedEncodingException ignored) {}

        initLogger();

        try {
            serverSocket = new ServerSocket(port);
            logger.log("Сервер запущен и слушает порт " + port + "...");
        } catch (IOException e) {
            logger.err("Ошибка создания серверного сокета (" + e.getLocalizedMessage() + "), приложение будет остановлено.");
            System.exit(1);
        }

        Wardrobe wardrobe = new Wardrobe();

        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                new RequestResolver(clientSocket, wardrobe, logger);

            } catch (IOException e) {
                logger.err("Connection error: " + e.getMessage());
            }
        }
    }

    /**
     * Инициализирует логгер для сервера
     */
    private static void initLogger() {
        try {
            logger = new Logger(
                    new PrintStream(new TeeOutputStream(System.out, new FileOutputStream(outLogFile)), true, "UTF-8"),
                    new PrintStream(new TeeOutputStream(System.err, new FileOutputStream(errLogFile)), true, "UTF-8")
            );
        } catch (IOException e) {
            System.err.println("Ошибка записи логов: " + e.getLocalizedMessage());
            System.exit(1);
        }
    }

}