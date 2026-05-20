package com.example.statistics.presentation

import android.graphics.Paint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.designsystem.components.appbar.AppBarState
import com.example.designsystem.components.appbar.CommonAppBar
import com.example.designsystem.theme.SuccessGreenDark
import com.example.statistics.R
import com.example.statistics.domain.model.DailyStat
import com.example.statistics.domain.model.MonthlyStat
import com.example.statistics.presentation.MonthMapper.toUINumber
import kotlin.math.max

@Composable
fun StatisticsScreen(
    viewModel : StatisticsViewModel,
    onBackClick : () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                CommonAppBar(
                    modifier = Modifier.padding(16.dp), state = AppBarState.Back(
                        title = stringResource(R.string.statistics),
                    ), onBackClick = onBackClick
                )
            }, modifier = Modifier.systemBarsPadding()
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                when (uiState) {
                    is StatisticsUiState.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    is StatisticsUiState.Error -> {
                        Box(
                            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = (uiState as StatisticsUiState.Error).message,
                                    color = MaterialTheme.colorScheme.error,
                                    modifier = Modifier.padding(16.dp)
                                )
                                Button(onClick = { viewModel.retry() }) {
                                    Text(stringResource(R.string.retry))
                                }
                            }
                        }
                    }

                    is StatisticsUiState.Content -> {
                        val content = uiState as StatisticsUiState.Content
                        StatisticsContent(
                            currentYear = content.currentYear,
                            yearlyStats = content.yearlyStats,
                            selectedMonthName = content.selectedMonthName,
                            monthlyStats = content.selectedMonthStats,
                            onYearChange = { viewModel.onYearChange(it) },
                            onMonthSelected = { viewModel.onMonthSelected(it) })
                    }
                }
            }
        }
    }

}

@Composable
private fun StatisticsContent(
    currentYear : Int,
    yearlyStats : List<MonthlyStat>,
    selectedMonthName : String,
    monthlyStats : List<DailyStat>,
    onYearChange : (Int) -> Unit,
    onMonthSelected : (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.statistics),
                    style = MaterialTheme.typography.displayMedium,
                    modifier = Modifier.weight(1f)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    IconButton(
                        onClick = { onYearChange(currentYear - 1) }, enabled = currentYear > 2025
                    ) {
                        Icon(
                            Icons.Default.ArrowDropDown,
                            contentDescription = stringResource(R.string.previous_year),
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Text(
                        text = currentYear.toString(),
                        style = MaterialTheme.typography.displaySmall,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )

                    IconButton(
                        onClick = { onYearChange(currentYear + 1) }, enabled = currentYear < 2026
                    ) {
                        Icon(
                            Icons.Default.ArrowDropUp,
                            contentDescription = stringResource(R.string.next_year),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(4.dp),
                shape = RoundedCornerShape(12.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.yearly_overview),
                        style = MaterialTheme.typography.displayMedium
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    YearlyBarChart(
                        stats = yearlyStats, onBarClick = onMonthSelected
                    )
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ), elevation = CardDefaults.cardElevation(4.dp), shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = stringResource(
                            R.string.details_for_month, selectedMonthName, currentYear
                        ), style = MaterialTheme.typography.displayMedium
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    if (monthlyStats.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = stringResource(R.string.no_data_available),
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(32.dp)
                            )
                        }
                    } else {
                        monthlyStats.forEach { stat ->
                            DailyStatItem(stat)
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DailyStatItem(stat : DailyStat) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f))
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stat.label,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = stat.value.toString(),
            style = MaterialTheme.typography.titleSmall,
            color = SuccessGreenDark
        )
    }
}

@Composable
private fun YearlyBarChart(
    stats : List<MonthlyStat>, onBarClick : (Int) -> Unit, modifier : Modifier = Modifier
) {
    if (stats.isEmpty()) return

    val maxValue = max(stats.maxOfOrNull { it.value } ?: 1, 1)
    val animatedProgress = remember { Animatable(0f) }

    LaunchedEffect(stats) {
        animatedProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing)
        )
    }

    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val barWidth = size.width / stats.size
                val cornerRadius = 12.dp.toPx()

                stats.forEachIndexed { index, stat ->
                    val targetHeight = if (maxValue > 0) {
                        (stat.value.toFloat() / maxValue) * (size.height - 40.dp.toPx())
                    } else {
                        0f
                    }

                    val barHeight = targetHeight * animatedProgress.value

                    val left = index * barWidth + 6.dp.toPx()
                    val right = (index + 1) * barWidth - 6.dp.toPx()
                    val top = size.height - barHeight - 20.dp.toPx()

                    drawRoundRect(
                        color = SuccessGreenDark,
                        topLeft = Offset(left, top),
                        size = Size(right - left, barHeight),
                        cornerRadius = CornerRadius(cornerRadius, cornerRadius)
                    )

                    drawRoundRect(
                        color = SuccessGreenDark.copy(alpha = 0.5f),
                        topLeft = Offset(left, top),
                        size = Size(right - left, (barHeight * 0.4f).coerceAtLeast(4.dp.toPx())),
                        cornerRadius = CornerRadius(cornerRadius, cornerRadius)
                    )

                    drawRoundRect(
                        color = Color.White.copy(alpha = 0.3f),
                        topLeft = Offset(left + 2.dp.toPx(), top + 2.dp.toPx()),
                        size = Size((right - left) / 3, 4.dp.toPx()),
                        cornerRadius = CornerRadius(2.dp.toPx(), 2.dp.toPx())
                    )

                    if (stat.value > 0 && barHeight > 0) {
                        drawContext.canvas.nativeCanvas.apply {
                            val text = stat.value.toString()
                            val paint = Paint().apply {
                                color = android.graphics.Color.GREEN
                                textSize = 28f
                                textAlign = Paint.Align.CENTER
                                isFakeBoldText = true
                                setShadowLayer(4f, 0f, 1f, android.graphics.Color.BLACK)
                            }
                            drawText(
                                text, left + (right - left) / 2, top - 8.dp.toPx(), paint
                            )
                        }
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            stats.forEachIndexed { index, stat ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onBarClick(index) }) {
                    Text(
                        text = toUINumber(stat.label),
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )

                    if (stat.value > 0) {
                        Box(
                            modifier = Modifier
                                .size(4.dp)
                                .background(
                                    color = SuccessGreenDark, shape = CircleShape
                                )
                        )
                    }
                }
            }
        }
    }
}