# PracticaChatGroupV2

Aplicación de chat grupal cliente-servidor desarrollada en Java, usando sockets y programación multihilo. El servidor acepta múltiples clientes simultáneos y reenvía los mensajes de cada usuario al resto de conectados.

## Características

- **Servidor multicliente**: acepta varias conexiones a la vez, creando un hilo dedicado por cada cliente para no bloquear al resto.
- **Difusión de mensajes (broadcast)**: los mensajes de un usuario se reenvían a todos los demás conectados.
- **Cliente con lectura en paralelo**: usa un hilo independiente para recibir mensajes mientras el usuario escribe, evitando que el programa se bloquee.
- **Identificación por nombre**: cada usuario introduce un nombre al conectarse.
- **Comunicación por sockets TCP**.

## Tecnologías

- **Lenguaje**: Java
- **Red**: Sockets TCP (`ServerSocket`, `Socket`)
- **Concurrencia**: Hilos (`Thread`)
- **IDE**: IntelliJ IDEA

## Estructura

```
src/
├── ServidorChat.java   # Servidor: acepta conexiones y coordina el chat
├── HiloCliente.java    # Hilo del servidor que atiende a cada cliente conectado
├── ClienteChat.java    # Cliente: conexión y envío de mensajes
└── HiloLectura.java    # Hilo del cliente para recibir mensajes en paralelo
```

## Cómo ejecutar

1. Compilar las clases:
```bash
   javac -d out/production src/*.java
```
2. Arrancar el servidor (en una terminal):
```bash
   java -cp out/production ServidorChat
```
3. Arrancar uno o varios clientes (en otras terminales):
```bash
   java -cp out/production ClienteChat
```

## Autor

Jorge Gómez — [GitHub](https://github.com/jorgegrhlm) · [LinkedIn](https://www.linkedin.com/in/jorge-gomez-1660a128a)

Proyecto desarrollado en el marco del ciclo de Desarrollo de Aplicaciones Multiplataforma (DAM).
