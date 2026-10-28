package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.GlobalRepository
import com.example.model.Category
import com.example.model.Country
import com.example.model.PlatformInfo

@Composable
fun PlatformsScreen(
  currentCountry: Country,
  onSelectPlatform: (PlatformInfo) -> Unit,
  modifier: Modifier = Modifier
) {
  var selectedCategory by remember { mutableStateOf(Category.ALL) }
  var copiedPromo by remember { mutableStateOf<String?>(null) }

  val filteredPlatforms = GlobalRepository.platforms.filter { platform ->
    (selectedCategory == Category.ALL) || (platform.category == selectedCategory)
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
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A)),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(18.dp)) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            Box(
              modifier = Modifier
                .size(38.dp)
                .clip(CircleShape)
                .background(Color(0xFF38BDF8)),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.Public,
                contentDescription = "Global",
                tint = Color(0xFF0F172A),
                modifier = Modifier.size(22.dp)
              )
            }
            Column {
              Text(
                text = "Global Platform Hub",
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
                color = Color.White
              )
              Text(
                text = "Seamlessly dispatching across 20+ countries",
                fontSize = 12.sp,
                color = Color(0xFF94A3B8)
              )
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          Text(
            text = "OmniShop unifies individual platform APIs into a single autonomous interface. Select any vendor below to explore its catalog or copy automated token discounts.",
            fontSize = 12.sp,
            color = Color(0xFFCBD5E1),
            lineHeight = 17.sp
          )
        }
      }
    }

    // Category Filter Chips
    item {
      LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
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
            shape = RoundedCornerShape(20.dp)
          )
        }
      }
    }

    // Platforms List
    items(filteredPlatforms) { platform ->
      val brandColor = Color(platform.brandColorHex)
      val countryFlags = platform.countryCodes.mapNotNull { code ->
        GlobalRepository.countries.find { it.code == code }?.flagEmoji
      }.joinToString(" ")

      Card(
        modifier = Modifier
          .fillMaxWidth()
          .clickable { onSelectPlatform(platform) },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
              Box(
                modifier = Modifier
                  .size(42.dp)
                  .clip(RoundedCornerShape(12.dp))
                  .background(brandColor.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
              ) {
                Text(platform.logoEmoji, fontSize = 22.sp)
              }

              Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Text(
                    text = platform.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color(0xFF0F172A)
                  )
                  Spacer(modifier = Modifier.width(6.dp))
                  Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = brandColor.copy(alpha = 0.1f)
                  ) {
                    Text(
                      text = platform.category.displayName,
                      fontSize = 10.sp,
                      fontWeight = FontWeight.Bold,
                      color = brandColor,
                      modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                  }
                }

                Spacer(modifier = Modifier.height(2.dp))

                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                  Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                      imageVector = Icons.Default.Star,
                      contentDescription = "Rating",
                      tint = Color(0xFFF59E0B),
                      modifier = Modifier.size(13.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                      text = "${platform.rating}",
                      fontSize = 11.sp,
                      fontWeight = FontWeight.Bold,
                      color = Color(0xFF1E293B)
                    )
                  }

                  Text("•", color = Color(0xFF94A3B8), fontSize = 11.sp)

                  Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                      imageVector = Icons.Default.Speed,
                      contentDescription = "Speed",
                      tint = Color(0xFF64748B),
                      modifier = Modifier.size(13.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                      text = platform.avgDeliveryTime,
                      fontSize = 11.sp,
                      color = Color(0xFF64748B)
                    )
                  }
                }
              }
            }

            Text(
              text = countryFlags.ifEmpty { "🌐" },
              fontSize = 18.sp
            )
          }

          Spacer(modifier = Modifier.height(10.dp))

          Text(
            text = platform.tagLine,
            fontSize = 12.sp,
            color = Color(0xFF475569),
            lineHeight = 16.sp
          )

          Spacer(modifier = Modifier.height(12.dp))

          // Promo Code Row
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Surface(
              shape = RoundedCornerShape(8.dp),
              color = Color(0xFFF1F5F9),
              border = BorderStroke(1.dp, Color(0xFFCBD5E1)),
              modifier = Modifier.clickable {
                copiedPromo = platform.promoCode
              }
            ) {
              Row(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
              ) {
                Icon(
                  imageVector = if (copiedPromo == platform.promoCode) Icons.Default.Check else Icons.Default.ContentCopy,
                  contentDescription = "Copy Promo",
                  tint = if (copiedPromo == platform.promoCode) Color(0xFF059669) else Color(0xFF64748B),
                  modifier = Modifier.size(12.dp)
                )
                Text(
                  text = "Promo: ${platform.promoCode}",
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold,
                  color = if (copiedPromo == platform.promoCode) Color(0xFF059669) else Color(0xFF334155)
                )
              }
            }

            Surface(
              shape = RoundedCornerShape(8.dp),
              color = Color(0xFF4338CA).copy(alpha = 0.08f),
              modifier = Modifier.clickable { onSelectPlatform(platform) }
            ) {
              Text(
                text = "Browse Items →",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4338CA),
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
              )
            }
          }
        }
      }
    }

    item {
      Spacer(modifier = Modifier.height(80.dp))
    }
  }
}
