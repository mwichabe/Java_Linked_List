public class Subscriber implements Comparable<Subscriber> {
    private String firstName;
    private String lastName;
    private SortedLinkedList<Meal> subscribedMeals;

    public Subscriber(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.subscribedMeals = new SortedLinkedList<>();
    }

    // Getters and setters

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public SortedLinkedList<Meal> getSubscribedMeals() {
        return subscribedMeals;
    }

    public void setSubscribedMeals(SortedLinkedList<Meal> subscribedMeals) {
        this.subscribedMeals = subscribedMeals;
    }

    // Methods to add and remove meals

    public void addMeal(Meal meal) {
        subscribedMeals.sortedInsert(meal);
    }

    public void removeMeal(Meal meal) {
        subscribedMeals.remove(meal);
    }

    @Override
    public int compareTo(Subscriber otherSubscriber) {
        int lastNameComparison = this.lastName.compareTo(otherSubscriber.lastName);
        if (lastNameComparison != 0) {
            return lastNameComparison;
        } else {
            return this.firstName.compareTo(otherSubscriber.firstName);
        }
    }

    public void addMealToSubscription(Meal meal, int quantity) {
    }
    private String notEnoughMealsLetter;

    public void setNotEnoughMealsLetter(String letter) {
        this.notEnoughMealsLetter = letter;
    }

    public String getNotEnoughMealsLetter() {
        return notEnoughMealsLetter;
    }


    public void removeMealFromSubscription(Meal meal, int quantityToRemove) {
    }

    public int getTotalSubscribedMeals() {
        return 0;
    }
    
}
