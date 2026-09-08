package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.UserProfile
import com.example.ui.theme.CrimsonRed
import com.example.ui.theme.CyberAmber
import com.example.ui.theme.CyberBackgroundDarker
import com.example.ui.theme.CyberBorder
import com.example.ui.theme.CyberBorderBright
import com.example.ui.theme.CyberBorderGlow
import com.example.ui.theme.CyberPurple
import com.example.ui.theme.CyberSurface
import com.example.ui.theme.CyberSurfaceElevated
import com.example.ui.theme.CyberSurfaceVariant
import com.example.ui.theme.EmeraldGreen
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonCyanDark
import com.example.ui.theme.NeonCyanGlow
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite

/**
 * Structured Brand Logo for brayanTech.
 * Combines a sleek tech shield, stylized 'B' monogram and glowing circuit node accents.
 */
@Composable
fun BrayanTechBrandLogo(
    modifier: Modifier = Modifier,
    size: Dp = 44.dp,
    showGlow: Boolean = true
) {
    val infiniteTransition = rememberInfiniteTransition(label = "circuit_pulse")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 0.95f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseAlpha"
    )

    Box(
        modifier = modifier
            .size(size)
            .testTag("brand_logo_brayantech"),
        contentAlignment = Alignment.Center
    ) {
        // Outer glowing canvas with tech shield and circuit paths
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = this.size.width
            val h = this.size.height

            // Tech shield path
            val shieldPath = Path().apply {
                moveTo(w * 0.5f, h * 0.05f)
                lineTo(w * 0.90f, h * 0.20f)
                lineTo(w * 0.88f, h * 0.65f)
                cubicTo(w * 0.80f, h * 0.85f, w * 0.65f, h * 0.95f, w * 0.50f, h * 0.98f)
                cubicTo(w * 0.35f, h * 0.95f, w * 0.20f, h * 0.85f, w * 0.12f, h * 0.65f)
                lineTo(w * 0.10f, h * 0.20f)
                close()
            }

            // Fill with dark metallic gradient
            drawPath(
                path = shieldPath,
                brush = Brush.verticalGradient(
                    colors = listOf(CyberSurfaceElevated, CyberBackgroundDarker)
                )
            )

            // Neon glowing shield contour
            drawPath(
                path = shieldPath,
                brush = Brush.linearGradient(
                    colors = listOf(NeonCyan.copy(alpha = pulseAlpha), CyberPurple, NeonCyanDark)
                ),
                style = Stroke(width = w * 0.055f)
            )

            // Circuit node lines
            // Left circuit trace
            drawLine(
                color = NeonCyan.copy(alpha = pulseAlpha * 0.8f),
                start = Offset(w * 0.28f, h * 0.40f),
                end = Offset(w * 0.15f, h * 0.40f),
                strokeWidth = w * 0.035f,
                cap = StrokeCap.Round
            )
            drawCircle(
                color = NeonCyan,
                radius = w * 0.045f,
                center = Offset(w * 0.15f, h * 0.40f)
            )

            // Right circuit trace
            drawLine(
                color = NeonCyan.copy(alpha = pulseAlpha * 0.8f),
                start = Offset(w * 0.72f, h * 0.40f),
                end = Offset(w * 0.85f, h * 0.40f),
                strokeWidth = w * 0.035f,
                cap = StrokeCap.Round
            )
            drawCircle(
                color = NeonCyan,
                radius = w * 0.045f,
                center = Offset(w * 0.85f, h * 0.40f)
            )

            // Bottom node
            drawCircle(
                color = EmeraldGreen,
                radius = w * 0.035f,
                center = Offset(w * 0.5f, h * 0.88f)
            )
        }

        // Center Stylized "B" Monogram
        Text(
            text = "B",
            color = NeonCyan,
            fontSize = (size.value * 0.52f).sp,
            fontWeight = FontWeight.Black,
            fontFamily = FontFamily.Monospace,
            letterSpacing = (-1).sp
        )
    }
}

/**
 * Top App Bar Header Component for brayanTech.
 * Features the structured logo, glow, "brayanTech Security Engine" badge and interactive profile avatar.
 */
@Composable
fun BrayanTechTopHeader(
    profile: UserProfile,
    onAvatarClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.testTag("brayantech_top_header"),
        color = CyberSurface,
        border = androidx.compose.foundation.BorderStroke(1.dp, CyberBorder)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Left: Brand Logo + Engine Title
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                BrayanTechBrandLogo(size = 46.dp)

                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "brayanTech",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = TextWhite,
                            letterSpacing = (-0.5).sp
                        )
                        // Active engine status dot
                        Box(
                            modifier = Modifier
                                .size(7.dp)
                                .clip(CircleShape)
                                .background(EmeraldGreen)
                        )
                    }

                    // Structured Badge: "brayanTech Security Engine"
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(NeonCyanGlow)
                            .border(0.5.dp, NeonCyan.copy(alpha = 0.5f), RoundedCornerShape(4.dp))
                            .padding(horizontal = 6.dp, vertical = 1.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Security,
                            contentDescription = null,
                            tint = NeonCyan,
                            modifier = Modifier.size(10.dp)
                        )
                        Text(
                            text = "brayanTech Security Engine",
                            fontSize = 9.5.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = NeonCyan,
                            letterSpacing = 0.3.sp
                        )
                    }
                }
            }

            // Right: Interactive Profile Avatar with Level Badge
            Surface(
                onClick = onAvatarClick,
                color = CyberSurfaceVariant,
                shape = RoundedCornerShape(14.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, CyberBorderBright),
                modifier = Modifier.testTag("header_avatar_btn")
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
                ) {
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "SENTINEL",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = NeonCyan,
                            letterSpacing = 0.5.sp
                        )
                        Text(
                            text = "Niv. ${profile.level}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextWhite
                        )
                    }

                    // Avatar Circle with Level Outline
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(Brush.linearGradient(listOf(NeonCyanDark, CyberPurple)))
                            .border(1.5.dp, NeonCyan, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "BT",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextWhite
                        )
                    }
                }
            }
        }
    }
}
