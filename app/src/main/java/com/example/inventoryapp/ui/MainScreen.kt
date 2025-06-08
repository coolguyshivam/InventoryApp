package com.example.inventoryapp.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import androidx.navigation.navArgument

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("inventory") },
                    icon = {}, label = { Text("Inventory") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("transaction") },
                    icon = {}, label = { Text("Add Txn") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("scanner") },
                    icon = {}, label = { Text("Scan") }
                )
            }
        }
    ) { innerPadding: PaddingValues ->
        NavHost(
            navController,
            startDestination = "inventory",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("inventory") {
                InventoryScreen(navController)
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
                    navController = navController,
                    defaultType = if (sale) "Sale" else "Purchase",
                    defaultSerial = serial,
                    defaultItem = item
                )
            }

            composable("transaction") {
                TransactionScreen(navController)
            }

            composable("scanner") {
                BarcodeScannerScreen { scannedValue ->
                    navController.popBackStack()
                }
            }
        }
    }
}
