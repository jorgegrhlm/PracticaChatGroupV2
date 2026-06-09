import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/*
Esta clase es el cliente del chat.
Desde aquí el usuario se conecta al servidor, escribe su nombre
y puede mandar mensajes.
Además usa otro hilo para poder recibir mensajes del servidor
al mismo tiempo que el usuario escribe, así no se traba el programa.
*/


public class ClienteChat {

    private String host;
    private int puerto;
    private String nombre;

    public ClienteChat(String host, int puerto) {
        this.host = host;
        this.puerto = puerto;
    }

    public void ejecutar() {
        try {
            Socket socket = new Socket(host, puerto);

            new HiloLectura(socket).start();

            BufferedWriter escritor = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream())
            );

            Scanner sc = new Scanner(System.in);

            System.out.print("Ingrese su nombre: ");
            nombre = sc.nextLine();

            escritor.write(nombre);
            escritor.newLine();
            escritor.flush();

            String texto;
            while (!(texto = sc.nextLine()).equals("bye")) {
                escritor.write(texto);
                escritor.newLine();
                escritor.flush();
            }

            socket.close();

        } catch (IOException e) {
            System.out.println("Error en el cliente");
        }
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Use: java -jar Client.jar localhost 5000");
            return;
        }

        new ClienteChat(args[0], Integer.parseInt(args[1])).ejecutar();
    }
}
