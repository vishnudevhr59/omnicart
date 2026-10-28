package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import com.example.model.CartItem
import com.example.model.Country
import com.example.ui.components.PlatformBadge

@Composable
fun CartScreen(
  cartItems: List<CartItem>,
  currentCountry: Country,
  onUpdateQuantity: (CartItem, Int) -> Unit,
  onRemoveItem: (CartItem) -> Unit,
  onCheckout: (String) -> Unit,
  onExploreClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  var deliveryAddress by remember {
    mutableStateOf(
      when (currentCountry.code) {
        "IN" -> "Flat 402, Lotus Heights, Indiranagar, Bangalore"
        "GB" -> "Flat 12B, Kensington High Street, London W8"
        "JP" -> "Shibuya-ku, 2-24-12, Tokyo 150-8512"
        "SG" -> "32 Marina Boulevard, Marina Bay Suites, Singapore"
        else -> "742 Evergreen Terrace, Suite 3A, New York, NY 10001"
      }
    )
  }

  val subtotalUsd = cartItems.sumOf { it.product.priceUsd * it.quantity }
  val bundleSavingsUsd = if (cartItems.size >= 2) subtotalUsd * 0.12 else 0.0
  val finalTotalUsd = (subtotalUsd - bundleSavingsUsd).coerceAtLeast(0.0)

  // Group items by vendor platform
  val groupedByPlatform = cartItems.groupBy { it.product.platformName }

  if (cartItems.isEmpty()) {
    Column(
      modifier = modifier
        .fillMaxSize()
        .background(Color(0xFFF8FAFC))
        .padding(24.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
    ) {
      Box(
        modifier = Modifier
          .size(72.dp)
          .clip(CircleShape)
          .background(Color(0xFFE2E8F0)),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = Icons.Default.ShoppingBag,
          contentDescription = "Empty Cart",
          tint = Color(0xFF64748B),
          modifier = Modifier.size(36.dp)
        )
      }

      Spacer(modifier = Modifier.height(16.dp))

      Text(
        text = "Your Universal Cart is Empty",
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF0F172A)
      )

      Spacer(modifier = Modifier.height(8.dp))

      Text(
        text = "Ask the AI Agent to order across food, grocery, cinema tickets, or specs, or browse items in Explore.",
        fontSize = 13.sp,
        color = Color(0xFF64748B),
        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
        lineHeight = 18.sp
      )

      Spacer(modifier = Modifier.height(20.dp))

      Button(
        onClick = onExploreClick,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4338CA))
      ) {
        Text("Explore Global Marketplaces", fontWeight = FontWeight.Bold)
      }
    }
    return
  }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(Color(0xFFF8FAFC))
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    item {
      Spacer(modifier = Modifier.height(6.dp))
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFEEF2FF)),
        modifier = Modifier.fillMaxWidth()
      ) {
        Row(
          modifier = Modifier.padding(14.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = "Universal Cart",
            tint = Color(0xFF4338CA),
            modifier = Modifier.size(24.dp)
          )
          Column {
            Text(
              text = "Multi-Platform Universal Checkout",
              fontWeight = FontWeight.Bold,
              fontSize = 14.sp,
              color = Color(0xFF312E81)
            )
            Text(
              text = "OmniAgent combines orders across ${groupedByPlatform.keys.size} platforms into 1 single payment",
              fontSize = 11.sp,
              color = Color(0xFF4338CA)
            )
          }
        }
      }
    }

    // Platform Group Cards
    groupedByPlatform.forEach { (platformName, itemsForPlatform) ->
      item {
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White),
          elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(14.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              PlatformBadge(platformName = platformName)
              Surface(
                shape = RoundedCornerShape(6.dp),
                color = Color(0xFFF1F5F9)
              ) {
                Text(
                  text = "⚡ Delivery: ${itemsForPlatform.first().product.deliveryEta}",
                  fontSize = 11.sp,
                  fontWeight = FontWeight.SemiBold,
                  color = Color(0xFF475569),
                  modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
              }
            }

            Spacer(modifier = Modifier.height(10.dp))

            itemsForPlatform.forEach { cartItem ->
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
              ) {
                AsyncImage(
                  model = cartItem.product.imageUrl,
                  contentDescription = cartItem.product.name,
                  modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(10.dp)),
                  contentScale = ContentScale.Crop
                )

                Column(modifier = Modifier.weight(1f)) {
                  Text(
                    text = cartItem.product.name,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp,
                    color = Color(0xFF0F172A),
                    maxLines = 2
                  )
                  if (cartItem.selectedSpecs.isNotEmpty()) {
                    Text(
                      text = cartItem.selectedSpecs.values.joinToString(" • "),
                      fontSize = 11.sp,
                      color = Color(0xFF64748B)
                    )
                  }
                  Text(
                    text = GlobalRepository.formatPrice(cartItem.product.priceUsd, currentCountry),
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = Color(0xFF4338CA)
                  )
                }

                // Quantity Controls
                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                  Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = Color(0xFFF1F5F9),
                    modifier = Modifier.size(28.dp)
                  ) {
                    IconButton(onClick = {
                      if (cartItem.quantity > 1) {
                        onUpdateQuantity(cartItem, cartItem.quantity - 1)
                      } else {
                        onRemoveItem(cartItem)
                      }
                    }) {
                      Icon(
                        imageVector = if (cartItem.quantity > 1) Icons.Default.Remove else Icons.Default.Delete,
                        contentDescription = "Minus",
                        modifier = Modifier.size(14.dp),
                        tint = if (cartItem.quantity > 1) Color(0xFF334155) else Color(0xFFEF4444)
                      )
                    }
                  }

                  Text(
                    text = "${cartItem.quantity}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(horizontal = 4.dp)
                  )

                  Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = Color(0xFF4338CA).copy(alpha = 0.1f),
                    modifier = Modifier.size(28.dp)
                  ) {
                    IconButton(onClick = { onUpdateQuantity(cartItem, cartItem.quantity + 1) }) {
                      Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Plus",
                        modifier = Modifier.size(14.dp),
                        tint = Color(0xFF4338CA)
                      )
                    }
                  }
                }
              }
            }
          }
        }
      }
    }

    // Delivery Address
    item {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            Icon(
              imageVector = Icons.Default.LocationOn,
              contentDescription = "Address",
              tint = Color(0xFFE11D48),
              modifier = Modifier.size(18.dp)
            )
            Text(
              text = "Universal Delivery Destination",
              fontWeight = FontWeight.Bold,
              fontSize = 13.sp,
              color = Color(0xFF0F172A)
            )
          }

          Spacer(modifier = Modifier.height(8.dp))

          OutlinedTextField(
            value = deliveryAddress,
            onValueChange = { deliveryAddress = it },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = Color(0xFF4338CA),
              unfocusedBorderColor = Color(0xFFE2E8F0)
            ),
            textStyle = androidx.compose.ui.text.TextStyle(fontSize = 12.sp)
          )
        }
      }
    }

    // Bill Summary
    item {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = "Payment & Price Breakdown",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color(0xFF0F172A)
          )

          Spacer(modifier = Modifier.height(10.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text("Items Subtotal", fontSize = 13.sp, color = Color(0xFF64748B))
            Text(
              GlobalRepository.formatPrice(subtotalUsd, currentCountry),
              fontSize = 13.sp,
              fontWeight = FontWeight.Medium
            )
          }

          if (bundleSavingsUsd > 0) {
            Spacer(modifier = Modifier.height(6.dp))
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text("OmniAgent Multi-Platform Discount", fontSize = 13.sp, color = Color(0xFF059669))
              Text(
                "- " + GlobalRepository.formatPrice(bundleSavingsUsd, currentCountry),
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF059669)
              )
            }
          }

          Spacer(modifier = Modifier.height(6.dp))
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text("Unified Fulfillment & Dispatch", fontSize = 13.sp, color = Color(0xFF64748B))
            Text("FREE (AI Pass)", fontSize = 13.sp, color = Color(0xFF059669), fontWeight = FontWeight.Bold)
          }

          Spacer(modifier = Modifier.height(10.dp))
          Box(modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Color(0xFFE2E8F0)))
          Spacer(modifier = Modifier.height(10.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text("Total Amount", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF0F172A))
              Text("All taxes & fees included", fontSize = 11.sp, color = Color(0xFF94A3B8))
            }
            Text(
              text = GlobalRepository.formatPrice(finalTotalUsd, currentCountry),
              fontWeight = FontWeight.ExtraBold,
              fontSize = 20.sp,
              color = Color(0xFF4338CA)
            )
          }

          Spacer(modifier = Modifier.height(18.dp))

          Button(
            onClick = { onCheckout(deliveryAddress) },
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF059669)),
            modifier = Modifier
              .fillMaxWidth()
              .height(50.dp)
          ) {
            Text(
              text = "Place Multi-Platform Order (${GlobalRepository.formatPrice(finalTotalUsd, currentCountry)})",
              fontWeight = FontWeight.Bold,
              fontSize = 14.sp
            )
            Spacer(modifier = Modifier.width(6.dp))
            Icon(Icons.Default.ArrowForward, contentDescription = "Pay")
          }
        }
      }
    }

    item {
      Spacer(modifier = Modifier.height(80.dp))
    }
  }
}
