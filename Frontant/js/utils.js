// Back To Top
const backToTop = document.querySelector(".back-to-top");
window.addEventListener("scroll", () => {
    if (window.scrollY > 300) {
        backToTop.classList.add("show");
    } else {
        backToTop.classList.remove("show");
    }
});

if (backToTop) {
    backToTop.addEventListener("click", () => {
        window.scrollTo({
            top: 0,
            behavior: "smooth"
        });
    });
}

// Counter Animation
const counters = document.querySelectorAll(".counter");
const startCounter = () => {
    counters.forEach(counter => {
        const target = +counter.dataset.target;
        let count = 0;
        const speed = Math.ceil(target / 120);
        const update = () => {
            count += speed;
            if (count < target) {
                counter.innerText = count;
                requestAnimationFrame(update);
            } else {
                counter.innerText = target;
            }
        };
        update();
    });
};

// Run Counter Once
let counterStarted = false;
window.addEventListener("scroll", () => {
    const stats = document.querySelector(".statistics");
    if (stats && !counterStarted) {
        const top = stats.getBoundingClientRect().top;
        if (top < window.innerHeight - 100) {
            counterStarted = true;
            startCounter();
        }
    }
});

// Smooth Scroll
document.querySelectorAll('a[href^="#"]').forEach(link => {
    link.addEventListener("click", function (e) {
        e.preventDefault();
        const target = document.querySelector(this.getAttribute("href"));
        if (target) {
            target.scrollIntoView({
                behavior: "smooth"
            });
        }
    });
});