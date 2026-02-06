package cm.daccvo.auth.ui.dashboard

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen

object Dashboard : Screen {
    @Composable
    override fun Content() {
        DashboardScreen(
            user = DashboardUser(
                initials = "JD",
                firstName = "John",
                lastName = "Doe",
                lastLogin = "2h ago"
            ),
            selectedTab = 0
        )
    }
}