public class Dog extends Pet {

    private final boolean needsGrooming;

    Dog(String name, int ageYears, boolean needsGrooming) {
        super(name, ageYears);
        this.needsGrooming = needsGrooming;
    }

    @Override
    public double visitCost() {
        double cost = super.visitCost();
        if (needsGrooming) {
            cost += 30.0; // Additional cost for grooming
        }
        return cost;
    }

    @Override
    public String sound() {
        return "Woof!";
    }

    public void fetch() {
        System.out.println(name + " is fetching the ball!");
    }

}
