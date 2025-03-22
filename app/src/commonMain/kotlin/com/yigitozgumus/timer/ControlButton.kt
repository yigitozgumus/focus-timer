import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compose.icons.LineAwesomeIcons
import compose.icons.lineawesomeicons.MinusCircleSolid
import compose.icons.lineawesomeicons.MinusSquare
import compose.icons.lineawesomeicons.PlusCircleSolid
import compose.icons.lineawesomeicons.PlusSquare

@Composable
fun ControlButton(
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    image: ImageVector,
    isEnabled: Boolean = true,
    label: String,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .size(40.dp)
            .clip(shape)
            .background(Color.LightGray, shape = shape)
            .clickable(enabled = isEnabled) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(image, contentDescription = label)
    }
}

@Composable
fun ControlPanel(
    modifier: Modifier = Modifier,
    initialValue: Int = 10,
    areControlButtonsEnabled: Boolean = true,
    onValueChange: (Int) -> Unit = {}
) {
    var value by remember { mutableStateOf(initialValue.toString()) }

    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        ControlButton(
            image = LineAwesomeIcons.MinusCircleSolid,
            label = "-",
            isEnabled = areControlButtonsEnabled,
            shape = CircleShape
        ) {
            onValueChange(-((value.toIntOrNull() ?: 10) * 60))
        }

        OutlinedTextField(
            value = value,
            onValueChange = {
                value = it
            },
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .background(Color.LightGray, shape = RoundedCornerShape(12.dp))
                .border(1.dp, color = Color.LightGray, shape = RoundedCornerShape(12.dp)),
            textStyle = androidx.compose.ui.text.TextStyle(
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
            ),
            shape = RoundedCornerShape(12.dp),
            singleLine = true
        )

        ControlButton(
            image = LineAwesomeIcons.PlusCircleSolid,
            label = "+",
            isEnabled = areControlButtonsEnabled,
            shape = CircleShape
        ) {
            onValueChange(((value.toIntOrNull() ?: 10) * 60))
        }
    }
}
