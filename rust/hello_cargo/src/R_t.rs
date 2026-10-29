fn main() {
    // This is a comment
    println!("Hello, world!");

    // the is math
    let x = 5;
    let y = 10;
    let z = x + y;
    // {} are placeholders for values
    println!("The sum of {} and {} is {}", x, y, z);

    // for loop
    for i in 0..5 {
        println!("The value of i is: {}", i);
}

    //while loop
    let mut count = 0;
    while count < 5 {
        println!("Count is: {}", count);
        count += 1;
    }

    // if statement
    let number = 7;
    if number < 5 {
        println!("The number is less than 5");
    } else if number == 5 {
        println!("The number is equal to 5");
    } else {
        println!("The number is greater than 5");
    }

    // match statement this is the same as a switch statement in other languages
    let day = 3;
    match day {
        1 => println!("Monday"),
        2 => println!("Tuesday"),
        3 => println!("Wednesday"),
        4 => println!("Thursday"),
        _ => println!("Invalid day"),
    }

    // function call
    
    let result = add(5, 10);
    println!("The result of adding 5 and 10 is: {}", result)
   

   // parent class and subclass
    let animal = Animal::new("Dog");


    // method 
    

    // method call
    println!("Animal name: {}", animal.get_name());

}
