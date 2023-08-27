package dev.jianastrero.compose_nav_transition.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import dev.jianastrero.compose_nav_transition.element.ImageElement
import dev.jianastrero.compose_nav_transition.element.TextElement
import dev.jianastrero.compose_nav_transition.navigation.NavTransitionHost
import dev.jianastrero.compose_nav_transition.navigation.NavTransitionScope
import dev.jianastrero.compose_nav_transition.navigation.transitionComposable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainNavGraph()
        }
    }
}

@Composable
private fun MainNavGraph() {
    val navController = rememberNavController()
    NavTransitionHost(
        navController = navController,
        startDestination = "Home",
        modifier = Modifier.fillMaxSize()
    ) {
        transitionComposable("home") {
            HomeScreen(navigate = navController::navigate, modifier = Modifier.fillMaxSize())
        }
        transitionComposable("detail") {
            DetailScreen(modifier = Modifier.fillMaxSize())
        }
    }
}

@Composable
private fun NavTransitionScope.HomeScreen(
    navigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val text = remember { "Home" }
    val fontSize = remember { 24.sp }
    val fontWeight = remember { FontWeight.Bold }
    val textModifier = remember { Modifier.padding(4.dp) }

    val imagePainter = painterResource(id = R.drawable.sample)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Text(
            text = text,
            fontSize = fontSize,
            fontWeight = fontWeight,
            modifier = Modifier
                .padding(32.dp)
                .sharedElement(
                    tag = "text",
                    element = TextElement(
                        text = text,
                        fontSize = fontSize,
                        fontWeight = fontWeight,
                        modifier = textModifier
                        /**
                         * The only modifier you need to add are the ones after the "sharedElement" function call.
                         */
                    )
                )
                /**
                 *  The paddings after the "sharedElement" function call are inherited by the animation.
                 */
                .then(textModifier)
        )
        Image(
            painter = imagePainter,
            contentDescription = "Sample Image",
            modifier = Modifier
                .size(100.dp)
                .clickable { navigate("detail") }
                .sharedElement(
                    "image",
                    element = ImageElement(
                        painter = imagePainter,
                        contentScale = ContentScale.Crop // You might want to add content scale for better animations.
                    )
                )
        )
        Text(
            text = Constants.DUMMY_TEXT,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .sharedElement(
                    tag = "description",
                    element = TextElement(
                        text = Constants.DUMMY_TEXT,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(horizontal = 32.dp)
                    )
                )
                .padding(horizontal = 32.dp)
        )
    }
}

@Composable
private fun NavTransitionScope.DetailScreen(
    modifier: Modifier
) {
    val text = remember { "Detail" }
    val fontSize = remember { 32.sp }
    val fontWeight = remember { FontWeight.Bold }

    val imagePainter = painterResource(id = R.drawable.sample)

    Column(modifier = modifier) {
        Text(
            text = text,
            fontSize = fontSize,
            fontWeight = fontWeight,
            modifier = Modifier.sharedElement(
                tag = "text",
                element = TextElement(
                    text = text,
                    fontSize = fontSize,
                    fontWeight = fontWeight
                )
            )
        )
        Image(
            painter = imagePainter,
            contentDescription = "Sample Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .sharedElement(
                    "image",
                    element = ImageElement(
                        painter = imagePainter,
                        contentScale = ContentScale.Crop
                    )
                )
        )
        Text(
            text = Constants.DUMMY_TEXT,
            modifier = Modifier
                .sharedElement(
                    tag = "description",
                    element = TextElement(
                        text = Constants.DUMMY_TEXT,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(12.dp)
                    )
                )
                .padding(12.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainNavGraphPreview() {
    MainNavGraph()
}
