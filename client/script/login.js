//document.getElementById('button1').addEventListener('click', getExternal);
var account = document.getElementById('acc');
var pass = document.getElementById('pass');

// Get external API data
function login() {
    fetch('http://localhost:8080/login',{


    // Adding method type
        method: "POST",

        // Adding body or contents to send
        body: JSON.stringify({
        acc: account,
        pass: pass,
        userId: 1
    }),

        // Adding headers to the request
        headers: {
        "Content-type": "application/json; charset=UTF-8"
    }
})
        .then(function(res){
            return res.json();
        })
        .then(function(data) {
            // This is an array so we have to loop through it
            alert(data);

        })
        .catch(function(err){
            console.log(err);
        });
}
