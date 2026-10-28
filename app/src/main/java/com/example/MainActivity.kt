package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.GlobalRepository
import com.example.model.AgentPlan
import com.example.model.CartItem
import com.example.model.Country
import com.example.model.OrderRecord
import com.example.model.OrderStatus
import com.example.model.ProductItem
import com.example.ui.components.CountryPickerDialog
import com.example.ui.components.UniversalTopBar
import com.example.ui.screens.AgentScreen
import com.example.ui.screens.CartScreen
import com.example.ui.screens.ExploreScreen
import com.example.ui.screens.OrdersScreen
import com.example.ui.screens.PlatformsScreen
import com.example.ui.theme.MyApplicationTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.UUID

enum class AppScreen(val label: String) {
  AGENT("OmniAgent"),
  EXPLORE("Explore"),
  PLATFORMS("Platforms"),
  CART("Cart"),
  ORDERS("Orders")
}

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        OmniShopApp()
      }
    }
  }
}

@Composable
fun OmniShopApp() {
  val scope = rememberCoroutineScope()
  val snackbarHostState = remember { SnackbarHostState() }

  var currentScreen by remember { mutableStateOf(AppScreen.AGENT) }
  var currentCountry by remember { mutableStateOf(GlobalRepository.countries[0]) } // US default
  var showCountryPicker by remember { mutableStateOf(false) }

  val cartItems = remember { mutableStateListOf<CartItem>() }
  val orders = remember { mutableStateListOf<OrderRecord>() }

  // Seed with an initial sample order for rich immediate preview
  remember {
    val sampleProduct = GlobalRepository.sampleProducts[0] // Avocado
    orders.add(
      OrderRecord(
        orderId = "ORD-GLOBAL-8942",
        timestamp = System.currentTimeMillis() - 1000 * 60 * 18,
        items = listOf(CartItem(sampleProduct, quantity = 2)),
        totalAmountUsd = sampleProduct.priceUsd * 2,
        currencySymbol = currentCountry.currencySymbol,
        formattedTotal = GlobalRepository.formatPrice(sampleProduct.priceUsd * 2, currentCountry),
        platformsInvolved = listOf("Blinkit", "Swiggy"),
        status = OrderStatus.IN_TRANSIT,
        deliveryAddress = "Flat 402, Lotus Heights, Indiranagar"
      )
    )
    true
  }

  fun addToCart(product: ProductItem, specs: Map<String, String> = emptyMap()) {
    val existingIndex = cartItems.indexOfFirst { it.product.id == product.id }
    if (existingIndex >= 0) {
      val existing = cartItems[existingIndex]
      cartItems[existingIndex] = existing.copy(quantity = existing.quantity + 1)
    } else {
      cartItems.add(CartItem(product = product, quantity = 1, selectedSpecs = specs))
    }
    scope.launch {
      snackbarHostState.showSnackbar("Added \"${product.name}\" to Universal Cart")
    }
  }

  fun addMultipleToCart(products: List<ProductItem>) {
    products.forEach { prod ->
      val existingIndex = cartItems.indexOfFirst { it.product.id == prod.id }
      if (existingIndex >= 0) {
        val existing = cartItems[existingIndex]
        cartItems[existingIndex] = existing.copy(quantity = existing.quantity + 1)
      } else {
        cartItems.add(CartItem(product = prod, quantity = 1))
      }
    }
    scope.launch {
      snackbarHostState.showSnackbar("Added ${products.size} items from OmniAgent to Cart")
    }
    currentScreen = AppScreen.CART
  }

  fun executeInstantOrder(plan: AgentPlan) {
    val orderId = "ORD-AGENT-" + UUID.randomUUID().toString().take(6).uppercase()
    val newOrder = OrderRecord(
      orderId = orderId,
      timestamp = System.currentTimeMillis(),
      items = plan.recommendedProducts.map { CartItem(it, 1) },
      totalAmountUsd = plan.totalEstimatedPriceUsd,
      currencySymbol = currentCountry.currencySymbol,
      formattedTotal = GlobalRepository.formatPrice(plan.totalEstimatedPriceUsd, currentCountry),
      platformsInvolved = plan.steps.map { it.platformName.substringBefore(" ") }.distinct(),
      status = OrderStatus.PROCESSING,
      deliveryAddress = "Universal Courier Destination (${currentCountry.name})"
    )
    orders.add(0, newOrder)
    currentScreen = AppScreen.ORDERS

    scope.launch {
      snackbarHostState.showSnackbar("Autonomous Order $orderId placed across ${newOrder.platformsInvolved.size} platforms!")
      delay(4000)
      val idx = orders.indexOfFirst { it.orderId == orderId }
      if (idx >= 0) {
        orders[idx] = orders[idx].copy(status = OrderStatus.IN_TRANSIT)
      }
    }
  }

  fun checkoutCart(deliveryAddress: String) {
    if (cartItems.isEmpty()) return
    val orderId = "ORD-CHECKOUT-" + UUID.randomUUID().toString().take(6).uppercase()
    val subtotal = cartItems.sumOf { it.product.priceUsd * it.quantity }
    val discount = if (cartItems.size >= 2) subtotal * 0.12 else 0.0
    val total = subtotal - discount

    val newOrder = OrderRecord(
      orderId = orderId,
      timestamp = System.currentTimeMillis(),
      items = cartItems.toList(),
      totalAmountUsd = total,
      currencySymbol = currentCountry.currencySymbol,
      formattedTotal = GlobalRepository.formatPrice(total, currentCountry),
      platformsInvolved = cartItems.map { it.product.platformName }.distinct(),
      status = OrderStatus.PROCESSING,
      deliveryAddress = deliveryAddress
    )
    orders.add(0, newOrder)
    cartItems.clear()
    currentScreen = AppScreen.ORDERS

    scope.launch {
      snackbarHostState.showSnackbar("Universal Order Placed! Dispatched to all merchant APIs.")
      delay(4500)
      val idx = orders.indexOfFirst { it.orderId == orderId }
      if (idx >= 0) {
        orders[idx] = orders[idx].copy(status = OrderStatus.IN_TRANSIT)
      }
    }
  }

  Scaffold(
    modifier = Modifier.fillMaxSize(),
    topBar = {
      UniversalTopBar(
        currentCountry = currentCountry,
        cartItemCount = cartItems.sumOf { it.quantity },
        onCountryClick = { showCountryPicker = true },
        onCartClick = { currentScreen = AppScreen.CART }
      )
    },
    bottomBar = {
      NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 6.dp
      ) {
        NavigationBarItem(
          selected = currentScreen == AppScreen.AGENT,
          onClick = { currentScreen = AppScreen.AGENT },
          icon = {
            Icon(
              imageVector = Icons.Default.AutoAwesome,
              contentDescription = "OmniAgent"
            )
          },
          label = { Text("OmniAgent", fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
          colors = NavigationBarItemDefaults.colors(
            selectedIconColor = Color(0xFF4338CA),
            selectedTextColor = Color(0xFF4338CA),
            indicatorColor = Color(0xFF4338CA).copy(alpha = 0.12f)
          )
        )

        NavigationBarItem(
          selected = currentScreen == AppScreen.EXPLORE,
          onClick = { currentScreen = AppScreen.EXPLORE },
          icon = {
            Icon(
              imageVector = Icons.Default.Explore,
              contentDescription = "Explore"
            )
          },
          label = { Text("Explore", fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
          colors = NavigationBarItemDefaults.colors(
            selectedIconColor = Color(0xFF4338CA),
            selectedTextColor = Color(0xFF4338CA),
            indicatorColor = Color(0xFF4338CA).copy(alpha = 0.12f)
          )
        )

        NavigationBarItem(
          selected = currentScreen == AppScreen.PLATFORMS,
          onClick = { currentScreen = AppScreen.PLATFORMS },
          icon = {
            Icon(
              imageVector = Icons.Default.Public,
              contentDescription = "Platforms"
            )
          },
          label = { Text("Platforms", fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
          colors = NavigationBarItemDefaults.colors(
            selectedIconColor = Color(0xFF4338CA),
            selectedTextColor = Color(0xFF4338CA),
            indicatorColor = Color(0xFF4338CA).copy(alpha = 0.12f)
          )
        )

        NavigationBarItem(
          selected = currentScreen == AppScreen.CART,
          onClick = { currentScreen = AppScreen.CART },
          icon = {
            BadgedBox(
              badge = {
                val count = cartItems.sumOf { it.quantity }
                if (count > 0) {
                  Badge(containerColor = Color(0xFFE11D48)) {
                    Text("$count", fontSize = 10.sp)
                  }
                }
              }
            ) {
              Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = "Cart"
              )
            }
          },
          label = { Text("Cart", fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
          colors = NavigationBarItemDefaults.colors(
            selectedIconColor = Color(0xFF4338CA),
            selectedTextColor = Color(0xFF4338CA),
            indicatorColor = Color(0xFF4338CA).copy(alpha = 0.12f)
          )
        )

        NavigationBarItem(
          selected = currentScreen == AppScreen.ORDERS,
          onClick = { currentScreen = AppScreen.ORDERS },
          icon = {
            Icon(
              imageVector = Icons.Default.ReceiptLong,
              contentDescription = "Orders"
            )
          },
          label = { Text("Orders", fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
          colors = NavigationBarItemDefaults.colors(
            selectedIconColor = Color(0xFF4338CA),
            selectedTextColor = Color(0xFF4338CA),
            indicatorColor = Color(0xFF4338CA).copy(alpha = 0.12f)
          )
        )
      }
    },
    snackbarHost = { SnackbarHost(snackbarHostState) }
  ) { paddingValues ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
    ) {
      when (currentScreen) {
        AppScreen.AGENT -> {
          AgentScreen(
            currentCountry = currentCountry,
            onAddProductsToCart = { products -> addMultipleToCart(products) },
            onInstantOrder = { plan -> executeInstantOrder(plan) }
          )
        }
        AppScreen.EXPLORE -> {
          ExploreScreen(
            currentCountry = currentCountry,
            onAddToCart = { product, specs -> addToCart(product, specs) }
          )
        }
        AppScreen.PLATFORMS -> {
          PlatformsScreen(
            currentCountry = currentCountry,
            onSelectPlatform = { platform ->
              currentScreen = AppScreen.EXPLORE
            }
          )
        }
        AppScreen.CART -> {
          CartScreen(
            cartItems = cartItems,
            currentCountry = currentCountry,
            onUpdateQuantity = { item, newQty ->
              val index = cartItems.indexOf(item)
              if (index >= 0) {
                cartItems[index] = item.copy(quantity = newQty)
              }
            },
            onRemoveItem = { item -> cartItems.remove(item) },
            onCheckout = { address -> checkoutCart(address) },
            onExploreClick = { currentScreen = AppScreen.EXPLORE }
          )
        }
        AppScreen.ORDERS -> {
          OrdersScreen(
            orders = orders,
            currentCountry = currentCountry,
            onExploreClick = { currentScreen = AppScreen.AGENT }
          )
        }
      }
    }
  }

  // Country Picker Bottom Sheet
  if (showCountryPicker) {
    CountryPickerDialog(
      currentCountry = currentCountry,
      onCountrySelected = { selected ->
        currentCountry = selected
      },
      onDismiss = { showCountryPicker = false }
    )
  }
}

/**
 * Kept for test compatibility (GreetingScreenshotTest)
 */
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
  Text(text = "Hello $name!", modifier = modifier)
}

@Preview(showBackground = true)
@Composable
fun OmniShopPreview() {
  MyApplicationTheme {
    OmniShopApp()
  }
}
