const navbar = document.querySelector(".navbar");
const menuToggle = document.querySelector(".menu-toggle");
const navMenu = document.querySelector(".nav-menu");
const navLinks = document.querySelectorAll(".nav-menu a");

// Navbar Scroll Effect

window.addEventListener("scroll", () => {

    if (window.scrollY > 60) {

        navbar.classList.add("scrolled");

    } else {

        navbar.classList.remove("scrolled");

    }

});

// Mobile Menu Toggle

if (menuToggle) {

    menuToggle.addEventListener("click", () => {

        navMenu.classList.toggle("active");

        if (menuToggle.classList.contains("fa-bars")) {

            menuToggle.classList.remove("fa-bars");
            menuToggle.classList.add("fa-xmark");

        } else {

            menuToggle.classList.remove("fa-xmark");
            menuToggle.classList.add("fa-bars");

        }

    });

}

// Close Mobile Menu

navLinks.forEach(link => {

    link.addEventListener("click", () => {

        if (navMenu.classList.contains("active")) {

            navMenu.classList.remove("active");

            if (menuToggle) {

                menuToggle.classList.remove("fa-xmark");
                menuToggle.classList.add("fa-bars");

            }

        }

    });

});

// Active Navigation Link

const currentPage = window.location.pathname.split("/").pop();

navLinks.forEach(link => {

    const href = link.getAttribute("href");

    if (href === currentPage || (currentPage === "" && href === "index.html")) {

        link.classList.add("active");

    }

});