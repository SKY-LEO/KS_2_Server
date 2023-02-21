import java.io.*;
import java.net.*;
import java.util.Arrays;


public class Main {

    public static void main(String[] args) {
      /* Создайте буферы для хранения отправляемых и получаемых данных.
Они временно хранят данные в случае задержек связи */
        byte[] receivingDataBuffer = new byte[1024];
        byte[] sendingDataBuffer = new byte[1024];

        try {
            DatagramSocket socket = new DatagramSocket(6000);

            while (true) {
                DatagramPacket inputPacket = new DatagramPacket(receivingDataBuffer, receivingDataBuffer.length);
                socket.receive(inputPacket);
                String receivedData = new String(inputPacket.getData(), inputPacket.getOffset(),
                        inputPacket.getLength());
                System.out.println("Получено сообщение: " + receivedData);
                int len = receivedData.length();
                if(len>15) {
                    receivedData = receivedData.replaceAll("([a-z])", "");
                    len -= receivedData.length();
                }
                String message = receivedData + " : " + len;
                sendingDataBuffer = message.getBytes();
                InetAddress senderAddress = inputPacket.getAddress();
                int senderPort = inputPacket.getPort();
                DatagramPacket outputPacket = new DatagramPacket(
                        sendingDataBuffer, sendingDataBuffer.length,
                        senderAddress, senderPort
                );
                socket.send(outputPacket);
                System.out.println("Сервер отправил сообщение: " + message);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}