public class Cat extends Pet {


    private final boolean indoor;

    Cat(String name, int ageYears, boolean indoor) {
        super(name, ageYears);
        this.indoor = indoor;
    }

    @Override 
    public double visitCost() {
        double cost = super.visitCost();
        if (!indoor) {
            cost += 20.0; // Additional cost for outdoor cats
        }
        return cost;
    }

    @Override
    public String sound() {
        return "Meow!";
    }
}
