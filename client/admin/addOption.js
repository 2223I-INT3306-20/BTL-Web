const formOption = {
    name: document.querySelector("#ten"),
    acc: document.querySelector("#username"),
    password: document.querySelector("#pw"),
    role: document.querySelector("#role"),
    submit: document.querySelector("#btnAddOption"),
};

let buttonOption = formOption.submit.addEventListener("click", (e) => {
    e.preventDefault();
    Themsp();
});


function fetchAddOption() {
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

function Themsp() {
    var check = 0;
    var brand = document.getElementById("brand").value;
    if (brand.trim().length === 0) {
        document.getElementById("error_brand").innerHTML = "Quý vị chưa nhập tên hãng";
        document.getElementById("error_brand").classList.add("error");
        check = 1;
    } else {
        document.getElementById("error_brand").innerHTML = "";
        document.getElementById("error_brand").classList.remove("error");
    }

    var line = document.getElementById("line").value;
    if (line.trim().length === 0) {
        document.getElementById("error_line").innerHTML = "Quý vị chưa nhập dòng sản phẩm";
        document.getElementById("error_line").classList.add("error");
        check = 1;
    } else {
        document.getElementById("error_line").innerHTML = "";
        document.getElementById("error_line").classList.remove("error");
    }

    var screen = document.getElementById("screen").value;
    if (screen.trim().length === 0) {
        document.getElementById("error_screen").innerHTML = "Quý vị chưa nhập kích thước";
        document.getElementById("error_screen").classList.add("error");
        check = 1;
    } else {
        document.getElementById("error_screen").innerHTML = "";
        document.getElementById("error_screen").classList.remove("error");
    }

    var battery = document.getElementById("battery").value;
    if (battery.trim().length === 0) {
        document.getElementById("error_battery").innerHTML = "Quý vị chưa nhập dung lượng pin";
        document.getElementById("error_battery").classList.add("error");
        check = 1;
    } else {
        document.getElementById("error_battery").innerHTML = "";
        document.getElementById("error_battery").classList.remove("error");
    }

    var cpubr = document.getElementById("cpubr").value;
    if (cpubr.trim().length === 0) {
        document.getElementById("error_cpubr").innerHTML = "Quý vị chưa nhập thông tin";
        document.getElementById("error_cpubr").classList.add("error");
        check = 1;
    } else {
        document.getElementById("error_cpubr").innerHTML = "";
        document.getElementById("error_cpubr").classList.remove("error");
    }

    var cpu = document.getElementById("cpu").value;
    if (cpu.trim().length === 0) {
        document.getElementById("error_cpu").innerHTML = "Quý vị chưa nhập tên CPU";
        document.getElementById("error_cpu").classList.add("error");
        check = 1;
    } else {
        document.getElementById("error_cpu").innerHTML = "";
        document.getElementById("error_cpu").classList.remove("error");
    }

    var rom = document.getElementById("rom").value;
    if (rom.trim().length === 0) {
        document.getElementById("error_rom").innerHTML = "Quý vị chưa nhập dung lượng ROM";
        document.getElementById("error_rom").classList.add("error");
        check = 1;
    } else {
        document.getElementById("error_rom").innerHTML = "";
        document.getElementById("error_rom").classList.remove("error");
    }

    var gpu = document.getElementById("gpu").value;
    if (gpu.trim().length === 0) {
        document.getElementById("error_gpu").innerHTML = "Quý vị chưa nhập thông tin GPU";
        document.getElementById("error_gpu").classList.add("error");
        check = 1;
    } else {
        document.getElementById("error_gpu").innerHTML = "";
        document.getElementById("error_gpu").classList.remove("error");
    }

    if (check === 0 ) {
        fetchAddOption();
    }
}
