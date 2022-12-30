const formAddOption = {
    brand: document.querySelector("#brand"),
    line: document.querySelector("#line"),
    screen: document.querySelector("#screen"),
    resolution: document.querySelector("#resolution"),
    screenType: document.querySelector("#screenType"),
    battery: document.querySelector("#battery"),
    cpub: document.querySelector("#cpub"),
    cpu: document.querySelector("#cpu"),
    ram: document.querySelector("#ram"),
    rom: document.querySelector("#rom"),
    romType: document.querySelector("#romType"),
    gpu: document.querySelector("#gpu"),
    submit: document.querySelector("#btnAddOption"),
};

let buttonOption = formAddOption.submit.addEventListener("click", (e) => {
    e.preventDefault();
    validateOption();
});


function fetchAddOption() {
    const login = "http://localhost:8080/admin/createNewOption";

    fetch(login, {
        method: "POST",
        headers: {
            Accept: "application/json, text/plain, */*",
            "Content-Type": "application/json",
            "Authorization": "Bearer " + window.sessionStorage.getItem('token'),
        },
        body: JSON.stringify({
            brandName: formAddOption.brand.value,
            optionName: formAddOption.line.value,
            screenSize: formAddOption.screen.value,
            screenType: formAddOption.screenType.value,
            resolution: formAddOption.resolution.value,
            battery: formAddOption.battery.value,
            cpuBrand: formAddOption.cpub.value,
            cpuName: formAddOption.cpu.value,
            ram: formAddOption.ram.value,
            rom: formAddOption.rom.value,
            romType: formAddOption.romType.value,
            gpu: formAddOption.gpu.value,
        }),
    })
        .then(function(response) {
            if (response.ok) {
                loadOption();
                document.getElementById("closeAddOption").click();
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

function validateOption() {
    var check  = 0;
    // var sp = document.getElementById("brand").value;
    // if (sp.trim().length === 0) {
    //     document.getElementById("error_brand").innerHTML = "Quý vị chưa nhập tên";
    //     document.getElementById("error_brand").classList.add("error");
    //     check = 1;
    // } else {
    //     document.getElementById("error_brand").innerHTML = "";
    //     document.getElementById("error_brand").classList.remove("error");
    // }
    //
    // var ts = document.getElementById("line").value;
    // if (ts.trim().length === 0) {
    //     document.getElementById("error_line").innerHTML = "Quý vị chưa nhập thông số";
    //     document.getElementById("error_line").classList.add("error");
    //     check = 1;
    // } else {
    //     document.getElementById("error_line").innerHTML = "";
    //     document.getElementById("error_line").classList.remove("error");
    // }
    //
    // var sln = document.getElementById("screen").value;
    // if (sln.trim().length === 0) {
    //     document.getElementById("error_screen").innerHTML = "Quý vị chưa nhập số lượng";
    //     document.getElementById("error_screen").classList.add("error");
    //     check = 1;
    // } else {
    //     document.getElementById("error_screen").innerHTML = "";
    //     document.getElementById("error_screen").classList.remove("error");
    // }
    //
    // var slb = document.getElementById("battery").value;
    // if (slb.trim().length === 0) {
    //     document.getElementById("error_battery").innerHTML = "Quý vị chưa nhập số lượng";
    //     document.getElementById("error_battery").classList.add("error");
    //     check = 1;
    // } else {
    //     document.getElementById("error_battery").innerHTML = "";
    //     document.getElementById("error_battery").classList.remove("error");
    // }
    //
    // var gn = document.getElementById("cpu").value;
    // if (gn.trim().length === 0) {
    //     document.getElementById("error_cpu").innerHTML = "Quý vị chưa nhập số tiền";
    //     document.getElementById("error_cpu").classList.add("error");
    //     check = 1;
    // } else {
    //     document.getElementById("error_cpu").innerHTML = "";
    //     document.getElementById("error_cpu").classList.remove("error");
    // }
    //
    // var gb = document.getElementById("ram").value;
    // if (gb.trim().length === 0) {
    //     document.getElementById("error_ram").innerHTML = "Quý vị chưa nhập số tiền";
    //     document.getElementById("error_ram").classList.add("error");
    //     check = 1;
    // } else {
    //     document.getElementById("error_ram").innerHTML = "";
    //     document.getElementById("error_ram").classList.remove("error");
    // }
    //
    // var tl = document.getElementById("rom").value;
    // if (tl.trim().length === 0) {
    //     document.getElementById("error_rom").innerHTML = "Quý vị chưa nhập số tiền";
    //     document.getElementById("error_rom").classList.add("error");
    //     check = 1;
    // } else {
    //     document.getElementById("error_rom").innerHTML = "";
    //     document.getElementById("error_rom").classList.remove("error");
    // }
    //
    // var ten = document.getElementById("gpu").value;
    // if (ten.trim().length === 0) {
    //     document.getElementById("error_gpu").innerHTML = "Quý vị chưa nhập họ tên";
    //     document.getElementById("error_gpu").classList.add("error");
    //     check = 1;
    // } else {
    //     document.getElementById("error_gpu").innerHTML = "";
    //     document.getElementById("error_gpu").classList.remove("error");
    // }

    check  = 0;

    if (check === 0) {
        fetchAddOption();
    } 
}
