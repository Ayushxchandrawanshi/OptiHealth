const loader = document.querySelector(".loader");

window.addEventListener("load", () => {

    if (loader) {

        loader.style.opacity = "0";

        loader.style.visibility = "hidden";

        setTimeout(() => {

            loader.style.display = "none";

        }, 500);

    }

});