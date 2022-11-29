package processor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.TreeMap;

public class OrdersProcessor implements Runnable {
	private HashMap<String, Double> prices = new HashMap<String, Double>();
	private TreeMap<String, Double> orders = new TreeMap<String, Double>();

	private int numOrders, currentOrderNum;

	private String dataFileName, baseFileName, resultFileName, multiThread;

	protected NumberFormat usd = NumberFormat.getCurrencyInstance(Locale.US);

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		OrdersProcessor orderProcessor = new OrdersProcessor();

		System.out.print("Enter item's data file name: ");
		orderProcessor.dataFileName = scanner.next().trim();

		System.out.print("Enter 'y' for multiple threads, any other character otherwise: ");
		orderProcessor.multiThread = scanner.next().trim();

		System.out.print("Enter number of orders to prcoess: ");
		orderProcessor.numOrders = scanner.nextInt();

		System.out.print("Enter order's base filename: ");
		orderProcessor.baseFileName = scanner.next();

		System.out.print("Enter result's filename: ");
		orderProcessor.resultFileName = scanner.next().trim();

		scanner.close();
		if (orderProcessor.multiThread.equals("y")) {
			orderProcessor.multiThreadProcessor();
		} else {
			orderProcessor.singleThreadProcessor();
		}
	}

	public void multiThreadProcessor() {
		long start = System.currentTimeMillis();

		currentOrderNum = 1;

		List<Thread> threadArrayList = new ArrayList<Thread>();
		for (int i = 1; i <= this.numOrders; i++) {
			Thread thread = new Thread(this);
			threadArrayList.add(thread);

			thread.start();
		}
		try {
			for (Thread thread : threadArrayList) {
				thread.join();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		orderSummary(this.orders);

		long end = System.currentTimeMillis();

		System.out.println("Processing time (msec): " + (end - start));
	}

	public void singleThreadProcessor() {
		long start = System.currentTimeMillis();

		currentOrderNum = 1;

		Thread thread = new Thread(this);

		for (int i = 1; i <= this.numOrders; i++) {
			thread = new Thread(this);
			thread.start();
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		orderSummary(this.orders);

		long end = System.currentTimeMillis();

		System.out.println("Processing time (msec): " + (end - start));
	}

	public void readData() {
		try {
			Scanner scanner = new Scanner(new File(dataFileName));
			while (scanner.hasNext()) {
				prices.put(scanner.next(), scanner.nextDouble());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void processOrder(TreeMap<String, Double> map, String clientID) {
		try {
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(resultFileName, true));

			bufferedWriter.append("----- Order details for client with Id: " + clientID + " -----" + "\n");

			double total = 0;

			for (String item : map.keySet()) {
				bufferedWriter.append("Item's name: " + item + ", Cost per item: " + usd.format(prices.get(item))
						+ ", Quantity: " + Math.round(map.get(item)) + ", Cost: "
						+ usd.format(map.get(item) * prices.get(item)) + "\n");

				total += map.get(item) * prices.get(item);
			}

			bufferedWriter.append("Order Total: " + usd.format(total) + "\n");
			bufferedWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void orderSummary(TreeMap<String, Double> map) {
		double total = 0;
		try {
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(resultFileName, true));
			bufferedWriter.append("***** Summary of all orders *****" + "\n");
			for (String item : map.keySet()) {
				bufferedWriter.append("Summary - Item's name: " + item + ", Cost per item: "
						+ usd.format(prices.get(item)) + ", Number sold: " + Math.round(map.get(item))
						+ ", Item's Total: " + usd.format(map.get(item) * prices.get(item)) + "\n");

				total += map.get(item) * prices.get(item);
			}
			bufferedWriter.append("Summary Grand Total: " + usd.format(total) + "\n");
			bufferedWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		readData();

		String linesRead = null;

		synchronized (this) {
			File file = new File(baseFileName + currentOrderNum + ".txt");

			TreeMap<String, Double> singleOrder = new TreeMap<String, Double>();

			try {
				FileInputStream fileInput = new FileInputStream(file);

				InputStreamReader inputReader = new InputStreamReader(fileInput);

				@SuppressWarnings("resource")
				BufferedReader bufferedInputReader = new BufferedReader(inputReader);

				String clientID = bufferedInputReader.readLine().substring(10);

				System.out.println("Reading order for client with id: " + clientID);

				while ((linesRead = bufferedInputReader.readLine()) != null) {
					String itemName = linesRead.substring(0, linesRead.indexOf(" "));

					if (singleOrder.containsKey(itemName)) {
						singleOrder.put(itemName, singleOrder.get(itemName) + 1);

					} else {
						singleOrder.put(itemName, 1.00);
					}

					if (orders.containsKey(itemName)) {
						orders.put(itemName, orders.get(itemName) + 1);
					} else {
						orders.put(itemName, 1.00);
					}

				}
				processOrder(singleOrder, clientID);
				currentOrderNum++;

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}