package design_patterns;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Observable;
import java.util.Observer;

public class ObserverExample {

	public static class EventSource extends Observable implements Runnable {
		public void run() {
			try {
				final InputStreamReader isr = new InputStreamReader(System.in);
				final BufferedReader br = new BufferedReader(isr);
				while (true) {
					String response = br.readLine();
					setChanged();
					notifyObservers(response);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static class ResponseHandler implements Observer {
		private String resp;

		public void update(Observable obj, Object arg) {
			if (arg instanceof String) {
				resp = (String) arg;
				System.out.println("\nReceived Response: " + resp);
			}
		}
	}

	public static void main(String[] args) {
		System.out.println("Enter Text >");
		final EventSource evSrc = new EventSource();
		final ResponseHandler respHandler = new ResponseHandler();
		evSrc.addObserver(respHandler);
		Thread thread = new Thread(evSrc);
		thread.start();
	}

}
