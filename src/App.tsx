import React, { useState, useEffect } from 'react';
import { 
  Compass, 
  Layers, 
  Briefcase, 
  Send, 
  Check, 
  Trash2, 
  Sparkles, 
  Info, 
  Star, 
  Phone, 
  Mail, 
  Globe, 
  MapPin, 
  ArrowRight, 
  X, 
  Moon, 
  Sun,
  Plus,
  Minus,
  CheckCircle2,
  Calendar,
  MessageSquare
} from 'lucide-react';

// Service Item Type
interface ServiceItem {
  id: string;
  title: string;
  icon: string;
  summary: string;
  description: string;
  bullets: string[];
}

// All 12 services from the original content
const ALL_SERVICES: ServiceItem[] = [
  {
    id: "logo-design",
    title: "Logo Design",
    icon: "🎨",
    summary: "Create a unique and memorable logo that reflects your brand's personality and values.",
    description: "A premium professional logo is the foundation of your corporate visual identity. Our logos are designed to represent your message across print, web, signage, and packaging.",
    bullets: ["3 Unique Concepts", "Full Vector Deliverables (AI, EPS, SVG)", "Brand Color Palette Definition", "Logo Usage Guidelines"]
  },
  {
    id: "brand-identity",
    title: "Brand Identity Design",
    icon: "🌟",
    summary: "Build a strong and consistent brand image with custom colors, typography, and guidelines.",
    description: "Build a lasting public image. We craft entire brand universes including customized typography rules, color formulas, voice alignment, and complete brand identity style books.",
    bullets: ["Complete Brand Book PDF", "Primary/Secondary Color Specs", "Custom Pattern Design", "Stationery Graphic Set"]
  },
  {
    id: "brochure-design",
    title: "Brochure Design",
    icon: "📄",
    summary: "Professional brochures designed to showcase your products, services, and company information effectively.",
    description: "Communicate clearly and persuasively with high-end corporate brochures. We handle layout structures, graphics, and font flow optimized for high-impact viewing.",
    bullets: ["Bi-Fold or Tri-Fold Options", "Print-Ready PDF format", "High-Resolution Custom Graphics", "Content Structure and Formatting"]
  },
  {
    id: "packaging-design",
    title: "Packaging Design",
    icon: "📦",
    summary: "Eye-catching packaging that enhances product appeal and attracts customers.",
    description: "Product packaging is a silent salesman. We construct creative box shapes, custom labeling, and outstanding print finishes that stand out on retail shelves.",
    bullets: ["3D Mockup Visualizations", "Die-Cut Line Formats", "Ingredient & Label Layouts", "Supplier Print Consultation"]
  },
  {
    id: "web-design",
    title: "Web Design",
    icon: "💻",
    summary: "Modern, responsive, and user-friendly website designs that provide excellent user experiences.",
    description: "Your website is the global lobby of your brand. We design fully responsive, high-converting digital storefronts focused on modern grid symmetry and smooth navigation.",
    bullets: ["Figma Prototype Ready Files", "Responsive Native Grid layouts", "Custom Dynamic UI Components", "SEO Friendly Visual Structure"]
  },
  {
    id: "digital-marketing",
    title: "Digital Marketing",
    icon: "📈",
    summary: "Grow your online presence through strategic marketing campaigns and targeted promotions.",
    description: "Reach your ideal consumer pool. We develop hyper-focused demographic research, custom paid media formats, and structural planning that accelerates lead capture.",
    bullets: ["Target Audience Profiles", "Campaign Budget Optimization", "Conversion Event Funnel Mapping", "Weekly Analytics Reports"]
  },
  {
    id: "social-media",
    title: "Social Media Graphics",
    icon: "📱",
    summary: "Engaging social media posts, stories, banners, and advertisements designed to increase audience engagement.",
    description: "Stop scroll-dead with gorgeous, tailored graphics optimized for Instagram, Facebook, LinkedIn, and TikTok. Consistent post graphics are essential for digital retention.",
    bullets: ["15 Bespoke Post Templates", "Animated Story Mockups", "Header/Banner Layouts for all socials", "Design Asset Repository Access"]
  },
  {
    id: "print-design",
    title: "Print Design",
    icon: "🖨️",
    summary: "Premium print materials including business cards, flyers, posters, catalogs, and more.",
    description: "Physical graphic touchpoints create tangible memory nodes. We deliver exquisite business cards, elegant store signage, and catalog grids with meticulous bleed settings.",
    bullets: ["Business Cards Layouts", "Flyers & Poster Design", "Complete Catalog Page Structure", "Color Proof Syncing"]
  },
  {
    id: "advertising-design",
    title: "Advertising Design",
    icon: "📢",
    summary: "Creative ad campaigns that help your business attract attention and generate leads.",
    description: "Commercial impact relies on smart, memorable visuals. We craft billboards, magazine advertisements, and interactive print campaigns that maximize ROI.",
    bullets: ["High Impact Billboards", "Magazine Grid Spreads", "Direct Mailer Visual Assets", "Promotional Merchandise Prints"]
  },
  {
    id: "infographic-design",
    title: "Infographic Design",
    icon: "📊",
    summary: "Turn complex information into visually appealing and easy-to-understand graphics.",
    description: "Data is powerful, but only if understood. We convert heavy statistics, complex processes, and corporate milestones into beautifully illustrated pathways.",
    bullets: ["Custom Metaphor Drawings", "Data Flow Visual Layout", "Web-Responsive Scalable Vectors", "Corporate Slide Ready Graphics"]
  },
  {
    id: "banner-ad",
    title: "Banner Ad Design",
    icon: "🎯",
    summary: "High-converting digital banners for websites, social media, and online advertising campaigns.",
    description: "Capture web traffic with stunning display ads. We focus on clear value propositions and strong, clickable Calls to Action optimized for Google Display Network.",
    bullets: ["7 Standard Banner Sizes", "GIF Animated/Static Layouts", "A/B Layout Testing Drafts", "High Click-Through-Rate Design"]
  },
  {
    id: "three-d",
    title: "3D Design",
    icon: "🧊",
    summary: "Realistic 3D product visualization, mockups, and creative concepts that bring ideas to life.",
    description: "Explore creative dimension. We render high-definition 3D product visualizations, virtual trade show materials, and advanced prototype previews for investors.",
    bullets: ["Unmatched 3D Photorealism", "Multiple Camera Angle Renders", "Realistic Texturing & Lightning", "Exportable OBJ/MP4 Assets"]
  }
];

// Consultation Database Entity
interface Consultation {
  id: number;
  fullName: string;
  companyName: string;
  email: string;
  phone: string;
  servicesSelected: string;
  notes: string;
  contactMethod: string;
  preferredTime: string;
  timestamp: number;
  status: string;
}

// Process Steps Data
const PROCESS_STEPS = [
  { no: "1", title: "Consultation", desc: "Understanding your business goals, target demographic, and creative specifications.", icon: "📞" },
  { no: "2", title: "Planning", "desc": "Drafting strategic style proposals, mood boards, and marketing metrics.", icon: "📝" },
  { no: "3", title: "Design & Development", desc: "Crafting beautiful concept models, color compositions, and graphic architectures.", icon: "💡" },
  { no: "4", title: "Review & Feedback", desc: "Collaborating synchronously to refine details and align creative files to guidelines.", icon: "🔄" },
  { no: "5", title: "Final Delivery", desc: "Providing high-resolution vector source formats ready to scale globally.", icon: "🚀" }
];

export default function App() {
  // Navigation & Onboarding state
  const [showOnboarding, setShowOnboarding] = useState(true);
  const [activeTab, setActiveTab] = useState('explore');
  const [darkMode, setDarkMode] = useState(false);

  // Core Functional Database Simulation state
  const [consultations, setConsultations] = useState<Consultation[]>([]);
  const [selectedServices, setSelectedServices] = useState<string[]>([]);
  const [expandedService, setExpandedService] = useState<ServiceItem | null>(null);

  // Form State
  const [fullName, setFullName] = useState('');
  const [companyName, setCompanyName] = useState('');
  const [email, setEmail] = useState('');
  const [phone, setPhone] = useState('');
  const [notes, setNotes] = useState('');
  const [contactMethod, setContactMethod] = useState('Email');
  const [preferredTime, setPreferredTime] = useState('Morning');
  
  // Notification states
  const [notification, setNotification] = useState<string | null>(null);

  // Load from LocalStorage
  useEffect(() => {
    const savedConsultations = localStorage.getItem('pallavi_consultations');
    if (savedConsultations) {
      setConsultations(JSON.parse(savedConsultations));
    }
    
    const savedOnboarding = localStorage.getItem('pallavi_onboarding_dismissed');
    if (savedOnboarding === 'true') {
      setShowOnboarding(false);
    }

    const savedTheme = localStorage.getItem('pallavi_dark_mode');
    if (savedTheme === 'true') {
      setDarkMode(true);
    }
  }, []);

  // Sync Dark mode
  useEffect(() => {
    if (darkMode) {
      document.documentElement.classList.add('dark');
    } else {
      document.documentElement.classList.remove('dark');
    }
    localStorage.setItem('pallavi_dark_mode', darkMode.toString());
  }, [darkMode]);

  const triggerToast = (msg: string) => {
    setNotification(msg);
    setTimeout(() => {
      setNotification(null);
    }, 3000);
  };

  const handleCompleteOnboarding = () => {
    setShowOnboarding(false);
    localStorage.setItem('pallavi_onboarding_dismissed', 'true');
  };

  const toggleServiceInDraft = (serviceTitle: string) => {
    if (selectedServices.includes(serviceTitle)) {
      setSelectedServices(selectedServices.filter(s => s !== serviceTitle));
      triggerToast(`Removed "${serviceTitle}" from brief draft`);
    } else {
      setSelectedServices([...selectedServices, serviceTitle]);
      triggerToast(`Added "${serviceTitle}" to brief draft`);
    }
  };

  const handleSubmitConsultation = (e: React.FormEvent) => {
    e.preventDefault();
    if (!fullName || !email || !phone) {
      triggerToast("Please fill out all required fields!");
      return;
    }

    const newConsultation: Consultation = {
      id: Date.now(),
      fullName,
      companyName: companyName || "Individual",
      email,
      phone,
      servicesSelected: selectedServices.length > 0 ? selectedServices.join(", ") : "General Inquiry",
      notes: notes || "Looking forward to growing our brand.",
      contactMethod,
      preferredTime,
      timestamp: Date.now(),
      status: "Pending Review"
    };

    const updated = [newConsultation, ...consultations];
    setConsultations(updated);
    localStorage.setItem('pallavi_consultations', JSON.stringify(updated));

    // Reset Form & Selected
    setFullName('');
    setCompanyName('');
    setEmail('');
    setPhone('');
    setNotes('');
    setSelectedServices([]);
    triggerToast("Inquiry Brief Submitted Successfully!");
    
    // Redirect to inquiries database list
    setActiveTab('consult');
  };

  const handleDeleteConsultation = (id: number) => {
    const updated = consultations.filter(c => c.id !== id);
    setConsultations(updated);
    localStorage.setItem('pallavi_consultations', JSON.stringify(updated));
    triggerToast("Inquiry brief cancelled successfully.");
  };

  if (showOnboarding) {
    return (
      <div className={`min-h-screen flex items-center justify-center p-4 transition-colors duration-300 ${darkMode ? 'bg-brand-darkBg text-brand-darkTextLight' : 'bg-gradient-to-tr from-[#FFF8F0] via-[#FFE7D6] to-[#FFFDFC]'}`}>
        <div className="max-w-xl w-full text-center glass-card p-8 rounded-3xl shadow-xl border border-brand-peach/50 flex flex-col items-center">
          
          {/* Logo container */}
          <div className="w-28 h-28 rounded-2xl overflow-hidden border-2 border-brand-orange/40 bg-white p-2 shadow-md mb-6 transform hover:scale-105 transition-transform">
            <img 
              src="/assets/img_app_icon_1782131939661.jpg" 
              alt="Pallavi Graphics Logo" 
              className="w-full h-full object-cover rounded-xl"
            />
          </div>

          <h1 className="text-3xl font-extrabold tracking-tight text-brand-darkText dark:text-brand-darkTextLight uppercase leading-snug">
            Pallavi Digital <br />
            <span className="text-brand-orange text-4xl">Graphics Agency</span>
          </h1>

          <p className="text-brand-orange font-medium italic mt-2 text-lg">
            "Where Creativity Meets Business Growth" 🚀
          </p>

          <div className="h-[2px] w-1/4 bg-brand-orange/30 my-6"></div>

          <p className="text-brand-mutedText dark:text-gray-300 text-sm md:text-base leading-relaxed max-w-md">
            Get ready to transform your business identity with professional logos, immersive social media artwork, realistic 3D mockups, and highly converting digital marketing campaigns. Let's design success together.
          </p>

          <button 
            onClick={handleCompleteOnboarding}
            className="mt-8 w-full md:w-auto px-8 py-4 bg-brand-orange hover:bg-brand-orangeHover text-white font-bold rounded-2xl shadow-lg hover:shadow-brand-orange/20 hover:scale-[1.02] active:scale-[0.98] transition-all flex items-center justify-center gap-3 text-base"
          >
            Begin Creative Journey <Sparkles className="w-5 h-5 animate-pulse" />
          </button>
        </div>
      </div>
    );
  }

  return (
    <div className={`min-h-screen flex flex-col transition-colors duration-300 ${darkMode ? 'bg-brand-darkBg text-gray-100' : 'bg-brand-beige text-brand-darkText'}`}>
      
      {/* Dynamic Toast Notification */}
      {notification && (
        <div className="fixed top-4 left-1/2 transform -translate-x-1/2 z-50 px-5 py-3 bg-brand-darkBg dark:bg-brand-darkTextLight text-brand-darkTextLight dark:text-brand-darkBg font-bold text-sm rounded-xl shadow-2xl flex items-center gap-2 border border-brand-orange/40 animate-bounce">
          <CheckCircle2 className="w-5 h-5 text-brand-orange" />
          {notification}
        </div>
      )}

      {/* Elegant Web Header Navigation */}
      <header className="sticky top-0 z-40 glass-card border-b border-gray-100 dark:border-brand-darkSurface/50 backdrop-blur-md">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 h-20 flex items-center justify-between">
          
          {/* Brand Logo & Name */}
          <div className="flex items-center gap-3 cursor-pointer" onClick={() => setActiveTab('explore')}>
            <div className="w-10 h-10 rounded-xl overflow-hidden border border-brand-orange/30 shadow-sm bg-white">
              <img 
                src="/assets/img_app_icon_1782131939661.jpg" 
                alt="Pallavi Graphics Mini Logo" 
                className="w-full h-full object-cover"
              />
            </div>
            <div>
              <span className="font-extrabold text-lg tracking-tight uppercase block leading-tight">
                Pallavi <span className="text-brand-orange">Graphics</span>
              </span>
              <span className="text-[10px] text-brand-mutedText dark:text-gray-400 block -mt-0.5">
                Digital Design Agency
              </span>
            </div>
          </div>

          {/* Desktop Nav Actions */}
          <nav className="hidden md:flex items-center gap-1">
            <button 
              onClick={() => setActiveTab('explore')}
              className={`px-4 py-2 rounded-xl text-sm font-bold tracking-wide transition-all flex items-center gap-1.5 ${activeTab === 'explore' ? 'bg-brand-orange text-white' : 'hover:bg-brand-peach/30 text-brand-mutedText dark:text-gray-300'}`}
            >
              <Compass className="w-4 h-4" /> Explore
            </button>
            <button 
              onClick={() => setActiveTab('services')}
              className={`px-4 py-2 rounded-xl text-sm font-bold tracking-wide transition-all flex items-center gap-1.5 ${activeTab === 'services' ? 'bg-brand-orange text-white' : 'hover:bg-brand-peach/30 text-brand-mutedText dark:text-gray-300'}`}
            >
              <Layers className="w-4 h-4" /> Services 
              {selectedServices.length > 0 && (
                <span className="ml-1 bg-white text-brand-orange dark:bg-brand-darkBg px-2 py-0.5 text-[11px] rounded-full font-black">
                  {selectedServices.length}
                </span>
              )}
            </button>
            <button 
              onClick={() => setActiveTab('portfolio')}
              className={`px-4 py-2 rounded-xl text-sm font-bold tracking-wide transition-all flex items-center gap-1.5 ${activeTab === 'portfolio' ? 'bg-brand-orange text-white' : 'hover:bg-brand-peach/30 text-brand-mutedText dark:text-gray-300'}`}
            >
              <Briefcase className="w-4 h-4" /> Portfolio
            </button>
            <button 
              onClick={() => setActiveTab('consult')}
              className={`px-4 py-2 rounded-xl text-sm font-bold tracking-wide transition-all flex items-center gap-1.5 ${activeTab === 'consult' ? 'bg-brand-orange text-white' : 'hover:bg-brand-peach/30 text-brand-mutedText dark:text-gray-300'}`}
            >
              <MessageSquare className="w-4 h-4" /> Brief Builder
              {consultations.length > 0 && (
                <span className="ml-1 bg-brand-orange/10 text-brand-orange px-2 py-0.5 text-[11px] rounded-full font-black">
                  {consultations.length}
                </span>
              )}
            </button>
          </nav>

          {/* Right Accessories (Theme, Call Button) */}
          <div className="flex items-center gap-3">
            <button 
              onClick={() => setDarkMode(!darkMode)}
              className="p-2.5 rounded-xl hover:bg-brand-peach/20 transition-all text-brand-orange"
              title="Toggle theme"
            >
              {darkMode ? <Sun className="w-5 h-5" /> : <Moon className="w-5 h-5" />}
            </button>

            <a 
              href="tel:9156482354"
              className="hidden lg:flex items-center gap-1.5 bg-brand-orange/10 hover:bg-brand-orange/20 text-brand-orange px-4 py-2 rounded-xl text-sm font-bold transition-all"
            >
              <Phone className="w-4 h-4" /> 9156482354
            </a>
          </div>
        </div>
      </header>

      {/* Main Content Area */}
      <main className="flex-grow max-w-7xl w-full mx-auto px-4 sm:px-6 lg:px-8 py-8">
        
        {/* Explore Screen Tab */}
        {activeTab === 'explore' && (
          <div className="space-y-16 animate-fadeIn">
            
            {/* Hero Interactive Canvas Card */}
            <div className="relative overflow-hidden rounded-3xl shadow-xl glass-card border border-brand-peach/30">
              <div className="grid grid-cols-1 lg:grid-cols-12">
                
                {/* Left side info */}
                <div className="lg:col-span-7 p-8 sm:p-12 flex flex-col justify-center relative z-10">
                  <span className="inline-flex items-center gap-1 bg-brand-orange/10 text-brand-orange font-bold text-xs px-3 py-1.5 rounded-full uppercase tracking-wider mb-6 w-fit">
                    <Sparkles className="w-3.5 h-3.5" /> Design Studio Premium
                  </span>

                  <h2 className="text-3xl sm:text-4xl lg:text-5xl font-black tracking-tight leading-tight mb-4">
                    Creative Design Solutions <br />
                    <span className="text-brand-orange">That Grow Your Brand</span>
                  </h2>

                  <p className="text-brand-mutedText dark:text-gray-300 text-sm sm:text-base leading-relaxed mb-8 max-w-xl">
                    Transform your business with stunning visuals, powerful branding, and innovative digital marketing solutions. We help businesses create a memorable identity and connect with customers through professional design services.
                  </p>

                  <div className="flex flex-col sm:flex-row gap-3">
                    <button 
                      onClick={() => setActiveTab('consult')}
                      className="px-6 py-3.5 bg-brand-orange hover:bg-brand-orangeHover text-white font-bold rounded-xl shadow-md transition-all text-sm flex items-center justify-center gap-2"
                    >
                      Get Free Consultation <ArrowRight className="w-4 h-4" />
                    </button>
                    <button 
                      onClick={() => setActiveTab('portfolio')}
                      className="px-6 py-3.5 border-2 border-brand-orange/30 hover:border-brand-orange text-brand-orange hover:bg-brand-orange/5 font-bold rounded-xl transition-all text-sm flex items-center justify-center"
                    >
                      View Portfolio
                    </button>
                    <button 
                      onClick={() => setActiveTab('services')}
                      className="px-6 py-3.5 text-brand-mutedText dark:text-gray-300 hover:text-brand-orange font-bold rounded-xl transition-all text-sm flex items-center justify-center"
                    >
                      Our Services
                    </button>
                  </div>
                </div>

                {/* Right side background visual image */}
                <div className="lg:col-span-5 h-64 lg:h-auto min-h-[300px] relative overflow-hidden">
                  <img 
                    src="/assets/img_hero_banner_1782131897390.jpg" 
                    alt="Creative workspace design wave illustration" 
                    className="w-full h-full object-cover"
                  />
                  <div className="absolute inset-0 bg-gradient-to-t lg:bg-gradient-to-r from-brand-beige dark:from-brand-darkBg via-transparent to-transparent lg:w-1/3"></div>
                </div>

              </div>
            </div>

            {/* About Us Card */}
            <div className="grid grid-cols-1 md:grid-cols-12 gap-8 items-center">
              <div className="md:col-span-6 space-y-6">
                <div className="inline-flex items-center gap-2 text-brand-orange font-bold text-sm">
                  <Info className="w-5 h-5" /> About Our Agency
                </div>

                <h3 className="text-2xl sm:text-3xl font-extrabold tracking-tight">
                  Welcome to <span className="text-brand-orange">Pallavi Digital Graphics</span> Agency
                </h3>

                <p className="text-brand-mutedText dark:text-gray-300 text-sm sm:text-base leading-relaxed">
                  At Pallavi Digital Graphics Agency, we specialize in creating visually appealing and result-driven designs that help businesses stand out in today's competitive market. Our team combines creativity, strategy, and innovation to deliver premium graphic design and digital marketing services for businesses of all sizes.
                </p>

                <p className="text-brand-mutedText dark:text-gray-300 text-sm sm:text-base leading-relaxed">
                  Whether you're launching a new brand or growing an existing one, we provide complete design solutions tailored to your business goals. Our workflow guarantees fast delivery and professional concepts tailored exactly to your criteria.
                </p>
              </div>

              <div className="md:col-span-6 grid grid-cols-2 gap-4">
                <div className="p-6 rounded-2xl glass-card text-center border border-brand-peach/40">
                  <div className="text-3xl mb-2">🚀</div>
                  <h4 className="font-bold text-sm text-brand-orange mb-1">Worldwide Service</h4>
                  <p className="text-xs text-brand-mutedText dark:text-gray-400">Serving enterprise & local agencies worldwide.</p>
                </div>
                <div className="p-6 rounded-2xl glass-card text-center border border-brand-peach/40">
                  <div className="text-3xl mb-2">✨</div>
                  <h4 className="font-bold text-sm text-brand-orange mb-1">Professional Crew</h4>
                  <p className="text-xs text-brand-mutedText dark:text-gray-400">Creative designers & marketing authorities.</p>
                </div>
                <div className="p-6 rounded-2xl glass-card text-center border border-brand-peach/40">
                  <div className="text-3xl mb-2">⭐</div>
                  <h4 className="font-bold text-sm text-brand-orange mb-1">Guaranteed Reviews</h4>
                  <p className="text-xs text-brand-mutedText dark:text-gray-400">Continuous iterations until full satisfaction.</p>
                </div>
                <div className="p-6 rounded-2xl glass-card text-center border border-brand-peach/40">
                  <div className="text-3xl mb-2">⚡</div>
                  <h4 className="font-bold text-sm text-brand-orange mb-1">Rapid Delivery</h4>
                  <p className="text-xs text-brand-mutedText dark:text-gray-400">Punctual execution without compromising quality.</p>
                </div>
              </div>
            </div>

            {/* Why Choose Us Carousel/Cards */}
            <div className="space-y-6">
              <div className="flex flex-col sm:flex-row items-start sm:items-center justify-between gap-4">
                <div>
                  <h3 className="text-2xl font-black">Why Businesses Choose Us</h3>
                  <p className="text-xs sm:text-sm text-brand-mutedText dark:text-gray-400">Excellence in visual execution is our baseline standard</p>
                </div>
                <button 
                  onClick={() => setActiveTab('services')}
                  className="text-brand-orange hover:text-brand-orangeHover text-xs sm:text-sm font-bold flex items-center gap-1.5 transition-all group"
                >
                  Explore Services <ArrowRight className="w-4 h-4 group-hover:translate-x-1 transition-transform" />
                </button>
              </div>

              <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-5 gap-4">
                {[
                  { title: "Creative Excellence", desc: "Unique and innovative designs tailored to your business.", symbol: "🎨" },
                  { title: "Professional Team", desc: "Experienced designers and marketing experts.", symbol: "👥" },
                  { title: "Affordable Pricing", desc: "High-quality services at competitive rates.", symbol: "💎" },
                  { title: "Fast Delivery", desc: "Timely project completion without compromising quality.", symbol: "⏱️" },
                  { title: "Customer Satisfaction", desc: "Dedicated support and revisions to ensure your satisfaction.", symbol: "🤝" }
                ].map((item, index) => (
                  <div key={index} className="p-5 rounded-2xl bg-brand-peach/20 dark:bg-brand-darkSurface border border-brand-peach/30 dark:border-brand-darkSurface/30 flex flex-col justify-between h-full">
                    <div>
                      <div className="text-2xl mb-3">{item.symbol}</div>
                      <h4 className="font-extrabold text-sm mb-1.5">{item.title}</h4>
                      <p className="text-xs text-brand-mutedText dark:text-gray-400 leading-relaxed">{item.desc}</p>
                    </div>
                  </div>
                ))}
              </div>
            </div>

            {/* Structured Workflow Step Process */}
            <div className="p-8 sm:p-10 rounded-3xl glass-card border border-brand-peach/40">
              <div className="text-center max-w-xl mx-auto mb-10">
                <span className="text-brand-orange text-xs font-bold uppercase tracking-widest block mb-2">Our Method</span>
                <h3 className="text-2xl font-black">Our Streamlined Creative Process</h3>
                <p className="text-xs text-brand-mutedText dark:text-gray-400 mt-1">From initial discussion to production assets, we assure clean iteration cycles.</p>
              </div>

              <div className="relative grid grid-cols-1 md:grid-cols-5 gap-8">
                {/* Horizontal line for desktop */}
                <div className="hidden md:block absolute top-[26px] left-[10%] right-[10%] h-[2px] bg-brand-orange/20 -z-10"></div>

                {PROCESS_STEPS.map((step, idx) => (
                  <div key={idx} className="flex flex-col items-center text-center space-y-3 relative z-10">
                    <div className="w-12 h-12 rounded-full bg-brand-orange text-white font-black text-sm flex items-center justify-center shadow-md">
                      {step.no}
                    </div>
                    <div className="flex items-center gap-1">
                      <span className="text-sm">{step.icon}</span>
                      <h4 className="font-extrabold text-sm">{step.title}</h4>
                    </div>
                    <p className="text-xs text-brand-mutedText dark:text-gray-400 leading-normal px-2">
                      {step.desc}
                    </p>
                  </div>
                ))}
              </div>
            </div>

            {/* High Impact Verified Testimonials */}
            <div className="space-y-6">
              <h3 className="text-2xl font-black">What Clients Say About Us</h3>
              <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                {[
                  { quote: "Outstanding Design Quality!", reviewer: "Sarah Mercer, Brand Director", opinion: "Pallavi Digital Graphics Agency transformed our brand identity and helped us attract more customers. Highly professional layout strategies." },
                  { quote: "Professional and Reliable", reviewer: "Devon Sterling, Co-Founder", opinion: "Their creativity and attention to detail exceeded our expectations. Extremely satisfied with our custom product packaging assets." }
                ].map((item, index) => (
                  <div key={index} className="p-6 rounded-2xl glass-card border border-brand-peach/30 flex flex-col justify-between">
                    <div>
                      <div className="flex gap-1 mb-3">
                        {[...Array(5)].map((_, i) => (
                          <Star key={i} className="w-4 h-4 fill-brand-orange text-brand-orange" />
                        ))}
                      </div>
                      <h4 className="font-bold text-base italic mb-2">"{item.quote}"</h4>
                      <p className="text-xs sm:text-sm text-brand-mutedText dark:text-gray-300 leading-relaxed">
                        {item.opinion}
                      </p>
                    </div>
                    <div className="mt-4 border-t border-brand-peach/20 pt-3 flex items-center justify-between text-[11px] text-brand-mutedText dark:text-gray-400">
                      <span className="font-bold">{item.reviewer}</span>
                      <span className="bg-brand-orange/10 text-brand-orange px-2 py-0.5 rounded-full">Verified Client</span>
                    </div>
                  </div>
                ))}
              </div>
            </div>

            {/* Quick footer style banner */}
            <div className="p-8 rounded-2xl bg-brand-orange/5 dark:bg-brand-darkSurface border border-brand-orange/10 flex flex-col md:flex-row items-center justify-between gap-6">
              <div className="text-center md:text-left">
                <h4 className="font-bold text-lg mb-1">Ready to Grow Your Business?</h4>
                <p className="text-xs text-brand-mutedText dark:text-gray-300">Schedule your consultation or explore our design briefs catalog today.</p>
              </div>
              <div className="flex gap-3">
                <button 
                  onClick={() => setActiveTab('services')}
                  className="px-5 py-2.5 bg-brand-orange hover:bg-brand-orangeHover text-white font-bold text-xs rounded-xl transition-all"
                >
                  Explore Services
                </button>
                <button 
                  onClick={() => setActiveTab('consult')}
                  className="px-5 py-2.5 border border-brand-orange/30 text-brand-orange font-bold text-xs rounded-xl hover:bg-brand-orange/5 transition-all"
                >
                  Create Brief Request
                </button>
              </div>
            </div>

          </div>
        )}

        {/* Services Tab Screen */}
        {activeTab === 'services' && (
          <div className="space-y-8 animate-fadeIn">
            
            {/* Header section */}
            <div className="flex flex-col md:flex-row md:items-center justify-between gap-4 bg-brand-cream dark:bg-brand-darkSurface/40 p-6 rounded-2xl border border-brand-peach/30">
              <div>
                <h2 className="text-2xl font-black">Professional Services Catalog</h2>
                <p className="text-xs text-brand-mutedText dark:text-gray-400 mt-0.5">
                  Choose from {ALL_SERVICES.length} tailored deliverables. Select items to add them directly to your consultation draft.
                </p>
              </div>
              
              {selectedServices.length > 0 ? (
                <div className="flex items-center gap-3">
                  <span className="text-xs font-bold text-brand-orange bg-brand-orange/10 px-3 py-1.5 rounded-lg">
                    {selectedServices.length} Selected Services
                  </span>
                  <button 
                    onClick={() => setActiveTab('consult')}
                    className="px-4 py-2 bg-brand-orange hover:bg-brand-orangeHover text-white font-bold text-xs rounded-xl shadow-md transition-all flex items-center gap-1"
                  >
                    Configure Brief <ArrowRight className="w-3.5 h-3.5" />
                  </button>
                </div>
              ) : (
                <span className="text-xs font-medium text-brand-mutedText dark:text-gray-400">
                  No services in brief draft. Tap cards to learn more or add drafts.
                </span>
              )}
            </div>

            {/* Grid of 12 Services */}
            <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
              {ALL_SERVICES.map((service) => {
                const isSelected = selectedServices.includes(service.title);
                return (
                  <div 
                    key={service.id}
                    className={`p-6 rounded-2xl transition-all flex flex-col justify-between h-full border ${
                      isSelected 
                        ? 'bg-brand-peach/50 dark:bg-brand-darkSurface border-brand-orange shadow-md' 
                        : 'bg-white dark:bg-brand-darkSurface/20 border-brand-peach/30 hover:border-brand-orange/40 shadow-sm'
                    }`}
                  >
                    <div>
                      {/* Top icon and selected tag */}
                      <div className="flex items-center justify-between mb-4">
                        <span className="text-3xl">{service.icon}</span>
                        {isSelected && (
                          <span className="inline-flex items-center gap-1 bg-brand-orange text-white font-black text-[10px] uppercase px-2 py-1 rounded-full">
                            <Check className="w-3 h-3" /> Draft Briefed
                          </span>
                        )}
                      </div>

                      <h3 className="font-extrabold text-base mb-1.5 text-brand-darkText dark:text-brand-darkTextLight">
                        {service.title}
                      </h3>

                      <p className="text-xs text-brand-mutedText dark:text-gray-300 leading-relaxed mb-4 line-clamp-3">
                        {service.summary}
                      </p>
                    </div>

                    <div className="mt-4 border-t border-brand-peach/20 pt-3 flex items-center justify-between">
                      <button 
                        onClick={() => setExpandedService(service)}
                        className="text-xs font-bold text-brand-orange hover:underline flex items-center gap-1"
                      >
                        Learn Deliverables <Info className="w-3.5 h-3.5" />
                      </button>

                      <button 
                        onClick={() => toggleServiceInDraft(service.title)}
                        className={`p-2 rounded-xl border transition-all ${
                          isSelected 
                            ? 'bg-brand-orange text-white border-brand-orange hover:bg-brand-orangeHover' 
                            : 'border-brand-orange/20 text-brand-orange hover:bg-brand-orange/10'
                        }`}
                        title={isSelected ? "Remove from Brief Draft" : "Add to Brief Draft"}
                      >
                        {isSelected ? <Minus className="w-4 h-4" /> : <Plus className="w-4 h-4" />}
                      </button>
                    </div>
                  </div>
                );
              })}
            </div>

          </div>
        )}

        {/* Portfolio Tab Screen */}
        {activeTab === 'portfolio' && (
          <div className="space-y-12 animate-fadeIn">
            
            {/* Header */}
            <div className="text-center max-w-xl mx-auto space-y-2">
              <span className="text-brand-orange text-xs font-black uppercase tracking-widest block">Featured Work</span>
              <h2 className="text-2xl sm:text-3xl font-black">Our Graphic Masterpieces</h2>
              <p className="text-xs text-brand-mutedText dark:text-gray-400">
                Explore samples of visual brand designs, interactive digital layout previews, and premium client campaigns.
              </p>
            </div>

            {/* Showcase grid of mockups generated in first turn */}
            <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
              
              {/* Item 1: Brand Identity Design Mockup */}
              <div className="rounded-2xl overflow-hidden shadow-md glass-card border border-brand-peach/30 flex flex-col">
                <div className="h-64 sm:h-80 relative overflow-hidden bg-gray-100">
                  <img 
                    src="/assets/img_portfolio_brand_1782131912767.jpg" 
                    alt="Corporate Brand Identity Showcase Design Mockup" 
                    className="w-full h-full object-cover transform hover:scale-105 transition-transform duration-500"
                  />
                  <div className="absolute top-3 left-3 bg-brand-orange text-white text-[10px] font-bold uppercase px-2.5 py-1 rounded-full">
                    Branding
                  </div>
                </div>
                <div className="p-6 flex-grow flex flex-col justify-between">
                  <div>
                    <h3 className="font-extrabold text-lg mb-2">Corporate Brand Identity & Color Guide</h3>
                    <p className="text-xs text-brand-mutedText dark:text-gray-300 leading-relaxed">
                      Complete visual rebranding suite constructed on a warm aesthetic palette. This design highlights customized business card mockups, consistent typography hierarchy scales, clean envelope alignment structures, and corporate rulesets.
                    </p>
                  </div>
                  <div className="mt-4 pt-4 border-t border-brand-peach/20 flex justify-between items-center text-[11px] text-brand-mutedText dark:text-gray-400">
                    <span>Delivered in vector scale & PDF format</span>
                    <button 
                      onClick={() => {
                        toggleServiceInDraft("Brand Identity Design");
                        setActiveTab('consult');
                      }}
                      className="text-brand-orange font-bold hover:underline"
                    >
                      Request Similar Design
                    </button>
                  </div>
                </div>
              </div>

              {/* Item 2: Modern Web Design Layout Preview */}
              <div className="rounded-2xl overflow-hidden shadow-md glass-card border border-brand-peach/30 flex flex-col">
                <div className="h-64 sm:h-80 relative overflow-hidden bg-gray-100">
                  <img 
                    src="/assets/img_portfolio_web_1782131926255.jpg" 
                    alt="Responsive Wellness Web Design Showcase" 
                    className="w-full h-full object-cover transform hover:scale-105 transition-transform duration-500"
                  />
                  <div className="absolute top-3 left-3 bg-brand-orange text-white text-[10px] font-bold uppercase px-2.5 py-1 rounded-full">
                    UX / UI Web
                  </div>
                </div>
                <div className="p-6 flex-grow flex flex-col justify-between">
                  <div>
                    <h3 className="font-extrabold text-lg mb-2">Interactive responsive Wellness Boutique</h3>
                    <p className="text-xs text-brand-mutedText dark:text-gray-300 leading-relaxed">
                      A modern website layout constructed to support beautiful e-commerce workflows and user touch targets. Focused on soft peach gradient waves and highly responsive mobile layouts styled in sleek grid arrays.
                    </p>
                  </div>
                  <div className="mt-4 pt-4 border-t border-brand-peach/20 flex justify-between items-center text-[11px] text-brand-mutedText dark:text-gray-400">
                    <span>Figma Interactive Mockup</span>
                    <button 
                      onClick={() => {
                        toggleServiceInDraft("Web Design");
                        setActiveTab('consult');
                      }}
                      className="text-brand-orange font-bold hover:underline"
                    >
                      Request Web Mockups
                    </button>
                  </div>
                </div>
              </div>

            </div>

            {/* Quick Consultation Prompt */}
            <div className="p-8 text-center rounded-2xl glass-card border border-brand-peach/40 max-w-2xl mx-auto space-y-4">
              <h3 className="text-xl font-bold">Impressed with our past works?</h3>
              <p className="text-xs text-brand-mutedText dark:text-gray-300 max-w-md mx-auto leading-normal">
                Each of our layout assets is created uniquely. Share details of your business, and our creative strategists will construct custom concepts.
              </p>
              <button 
                onClick={() => setActiveTab('consult')}
                className="px-6 py-3 bg-brand-orange hover:bg-brand-orangeHover text-white font-bold rounded-xl text-xs transition-all shadow-md inline-flex items-center gap-2"
              >
                Launch Inquiry Brief <ArrowRight className="w-4 h-4" />
              </button>
            </div>

          </div>
        )}

        {/* Consult Screen Tab */}
        {activeTab === 'consult' && (
          <div className="space-y-12 animate-fadeIn">
            
            <div className="grid grid-cols-1 lg:grid-cols-12 gap-8 items-start">
              
              {/* Left Column: Form to create/draft new consultation request */}
              <div className="lg:col-span-7 p-6 sm:p-8 rounded-3xl glass-card border border-brand-peach/40 space-y-6">
                <div>
                  <h2 className="text-xl font-black">Draft Project Consultation Brief</h2>
                  <p className="text-xs text-brand-mutedText dark:text-gray-400 mt-1">
                    Fill out your brand details. Selected services will be packaged in the database brief automatically.
                  </p>
                </div>

                <form onSubmit={handleSubmitConsultation} className="space-y-4">
                  
                  {/* Select Services Preview */}
                  <div className="p-4 rounded-xl bg-brand-orange/5 dark:bg-brand-darkSurface/60 border border-brand-orange/10">
                    <span className="text-[11px] font-bold text-brand-orange uppercase block tracking-wider mb-2">
                      Briefed Services Package
                    </span>
                    {selectedServices.length > 0 ? (
                      <div className="flex flex-wrap gap-1.5">
                        {selectedServices.map(s => (
                          <span key={s} className="inline-flex items-center gap-1 bg-white dark:bg-brand-darkBg text-brand-darkText dark:text-brand-darkTextLight text-[11px] font-bold px-2.5 py-1 rounded-lg border border-brand-orange/20 shadow-sm">
                            {s}
                            <X className="w-3 h-3 text-brand-orange cursor-pointer" onClick={() => toggleServiceInDraft(s)} />
                          </span>
                        ))}
                      </div>
                    ) : (
                      <div className="flex items-center justify-between">
                        <span className="text-xs text-brand-mutedText dark:text-gray-400">
                          No specific services selected. (Defaults to General Inquiry)
                        </span>
                        <button 
                          type="button"
                          onClick={() => setActiveTab('services')}
                          className="text-xs font-bold text-brand-orange hover:underline"
                        >
                          Add Services
                        </button>
                      </div>
                    )}
                  </div>

                  {/* Inputs */}
                  <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
                    <div className="space-y-1.5">
                      <label className="text-xs font-bold text-brand-darkText dark:text-brand-darkTextLight">
                        Full Name <span className="text-brand-orange">*</span>
                      </label>
                      <input 
                        type="text" 
                        required
                        value={fullName}
                        onChange={(e) => setFullName(e.target.value)}
                        placeholder="John Doe"
                        className="w-full px-4 py-3 bg-white dark:bg-brand-darkSurface/30 border border-brand-peach/40 focus:border-brand-orange rounded-xl text-sm outline-none transition-colors"
                      />
                    </div>

                    <div className="space-y-1.5">
                      <label className="text-xs font-bold text-brand-darkText dark:text-brand-darkTextLight">
                        Company Name
                      </label>
                      <input 
                        type="text" 
                        value={companyName}
                        onChange={(e) => setCompanyName(e.target.value)}
                        placeholder="e.g. Acme Corp (Optional)"
                        className="w-full px-4 py-3 bg-white dark:bg-brand-darkSurface/30 border border-brand-peach/40 focus:border-brand-orange rounded-xl text-sm outline-none transition-colors"
                      />
                    </div>
                  </div>

                  <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
                    <div className="space-y-1.5">
                      <label className="text-xs font-bold text-brand-darkText dark:text-brand-darkTextLight">
                        Email Address <span className="text-brand-orange">*</span>
                      </label>
                      <input 
                        type="email" 
                        required
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        placeholder="john@example.com"
                        className="w-full px-4 py-3 bg-white dark:bg-brand-darkSurface/30 border border-brand-peach/40 focus:border-brand-orange rounded-xl text-sm outline-none transition-colors"
                      />
                    </div>

                    <div className="space-y-1.5">
                      <label className="text-xs font-bold text-brand-darkText dark:text-brand-darkTextLight">
                        Phone Number <span className="text-brand-orange">*</span>
                      </label>
                      <input 
                        type="tel" 
                        required
                        value={phone}
                        onChange={(e) => setPhone(e.target.value)}
                        placeholder="e.g. 9156482354"
                        className="w-full px-4 py-3 bg-white dark:bg-brand-darkSurface/30 border border-brand-peach/40 focus:border-brand-orange rounded-xl text-sm outline-none transition-colors"
                      />
                    </div>
                  </div>

                  {/* Preferences selectors */}
                  <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
                    <div className="space-y-1.5">
                      <label className="text-xs font-bold text-brand-darkText dark:text-brand-darkTextLight">
                        Preferred Contact Method
                      </label>
                      <select 
                        value={contactMethod}
                        onChange={(e) => setContactMethod(e.target.value)}
                        className="w-full px-4 py-3 bg-white dark:bg-brand-darkSurface/30 border border-brand-peach/40 focus:border-brand-orange rounded-xl text-sm outline-none transition-colors"
                      >
                        <option value="Email">Email Message</option>
                        <option value="Call">Phone Call</option>
                        <option value="WhatsApp">WhatsApp Chat</option>
                      </select>
                    </div>

                    <div className="space-y-1.5">
                      <label className="text-xs font-bold text-brand-darkText dark:text-brand-darkTextLight">
                        Preferred Time Window
                      </label>
                      <select 
                        value={preferredTime}
                        onChange={(e) => setPreferredTime(e.target.value)}
                        className="w-full px-4 py-3 bg-white dark:bg-brand-darkSurface/30 border border-brand-peach/40 focus:border-brand-orange rounded-xl text-sm outline-none transition-colors"
                      >
                        <option value="Morning">Morning (9 AM - 12 PM)</option>
                        <option value="Afternoon">Afternoon (12 PM - 4 PM)</option>
                        <option value="Evening">Evening (4 PM - 8 PM)</option>
                      </select>
                    </div>
                  </div>

                  <div className="space-y-1.5">
                    <label className="text-xs font-bold text-brand-darkText dark:text-brand-darkTextLight">
                      Inquiry Notes & Brief Specifications
                    </label>
                    <textarea 
                      rows={3}
                      value={notes}
                      onChange={(e) => setNotes(e.target.value)}
                      placeholder="Please details your visual specifications, references or timeline goals..."
                      className="w-full px-4 py-3 bg-white dark:bg-brand-darkSurface/30 border border-brand-peach/40 focus:border-brand-orange rounded-xl text-sm outline-none transition-colors resize-none"
                    />
                  </div>

                  <button 
                    type="submit"
                    className="w-full py-4 bg-brand-orange hover:bg-brand-orangeHover text-white font-black rounded-xl shadow-lg transition-all text-sm flex items-center justify-center gap-2"
                  >
                    Submit Consultation Package <Send className="w-4 h-4" />
                  </button>

                </form>
              </div>

              {/* Right Column: Database list (Localstorage) mimicking Room database */}
              <div className="lg:col-span-5 space-y-6">
                <div>
                  <h2 className="text-xl font-black">Submitted Inquiries ({consultations.length})</h2>
                  <p className="text-xs text-brand-mutedText dark:text-gray-400 mt-1">
                    Your offline Room database inquiries stored locally. Clear or view briefs.
                  </p>
                </div>

                {consultations.length === 0 ? (
                  <div className="p-8 text-center rounded-2xl border-2 border-dashed border-brand-peach/40 bg-brand-orange/5 dark:bg-brand-darkSurface/10 space-y-3">
                    <div className="text-3xl">🗄️</div>
                    <h3 className="font-bold text-sm text-brand-orange">Database Empty</h3>
                    <p className="text-xs text-brand-mutedText dark:text-gray-400 max-w-xs mx-auto leading-normal">
                      No briefs submitted yet. Fill out the consultation request on the left to add a record.
                    </p>
                  </div>
                ) : (
                  <div className="space-y-4 max-h-[520px] overflow-y-auto pr-1 no-scrollbar">
                    {consultations.map((c) => (
                      <div 
                        key={c.id}
                        className="p-5 rounded-2xl glass-card border border-brand-peach/40 shadow-sm relative space-y-3 hover:shadow-md transition-shadow"
                      >
                        <div className="flex justify-between items-start">
                          <div>
                            <h4 className="font-extrabold text-sm text-brand-darkText dark:text-brand-darkTextLight leading-tight">
                              {c.fullName}
                            </h4>
                            <span className="text-[10px] text-brand-mutedText dark:text-gray-400 block mt-0.5">
                              {c.companyName}
                            </span>
                          </div>

                          <button 
                            onClick={() => handleDeleteConsultation(c.id)}
                            className="p-1.5 text-gray-400 hover:text-red-500 rounded-lg hover:bg-red-50 dark:hover:bg-red-950/20 transition-all"
                            title="Cancel inquiry"
                          >
                            <Trash2 className="w-4 h-4" />
                          </button>
                        </div>

                        {/* Badges of selected services */}
                        <div className="flex flex-wrap gap-1">
                          {c.servicesSelected.split(", ").map((serv, i) => (
                            <span key={i} className="text-[9px] font-bold bg-brand-orange/10 text-brand-orange px-2 py-0.5 rounded-md">
                              {serv}
                            </span>
                          ))}
                        </div>

                        <p className="text-xs text-brand-mutedText dark:text-gray-300 leading-normal bg-brand-beige/50 dark:bg-brand-darkBg/30 p-2.5 rounded-lg border border-brand-peach/10 italic">
                          "{c.notes}"
                        </p>

                        <div className="flex flex-wrap items-center justify-between gap-2 text-[10px] text-brand-mutedText dark:text-gray-400 pt-1.5 border-t border-brand-peach/10">
                          <span className="flex items-center gap-1">
                            <Calendar className="w-3.5 h-3.5 text-brand-orange" /> {c.contactMethod} ({c.preferredTime})
                          </span>
                          <span className="font-extrabold text-brand-orange text-[10px] uppercase">
                            {c.status}
                          </span>
                        </div>
                      </div>
                    ))}
                  </div>
                )}
              </div>

            </div>

          </div>
        )}

      </main>

      {/* Expanded Deliverables Modal dialog */}
      {expandedService && (
        <div className="fixed inset-0 z-50 bg-black/60 backdrop-blur-sm flex items-center justify-center p-4">
          <div className="bg-white dark:bg-brand-darkSurface max-w-lg w-full rounded-2xl shadow-2xl overflow-hidden border border-brand-peach/40 animate-scaleUp">
            
            {/* Header bar */}
            <div className="p-6 border-b border-brand-peach/20 flex items-center justify-between">
              <div className="flex items-center gap-2.5">
                <span className="text-3xl">{expandedService.icon}</span>
                <h3 className="font-extrabold text-lg text-brand-darkText dark:text-brand-darkTextLight">
                  {expandedService.title}
                </h3>
              </div>
              <button 
                onClick={() => setExpandedService(null)}
                className="p-1.5 text-gray-400 hover:text-brand-darkText dark:hover:text-white rounded-lg"
              >
                <X className="w-5 h-5" />
              </button>
            </div>

            {/* Content Body */}
            <div className="p-6 space-y-5">
              <div className="space-y-1.5">
                <span className="text-[10px] font-black text-brand-orange uppercase block tracking-widest">
                  Service Profile Description
                </span>
                <p className="text-xs sm:text-sm text-brand-mutedText dark:text-gray-300 leading-relaxed">
                  {expandedService.description}
                </p>
              </div>

              <div className="space-y-2">
                <span className="text-[10px] font-black text-brand-orange uppercase block tracking-widest">
                  Standard Key Deliverables Include
                </span>
                <div className="grid grid-cols-1 gap-2">
                  {expandedService.bullets.map((bullet, idx) => (
                    <div key={idx} className="flex items-center gap-2 text-xs text-brand-mutedText dark:text-gray-300">
                      <CheckCircle2 className="w-4 h-4 text-brand-orange flex-shrink-0" />
                      <span>{bullet}</span>
                    </div>
                  ))}
                </div>
              </div>
            </div>

            {/* Action Bar */}
            <div className="p-6 border-t border-brand-peach/20 flex gap-3 bg-brand-cream/30 dark:bg-brand-darkBg/10">
              <button 
                onClick={() => setExpandedService(null)}
                className="flex-1 py-2.5 border border-brand-peach/50 text-brand-mutedText dark:text-gray-300 font-bold rounded-xl text-xs hover:bg-brand-peach/10"
              >
                Dismiss Profile
              </button>
              <button 
                onClick={() => {
                  toggleServiceInDraft(expandedService.title);
                  setExpandedService(null);
                }}
                className={`flex-1.5 py-2.5 font-bold rounded-xl text-xs transition-all ${
                  selectedServices.includes(expandedService.title)
                    ? 'bg-red-500 hover:bg-red-600 text-white'
                    : 'bg-brand-orange hover:bg-brand-orangeHover text-white'
                }`}
              >
                {selectedServices.includes(expandedService.title) ? 'Remove Brief' : 'Add to Draft Brief'}
              </button>
            </div>

          </div>
        </div>
      )}

      {/* Global Agency Address Section Footnotes */}
      <footer className="mt-16 bg-brand-cream dark:bg-brand-darkSurface/60 border-t border-brand-peach/30 py-10 text-center">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 space-y-4">
          <div className="flex items-center justify-center gap-2">
            <span className="font-extrabold text-sm uppercase tracking-wider block">
              Pallavi Digital <span className="text-brand-orange">Graphics Agency</span>
            </span>
          </div>

          <p className="text-xs text-brand-mutedText dark:text-gray-400 max-w-md mx-auto leading-relaxed">
            Transforming corporate visions with elite logo suites, package labels, responsive web portals, and demographic marketing campaigns worldwide.
          </p>

          <div className="flex flex-col sm:flex-row items-center justify-center gap-4 sm:gap-6 text-xs text-brand-mutedText dark:text-gray-300">
            <span className="flex items-center gap-1"><Phone className="w-3.5 h-3.5 text-brand-orange" /> 9156482354</span>
            <span className="flex items-center gap-1"><Mail className="w-3.5 h-3.5 text-brand-orange" /> info@pallavidigitalgraphics.com</span>
            <span className="flex items-center gap-1"><Globe className="w-3.5 h-3.5 text-brand-orange" /> www.pallavidigitalgraphics.com</span>
            <span className="flex items-center gap-1"><MapPin className="w-3.5 h-3.5 text-brand-orange" /> Serving Worldwide</span>
          </div>

          <div className="h-[1px] w-2/3 bg-brand-peach/30 mx-auto my-4"></div>
          
          <p className="text-[10px] text-brand-mutedText dark:text-gray-500">
            &copy; {new Date().getFullYear()} Pallavi Digital Graphics Agency. All rights reserved.
          </p>
        </div>
      </footer>

    </div>
  );
}
