const jobs = [
  { title: "Frontend Developer", company: "Arc Labs", logo: "A", type: "Full-time", mode: "Hybrid", location: "Bengaluru", salary: "₹8–12 LPA", posted: "2 days ago", skills: ["React", "JavaScript"] },
  { title: "Product Design Intern", company: "Vanta Studio", logo: "V", type: "Internship", mode: "Remote", location: "Delhi NCR", salary: "₹20k–30k/mo", posted: "1 day ago", skills: ["Figma", "UI/UX"] },
  { title: "Backend Engineer", company: "Signal Works", logo: "S", type: "Full-time", mode: "On-site", location: "Pune", salary: "₹10–16 LPA", posted: "3 days ago", skills: ["Java", "Spring Boot"] },
  { title: "AI Research Intern", company: "Arc Labs", logo: "A", type: "Internship", mode: "Hybrid", location: "Bengaluru", salary: "₹30k–45k/mo", posted: "5 hours ago", skills: ["Python", "LLMs"] },
  { title: "Growth Associate", company: "Novo Systems", logo: "N", type: "Full-time", mode: "Hybrid", location: "Mumbai", salary: "₹7–10 LPA", posted: "4 days ago", skills: ["Growth", "Analytics"] },
  { title: "Full Stack Developer", company: "Novo Systems", logo: "N", type: "Full-time", mode: "Remote", location: "India", salary: "₹9–14 LPA", posted: "6 hours ago", skills: ["Java", "React"] },
  { title: "Motion Design Intern", company: "Vanta Studio", logo: "V", type: "Internship", mode: "Remote", location: "Delhi NCR", salary: "₹18k–25k/mo", posted: "5 days ago", skills: ["After Effects", "3D"] },
  { title: "Community Associate", company: "Signal Works", logo: "S", type: "Part-time", mode: "Hybrid", location: "Pune", salary: "₹25k–35k/mo", posted: "1 week ago", skills: ["Community", "Events"] }
];

const jobGrid = document.getElementById("jobGrid");
const searchInput = document.getElementById("searchInput");
const typeFilter = document.getElementById("typeFilter");
const modeFilter = document.getElementById("modeFilter");
const loadMore = document.getElementById("loadMore");

function renderJobs() {
  const q = searchInput.value.toLowerCase().trim();
  const type = typeFilter.value;
  const mode = modeFilter.value;
  const filtered = jobs.filter(j => {
    const hay = [j.title, j.company, j.location, ...j.skills].join(" ").toLowerCase();
    return (!q || hay.includes(q)) && (type === "all" || j.type === type) && (mode === "all" || j.mode === mode);
  });
  jobGrid.innerHTML = filtered.map(j => `
    <article class="job-card reveal">
      <div>
        <div class="job-top">
          <div class="company-mini">
            <div class="company-mini-logo">${j.logo}</div>
            <div><b>${j.company}</b><small>${j.location}</small></div>
          </div>
          <span class="chapter">${j.type}</span>
        </div>
        <h3>${j.title}</h3>
      </div>
      <div>
        <div class="job-meta">${j.skills.map(s => `<span>${s}</span>`).join("")}<span>${j.mode}</span></div>
        <div class="job-bottom"><small>${j.salary} · ${j.posted}</small><button class="apply-link" data-job="${j.title}">View role →</button></div>
      </div>
    </article>`).join("");
  if (!filtered.length) jobGrid.innerHTML = `<div class="job-card"><h3>No opportunities found.</h3><p style="color:#888;margin-top:10px">Try a different search or filter.</p></div>`;
  observeReveals();
}
[searchInput, typeFilter, modeFilter].forEach(el => el.addEventListener("input", renderJobs));
loadMore.addEventListener("click", () => { loadMore.textContent = "All current opportunities shown"; loadMore.disabled = true });

const modal = document.getElementById("modal");
const modalTitle = document.getElementById("modalTitle");
const modalCopy = document.getElementById("modalCopy");
const modalForm = document.getElementById("modalForm");
function openModal(kind) {
  if (kind === "login") { modalTitle.textContent = "Welcome back"; modalCopy.textContent = "Sign in to manage applications and your profile." }
  else if (kind === "company") { modalTitle.textContent = "Create a company profile"; modalCopy.textContent = "Publish openings and meet candidates who fit your team." }
  else { modalTitle.textContent = "Create your profile"; modalCopy.textContent = "Start with the basics. You can complete your profile later." }
  modal.classList.add("open");
}
document.querySelectorAll("[data-open]").forEach(b => b.addEventListener("click", () => openModal(b.dataset.open)));
document.getElementById("modalClose").addEventListener("click", () => modal.classList.remove("open"));
modal.addEventListener("click", e => { if (e.target === modal) modal.classList.remove("open") });
modalForm.addEventListener("submit", e => { e.preventDefault(); modalTitle.textContent = "You're on the path."; modalCopy.textContent = "The Java backend will connect this form to real authentication and profiles."; modalForm.innerHTML = '<button type="button" class="primary-btn" onclick="document.getElementById(\'modal\').classList.remove(\'open\')">Enter TRIDENT <span>→</span></button>' });
document.addEventListener("click", e => { const b = e.target.closest(".apply-link"); if (b) { openModal("register"); modalTitle.textContent = b.dataset.job; modalCopy.textContent = "Create your profile to apply. Application handling will be connected to the Java backend later." } });

const cursor = document.querySelector(".cursor-glow");
window.addEventListener("pointermove", e => { cursor.style.left = e.clientX + "px"; cursor.style.top = e.clientY + "px" });

function observeReveals() {
  const io = new IntersectionObserver(entries => entries.forEach(x => { if (x.isIntersecting) x.target.classList.add("visible") }), { threshold: .08 });
  document.querySelectorAll(".reveal:not(.visible)").forEach(x => io.observe(x));
}
observeReveals();

const canvas = document.getElementById("world");
const ctx = canvas.getContext("2d");
let particles = [];
let mouseX = 0, mouseY = 0;

function resizeCanvas() {
  canvas.width = innerWidth * devicePixelRatio;
  canvas.height = innerHeight * devicePixelRatio;
  canvas.style.width = innerWidth + "px";
  canvas.style.height = innerHeight + "px";
  ctx.setTransform(devicePixelRatio, 0, 0, devicePixelRatio, 0, 0);
}
function createParticles() {
  particles = Array.from({ length: 140 }, () => ({
    x: Math.random() * innerWidth,
    y: Math.random() * innerHeight,
    z: Math.random() * 1 + .2,
    r: Math.random() * 1.7 + .3,
    speed: Math.random() * .18 + .03,
    drift: (Math.random() - .5) * .15
  }));
}
function drawWorld(t) {
  ctx.clearRect(0, 0, innerWidth, innerHeight);
  const cx = innerWidth * .57 + mouseX * 35;
  const cy = innerHeight * .48 + mouseY * 20;
  const gradient = ctx.createRadialGradient(cx, cy, 20, cx, cy, Math.min(innerWidth, innerHeight) * .55);
  gradient.addColorStop(0, "rgba(120,42,32,.16)");
  gradient.addColorStop(.35, "rgba(70,30,25,.07)");
  gradient.addColorStop(1, "rgba(0,0,0,0)");
  ctx.fillStyle = gradient;
  ctx.fillRect(0, 0, innerWidth, innerHeight);

  particles.forEach(p => {
    p.y -= p.speed;
    p.x += p.drift;
    if (p.y < -10) { p.y = innerHeight + 10; p.x = Math.random() * innerWidth }
    if (p.x < -10) p.x = innerWidth + 10;
    if (p.x > innerWidth + 10) p.x = -10;
    ctx.beginPath();
    ctx.arc(p.x, p.y, p.r * p.z, 0, Math.PI * 2);
    ctx.fillStyle = `rgba(190,72,55,${.12 + p.z * .25})`;
    ctx.fill();
  });

  const ringX = cx;
  const ringY = cy;
  const ring = Math.min(innerWidth, innerHeight) * .19;
  ctx.beginPath();
  ctx.arc(ringX, ringY, ring, 0, Math.PI * 2);
  ctx.strokeStyle = "rgba(139,56,45,.18)";
  ctx.lineWidth = 1;
  ctx.stroke();

  requestAnimationFrame(drawWorld);
}

window.addEventListener("pointermove", e => {
  mouseX = e.clientX / innerWidth - .5;
  mouseY = e.clientY / innerHeight - .5;
  document.body.style.setProperty("--bg-x", `${mouseX * 1.8}%`);
  document.body.style.setProperty("--bg-y", `${mouseY * 1.2}%`);
});

window.addEventListener("resize", () => {
  resizeCanvas();
  createParticles();
});
resizeCanvas();
createParticles();
requestAnimationFrame(drawWorld);

let progress = 0;
const progressTimer = setInterval(() => {
  progress = Math.min(progress + Math.floor(Math.random() * 13) + 5, 100);
  document.getElementById("loadProgress").textContent = progress + "%";
  document.querySelector(".loader-line span").style.width = progress + "%";
  if (progress === 100) { clearInterval(progressTimer); setTimeout(() => document.querySelector(".loader").classList.add("done"), 350) }
}, 120);

renderJobs();
