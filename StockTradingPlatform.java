import java.util.*;

class Stock {
    private String stockName;
    private double price;

    public Stock(String stockName, double price) {
        this.stockName = stockName;
        this.price = price;
    }

    public String getStockName() {
        return stockName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

class Transaction {
    private String type;
    private String stockName;
    private int quantity;
    private double amount;

    public Transaction(String type, String stockName,
                       int quantity, double amount) {
        this.type = type;
        this.stockName = stockName;
        this.quantity = quantity;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return type + " | " + stockName +
                " | Qty: " + quantity +
                " | Amount: ₹" + amount;
    }
}

class User {
    private String name;
    private double balance;
    private HashMap<String, Integer> portfolio;
    private ArrayList<Transaction> history;

    public User(String name, double balance) {
        this.name = name;
        this.balance = balance;
        portfolio = new HashMap<>();
        history = new ArrayList<>();
    }

    public void buyStock(String stockName,
                         int quantity,
                         double price) {

        double totalCost = quantity * price;

        if (totalCost <= balance) {

            balance -= totalCost;

            portfolio.put(stockName,
                    portfolio.getOrDefault(stockName, 0)
                            + quantity);

            history.add(new Transaction(
                    "BUY",
                    stockName,
                    quantity,
                    totalCost));

            System.out.println("Stock purchased successfully.");
        }
        else {
            System.out.println("Insufficient balance.");
        }
    }

    public void sellStock(String stockName,
                          int quantity,
                          double price) {

        if (portfolio.containsKey(stockName)
                && portfolio.get(stockName) >= quantity) {

            double totalAmount = quantity * price;

            balance += totalAmount;

            portfolio.put(stockName,
                    portfolio.get(stockName) - quantity);

            history.add(new Transaction(
                    "SELL",
                    stockName,
                    quantity,
                    totalAmount));

            System.out.println("Stock sold successfully.");
        }
        else {
            System.out.println("Not enough shares available.");
        }
    }

    public void showPortfolio() {

        System.out.println("\n----- PORTFOLIO -----");

        if (portfolio.isEmpty()) {
            System.out.println("No stocks purchased yet.");
        }
        else {

            for (String stock : portfolio.keySet()) {

                System.out.println(
                        stock + " : "
                                + portfolio.get(stock)
                                + " shares");
            }
        }

        System.out.println("Available Balance: ₹" + balance);
    }

    public void showHistory() {

        System.out.println("\n----- TRANSACTION HISTORY -----");

        if (history.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }

        for (Transaction transaction : history) {
            System.out.println(transaction);
        }
    }
}

class TradingPlatform {

    private ArrayList<Stock> marketStocks;

    public TradingPlatform() {

        marketStocks = new ArrayList<>();

        marketStocks.add(new Stock("TCS", 3850));
        marketStocks.add(new Stock("INFY", 1620));
        marketStocks.add(new Stock("RELIANCE", 2900));
        marketStocks.add(new Stock("HDFCBANK", 1750));
    }

    public void displayMarketData() {

        System.out.println("\n----- MARKET DATA -----");

        for (Stock stock : marketStocks) {

            System.out.println(
                    stock.getStockName()
                            + " : ₹"
                            + stock.getPrice());
        }
    }

    public Stock findStock(String stockName) {

        for (Stock stock : marketStocks) {

            if (stock.getStockName()
                    .equalsIgnoreCase(stockName)) {

                return stock;
            }
        }

        return null;
    }
}

public class StockTradingPlatform {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        TradingPlatform market =
                new TradingPlatform();

        User user =
                new User("Investor", 100000);

        int choice;

        do {

            System.out.println("\n========== STOCK TRADING PLATFORM ==========");
            System.out.println("1. View Market Data");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. Transaction History");
            System.out.println("6. Exit");

            System.out.print("Enter Choice: ");
            choice = scanner.nextInt();

            switch (choice) {

                case 1:

                    market.displayMarketData();
                    break;

                case 2:

                    System.out.print("Enter Stock Name: ");
                    String buyStock = scanner.next();

                    System.out.print("Enter Quantity: ");
                    int buyQty = scanner.nextInt();

                    Stock stockToBuy =
                            market.findStock(buyStock);

                    if (stockToBuy != null) {

                        user.buyStock(
                                buyStock,
                                buyQty,
                                stockToBuy.getPrice());
                    }
                    else {
                        System.out.println("Stock not found.");
                    }

                    break;

                case 3:

                    System.out.print("Enter Stock Name: ");
                    String sellStock = scanner.next();

                    System.out.print("Enter Quantity: ");
                    int sellQty = scanner.nextInt();

                    Stock stockToSell =
                            market.findStock(sellStock);

                    if (stockToSell != null) {

                        user.sellStock(
                                sellStock,
                                sellQty,
                                stockToSell.getPrice());
                    }
                    else {
                        System.out.println("Stock not found.");
                    }

                    break;

                case 4:

                    user.showPortfolio();
                    break;

                case 5:

                    user.showHistory();
                    break;

                case 6:

                    System.out.println("Thank you for using the platform.");
                    break;

                default:

                    System.out.println("Invalid choice.");
            }

        } while (choice != 6);

        scanner.close();
    }
}
    
