public class Main {
    public static void main(String[] args) {

        double totalVisitCost = 0;

        // Create an array of pets with 2 Dogs and 2 Cats
        Pet[] pets = {
            new Dog("Buddy", 3, true),
            new Dog("Max", 5, false),
            new Cat("Whiskers", 2, true),
            new Cat("Shadow", 4, false)
        };

        // Loop through each pet and print details
        for (Pet p : pets) {
            System.out.println(p.tag());
            System.out.println("Sound: " + p.sound());
            System.out.println("Visit Cost: $" + p.visitCost());

            // If the pet is a Dog, call fetch()
            if (p instanceof Dog d) {
                d.fetch();
            }

            totalVisitCost += p.visitCost();
        }

        System.out.println("Total Visit Cost: $" + totalVisitCost);

        // Call feed() and feed(200) on one pet
        pets[0].feed();
        pets[0].feed(200);

        System.out.println("Total number of pets: " + Pet.getPetCount());

    }
}
