public class Pet {

    private static int petCount = 0;
    protected String name;
    protected int ageYears;

    Pet(String name, int ageYears) {
        this.name = name;
        this.ageYears = ageYears;
        petCount++;
    }

    public static int getPetCount() {
        return petCount;
    }

    public double visitCost() {
        return 50.0;
    }

    public String sound() {
        return "...";
    }

    public void feed() {
        System.out.println(name + " is being fed.");
    }

    public void feed(int grams) {
        System.out.println(name + " is being fed " + grams + " grams of food.");
    }

    // the [final] keyword means that this method cannot be overridden in subclasses
    public final String tag() {
        return "Pet[name= " + name + ", ageYears=" + ageYears + ']';
    }

}
