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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
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
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Text(
            text = "Home",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(32.dp)
                .sharedElement("text")
                .padding(4.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.sample),
            contentDescription = "Sample Image",
            modifier = Modifier
                .size(100.dp)
                .clickable { navigate("detail") }
                .sharedElement("image")
        )
    }
}

@Composable
private fun NavTransitionScope.DetailScreen(
    modifier: Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "Detail",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.sharedElement("text")
        )
        Image(
            painter = painterResource(id = R.drawable.sample),
            contentDescription = "Sample Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .sharedElement("image")
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainNavGraphPreview() {
    MainNavGraph()
}
