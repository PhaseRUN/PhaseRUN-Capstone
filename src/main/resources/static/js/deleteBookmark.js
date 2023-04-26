// function deleteBookmark(raceId) {
//     if (confirm('Are you sure you want to delete this bookmark?')) {
//         // make a DELETE request to the server to delete the bookmark with the given raceId
//         fetch('/bookmarks/' + raceId, {
//             method: 'DELETE',
//         })
//             .then(response => {
//                 if (response.ok) {
//                     // reload the page to show the updated list of bookmarks
//                     location.reload();
//                 } else {
//                     console.log('Failed to delete bookmark: ' + response.status + ' ' + response.statusText);
//                 }
//             })
//             .catch(error => {
//                 console.log('Failed to delete bookmark:', error);
//             });
//     }
// }