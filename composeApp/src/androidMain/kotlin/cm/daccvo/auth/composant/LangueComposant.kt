package cm.daccvo.auth.composant

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cm.daccvo.auth.ui.login.AppColors

sealed class Flag {
    data class Vector(val icon: ImageVector) : Flag()
    data class Emoji(val value: String) : Flag()
}

data class Language(
    val code: String,
    val label: String,
    val flag: String
)

val LANGUAGES = listOf(
    Language("fr", "Français", "🇫🇷"),
    Language("en", "English", "🇬🇧")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageSelector(
    languages: List<Language>,
    selected: Language,
    onSelected: (Language) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {

        // 🔥 bouton compact au lieu d'un TextField full width
        Surface(
            modifier = Modifier
                .menuAnchor()
                .height(36.dp),
            shape = RoundedCornerShape(18.dp),
            color = AppColors.InputHover,
            border = BorderStroke(1.dp, AppColors.Divider),
            onClick = { expanded = true }
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                // flag

                Text(
                    text = selected.flag,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(end = 8.dp)
                )


                Spacer(Modifier.width(6.dp))

                Text(
                    text = selected.code.uppercase(),
                    color = AppColors.White,
                    fontSize = 12.sp
                )

                Spacer(Modifier.width(4.dp))

                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    tint = AppColors.White,
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        // MENU
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            containerColor = AppColors.BackgroundDark
        ) {
            languages.forEach { lang ->
                DropdownMenuItem(
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {

                            when (val flag = lang.flag) {
                                is Flag.Vector -> Icon(
                                    flag.icon,
                                    contentDescription = null,
                                    tint = AppColors.White,
                                    modifier = Modifier.size(20.dp)
                                )
                                is Flag.Emoji -> Text(flag.value)
                            }

                            Spacer(Modifier.width(8.dp))

                            Text(
                                lang.code,
                                color = AppColors.White
                            )
                        }
                    },
                    onClick = {
                        onSelected(lang)
                        expanded = false
                    }
                )
            }
        }
    }
}