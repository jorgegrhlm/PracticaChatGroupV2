import java.io.*;
import java.net.Socket;

/*
Esta clase maneja a un cliente desde el lado del servidor.
Se ejecuta en un hilo separado y se encarga de leer los mensajes
que manda el cliente y avisarle al servidor para que los mande
a los demás.
También controla cuando el usuario se desconecta del chat.
*/


public class HiloCliente extends Thread {

    private Socket socket;
    private ServidorChat servidor;
    private BufferedReader lector;
    private BufferedWriter escritor;
    private String nombreUsuario;

    public HiloCliente(Socket socket, ServidorChat servidor) {
        this.socket = socket;
        this.servidor = servidor;
    }

    public void run() {
        try {
            lector = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            escritor = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            enviarMensaje("Conectado al servidor");

            nombreUsuario = lector.readLine();

            System.out.println("Nuevo usuario conectado: " + nombreUsuario);

            servidor.agregarCliente(this);

            String usuarios = servidor.obtenerUsuariosConectados();
            if (!usuarios.isEmpty()) {
                enviarMensaje("Usuarios conectados: " + usuarios);
            } else {
                enviarMensaje("No hay usuarios conectados");
            }

            servidor.difundir("Nuevo usuario conectado: " + nombreUsuario, this);

            String mensaje;
            while ((mensaje = lector.readLine()) != null && !mensaje.equals("bye")) {
                servidor.difundir("[" + nombreUsuario + "]: " + mensaje, this);
            }

            servidor.eliminarCliente(this);
            servidor.difundir(nombreUsuario + " saliò de chat.", this);
            socket.close();

        } catch (IOException e) {
            System.out.println("Error en HiloCliente");
        }
    }

    public void enviarMensaje(String mensaje) {
        try {
            escritor.write(mensaje);
            escritor.newLine();
            escritor.flush();
        } catch (IOException e) {
            System.out.println("Error enviando mensaje");
        }
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }
}
