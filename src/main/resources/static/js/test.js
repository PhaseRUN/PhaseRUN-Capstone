var settings = {
    "url": "https://runsignup.com/rest/races?api_key=1b5511344c1892462a5fdff8780b7e98ae45e645",
    "method": "GET",
    "timeout": 0,
};

$.ajax(settings).done(function (response) {
    console.log(response);
});