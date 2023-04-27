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
// $('.comment-btn').click(function() {
//     $(this).parent().find('.comment-section').toggle();
// });
//
// $('.info-btn').click(function() {
//     $(this).parent().find('.info-section').toggle();
// });

// const commentContainer = document.getElementById('allComments');
// document.getElementsByClassName('addComments').addEventListener('click', function (ev) {
//     addComment(ev);
// });
//
// function addComment(ev) {
//     let commentText = ''
//     const textBox = document.createElement('div');
//     const replyButton = document.createElement('button');
//     replyButton.className = 'reply';
//     replyButton.innerHTML = 'Reply';
//     const likeButton = document.createElement('button');
//     likeButton.innerHTML = 'Like';
//     likeButton.className = 'likeComment';
//     const deleteButton = document.createElement('button');
//     deleteButton.innerHTML = 'Delete';
//     deleteButton.className = 'deleteComment';
//     const wrapDiv = document.createElement('div');
//     wrapDiv.className = 'wrapper';
//     wrapDiv.style.marginLeft = 0;
//     commentText = document.getElementById('newComment').value;
//     document.getElementById('newComment').value = '';
//     textBox.innerHTML = commentText;
//     wrapDiv.append(textBox, replyButton, likeButton, deleteButton);
//     commentContainer.appendChild(wrapDiv);
// }

// function hasClass(elem, className) {
//     return elem.className.split(' ').indexOf(className) > -1;
// }
// document.getElementById('allComments').addEventListener('click', function (e) {
//     if (hasClass(e.target, 'reply')) {
//         const parentDiv = e.target.parentElement;
//         const wrapDiv = document.createElement('div');
//         wrapDiv.style.marginLeft = (Number.parseInt(parentDiv.style.marginLeft) + 15).toString() + 'px';
//         wrapDiv.className = 'wrapper';
//         const textArea = document.createElement('textarea');
//         textArea.style.marginRight = '20px';
//         const addButton = document.createElement('button');
//         addButton.className = 'addReply';
//         addButton.innerHTML = 'Add';
//         const cancelButton = document.createElement('button');
//         cancelButton.innerHTML = 'Cancel';
//         cancelButton.className='cancelReply';
//         wrapDiv.append(textArea, addButton, cancelButton);
//         parentDiv.appendChild(wrapDiv);
//     } else if(hasClass(e.target, 'addReply')) {
//         addComment(e);
//     } else if(hasClass(e.target, 'likeComment')) {
//         const likeBtnValue = e.target.innerHTML;
//         e.target.innerHTML = likeBtnValue !== 'Like' ? Number.parseInt(likeBtnValue) + 1 : 1;
//     } else if(hasClass(e.target, 'cancelReply')) {
//         e.target.parentElement.innerHTML = '';
//     } else if(hasClass(e.target, 'deleteComment')) {
//         e.target.parentElement.remove();
//     }
// });

// function setOnLocalStorage () {
//     localStorage.setItem('template', document.getElementById('allComments').innerHTML);
// }


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


function postProfilePicData(res){
    const csrfToken = document.querySelector('meta[name="_csrf"]').content;
    // const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;
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
        })
        .catch(error => console.log(error))
}
