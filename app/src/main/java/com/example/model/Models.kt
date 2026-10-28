package com.example.model

enum class Category(val displayName: String, val emoji: String) {
  ALL("All Services", "🌐"),
  GROCERY("Groceries", "🥦"),
  FOOD("Food Delivery", "🍔"),
  MOVIES("Movie Tickets", "🎬"),
  EYEWEAR("Specs & Eyewear", "👓"),
  RETAIL("Shopping & Tech", "📱"),
  PHARMACY("Pharmacy", "💊")
}

data class Country(
  val code: String,
  val name: String,
  val flagEmoji: String,
  val currencySymbol: String,
  val currencyCode: String,
  val exchangeRateToUsd: Double, // 1 USD = X local currency
  val topPlatforms: List<String>
)

data class PlatformInfo(
  val id: String,
  val name: String,
  val category: Category,
  val countryCodes: List<String>,
  val logoEmoji: String,
  val brandColorHex: Long,
  val avgDeliveryTime: String,
  val rating: Double,
  val tagLine: String,
  val promoCode: String
)

data class ProductItem(
  val id: String,
  val name: String,
  val category: Category,
  val platformId: String,
  val platformName: String,
  val priceUsd: Double,
  val originalPriceUsd: Double? = null,
  val rating: Double,
  val reviewsCount: Int,
  val deliveryEta: String,
  val imageUrl: String,
  val description: String,
  val specs: Map<String, String> = emptyMap(),
  val countryCode: String = "US",
  val isFastDelivery: Boolean = false,
  val isPopular: Boolean = false
)

data class CartItem(
  val product: ProductItem,
  var quantity: Int = 1,
  val selectedSpecs: Map<String, String> = emptyMap()
)

data class AgentExecutionStep(
  val stepNumber: Int,
  val platformName: String,
  val actionDescription: String,
  val targetItem: String,
  val priceUsd: Double,
  val deliveryEta: String,
  val reasoning: String
)

data class AgentPlan(
  val prompt: String,
  val summary: String,
  val detectedCategories: List<Category>,
  val steps: List<AgentExecutionStep>,
  val recommendedProducts: List<ProductItem>,
  val totalSavingsUsd: Double,
  val totalEstimatedPriceUsd: Double
)

enum class OrderStatus(val label: String, val progress: Float) {
  PLACED("Order Dispatched to Platforms", 0.25f),
  PROCESSING("Platform Handshake & Prep", 0.50f),
  IN_TRANSIT("Out for Delivery / Tickets Ready", 0.75f),
  COMPLETED("Delivered & Fulfilled", 1.0f)
}

data class OrderRecord(
  val orderId: String,
  val timestamp: Long,
  val items: List<CartItem>,
  val totalAmountUsd: Double,
  val currencySymbol: String,
  val formattedTotal: String,
  val platformsInvolved: List<String>,
  val status: OrderStatus,
  val deliveryAddress: String
)
