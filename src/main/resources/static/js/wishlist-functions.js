/**
* Kopierer et delingslink til udklipsholderen
 */
function copyShareLink(shareLink) {
    navigator.clipboard.writeText(shareLink).then(function() {
        alert("Link kopieret til udklipsholder!");
    }).catch(function() {
        // Fallback til browsere, der ikke understøtter clipboard API
        const textarea = document.createElement('textarea');
        textarea.value = shareLink;
        textarea.style.position = 'fixed';  // Ingen scrolling
        document.body.appendChild(textarea);
        textarea.focus();
        textarea.select();
        try {
            document.execCommand('copy');
            alert("Link kopieret til udklipsholder!");
        } catch (err) {
            console.error('Failed to copy: ', err);
        }
        document.body.removeChild(textarea);
    });
}

/**
 * sletter med POST
 */
function deleteWishlist(wishlistId) {
    if (confirm('Er du sikker på, at du vil slette denne ønskeseddel?')) {
        // Opretter et form element
        const form = document.createElement('form');
        form.method = 'POST';
        form.action = '/wishlist/' + wishlistId + '/delete';

        // Tilføjer til dokumentet og sender
        document.body.appendChild(form);
        form.submit();
    }
}

/**
 * Sletter med POST
 */
function deleteItem(itemId) {
    if (confirm('Er du sikker på, at du vil slette dette produkt?')) {
        // Opretter et form element
        const form = document.createElement('form');
        form.method = 'POST';
        form.action = '/wishlist/item/' + itemId + '/delete';

        // Tilføjer til dokumentet og sender
        document.body.appendChild(form);
        form.submit();
    }
}

/**
 * åbner popup til at tilføje et produkt
 */
function openAddItemPopup() {
    document.getElementById('add-item-popup').classList.add('show');
    document.getElementById('popup-overlay').classList.add('show');
}

/**
 * lukker popup til at tilføje et produkt
 */
function closeAddItemPopup() {
    document.getElementById('add-item-popup').classList.remove('show');
    document.getElementById('popup-overlay').classList.remove('show');
}

/**
 * Reserver funktion
 */
    function handleReservation(event, form) {
    event.preventDefault();

    // hent Id fra formularen
    const itemId = form.querySelector('input[name="itemId"]').value;
    const cardElement = document.getElementById('item-card-' + itemId);

    // smid reserver på med det samme
    cardElement.classList.add('reserved');

    // opret og tilføj overlay
    const overlay = document.createElement('div');
    overlay.className = 'reserved-overlay';

    const text = document.createElement('span');
    text.className = 'reserved-text';
    text.textContent = 'Reserveret';

    overlay.appendChild(text);
    cardElement.prepend(overlay);

    // fjern knapperne
    const itemButtons = cardElement.querySelector('.item-buttons');
    if (itemButtons) {
    itemButtons.remove();
}

    // send reservationen
    fetch(form.action, {
    method: 'POST',
    body: new FormData(form),
    headers: {
    'X-Requested-With': 'XMLHttpRequest'
}
})
    .then(response => {
    if (!response.ok) {
    throw new Error('Reservation failed');
}
    return response.text();
})
    .catch(error => {
    console.error('Error:', error);
    // Fjern overlay og reservering ved fejl
    cardElement.classList.remove('reserved');
    if (overlay) {
    overlay.remove();
}
});
}

    // confirm animaiton
    document.addEventListener('DOMContentLoaded', function() {
    const message = document.getElementById('reservation-message');
    if (message) {
    message.style.opacity = '0';
    message.style.transform = 'translateY(-20px)';
    message.style.transition = 'opacity 0.5s ease, transform 0.5s ease';

    setTimeout(function() {
    message.style.opacity = '1';
    message.style.transform = 'translateY(0)';
}, 100);

    setTimeout(function() {
    message.style.opacity = '0';
    message.style.transform = 'translateY(-20px)';
}, 5000);
}
});