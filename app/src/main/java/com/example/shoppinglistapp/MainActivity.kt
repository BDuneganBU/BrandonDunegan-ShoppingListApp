package com.example.shoppinglistapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.shoppinglistapp.ui.theme.ShoppingListAppTheme
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShoppingListAppTheme {
                ShoppingListApp()
            }
        }
    }
}

data class ShoppingListItem(
    val name: String,
    var isChecked: Boolean = false
)

@Composable
fun ShoppingListApp() {
    var shoppingList = remember { mutableStateOf(listOf<ShoppingListItem>()) }
    var itemField = remember { mutableStateOf("") }
    var quantityField = remember { mutableStateOf("") }
    val isDropDownExpanded = remember { mutableStateOf(false) }
    var itemPosition = remember { mutableStateOf(0) }
    var measurements = listOf("oz", "lbs", "kg", "box")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            OutlinedTextField(
                modifier = Modifier
                    .width(150.dp),
                colors = OutlinedTextFieldDefaults.colors( unfocusedTextColor = Color.Black, focusedTextColor = Color.Black),
                value = itemField.value,
                onValueChange = { text ->
                    itemField.value = text
                },
                label = { Text("Grocery Item")}
            )

            OutlinedTextField(
                modifier = Modifier
                    .width(150.dp),
                colors = OutlinedTextFieldDefaults.colors( unfocusedTextColor = Color.Black, focusedTextColor = Color.Black),
                value = quantityField.value,
                onValueChange = { text ->
                    quantityField.value = text
                },
                label = { Text("Quantity")}
            )
            Row(
                modifier = Modifier.clickable {
                    isDropDownExpanded.value = true
                }
            ) {
                Text(text = measurements[itemPosition.value])
            }
            DropdownMenu(
                expanded = isDropDownExpanded.value,
                onDismissRequest = {
                    isDropDownExpanded.value = false
                }) {
                measurements.forEachIndexed { index, username ->
                    DropdownMenuItem(text = {
                        Text(text = username)
                    },
                        onClick = {
                            isDropDownExpanded.value = false
                            itemPosition.value = index
                        })
                }
            }
        }

        Button(
            onClick = {
                shoppingList.value +=
                    ShoppingListItem(name = quantityField.value +
                            measurements.get(itemPosition.value) +
                            " " +
                            itemField.value, isChecked = false)
                itemField.value = ""
                quantityField.value = ""
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Item")
        }

        LazyColumn() {
            items(shoppingList.value) { item ->
                Row() {
                    Checkbox(
                        checked = item.isChecked,
                        onCheckedChange = { item.isChecked = !item.isChecked }
                    )
                    Text(text = item.name)
                }
            }
        }
    }
}

