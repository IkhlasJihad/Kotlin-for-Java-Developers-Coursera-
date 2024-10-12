package taxipark

/*
 * Task #1. Find all the drivers who performed no trips.
 */
fun TaxiPark.findFakeDrivers(): Set<Driver> = 
        this.allDrivers.filter{ it -> 
            it !in this.trips.map { it.driver }
        }.toSet()
/*
 * Task #2. Find all the clients who completed at least the given number of trips.
 */
fun TaxiPark.findFaithfulPassengers(minTrips: Int): Set<Passenger> =
        allPassengers.filter{ passenger ->
            trips.count { trip: Trip -> passenger in trip.passengers } >= minTrips
        }.toSet() 

/*
 * Task #3. Find all the passengers, who were taken by a given driver more than once.
 */
fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger> =
        allPassengers.filter { it
            trips.count{ trip -> it in trip.passengers && driver == trip.driver} > 1
        }.toSet()

/*
 * Task #4. Find the passengers who had a discount for majority of their trips.
 */
fun TaxiPark.findSmartPassengers(): Set<Passenger> =
        allPassengers.filter { it ->
            trips.count { trip -> it in trip.passengers && trip.discount != null } >
                    trips.count { trip -> it in trip.passengers && trip.discount == null }
        }.toSet()

/*
 * Task #5. Find the most frequent trip duration among minute periods 0..9, 10..19, 20..29, and so on.
 * Return any period if many are the most frequent, return `null` if there're no trips.
 */
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange? {
    if (trips.isEmpty())
        return null
    val maxDuration = trips.maxOfOrNull { it.duration } ?: 0
    val tripsFreqMap = HashMap<Int, IntRange>()
    
    for (i in 0..maxDuration step 10) {
        val currentRange = IntRange(i, i +9)
        val tripsWithinCurrentRange = trips.count { it.duration in currentRange }
        tripsFreqMap[tripsWithinCurrentRange] = currentRange
    }
    return tripsFreqMap[tripsFreqMap.toSortedMap().lastKey()]
}

/*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 */
fun TaxiPark.checkParetoPrinciple(): Boolean {
    // calculate the TaxiPark income of all trips
    if (trips.isEmpty())        
        return false
    val totalIncome = trips.map { it.cost }.sum()
    val driverToIncomeMap =  allDrivers.associateWith { driver ->
        trips.filter { trip -> driver == trip.driver }
            .map { it.cost }
            .sum()
    }
    var sumIncome = 0.0
    var driversCount = 0
    val sortedMapByIncome = driverToIncomeMap.toList().sortedByDescending { (_, value) -> value}
        .toMap()
    // calculate the 80% of the income starting from the most successful drivers
    for (income in sortedMapByIncome.values) {
        sumIncome += income
        driversCount++
        if (sumIncome >= 0.8 * totalIncome) break
    }
    return driversCount <= allDrivers.size * 0.2
}
