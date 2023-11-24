const bar = document.getElementById('bar');
const close = document.getElementById('close');
const nav = document.getElementById('navbar');

if (bar) {
    bar.addEventListener('click', (event) => {
        event.stopPropagation();
        nav.classList.add('active');
    })
}

if (close) {
    close.addEventListener('click', () => {
        nav.classList.remove('active');
    })
}

document.addEventListener('click', (event) => {
    const isClickedOutside = !nav.contains(event.target);

    if (isClickedOutside && nav.classList.contains('active')) {
        nav.classList.remove('active');
    }
});