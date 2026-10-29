use std::io;

fn main() {
    println!("Guess the number!");

    //random number generator
    let secret_number = rand::random_range(1..=100); // generates a random number between 1 and 100

    let apple = 5; // this is an immutable variable
    let mut banana = 10; // this is a mutable variable

    println!("Please input your guess.");

    let mut guess = String::new(); // this is a mutable variable

    io::stdin()
        .read_line(&mut guess)
        .expect("Failed to read line");

    println!("You guessed: {}", guess);

    if guess.trim().parse::<u32>().unwrap() == secret_number {
        println!("You guessed the correct number!");
    } else {
        println!("You guessed the wrong number! The correct number was: {}", secret_number);
    }

    // Using match to handle the result of parsing the guess
    match guess.trim().parse::<u32>() {
        Ok(num) => {
            if num < secret_number {
                println!("Too small!");
            } else if num > secret_number {
                println!("Too big!");
            } else {
                println!("You guessed the correct number!");
            }
        }
        Err(_) => println!("Please enter a valid number!"),
    }
}
