package control;

import visual.ChatWindow;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyPair;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bouncycastle.crypto.util.PublicKeyFactory;

public class TCPControl {

    /**
     * @return the crypto
     */
    public static RSAControl getCrypto() {
        return crypto;
    }

    // Usa porta 22008, como referência ao 2° semestre de 2008
    final private int PORT = 22008;
    private ClientThread clientHandler;
    private Socket clientSocket;
    private ServerThread serverHandler;
    private ServerSocket serverSocket; // esse socket só aguarda a conexão do cliente
    private Socket connectedServerSocket; // esse é o socket usado de fato
    private boolean server = false;
    private boolean connected = false;
    private static RSAControl crypto = new RSAControl(); // controle de criptografia RSA

    // Tenta conectar o cliente no servidor
    public boolean connect(String host) throws IOException {
        clientSocket = new Socket(host, PORT);
        clientHandler = new ClientThread(clientSocket, this);
        clientHandler.start();
        return clientSocket.isConnected();
    }

    // Liga ou desliga o servidor
    public void listen(boolean ligar) throws IOException {
        if (ligar) {
            serverSocket = new ServerSocket(PORT);
            serverHandler = new ServerThread(serverSocket, this);
            serverHandler.start();
        } else {
            serverHandler.stop();
            serverSocket.close();
        }
    }

    // Envia mensagem: cliente>servidor e servidor>cliente
    public void sendMessage(String message) {

        if (message.getBytes().length >= 40) {
            ChatWindow.log("GRAY", "[ Erro ] - Mensagem não pode ter mais de 40 caracteres");
        } else {

            try {

                String quemSou;
                String color;
                Socket socket;

                if (server) {
                    socket = connectedServerSocket;
                    quemSou = "Servidor";
                    color = "BLUE";
                } else {
                    socket = clientSocket;
                    quemSou = "Cliente";
                    color = "RED";
                }

                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                if (out != null) {

                    // Criptografar se tenho chave pública do interlocutor
                    if (crypto.hasInterlocutorPublicKey()) {
                        out.writeBytes(crypto.encrypt(message) + "\n");
                    } else {
                        out.writeBytes(message + "\n");
                    }

                    ChatWindow.log(color, quemSou + "> " + message);
                }

            } catch (Exception ex) {
                Logger.getLogger(TCPControl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    // Desconecta e sai
    public void sair() {
        try {
            sendMessage("Bye Bye!");
            Thread.sleep(1000);

            Socket socket = server ? connectedServerSocket : clientSocket;
            socket.close();
            System.exit(0);

        } catch (InterruptedException ex) {
            Logger.getLogger(TCPControl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TCPControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Quando o cliente conecta no servidor, essa conexão é retornada pra essa classe de controle
    public void setServerSocket(Socket connectionSocket) {
        this.connectedServerSocket = connectionSocket;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public boolean isServer() {
        return server;
    }

    public void setServer(boolean isServer) {
        server = isServer;
    }
}

