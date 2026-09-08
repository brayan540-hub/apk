package com.example.ui

import android.widget.Toast
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.model.UserProfile
import com.example.ui.certifications.CertificationsScreen
import com.example.ui.components.BrayanTechBrandLogo
import com.example.ui.components.BrayanTechTopHeader
import com.example.ui.courses.CoursesScreen
import com.example.ui.dashboard.DashboardScreen
import com.example.ui.labs.LabsScreen
import com.example.ui.theme.CrimsonRed
import com.example.ui.theme.CyberAmber
import com.example.ui.theme.CyberBackground
import com.example.ui.theme.CyberBackgroundDarker
import com.example.ui.theme.CyberBorder
import com.example.ui.theme.CyberBorderBright
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
import com.example.ui.toolbox.ToolboxScreen

enum class BrayanTechTab(val label: String, val icon: ImageVector, val tag: String) {
    DASHBOARD("Tableau de bord", Icons.Default.Dashboard, "tab_dashboard"),
    COURSES("Formations", Icons.Default.School, "tab_courses"),
    LABS("Labs CTF", Icons.Default.Terminal, "tab_labs"),
    CERTIFICATIONS("Certifications", Icons.Default.MilitaryTech, "tab_certifications"),
    TOOLBOX("Outils", Icons.Default.Build, "tab_toolbox")
}

@Composable
fun BrayanTechApp() {
    val context = LocalContext.current
    var currentTab by remember { mutableStateOf(BrayanTechTab.DASHBOARD) }
    var selectedCourseToOpen by remember { mutableStateOf<String?>(null) }
    var showProfileDialog by remember { mutableStateOf(false) }

    var userProfile by remember {
        mutableStateOf(
            UserProfile(
                name = "Alexandre V.",
                callsign = "Sentinel-09",
                title = "Sentinelle brayanTech",
                level = 5,
                xp = 3850,
                nextLevelXp = 5000,
                streakDays = 14,
                lessonsCompleted = 23,
                labsSolved = 18,
                threatDefenseScore = 94
            )
        )
    }

    fun handleXpEarned(amount: Int) {
        val newXp = userProfile.xp + amount
        if (newXp >= userProfile.nextLevelXp) {
            userProfile = userProfile.copy(
                level = userProfile.level + 1,
                xp = newXp - userProfile.nextLevelXp,
                nextLevelXp = userProfile.nextLevelXp + 2000
            )
            Toast.makeText(context, "LEVEL UP ! Vous êtes désormais Niveau ${userProfile.level} !", Toast.LENGTH_LONG).show()
        } else {
            userProfile = userProfile.copy(xp = newXp)
            Toast.makeText(context, "+$amount XP accordés !", Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(CyberBackground),
        containerColor = CyberBackground,
        topBar = {
            BrayanTechTopHeader(
                profile = userProfile,
                onAvatarClick = { showProfileDialog = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .height(64.dp)
            )
        },
        bottomBar = {
            BrayanTechBottomBar(
                currentTab = currentTab,
                onTabSelected = {
                    currentTab = it
                    if (it != BrayanTechTab.COURSES) {
                        selectedCourseToOpen = null
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(CyberBackground)
        ) {
            Crossfade(targetState = currentTab, label = "tab_fade") { tab ->
                when (tab) {
                    BrayanTechTab.DASHBOARD -> DashboardScreen(
                        userProfile = userProfile,
                        onNavigateToCourse = { courseId ->
                            selectedCourseToOpen = courseId
                            currentTab = BrayanTechTab.COURSES
                        },
                        onNavigateToLabs = {
                            currentTab = BrayanTechTab.LABS
                        },
                        onNavigateToToolbox = {
                            currentTab = BrayanTechTab.TOOLBOX
                        }
                    )
                    BrayanTechTab.COURSES -> CoursesScreen(
                        initialCourseId = selectedCourseToOpen,
                        onXpEarned = { handleXpEarned(it) }
                    )
                    BrayanTechTab.LABS -> LabsScreen(
                        onXpEarned = { handleXpEarned(it) }
                    )
                    BrayanTechTab.CERTIFICATIONS -> CertificationsScreen(
                        userProfile = userProfile,
                        onXpEarned = { handleXpEarned(it) }
                    )
                    BrayanTechTab.TOOLBOX -> ToolboxScreen()
                }
            }
        }
    }

    if (showProfileDialog) {
        SentinelProfileDialog(
            profile = userProfile,
            onDismiss = { showProfileDialog = false }
        )
    }
}

@Composable
private fun BrayanTechBottomBar(
    currentTab: BrayanTechTab,
    onTabSelected: (BrayanTechTab) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding(),
        color = CyberSurface,
        border = androidx.compose.foundation.BorderStroke(1.dp, CyberBorder)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BrayanTechTab.values().forEach { tab ->
                val isSelected = currentTab == tab
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(3.dp),
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { onTabSelected(tab) }
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .testTag(tab.tag)
                ) {
                    Box(
                        modifier = Modifier
                            .size(width = 44.dp, height = 28.dp)
                            .clip(CircleShape)
                            .background(if (isSelected) NeonCyanGlow else Color.Transparent),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = tab.icon,
                            contentDescription = tab.label,
                            tint = if (isSelected) NeonCyan else TextMuted,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    Text(
                        text = tab.label,
                        fontSize = 10.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        color = if (isSelected) NeonCyan else TextMuted
                    )
                }
            }
        }
    }
}

@Composable
private fun SentinelProfileDialog(
    profile: UserProfile,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("profile_dialog"),
            colors = CardDefaults.cardColors(containerColor = CyberBackgroundDarker),
            shape = RoundedCornerShape(20.dp),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, NeonCyanDark)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        BrayanTechBrandLogo(size = 32.dp)
                        Text(
                            text = "PROFIL SENTINEL",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = NeonCyan,
                            letterSpacing = 1.sp
                        )
                    }

                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Fermer", tint = TextMuted)
                    }
                }

                // Avatar
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                        .background(Brush.linearGradient(listOf(NeonCyanDark, CyberPurple)))
                        .border(2.dp, NeonCyan, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "BT",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Black,
                        color = TextWhite
                    )
                }

                Text(
                    text = "${profile.name} • ${profile.callsign}",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextWhite
                )

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(CyberSurfaceElevated)
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "SENTINELLE brayanTech — NIVEAU ${profile.level}",
                        fontSize = 11.5.sp,
                        fontWeight = FontWeight.Bold,
                        color = NeonCyan
                    )
                }

                // Badges
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "INSIGNES & DISTINCTIONS OBTENUES",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextMuted,
                        letterSpacing = 0.5.sp
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        profile.badges.forEach { badge ->
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(CyberSurface)
                                    .border(1.dp, CyberBorderBright, RoundedCornerShape(6.dp))
                                    .padding(vertical = 6.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = badge,
                                    fontSize = 9.5.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = EmeraldGreen,
                                    maxLines = 1
                                )
                            }
                        }
                    }
                }

                Button(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = NeonCyan,
                        contentColor = CyberBackgroundDarker
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Fermer", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
