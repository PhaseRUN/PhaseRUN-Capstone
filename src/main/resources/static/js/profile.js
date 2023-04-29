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
    const infoSection = $(this).parent().find('.info-section');
    const isActive = $(this).hasClass('active');

    infoSection.toggle();
    $(this).toggleClass('active');

    if (isActive) {
        infoSection.removeClass('text-info');
    } else {
        infoSection.addClass('text-info');
    }
});


function hasClass(elem, className) {
    return elem.className.split(' ').indexOf(className) > -1;
}
