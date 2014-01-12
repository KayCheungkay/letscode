package tij4.concurrency;

//: concurrency/CloseResource.java
// Interrupting a blocked task by
// closing the underlying resource.
// {RunByHand}
import static net.mindview.util.Print.print;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CloseResource {
	public static void main(String[] args) throws Exception {
		ExecutorService exec = Executors.newCachedThreadPool();
		ServerSocket server = new ServerSocket(8080);
		InputStream socketInput = new Socket("localhost", 8080)
				.getInputStream();
		exec.execute(new IOBlocked(socketInput));
		exec.execute(new IOBlocked(System.in));
		TimeUnit.MILLISECONDS.sleep(100);
		print("Shutting down all threads");
		exec.shutdownNow();

		TimeUnit.SECONDS.sleep(1);
		print("Closing " + socketInput.getClass().getName());
		socketInput.close(); // Releases blocked thread

		TimeUnit.SECONDS.sleep(1);
		print("Closing " + System.in.getClass().getName());
		System.in.close(); // Releases blocked thread
	}
} /*
 * Output: (85% match) Waiting for read(): Waiting for read(): Shutting down all
 * threads Closing java.net.SocketInputStream Interrupted from blocked I/O
 * Exiting IOBlocked.run() Closing java.io.BufferedInputStream Exiting
 * IOBlocked.run()
 */// :~
