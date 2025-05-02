import androidx.compose.material3.Surface
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.unit.dp
import com.example.bibliotrack.MainActivity
import com.example.bibliotrack.MyApp
import com.example.bibliotrack.navigation.BookNavigation
import com.example.bibliotrack.ui.theme.AppTheme
import org.junit.Rule
import org.junit.Test

class UiTest{
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun toListScreen() {
        composeTestRule
            .onNodeWithText("Start")
            .performClick()

        composeTestRule.onNode(hasSetTextAction()).performTextInput("T")

        //composeTestRule.onNodeWithText("Title").assertExists()
    }
}
