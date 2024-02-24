import java.time.LocalDate
import java.util.Scanner

data class SleepData(
    val date: LocalDate,
    val amountSleptHours: Double
)

class SleepTracker {
    private val sleepEntries: MutableList<SleepData> = mutableListOf()


    fun addManualSleepEntry(date: LocalDate, hoursSlept: Double) {
        sleepEntries.add(SleepData(date, hoursSlept))
    }


    fun isSleepAdequate(sleepData: SleepData): Boolean {
        return sleepData.amountSleptHours in 7.0..9.0
    }

    fun checkAndPrintSleepAdequacy(date: LocalDate, hoursSlept: Double) {
        val sleepData = SleepData(date, hoursSlept)
        if (isSleepAdequate(sleepData)) {
            println("You had an adequate amount of sleep on $date.")
        } else {
            println("Your sleep duration on $date was not adequate.")
        }
    }
}

fun main() {
    val sleepTracker = SleepTracker()
    val scanner = Scanner(System.`in`)

    println("Enter the date (YYYY-MM-DD):")
    val dateInput = scanner.nextLine()
    val date = LocalDate.parse(dateInput)

    println("Enter amount of sleep in hours (e.g., 8.5):")
    val hoursSlept = scanner.nextDouble()

    sleepTracker.checkAndPrintSleepAdequacy(date, hoursSlept)
}
// firebase implementation TBD:
// import com.google.firebase.firestore.FirebaseFirestore
//import java.time.LocalDate

//data class SleepData(
//    val date: String,
//    val amountSleptHours: Double
//)

//class SleepTracker {
//    private val db = FirebaseFirestore.getInstance()

//    fun addManualSleepEntry(date: LocalDate, hoursSlept: Double) {
//        val sleepData = SleepData(date.toString(), hoursSlept)
//        db.collection("sleepData").add(sleepData)
//            .addOnSuccessListener { documentReference ->
//                println("DocumentSnapshot added with ID: ${documentReference.id}")
//        }
//            .addOnFailureListener { e ->
//                println("Error adding document $e")
//          }
//   }

//    fun isSleepAdequate(sleepData: SleepData): Boolean {
//        return sleepData.amountSleptHours in 7.0..9.0
//    }
//}