import java.io.*;
import java.net.Socket;

/*
Esta clase se usa del lado del cliente.
Sirve para leer los mensajes que manda el servidor y mostrarlos
por pantalla.
Está en un hilo aparte para que el cliente pueda seguir escribiendo
sin que se bloquee el programa.
*/


public class HiloLectura extends Thread {

    private BufferedReader lector;

    public HiloLectura(Socket socket) {
        try {
            lector = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );
        } catch (IOException e) {
            System.out.println("Error creando lector");
        }
    }

    public void run() {
        try {
            String mensaje;
            while ((mensaje = lector.readLine()) != null) {
                System.out.println(mensaje);
            }
        } catch (IOException e) {
            System.out.println("Conexión cerrada");
        }
    }
}
