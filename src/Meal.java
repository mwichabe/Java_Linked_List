public class Meal implements Comparable<Meal> {
    private String name;
    private int availableQuantity;

    public Meal(String name, int availableQuantity) {
        this.name = name;
        this.availableQuantity = availableQuantity;
    }

    // Getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    @Override
    public int compareTo(Meal otherMeal) {
        return this.name.compareTo(otherMeal.name);
    }
    public void decreaseAvailableQuantity(int quantity) {
        if (quantity > 0 && quantity <= availableQuantity) {
            availableQuantity -= quantity;
        } else {
            System.out.println("Error: Invalid quantity to decrease.");
        }
    }
    public void increaseAvailableQuantity(int quantity) {
        if (quantity > 0 && quantity <= availableQuantity) {
            availableQuantity += quantity;
        } else {
            System.out.println("Error: Invalid quantity to increase.");
        }
    }
}
