package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.agent.OmniShoppingAgent
import com.example.data.GlobalRepository
import com.example.model.AgentPlan
import com.example.model.Country
import com.example.model.ProductItem
import com.example.ui.components.PlatformBadge
import kotlinx.coroutines.launch

@Composable
fun AgentScreen(
  currentCountry: Country,
  onAddProductsToCart: (List<ProductItem>) -> Unit,
  onInstantOrder: (AgentPlan) -> Unit,
  modifier: Modifier = Modifier
) {
  val scope = rememberCoroutineScope()
  var queryText by remember { mutableStateOf("") }
  var isLoading by remember { mutableStateOf(false) }
  var currentPlan by remember { mutableStateOf<AgentPlan?>(null) }
  var isListeningMock by remember { mutableStateOf(false) }

  val samplePills = listOf(
    "🍿 Movie night: 2 IMAX tickets, pizza & popcorn",
    "🥦 Weekly groceries under ${currentCountry.currencySymbol}25",
    "👓 Titanium Blu-Cut computer glasses with anti-glare",
    "🍜 Hot spicy ramen & gyoza delivered in 25 mins",
    "🎧 Compare Sony noise-cancelling headphones",
    "💊 Daily vitamins & sleep melts express delivery"
  )

  fun triggerAgent(prompt: String) {
    if (prompt.isBlank()) return
    isLoading = true
    scope.launch {
      val plan = OmniShoppingAgent.processShoppingPrompt(prompt, currentCountry)
      currentPlan = plan
      isLoading = false
    }
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
      // Hero Agent Banner
      Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1B4B)),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(18.dp)) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            Box(
              modifier = Modifier
                .size(42.dp)
                .clip(CircleShape)
                .background(Color(0xFF6366F1)),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.AutoAwesome,
                contentDescription = "AI Agent",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
              )
            }
            Column {
              Text(
                text = "OmniAgent AI Dispatcher",
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
                color = Color.White
              )
              Text(
                text = "Autonomous cross-platform multi-order engine",
                fontSize = 12.sp,
                color = Color(0xFFC7D2FE)
              )
            }
          }

          Spacer(modifier = Modifier.height(12.dp))

          Text(
            text = "Tell the agent what you need across groceries, cinema tickets, food delivery, eyewear, or tech. OmniAgent will scan global platforms, compare prices, and prepare a unified multi-cart order.",
            fontSize = 13.sp,
            lineHeight = 18.sp,
            color = Color(0xFFE0E7FF)
          )

          Spacer(modifier = Modifier.height(14.dp))

          // Voice and Input Box
          Surface(
            shape = RoundedCornerShape(14.dp),
            color = Color.White.copy(alpha = 0.12f),
            modifier = Modifier.fillMaxWidth()
          ) {
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 6.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              OutlinedTextField(
                value = queryText,
                onValueChange = { queryText = it },
                placeholder = {
                  Text(
                    "e.g. 2 IMAX tickets, spicy pizza, and blue-cut specs...",
                    fontSize = 13.sp,
                    color = Color(0xFFA5B4FC)
                  )
                },
                modifier = Modifier.weight(1f),
                colors = OutlinedTextFieldDefaults.colors(
                  focusedBorderColor = Color.Transparent,
                  unfocusedBorderColor = Color.Transparent,
                  focusedTextColor = Color.White,
                  unfocusedTextColor = Color.White
                ),
                maxLines = 2
              )

              IconButton(
                onClick = {
                  isListeningMock = !isListeningMock
                  if (isListeningMock) {
                    queryText = "Order 2 IMAX tickets for Dune, a spicy pizza, and computer glasses"
                  }
                }
              ) {
                Icon(
                  imageVector = Icons.Default.Mic,
                  contentDescription = "Voice Input",
                  tint = if (isListeningMock) Color(0xFFEF4444) else Color(0xFFC7D2FE)
                )
              }

              Button(
                onClick = {
                  triggerAgent(queryText)
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4F46E5)),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 12.dp, vertical = 6.dp)
              ) {
                Icon(
                  imageVector = Icons.Default.Send,
                  contentDescription = "Dispatch",
                  modifier = Modifier.size(16.dp)
                )
              }
            }
          }
        }
      }
    }

    // Quick Prompt Pills
    item {
      Column {
        Text(
          text = "Try AI Agent Scenarios",
          fontWeight = FontWeight.Bold,
          fontSize = 13.sp,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          items(samplePills) { pill ->
            Surface(
              shape = RoundedCornerShape(16.dp),
              color = Color.White,
              border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
              modifier = Modifier.clickable {
                queryText = pill.substringAfter(" ")
                triggerAgent(queryText)
              }
            ) {
              Text(
                text = pill,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF334155),
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp)
              )
            }
          }
        }
      }
    }

    // Loading State
    item {
      AnimatedVisibility(
        visible = isLoading,
        enter = fadeIn(),
        exit = fadeOut()
      ) {
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White),
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
          ) {
            CircularProgressIndicator(
              modifier = Modifier.size(28.dp),
              color = Color(0xFF4F46E5),
              strokeWidth = 3.dp
            )
            Column {
              Text(
                text = "OmniAgent Scanning Global Marketplaces...",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color(0xFF1E293B)
              )
              Text(
                text = "Cross-comparing rates across ${currentCountry.topPlatforms.take(4).joinToString(", ")}",
                fontSize = 12.sp,
                color = Color(0xFF64748B)
              )
            }
          }
        }
      }
    }

    // Agent Plan Result
    currentPlan?.let { plan ->
      item {
        Card(
          shape = RoundedCornerShape(20.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White),
          elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(18.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
              ) {
                Icon(
                  imageVector = Icons.Default.CheckCircle,
                  contentDescription = "Success",
                  tint = Color(0xFF10B981),
                  modifier = Modifier.size(20.dp)
                )
                Text(
                  text = "OmniAgent Execution Plan",
                  fontWeight = FontWeight.Bold,
                  fontSize = 16.sp,
                  color = Color(0xFF0F172A)
                )
              }

              Surface(
                shape = RoundedCornerShape(8.dp),
                color = Color(0xFFECFDF5)
              ) {
                Text(
                  text = "Est. Savings: " + GlobalRepository.formatPrice(plan.totalSavingsUsd, currentCountry),
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold,
                  color = Color(0xFF047857),
                  modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
              }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
              text = plan.summary,
              fontSize = 13.sp,
              color = Color(0xFF475569),
              lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
              text = "Multi-Platform Dispatch Route (${plan.steps.size} platforms)",
              fontWeight = FontWeight.Bold,
              fontSize = 13.sp,
              color = Color(0xFF334155)
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Step items
            plan.steps.forEach { step ->
              Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFFF8FAFC),
                border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(vertical = 4.dp)
              ) {
                Column(modifier = Modifier.padding(12.dp)) {
                  Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                  ) {
                    Row(
                      verticalAlignment = Alignment.CenterVertically,
                      horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                      Box(
                        modifier = Modifier
                          .size(24.dp)
                          .clip(CircleShape)
                          .background(Color(0xFF4338CA)),
                        contentAlignment = Alignment.Center
                      ) {
                        Text(
                          text = "${step.stepNumber}",
                          color = Color.White,
                          fontSize = 11.sp,
                          fontWeight = FontWeight.Bold
                        )
                      }
                      PlatformBadge(platformName = step.platformName)
                    }

                    Text(
                      text = GlobalRepository.formatPrice(step.priceUsd, currentCountry),
                      fontWeight = FontWeight.Bold,
                      fontSize = 13.sp,
                      color = Color(0xFF0F172A)
                    )
                  }

                  Spacer(modifier = Modifier.height(6.dp))

                  Text(
                    text = step.targetItem,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp,
                    color = Color(0xFF1E293B)
                  )

                  Text(
                    text = step.actionDescription,
                    fontSize = 12.sp,
                    color = Color(0xFF64748B)
                  )

                  Spacer(modifier = Modifier.height(4.dp))

                  Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                  ) {
                    Icon(
                      imageVector = Icons.Default.Speed,
                      contentDescription = "ETA",
                      tint = Color(0xFF6366F1),
                      modifier = Modifier.size(14.dp)
                    )
                    Text(
                      text = "Fulfillment: ${step.deliveryEta}",
                      fontSize = 11.sp,
                      fontWeight = FontWeight.Medium,
                      color = Color(0xFF6366F1)
                    )
                  }

                  Spacer(modifier = Modifier.height(4.dp))

                  Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = Color(0xFFEEF2FF)
                  ) {
                    Text(
                      text = "🤖 AI Reasoning: ${step.reasoning}",
                      fontSize = 11.sp,
                      color = Color(0xFF3730A3),
                      modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                      lineHeight = 15.sp
                    )
                  }
                }
              }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Total Price & Actions
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Column {
                Text(
                  text = "Unified Order Total",
                  fontSize = 12.sp,
                  color = Color(0xFF64748B)
                )
                Text(
                  text = GlobalRepository.formatPrice(plan.totalEstimatedPriceUsd, currentCountry),
                  fontSize = 18.sp,
                  fontWeight = FontWeight.ExtraBold,
                  color = Color(0xFF0F172A)
                )
              }

              Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                  onClick = { onAddProductsToCart(plan.recommendedProducts) },
                  shape = RoundedCornerShape(12.dp),
                  colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4338CA)),
                  contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 14.dp, vertical = 8.dp)
                ) {
                  Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Cart",
                    modifier = Modifier.size(16.dp)
                  )
                  Spacer(modifier = Modifier.width(4.dp))
                  Text("Add All to Cart", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }

                Button(
                  onClick = { onInstantOrder(plan) },
                  shape = RoundedCornerShape(12.dp),
                  colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF059669)),
                  contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 14.dp, vertical = 8.dp)
                ) {
                  Text("⚡ 1-Tap Order", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
              }
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
