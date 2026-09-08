package com.example.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Dns
import androidx.compose.material.icons.filled.EnhancedEncryption
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.ui.graphics.vector.ImageVector

data class UserProfile(
    val name: String = "Alexandre V.",
    val callsign: String = "Sentinel-09",
    val title: String = "Sentinelle brayanTech",
    val level: Int = 5,
    val xp: Int = 3850,
    val nextLevelXp: Int = 5000,
    val streakDays: Int = 14,
    val lessonsCompleted: Int = 23,
    val labsSolved: Int = 18,
    val threatDefenseScore: Int = 94,
    val badges: List<String> = listOf("Defensive Shield", "SQLi Slayer", "Crypto Breaker", "Packet Master")
)

data class SecurityAlert(
    val id: String,
    val cve: String,
    val title: String,
    val severity: String, // "Critique", "Élevé", "Moyen"
    val cvssScore: Double,
    val description: String,
    val impactedSystems: String,
    val recommendation: String,
    val timeAgo: String
)

data class CourseModule(
    val id: String,
    val title: String,
    val subtitle: String,
    val category: String,
    val difficulty: String, // "Débutant", "Intermédiaire", "Avancé"
    val durationMinutes: Int,
    val lessonsCount: Int,
    val xpReward: Int,
    val progressPercent: Int,
    val icon: ImageVector,
    val description: String,
    val lessons: List<Lesson>
)

data class Lesson(
    val id: String,
    val title: String,
    val estimatedMinutes: Int,
    val contentSections: List<LessonSection>,
    val codeSnippet: String? = null,
    val codeLanguage: String? = null,
    val diagramType: String? = null, // "PIPELINE_CRYPTO", "SQL_INJECTION", "ATTACK_SURFACE", "SOC_TRIAGE"
    val keyTakeaways: List<String>,
    val quickQuiz: QuizQuestion? = null
)

data class LessonSection(
    val heading: String,
    val body: String
)

data class QuizQuestion(
    val question: String,
    val options: List<String>,
    val correctOptionIndex: Int,
    val explanation: String
)

data class CtfLab(
    val id: String,
    val title: String,
    val category: String,
    val difficulty: String,
    val targetHost: String,
    val points: Int,
    val scenario: String,
    val missionBrief: String,
    val initialHint: String,
    val expectedFlag: String,
    val labType: LabType,
    var isSolved: Boolean = false
)

enum class LabType {
    TERMINAL_CLI,
    PHISHING_DETECTOR,
    LOG_ANALYZER,
    CRYPTO_DECODER
}

data class GlossaryTerm(
    val term: String,
    val category: String,
    val definition: String,
    val example: String,
    val tags: List<String>
)

object CyberRepository {
    val sampleAlerts = listOf(
        SecurityAlert(
            id = "alert-1",
            cve = "CVE-2026-8912",
            title = "Vulnérabilité RCE Critique sur les Microservices Gateway",
            severity = "Critique",
            cvssScore = 9.8,
            description = "Désérialisation non sécurisée permettant une exécution de code arbitraire à distance via injection d'en-têtes HTTP malveillants.",
            impactedSystems = "API Gateways v3.2+, Reverse Proxies",
            recommendation = "Appliquer le patch d'urgence BT-SEC-2026.04 et activer le WAF avec règle anti-désérialisation.",
            timeAgo = "Il y a 35 min"
        ),
        SecurityAlert(
            id = "alert-2",
            cve = "CVE-2026-4421",
            title = "Campagne Mondiale de Phishing Ciblant les Clés FIDO2",
            severity = "Élevé",
            cvssScore = 8.1,
            description = "Technique de proxy inverse Man-in-the-Middle interceptant les flux d'authentification multifacteur en temps réel.",
            impactedSystems = "Portails Web SSO, Employés administratifs",
            recommendation = "Renforcer l'authentification avec liaisons d'origine vérifiées WebAuthn strictes.",
            timeAgo = "Il y a 3 heures"
        )
    )

    val courses = listOf(
        CourseModule(
            id = "course-1",
            title = "Hygiène Numérique & Protection des Données",
            subtitle = "Fondations de la résilience cyber personnelle et d'entreprise",
            category = "Défense Fondamentale",
            difficulty = "Débutant",
            durationMinutes = 45,
            lessonsCount = 4,
            xpReward = 350,
            progressPercent = 100,
            icon = Icons.Default.Shield,
            description = "Maîtrisez la gestion des identités, le principe du moindre privilège, les sauvegardes 3-2-1 et la sensibilisation contre l'ingénierie sociale.",
            lessons = listOf(
                Lesson(
                    id = "c1-l1",
                    title = "Principes Fondamentaux de la Sécurité (CIA Triad)",
                    estimatedMinutes = 10,
                    contentSections = listOf(
                        LessonSection(
                            "Le triptyque Confidentialité - Intégrité - Disponibilité",
                            "Toute stratégie de sécurité repose sur le modèle CIA. La Confidentialité garantit que seules les entités autorisées accèdent aux données. L'Intégrité assure que l'information n'a pas été altérée. La Disponibilité veille à ce que les systèmes restent accessibles."
                        ),
                        LessonSection(
                            "Le Principe du Moindre Privilège (PoLP)",
                            "Chaque utilisateur, service ou composant ne doit détenir que les autorisations strictement requises pour accomplir sa mission immédiate, réduisant la surface d'attaque en cas de compromission."
                        )
                    ),
                    diagramType = "ATTACK_SURFACE",
                    keyTakeaways = listOf(
                        "Modèle CIA : Confidentialité, Intégrité, Disponibilité",
                        "PoLP : Pas de droits administrateurs permanents",
                        "Sauvegardes 3-2-1 : 3 copies, 2 supports, 1 hors-site"
                    ),
                    quickQuiz = QuizQuestion(
                        question = "Quel pilier du modèle CIA est directement menacé par un ransomware qui chiffre les bases de données ?",
                        options = listOf(
                            "Uniquement la confidentialité",
                            "La disponibilité et l'intégrité",
                            "Le non-répudiation uniquement",
                            "Aucun des piliers"
                        ),
                        correctOptionIndex = 1,
                        explanation = "Un ransomware bloque l'accès aux systèmes (menace sur la Disponibilité) et modifie le contenu des fichiers sans consentement (menace sur l'Intégrité)."
                    )
                ),
                Lesson(
                    id = "c1-l2",
                    title = "Ingénierie Sociale & Détection Avancée du Phishing",
                    estimatedMinutes = 12,
                    contentSections = listOf(
                        LessonSection(
                            "Vecteurs d'ingénierie sociale modernes",
                            "Le spear-phishing, le whaling et le vishing exploitent les biais cognitifs humains (urgence, autorité, peur, opportunité). L'analyse technique des en-têtes SPF, DKIM et DMARC est essentielle."
                        )
                    ),
                    keyTakeaways = listOf(
                        "Toujours examiner l'adresse d'expédition réelle (Return-Path)",
                        "Vérifier les mécanismes d'authentification SPF, DKIM et DMARC",
                        "Ne jamais céder à l'urgence artificielle d'un email"
                    )
                )
            )
        ),
        CourseModule(
            id = "course-2",
            title = "Sécurité Réseau & Cryptographie Avancée",
            subtitle = "Chiffrement symétrique, asymétrique, PKI et protocoles TLS",
            category = "Cryptographie & Réseau",
            difficulty = "Intermédiaire",
            durationMinutes = 90,
            lessonsCount = 5,
            xpReward = 650,
            progressPercent = 60,
            icon = Icons.Default.EnhancedEncryption,
            description = "Explorez les mécanismes de chiffrement AES-256-GCM, RSA, courbes elliptiques (Ed25519), et la sécurisation des architectures réseau avec segmentation et zéro trust.",
            lessons = listOf(
                Lesson(
                    id = "c2-l1",
                    title = "Chiffrement Symétrique (AES) vs Asymétrique (RSA/ECC)",
                    estimatedMinutes = 15,
                    contentSections = listOf(
                        LessonSection(
                            "AES-256 : Vitesse et Robustesse Massive",
                            "AES (Advanced Encryption Standard) utilise la même clé secrète pour le chiffrement et le déchiffrement. En mode GCM (Galois/Counter Mode), il garantit à la fois la confidentialité et l'authenticité (AEAD)."
                        ),
                        LessonSection(
                            "Cryptographie Asymétrique & Échange de Clés",
                            "RSA et Diffie-Hellman (ECDH) permettent à deux interlocuteurs d'établir un secret partagé sur un canal public non sécurisé, posant les fondations de HTTPS/TLS."
                        )
                    ),
                    codeSnippet = """// Exemple d'initialisation AES-256-GCM sécurisée
val cipher = Cipher.getInstance("AES/GCM/NoPadding")
val iv = ByteArray(12).apply { SecureRandom().nextBytes(this) }
val spec = GCMParameterSpec(128, iv)
cipher.init(Cipher.ENCRYPT_MODE, secretKey, spec)
val ciphertext = cipher.doFinal(plaintext)""",
                    codeLanguage = "Kotlin / Java Cryptography",
                    diagramType = "PIPELINE_CRYPTO",
                    keyTakeaways = listOf(
                        "AES-GCM offre un chiffrement authentifié (AEAD)",
                        "L'IV (Vecteur d'Initialisation) ne doit JAMAIS être réutilisé avec la même clé",
                        "Les courbes elliptiques (X25519) surpassent RSA en efficacité"
                    ),
                    quickQuiz = QuizQuestion(
                        question = "Pourquoi est-il crucial de ne jamais réutiliser un IV avec la même clé en mode AES-GCM ?",
                        options = listOf(
                            "Cela ralentit le processeur",
                            "Cela permet de retrouver la clé d'authentification et compromettre le chiffrement",
                            "Cela rend le texte en clair 2 fois plus long",
                            "L'algorithme bascule automatiquement en DES"
                        ),
                        correctOptionIndex = 1,
                        explanation = "La réutilisation d'un nonce/IV en AES-GCM (Two-Time Pad) casse la sécurité mathématique de la clé GHASH, permettant de forger des messages et d'extraire des fragments de clair."
                    )
                )
            )
        ),
        CourseModule(
            id = "course-3",
            title = "Ethical Hacking & Tests d'Infiltration",
            subtitle = "Méthodologie offensive, scan de vulnérabilités et post-exploitation",
            category = "Sécurité Offensive",
            difficulty = "Avancé",
            durationMinutes = 120,
            lessonsCount = 6,
            xpReward = 900,
            progressPercent = 30,
            icon = Icons.Default.Code,
            description = "Apprenez les 5 phases du test d'intrusion : Reconnaissance passive/active, Cartographie de ports, Exploitation, Élévation de privilèges et Rédaction de rapport d'audit.",
            lessons = listOf(
                Lesson(
                    id = "c3-l1",
                    title = "Reconnaissance Réseau avec Nmap & Découverte de Services",
                    estimatedMinutes = 20,
                    contentSections = listOf(
                        LessonSection(
                            "Le scan SYN (Half-Open Scan)",
                            "La commande `nmap -sS` envoie un paquet TCP SYN sans terminer le handshake TCP 3-voies, réduisant les traces dans les logs applicatifs traditionnels."
                        )
                    ),
                    codeSnippet = """# Scan complet avec détection de versions et scripts NSE
nmap -sV -sC -p- -T4 192.168.1.100 -oN audit_scan.txt

# Scan UDP des services critiques (DNS, SNMP)
nmap -sU --top-ports 50 192.168.1.100""",
                    codeLanguage = "Bash / CLI Nmap",
                    diagramType = "ATTACK_SURFACE",
                    keyTakeaways = listOf(
                        "L'option -sV détecte les bannières de versions logicielles",
                        "L'option -sC exécute la bibliothèque de scripts de vulnérabilités NSE",
                        "Toujours posséder une autorisation écrite formelle (Rules of Engagement)"
                    ),
                    quickQuiz = QuizQuestion(
                        question = "Que signifie un port retourné à l'état 'Filtered' par Nmap ?",
                        options = listOf(
                            "Le service est ouvert et prêt à accepter des connexions",
                            "Un pare-feu bloque ou filtre les sondes sans renvoyer de réponse RST",
                            "Le serveur est éteint",
                            "Le port utilise un protocole inconnu"
                        ),
                        correctOptionIndex = 1,
                        explanation = "Un état Filtered indique que les paquets sont rejetés ou ignorés silencieusement par un firewall/WAF, empêchant Nmap de savoir si le port écoute."
                    )
                )
            )
        ),
        CourseModule(
            id = "course-4",
            title = "Sécurité Web & OWASP Top 10",
            subtitle = "Failles d'injection SQL, XSS, CSRF, SSRF et sécurisation des API",
            category = "Sécurité Applicative",
            difficulty = "Intermédiaire",
            durationMinutes = 75,
            lessonsCount = 5,
            xpReward = 550,
            progressPercent = 45,
            icon = Icons.Default.Dns,
            description = "Décortiquez les vulnérabilités du web moderne : Injection SQL paramétrée, Cross-Site Scripting (Stored & Reflected), CORS misconfiguration et sécurisation des JWT.",
            lessons = listOf(
                Lesson(
                    id = "c4-l1",
                    title = "Anatomie d'une Injection SQL & Contre-Mesures",
                    estimatedMinutes = 15,
                    contentSections = listOf(
                        LessonSection(
                            "Comment l'injection SQL altère la logique du SGBD",
                            "Lorsque des entrées utilisateurs non aseptisées sont directement concaténées dans une chaîne SQL, l'attaquant peut injecter des métacaractères (`' OR 1=1 --`) pour contourner l'authentification ou siphonner la base."
                        )
                    ),
                    codeSnippet = """-- VULNERABLE : Concaténation directe
SELECT * FROM users WHERE email = '' OR 1=1 --' AND password = 'xxx';

-- SECURISE : Requête Préparée avec Paramètres Liés (PreparedStatement)
val query = "SELECT * FROM users WHERE email = ? AND password_hash = ?"
val statement = connection.prepareStatement(query)
statement.setString(1, userEmail)
statement.setString(2, hashedInput)""",
                    codeLanguage = "SQL & Kotlin",
                    diagramType = "SQL_INJECTION",
                    keyTakeaways = listOf(
                        "Les requêtes préparées (Prepared Statements) séparent le code des données",
                        "Ne jamais faire confiance aux entrées de l'utilisateur (validation stricte)",
                        "Appliquer le moindre privilège sur le compte de la base de données"
                    ),
                    quickQuiz = QuizQuestion(
                        question = "Quelle est la méthode la plus efficace pour éliminer définitivement les injections SQL ?",
                        options = listOf(
                            "Remplacer les apostrophes par des espaces",
                            "Utiliser systématiquement des requêtes préparées paramétrées (PreparedStatements)",
                            "Chiffrer l'ensemble de la base de données",
                            "Bloquer les adresses IP provenant de l'étranger"
                        ),
                        correctOptionIndex = 1,
                        explanation = "Les requêtes préparées paramétrées forcent le moteur SQL à traiter l'entrée comme un littéral de données pur et jamais comme du code exécutable."
                    )
                )
            )
        ),
        CourseModule(
            id = "course-5",
            title = "Analyse Forensique, SOC & Gestion des Incidents",
            subtitle = "Surveillance SIEM, analyse de journaux, détection d'intrusions et réponse",
            category = "Sécurité Défensive",
            difficulty = "Avancé",
            durationMinutes = 110,
            lessonsCount = 5,
            xpReward = 850,
            progressPercent = 15,
            icon = Icons.Default.Security,
            description = "Développez les compétences des analystes SOC Niveau 1 & 2 : triage des alertes, analyse des journaux d'événements Windows/Linux, recherche d'indicateurs de compromission (IoC).",
            lessons = listOf(
                Lesson(
                    id = "c5-l1",
                    title = "Triage d'Incidents & Pyramide de la Douleur (David Bianco)",
                    estimatedMinutes = 18,
                    contentSections = listOf(
                        LessonSection(
                            "La Pyramide de la Douleur",
                            "Les attaquants peuvent changer facilement leurs adresses IP et leurs hashs de fichiers. En revanche, changer leurs TTP (Tactiques, Techniques & Procédures) inflige un coût maximal à l'adversaire."
                        )
                    ),
                    diagramType = "SOC_TRIAGE",
                    keyTakeaways = listOf(
                        "Les TTPs sont au sommet de la Pyramide de la Douleur",
                        "Corréler les logs réseau (Zeek), hôte (Sysmon) et pare-feu",
                        "Isoler la machine compromise avant toute extinction brutale (préserver la RAM)"
                    )
                )
            )
        )
    )

    val ctfLabs = listOf(
        CtfLab(
            id = "lab-phishing",
            title = "Phishing Email Analyzer",
            category = "Ingénierie Sociale",
            difficulty = "Débutant",
            targetHost = "mailgate.brayantech.internal",
            points = 150,
            scenario = "Un cadre de la direction financière signale un courriel suspect reçu ce matin prétextant une mise à jour urgente de facture avec lien externe.",
            missionBrief = "Inspectez les en-têtes de l'email, identifiez l'adresse d'expédition réelle, le domaine usurpé et décodez la balise cachée pour extraire le Flag brayanTech.",
            initialHint = "Regardez attentivement le champ Return-Path et l'en-tête X-Originating-IP.",
            expectedFlag = "brayanTech{Ph1sh1ng_H34d3r_D3t3ct3d}",
            labType = LabType.PHISHING_DETECTOR
        ),
        CtfLab(
            id = "lab-log",
            title = "Apache Web Log Investigator",
            category = "Forensique & SOC",
            difficulty = "Intermédiaire",
            targetHost = "srv-web01.corp.lan",
            points = 250,
            scenario = "Le serveur web principal a déclenché une alerte CPU à 100%. Les journaux HTTP montrent une rafale inhabituelle de requêtes sur l'endpoint `/api/auth`.",
            missionBrief = "Analysez les lignes de journal, isolez l'adresse IP malveillante qui a réussi une attaque par injection SQL et trouvez l'empreinte de la table compromise.",
            initialHint = "Filtrez les codes HTTP 200 contenant des caractères SQL tels que UNION SELECT.",
            expectedFlag = "brayanTech{SqL_Inj3ct1on_In_L0gs_Sp0tt3d}",
            labType = LabType.LOG_ANALYZER
        ),
        CtfLab(
            id = "lab-crypto",
            title = "Cyber Cipher & Cryptanalyse",
            category = "Cryptographie",
            difficulty = "Intermédiaire",
            targetHost = "vault.shadow.ops",
            points = 200,
            scenario = "Une communication interceptée sur le réseau d'un groupe APT contient une chaîne chiffrée à couches multiples.",
            missionBrief = "Décryptez la chaîne encodée en Base64 puis appliquez le décalage César (ROT13) pour révéler la clé maîtresse.",
            initialHint = "Base64 d'abord, puis inversez la rotation alphabétique.",
            expectedFlag = "brayanTech{CrYpt0_D3c0d3r_M4st3r}",
            labType = LabType.CRYPTO_DECODER
        ),
        CtfLab(
            id = "lab-cli",
            title = "Console Interactive Terminal CTF",
            category = "Offensif & Réseau",
            difficulty = "Avancé",
            targetHost = "10.10.14.88 (Target)",
            points = 350,
            scenario = "Vous avez accès à une console d'évaluation isolée brayanTech. Utilisez les commandes d'audit pour cartographier le serveur cible et dénicher le drapeau caché sur le port 8080.",
            missionBrief = "Tapez `help` pour découvrir les commandes disponibles. Scannez la cible avec `nmap -sV 10.10.14.88` puis inspectez le service avec `curl http://10.10.14.88:8080/flag`.",
            initialHint = "Utilisez nmap pour révéler les ports cachés puis effectuez une requête ciblée.",
            expectedFlag = "brayanTech{P0rt_8080_N3t_P3n3tr4t1on}",
            labType = LabType.TERMINAL_CLI
        )
    )

    val glossaryTerms = listOf(
        GlossaryTerm(
            term = "SOC (Security Operations Center)",
            category = "Défense & Surveillance",
            definition = "Équipe centralisée responsable de la surveillance continue, de la détection et de la réponse aux incidents de sécurité 24h/24 et 7j/7.",
            example = "Le SOC a identifié une tentative d'exfiltration de données à 03h14 grâce aux règles de corrélation du SIEM.",
            tags = listOf("SOC", "Surveillance", "Triage", "Bleu")
        ),
        GlossaryTerm(
            term = "SIEM (Security Information and Event Management)",
            category = "Défense & Outils",
            definition = "Plateforme logicielle agrégeant les journaux d'événements de l'ensemble du parc informatique pour détecter les anomalies et menaces.",
            example = "Splunk, Microsoft Sentinel et Elastic SIEM sont des solutions SIEM largement déployées.",
            tags = listOf("Logs", "Corrélation", "Détection")
        ),
        GlossaryTerm(
            term = "Zero-Day (0-Day)",
            category = "Menaces & Failles",
            definition = "Vulnérabilité logicielle inconnue du développeur ou pour laquelle aucun correctif officiel (patch) n'a encore été publié.",
            example = "L'attaquant a exploité un Zero-Day dans le parseur de certificats pour exécuter du code à distance.",
            tags = listOf("Exploit", "Vulnérabilité", "Urgence")
        ),
        GlossaryTerm(
            term = "Ransomware (Rançongiciel)",
            category = "Malwares",
            definition = "Logiciel malveillant qui chiffre les fichiers de la victime et exige le paiement d'une rançon contre la clé de déchiffrement.",
            example = "WannaCry et LockBit sont des exemples notoires d'attaques par ransomware à grande échelle.",
            tags = listOf("Chiffrement", "Extorsion", "Sauvegardes")
        ),
        GlossaryTerm(
            term = "Man-in-the-Middle (MITM)",
            category = "Attaques Réseau",
            definition = "Attaque par laquelle un pirate s'intercale secrètement entre deux entités communicantes pour intercepter ou altérer leurs échanges.",
            example = "L'empoisonnement ARP sur un Wi-Fi public permet à l'attaquant de mener une attaque MITM.",
            tags = listOf("Réseau", "ARP", "TLS", "Écoute")
        ),
        GlossaryTerm(
            term = "XSS (Cross-Site Scripting)",
            category = "Sécurité Web",
            definition = "Injection de scripts malveillants dans des pages web légitimes consultées par d'autres utilisateurs.",
            example = "Un script JavaScript inséré dans un commentaire de forum vole le cookie de session de l'administrateur.",
            tags = listOf("Web", "JavaScript", "OWASP")
        ),
        GlossaryTerm(
            term = "Honeypot (Pot de Miel)",
            category = "Défense Active",
            definition = "Système leurre intentionnellement vulnérable déployé pour attirer, observer et analyser les tactiques des cyberattaquants.",
            example = "Le faux serveur SSH a capturé plus de 10 000 tentatives de mot de passe par dictionnaire en 24h.",
            tags = listOf("Leurre", "Renseignement", "Cyber-espionnage")
        )
    )

    val certificationQuizQuestions = listOf(
        QuizQuestion(
            question = "Quel est le rôle principal d'une autorité de certification (CA) dans l'architecture PKI / TLS ?",
            options = listOf(
                "Bloquer les paquets réseau malveillants à l'entrée du pare-feu",
                "Signer cryptographiquement des certificats pour certifier l'identité d'un serveur ou client",
                "Chiffrer le disque dur du serveur avec une clé symétrique",
                "Générer des mots de passe aléatoires pour les administrateurs"
            ),
            correctOptionIndex = 1,
            explanation = "L'autorité de certification (CA) agit comme un tiers de confiance qui valide et signe numériquement le certificat d'un domaine afin que les navigateurs puissent vérifier son authenticité."
        ),
        QuizQuestion(
            question = "Dans le cadre de l'ingénierie sociale, qu'est-ce que le 'Whaling' ?",
            options = listOf(
                "Une attaque par déni de service distribuée ciblant les centres de données maritimes",
                "Une attaque de phishing hautement ciblée visant les cadres dirigeants ou hauts responsables",
                "L'utilisation d'ordinateurs quantiques pour casser le chiffrement AES",
                "L'analyse de gros volumes de trafic réseau non chiffré"
            ),
            correctOptionIndex = 1,
            explanation = "Le Whaling (chasse à la baleine) est une forme spécialisée de spear-phishing visant spécifiquement les dirigeants (PDG, directeurs financiers) pour détourner des fonds ou accéder à des données stratégiques."
        ),
        QuizQuestion(
            question = "Parmi ces protocoles, lequel fournit une couche de transport chiffrée remplaçant avantageusement Telnet ?",
            options = listOf(
                "FTP",
                "SSH (Secure Shell)",
                "HTTP 1.0",
                "SNMP v1"
            ),
            correctOptionIndex = 1,
            explanation = "SSH chiffre l'intégralité de la session (authentification et commandes), là où Telnet transmettait identifiants et données en texte clair sur le réseau."
        ),
        QuizQuestion(
            question = "Quelle technique permet de limiter l'impact d'une élévation de privilèges lors de la compromission d'un service web ?",
            options = listOf(
                "Exécuter le service sous le compte root pour éviter les erreurs de permission",
                "Isoler le service dans un conteneur dédié avec utilisateur non-root et capacités réduites (Sandboxing)",
                "Désactiver complètement les pare-feux pour faciliter le diagnostic",
                "Utiliser le même mot de passe pour tous les microservices"
            ),
            correctOptionIndex = 1,
            explanation = "Le confinement (sandboxing / conteneurisation) sous un utilisateur à privilèges minimaux empêche l'attaquant de s'échapper sur le système hôte même s'il compromet le processus web."
        ),
        QuizQuestion(
            question = "Quelle fonction de hachage est considérée comme cryptographiquement compromise et déconseillée pour stocker des mots de passe ?",
            options = listOf(
                "Argon2id",
                "bcrypt",
                "MD5",
                "PBKDF2"
            ),
            correctOptionIndex = 2,
            explanation = "MD5 présente de sévères collisions cryptographiques et se calcule trop rapidement avec les GPU modernes, facilitant les attaques par dictionnaires et tables arc-en-ciel."
        )
    )
}
