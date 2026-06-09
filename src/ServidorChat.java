import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;


/*
Esta clase es la que hace de servidor del chat.
Se queda esperando que los clientes se conecten
a un puerto.
Cada vez que un cliente se conecta, crea un hilo para poder
atenderlo sin bloquear a los demás.
También guarda quiénes están conectados y se encarga de
mandar los mensajes de un usuario a todos los demás.
*/



public class ServidorChat {

    private int puerto;
    private Set<HiloCliente> clientes;

    public ServidorChat(int puerto) {
        this.puerto = puerto;
        this.clientes = new HashSet<>();
    }

    public void ejecutar() {
        try (ServerSocket servidor = new ServerSocket(puerto)) {
            System.out.println("Servidor escuchando por el puerto: " + puerto);

            while (true) {
                Socket socket = servidor.accept();
                HiloCliente hilo = new HiloCliente(socket, this);
                hilo.start();
            }

        } catch (IOException e) {
            System.out.println("Error en el servidor");
        }
    }

    public synchronized void agregarCliente(HiloCliente cliente) {
        clientes.add(cliente);
    }

    public synchronized void eliminarCliente(HiloCliente cliente) {
        clientes.remove(cliente);
        System.out.println("El usuario " + cliente.getNombreUsuario() + " saliò del chat.");
    }

    public synchronized void difundir(String mensaje, HiloCliente emisor) {
        for (HiloCliente cliente : clientes) {
            if (cliente != emisor) {
                cliente.enviarMensaje(mensaje);
            }
        }
    }

    public synchronized String obtenerUsuariosConectados() {
        StringBuilder stringBuilder = new StringBuilder();
        for (HiloCliente cliente : clientes) {
            if (cliente.getNombreUsuario() != null) {
                stringBuilder.append(cliente.getNombreUsuario()).append(" ");
            }
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Uso: java ServidorChat <puerto>");
            return;
        }

        int puerto = Integer.parseInt(args[0]);
        new ServidorChat(puerto).ejecutar();
    }
}
