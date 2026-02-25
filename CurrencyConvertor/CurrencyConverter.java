package CurrencyConvertor;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class CurrencyConverter {

    // Extract exchange rate from JSON response
    public static double getRate(String json, String target) {
        String key = "\"" + target + "\":";
        int index = json.indexOf(key);

        if (index == -1) return -1;

        int start = index + key.length();
        int end = json.indexOf(",", start);

        if (end == -1) {
            end = json.indexOf("}", start);
        }

        return Double.parseDouble(json.substring(start, end).trim());
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("===== Currency Converter =====");

        System.out.print("Enter Base Currency (e.g., USD, INR, EUR): ");
        String base = sc.nextLine().toUpperCase();

        System.out.print("Enter Target Currency (e.g., USD, INR, EUR): ");
        String target = sc.nextLine().toUpperCase();

        System.out.print("Enter Amount: ");
        double amount = sc.nextDouble();

        try {
            String url = "https://open.er-api.com/v6/latest/" + base;

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(
                    request, HttpResponse.BodyHandlers.ofString());

            String json = response.body();

            // Validate API response
            if (!json.contains("\"result\":\"success\"")) {
                System.out.println("❌ Invalid base currency or API error.");
                return;
            }

            double rate = getRate(json, target);

            if (rate == -1) {
                System.out.println("❌ Invalid target currency.");
            } else {
                double convertedAmount = amount * rate;

                System.out.printf("\n✅ Conversion Result:\n");
                System.out.printf("%.2f %s = %.2f %s\n",
                        amount, base, convertedAmount, target);
            }

        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }

        sc.close();
    }
}