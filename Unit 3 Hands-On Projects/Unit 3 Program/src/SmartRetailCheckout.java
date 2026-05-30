import java.util.Scanner;

public class SmartRetailCheckout {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("=== Smart Retail Checkout ===");

        // TODO 1: Prompt for category (G/E/C/H) and read as char
        System.out.print("Enter category (G=Grocery, E=Electronics, C=Clothing, H=Household): ");
        char category = input.next().toUpperCase().charAt(0);

        // TODO 2: Prompt for quantity (int). Validate: > 0
        int qty = 0;
        do {
            System.out.print("Enter quantity (must be > 0): ");
            qty = input.nextInt();
            if (qty <= 0) {
                System.out.println("Invalid quantity. Please enter a positive integer.");
            }
        } while (qty <= 0);

        // TODO 3: Prompt for unit price (double). Validate: > 0
        double price = 0.0;
        do {
            System.out.print("Enter unit price (must be > 0): ");
            price = input.nextDouble();
            if (price <= 0) {
                System.out.println("Invalid price. Please enter a positive value.");
            }
        } while (price <= 0);

        // TODO 4: Prompt for member status (Y/N) -> boolean isMember
        System.out.print("Are you a member? (Y/N): ");
        char memberInput = input.next().toUpperCase().charAt(0);
        boolean isMember = (memberInput == 'Y');

        // TODO 5: Prompt for clearance (Y/N) -> boolean isClearance
        System.out.print("Is this a clearance item? (Y/N): ");
        char clearanceInput = input.next().toUpperCase().charAt(0);
        boolean isClearance = (clearanceInput == 'Y');

        // TODO 6: Prompt coupon code (String), allow empty
        System.out.print("Enter coupon code (or press Enter to skip): ");
        input.nextLine(); // consume leftover newline
        String coupon = input.nextLine().trim();

        double subtotal = qty * price;

        // TODO 7: Base discount tiers (nested if/else ladder)
        double baseDiscountRate = 0.0;
        if (qty >= 20) {
            baseDiscountRate = 0.12;
        } else if (qty >= 10) {
            baseDiscountRate = 0.07;
        } else if (qty >= 5) {
            baseDiscountRate = 0.03;
        } else {
            baseDiscountRate = 0.0;
        }

        // TODO 8: Member bonus: if isMember AND subtotal >= 200 -> add 0.05
        double discountRate = baseDiscountRate;
        if (isMember && subtotal >= 200) {
            discountRate = discountRate + 0.05;
        }

        // TODO 9: Apply discount (multiple statements in if)
        double discountedSubtotal = subtotal;
        if (discountRate > 0) {
            double discountAmount = subtotal * discountRate;
            discountedSubtotal = subtotal - discountAmount;
        }

        // TODO 10: Coupon rule using AND + NOT:
        // if coupon == "SAVE10" AND NOT clearance -> subtract 10 (min 0)
        if (coupon.equals("SAVE10") && !isClearance) {
            discountedSubtotal = Math.max(0, discountedSubtotal - 10);
        }

        // TODO 11: Tax rate using switch(category)
        double taxRate;
        switch (category) {
            case 'G':
                taxRate = 0.00;
                break;
            case 'E':
                taxRate = 0.08;
                break;
            case 'C':
                taxRate = 0.06;
                break;
            case 'H':
                taxRate = 0.05;
                break;
            default:
                taxRate = 0.07;
                break;
        }

        double tax = discountedSubtotal * taxRate;
        double finalTotal = discountedSubtotal + tax;

        // TODO 12: Risk flag with precedence:
        // (subtotal > 1000 AND NOT member) OR qty > 50 OR (price > 500 AND category == 'E')
        boolean highRisk = (subtotal > 1000 && !isMember) || (qty > 50) || (price > 500 && category == 'E');

        // TODO 13: Conditional operator for risk label
        String riskLabel = highRisk ? "HIGH_RISK" : "NORMAL";

        System.out.println("\n--- Receipt ---");
        System.out.printf("Category: %c%n", category);
        System.out.printf("Qty: %d%n", qty);
        System.out.printf("Unit Price: $%.2f%n", price);
        System.out.printf("Subtotal: $%.2f%n", subtotal);
        System.out.printf("Discount Rate: %.0f%%%n", discountRate * 100);
        System.out.printf("After Discounts: $%.2f%n", discountedSubtotal);
        System.out.printf("Tax Rate: %.0f%%%n", taxRate * 100);
        System.out.printf("Tax: $%.2f%n", tax);
        System.out.printf("Final Total: $%.2f%n", finalTotal);
        System.out.println("Risk Flag: " + riskLabel);

        input.close();
    }
}