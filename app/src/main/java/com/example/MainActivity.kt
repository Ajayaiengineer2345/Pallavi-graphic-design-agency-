package com.example

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.data.Consultation
import com.example.ui.AgencyViewModel
import com.example.ui.theme.MyApplicationTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MainAppScreen()
            }
        }
    }
}

// Model for Services representing Agency Data
data class ServiceItem(
    val title: String,
    val iconString: String,
    val summary: String,
    val description: String,
    val bullets: List<String>
)

// List of all 12 services mentioned in the provided script
val ALL_SERVICES = listOf(
    ServiceItem(
        "Logo Design", "🎨",
        "Create a unique and memorable logo reflecting your brand's personality.",
        "A premium professional logo is the foundation of your corporate visual identity. Our logos are designed to represent your message across print, web, signage, and packaging.",
        listOf("3 Unique Concepts", "Full Vector Deliverables (AI, EPS, SVG)", "Brand Color Palette Definition", "Logo Usage Guidelines")
    ),
    ServiceItem(
        "Brand Identity Design", "🌟",
        "Build a strong and consistent brand image with tailored guidelines.",
        "Build a lasting public image. We craft entire brand universes including customized typography rules, color formulas, voice alignment, and complete brand identity style books.",
        listOf("Complete Brand Book PDF", "Primary/Secondary Color Specs", "Custom Pattern Design", "Stationery Graphic Set")
    ),
    ServiceItem(
        "Brochure Design", "📄",
        "Showcase your products, services, and company story beautifully.",
        "Communicate clearly and persuasively with high-end corporate brochures. We handle layout structures, graphics, and font flow optimized for high-impact viewing.",
        listOf("Bi-Fold or Tri-Fold Options", "Print-Ready PDF format", "High-Resolution Custom Graphics", "Content Structure and Formatting")
    ),
    ServiceItem(
        "Packaging Design", "📦",
        "Eye-catching packaging that enhances product appeal & attracts buyers.",
        "Product packaging is a silent salesman. We construct creative box shapes, custom labeling, and outstanding print finishes that stand out on retail shelves.",
        listOf("3D Mockup Visualizations", "Die-Cut Line Formats", "Ingredient & Label Layouts", "Supplier Print Consultation")
    ),
    ServiceItem(
        "Web Design", "💻",
        "Modern, responsive, user-friendly designs for excellent user experience.",
        "Your website is the global lobby of your brand. We design fully responsive, high-converting digital storefronts focused on modern grid symmetry and smooth navigation.",
        listOf("Figma Prototype Ready Files", "Responsive Native Grid layouts", "Custom Dynamic UI Components", "SEO Friendly Visual Structure")
    ),
    ServiceItem(
        "Digital Marketing", "📈",
        "Grow your online presence through strategic campaign management.",
        "Reach your ideal consumer pool. We develop hyper-focused demographic research, custom paid media formats, and structural planning that accelerates lead capture.",
        listOf("Target Audience Profiles", "Campaign Budget Optimization", "Conversion Event Funnel Mapping", "Weekly Analytics Reports")
    ),
    ServiceItem(
        "Social Media Graphics", "📱",
        "Engaging posts, stories, and campaigns designed to build audiences.",
        "Stop scroll-dead with gorgeous, tailored graphics optimized for Instagram, Facebook, LinkedIn, and TikTok. Consistent post graphics are essential for digital retention.",
        listOf("15 Bespoke Post Templates", "Animated Story Mockups", "Header/Banner Layouts for all socials", "Design Asset Repository Access")
    ),
    ServiceItem(
        "Print Design", "🖨️",
        "Premium print creations including cards, flyers, posters, and catalogs.",
        "Physical graphic touchpoints create tangible memory nodes. We deliver exquisite business cards, elegant store signage, and catalog grids with meticulous bleed settings.",
        listOf("Business Cards Layouts", "Flyers & Poster Design", "Complete Catalog Page Structure", "Color Proof Syncing")
    ),
    ServiceItem(
        "Advertising Design", "📢",
        "Creative advertising strategies that attract interest & convert leads.",
        "Commercial impact relies of smart, memorable visuals. We craft billboards, magazine advertisements, and interactive print campaigns that maximize ROI.",
        listOf("High Impact Billboards", "Magazine Grid Spreads", "Direct Mailer Visual Assets", "Promotional Merchandise Prints")
    ),
    ServiceItem(
        "Infographic Design", "📊",
        "Transform complex data metrics into simple, engaging visual maps.",
        "Data is powerful, but only if understood. We convert heavy statistics, complex processes, and corporate milestones into beautifully illustrated pathways.",
        listOf("Custom Metaphor Drawings", "Data Flow Visual Layout", "Web-Responsive Scalable Vectors", "Corporate Slide Ready Graphics")
    ),
    ServiceItem(
        "Banner Ad Design", "🎯",
        "High-converting visual banners for display advertising.",
        "Capture web traffic with stunning display ads. We focus on clear value propositions and strong, clickable Calls to Action optimized for Google Display Network.",
        listOf("7 Standard Banner Sizes", "GIF Animated/Static Layouts", "A/B Layout Testing Drafts", "High Click-Through-Rate Design")
    ),
    ServiceItem(
        "3D Design", "🧊",
        "Realistic 3D visualizations, products, and mockups.",
        "Explore creative dimension. We render high-definition 3D product visualizations, virtual trade show materials, and advanced prototype previews for investors.",
        listOf("Unmatched 3D Photorealism", "Multiple Camera Angle Renders", "Realistic Texturing & Lightning", "Exportable OBJ/MP4 Assets")
    )
)

// Process Data
data class ProcessStep(
    val stepNo: String,
    val title: String,
    val description: String,
    val icon: String
)

val PROCESS_STEPS = listOf(
    ProcessStep("1", "Consultation", "Understanding your brand mission, target audience, and precise graphic requirements.", "📞"),
    ProcessStep("2", "Planning", "Constructing a solid visual blueprint and marketing strategy to set up goals.", "📝"),
    ProcessStep("3", "Design & Development", "Combining color theory, typography rules, and strategic visuals to craft designs.", "💡"),
    ProcessStep("4", "Review & Feedback", "Analyzing designs together and refining finer elements based on client instructions.", "🔄"),
    ProcessStep("5", "Final Delivery", "Providing high-resolution print vectors, source files, and brand assets worldwide.", "🚀")
)

@Composable
fun MainAppScreen(viewModel: AgencyViewModel = viewModel()) {
    var currentTab by remember { mutableStateOf("explore") }
    var userHasApprovedOnboarding by remember { mutableStateOf(false) }

    // Onboarding Splash screen overlay
    if (!userHasApprovedOnboarding) {
        OnboardingSplash {
            userHasApprovedOnboarding = true
        }
        return
    }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp,
                modifier = Modifier.testTag("app_bottom_bar")
            ) {
                NavigationBarItem(
                    selected = currentTab == "explore",
                    onClick = { currentTab = "explore" },
                    label = { Text("Explore", fontSize = 11.sp) },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home tab") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    ),
                    modifier = Modifier.testTag("nav_explore")
                )
                NavigationBarItem(
                    selected = currentTab == "services",
                    onClick = { currentTab = "services" },
                    label = { Text("Services", fontSize = 11.sp) },
                    icon = {
                        BadgedBox(badge = {
                            if (viewModel.selectedServiceDrafts.isNotEmpty()) {
                                Badge { Text(viewModel.selectedServiceDrafts.size.toString()) }
                            }
                        }) {
                            Icon(Icons.Default.List, contentDescription = "Services tab")
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    ),
                    modifier = Modifier.testTag("nav_services")
                )
                NavigationBarItem(
                    selected = currentTab == "portfolio",
                    onClick = { currentTab = "portfolio" },
                    label = { Text("Portfolio", fontSize = 11.sp) },
                    icon = { Icon(Icons.Default.Star, contentDescription = "Portfolio tab") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    ),
                    modifier = Modifier.testTag("nav_portfolio")
                )
                NavigationBarItem(
                    selected = currentTab == "consult",
                    onClick = { currentTab = "consult" },
                    label = { Text("Consult", fontSize = 11.sp) },
                    icon = { Icon(Icons.Default.Email, contentDescription = "Consultation requests") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    ),
                    modifier = Modifier.testTag("nav_consult")
                )
            }
        },
        contentWindowInsets = WindowInsets.safeDrawing
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.background,
                            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                            MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.1f)
                        )
                    )
                )
        ) {
            AnimatedContent(
                targetState = currentTab,
                transitionSpec = {
                    fadeIn(animationSpec = tween(220)) togetherWith fadeOut(animationSpec = tween(220))
                },
                label = "TabTransition"
            ) { targetTab ->
                when (targetTab) {
                    "explore" -> ExploreScreen(
                        onNavigateToConsult = { currentTab = "consult" },
                        onNavigateToPortfolio = { currentTab = "portfolio" },
                        onNavigateToServices = { currentTab = "services" }
                    )
                    "services" -> ServicesScreen(viewModel)
                    "portfolio" -> PortfolioScreen(onNavigateToConsult = { currentTab = "consult" })
                    "consult" -> ConsultScreen(viewModel)
                }
            }
        }
    }
}

@Composable
fun OnboardingSplash(onDone: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFFFF8F0),
                        Color(0xFFFFE7D6),
                        Color(0xFFFFFDFC)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Animated Icon Container
            Box(
                modifier = Modifier
                    .size(110.dp)
                    .clip(RoundedCornerShape(32.dp))
                    .border(2.dp, Color(0xFFFF7A00).copy(alpha = 0.6f), RoundedCornerShape(32.dp))
                    .background(Color.White)
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_app_icon_1782131939661),
                    contentDescription = "Pallavi Graphics Logo",
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "PALLAVI DIGITAL GRAPHICS\nAGENCY",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 24.sp,
                letterSpacing = 1.2.sp,
                color = Color(0xFF2C1D14),
                textAlign = TextAlign.Center,
                lineHeight = 28.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "\"Where Creativity Meets Business Growth\"",
                fontWeight = FontWeight.Medium,
                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                fontSize = 15.sp,
                color = Color(0xFFFF7A00),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "Get ready to transform your brand identity with premier logos, dynamic social media artwork, realistic 3D designs, and high-converting marketing solutions. Let's build success together.",
                fontSize = 14.sp,
                lineHeight = 20.sp,
                color = Color(0xFF6C574F),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 12.dp)
            )

            Spacer(modifier = Modifier.height(44.dp))

            Button(
                onClick = onDone,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF7A00),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .shadow(8.dp, RoundedCornerShape(16.dp))
                    .testTag("get_started_onboarding_button")
            ) {
                Text(
                    text = "Begin Creative Journey 🚀",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun ExploreScreen(
    onNavigateToConsult: () -> Unit,
    onNavigateToPortfolio: () -> Unit,
    onNavigateToServices: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("explore_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Hero Section Header
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(24.dp)
                    )
            ) {
                Column {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_hero_banner_1782131897390),
                            contentDescription = "Abstract flowing waves",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Transparent,
                                            Color.Black.copy(alpha = 0.6f)
                                        )
                                    )
                                )
                        )
                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "Pallavi Digital Graphics Agency",
                                fontSize = 21.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.White
                            )
                            Text(
                                text = "Designing Brands. Building Success.",
                                fontSize = 13.sp,
                                color = Color(0xFFFFE7D6)
                            )
                        }
                    }

                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(
                            text = "Creative Design Solutions That Grow Your Brand",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Transform your business with stunning visuals, powerful branding, and innovative digital marketing solutions. We help businesses create a memorable identity and connect with customers through professional design services.",
                            fontSize = 13.sp,
                            lineHeight = 19.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = onNavigateToConsult,
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("cta_consultation"),
                                contentPadding = PaddingValues(horizontal = 8.dp)
                            ) {
                                Text("Get Consultation", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                            OutlinedButton(
                                onClick = onNavigateToPortfolio,
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("cta_portfolio"),
                                contentPadding = PaddingValues(horizontal = 8.dp)
                            ) {
                                Text("Our Portfolio", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }

        // About Us Card Style
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Info, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "About Us",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Welcome to Pallavi Digital Graphics Agency",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.9f)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "At Pallavi Digital Graphics Agency, we specialize in creating visually appealing and result-driven designs that help businesses stand out in today's competitive market. Our team combines creativity, strategy, and innovation to deliver premium graphic design and digital marketing services for businesses of all sizes.\n\nWhether you're launching a new brand or growing an existing one, we provide complete design solutions tailored to your business goals.",
                        fontSize = 13.sp,
                        lineHeight = 19.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
            }
        }

        // Why Choose Us Section
        item {
            Column {
                Text(
                    text = "Why Choose Us",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 6.dp)
                )

                val selectReasons = listOf(
                    "Creative Excellence" to "Unique and innovative designs tailored to your business.",
                    "Professional Team" to "Experienced designers and marketing experts.",
                    "Affordable Pricing" to "High-quality services at competitive rates.",
                    "Fast Delivery" to "Timely project completion without compromising quality.",
                    "Customer Satisfaction" to "Dedicated support and revisions to ensure your satisfaction."
                )

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(horizontal = 4.dp)
                ) {
                    items(selectReasons) { (title, subtitle) ->
                        Card(
                            modifier = Modifier
                                .width(220.dp)
                                .height(120.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.4f))
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(14.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        Icons.Default.Check,
                                        contentDescription = "Checked",
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = title,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        color = MaterialTheme.colorScheme.onTertiaryContainer
                                    )
                                }
                                Text(
                                    text = subtitle,
                                    fontSize = 11.sp,
                                    lineHeight = 15.sp,
                                    maxLines = 3,
                                    overflow = TextOverflow.Ellipsis,
                                    color = MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.8f)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Our Flow Process
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "Our Process",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(14.dp))

                    PROCESS_STEPS.forEachIndexed { idx, st ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.Top
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .background(MaterialTheme.colorScheme.primary),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = st.stepNo,
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp
                                    )
                                }
                                if (idx < PROCESS_STEPS.size - 1) {
                                    Box(
                                        modifier = Modifier
                                            .width(2.dp)
                                            .height(35.dp)
                                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.3f))
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(st.icon, fontSize = 14.sp)
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        st.title,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    st.description,
                                    fontSize = 12.sp,
                                    lineHeight = 16.sp,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.65f)
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                        }
                    }
                }
            }
        }

        // Testimonials
        item {
            Column {
                Text(
                    text = "Client Testimonials",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))

                val reviews = listOf(
                    "Outstanding Design Quality!" to "Pallavi Digital Graphics Agency transformed our brand identity and helped us attract more customers.",
                    "Professional and Reliable" to "Their creativity and attention to detail exceeded our expectations."
                )

                reviews.forEach { (title, opinion) ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                repeat(5) {
                                    Icon(
                                        Icons.Default.Star,
                                        contentDescription = "Star",
                                        tint = Color(0xFFFFB300),
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    "Verified Client",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "\"$title\"",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = opinion,
                                fontSize = 12.sp,
                                lineHeight = 16.sp,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                            )
                        }
                    }
                }
            }
        }

        // Footnote Info Section
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f))
                    .padding(18.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Pallavi Digital Graphics Agency",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "📞 9156482354    |    📧 info@pallavidigitalgraphics.com\n📍 Serving Creative Excellence Worldwide",
                        fontSize = 11.sp,
                        lineHeight = 16.sp,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.75f)
                    )
                }
            }
        }
    }
}

@Composable
fun ServicesScreen(viewModel: AgencyViewModel) {
    var expandedService by remember { mutableStateOf<ServiceItem?>(null) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag("services_screen")
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(vertical = 12.dp, horizontal = 16.dp)
        ) {
            Column {
                Text(
                    text = "Professional Services Catalog",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "${ALL_SERVICES.size} custom design deliverables to grow your digital brand",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(ALL_SERVICES) { service ->
                val isSelectedInDraft = viewModel.selectedServiceDrafts.contains(service.title)

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { expandedService = service },
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelectedInDraft) {
                            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f)
                        } else {
                            MaterialTheme.colorScheme.surface
                        }
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = if (isSelectedInDraft) 4.dp else 1.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(14.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = service.iconString,
                                    fontSize = 24.sp
                                )
                                if (isSelectedInDraft) {
                                    Icon(
                                        Icons.Default.CheckCircle,
                                        contentDescription = "Selected for consultation draft",
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = service.title,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = service.summary,
                                fontSize = 11.sp,
                                lineHeight = 14.sp,
                                maxLines = 3,
                                overflow = TextOverflow.Ellipsis,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.65f)
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "Learn More",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            IconButton(
                                onClick = {
                                    if (isSelectedInDraft) {
                                        viewModel.removeServiceFromDraft(service.title)
                                        Toast.makeText(context, "Removed from Brief", Toast.LENGTH_SHORT).show()
                                    } else {
                                        viewModel.addServiceToDraft(service.title)
                                        Toast.makeText(context, "Added to Brief draft", Toast.LENGTH_SHORT).show()
                                    }
                                },
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(
                                    if (isSelectedInDraft) Icons.Default.Delete else Icons.Default.Add,
                                    contentDescription = "Add/Remove",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    // Modal dialog to explain individual service deliverables
    expandedService?.let { service ->
        val inDraft = viewModel.selectedServiceDrafts.contains(service.title)
        Dialog(
            onDismissRequest = { expandedService = null },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .clip(RoundedCornerShape(24.dp))
                    .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(24.dp)),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(22.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(service.iconString, fontSize = 28.sp)
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                service.title,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                        IconButton(onClick = { expandedService = null }) {
                            Icon(Icons.Default.Close, contentDescription = "Close description modal")
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = "Service Profile",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        letterSpacing = 0.5.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = service.description,
                        fontSize = 13.sp,
                        lineHeight = 18.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Standard Key Deliverables:",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        letterSpacing = 0.5.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    service.bullets.forEach { bullet ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 3.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = bullet,
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedButton(
                            onClick = { expandedService = null },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Dismiss")
                        }

                        Button(
                            onClick = {
                                if (inDraft) {
                                    viewModel.removeServiceFromDraft(service.title)
                                } else {
                                    viewModel.addServiceToDraft(service.title)
                                }
                                expandedService = null
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (inDraft) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                            ),
                            modifier = Modifier.weight(1.5f)
                        ) {
                            Text(if (inDraft) "Remove Brief Design" else "Choose Service")
                        }
                    }
                }
            }
        }
    }
}

// Model representing works in Portfolio
data class PortfolioItem(
    val title: String,
    val category: String,
    val imageResId: Int,
    val clientName: String,
    val description: String,
    val deliverables: List<String>
)

@Composable
fun PortfolioScreen(onNavigateToConsult: () -> Unit) {
    var selectedItem by remember { mutableStateOf<PortfolioItem?>(null) }
    var selectedCategoryFilter by remember { mutableStateOf("All") }

    val items = listOf(
        PortfolioItem(
            "Acme Corp Brand Suite",
            "Branding",
            R.drawable.img_portfolio_brand_1782131912767,
            "Acme Corp Inc.",
            "Complete transformation of corporate brand guidelines featuring minimalist logos, custom palettes, corporate brochures, and digital style layouts.",
            listOf("Logo System Specs", "Typography Guide", "Packaging Mockups", "Print Stationeries")
        ),
        PortfolioItem(
            "Wellness Boutique Platform",
            "Web Design",
            R.drawable.img_portfolio_web_1782131926255,
            "Wellness Labs LLC",
            "Responsive, user friendly corporate interface designed using light custom pastel gradients and grid structures tailored for customer conversions.",
            listOf("Responsive UI Assets", "Mobile Screen Templates", "Web Copy Assembly", "Figma Blueprint Ready")
        )
    )

    val filters = listOf("All", "Branding", "Web Design")
    val filteredItems = if (selectedCategoryFilter == "All") items else items.filter { it.category == selectedCategoryFilter }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag("portfolio_screen")
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(vertical = 12.dp, horizontal = 16.dp)
        ) {
            Column {
                Text(
                    text = "Bespoke Portfolios",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Take a preview at our latest premium client works",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        // Project Filters
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            filters.forEach { filter ->
                val active = selectedCategoryFilter == filter
                ElevatedFilterChip(
                    selected = active,
                    onClick = { selectedCategoryFilter = filter },
                    label = { Text(filter, fontSize = 12.sp) },
                    colors = FilterChipDefaults.elevatedFilterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        selectedLabelColor = Color.White
                    ),
                    modifier = Modifier.testTag("portfolio_chip_$filter")
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(filteredItems) { proj ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { selectedItem = proj },
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(170.dp)
                        ) {
                            Image(
                                painter = painterResource(id = proj.imageResId),
                                contentDescription = proj.title,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                            Box(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(12.dp)
                                    .background(
                                        color = MaterialTheme.colorScheme.primary,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .padding(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = proj.category,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }

                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = proj.title,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Client: ${proj.clientName}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = proj.description,
                                fontSize = 12.sp,
                                lineHeight = 16.sp,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "Click to view full lightbox details →",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
    }

    // Full Portfolio Lightbox Overlay
    selectedItem?.let { proj ->
        Dialog(
            onDismissRequest = { selectedItem = null },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxSize(0.95f)
                    .clip(RoundedCornerShape(24.dp)),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(260.dp)
                        ) {
                            Image(
                                painter = painterResource(id = proj.imageResId),
                                contentDescription = proj.title,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                            IconButton(
                                onClick = { selectedItem = null },
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(12.dp)
                                    .background(Color.Black.copy(alpha = 0.5f), CircleShape)
                            ) {
                                Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White)
                            }
                        }
                    }

                    item {
                        Column(modifier = Modifier.padding(20.dp)) {
                            Box(
                                modifier = Modifier
                                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f), RoundedCornerShape(6.dp))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = proj.category,
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = proj.title,
                                fontSize = 21.sp,
                                fontWeight = FontWeight.Black,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Client Partnership: ${proj.clientName}",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.primary
                            )

                            HorizontalDivider(modifier = Modifier.padding(vertical = 14.dp))

                            Text(
                                text = "Case Study Overview",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = proj.description,
                                fontSize = 13.sp,
                                lineHeight = 18.sp,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                            )

                            Spacer(modifier = Modifier.height(18.dp))
                            Text(
                                text = "Services & Creative Deliverables",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            proj.deliverables.forEach { item ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        Icons.Default.CheckCircle,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = item,
                                        fontSize = 12.sp,
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(28.dp))

                            Button(
                                onClick = {
                                    selectedItem = null
                                    onNavigateToConsult()
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text("Inquire About Similar Design", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ConsultScreen(viewModel: AgencyViewModel) {
    val consultations by viewModel.allConsultations.collectAsState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    // Contact form states
    var fullName by remember { mutableStateOf("") }
    var companyName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var contactMethod by remember { mutableStateOf("Call") }
    var preferredTime by remember { mutableStateOf("Morning") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("consult_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Form Title Card
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "Ready to Grow Your Business? 🚀",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Submit a free consultation. Our creative advisors will review your visual requirements and configure details tailored for your target growth.",
                        fontSize = 12.sp,
                        lineHeight = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f)
                    )
                }
            }
        }

        // Active Booking list
        item {
            Column {
                Text(
                    text = "Your Booked Schedulers (${consultations.size})",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                )

                if (consultations.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.6f))
                            .border(
                                1.dp,
                                MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f),
                                RoundedCornerShape(16.dp)
                            )
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                Icons.Default.Email,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f),
                                modifier = Modifier.size(36.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                "No Active Consultation Tickets",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                "Enter your project details below to build your first free consultation ticket.",
                                fontSize = 11.sp,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                            )
                        }
                    }
                } else {
                    consultations.forEach { consult ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Text(
                                            text = consult.fullName,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 14.sp
                                        )
                                        Text(
                                            text = if (consult.companyName.isEmpty() || consult.companyName == "Individual") {
                                                "Individual Inquirer"
                                            } else {
                                                "Company: ${consult.companyName}"
                                            },
                                            fontSize = 11.sp,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                    Box(
                                        modifier = Modifier
                                            .background(
                                                color = MaterialTheme.colorScheme.primaryContainer,
                                                shape = RoundedCornerShape(6.dp)
                                            )
                                            .padding(horizontal = 8.dp, vertical = 2.dp)
                                    ) {
                                        Text(
                                            text = consult.status,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onPrimaryContainer
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = "Selected Services: ${consult.servicesSelected}",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                                )
                                if (consult.notes.isNotEmpty()) {
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "Brief Notes: ${consult.notes}",
                                        fontSize = 11.sp,
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                    )
                                }

                                Spacer(modifier = Modifier.height(10.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Contact: ${consult.contactMethod} (${consult.preferredTime})",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    TextButton(
                                        onClick = { viewModel.deleteConsultation(consult.id) },
                                        colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error),
                                        contentPadding = PaddingValues(0.dp),
                                        modifier = Modifier.height(18.dp)
                                    ) {
                                        Text("Cancel Booking", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Complete form input fields
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Text(
                        text = "Consultation Request Form",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    // Display if any service is draft-prefilled from Catalog Tab
                    if (viewModel.selectedServiceDrafts.isNotEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.08f))
                                .padding(10.dp)
                        ) {
                            Column {
                                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                                    Text(
                                        "Draft Services Chosen:",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    Text(
                                        "Clear",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.error,
                                        modifier = Modifier.clickable { viewModel.clearServiceDrafts() }
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = viewModel.selectedServiceDrafts.joinToString(", "),
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.82f)
                                )
                            }
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.04f))
                                .padding(10.dp)
                        ) {
                            Text(
                                text = "💡 Tip: Go to the 'Services' tab to check and select specific design deliverables directly into this form!",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                            )
                        }
                    }

                    OutlinedTextField(
                        value = fullName,
                        onValueChange = { fullName = it },
                        label = { Text("Full Name *") },
                        placeholder = { Text("Enter your name") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("fullname_input"),
                        shape = RoundedCornerShape(12.dp)
                    )

                    OutlinedTextField(
                        value = companyName,
                        onValueChange = { companyName = it },
                        label = { Text("Company / Individual Name") },
                        placeholder = { Text("e.g. Cafe Bistro") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("company_input"),
                        shape = RoundedCornerShape(12.dp)
                    )

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email Address *") },
                        placeholder = { Text("info@yourbusiness.com") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("email_input"),
                        shape = RoundedCornerShape(12.dp)
                    )

                    OutlinedTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        label = { Text("Phone Number *") },
                        placeholder = { Text("Enter 10-digit number") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("phone_input"),
                        shape = RoundedCornerShape(12.dp)
                    )

                    // Contact preference options
                    Column {
                        Text(
                            text = "How should we contact you?",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            listOf("Call", "Email", "WhatsApp").forEach { method ->
                                val selected = contactMethod == method
                                Button(
                                    onClick = { contactMethod = method },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                                        contentColor = if (selected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                                    ),
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.weight(1f),
                                    contentPadding = PaddingValues(0.dp)
                                ) {
                                    Text(method, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }

                    // Contact hours
                    Column {
                        Text(
                            text = "Preferred Consultation Hours:",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            listOf("Morning", "Afternoon", "Evening").forEach { time ->
                                val selected = preferredTime == time
                                FilterChip(
                                    selected = selected,
                                    onClick = { preferredTime = time },
                                    label = { Text(time, fontSize = 11.sp) },
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }

                    OutlinedTextField(
                        value = notes,
                        onValueChange = { notes = it },
                        label = { Text("Additional Notes / Project Details") },
                        placeholder = { Text("e.g. Looking for a modern 3D vector styling with orange branding palette...") },
                        maxLines = 4,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        shape = RoundedCornerShape(12.dp)
                    )

                    Button(
                        onClick = {
                            if (fullName.trim().isEmpty() || email.trim().isEmpty() || phone.trim().isEmpty()) {
                                Toast.makeText(context, "Please configure required (*) fields.", Toast.LENGTH_SHORT).show()
                            } else {
                                viewModel.submitConsultation(
                                    fullName = fullName,
                                    companyName = companyName,
                                    email = email,
                                    phone = phone,
                                    notes = notes,
                                    contactMethod = contactMethod,
                                    preferredTime = preferredTime
                                )
                                Toast.makeText(context, "Consultation Ticket Created Successfully!", Toast.LENGTH_LONG).show()
                                // Clear fields
                                fullName = ""
                                companyName = ""
                                email = ""
                                phone = ""
                                notes = ""
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("submit_consultation_form")
                    ) {
                        Text("Create Consultation Ticket", fontWeight = FontWeight.Black, fontSize = 14.sp)
                    }
                }
            }
        }
    }
}
