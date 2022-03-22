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


class Car {
    companion object {
        val MUSCLE = 2
        val ECONOMY = 0
        val SUPERCAR = 1
    }

    val _title: String
    var _priceCode: Int

    constructor(title: String, priceCode: Int) {
        this._title = title
        this._priceCode = priceCode
    }
    fun getPriceCode(): Int {
        return _priceCode
    }
    fun setPriceCode(arg: Int) {
        this._priceCode = arg
    }
    fun getTitle(): String {
        return _title
    }
}


class Rental {
    private val _car : Car
    private val _daysRented: Int

    constructor(car: Car, daysRented: Int) {
        this._car = car
        this._daysRented = daysRented
    }
    fun getDaysRented(): Int {
        return _daysRented;
    }
    fun getCar() :  Car {
        return _car;
    }
}

class Customer {
    val _name: String
    private var _rentals = ArrayList<Rental>();

    constructor (name: String) {
        this._name = name;
    }

    fun addRental(arg: Rental) {
        _rentals.add(arg);
    }
    fun getName(): String {
        return _name;
    }

    fun billingStatement(): String {

        var totalAmount: Double = 0.0
        var frequentRenterPoints = 0

        var iterator = _rentals.iterator()
        var result = "Rental Record for " + getName() + "\n"

        while (true) {
            try {
                val each = iterator.next()
                var thisAmount: Double = 0.0;

                //determine amounts for each line
                when (each.getCar().getPriceCode()) {
                    Car.ECONOMY -> {
                        thisAmount += 80
                        if (each.getDaysRented() > 2) {
                            thisAmount += ((each.getDaysRented()) - 2).toDouble() * 30.0
                        }
                    }
                    Car.SUPERCAR ->
                        thisAmount += (each.getDaysRented()).toDouble() * 200.0
                    Car.MUSCLE -> {
                        thisAmount += 200
                        if (each.getDaysRented() > 3) {
                            thisAmount += ((each.getDaysRented()).toDouble() - 3) * 50.0
                        }
                    }
                }
                // add frequent renter points
                frequentRenterPoints += 1
                // add bonus for a two day new release rental
                if ((each.getCar().getPriceCode() == Car.SUPERCAR) && each.getDaysRented() > 1) { frequentRenterPoints += 1 }
                //show figures for this rental
                result += "\t" + each.getCar().getTitle() + "\t" + thisAmount.toString() + "\n";
                totalAmount += thisAmount;
            } catch (e: java.util.NoSuchElementException){
                break
            }
        }
        //add footer lines
        result += "Final rental payment owed " + totalAmount.toString() + "\n";
        result += "You received an additional " + frequentRenterPoints.toString() + " frequent customer points"; return result;
    }
}
fun main() {
    val rental1 = Rental(Car("Mustang", Car.MUSCLE), 5)
    val rental2 = Rental(Car("Lambo", Car.SUPERCAR), 20)
    val customer = Customer("Liviu")
    customer.addRental(rental1)
    customer.addRental(rental2)
    println(customer.billingStatement())
}


