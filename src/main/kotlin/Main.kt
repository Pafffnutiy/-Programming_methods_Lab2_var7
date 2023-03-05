import com.intellij.util.containers.MultiMap
import java.io.File
import kotlin.system.measureNanoTime

val sizes = listOf(5, 100, 500, 1000, 5000, 10000, 50000, 100000)

fun main() {
    File("src/main/resources/time.txt").printWriter().use { writer ->

        fun linearSearch() {
            writer.println("Linear search:")
            for (size in sizes) {
                val team = OlympicTeamReader().readTeamFromJSON("$size.json")
                val target = team.random().sportsType
                val timeToSearch = measureTimeMicros { team.linearSearch(target) }

                writer.println("\t$size: ${timeToSearch}mcs")
            }
        }


        fun binarySearchInSortedTeam() {
            writer.println("\nBinary search in sorted team:")
            for (size in sizes) {
                val team = OlympicTeamReader().readTeamFromJSON("$size.json")
                val sortedTeam = team.sortPyramid()
                val target = team.random().sportsType

                val timeToSearch = measureTimeMicros { sortedTeam.binarySearch(target) }

                writer.println("\t$size: ${timeToSearch}mcs")
            }
        }

        fun binarySearchWithSorting() {
            writer.println("\nBinary search with sorting:")
            for (size in sizes) {
                val team = OlympicTeamReader().readTeamFromJSON("$size.json")
                val target = team.random().sportsType

                val timeToSearch = measureTimeMicros {
                    val sortedTeam = team.sortPyramid()
                    sortedTeam.binarySearch(target)
                }

                writer.println("\t$size: ${timeToSearch}mcs")
            }
        }

        fun searchInMultiMap() {
            writer.println("\nSearch in MultiMap")
            for (size in sizes) {
                val team = OlympicTeamReader().readTeamFromJSON("$size.json")
                val target = team.random().sportsType

                val multimap = MultiMap<String, Sportsman>()
                team.team.forEach { sportsman ->
                    multimap.putValue(sportsman.sportsType, sportsman)
                }

                val timeToSearchInMultiMap = measureNanoTime { multimap[target] }
                writer.println("\t$size: ${timeToSearchInMultiMap}ns")
            }
        }

        linearSearch()
        binarySearchInSortedTeam()
        binarySearchWithSorting()
        searchInMultiMap()
    }
}

inline fun measureTimeMicros(block: () -> Unit) : Long {
    val time = measureNanoTime { block() }
    return time / 1000
}