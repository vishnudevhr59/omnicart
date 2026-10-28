package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.GlobalRepository
import com.example.model.Category
import com.example.model.Country
import com.example.model.ProductItem
import com.example.ui.components.PlatformBadge
import com.example.ui.components.ProductCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreScreen(
  currentCountry: Country,
  onAddToCart: (ProductItem, Map<String, String>) -> Unit,
  modifier: Modifier = Modifier
) {
  var searchQuery by remember { mutableStateOf("") }
  var selectedCategory by remember { mutableStateOf(Category.ALL) }
  var fastDeliveryOnly by remember { mutableStateOf(false) }
  var inspectingProduct by remember { mutableStateOf<ProductItem?>(null) }

  // Filtered products
  val filteredProducts = GlobalRepository.sampleProducts.filter { item ->
    val matchesCategory = (selectedCategory == Category.ALL) || (item.category == selectedCategory)
    val matchesSearch = searchQuery.isBlank() ||
      item.name.contains(searchQuery, ignoreCase = true) ||
      item.platformName.contains(searchQuery, ignoreCase = true) ||
      item.description.contains(searchQuery, ignoreCase = true)
    val matchesFast = !fastDeliveryOnly || item.isFastDelivery

    matchesCategory && matchesSearch && matchesFast
  }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(Color(0xFFF8FAFC))
  ) {
    // Search Bar
    Surface(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 8.dp),
      shape = RoundedCornerShape(14.dp),
      color = Color.White,
      shadowElevation = 1.dp
    ) {
      OutlinedTextField(
        value = searchQuery,
        onValueChange = { searchQuery = it },
        placeholder = {
          Text(
            "Search across groceries, movies, food, specs...",
            fontSize = 13.sp,
            color = Color(0xFF94A3B8)
          )
        },
        leadingIcon = {
          Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "Search",
            tint = Color(0xFF64748B)
          )
        },
        trailingIcon = {
          if (searchQuery.isNotEmpty()) {
            IconButton(onClick = { searchQuery = "" }) {
              Icon(Icons.Default.Close, contentDescription = "Clear", tint = Color(0xFF64748B))
            }
          }
        },
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
          focusedBorderColor = Color.Transparent,
          unfocusedBorderColor = Color.Transparent
        ),
        singleLine = true
      )
    }

    // Category Tabs
    LazyRow(
      modifier = Modifier.fillMaxWidth(),
      contentPadding = PaddingValues(horizontal = 16.dp),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      items(Category.values()) { cat ->
        val isSelected = selectedCategory == cat
        FilterChip(
          selected = isSelected,
          onClick = { selectedCategory = cat },
          label = {
            Text(
              "${cat.emoji} ${cat.displayName}",
              fontSize = 12.sp,
              fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            )
          },
          colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = Color(0xFF4338CA),
            selectedLabelColor = Color.White,
            containerColor = Color.White,
            labelColor = Color(0xFF334155)
          ),
          border = FilterChipDefaults.filterChipBorder(
            enabled = true,
            selected = isSelected,
            borderColor = if (isSelected) Color(0xFF4338CA) else Color(0xFFE2E8F0)
          ),
          shape = RoundedCornerShape(20.dp)
        )
      }
    }

    // Secondary Filter Row
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 6.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Text(
        text = "${filteredProducts.size} items from global platforms",
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        color = Color(0xFF64748B)
      )

      FilterChip(
        selected = fastDeliveryOnly,
        onClick = { fastDeliveryOnly = !fastDeliveryOnly },
        label = { Text("⚡ Fast Dispatch (<30m)", fontSize = 11.sp) },
        colors = FilterChipDefaults.filterChipColors(
          selectedContainerColor = Color(0xFF10B981).copy(alpha = 0.15f),
          selectedLabelColor = Color(0xFF047857)
        ),
        shape = RoundedCornerShape(12.dp)
      )
    }

    // Products Grid
    LazyVerticalGrid(
      columns = GridCells.Fixed(2),
      modifier = Modifier.fillMaxSize(),
      contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 80.dp),
      horizontalArrangement = Arrangement.spacedBy(12.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      items(filteredProducts) { product ->
        ProductCard(
          product = product,
          currentCountry = currentCountry,
          onAddToCart = { onAddToCart(it, emptyMap()) },
          onItemClick = { inspectingProduct = it }
        )
      }
    }
  }

  // Product Spec / Customization Modal Sheet
  inspectingProduct?.let { product ->
    val sheetState = rememberModalBottomSheetState()
    var selectedSpecOption by remember { mutableStateOf("Standard") }

    ModalBottomSheet(
      onDismissRequest = { inspectingProduct = null },
      sheetState = sheetState,
      containerColor = Color.White
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 20.dp, vertical = 12.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          PlatformBadge(platformName = product.platformName)
          IconButton(onClick = { inspectingProduct = null }) {
            Icon(Icons.Default.Close, contentDescription = "Close")
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Box(
          modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .clip(RoundedCornerShape(16.dp))
        ) {
          AsyncImage(
            model = product.imageUrl,
            contentDescription = product.name,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
          )
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(
          text = product.name,
          fontWeight = FontWeight.Bold,
          fontSize = 18.sp,
          color = Color(0xFF0F172A)
        )

        Spacer(modifier = Modifier.height(6.dp))

        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          Text(
            text = GlobalRepository.formatPrice(product.priceUsd, currentCountry),
            fontSize = 20.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color(0xFF4338CA)
          )
          Surface(
            shape = RoundedCornerShape(8.dp),
            color = Color(0xFFFEF3C7)
          ) {
            Text(
              text = "⚡ Delivery: ${product.deliveryEta}",
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold,
              color = Color(0xFFB45309),
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
          text = product.description,
          fontSize = 13.sp,
          color = Color(0xFF475569),
          lineHeight = 18.sp
        )

        // Category-specific options
        if (product.category == Category.EYEWEAR) {
          Spacer(modifier = Modifier.height(14.dp))
          Text(
            text = "Select Lens Customization:",
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            color = Color(0xFF1E293B)
          )
          Spacer(modifier = Modifier.height(6.dp))
          Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("Blu-Cut Zero Power", "Single Vision Prescr.", "Anti-Glare Tint").forEach { opt ->
              Surface(
                shape = RoundedCornerShape(10.dp),
                color = if (selectedSpecOption == opt) Color(0xFF4338CA).copy(alpha = 0.1f) else Color(0xFFF1F5F9),
                border = if (selectedSpecOption == opt) androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF4338CA)) else null,
                modifier = Modifier.clickable { selectedSpecOption = opt }
              ) {
                Text(
                  text = opt,
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Medium,
                  color = if (selectedSpecOption == opt) Color(0xFF4338CA) else Color(0xFF475569),
                  modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                )
              }
            }
          }
        } else if (product.category == Category.MOVIES) {
          Spacer(modifier = Modifier.height(14.dp))
          Text(
            text = "Showtime & Screen Type:",
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            color = Color(0xFF1E293B)
          )
          Spacer(modifier = Modifier.height(6.dp))
          Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("7:30 PM (IMAX Laser)", "10:15 PM (Dolby Atmos)", "Tomorrow 4:00 PM").forEach { opt ->
              Surface(
                shape = RoundedCornerShape(10.dp),
                color = if (selectedSpecOption == opt) Color(0xFF4338CA).copy(alpha = 0.1f) else Color(0xFFF1F5F9),
                border = if (selectedSpecOption == opt) androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF4338CA)) else null,
                modifier = Modifier.clickable { selectedSpecOption = opt }
              ) {
                Text(
                  text = opt,
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Medium,
                  color = if (selectedSpecOption == opt) Color(0xFF4338CA) else Color(0xFF475569),
                  modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                )
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
          onClick = {
            onAddToCart(product, mapOf("Option" to selectedSpecOption))
            inspectingProduct = null
          },
          shape = RoundedCornerShape(14.dp),
          colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4338CA)),
          modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
        ) {
          Icon(Icons.Default.ShoppingCart, contentDescription = "Add")
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "Add to Universal Cart (${GlobalRepository.formatPrice(product.priceUsd, currentCountry)})",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
          )
        }

        Spacer(modifier = Modifier.height(24.dp))
      }
    }
  }
}
