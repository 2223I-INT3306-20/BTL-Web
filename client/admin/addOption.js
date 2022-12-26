const form = {
    name: document.querySelector("#ten"),
    acc: document.querySelector("#username"),
    password: document.querySelector("#pw"),
    role: document.querySelector("#role"),
    submit: document.querySelector("#btnAddAcc"),
};

let button = form.submit.addEventListener("click", (e) => {
    e.preventDefault();
    validate();
});


function fetchAddAcount() {
    const login = "http://localhost:8080/auth/signup";

    fetch(login, {
        method: "POST",
        headers: {
            Accept: "application/json, text/plain, */*",
            "Content-Type": "application/json",
            "Authorization": "Bearer " + window.sessionStorage.getItem('token'),
        },
        body: JSON.stringify({
            name: form.name.value,
            username: form.acc.value,
            password: form.password.value,
            role: form.role.value
        }),
    })
        .then(function(response) {
            if (!response.ok) {
                //toastShow();
            }
            return response.json();})
        .then((data) => {
            console.log(data);
            if (data.error) {
                alert("Error");
            } else {
                alert("Oke");
            }
        })
        .catch((err) => {
            console.log(err);
        });
}

function validate() {
    var check  = 0;
    var sp = document.getElementById("brand").value;
    if (sp.trim().length === 0) {
        document.getElementById("error_brand").innerHTML = "Quý vị chưa nhập tên";
        document.getElementById("error_brand").classList.add("error");
        check = 1;
    } else {
        document.getElementById("error_brand").innerHTML = "";
        document.getElementById("error_brand").classList.remove("error");
    }

    var ts = document.getElementById("line").value;
    if (ts.trim().length === 0) {
        document.getElementById("error_line").innerHTML = "Quý vị chưa nhập thông số";
        document.getElementById("error_line").classList.add("error");
        check = 1;
    } else {
        document.getElementById("error_line").innerHTML = "";
        document.getElementById("error_line").classList.remove("error");
    }

    var sln = document.getElementById("screen").value;
    if (sln.trim().length === 0) {
        document.getElementById("error_screen").innerHTML = "Quý vị chưa nhập số lượng";
        document.getElementById("error_screen").classList.add("error");
        check = 1;
    } else {
        document.getElementById("error_screen").innerHTML = "";
        document.getElementById("error_screen").classList.remove("error");
    }

    var slb = document.getElementById("battery").value;
    if (slb.trim().length === 0) {
        document.getElementById("error_battery").innerHTML = "Quý vị chưa nhập số lượng";
        document.getElementById("error_battery").classList.add("error");
        check = 1;
    } else {
        document.getElementById("error_battery").innerHTML = "";
        document.getElementById("error_battery").classList.remove("error");
    }

    var gn = document.getElementById("cpu").value;
    if (gn.trim().length === 0) {
        document.getElementById("error_cpu").innerHTML = "Quý vị chưa nhập số tiền";
        document.getElementById("error_cpu").classList.add("error");
        check = 1;
    } else {
        document.getElementById("error_cpu").innerHTML = "";
        document.getElementById("error_cpu").classList.remove("error");
    }

    var gb = document.getElementById("ram").value;
    if (gb.trim().length === 0) {
        document.getElementById("error_ram").innerHTML = "Quý vị chưa nhập số tiền";
        document.getElementById("error_ram").classList.add("error");
        check = 1;
    } else {
        document.getElementById("error_ram").innerHTML = "";
        document.getElementById("error_ram").classList.remove("error");
    }

    var tl = document.getElementById("rom").value;
    if (tl.trim().length === 0) {
        document.getElementById("error_rom").innerHTML = "Quý vị chưa nhập số tiền";
        document.getElementById("error_rom").classList.add("error");
        check = 1;
    } else {
        document.getElementById("error_rom").innerHTML = "";
        document.getElementById("error_rom").classList.remove("error");
    }

    var ten = document.getElementById("gpu").value;
    if (ten.trim().length === 0) {
        document.getElementById("error_gpu").innerHTML = "Quý vị chưa nhập họ tên";
        document.getElementById("error_gpu").classList.add("error");
        check = 1;
    } else {
        document.getElementById("error_gpu").innerHTML = "";
        document.getElementById("error_gpu").classList.remove("error");
    }


    if (check === 0 ) {
        fetchAddAcount();
    } 
}
