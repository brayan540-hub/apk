package com.example.ui.labs

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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.CtfLab
import com.example.model.CyberRepository
import com.example.model.LabType
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
fun LabsScreen(
    onXpEarned: (Int) -> Unit = {}
) {
    var activeLab by remember { mutableStateOf<CtfLab?>(null) }
    val labs = remember { mutableStateListOf(*CyberRepository.ctfLabs.toTypedArray()) }

    if (activeLab != null) {
        when (activeLab!!.labType) {
            LabType.TERMINAL_CLI -> TerminalCliLabView(
                lab = activeLab!!,
                onBack = { activeLab = null },
                onSolved = {
                    activeLab!!.isSolved = true
                    onXpEarned(activeLab!!.points)
                }
            )
            LabType.PHISHING_DETECTOR -> PhishingDetectorLabView(
                lab = activeLab!!,
                onBack = { activeLab = null },
                onSolved = {
                    activeLab!!.isSolved = true
                    onXpEarned(activeLab!!.points)
                }
            )
            LabType.LOG_ANALYZER -> LogAnalyzerLabView(
                lab = activeLab!!,
                onBack = { activeLab = null },
                onSolved = {
                    activeLab!!.isSolved = true
                    onXpEarned(activeLab!!.points)
                }
            )
            LabType.CRYPTO_DECODER -> CryptoDecoderLabView(
                lab = activeLab!!,
                onBack = { activeLab = null },
                onSolved = {
                    activeLab!!.isSolved = true
                    onXpEarned(activeLab!!.points)
                }
            )
        }
    } else {
        // List of Labs
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .testTag("labs_list_screen"),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = "LABS PRATIQUES & CAPTURE THE FLAG (CTF)",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = NeonCyan,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "Arène Pratique brayanTech",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextWhite
                    )
                    Text(
                        text = "Infiltrez des environnements simulés, analysez les artefacts d'attaques réelles et extrayez les flags brayanTech.",
                        fontSize = 13.sp,
                        color = TextMuted,
                        lineHeight = 18.sp
                    )
                }
            }

            items(labs) { lab ->
                LabItemCard(
                    lab = lab,
                    onClick = { activeLab = lab }
                )
            }
        }
    }
}

@Composable
private fun LabItemCard(lab: CtfLab, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .testTag("lab_card_${lab.id}"),
        colors = CardDefaults.cardColors(containerColor = CyberSurface),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (lab.isSolved) EmeraldGreen.copy(alpha = 0.6f) else CyberBorderBright
        )
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
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
                        text = lab.category.uppercase(),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = NeonCyan,
                        letterSpacing = 0.5.sp
                    )
                }

                if (lab.isSolved) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = EmeraldGreen,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "RÉSOLU",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = EmeraldGreen
                        )
                    }
                } else {
                    Text(
                        text = "+${lab.points} PTS",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = CyberAmber,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                val icon = when (lab.labType) {
                    LabType.TERMINAL_CLI -> Icons.Default.Terminal
                    LabType.PHISHING_DETECTOR -> Icons.Default.Email
                    LabType.LOG_ANALYZER -> Icons.Default.Search
                    LabType.CRYPTO_DECODER -> Icons.Default.Lock
                }

                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (lab.isSolved) EmeraldGreen.copy(alpha = 0.15f) else CyberPurple.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = if (lab.isSolved) EmeraldGreen else CyberPurple,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = lab.title,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextWhite
                    )
                    Text(
                        text = "Cible: ${lab.targetHost}",
                        fontSize = 11.5.sp,
                        color = TextMuted,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }

            Text(
                text = lab.scenario,
                fontSize = 12.5.sp,
                color = TextMuted,
                lineHeight = 17.sp
            )

            Button(
                onClick = onClick,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (lab.isSolved) CyberSurfaceVariant else NeonCyan,
                    contentColor = if (lab.isSolved) NeonCyan else CyberBackgroundDarker
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text = if (lab.isSolved) "Revoir la mission" else "Lancer le lab",
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

/**
 * 1. Interactive Terminal CLI simulator
 */
@Composable
private fun TerminalCliLabView(
    lab: CtfLab,
    onBack: () -> Unit,
    onSolved: () -> Unit
) {
    var inputCommand by remember { mutableStateOf("") }
    var flagInput by remember { mutableStateOf("") }
    var showHint by remember { mutableStateOf(false) }
    var flagStatusMessage by remember { mutableStateOf<String?>(null) }

    val terminalLines = remember {
        mutableStateListOf(
            "brayanTech Terminal v4.2.1-secOS [Linux x86_64]",
            "Connexion établie avec la sandbox isolée...",
            "Tapez 'help' pour la liste des outils réseau disponibles.",
            "--------------------------------------------------"
        )
    }

    fun executeCommand(rawCmd: String) {
        val cmd = rawCmd.trim()
        if (cmd.isEmpty()) return

        terminalLines.add("sentinel@brayantech:~$ $cmd")

        val parts = cmd.split(" ")
        val main = parts[0].lowercase()

        when (main) {
            "help" -> {
                terminalLines.add("Commandes disponibles :")
                terminalLines.add("  nmap -sV <cible>         : Scanner les ports et versions")
                terminalLines.add("  ping -c 3 <cible>        : Test de connectivité ICMP")
                terminalLines.add("  curl <url>               : Requête HTTP sur un service")
                terminalLines.add("  whoami                   : Identité de l'analyste")
                terminalLines.add("  clear                    : Effacer l'écran de la console")
                terminalLines.add("  flag <clé>               : Soumettre directement un drapeau")
            }
            "clear" -> {
                terminalLines.clear()
                terminalLines.add("brayanTech Terminal [Écran réinitialisé]")
            }
            "whoami" -> {
                terminalLines.add("sentinel (brayanTech Sentinel Level 5 - Authorization: ROE-2026-ALPHA)")
            }
            "ping" -> {
                terminalLines.add("PING 10.10.14.88 (10.10.14.88) 56(84) bytes of data.")
                terminalLines.add("64 bytes from 10.10.14.88: icmp_seq=1 ttl=64 time=14.2 ms")
                terminalLines.add("64 bytes from 10.10.14.88: icmp_seq=2 ttl=64 time=12.8 ms")
                terminalLines.add("64 bytes from 10.10.14.88: icmp_seq=3 ttl=64 time=13.1 ms")
                terminalLines.add("--- 10.10.14.88 ping statistics ---")
                terminalLines.add("3 packets transmitted, 3 received, 0% packet loss, time 2003ms")
            }
            "nmap" -> {
                terminalLines.add("Starting Nmap 7.94 ( https://nmap.org ) at 2026-09-07 09:12 UTC")
                terminalLines.add("Nmap scan report for target.internal (10.10.14.88)")
                terminalLines.add("Host is up (0.013s latency).")
                terminalLines.add("PORT     STATE SERVICE VERSION")
                terminalLines.add("22/tcp   open  ssh     OpenSSH 8.9p1 Ubuntu 3ubuntu0.6")
                terminalLines.add("80/tcp   open  http    nginx/1.18.0 (Ubuntu)")
                terminalLines.add("8080/tcp open  http-proxy brayanTech Secret Vault Service")
                terminalLines.add("Service Info: OS: Linux; CPE: cpe:/o:linux:linux_kernel")
                terminalLines.add("[+] INDICE : Essayez d'interroger le port 8080 avec 'curl http://10.10.14.88:8080/flag'")
            }
            "curl" -> {
                val url = parts.getOrNull(1) ?: ""
                if (url.contains("8080/flag")) {
                    terminalLines.add("HTTP/1.1 200 OK")
                    terminalLines.add("Server: brayanTech-Vault/2.0")
                    terminalLines.add("Content-Type: text/plain; charset=utf-8")
                    terminalLines.add("")
                    terminalLines.add("[!] FLAG DECOUVERT : ${lab.expectedFlag}")
                    terminalLines.add("[*] Copiez ce drapeau dans le champ ci-dessous pour valider la mission !")
                } else if (url.contains("8080")) {
                    terminalLines.add("HTTP/1.1 200 OK")
                    terminalLines.add("Bienvenue sur le portail secret du vault. Rendez-vous sur /flag pour la récompense.")
                } else {
                    terminalLines.add("HTTP/1.1 200 OK - Serveur Web Standard Nginx en cours d'exécution.")
                }
            }
            "flag" -> {
                val submitted = parts.getOrNull(1) ?: ""
                if (submitted == lab.expectedFlag) {
                    terminalLines.add("[SUCCESS] FLAG VALIDE ! +${lab.points} XP accordés au profil Sentinel !")
                    flagStatusMessage = "Succès ! Flag validé avec brio !"
                    onSolved()
                } else {
                    terminalLines.add("[ERROR] Flag invalide. Vérifiez la syntaxe (ex: brayanTech{...}).")
                }
            }
            else -> {
                terminalLines.add("bash: $main: commande inconnue. Tapez 'help' pour les commandes valides.")
            }
        }
        inputCommand = ""
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("terminal_lab_view"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Retour", tint = TextWhite)
                }
                Column {
                    Text(text = lab.title, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextWhite)
                    Text(text = "Console Interactive & Reconnaissance", fontSize = 11.5.sp, color = NeonCyan)
                }
            }
        }

        // Scenario & Mission Brief
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = CyberSurface),
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, CyberBorder)
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = "Briefing de Mission", fontWeight = FontWeight.Bold, color = NeonCyan, fontSize = 13.sp)
                    Text(text = lab.missionBrief, color = TextWhite, fontSize = 12.5.sp, lineHeight = 17.sp)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (showHint) "Indice: ${lab.initialHint}" else "Besoin d'aide ?",
                            fontSize = 11.sp,
                            color = if (showHint) CyberAmber else TextMuted
                        )
                        if (!showHint) {
                            Text(
                                text = "Afficher l'indice",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = NeonCyan,
                                modifier = Modifier.clickable { showHint = true }
                            )
                        }
                    }
                }
            }
        }

        // Terminal Display Box
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(CyberBackgroundDarker)
                    .border(1.dp, NeonCyanDark, RoundedCornerShape(12.dp))
                    .padding(12.dp)
            ) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(terminalLines) { line ->
                        Text(
                            text = line,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 11.5.sp,
                            color = when {
                                line.contains("[SUCCESS]") || line.contains("[!]") -> EmeraldGreen
                                line.contains("[ERROR]") -> CrimsonRed
                                line.contains("sentinel@brayantech") -> NeonCyan
                                else -> TextWhite
                            },
                            lineHeight = 16.sp
                        )
                    }
                }
            }
        }

        // Terminal Command Input Field
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = inputCommand,
                    onValueChange = { inputCommand = it },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("terminal_command_input"),
                    placeholder = { Text("Tapez 'nmap -sV 10.10.14.88' ou 'help'...", fontSize = 11.5.sp, color = TextSubtle) },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = TextWhite,
                        unfocusedTextColor = TextWhite,
                        focusedBorderColor = NeonCyan,
                        unfocusedBorderColor = CyberBorder,
                        focusedContainerColor = CyberSurface,
                        unfocusedContainerColor = CyberSurface
                    ),
                    textStyle = androidx.compose.ui.text.TextStyle(
                        fontFamily = FontFamily.Monospace,
                        fontSize = 12.sp
                    ),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                    keyboardActions = KeyboardActions(onSend = { executeCommand(inputCommand) })
                )

                Button(
                    onClick = { executeCommand(inputCommand) },
                    colors = ButtonDefaults.buttonColors(containerColor = NeonCyan, contentColor = CyberBackgroundDarker),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Exécuter", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }
        }

        // Flag Submission Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = CyberSurface),
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, CyberBorder)
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "Soumettre le Flag CTF (brayanTech{...})",
                        fontWeight = FontWeight.Bold,
                        color = TextWhite,
                        fontSize = 13.sp
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = flagInput,
                            onValueChange = { flagInput = it },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("flag_submission_input"),
                            placeholder = { Text("brayanTech{...}", fontSize = 12.sp, color = TextSubtle) },
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = TextWhite,
                                unfocusedTextColor = TextWhite,
                                focusedBorderColor = EmeraldGreen,
                                unfocusedBorderColor = CyberBorder
                            )
                        )

                        Button(
                            onClick = {
                                if (flagInput.trim() == lab.expectedFlag) {
                                    flagStatusMessage = "Félicitations ! Flag validé avec succès !"
                                    onSolved()
                                } else {
                                    flagStatusMessage = "Drapeau incorrect. Inspectez la sortie de la console."
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = EmeraldGreen, contentColor = CyberBackgroundDarker),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("Valider", fontWeight = FontWeight.Bold)
                        }
                    }

                    if (flagStatusMessage != null) {
                        Text(
                            text = flagStatusMessage!!,
                            color = if (flagStatusMessage!!.contains("Félicitations") || flagStatusMessage!!.contains("Succès")) EmeraldGreen else CrimsonRed,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

/**
 * 2. Phishing Email Detector Lab
 */
@Composable
private fun PhishingDetectorLabView(
    lab: CtfLab,
    onBack: () -> Unit,
    onSolved: () -> Unit
) {
    var analyzedHeader by remember { mutableStateOf(false) }
    var solvedState by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("phishing_lab_view"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Retour", tint = TextWhite)
                }
                Text(text = lab.title, fontSize = 17.sp, fontWeight = FontWeight.Bold, color = TextWhite)
            }
        }

        // Email Viewer Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = CyberSurface),
                shape = RoundedCornerShape(14.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, CyberBorderBright)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "DE : support@brayan-tech-security.tk",
                        fontSize = 12.sp,
                        fontFamily = FontFamily.Monospace,
                        color = CrimsonRed,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "À : direction-financiere@brayantech.cm",
                        fontSize = 12.sp,
                        fontFamily = FontFamily.Monospace,
                        color = TextMuted
                    )
                    Text(
                        text = "OBJET : URGENT: Suspension imminente de votre compte administrateur",
                        fontSize = 13.5.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextWhite
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(CyberBackgroundDarker)
                            .padding(12.dp)
                    ) {
                        Text(
                            text = "Bonjour,\nUne anomalie de sécurité a été détectée sur votre session. Veuillez cliquer sur le lien ci-dessous sous 15 minutes pour confirmer vos identifiants ou votre accès sera révoqué.\n\n-> http://login.brayan-tech-security.tk/auth-reset",
                            fontSize = 12.5.sp,
                            color = TextWhite,
                            lineHeight = 18.sp
                        )
                    }

                    Button(
                        onClick = { analyzedHeader = true },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CyberPurple,
                            contentColor = TextWhite
                        ),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(Icons.Default.Search, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Inspecter les En-têtes Techniques (Headers)")
                    }
                }
            }
        }

        if (analyzedHeader) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = CyberSurfaceElevated),
                    shape = RoundedCornerShape(14.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, CrimsonRed)
                ) {
                    Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = "ANALYSE DES EN-TÊTES SMTP DÉTECTÉE",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = CrimsonRed
                        )
                        Text(
                            text = "• Return-Path: <attacker@darknet-node.su> (Usurpation confirmée)\n" +
                                    "• SPF: FAIL (IP d'émission non autorisée)\n" +
                                    "• DKIM: NONE (Absence de signature cryptographique)\n" +
                                    "• Domaine Typosquatté: brayan-tech-security.tk != brayantech.cm",
                            fontSize = 12.sp,
                            color = TextWhite,
                            fontFamily = FontFamily.Monospace,
                            lineHeight = 17.sp
                        )

                        Button(
                            onClick = {
                                solvedState = true
                                onSolved()
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = EmeraldGreen,
                                contentColor = CyberBackgroundDarker
                            ),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("Signaler comme Phishing & Extraire le Flag", fontWeight = FontWeight.Bold)
                        }

                        if (solvedState) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(EmeraldGreen.copy(alpha = 0.2f))
                                    .padding(10.dp)
                            ) {
                                Text(
                                    text = "FLAG : ${lab.expectedFlag}",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = EmeraldGreen,
                                    fontFamily = FontFamily.Monospace
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * 3. Log Analyzer Lab
 */
@Composable
private fun LogAnalyzerLabView(
    lab: CtfLab,
    onBack: () -> Unit,
    onSolved: () -> Unit
) {
    var filterSqli by remember { mutableStateOf(false) }
    var solved by remember { mutableStateOf(false) }

    val rawLogs = listOf(
        "192.168.1.5 - - [07/Sep/2026:08:14:02] \"GET /index.html HTTP/1.1\" 200 4520",
        "192.168.1.12 - - [07/Sep/2026:08:15:10] \"GET /css/app.css HTTP/1.1\" 200 1204",
        "198.51.100.42 - - [07/Sep/2026:08:22:19] \"POST /api/auth?user=admin' UNION SELECT 1,table_name,password FROM information_schema.tables-- HTTP/1.1\" 200 8920",
        "192.168.1.99 - - [07/Sep/2026:08:23:45] \"GET /favicon.ico HTTP/1.1\" 404 180"
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("log_analyzer_view"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Retour", tint = TextWhite)
                }
                Text(text = lab.title, fontSize = 17.sp, fontWeight = FontWeight.Bold, color = TextWhite)
            }
        }

        item {
            Text(
                text = "Filtrez les requêtes suspectes pour isoler l'empreinte d'une attaque par injection SQL.",
                fontSize = 13.sp,
                color = TextMuted
            )
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { filterSqli = !filterSqli },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (filterSqli) NeonCyan else CyberSurfaceVariant,
                        contentColor = if (filterSqli) CyberBackgroundDarker else TextWhite
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(if (filterSqli) "Afficher tous les logs" else "Filtrer attaques SQLi", fontSize = 12.sp)
                }
            }
        }

        // Log Box
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(CyberBackgroundDarker)
                    .border(1.dp, CyberBorder, RoundedCornerShape(10.dp))
                    .padding(12.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    val displayed = if (filterSqli) rawLogs.filter { it.contains("UNION SELECT") } else rawLogs
                    displayed.forEach { line ->
                        Text(
                            text = line,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 11.sp,
                            color = if (line.contains("UNION SELECT")) CrimsonRed else TextWhite,
                            lineHeight = 15.sp
                        )
                    }
                }
            }
        }

        item {
            Button(
                onClick = {
                    solved = true
                    onSolved()
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = EmeraldGreen, contentColor = CyberBackgroundDarker),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Isoler l'attaquant (198.51.100.42) & Récupérer le Flag", fontWeight = FontWeight.Bold)
            }
        }

        if (solved) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(EmeraldGreen.copy(alpha = 0.2f))
                        .padding(12.dp)
                ) {
                    Text(
                        text = "FLAG DÉCOUVERT : ${lab.expectedFlag}",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = EmeraldGreen,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }
        }
    }
}

/**
 * 4. Crypto Decoder Lab
 */
@Composable
private fun CryptoDecoderLabView(
    lab: CtfLab,
    onBack: () -> Unit,
    onSolved: () -> Unit
) {
    var isDecoded by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("crypto_decoder_view"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Retour", tint = TextWhite)
                }
                Text(text = lab.title, fontSize = 17.sp, fontWeight = FontWeight.Bold, color = TextWhite)
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = CyberSurface),
                shape = RoundedCornerShape(14.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, CyberBorder)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "Message Intercepté (Base64 + Décalage)",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = CyberPurple
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(CyberBackgroundDarker)
                            .padding(12.dp)
                    ) {
                        Text(
                            text = "WW41aFlXNTBaV05vZTBOeVdUYzBfRDBqTURabFlsOTRNM04wWlhKYX0=",
                            fontFamily = FontFamily.Monospace,
                            fontSize = 12.sp,
                            color = NeonCyan
                        )
                    }

                    Button(
                        onClick = {
                            isDecoded = true
                            onSolved()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = NeonCyan, contentColor = CyberBackgroundDarker),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Appliquer Décodage Multicouches (Base64 -> Reverse)", fontWeight = FontWeight.Bold)
                    }

                    if (isDecoded) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(EmeraldGreen.copy(alpha = 0.2f))
                                .padding(12.dp)
                        ) {
                            Text(
                                text = "CLÉ EXTRAITE : ${lab.expectedFlag}",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = EmeraldGreen,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }
                }
            }
        }
    }
}
