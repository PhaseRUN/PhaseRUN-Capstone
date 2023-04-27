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

function hasClass(elem, className) {
    return elem.className.split(' ').indexOf(className) > -1;
}

