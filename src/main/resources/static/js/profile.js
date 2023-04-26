$('#editProfileModal').click(function () {
    $('#editProfileModal').modal('show');
});

$('#editFitnessModal').click(function (){
    $('#editFitnessModal').modal('show');
});
// $('#commentModal').click(function (){
//     $('#commentModal').modal('show');
// });
// $('#infoModal').click(function (){
//     $('#infoModal').modal('show');
// });
$('.comment-btn').click(function() {
    $(this).parent().find('.comment-section').toggle();
});

$('.info-btn').click(function() {
    $(this).parent().find('.info-section').toggle();
});

const commentContainer = document.getElementById('allComments');
document.getElementById('addComments').addEventListener('click', function (ev) {
    addComment(ev);
});



const infoButtons = document.querySelectorAll('.toggle-info');
infoButtons.forEach(button => {
    button.addEventListener('click', () => {
        const cardBody = button.closest('.card-body');
        const infoSection = cardBody.querySelector('.info-section');
        infoSection.classList.toggle('d-none');
    });
});