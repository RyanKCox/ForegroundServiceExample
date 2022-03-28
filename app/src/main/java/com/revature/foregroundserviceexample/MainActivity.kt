package com.revature.foregroundserviceexample

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.revature.foregroundserviceexample.services.foregroundStartService
import com.revature.foregroundserviceexample.ui.theme.ForegroundServiceExampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ForegroundServiceExampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ForegroundServiceScreen()
                }
            }
        }
    }
}
@Composable
fun ForegroundServiceScreen(){

    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        scaffoldState = scaffoldState,
        topBar = { TopAppBar(
            title = {Text("Foreground Service",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth())}) },
        content = {

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {

                Button(onClick = {

                    //Service
                    context.foregroundStartService("Start")

                    //Toast notification
                    Toast.makeText(
                        context,
                        "Running Service",
                        Toast.LENGTH_LONG).show()

                }) {
                    Text("Foreground Service")
                }

                Spacer(Modifier.size(10.dp))

                Button(onClick = {
                    context.foregroundStartService("Stop")

                }) {
                    Text("Stop Service")
                }

            }

        }

    )
}
@Preview
@Composable
fun AppPrev(){
    ForegroundServiceScreen()
}
