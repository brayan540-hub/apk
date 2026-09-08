package com.example.ui.dashboard

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoGraph
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material.icons.filled.Warning
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.CourseModule
import com.example.model.CyberRepository
import com.example.model.SecurityAlert
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
import com.example.ui.theme.TextSubtle
import com.example.ui.theme.TextWhite

@Composable
fun DashboardScreen(
    userProfile: UserProfile,
    onNavigateToCourse: (String) -> Unit,
    onNavigateToLabs: () -> Unit,
    onNavigateToToolbox: () -> Unit
) {
    var selectedAttackVector by remember { mutableIntStateOf(0) }
    val alerts = remember { CyberRepository.sampleAlerts }
    val activeCourses = remember { CyberRepository.courses }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("dashboard_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // 1. Sentinel XP & Level Progress Bar
        item {
            SentinelProgressCard(profile = userProfile)
        }

        // 2. SOC Threat Monitoring Hero Card (with generated Banner)
        item {
            SocThreatBannerCard(alert = alerts.first())
        }

        // 3. Quick Stats Grid (4 Metric Tiles)
        item {
            QuickStatsGrid(profile = userProfile)
        }

        // 4. Interactive Attack Vector Visualizer
        item {
            AttackVectorVisualizerCard(
                selectedIndex = selectedAttackVector,
                onSelectVector = { selectedAttackVector = it }
            )
        }

        // 5. Continuer l'Apprentissage (Active Modules)
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "CONTINUER L'APPRENTISSAGE",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = NeonCyan,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "${activeCourses.size} cours disponibles",
                    fontSize = 11.sp,
                    color = TextMuted
                )
            }
        }

        items(activeCourses.take(3)) { course ->
            ActiveCourseCard(
                course = course,
                onClick = { onNavigateToCourse(course.id) }
            )
        }

        // 6. Quick Launch Cyber Actions
        item {
            QuickActionsBar(
                onLaunchCtf = onNavigateToLabs,
                onLaunchToolbox = onNavigateToToolbox
            )
        }
    }
}

@Composable
private fun SentinelProgressCard(profile: UserProfile) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("sentinel_progress_card"),
        colors = CardDefaults.cardColors(containerColor = CyberSurface),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, CyberBorderBright)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = profile.title.uppercase(),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = NeonCyan,
                        letterSpacing = 0.5.sp
                    )
                    Text(
                        text = "${profile.name} (${profile.callsign})",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextWhite
                    )
                }

                // Level Badge Pill
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(NeonCyanGlow)
                        .border(1.dp, NeonCyan, RoundedCornerShape(8.dp))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "NIVEAU ${profile.level}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black,
                        color = NeonCyan
                    )
                }
            }

            // XP Linear Progress
            val progress = profile.xp.toFloat() / profile.nextLevelXp.toFloat()
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "${profile.xp} / ${profile.nextLevelXp} XP",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextWhite,
                        fontFamily = FontFamily.Monospace
                    )
                    Text(
                        text = "${(progress * 100).toInt()}% vers Niv. ${profile.level + 1}",
                        fontSize = 12.sp,
                        color = TextMuted
                    )
                }

                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(CircleShape),
                    color = NeonCyan,
                    trackColor = CyberSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun SocThreatBannerCard(alert: SecurityAlert) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("threat_alert_card"),
        colors = CardDefaults.cardColors(containerColor = CyberSurface),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, CrimsonRed.copy(alpha = 0.5f))
    ) {
        Column {
            // Threat map visual hero
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_cyber_threat_map),
                    contentDescription = "Carte globale des cyber-menaces brayanTech",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                // Gradient overlay
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, CyberSurface)
                            )
                        )
                )
                // Live Alert Tag
                Row(
                    modifier = Modifier
                        .padding(12.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(CrimsonRed.copy(alpha = 0.9f))
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                    )
                    Text(
                        text = "ALERTE SOC DIRECT",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = alert.cve,
                        fontSize = 12.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        color = CrimsonRed
                    )
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(CrimsonRed.copy(alpha = 0.2f))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "CVSS ${alert.cvssScore} • ${alert.severity.uppercase()}",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = CrimsonRed
                        )
                    }
                }

                Text(
                    text = alert.title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextWhite
                )

                Text(
                    text = alert.description,
                    fontSize = 12.5.sp,
                    color = TextMuted,
                    lineHeight = 17.sp
                )

                // Recommendation note
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(CyberSurfaceVariant)
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Security,
                        contentDescription = null,
                        tint = EmeraldGreen,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = alert.recommendation,
                        fontSize = 11.5.sp,
                        color = TextWhite,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
private fun QuickStatsGrid(profile: UserProfile) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            StatCard(
                modifier = Modifier.weight(1f),
                title = "Leçons Finies",
                value = "${profile.lessonsCompleted}",
                accentColor = NeonCyan,
                icon = Icons.Default.CheckCircle
            )
            StatCard(
                modifier = Modifier.weight(1f),
                title = "Labs Résolus",
                value = "${profile.labsSolved}",
                accentColor = CyberPurple,
                icon = Icons.Default.Terminal
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            StatCard(
                modifier = Modifier.weight(1f),
                title = "Score Défense",
                value = "${profile.threatDefenseScore}%",
                accentColor = EmeraldGreen,
                icon = Icons.Default.Security
            )
            StatCard(
                modifier = Modifier.weight(1f),
                title = "Série Active",
                value = "${profile.streakDays} Jours",
                accentColor = CyberAmber,
                icon = Icons.Default.LocalFireDepartment
            )
        }
    }
}

@Composable
private fun StatCard(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    accentColor: Color,
    icon: ImageVector
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = CyberSurface),
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, CyberBorder)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(accentColor.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = accentColor,
                    modifier = Modifier.size(20.dp)
                )
            }
            Column {
                Text(
                    text = value,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextWhite,
                    fontFamily = FontFamily.Monospace
                )
                Text(
                    text = title,
                    fontSize = 11.sp,
                    color = TextMuted
                )
            }
        }
    }
}

/**
 * Interactive diagram component demonstrating real-world cybersecurity pipelines
 */
@Composable
private fun AttackVectorVisualizerCard(
    selectedIndex: Int,
    onSelectVector: (Int) -> Unit
) {
    val vectors = listOf(
        "Injection SQL" to "Altération de requête SQL et extraction",
        "Phishing & MITM" to "Capture d'identifiants par interception",
        "Pipeline AES-GCM" to "Chiffrement symétrique authentifié"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("attack_vector_card"),
        colors = CardDefaults.cardColors(containerColor = CyberSurface),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, CyberBorderBright)
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "VECTEURS D'ATTAQUE & TOPOLOGIES",
                    fontSize = 11.5.sp,
                    fontWeight = FontWeight.Bold,
                    color = NeonCyan,
                    letterSpacing = 0.5.sp
                )
                Text(
                    text = "Interactif",
                    fontSize = 10.sp,
                    color = EmeraldGreen,
                    fontWeight = FontWeight.SemiBold
                )
            }

            // Tabs to switch vector diagram
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                vectors.forEachIndexed { index, (label, _) ->
                    val isSelected = selectedIndex == index
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (isSelected) NeonCyanGlow else CyberSurfaceVariant)
                            .border(
                                1.dp,
                                if (isSelected) NeonCyan else CyberBorder,
                                RoundedCornerShape(8.dp)
                            )
                            .clickable { onSelectVector(index) }
                            .padding(vertical = 8.dp, horizontal = 4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = label,
                            fontSize = 11.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) NeonCyan else TextMuted
                        )
                    }
                }
            }

            // Dynamic Step Flow Graphic
            when (selectedIndex) {
                0 -> SqlInjectionDiagram()
                1 -> PhishingMitmDiagram()
                2 -> AesCryptoDiagram()
            }
        }
    }
}

@Composable
private fun SqlInjectionDiagram() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(CyberBackgroundDarker)
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        DiagramStepRow(
            stepNumber = "1",
            title = "Champ de saisie non aseptisé",
            desc = "L'attaquant soumet : admin' OR '1'='1' --",
            badgeColor = CrimsonRed
        )
        DiagramConnector()
        DiagramStepRow(
            stepNumber = "2",
            title = "Concaténation directe dans le SGBD",
            desc = "SELECT * FROM users WHERE user = 'admin' OR '1'='1' --' ...",
            badgeColor = CyberAmber
        )
        DiagramConnector()
        DiagramStepRow(
            stepNumber = "3",
            title = "Contournement & Fuite de Données",
            desc = "La condition '1'='1' évalue VRAI; session administrateur compromise",
            badgeColor = EmeraldGreen
        )
    }
}

@Composable
private fun PhishingMitmDiagram() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(CyberBackgroundDarker)
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        DiagramStepRow(
            stepNumber = "1",
            title = "Courriel Typosquatté (ex: login-brayan-tech.cm)",
            desc = "Appel urgent invitant la cible à réinitialiser son mot de passe",
            badgeColor = CrimsonRed
        )
        DiagramConnector()
        DiagramStepRow(
            stepNumber = "2",
            title = "Proxy Inverse Evilginx (MITM)",
            desc = "Capture en temps réel des identifiants et du cookie de session 2FA",
            badgeColor = CyberPurple
        )
        DiagramConnector()
        DiagramStepRow(
            stepNumber = "3",
            title = "Usurpation de Session Sans Alerte",
            desc = "Rejeu du jeton de session pour naviguer dans l'intranet légitime",
            badgeColor = CyberAmber
        )
    }
}

@Composable
private fun AesCryptoDiagram() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(CyberBackgroundDarker)
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        DiagramStepRow(
            stepNumber = "1",
            title = "Texte en clair + Clé Secrète (256-bit)",
            desc = "Génération d'un Vecteur d'Initialisation (IV) unique de 96-bit",
            badgeColor = NeonCyan
        )
        DiagramConnector()
        DiagramStepRow(
            stepNumber = "2",
            title = "Calcul AES-GCM (Chiffrement Authentifié)",
            desc = "Transformation de substitution-permutation + Calcul GHASH Tag",
            badgeColor = CyberPurple
        )
        DiagramConnector()
        DiagramStepRow(
            stepNumber = "3",
            title = "Chiffré + Tag d'Authentification (128-bit)",
            desc = "Toute altération du chiffré provoque un rejet immédiat au déchiffrement",
            badgeColor = EmeraldGreen
        )
    }
}

@Composable
private fun DiagramStepRow(stepNumber: String, title: String, desc: String, badgeColor: Color) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(badgeColor),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stepNumber,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = CyberBackgroundDarker
            )
        }
        Column {
            Text(
                text = title,
                fontSize = 12.5.sp,
                fontWeight = FontWeight.Bold,
                color = TextWhite
            )
            Text(
                text = desc,
                fontSize = 11.sp,
                color = TextMuted,
                fontFamily = FontFamily.Monospace
            )
        }
    }
}

@Composable
private fun DiagramConnector() {
    Box(
        modifier = Modifier
            .padding(start = 11.dp)
            .width(2.dp)
            .height(10.dp)
            .background(CyberBorderBright)
    )
}

@Composable
private fun ActiveCourseCard(course: CourseModule, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .testTag("course_item_${course.id}"),
        colors = CardDefaults.cardColors(containerColor = CyberSurface),
        shape = RoundedCornerShape(14.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, CyberBorder)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(NeonCyanGlow),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = course.icon,
                    contentDescription = null,
                    tint = NeonCyan,
                    modifier = Modifier.size(24.dp)
                )
            }

            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = course.title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextWhite
                )
                Text(
                    text = "${course.difficulty} • ${course.durationMinutes} min • +${course.xpReward} XP",
                    fontSize = 11.sp,
                    color = TextMuted
                )
                // Progress
                LinearProgressIndicator(
                    progress = { course.progressPercent / 100f },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .clip(CircleShape),
                    color = if (course.progressPercent == 100) EmeraldGreen else NeonCyan,
                    trackColor = CyberSurfaceVariant
                )
            }

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                tint = NeonCyan,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Composable
private fun QuickActionsBar(onLaunchCtf: () -> Unit, onLaunchToolbox: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Button(
            onClick = onLaunchCtf,
            modifier = Modifier
                .weight(1f)
                .testTag("btn_quick_ctf"),
            colors = ButtonDefaults.buttonColors(
                containerColor = CyberPurple,
                contentColor = TextWhite
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(Icons.Default.Terminal, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("Console CTF", fontSize = 12.5.sp, fontWeight = FontWeight.Bold)
        }

        Button(
            onClick = onLaunchToolbox,
            modifier = Modifier
                .weight(1f)
                .testTag("btn_quick_toolbox"),
            colors = ButtonDefaults.buttonColors(
                containerColor = CyberSurfaceElevated,
                contentColor = NeonCyan
            ),
            shape = RoundedCornerShape(12.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, NeonCyanDark)
        ) {
            Icon(Icons.Default.Security, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("Boîte à Outils", fontSize = 12.5.sp, fontWeight = FontWeight.Bold)
        }
    }
}
