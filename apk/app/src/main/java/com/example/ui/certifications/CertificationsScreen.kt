package com.example.ui.certifications

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.CyberRepository
import com.example.model.QuizQuestion
import com.example.model.UserProfile
import com.example.ui.components.BrayanTechBrandLogo
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
import kotlinx.coroutines.delay

@Composable
fun CertificationsScreen(
    userProfile: UserProfile,
    onXpEarned: (Int) -> Unit = {}
) {
    val context = LocalContext.current
    var isExamRunning by remember { mutableStateOf(false) }
    var currentQuestionIndex by remember { mutableIntStateOf(0) }
    var selectedOptionIndex by remember { mutableIntStateOf(-1) }
    var score by remember { mutableIntStateOf(0) }
    var examCompleted by remember { mutableStateOf(false) }
    var timerSeconds by remember { mutableIntStateOf(120) }

    val questions = remember { CyberRepository.certificationQuizQuestions }

    // Countdown timer during active exam
    LaunchedEffect(isExamRunning, timerSeconds) {
        if (isExamRunning && timerSeconds > 0) {
            delay(1000)
            timerSeconds--
        } else if (isExamRunning && timerSeconds == 0) {
            isExamRunning = false
            examCompleted = true
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("certifications_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = "CENTRE D'ÉVALUATION & CERTIFICATIONS",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = NeonCyan,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "Accréditations Officielles brayanTech",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextWhite
                )
                Text(
                    text = "Validez vos compétences en cybersécurité sous conditions d'examen chronométré pour obtenir votre certificat officiel brayanTech.",
                    fontSize = 13.sp,
                    color = TextMuted,
                    lineHeight = 18.sp
                )
            }
        }

        if (!isExamRunning && !examCompleted) {
            // Exam Overview & Start Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = CyberSurface),
                    shape = RoundedCornerShape(16.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, CyberBorderBright)
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(CyberPurple.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.MilitaryTech,
                                    contentDescription = null,
                                    tint = CyberPurple,
                                    modifier = Modifier.size(28.dp)
                                )
                            }

                            Column {
                                Text(
                                    text = "Certificat d'Excellence Cyber Défensive",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextWhite
                                )
                                Text(
                                    text = "Évaluation Globale des Fondamentaux",
                                    fontSize = 12.sp,
                                    color = NeonCyan
                                )
                            }
                        }

                        Text(
                            text = "L'évaluation comporte 5 questions techniques aléatoires portant sur la cryptographie, l'ingénierie sociale, la sécurité réseau et l'analyse forensique. Note minimale de passage : 80% (4/5).",
                            fontSize = 12.5.sp,
                            color = TextMuted,
                            lineHeight = 18.sp
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            ExamStatBadge(label = "Questions", value = "5 QCM")
                            ExamStatBadge(label = "Temps Alloué", value = "2 min")
                            ExamStatBadge(label = "Récompense", value = "+500 XP")
                        }

                        Button(
                            onClick = {
                                isExamRunning = true
                                currentQuestionIndex = 0
                                selectedOptionIndex = -1
                                score = 0
                                timerSeconds = 120
                                examCompleted = false
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("start_exam_button"),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = NeonCyan,
                                contentColor = CyberBackgroundDarker
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Démarrer l'Épreuve Chronométrée", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // Always display Certificate Preview
            item {
                BrayanTechCertificateCard(
                    profile = userProfile,
                    isVerified = true,
                    onDownload = {
                        Toast.makeText(context, "Certificat brayanTech téléchargé (PDF)", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        } else if (isExamRunning) {
            // Live Exam Interface
            val currentQ = questions[currentQuestionIndex]

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = CyberSurface),
                    shape = RoundedCornerShape(16.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, NeonCyanDark)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        // Timer & Progress
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
                                    imageVector = Icons.Default.Timer,
                                    contentDescription = null,
                                    tint = if (timerSeconds < 30) CrimsonRed else CyberAmber,
                                    modifier = Modifier.size(18.dp)
                                )
                                val minutes = timerSeconds / 60
                                val seconds = timerSeconds % 60
                                Text(
                                    text = String.format("%02d:%02d", minutes, seconds),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Monospace,
                                    color = if (timerSeconds < 30) CrimsonRed else CyberAmber
                                )
                            }

                            Text(
                                text = "Question ${currentQuestionIndex + 1} / ${questions.size}",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = NeonCyan
                            )
                        }

                        LinearProgressIndicator(
                            progress = { (currentQuestionIndex + 1).toFloat() / questions.size.toFloat() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .clip(CircleShape),
                            color = NeonCyan,
                            trackColor = CyberSurfaceVariant
                        )

                        Text(
                            text = currentQ.question,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextWhite,
                            lineHeight = 20.sp
                        )

                        // Options
                        currentQ.options.forEachIndexed { optIndex, optionText ->
                            val isSelected = selectedOptionIndex == optIndex
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(if (isSelected) NeonCyanGlow else CyberBackgroundDarker)
                                    .border(
                                        1.dp,
                                        if (isSelected) NeonCyan else CyberBorder,
                                        RoundedCornerShape(10.dp)
                                    )
                                    .clickable { selectedOptionIndex = optIndex }
                                    .padding(12.dp)
                            ) {
                                Text(
                                    text = optionText,
                                    fontSize = 12.5.sp,
                                    color = if (isSelected) TextWhite else TextMuted
                                )
                            }
                        }

                        Button(
                            onClick = {
                                if (selectedOptionIndex == currentQ.correctOptionIndex) {
                                    score++
                                }
                                if (currentQuestionIndex < questions.size - 1) {
                                    currentQuestionIndex++
                                    selectedOptionIndex = -1
                                } else {
                                    isExamRunning = false
                                    examCompleted = true
                                    if (score >= 4) {
                                        onXpEarned(500)
                                    }
                                }
                            },
                            enabled = selectedOptionIndex >= 0,
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("next_question_button"),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = NeonCyan,
                                contentColor = CyberBackgroundDarker
                            ),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text(
                                text = if (currentQuestionIndex == questions.size - 1) "Terminer l'examen" else "Question Suivante",
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        } else {
            // Exam Results Screen
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = CyberSurface),
                    shape = RoundedCornerShape(16.dp),
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        if (score >= 4) EmeraldGreen else CrimsonRed
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Icon(
                            imageVector = if (score >= 4) Icons.Default.CheckCircle else Icons.Default.Refresh,
                            contentDescription = null,
                            tint = if (score >= 4) EmeraldGreen else CrimsonRed,
                            modifier = Modifier.size(54.dp)
                        )

                        Text(
                            text = if (score >= 4) "FÉLICITATIONS ! EXAMEN RÉUSSI" else "NON VALIDÉ - CONTINUEZ L'ENTRAÎNEMENT",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (score >= 4) EmeraldGreen else CrimsonRed,
                            textAlign = TextAlign.Center
                        )

                        Text(
                            text = "Votre score : $score / ${questions.size} (${(score * 100 / questions.size)}%)",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextWhite,
                            fontFamily = FontFamily.Monospace
                        )

                        Text(
                            text = if (score >= 4)
                                "Vous avez démontré une maîtrise solide des fondations de la cyber-défense. Votre certificat officiel brayanTech est validé et débloqué !"
                            else
                                "La note de passage minimale requise est de 80% (4 bonnes réponses sur 5). Révisez les cours et retentez votre chance.",
                            fontSize = 12.5.sp,
                            color = TextMuted,
                            textAlign = TextAlign.Center,
                            lineHeight = 17.sp
                        )

                        Button(
                            onClick = {
                                examCompleted = false
                                isExamRunning = false
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = CyberSurfaceVariant,
                                contentColor = TextWhite
                            ),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("Retour au Centre d'Évaluation")
                        }
                    }
                }
            }

            // Display Certificate
            item {
                BrayanTechCertificateCard(
                    profile = userProfile,
                    isVerified = (score >= 4),
                    onDownload = {
                        Toast.makeText(context, "Téléchargement du certificat officiel brayanTech...", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    }
}

@Composable
private fun ExamStatBadge(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = value, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextWhite)
        Text(text = label, fontSize = 10.sp, color = TextMuted)
    }
}

/**
 * Downloadable / Printable Official brayanTech Certificate Card
 */
@Composable
private fun BrayanTechCertificateCard(
    profile: UserProfile,
    isVerified: Boolean,
    onDownload: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("certificate_card"),
        colors = CardDefaults.cardColors(containerColor = CyberBackgroundDarker),
        shape = RoundedCornerShape(20.dp),
        border = androidx.compose.foundation.BorderStroke(2.dp, NeonCyanDark)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Certificate Top Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BrayanTechBrandLogo(size = 40.dp)

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "ACCRÉDITATION CYBER OFFICIELLE",
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = NeonCyan,
                        letterSpacing = 0.5.sp
                    )
                    Text(
                        text = "ID: BT-2026-SEC-8891",
                        fontSize = 11.sp,
                        fontFamily = FontFamily.Monospace,
                        color = TextMuted
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "CERTIFICAT D'EXCELLENCE",
                fontSize = 18.sp,
                fontWeight = FontWeight.Black,
                color = TextWhite,
                letterSpacing = 1.sp
            )
            Text(
                text = "CYBERSÉCURITÉ & ANALYSE DÉFENSIVE",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = NeonCyan
            )

            Text(
                text = "Ce certificat atteste que l'analyste",
                fontSize = 11.5.sp,
                color = TextMuted
            )

            Text(
                text = profile.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Black,
                color = TextWhite
            )

            Text(
                text = "a validé avec succès l'ensemble des modules pratiques brayanTech, démontrant des aptitudes de niveau avancé en hygiène numérique, cryptographie appliquée, tests d'intrusion et gestion des incidents SOC.",
                fontSize = 11.5.sp,
                color = TextMuted,
                textAlign = TextAlign.Center,
                lineHeight = 16.sp
            )

            // Seal & Signature Footer
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(text = "Date : 07 Septembre 2026", fontSize = 10.sp, color = TextMuted)
                    Text(text = "Statut : Vérifié cryptographiquement", fontSize = 10.sp, color = EmeraldGreen, fontWeight = FontWeight.Bold)
                }

                // Official Seal Badge
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(NeonCyanGlow)
                        .border(1.dp, NeonCyan, RoundedCornerShape(8.dp))
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "SCEAU brayanTech",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = NeonCyan
                    )
                }
            }

            // Download & Share CTA
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = onDownload,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = NeonCyan,
                        contentColor = CyberBackgroundDarker
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(Icons.Default.Download, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Télécharger (PDF)", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }
        }
    }
}
