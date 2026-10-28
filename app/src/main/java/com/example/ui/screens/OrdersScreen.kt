package com.example.ui.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.GlobalRepository
import com.example.model.Country
import com.example.model.OrderRecord
import com.example.model.OrderStatus
import com.example.ui.components.PlatformBadge
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun OrdersScreen(
  orders: List<OrderRecord>,
  currentCountry: Country,
  onExploreClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val infiniteTransition = rememberInfiniteTransition(label = "pulse")
  val pulseAlpha by infiniteTransition.animateFloat(
    initialValue = 0.4f,
    targetValue = 1.0f,
    animationSpec = infiniteRepeatable(
      animation = tween(1200, easing = LinearEasing),
      repeatMode = androidx.compose.animation.core.RepeatMode.Reverse
    ),
    label = "pulseAlpha"
  )

  if (orders.isEmpty()) {
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
          imageVector = Icons.Default.ReceiptLong,
          contentDescription = "No Orders",
          tint = Color(0xFF64748B),
          modifier = Modifier.size(36.dp)
        )
      }

      Spacer(modifier = Modifier.height(16.dp))

      Text(
        text = "No Orders Placed Yet",
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF0F172A)
      )

      Spacer(modifier = Modifier.height(8.dp))

      Text(
        text = "Use the OmniAgent or Explore tab to dispatch your first multi-platform order across groceries, food, movies, or specs.",
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
        Text("Start Shopping with OmniAgent", fontWeight = FontWeight.Bold)
      }
    }
    return
  }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(Color(0xFFF8FAFC))
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    item {
      Spacer(modifier = Modifier.height(6.dp))
      Text(
        text = "Live Order Dispatch Hub (${orders.size})",
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF0F172A)
      )
      Text(
        text = "Real-time fulfillment tracking across worldwide platforms",
        fontSize = 12.sp,
        color = Color(0xFF64748B)
      )
    }

    items(orders) { order ->
      val dateFormat = SimpleDateFormat("MMM dd, yyyy • hh:mm a", Locale.getDefault())
      val dateStr = dateFormat.format(Date(order.timestamp))

      Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          // Top Header: Order ID + Status Badge
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(
                text = order.orderId,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 14.sp,
                color = Color(0xFF4338CA)
              )
              Text(
                text = dateStr,
                fontSize = 11.sp,
                color = Color(0xFF94A3B8)
              )
            }

            Surface(
              shape = RoundedCornerShape(8.dp),
              color = when (order.status) {
                OrderStatus.COMPLETED -> Color(0xFFECFDF5)
                else -> Color(0xFFEEF2FF).copy(alpha = pulseAlpha)
              }
            ) {
              Row(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
              ) {
                Icon(
                  imageVector = if (order.status == OrderStatus.COMPLETED) Icons.Default.CheckCircle else Icons.Default.LocalShipping,
                  contentDescription = "Status",
                  tint = if (order.status == OrderStatus.COMPLETED) Color(0xFF059669) else Color(0xFF4338CA),
                  modifier = Modifier.size(13.dp)
                )
                Text(
                  text = order.status.label,
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold,
                  color = if (order.status == OrderStatus.COMPLETED) Color(0xFF059669) else Color(0xFF4338CA)
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(12.dp))

          // Progress Bar
          LinearProgressIndicator(
            progress = { order.status.progress },
            modifier = Modifier
              .fillMaxWidth()
              .height(6.dp)
              .clip(RoundedCornerShape(3.dp)),
            color = Color(0xFF4338CA),
            trackColor = Color(0xFFE2E8F0)
          )

          Spacer(modifier = Modifier.height(14.dp))

          // Platforms involved badges
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            Text(
              text = "Dispatched to:",
              fontSize = 11.sp,
              fontWeight = FontWeight.SemiBold,
              color = Color(0xFF64748B)
            )
            order.platformsInvolved.forEach { platformName ->
              PlatformBadge(platformName = platformName)
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          // Items summary
          Surface(
            shape = RoundedCornerShape(10.dp),
            color = Color(0xFFF8FAFC),
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(modifier = Modifier.padding(10.dp)) {
              order.items.forEach { item ->
                Row(
                  modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 3.dp),
                  horizontalArrangement = Arrangement.SpaceBetween
                ) {
                  Text(
                    text = "${item.quantity}x ${item.product.name}",
                    fontSize = 12.sp,
                    color = Color(0xFF334155),
                    maxLines = 1,
                    modifier = Modifier.weight(1f)
                  )
                  Spacer(modifier = Modifier.width(8.dp))
                  Text(
                    text = GlobalRepository.formatPrice(item.product.priceUsd * item.quantity, currentCountry),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0F172A)
                  )
                }
              }
            }
          }

          Spacer(modifier = Modifier.height(12.dp))

          // Destination & Total
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = "Fulfillment Address:",
                fontSize = 10.sp,
                color = Color(0xFF94A3B8)
              )
              Text(
                text = order.deliveryAddress,
                fontSize = 11.sp,
                color = Color(0xFF475569),
                maxLines = 1
              )
            }

            Column(horizontalAlignment = Alignment.End) {
              Text(
                text = "Total Paid",
                fontSize = 10.sp,
                color = Color(0xFF94A3B8)
              )
              Text(
                text = order.formattedTotal,
                fontSize = 16.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF0F172A)
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
