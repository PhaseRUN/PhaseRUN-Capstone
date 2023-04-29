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


const profilePicElm = document.querySelector('.profile-pic');
const client = filestack.init('AthR8AktzR6e4xfLVdfX6z');

profilePicElm.addEventListener('click', function () {
    console.log('clicked');
    client.picker({
        onFileUploadFinished: (res) => {
            console.log(res);
            postProfilePicData(res);
        },
    }).open();
});

//profile pic upload to database
function postProfilePicData(res){
    const csrfToken = document.querySelector('meta[name="_csrf"]').content;
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;
    const profilePicURL = res.url
    const userIdElement = document.getElementById('userId');
    const userId = userIdElement.getAttribute("data-user-Id");
    console.log(userId);
    const jsonObject = {
        profilePicURL: profilePicURL,
        userId: userId
    };
    console.log(jsonObject);

    fetch("/api/upload-picture", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': csrfToken
        },
        body: JSON.stringify(jsonObject)
    })
        .then(response => {
            console.log(response);
            return response.json()
        })
        .then(data => {
            console.log("success");
            location.reload();
        })
        .catch(error => console.log(error))
}
