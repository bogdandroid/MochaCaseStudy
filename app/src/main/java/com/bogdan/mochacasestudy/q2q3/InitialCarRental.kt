package com.bogdan.mochacasestudy.q2q3


// Basic program that calculates a statement of a customer's charges at a car rental store.
//
// A customer can have multiple "Rental"s and a "Rental" has one "Car"
// As an ASCII class diagram:
//          Customer 1 ----> * Rental
//          Rental   * ----> 1 Car
//
// The charges depend on how long the car is rented and the type of the car (economy, muscle or supercar)
//
// The program also calculates frequent renter points.
//
//
// Refactor this class how you would see fit.
//
// The actual code is not that important, as much as its structure. You can even use "magic" functions (e.g. foo.sort()) if you want

data class Car(val title: String, val priceCode: PriceCode) {
    enum class PriceCode {
        MUSCLE,
        ECONOMY,
        SUPERCAR,
    }
}

data class Rental(val car: Car, val daysRented: Int)

data class Customer(val name: String) {
    private val rentals = mutableListOf<Rental>()

    fun addRental(arg: Rental) {
        rentals.add(arg);
    }

    fun billingStatement(): String {

        var totalAmount: Double = 0.0
        var frequentRenterPoints = 0

        var result = StringBuilder()
            .append("Rental Record for $name\n")

        rentals.forEach { rental ->
            var currentAmount = 0.0
            when (rental.car.priceCode) {
                Car.PriceCode.MUSCLE -> {
                    currentAmount += 200
                    val rest = (rental.daysRented - 3).toDouble() * 50.0
                    currentAmount += rest.coerceAtLeast(0.0)
                }
                Car.PriceCode.ECONOMY -> {
                    currentAmount += 80.0
                    val rest = (rental.daysRented - 2).toDouble() * 50.0
                    currentAmount += rest.coerceAtLeast(0.0)
                }
                Car.PriceCode.SUPERCAR -> {
                    currentAmount += rental.daysRented.toDouble() * 200.0
                }
            }
            frequentRenterPoints += if (rental.car.priceCode == Car.PriceCode.SUPERCAR && rental.daysRented > 1) 2 else 1
            result.append("\t").append(rental.car.title)
                .append("\t").append(currentAmount)
                .append("\n")
            totalAmount += currentAmount
        }

        //add footer lines
        result.append("Final rental payment owed $totalAmount\n")
        result.append("You received an additional $frequentRenterPoints frequent customer points")
        return result.toString()
    }
}

fun main() {
    val rental1 = Rental(Car("Mustang", Car.PriceCode.MUSCLE), 5)
    val rental2 = Rental(Car("Lambo", Car.PriceCode.SUPERCAR), 20)
    val customer = Customer("Liviu")
    customer.addRental(rental1)
    customer.addRental(rental2)
    println(customer.billingStatement())
}


