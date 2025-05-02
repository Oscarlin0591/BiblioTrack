import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.bibliotrack.MyApp
import com.example.bibliotrack.navigation.BookNavigation
import com.example.bibliotrack.ui.theme.AppTheme
import org.junit.Rule
import org.junit.Test

class UITest {

    @get:Rule
    val  composeTestRule = createComposeRule()

    @Test
    fun typeInSearch(){

        composeTestRule.setContent {
            AppTheme {
                MyApp {
                    BookNavigation()
                }
            }
        }

        composeTestRule.onNodeWithText("Start")
            .performClick()

        composeTestRule.onNode(hasSetTextAction())
            .performTextInput("T")
    }

}