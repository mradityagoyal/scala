import java.time.LocalDate
import java.time.format.DateTimeFormatter

val runDt = "05/15/2017"
val dtFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy")
val runDate: LocalDate = LocalDate.parse(runDt, dtFormat)