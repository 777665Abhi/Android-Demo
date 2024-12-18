package com.example.anddemo

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            DrawerBelowToolbarExample()
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerBelowToolbarExample() {
    // Manage the drawer state
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = androidx.compose.ui.platform.LocalContext.current

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            // Drawer content
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .background(Color.LightGray)
                    .padding(top = 32.dp, start = 16.dp, end = 64.dp)
            ) {
                Text(
                    "Drawer Item 1",
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable {
                            Toast.makeText(context, "Drawer Item 1 clicked!", Toast.LENGTH_SHORT).show()
                        },
                )
                Text("Drawer Item 2", modifier = Modifier
                    .padding(8.dp)
                    .clickable {
                        Toast.makeText(context, "Drawer Item 2 clicked!", Toast.LENGTH_SHORT).show()
                    })
                Text("Drawer Item 3", modifier = Modifier
                    .padding(8.dp)
                    .clickable {
                        Toast.makeText(context, "Drawer Item 3 clicked!", Toast.LENGTH_SHORT).show()
                    })
            }
        }
    ) {
        // Scaffold content (toolbar and main content)
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("My App") },
                    navigationIcon = {
                        IconButton(onClick = {
                            // Open the drawer
                            scope.launch {
                                drawerState.open()
                            }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            }
        ) { innerPadding ->
            val adjustedTopPadding = maxOf(0.dp, innerPadding.calculateTopPadding() - 16.dp)
                MyLazyColumn()
        }
    }
}

@Composable
fun MyLazyColumn() {
    val items = List(20) { "Item #$it" } // Sample data

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp), // Optional padding for the list
        verticalArrangement = Arrangement.spacedBy(4.dp) // Space between items
    ) {
        items(items) { item ->
            ListItem(text = item)
        }
    }
}

@Composable
fun ListItem(text: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),

    ) {
        Text(
            text = text,
            modifier = Modifier.padding(16.dp),
        )
    }
}

@Composable
fun ItemView(name: String, department: String) {
    Row {
        Image(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = "Profile picture",
        )
        Column {
            Text(
                text = "User $name!",
                modifier = Modifier.padding(bottom = 8.dp, top = 16.dp)
            )
            Text(
                text = "Department $department!",
            )
        }
    }
}


