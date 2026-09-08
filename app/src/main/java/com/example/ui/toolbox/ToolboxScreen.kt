package com.example.ui.toolbox

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.CyberRepository
import com.example.model.GlossaryTerm
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
import java.security.MessageDigest
import java.security.SecureRandom
import kotlin.math.log2

@Composable
fun ToolboxScreen() {
    var selectedToolTab by remember { mutableIntStateOf(0) }
    val toolTabs = listOf("Mots de Passe", "Hachage", "Analyseur URL", "Glossaire Cyber")

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("toolbox_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = "BOÎTE À OUTILS CYBER brayanTech",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = NeonCyan,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "Utilitaires & Analyseurs Pratiques",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextWhite
                )
                Text(
                    text = "Générez des clés robustes, calculez des condensats cryptographiques et auditez la sécurité des liens suspects.",
                    fontSize = 13.sp,
                    color = TextMuted,
                    lineHeight = 18.sp
                )
            }
        }

        // Horizontal Category Switcher
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                toolTabs.forEachIndexed { index, title ->
                    val isSelected = selectedToolTab == index
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (isSelected) NeonCyanGlow else CyberSurfaceVariant)
                            .border(1.dp, if (isSelected) NeonCyan else CyberBorder, RoundedCornerShape(8.dp))
                            .clickable { selectedToolTab = index }
                            .padding(vertical = 8.dp, horizontal = 4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = title,
                            fontSize = 10.5.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) NeonCyan else TextMuted
                        )
                    }
                }
            }
        }

        when (selectedToolTab) {
            0 -> item { PasswordStrengthAndGeneratorTool() }
            1 -> item { HashGeneratorAndIdentifierTool() }
            2 -> item { UrlThreatAnalyzerTool() }
            3 -> item { CyberGlossaryTool() }
        }
    }
}

/**
 * 1. Password Strength & Entropy Calculator with Generator
 */
@Composable
private fun PasswordStrengthAndGeneratorTool() {
    val context = LocalContext.current
    var inputPassword by remember { mutableStateOf("BTech@Sec#2026!") }
    var passwordLength by remember { mutableFloatStateOf(16f) }

    fun calculateEntropy(pass: String): Pair<Double, String> {
        if (pass.isEmpty()) return 0.0 to "Néant"
        var pool = 0
        if (pass.any { it.isLowerCase() }) pool += 26
        if (pass.any { it.isUpperCase() }) pool += 26
        if (pass.any { it.isDigit() }) pool += 10
        if (pass.any { !it.isLetterOrDigit() }) pool += 32
        if (pool == 0) pool = 26

        val entropy = pass.length * log2(pool.toDouble())
        val crackTime = when {
            entropy < 28 -> "Instantané (< 1 sec)"
            entropy < 45 -> "Quelques minutes"
            entropy < 65 -> "Plusieurs mois"
            entropy < 85 -> "Environ 2 000 ans"
            else -> "> 10 millions d'années (Résistant Post-Quantique)"
        }
        return entropy to crackTime
    }

    fun generateSecurePassword(length: Int): String {
        val chars = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ23456789!@#$%^&*()-_=+"
        val random = SecureRandom()
        return (1..length).map { chars[random.nextInt(chars.length)] }.joinToString("")
    }

    val (entropyBits, timeEstimation) = calculateEntropy(inputPassword)
    val entropyFraction = (entropyBits / 100.0).coerceIn(0.0, 1.0).toFloat()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("password_tool_card"),
        colors = CardDefaults.cardColors(containerColor = CyberSurface),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, CyberBorderBright)
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(Icons.Default.Key, contentDescription = null, tint = NeonCyan, modifier = Modifier.size(20.dp))
                Text(
                    text = "Calculateur d'Entropie & Force de Mot de Passe",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.5.sp,
                    color = TextWhite
                )
            }

            OutlinedTextField(
                value = inputPassword,
                onValueChange = { inputPassword = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("password_input"),
                label = { Text("Mot de passe à auditer") },
                singleLine = true,
                trailingIcon = {
                    IconButton(onClick = {
                        val cb = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        cb.setPrimaryClip(ClipData.newPlainText("Password", inputPassword))
                        Toast.makeText(context, "Copié dans le presse-papiers", Toast.LENGTH_SHORT).show()
                    }) {
                        Icon(Icons.Default.ContentCopy, contentDescription = "Copier", tint = NeonCyan)
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = TextWhite,
                    unfocusedTextColor = TextWhite,
                    focusedBorderColor = NeonCyan,
                    unfocusedBorderColor = CyberBorder
                )
            )

            // Entropy Gauge
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Entropie Shannon : ${entropyBits.toInt()} bits",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextWhite,
                        fontFamily = FontFamily.Monospace
                    )
                    Text(
                        text = if (entropyBits >= 65) "Très Robuste" else "Vulnérable",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (entropyBits >= 65) EmeraldGreen else CrimsonRed
                    )
                }

                LinearProgressIndicator(
                    progress = { entropyFraction },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(CircleShape),
                    color = if (entropyBits >= 65) EmeraldGreen else if (entropyBits >= 45) CyberAmber else CrimsonRed,
                    trackColor = CyberSurfaceVariant
                )

                Text(
                    text = "Temps estimé pour casser (Brute-Force GPU) : $timeEstimation",
                    fontSize = 11.5.sp,
                    color = TextMuted
                )
            }

            // Generator Section
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(CyberSurfaceElevated)
                    .padding(12.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Générateur Cryptographique : Longueur (${passwordLength.toInt()} caractères)",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextWhite
                    )

                    Slider(
                        value = passwordLength,
                        onValueChange = { passwordLength = it },
                        valueRange = 8f..32f,
                        steps = 23,
                        colors = SliderDefaults.colors(
                            thumbColor = NeonCyan,
                            activeTrackColor = NeonCyan,
                            inactiveTrackColor = CyberBorder
                        )
                    )

                    Button(
                        onClick = {
                            inputPassword = generateSecurePassword(passwordLength.toInt())
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = NeonCyan,
                            contentColor = CyberBackgroundDarker
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Générer un Nouveau Mot de Passe Sécurisé", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                }
            }
        }
    }
}

/**
 * 2. Hash Generator & Identifier Tool
 */
@Composable
private fun HashGeneratorAndIdentifierTool() {
    val context = LocalContext.current
    var textToHash by remember { mutableStateOf("brayanTech-Security-2026") }
    var hashToIdentify by remember { mutableStateOf("") }

    fun computeHash(text: String, algorithm: String): String {
        return try {
            val md = MessageDigest.getInstance(algorithm)
            val bytes = md.digest(text.toByteArray(Charsets.UTF_8))
            bytes.joinToString("") { "%02x".format(it) }
        } catch (e: Exception) {
            "Erreur"
        }
    }

    val md5Hash = remember(textToHash) { computeHash(textToHash, "MD5") }
    val sha256Hash = remember(textToHash) { computeHash(textToHash, "SHA-256") }
    val sha512Hash = remember(textToHash) { computeHash(textToHash, "SHA-512") }

    fun identifyHash(hash: String): String {
        val clean = hash.trim()
        return when (clean.length) {
            32 -> "Empreinte MD5 ou NTLM (32 caractères hexadécimaux)"
            40 -> "Empreinte SHA-1 (40 caractères hexadécimaux)"
            64 -> "Empreinte SHA-256 (64 caractères hexadécimaux - Standard Robuste)"
            128 -> "Empreinte SHA-512 / Whirlpool (128 caractères hexadécimaux)"
            else -> "Format de hash non reconnu (entrez 32, 40, 64 ou 128 caractères hex)"
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("hash_tool_card"),
        colors = CardDefaults.cardColors(containerColor = CyberSurface),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, CyberBorderBright)
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(Icons.Default.Fingerprint, contentDescription = null, tint = CyberPurple, modifier = Modifier.size(20.dp))
                Text(
                    text = "Générateur & Identificateur de Hachage",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.5.sp,
                    color = TextWhite
                )
            }

            OutlinedTextField(
                value = textToHash,
                onValueChange = { textToHash = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("hash_input_text"),
                label = { Text("Texte à hacher") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = TextWhite,
                    unfocusedTextColor = TextWhite,
                    focusedBorderColor = CyberPurple,
                    unfocusedBorderColor = CyberBorder
                )
            )

            // Computed Hashes
            HashResultRow(algorithm = "MD5 (Legacy)", hash = md5Hash, context = context)
            HashResultRow(algorithm = "SHA-256 (Recommandé)", hash = sha256Hash, context = context)
            HashResultRow(algorithm = "SHA-512 (Militaire)", hash = sha512Hash.take(48) + "...", context = context)

            Spacer(modifier = Modifier.height(6.dp))

            // Hash Identifier
            Text(
                text = "IDENTIFICATEUR DE TYPE DE CONDENSAT",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = NeonCyan,
                letterSpacing = 0.5.sp
            )

            OutlinedTextField(
                value = hashToIdentify,
                onValueChange = { hashToIdentify = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("hash_identify_input"),
                placeholder = { Text("Collez un hash à analyser...", fontSize = 11.5.sp, color = TextSubtle) },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = TextWhite,
                    unfocusedTextColor = TextWhite,
                    focusedBorderColor = NeonCyan,
                    unfocusedBorderColor = CyberBorder
                )
            )

            if (hashToIdentify.isNotBlank()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(CyberSurfaceElevated)
                        .padding(10.dp)
                ) {
                    Text(
                        text = identifyHash(hashToIdentify),
                        fontSize = 12.sp,
                        color = EmeraldGreen,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
private fun HashResultRow(algorithm: String, hash: String, context: Context) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(CyberBackgroundDarker)
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = algorithm, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = NeonCyan)
            Icon(
                imageVector = Icons.Default.ContentCopy,
                contentDescription = "Copier",
                tint = TextMuted,
                modifier = Modifier
                    .size(16.dp)
                    .clickable {
                        val cb = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        cb.setPrimaryClip(ClipData.newPlainText(algorithm, hash))
                        Toast.makeText(context, "Hash copié", Toast.LENGTH_SHORT).show()
                    }
            )
        }
        Text(
            text = hash,
            fontFamily = FontFamily.Monospace,
            fontSize = 11.sp,
            color = TextWhite,
            lineHeight = 15.sp
        )
    }
}

/**
 * 3. URL Threat Analyzer Simulator
 */
@Composable
private fun UrlThreatAnalyzerTool() {
    var urlInput by remember { mutableStateOf("http://login.secure-bank.brayan-update.xyz/auth?session=4819") }
    var analysisResult by remember { mutableStateOf<UrlReport?>(null) }

    fun analyzeUrl(url: String): UrlReport {
        val lower = url.lowercase().trim()
        val issues = mutableListOf<String>()
        var threatScore = 10 // scale 0-100 (higher = worse)

        if (lower.startsWith("http://")) {
            issues.add("Protocole HTTP non chiffré (vulnérable aux écoutes et attaques MITM)")
            threatScore += 30
        }
        if (lower.contains(".xyz") || lower.contains(".tk") || lower.contains(".top") || lower.contains(".ga")) {
            issues.add("TLD à haut risque statistique de malveillance détecté")
            threatScore += 25
        }
        if (lower.contains("login") || lower.contains("secure") || lower.contains("bank") || lower.contains("update")) {
            issues.add("Mots-clés d'appât d'ingénierie sociale (Usurpation de marque probable)")
            threatScore += 25
        }
        if (Regex("""\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}""").containsMatchIn(lower)) {
            issues.add("Utilisation directe d'une adresse IP sans nom de domaine légitime")
            threatScore += 35
        }

        threatScore = threatScore.coerceIn(5, 95)
        val verdict = when {
            threatScore >= 60 -> "MALVEILLANT / PHISHING PROBABLE"
            threatScore >= 35 -> "SUSPECT / PRUDENCE REQUISE"
            else -> "RÉPUTATION NEUTRE / FAIBLE RISQUE"
        }
        return UrlReport(url = url, score = threatScore, verdict = verdict, indicators = issues)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("url_tool_card"),
        colors = CardDefaults.cardColors(containerColor = CyberSurface),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, CyberBorderBright)
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(Icons.Default.Link, contentDescription = null, tint = EmeraldGreen, modifier = Modifier.size(20.dp))
                Text(
                    text = "Analyseur Heuristique de Menaces d'URL",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.5.sp,
                    color = TextWhite
                )
            }

            OutlinedTextField(
                value = urlInput,
                onValueChange = { urlInput = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("url_input_field"),
                label = { Text("URL à auditer") },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = TextWhite,
                    unfocusedTextColor = TextWhite,
                    focusedBorderColor = EmeraldGreen,
                    unfocusedBorderColor = CyberBorder
                )
            )

            Button(
                onClick = { analysisResult = analyzeUrl(urlInput) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = EmeraldGreen, contentColor = CyberBackgroundDarker),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Lancer l'Analyse Heuristique", fontWeight = FontWeight.Bold)
            }

            if (analysisResult != null) {
                val report = analysisResult!!
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(
                            if (report.score >= 60) CrimsonRed.copy(alpha = 0.15f)
                            else if (report.score >= 35) CyberAmber.copy(alpha = 0.15f)
                            else EmeraldGreen.copy(alpha = 0.15f)
                        )
                        .border(
                            1.dp,
                            if (report.score >= 60) CrimsonRed else if (report.score >= 35) CyberAmber else EmeraldGreen,
                            RoundedCornerShape(10.dp)
                        )
                        .padding(12.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(
                            text = "VERDICT : ${report.verdict} (Indice de risque: ${report.score}/100)",
                            fontSize = 12.5.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (report.score >= 60) CrimsonRed else if (report.score >= 35) CyberAmber else EmeraldGreen
                        )

                        report.indicators.forEach { ind ->
                            Text(text = "⚠️ $ind", fontSize = 11.5.sp, color = TextWhite)
                        }
                    }
                }
            }
        }
    }
}

data class UrlReport(
    val url: String,
    val score: Int,
    val verdict: String,
    val indicators: List<String>
)

/**
 * 4. Cyber Glossary Tool
 */
@Composable
private fun CyberGlossaryTool() {
    var searchQuery by remember { mutableStateOf("") }
    val terms = remember { CyberRepository.glossaryTerms }
    val filtered = remember(searchQuery) {
        if (searchQuery.isBlank()) terms
        else terms.filter {
            it.term.contains(searchQuery, ignoreCase = true) ||
                    it.definition.contains(searchQuery, ignoreCase = true) ||
                    it.tags.any { tag -> tag.contains(searchQuery, ignoreCase = true) }
        }
    }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("glossary_search"),
            placeholder = { Text("Rechercher un terme (ex: SOC, Ransomware, MITM)...", fontSize = 12.sp, color = TextSubtle) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = NeonCyan) },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = TextWhite,
                unfocusedTextColor = TextWhite,
                focusedBorderColor = NeonCyan,
                unfocusedBorderColor = CyberBorder
            )
        )

        filtered.forEach { term ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("term_${term.term}"),
                colors = CardDefaults.cardColors(containerColor = CyberSurface),
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, CyberBorder)
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = term.term,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = NeonCyan
                        )
                        Text(
                            text = term.category,
                            fontSize = 10.sp,
                            color = TextMuted
                        )
                    }

                    Text(
                        text = term.definition,
                        fontSize = 12.5.sp,
                        color = TextWhite,
                        lineHeight = 17.sp
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(6.dp))
                            .background(CyberBackgroundDarker)
                            .padding(8.dp)
                    ) {
                        Text(
                            text = "Exemple réel : ${term.example}",
                            fontSize = 11.5.sp,
                            color = TextMuted,
                            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                        )
                    }
                }
            }
        }
    }
}
