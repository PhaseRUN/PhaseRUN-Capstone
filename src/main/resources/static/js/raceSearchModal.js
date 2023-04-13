// Get all the race cards
const raceCards = document.querySelectorAll('.card');

// Loop through each race card
raceCards.forEach(raceCard => {
    // Get the modal element for the current race card
    const modal = raceCard.querySelector('.myModal');

    // Get the close button for the current race card modal
    const closeButton = modal.querySelector('.close');

    // Add a click event listener to the race card
    raceCard.addEventListener('click', () => {
        // Show the modal when the race card is clicked
        modal.style.display = 'block';
    });

    // Add a click event listener to the close button
    closeButton.addEventListener('click', () => {
        // Hide the modal when the close button is clicked
        modal.style.display = 'none';
    });

    // Add a click event listener to the modal itself
    modal.addEventListener('click', event => {
        // Hide the modal when the user clicks outside of it
        if (event.target === modal) {
            modal.style.display = 'none';
        }
    });
});
