package com.example.inventoryapp.ui

import androidx.compose.foundation.layout.PaddingValues      // bring in the type
import androidx.compose.foundation.layout.padding            // bring in the extension
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding: PaddingValues ->                  // name it and type it
        NavHost(
            navController,
            startDestination = "inventory",
            modifier = Modifier.padding(innerPadding)      // apply it here
        ) {
            composable("inventory") {
                InventoryScreen(navController)
            }
            composable("sold") {
                SoldScreen()
            }
            composable("transactions") {
                TransactionListScreen()
            }
            composable(
                "transaction?sale={sale}&serial={serial}&item={item}",
                arguments = listOf(
                    navArgument("sale") { defaultValue = "false" },
                    navArgument("serial") { defaultValue = "" },
                    navArgument("item") { defaultValue = "" }
                )
            ) { backStackEntry ->
                val sale = backStackEntry.arguments?.getString("sale") == "true"
                val serial = backStackEntry.arguments?.getString("serial").orEmpty()
                val item = backStackEntry.arguments?.getString("item").orEmpty()
                TransactionScreen(
                    navController   = navController,
                    defaultType     = if (sale) "Sale" else "Purchase",
                    defaultSerial   = serial,
                    defaultItem     = item
                )
            }
            composable("scanner") {
                BarcodeScannerScreen { navController.popBackStack() }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val currentRoute = navController
        .currentBackStackEntryAsState().value
        ?.destination?.route

    NavigationBar {
        NavigationBarItem(
            selected = currentRoute == "inventory",
            onClick  = { navController.navigate("inventory") },
            icon     = {}, label = { Text("Inventory") }
        )
        NavigationBarItem(
            selected = currentRoute == "sold",
            onClick  = { navController.navigate("sold") },
            icon     = {}, label = { Text("Sold") }
        )
        NavigationBarItem(
            selected = currentRoute == "transactions",
            onClick  = { navController.navigate("transactions") },
            icon     = {}, label = { Text("Reports") }
        )
    }
}
