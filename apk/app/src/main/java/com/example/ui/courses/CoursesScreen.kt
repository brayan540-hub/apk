package com.example.ui.courses

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.CourseModule
import com.example.model.CyberRepository
import com.example.model.Lesson
import com.example.model.QuizQuestion
import com.example.ui.theme.CrimsonRed
import com.example.ui.theme.CyberAmber
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

@Composable
fun CoursesScreen(
    initialCourseId: String? = null,
    onXpEarned: (Int) -> Unit = {}
) {
    var selectedDifficulty by remember { mutableStateOf("Tous") }
    var activeCourse by remember {
        mutableStateOf(
            if (initialCourseId != null) {
                CyberRepository.courses.find { it.id == initialCourseId }
            } else null
        )
    }
    var activeLesson by remember { mutableStateOf<Lesson?>(null) }

    val difficulties = listOf("Tous", "Débutant", "Intermédiaire", "Avancé")
    val filteredCourses = remember(selectedDifficulty) {
        if (selectedDifficulty == "Tous") {
            CyberRepository.courses
        } else {
            CyberRepository.courses.filter { it.difficulty.equals(selectedDifficulty, ignoreCase = true) }
        }
    }

    if (activeLesson != null && activeCourse != null) {
        LessonDetailView(
            course = activeCourse!!,
            lesson = activeLesson!!,
            onBack = { activeLesson = null },
            onCompleteLesson = {
                onXpEarned(100)
                activeLesson = null
            }
        )
    } else if (activeCourse != null) {
        CourseDetailView(
            course = activeCourse!!,
            onBack = { activeCourse = null },
            onSelectLesson = { lesson -> activeLesson = lesson }
        )
    } else {
        // Course Catalog View
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .testTag("courses_catalog_screen"),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = "PROGRAMMES DE FORMATION CYBER",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = NeonCyan,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "Cursus Spécialisés brayanTech",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextWhite
                    )
                    Text(
                        text = "Apprenez la théorie défensive et offensive à travers des cas pratiques réels et des architectures sécurisées.",
                        fontSize = 13.sp,
                        color = TextMuted,
                        lineHeight = 18.sp
                    )
                }
            }

            // Difficulty Filter Chips
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    difficulties.forEach { diff ->
                        val isSelected = selectedDifficulty == diff
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSelected) NeonCyanGlow else CyberSurfaceVariant)
                                .border(
                                    1.dp,
                                    if (isSelected) NeonCyan else CyberBorder,
                                    RoundedCornerShape(8.dp)
                                )
                                .clickable { selectedDifficulty = diff }
                                .padding(horizontal = 14.dp, vertical = 7.dp)
                        ) {
                            Text(
                                text = diff,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) NeonCyan else TextMuted
                            )
                        }
                    }
                }
            }

            items(filteredCourses) { course ->
                CourseCatalogCard(
                    course = course,
                    onClick = { activeCourse = course }
                )
            }
        }
    }
}

@Composable
private fun CourseCatalogCard(course: CourseModule, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .testTag("catalog_card_${course.id}"),
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
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(CyberSurfaceVariant)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = course.category.uppercase(),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = NeonCyan,
                        letterSpacing = 0.5.sp
                    )
                }

                // Difficulty badge
                val badgeColor = when (course.difficulty) {
                    "Débutant" -> EmeraldGreen
                    "Intermédiaire" -> CyberAmber
                    else -> CrimsonRed
                }
                Text(
                    text = course.difficulty,
                    fontSize = 11.5.sp,
                    fontWeight = FontWeight.Bold,
                    color = badgeColor
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(NeonCyanGlow),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = course.icon,
                        contentDescription = null,
                        tint = NeonCyan,
                        modifier = Modifier.size(26.dp)
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = course.title,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextWhite
                    )
                    Text(
                        text = course.subtitle,
                        fontSize = 12.sp,
                        color = TextMuted,
                        maxLines = 1
                    )
                }
            }

            Text(
                text = course.description,
                fontSize = 12.5.sp,
                color = TextMuted,
                lineHeight = 17.sp
            )

            // Progress and Action
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${course.lessonsCount} chapitres",
                        fontSize = 11.sp,
                        color = TextWhite,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "•",
                        fontSize = 11.sp,
                        color = TextSubtle
                    )
                    Text(
                        text = "+${course.xpReward} XP",
                        fontSize = 11.sp,
                        color = EmeraldGreen,
                        fontWeight = FontWeight.Bold
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = if (course.progressPercent == 100) "Revoir" else "Démarrer",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = NeonCyan
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        tint = NeonCyan,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun CourseDetailView(
    course: CourseModule,
    onBack: () -> Unit,
    onSelectLesson: (Lesson) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("course_detail_view"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Retour",
                        tint = TextWhite
                    )
                }
                Text(
                    text = "Aperçu du cours",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextWhite
                )
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = CyberSurface),
                shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, CyberBorderBright)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = course.title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextWhite
                    )
                    Text(
                        text = course.description,
                        fontSize = 13.sp,
                        color = TextMuted,
                        lineHeight = 18.sp
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        DetailChip(label = "Difficulté", value = course.difficulty)
                        DetailChip(label = "Durée", value = "${course.durationMinutes} min")
                        DetailChip(label = "XP", value = "+${course.xpReward}")
                    }
                }
            }
        }

        item {
            Text(
                text = "CHAPITRES & MODULES",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = NeonCyan,
                letterSpacing = 1.sp
            )
        }

        items(course.lessons) { lesson ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSelectLesson(lesson) }
                    .testTag("lesson_item_${lesson.id}"),
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
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(NeonCyanGlow),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = null,
                            tint = NeonCyan,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = lesson.title,
                            fontSize = 13.5.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextWhite
                        )
                        Text(
                            text = "${lesson.estimatedMinutes} min de lecture • Quiz inclus",
                            fontSize = 11.5.sp,
                            color = TextMuted
                        )
                    }

                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        tint = TextMuted,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun DetailChip(label: String, value: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(CyberSurfaceVariant)
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Column {
            Text(text = label, fontSize = 9.sp, color = TextSubtle)
            Text(text = value, fontSize = 11.5.sp, fontWeight = FontWeight.Bold, color = TextWhite)
        }
    }
}

/**
 * Full interactive lesson reader with rich code blocks, takeaways and immediate quiz validation
 */
@Composable
private fun LessonDetailView(
    course: CourseModule,
    lesson: Lesson,
    onBack: () -> Unit,
    onCompleteLesson: () -> Unit
) {
    var quizSubmitted by remember { mutableStateOf(false) }
    var selectedQuizOption by remember { mutableIntStateOf(-1) }
    var isQuizCorrect by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("lesson_detail_view"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Retour",
                        tint = TextWhite
                    )
                }
                Text(
                    text = course.title,
                    fontSize = 14.sp,
                    color = NeonCyan,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1
                )
            }
        }

        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = lesson.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextWhite
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "⏱️ ${lesson.estimatedMinutes} min de formation",
                        fontSize = 12.sp,
                        color = TextMuted
                    )
                }
            }
        }

        // Lesson Body Sections
        items(lesson.contentSections) { section ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = CyberSurface),
                shape = RoundedCornerShape(14.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, CyberBorder)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = section.heading,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = NeonCyan
                    )
                    Text(
                        text = section.body,
                        fontSize = 13.sp,
                        color = TextWhite,
                        lineHeight = 19.sp
                    )
                }
            }
        }

        // Code Snippet block if present
        if (lesson.codeSnippet != null) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "EXTRAIT DE CODE & SYNTAXE",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = CyberPurple,
                            letterSpacing = 0.5.sp
                        )
                        Text(
                            text = lesson.codeLanguage ?: "Code",
                            fontSize = 10.sp,
                            color = TextMuted
                        )
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(CyberBackgroundDarker)
                            .border(1.dp, CyberBorderBright, RoundedCornerShape(12.dp))
                            .padding(14.dp)
                    ) {
                        Text(
                            text = lesson.codeSnippet,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 12.sp,
                            color = NeonCyan,
                            lineHeight = 17.sp
                        )
                    }
                }
            }
        }

        // Key Takeaways
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = CyberSurfaceElevated),
                shape = RoundedCornerShape(14.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, NeonCyanDark)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Lightbulb,
                            contentDescription = null,
                            tint = CyberAmber,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = "Points Clés à Retenir",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextWhite
                        )
                    }

                    lesson.keyTakeaways.forEach { takeaway ->
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Text("🛡️", fontSize = 12.sp)
                            Text(
                                text = takeaway,
                                fontSize = 12.5.sp,
                                color = TextWhite,
                                lineHeight = 17.sp
                            )
                        }
                    }
                }
            }
        }

        // Quick Quiz Question
        if (lesson.quickQuiz != null) {
            val quiz = lesson.quickQuiz
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("lesson_quiz_card"),
                    colors = CardDefaults.cardColors(containerColor = CyberSurface),
                    shape = RoundedCornerShape(16.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, CyberBorderBright)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.HelpOutline,
                                contentDescription = null,
                                tint = NeonCyan,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "QUIZ RAPIDE DE VALIDATION",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = NeonCyan,
                                letterSpacing = 0.5.sp
                            )
                        }

                        Text(
                            text = quiz.question,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextWhite,
                            lineHeight = 19.sp
                        )

                        // Options
                        quiz.options.forEachIndexed { index, option ->
                            val isSelected = selectedQuizOption == index
                            val optionBorder = when {
                                quizSubmitted && index == quiz.correctOptionIndex -> EmeraldGreen
                                quizSubmitted && isSelected && !isQuizCorrect -> CrimsonRed
                                isSelected -> NeonCyan
                                else -> CyberBorder
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(if (isSelected) CyberSurfaceElevated else CyberBackgroundDarker)
                                    .border(1.dp, optionBorder, RoundedCornerShape(10.dp))
                                    .clickable(enabled = !quizSubmitted) {
                                        selectedQuizOption = index
                                    }
                                    .padding(12.dp)
                            ) {
                                Text(
                                    text = option,
                                    fontSize = 12.5.sp,
                                    color = if (isSelected) TextWhite else TextMuted
                                )
                            }
                        }

                        if (!quizSubmitted) {
                            Button(
                                onClick = {
                                    if (selectedQuizOption >= 0) {
                                        quizSubmitted = true
                                        isQuizCorrect = (selectedQuizOption == quiz.correctOptionIndex)
                                    }
                                },
                                enabled = selectedQuizOption >= 0,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("submit_quiz_button"),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = NeonCyan,
                                    contentColor = CyberBackgroundDarker
                                ),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text("Valider ma réponse", fontWeight = FontWeight.Bold)
                            }
                        } else {
                            // Feedback box
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(
                                        if (isQuizCorrect) EmeraldGreen.copy(alpha = 0.15f)
                                        else CrimsonRed.copy(alpha = 0.15f)
                                    )
                                    .border(
                                        1.dp,
                                        if (isQuizCorrect) EmeraldGreen else CrimsonRed,
                                        RoundedCornerShape(10.dp)
                                    )
                                    .padding(12.dp)
                            ) {
                                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Text(
                                        text = if (isQuizCorrect) "Bravo, réponse correcte ! (+50 XP)" else "Réponse incorrecte",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp,
                                        color = if (isQuizCorrect) EmeraldGreen else CrimsonRed
                                    )
                                    Text(
                                        text = quiz.explanation,
                                        fontSize = 12.sp,
                                        color = TextWhite,
                                        lineHeight = 16.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Finish Lesson Button
        item {
            Button(
                onClick = onCompleteLesson,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("complete_lesson_button"),
                colors = ButtonDefaults.buttonColors(
                    containerColor = EmeraldGreen,
                    contentColor = CyberBackgroundDarker
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.Check, contentDescription = null)
                Spacer(modifier = Modifier.width(6.dp))
                Text("Marquer comme terminé (+100 XP)", fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
