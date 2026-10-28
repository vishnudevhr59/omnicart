package com.example.agent

import com.example.data.GlobalRepository
import com.example.model.AgentExecutionStep
import com.example.model.AgentPlan
import com.example.model.Category
import com.example.model.Country
import com.example.model.ProductItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

object OmniShoppingAgent {

  suspend fun processShoppingPrompt(
    query: String,
    selectedCountry: Country
  ): AgentPlan = withContext(Dispatchers.Default) {
    // Simulate brief agent reasoning latency
    delay(850)

    val lower = query.lowercase().trim()
    val detectedCategories = mutableListOf<Category>()
    val recommendedProducts = mutableListOf<ProductItem>()
    val steps = mutableListOf<AgentExecutionStep>()

    val isMovieQuery = lower.contains("movie") || lower.contains("ticket") || lower.contains("cinema") ||
      lower.contains("dune") || lower.contains("inception") || lower.contains("interstellar") || lower.contains("imax")
    val isFoodQuery = lower.contains("food") || lower.contains("ramen") || lower.contains("pizza") ||
      lower.contains("dinner") || lower.contains("lunch") || lower.contains("biryani") || lower.contains("burger") || lower.contains("eat")
    val isGroceryQuery = lower.contains("grocery") || lower.contains("avocado") || lower.contains("bread") ||
      lower.contains("milk") || lower.contains("cheese") || lower.contains("fruit") || lower.contains("snack")
    val isEyewearQuery = lower.contains("spec") || lower.contains("glass") || lower.contains("eyewear") ||
      lower.contains("blue") || lower.contains("frame") || lower.contains("lens")
    val isTechQuery = lower.contains("headphone") || lower.contains("sony") || lower.contains("kindle") ||
      lower.contains("tech") || lower.contains("electronic") || lower.contains("gadget")
    val isPharmacyQuery = lower.contains("medicine") || lower.contains("vitamin") || lower.contains("pharmacy") ||
      lower.contains("health") || lower.contains("pill")

    var stepCounter = 1
    var totalSavings = 0.0

    // Match Movie Intent
    if (isMovieQuery || detectedCategories.isEmpty() && lower.contains("night")) {
      detectedCategories.add(Category.MOVIES)
      val movieProduct = GlobalRepository.sampleProducts.find { it.category == Category.MOVIES }
        ?: GlobalRepository.sampleProducts.first { it.id == "m1" }
      recommendedProducts.add(movieProduct)
      totalSavings += (movieProduct.originalPriceUsd ?: (movieProduct.priceUsd * 1.2)) - movieProduct.priceUsd

      steps.add(
        AgentExecutionStep(
          stepNumber = stepCounter++,
          platformName = if (selectedCountry.code == "IN") "BookMyShow" else if (selectedCountry.code == "GB") "Odeon Luxe" else "Fandango",
          actionDescription = "Securing 2x Reserved Recliner Seats (Dolby Atmos / IMAX)",
          targetItem = movieProduct.name,
          priceUsd = movieProduct.priceUsd,
          deliveryEta = "Instant Digital QR Code",
          reasoning = "Cross-checked seat availability across 3 platforms. Selected platform offers best seat view angle and free concession promo."
        )
      )
    }

    // Match Food Intent
    if (isFoodQuery || (isMovieQuery && lower.contains("pizza"))) {
      detectedCategories.add(Category.FOOD)
      val foodProduct: ProductItem = (when {
        lower.contains("pizza") -> GlobalRepository.sampleProducts.find { it.id == "f2" }
        lower.contains("biryani") -> GlobalRepository.sampleProducts.find { it.id == "f3" }
        else -> GlobalRepository.sampleProducts.find { it.category == Category.FOOD }
      }) ?: GlobalRepository.sampleProducts.first { it.id == "f1" }

      recommendedProducts.add(foodProduct)
      totalSavings += (foodProduct.originalPriceUsd ?: (foodProduct.priceUsd * 1.25)) - foodProduct.priceUsd

      steps.add(
        AgentExecutionStep(
          stepNumber = stepCounter++,
          platformName = if (selectedCountry.code == "IN") "Swiggy" else if (selectedCountry.code == "SG") "GrabFood" else "DoorDash",
          actionDescription = "Ordering from top-rated 4.9★ local kitchen with priority courier",
          targetItem = foodProduct.name,
          priceUsd = foodProduct.priceUsd,
          deliveryEta = foodProduct.deliveryEta,
          reasoning = "Found active promotion: 20% discount applied automatically via AI agent merchant gateway."
        )
      )
    }

    // Match Grocery Intent
    if (isGroceryQuery || lower.contains("avocado") || lower.contains("weekly")) {
      detectedCategories.add(Category.GROCERY)
      val groceryProducts = GlobalRepository.sampleProducts.filter { it.category == Category.GROCERY }.take(2)
      groceryProducts.forEach { item ->
        if (!recommendedProducts.contains(item)) {
          recommendedProducts.add(item)
          totalSavings += (item.originalPriceUsd ?: (item.priceUsd * 1.2)) - item.priceUsd
        }
      }

      steps.add(
        AgentExecutionStep(
          stepNumber = stepCounter++,
          platformName = if (selectedCountry.code == "IN") "Blinkit (10-min)" else "Instacart Supermarket",
          actionDescription = "Aggregating fresh produce & artisan bakery with 10-minute dispatch",
          targetItem = "Fresh Organic Produce & Sourdough Pack",
          priceUsd = groceryProducts.sumOf { it.priceUsd },
          deliveryEta = if (selectedCountry.code == "IN") "11 mins" else "35 mins",
          reasoning = "Direct dark-store fulfillment picked for maximum freshness and lowest delivery fee."
        )
      )
    }

    // Match Eyewear Intent
    if (isEyewearQuery) {
      detectedCategories.add(Category.EYEWEAR)
      val glassProduct: ProductItem = (if (lower.contains("titanium") || selectedCountry.code == "IN") {
        GlobalRepository.sampleProducts.find { it.id == "e1" }
      } else {
        GlobalRepository.sampleProducts.find { it.id == "e2" }
      }) ?: GlobalRepository.sampleProducts.first { it.category == Category.EYEWEAR }

      recommendedProducts.add(glassProduct)
      totalSavings += (glassProduct.originalPriceUsd ?: (glassProduct.priceUsd * 1.25)) - glassProduct.priceUsd

      steps.add(
        AgentExecutionStep(
          stepNumber = stepCounter++,
          platformName = if (selectedCountry.code == "IN") "Lenskart" else "Warby Parker",
          actionDescription = "Configuring Blu-Cut zero power lenses with anti-reflective coating",
          targetItem = glassProduct.name,
          priceUsd = glassProduct.priceUsd,
          deliveryEta = glassProduct.deliveryEta,
          reasoning = "Compared 4 optical labs; verified anti-glare specs and selected manufacturer with highest warranty coverage."
        )
      )
    }

    // Match Tech / Retail
    if (isTechQuery || lower.contains("headphone") || lower.contains("buy")) {
      detectedCategories.add(Category.RETAIL)
      val techProduct = GlobalRepository.sampleProducts.find { it.category == Category.RETAIL }
        ?: GlobalRepository.sampleProducts.first { it.id == "r1" }
      recommendedProducts.add(techProduct)
      totalSavings += (techProduct.originalPriceUsd ?: (techProduct.priceUsd * 1.15)) - techProduct.priceUsd

      steps.add(
        AgentExecutionStep(
          stepNumber = stepCounter++,
          platformName = "Amazon Global Fulfillment",
          actionDescription = "Checking price parity across Amazon US, UK & JP logistics networks",
          targetItem = techProduct.name,
          priceUsd = techProduct.priceUsd,
          deliveryEta = techProduct.deliveryEta,
          reasoning = "Secured warehouse deal with guaranteed authentic manufacturer serial number and 1-day shipping."
        )
      )
    }

    // Match Pharmacy
    if (isPharmacyQuery) {
      detectedCategories.add(Category.PHARMACY)
      val pharmProduct = GlobalRepository.sampleProducts.find { it.category == Category.PHARMACY }
        ?: GlobalRepository.sampleProducts.first { it.id == "p1" }
      recommendedProducts.add(pharmProduct)
      totalSavings += (pharmProduct.originalPriceUsd ?: (pharmProduct.priceUsd * 1.2)) - pharmProduct.priceUsd

      steps.add(
        AgentExecutionStep(
          stepNumber = stepCounter++,
          platformName = if (selectedCountry.code == "IN") "Apollo 24/7" else "CVS Health",
          actionDescription = "Instant pharmacy fulfillment with cold-chain packaging",
          targetItem = pharmProduct.name,
          priceUsd = pharmProduct.priceUsd,
          deliveryEta = pharmProduct.deliveryEta,
          reasoning = "Verified certified batch number and instant local rider dispatch."
        )
      )
    }

    // Fallback if generic or multi-service request
    if (recommendedProducts.isEmpty()) {
      detectedCategories.addAll(listOf(Category.GROCERY, Category.FOOD, Category.EYEWEAR))
      val p1 = GlobalRepository.sampleProducts[0] // Avocado
      val p2 = GlobalRepository.sampleProducts[4] // Ramen
      val p3 = GlobalRepository.sampleProducts[8] // Movie ticket
      recommendedProducts.addAll(listOf(p1, p2, p3))
      totalSavings = 12.50

      steps.add(
        AgentExecutionStep(
          stepNumber = 1,
          platformName = "Blinkit / Instacart",
          actionDescription = "Dispatched express grocery basket",
          targetItem = p1.name,
          priceUsd = p1.priceUsd,
          deliveryEta = p1.deliveryEta,
          reasoning = "Fastest route dark store selected."
        )
      )
      steps.add(
        AgentExecutionStep(
          stepNumber = 2,
          platformName = "DoorDash / UberEats",
          actionDescription = "Matched gourmet dining preference",
          targetItem = p2.name,
          priceUsd = p2.priceUsd,
          deliveryEta = p2.deliveryEta,
          reasoning = "Highest user satisfaction rating (4.9★)."
        )
      )
      steps.add(
        AgentExecutionStep(
          stepNumber = 3,
          platformName = "BookMyShow / Fandango",
          actionDescription = "Prime seat allocation",
          targetItem = p3.name,
          priceUsd = p3.priceUsd,
          deliveryEta = p3.deliveryEta,
          reasoning = "Best center-row viewing angles guaranteed."
        )
      )
    }

    val totalCost = recommendedProducts.sumOf { it.priceUsd }

    val summaryText = buildString {
      append("OmniAgent scanned ${steps.size} global platforms in ${selectedCountry.name}. ")
      append("Optimized your multi-order across ${detectedCategories.joinToString(", ") { it.displayName }} ")
      append("saving you approx. ${GlobalRepository.formatPrice(totalSavings, selectedCountry)} with unified checkout.")
    }

    AgentPlan(
      prompt = query,
      summary = summaryText,
      detectedCategories = detectedCategories.distinct(),
      steps = steps,
      recommendedProducts = recommendedProducts,
      totalSavingsUsd = totalSavings,
      totalEstimatedPriceUsd = totalCost
    )
  }
}
