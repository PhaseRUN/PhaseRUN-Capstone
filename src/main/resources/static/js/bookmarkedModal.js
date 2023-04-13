
let modal = document.getElementsByClassName("myModal")[0];

let bookMarkedRace = document.getElementsByClassName("bookmarkedRace");

let span = document.getElementsByClassName("close");

// Loop through the HTMLCollection and attach a click event listener to each element
for (let i = 0; i < bookMarkedRace.length; i++) {
    bookMarkedRace[i].addEventListener("click", function() {
        modal.style.display = "block";
    });
}

// When the user clicks on <span> (x), close the modal
for (let i = 0; i < span.length; i++) {
    span[i].addEventListener("click", function() {
        modal.style.display = "none";
    });
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
    if (event.target === modal) {
        modal.style.display = "none";
    }
}