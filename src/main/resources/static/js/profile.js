$('#editProfileModal').click(function () {
    $('#editProfileModal').modal('show');
});

$('#editFitnessModal').click(function (){
    $('#editFitnessModal').modal('show');
});

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