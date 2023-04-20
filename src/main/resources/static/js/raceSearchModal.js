// Get all the race cards
const raceCards = document.querySelectorAll('.race-cards');

// Loop through each race card
raceCards.forEach(raceCard => {
    // Get the modal element for the current race card
    const modal = raceCard.querySelector('.myModal');

    // Get the close button for the current race card modal
    const closeButton = modal.querySelector('.close');

    // Get the anchor tag that triggers the modal
    const raceCardLink = raceCard.querySelector('.raceCard');

    // Get the bookmark button for the current race card modal
    const bookmarkButton = modal.querySelector('.bookmark-btn');

    // Get the signup button for the current race card modal
    const signupButton = modal.querySelector('.signup-btn');

    // Add a click event listener to the race card link
    raceCardLink.addEventListener('click', event => {
        event.preventDefault(); // Prevent the default link behavior

        // Show the modal when the link is clicked
        modal.style.display = 'block';

        // Show the bookmark button when the modal is opened
        bookmarkButton.style.display = 'block';

        // Show the signup button when the modal is opened
        signupButton.style.display = 'block';
    });

    // Add a click event listener to the close button
    closeButton.addEventListener('click', () => {
        // Hide the bookmark button when the modal is closed
        modal.style.display = 'none';

        // Hide the bookmark button when the modal is opened
        bookmarkButton.style.display = 'none';

        // Hide the signup button when the modal is opened
        signupButton.style.display = 'none';
    });

    // Add a click event listener to the bookmark button
    bookmarkButton.addEventListener('click', () => {
        // Handle bookmark button click event here
        // ...
    });

    signupButton.addEventListener('click', () => {
        // Handle signup button click event here
        // ...
    });

    // Hide the modal and bookmark button when the page loads
    modal.style.display = 'none';
    bookmarkButton.style.display = 'none';
    signupButton.style.display = 'none';
});


