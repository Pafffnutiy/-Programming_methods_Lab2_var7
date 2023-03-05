import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SearchTest {
    private lateinit var team : OlympicTeam
    private lateinit var sortedTeam : OlympicTeam

    @BeforeEach
    fun setUp() {
        team = OlympicTeamReader().readTeamFromJSON("100.json")
        sortedTeam = team.sortPyramid()
    }

    @Test
    @DisplayName("Проверка корреткности линейного поиска")
    fun testLinearSearch() {
        assertEquals(EXPECTED_IND, team.linearSearch(TARGET))
    }

    @Test
    @DisplayName("Проверка пустоты списка при отсутсвии требуемого элемента")
    fun testIncorrectTargetLinearSearch() {
        assertTrue(team.linearSearch(INCORRECT_TARGET).isEmpty())
    }

    @Test
    @DisplayName("Проверка корреткности бинарного поиска")
    fun testBinarySearch() {
        assertEquals(EXPECTED_IND_SORTED, sortedTeam.binarySearch(TARGET))
    }

    @Test
    @DisplayName("Проверка пустоты списка при отсутсвии требуемого элемента")
    fun testIncorrectTargetBinarySearch() {
        assertTrue(team.binarySearch(INCORRECT_TARGET).isEmpty())
    }

    private companion object {
        const val TARGET = "Футбол"
        const val INCORRECT_TARGET = "Лыжи"
        val EXPECTED_IND = listOf(4, 19, 56, 65, 83)
        val EXPECTED_IND_SORTED = listOf(92, 93, 94, 95, 96)
    }
}