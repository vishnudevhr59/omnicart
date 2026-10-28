package com.example.data

import com.example.model.Category
import com.example.model.Country
import com.example.model.PlatformInfo
import com.example.model.ProductItem
import java.util.Locale

object GlobalRepository {

  val countries = listOf(
    Country(
      code = "US",
      name = "United States",
      flagEmoji = "🇺🇸",
      currencySymbol = "$",
      currencyCode = "USD",
      exchangeRateToUsd = 1.0,
      topPlatforms = listOf("DoorDash", "Amazon US", "Warby Parker", "Fandango", "Instacart")
    ),
    Country(
      code = "IN",
      name = "India",
      flagEmoji = "🇮🇳",
      currencySymbol = "₹",
      currencyCode = "INR",
      exchangeRateToUsd = 86.5,
      topPlatforms = listOf("Blinkit", "Swiggy", "Zomato", "BookMyShow", "Lenskart", "Amazon IN")
    ),
    Country(
      code = "GB",
      name = "United Kingdom",
      flagEmoji = "🇬🇧",
      currencySymbol = "£",
      currencyCode = "GBP",
      exchangeRateToUsd = 0.79,
      topPlatforms = listOf("Deliveroo", "Tesco Now", "Specsavers", "Odeon", "Amazon UK")
    ),
    Country(
      code = "JP",
      name = "Japan",
      flagEmoji = "🇯🇵",
      currencySymbol = "¥",
      currencyCode = "JPY",
      exchangeRateToUsd = 152.0,
      topPlatforms = listOf("UberEats JP", "Rakuten", "Toho Cinemas", "Owndays", "Amazon JP")
    ),
    Country(
      code = "SG",
      name = "Singapore",
      flagEmoji = "🇸🇬",
      currencySymbol = "S$",
      currencyCode = "SGD",
      exchangeRateToUsd = 1.34,
      topPlatforms = listOf("GrabFood", "Shopee", "Golden Village", "Owndays")
    ),
    Country(
      code = "AU",
      name = "Australia",
      flagEmoji = "🇦🇺",
      currencySymbol = "A$",
      currencyCode = "AUD",
      exchangeRateToUsd = 1.54,
      topPlatforms = listOf("UberEats AU", "Woolworths", "Hoyts", "Specsavers")
    ),
    Country(
      code = "DE",
      name = "Germany / EU",
      flagEmoji = "🇪🇺",
      currencySymbol = "€",
      currencyCode = "EUR",
      exchangeRateToUsd = 0.92,
      topPlatforms = listOf("Carrefour", "Deliveroo", "Mister Spex", "Amazon DE")
    ),
    Country(
      code = "BR",
      name = "Brazil",
      flagEmoji = "🇧🇷",
      currencySymbol = "R$",
      currencyCode = "BRL",
      exchangeRateToUsd = 5.75,
      topPlatforms = listOf("iFood", "Mercado Libre", "Cinemark", "Drogasil")
    )
  )

  val platforms = listOf(
    // Groceries
    PlatformInfo(
      id = "blinkit",
      name = "Blinkit",
      category = Category.GROCERY,
      countryCodes = listOf("IN"),
      logoEmoji = "⚡",
      brandColorHex = 0xFFF7C82D,
      avgDeliveryTime = "10-15 mins",
      rating = 4.8,
      tagLine = "10-minute grocery & everyday essentials delivery",
      promoCode = "BLINK10"
    ),
    PlatformInfo(
      id = "instacart",
      name = "Instacart",
      category = Category.GROCERY,
      countryCodes = listOf("US", "CA"),
      logoEmoji = "🥕",
      brandColorHex = 0xFF008938,
      avgDeliveryTime = "35-50 mins",
      rating = 4.7,
      tagLine = "Same-day grocery shopping from local supermarkets",
      promoCode = "FREESHIP"
    ),
    PlatformInfo(
      id = "tesco",
      name = "Tesco Now",
      category = Category.GROCERY,
      countryCodes = listOf("GB"),
      logoEmoji = "🛒",
      brandColorHex = 0xFF00539F,
      avgDeliveryTime = "25-40 mins",
      rating = 4.6,
      tagLine = "Fresh British produce, bakery, and meal deals",
      promoCode = "TESCO5"
    ),
    PlatformInfo(
      id = "carrefour",
      name = "Carrefour Direct",
      category = Category.GROCERY,
      countryCodes = listOf("DE", "FR"),
      logoEmoji = "🥖",
      brandColorHex = 0xFF1D5299,
      avgDeliveryTime = "30-45 mins",
      rating = 4.7,
      tagLine = "Gourmet cheese, organic pantry, and daily essentials",
      promoCode = "BONJOUR"
    ),

    // Food Delivery
    PlatformInfo(
      id = "doordash",
      name = "DoorDash",
      category = Category.FOOD,
      countryCodes = listOf("US", "CA", "AU"),
      logoEmoji = "🚗",
      brandColorHex = 0xFFFF3008,
      avgDeliveryTime = "25-35 mins",
      rating = 4.8,
      tagLine = "Restaurants and convenience stores delivered to your door",
      promoCode = "DASHPASS"
    ),
    PlatformInfo(
      id = "ubereats",
      name = "UberEats",
      category = Category.FOOD,
      countryCodes = listOf("US", "GB", "JP", "AU", "DE"),
      logoEmoji = "🍔",
      brandColorHex = 0xFF06C167,
      avgDeliveryTime = "20-30 mins",
      rating = 4.8,
      tagLine = "Top culinary spots, burgers, sushi, and artisan cafes",
      promoCode = "EATSGLOBAL"
    ),
    PlatformInfo(
      id = "swiggy",
      name = "Swiggy",
      category = Category.FOOD,
      countryCodes = listOf("IN"),
      logoEmoji = "🍲",
      brandColorHex = 0xFFFC8019,
      avgDeliveryTime = "20-30 mins",
      rating = 4.9,
      tagLine = "Authentic biryanis, healthy bowls, and street food favorites",
      promoCode = "SWIGGY50"
    ),
    PlatformInfo(
      id = "deliveroo",
      name = "Deliveroo",
      category = Category.FOOD,
      countryCodes = listOf("GB", "DE", "FR"),
      logoEmoji = "🚴",
      brandColorHex = 0xFF00CDBC,
      avgDeliveryTime = "25-35 mins",
      rating = 4.7,
      tagLine = "Rider network delivering London's & Europe's best dining",
      promoCode = "ROOPASS"
    ),
    PlatformInfo(
      id = "grabfood",
      name = "GrabFood",
      category = Category.FOOD,
      countryCodes = listOf("SG"),
      logoEmoji = "🛵",
      brandColorHex = 0xFF00B14F,
      avgDeliveryTime = "20-30 mins",
      rating = 4.8,
      tagLine = "Singapore hawker centers, laksa, satay, and bubble tea",
      promoCode = "GRABNOW"
    ),

    // Movie Tickets & Entertainment
    PlatformInfo(
      id = "bookmyshow",
      name = "BookMyShow",
      category = Category.MOVIES,
      countryCodes = listOf("IN"),
      logoEmoji = "🎟️",
      brandColorHex = 0xFFE51A4C,
      avgDeliveryTime = "Instant E-Ticket",
      rating = 4.9,
      tagLine = "IMAX 3D, Dolby Atmos recliner seats, live concerts & plays",
      promoCode = "BMSMOVIE"
    ),
    PlatformInfo(
      id = "fandango",
      name = "Fandango",
      category = Category.MOVIES,
      countryCodes = listOf("US"),
      logoEmoji = "🍿",
      brandColorHex = 0xFFFF7300,
      avgDeliveryTime = "Instant Mobile Pass",
      rating = 4.7,
      tagLine = "Guaranteed reserved cinema seats and exclusive VIP perks",
      promoCode = "VIPPASS"
    ),
    PlatformInfo(
      id = "odeon",
      name = "Odeon Luxe",
      category = Category.MOVIES,
      countryCodes = listOf("GB"),
      logoEmoji = "🎬",
      brandColorHex = 0xFF0A192F,
      avgDeliveryTime = "Instant QR Pass",
      rating = 4.7,
      tagLine = "Luxury reclining seating with premium immersive audio",
      promoCode = "LUXEFILM"
    ),
    PlatformInfo(
      id = "toho",
      name = "Toho Cinemas",
      category = Category.MOVIES,
      countryCodes = listOf("JP"),
      logoEmoji = "🎥",
      brandColorHex = 0xFF9E1B32,
      avgDeliveryTime = "Instant Digital Pass",
      rating = 4.8,
      tagLine = "Japan premier theater network with MX4D motion experiences",
      promoCode = "TOHONEW"
    ),

    // Specs & Eyewear
    PlatformInfo(
      id = "lenskart",
      name = "Lenskart",
      category = Category.EYEWEAR,
      countryCodes = listOf("IN", "SG"),
      logoEmoji = "👓",
      brandColorHex = 0xFF00BAC6,
      avgDeliveryTime = "24-48 Hours",
      rating = 4.9,
      tagLine = "Air Flex titanium frames, zero-power Blu-cut lenses & BOGO",
      promoCode = "AIRFLEX"
    ),
    PlatformInfo(
      id = "warbyparker",
      name = "Warby Parker",
      category = Category.EYEWEAR,
      countryCodes = listOf("US", "CA"),
      logoEmoji = "🕶️",
      brandColorHex = 0xFF1E3944,
      avgDeliveryTime = "2-3 Business Days",
      rating = 4.8,
      tagLine = "Designer prescription glasses & sunglasses with home try-on",
      promoCode = "WARBYNEW"
    ),
    PlatformInfo(
      id = "specsavers",
      name = "Specsavers",
      category = Category.EYEWEAR,
      countryCodes = listOf("GB", "AU"),
      logoEmoji = "🔬",
      brandColorHex = 0xFF00833E,
      avgDeliveryTime = "2-4 Days",
      rating = 4.7,
      tagLine = "Complete optical prescription lenses with anti-reflective coat",
      promoCode = "SAVERS20"
    ),
    PlatformInfo(
      id = "owndays",
      name = "Owndays",
      category = Category.EYEWEAR,
      countryCodes = listOf("JP", "SG"),
      logoEmoji = "🇯🇵",
      brandColorHex = 0xFF222222,
      avgDeliveryTime = "Same-Day / 24h",
      rating = 4.8,
      tagLine = "Minimalist Japanese high-index ultra thin lens frames",
      promoCode = "OWNDAYS5"
    ),

    // Shopping & Tech
    PlatformInfo(
      id = "amazon",
      name = "Amazon Global",
      category = Category.RETAIL,
      countryCodes = listOf("US", "IN", "GB", "JP", "DE"),
      logoEmoji = "📦",
      brandColorHex = 0xFFFF9900,
      avgDeliveryTime = "Next-Day Prime",
      rating = 4.8,
      tagLine = "Over 100M products with seamless international shipping",
      promoCode = "PRIMEFAST"
    ),
    PlatformInfo(
      id = "rakuten",
      name = "Rakuten Ichiba",
      category = Category.RETAIL,
      countryCodes = listOf("JP"),
      logoEmoji = "🎌",
      brandColorHex = 0xFFBF0000,
      avgDeliveryTime = "1-2 Days",
      rating = 4.7,
      tagLine = "Japan largest online mega-mart with super points rewards",
      promoCode = "RAKUTEN"
    ),

    // Pharmacy
    PlatformInfo(
      id = "cvs",
      name = "CVS Health",
      category = Category.PHARMACY,
      countryCodes = listOf("US"),
      logoEmoji = "💊",
      brandColorHex = 0xFFCC0000,
      avgDeliveryTime = "1-2 Hours",
      rating = 4.6,
      tagLine = "Prescriptions, vitamins, first-aid, and health essentials",
      promoCode = "CVSCARE"
    ),
    PlatformInfo(
      id = "apollo",
      name = "Apollo 24/7",
      category = Category.PHARMACY,
      countryCodes = listOf("IN"),
      logoEmoji = "🩺",
      brandColorHex = 0xFF026759,
      avgDeliveryTime = "19 mins",
      rating = 4.8,
      tagLine = "Genuine medicines, diagnostic kits, and doctor consultations",
      promoCode = "APOLLO15"
    )
  )

  val sampleProducts = listOf(
    // Groceries
    ProductItem(
      id = "g1",
      name = "Organic Hass Avocados (Pack of 4)",
      category = Category.GROCERY,
      platformId = "blinkit",
      platformName = "Blinkit",
      priceUsd = 3.99,
      originalPriceUsd = 5.20,
      rating = 4.8,
      reviewsCount = 4210,
      deliveryEta = "11 mins",
      imageUrl = "https://images.unsplash.com/photo-1523049673857-eb18f1d7b578?w=500&auto=format&fit=crop&q=80",
      description = "Farm-fresh ripe Hass avocados, rich in healthy monounsaturated fats. Perfectly ripened for guacamole or toast.",
      countryCode = "IN",
      isFastDelivery = true,
      isPopular = true
    ),
    ProductItem(
      id = "g2",
      name = "Artisan Sourdough Loaf (700g)",
      category = Category.GROCERY,
      platformId = "instacart",
      platformName = "Instacart",
      priceUsd = 4.89,
      originalPriceUsd = 6.00,
      rating = 4.9,
      reviewsCount = 1890,
      deliveryEta = "35 mins",
      imageUrl = "https://images.unsplash.com/photo-1589367920969-ab8e050bbb04?w=500&auto=format&fit=crop&q=80",
      description = "Slow-fermented whole wheat sourdough with a blistered crispy crust and tender open crumb.",
      countryCode = "US",
      isFastDelivery = false,
      isPopular = true
    ),
    ProductItem(
      id = "g3",
      name = "Organic Greek Yogurt & Fresh Berries Pack",
      category = Category.GROCERY,
      platformId = "tesco",
      platformName = "Tesco Now",
      priceUsd = 3.50,
      originalPriceUsd = 4.50,
      rating = 4.7,
      reviewsCount = 920,
      deliveryEta = "22 mins",
      imageUrl = "https://images.unsplash.com/photo-1488477181946-6428a0291777?w=500&auto=format&fit=crop&q=80",
      description = "Creamy whole milk Greek yogurt paired with wild blueberries and handpicked strawberries.",
      countryCode = "GB",
      isFastDelivery = true
    ),
    ProductItem(
      id = "g4",
      name = "French Brie & Gruyère Cheese Board Set",
      category = Category.GROCERY,
      platformId = "carrefour",
      platformName = "Carrefour Direct",
      priceUsd = 8.75,
      originalPriceUsd = 10.50,
      rating = 4.9,
      reviewsCount = 1450,
      deliveryEta = "30 mins",
      imageUrl = "https://images.unsplash.com/photo-1452195100486-9cc805987862?w=500&auto=format&fit=crop&q=80",
      description = "Authentic French AOP aged Gruyère and velvety Brie de Meaux with artisanal crackers.",
      countryCode = "DE",
      isPopular = true
    ),

    // Food Delivery
    ProductItem(
      id = "f1",
      name = "Tokyo Tonkotsu Spicy Black Garlic Ramen",
      category = Category.FOOD,
      platformId = "ubereats",
      platformName = "UberEats",
      priceUsd = 14.50,
      originalPriceUsd = 17.00,
      rating = 4.9,
      reviewsCount = 5230,
      deliveryEta = "22 mins",
      imageUrl = "https://images.unsplash.com/photo-1569718212165-3a8278d5f624?w=500&auto=format&fit=crop&q=80",
      description = "18-hour simmered pork bone broth with roasted chashu slices, soft-boiled ajitsuke tamago egg, and handmade noodles.",
      countryCode = "US",
      isFastDelivery = true,
      isPopular = true
    ),
    ProductItem(
      id = "f2",
      name = "Wood-Fired Neapolitan Truffle Pizza",
      category = Category.FOOD,
      platformId = "doordash",
      platformName = "DoorDash",
      priceUsd = 16.90,
      originalPriceUsd = 21.00,
      rating = 4.8,
      reviewsCount = 3410,
      deliveryEta = "28 mins",
      imageUrl = "https://images.unsplash.com/photo-1513104890138-7c749659a591?w=500&auto=format&fit=crop&q=80",
      description = "San Marzano tomato base, fresh buffalo mozzarella, black truffle cream, and wild cremini mushrooms.",
      countryCode = "US",
      isFastDelivery = true,
      isPopular = true
    ),
    ProductItem(
      id = "f3",
      name = "Hyderabadi Dum Biryani Feast Combo",
      category = Category.FOOD,
      platformId = "swiggy",
      platformName = "Swiggy",
      priceUsd = 6.40,
      originalPriceUsd = 8.00,
      rating = 4.9,
      reviewsCount = 8920,
      deliveryEta = "24 mins",
      imageUrl = "https://images.unsplash.com/photo-1563379091339-03b21ab4a4f8?w=500&auto=format&fit=crop&q=80",
      description = "Fragrant long-grain basmati cooked in clay pot with tender marinated chicken, saffron, and cooling mirchi ka salan.",
      countryCode = "IN",
      isFastDelivery = true,
      isPopular = true
    ),
    ProductItem(
      id = "f4",
      name = "Singapore Gourmet Chili Crab Bowl with Rice",
      category = Category.FOOD,
      platformId = "grabfood",
      platformName = "GrabFood",
      priceUsd = 11.80,
      originalPriceUsd = 14.20,
      rating = 4.8,
      reviewsCount = 2130,
      deliveryEta = "20 mins",
      imageUrl = "https://images.unsplash.com/photo-1555396273-367ea4eb4db5?w=500&auto=format&fit=crop&q=80",
      description = "Sweet and savory chili crab claw meat in egg ribbon gravy served over jasmine rice with golden mantou buns.",
      countryCode = "SG",
      isFastDelivery = true
    ),

    // Movie Tickets & Entertainment
    ProductItem(
      id = "m1",
      name = "IMAX 3D Prime Ticket: Dune: Part Two",
      category = Category.MOVIES,
      platformId = "bookmyshow",
      platformName = "BookMyShow",
      priceUsd = 7.50,
      originalPriceUsd = 9.00,
      rating = 4.9,
      reviewsCount = 14200,
      deliveryEta = "Instant Pass",
      imageUrl = "https://images.unsplash.com/photo-1489599849927-2ee91cede3ba?w=500&auto=format&fit=crop&q=80",
      description = "Reserved Recliner Row E, 12-channel laser audio, 4K dual projection. Includes free popcorn voucher code.",
      countryCode = "IN",
      specs = mapOf("Screen" to "IMAX with Laser", "Format" to "3D", "Audio" to "Dolby Atmos 12.1"),
      isFastDelivery = true,
      isPopular = true
    ),
    ProductItem(
      id = "m2",
      name = "Dolby Cinema Reserved Ticket: Inception 15th Anniv",
      category = Category.MOVIES,
      platformId = "fandango",
      platformName = "Fandango",
      priceUsd = 18.50,
      originalPriceUsd = 22.00,
      rating = 4.9,
      reviewsCount = 6840,
      deliveryEta = "Instant QR Code",
      imageUrl = "https://images.unsplash.com/photo-1517604931442-7e0c8ed2963c?w=500&auto=format&fit=crop&q=80",
      description = "Dolby Vision high-dynamic range with transducer rumble seats. VIP entrance fast lane included.",
      countryCode = "US",
      specs = mapOf("Screen" to "Dolby Cinema", "Seats" to "Luxury Recliner H-14", "Sound" to "Spatial Audio"),
      isFastDelivery = true,
      isPopular = true
    ),
    ProductItem(
      id = "m3",
      name = "Odeon Luxe Premier Seat: Interstellar 70mm",
      category = Category.MOVIES,
      platformId = "odeon",
      platformName = "Odeon Luxe",
      priceUsd = 16.00,
      originalPriceUsd = 19.50,
      rating = 4.8,
      reviewsCount = 3120,
      deliveryEta = "Instant Pass",
      imageUrl = "https://images.unsplash.com/photo-1478720568477-152d9b164e26?w=500&auto=format&fit=crop&q=80",
      description = "Heated electric recliner seat, individual tables, full bar service to seat before screening.",
      countryCode = "GB",
      specs = mapOf("Format" to "70mm Film", "Seat" to "Luxe Electric Row D"),
      isFastDelivery = true
    ),

    // Eyewear & Specs
    ProductItem(
      id = "e1",
      name = "Air Flex Featherlight Titanium Computer Glasses",
      category = Category.EYEWEAR,
      platformId = "lenskart",
      platformName = "Lenskart",
      priceUsd = 32.00,
      originalPriceUsd = 45.00,
      rating = 4.9,
      reviewsCount = 11400,
      deliveryEta = "Tomorrow",
      imageUrl = "https://images.unsplash.com/photo-1572635196237-14b3f281503f?w=500&auto=format&fit=crop&q=80",
      description = "Aerospace grade 8g memory titanium frame with BLU-cut anti-glare lenses. Blocks 98% harmful screen blue light.",
      countryCode = "IN",
      specs = mapOf("Frame" to "Pure Titanium", "Lens" to "Blu-Cut Zero Power", "Weight" to "8.2 grams"),
      isFastDelivery = true,
      isPopular = true
    ),
    ProductItem(
      id = "e2",
      name = "Haskell Custom Prescription Eyeglasses",
      category = Category.EYEWEAR,
      platformId = "warbyparker",
      platformName = "Warby Parker",
      priceUsd = 95.00,
      originalPriceUsd = 120.00,
      rating = 4.8,
      reviewsCount = 4780,
      deliveryEta = "2 Days",
      imageUrl = "https://images.unsplash.com/photo-1591076482161-42ce6da69f67?w=500&auto=format&fit=crop&q=80",
      description = "Custom hand-polished cellulose acetate frame in Crystal Amber. Includes scratch-resistant UV400 lenses.",
      countryCode = "US",
      specs = mapOf("Frame" to "Handcrafted Acetate", "Lens Type" to "Single Vision / Progressive", "UV Protection" to "100% UV400"),
      isPopular = true
    ),
    ProductItem(
      id = "e3",
      name = "Tokyo Minimalist Ultra-Thin 1.67 High-Index Specs",
      category = Category.EYEWEAR,
      platformId = "owndays",
      platformName = "Owndays",
      priceUsd = 68.00,
      originalPriceUsd = 85.00,
      rating = 4.9,
      reviewsCount = 2840,
      deliveryEta = "24h Express",
      imageUrl = "https://images.unsplash.com/photo-1511499767150-a48a237f0083?w=500&auto=format&fit=crop&q=80",
      description = "Japanese crafted stainless steel matte round frame. Ultra thin aspheric lenses with smudge-proof coating.",
      countryCode = "JP",
      specs = mapOf("Index" to "1.67 Aspheric", "Coating" to "Oleophobic & Hydrophobic"),
      isFastDelivery = true
    ),

    // Retail & Tech
    ProductItem(
      id = "r1",
      name = "Sony WH-1000XM5 Wireless ANC Headphones",
      category = Category.RETAIL,
      platformId = "amazon",
      platformName = "Amazon Global",
      priceUsd = 328.00,
      originalPriceUsd = 399.99,
      rating = 4.8,
      reviewsCount = 38400,
      deliveryEta = "Tomorrow",
      imageUrl = "https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=500&auto=format&fit=crop&q=80",
      description = "Industry-leading noise cancellation with 8 microphones and Auto NC Optimizer. 30-hour battery life with quick charge.",
      countryCode = "US",
      specs = mapOf("Battery" to "30 Hours", "ANC" to "Dual Processors V1/QN1", "Weight" to "250g"),
      isFastDelivery = true,
      isPopular = true
    ),
    ProductItem(
      id = "r2",
      name = "Kindle Paperwhite Signature Edition (32GB)",
      category = Category.RETAIL,
      platformId = "amazon",
      platformName = "Amazon Global",
      priceUsd = 149.99,
      originalPriceUsd = 189.99,
      rating = 4.9,
      reviewsCount = 29500,
      deliveryEta = "Same Day",
      imageUrl = "https://images.unsplash.com/photo-1544716278-ca5e3f4abd8c?w=500&auto=format&fit=crop&q=80",
      description = "6.8\" glare-free 300 ppi display, wireless Qi charging, auto-adjusting warm front light, IPX8 waterproof.",
      countryCode = "US",
      isFastDelivery = true
    ),

    // Pharmacy
    ProductItem(
      id = "p1",
      name = "Immune Support Vitamin C + Zinc Effervescent",
      category = Category.PHARMACY,
      platformId = "cvs",
      platformName = "CVS Health",
      priceUsd = 9.99,
      originalPriceUsd = 13.50,
      rating = 4.7,
      reviewsCount = 3200,
      deliveryEta = "45 mins",
      imageUrl = "https://images.unsplash.com/photo-1584308666744-24d5c474f2ae?w=500&auto=format&fit=crop&q=80",
      description = "Fast-acting high absorption formula with 1000mg Vitamin C, Zinc, and Elderberry extract.",
      countryCode = "US",
      isFastDelivery = true
    ),
    ProductItem(
      id = "p2",
      name = "Herbal Ayurvedic Sleep & Stress Melts",
      category = Category.PHARMACY,
      platformId = "apollo",
      platformName = "Apollo 24/7",
      priceUsd = 5.20,
      originalPriceUsd = 7.00,
      rating = 4.8,
      reviewsCount = 4500,
      deliveryEta = "18 mins",
      imageUrl = "https://images.unsplash.com/photo-1550572017-edd951aa8f72?w=500&auto=format&fit=crop&q=80",
      description = "Ashwagandha, chamomile and melatonin oral strips for deep restorative sleep with no morning grogginess.",
      countryCode = "IN",
      isFastDelivery = true
    )
  )

  fun formatPrice(priceUsd: Double, country: Country): String {
    val converted = priceUsd * country.exchangeRateToUsd
    return when (country.currencyCode) {
      "INR" -> String.format(Locale.US, "₹%.0f", converted)
      "JPY" -> String.format(Locale.US, "¥%.0f", converted)
      "EUR" -> String.format(Locale.US, "€%.2f", converted)
      "GBP" -> String.format(Locale.US, "£%.2f", converted)
      "BRL" -> String.format(Locale.US, "R$%.2f", converted)
      "SGD" -> String.format(Locale.US, "S$%.2f", converted)
      "AUD" -> String.format(Locale.US, "A$%.2f", converted)
      else -> String.format(Locale.US, "$%.2f", converted)
    }
  }

  fun getPlatformById(id: String): PlatformInfo? {
    return platforms.find { it.id.equals(id, ignoreCase = true) }
  }
}
