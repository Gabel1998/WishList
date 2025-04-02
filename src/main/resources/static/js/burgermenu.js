
<!-- BURGER-MENU JAVASCRIPT -->

document.addEventListener("DOMContentLoaded", function () {
    const burger = document.querySelector('.burger');
    const nav = document.querySelector('.main-nav');
    const menu = document.querySelector('.nav-menu');
    const loginBox = document.querySelector('.login-opret-bruger');
    const userBox = document.querySelector('.user-info');

    if (burger) {
        burger.addEventListener('click', () => {
            nav.classList.toggle('active');
            menu.classList.toggle('active');
            if (loginBox) loginBox.classList.toggle('active');
            if (userBox) userBox.classList.toggle('active');
        });
    }

    document.querySelectorAll('.nav-menu a, .login-opret-bruger a, .user-info a').forEach(link => {
        link.addEventListener('click', () => {
            nav.classList.remove('active');
            menu.classList.remove('active');
            if (loginBox) loginBox.classList.remove('active');
            if (userBox) userBox.classList.remove('active');
        });
    });
});
