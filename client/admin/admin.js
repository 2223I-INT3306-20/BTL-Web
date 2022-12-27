function checkAndJump(ev, nextid) {
    if (ev.keyCode == 13) {
        document.getElementById(nextid).focus();
    }
}

function Them() {
    var sp = document.getElementById("sp").value;
    if (sp.trim().length === 0) {
        document.getElementById("error_sp").innerHTML = "Quý vị chưa nhập tên";
        document.getElementById("error_sp").classList.add("error");
    } else {
        document.getElementById("error_sp").innerHTML = "";
        document.getElementById("error_sp").classList.remove("error");
    }

    var ts = document.getElementById("ts").value;
    if (ts.trim().length === 0) {
        document.getElementById("error_ts").innerHTML = "Quý vị chưa nhập thông số";
        document.getElementById("error_ts").classList.add("error");
    } else {
        document.getElementById("error_ts").innerHTML = "";
        document.getElementById("error_ts").classList.remove("error");
    }

    var sln = document.getElementById("sln").value;
    if (sln.trim().length === 0) {
        document.getElementById("error_sln").innerHTML = "Quý vị chưa nhập số lượng";
        document.getElementById("error_sln").classList.add("error");
    } else {
        document.getElementById("error_sln").innerHTML = "";
        document.getElementById("error_sln").classList.remove("error");
    }

    var slb = document.getElementById("slb").value;
    if (slb.trim().length === 0) {
        document.getElementById("error_slb").innerHTML = "Quý vị chưa nhập số lượng";
        document.getElementById("error_slb").classList.add("error");
    } else {
        document.getElementById("error_slb").innerHTML = "";
        document.getElementById("error_slb").classList.remove("error");
    }

    var gn = document.getElementById("gn").value;
    if (gn.trim().length === 0) {
        document.getElementById("error_gn").innerHTML = "Quý vị chưa nhập số tiền";
        document.getElementById("error_gn").classList.add("error");
    } else {
        document.getElementById("error_gn").innerHTML = "";
        document.getElementById("error_gn").classList.remove("error");
    }

    var gb = document.getElementById("gb").value;
    if (gb.trim().length === 0) {
        document.getElementById("error_gb").innerHTML = "Quý vị chưa nhập số tiền";
        document.getElementById("error_gb").classList.add("error");
    } else {
        document.getElementById("error_gb").innerHTML = "";
        document.getElementById("error_gb").classList.remove("error");
    }

    var tl = document.getElementById("tl").value;
    if (tl.trim().length === 0) {
        document.getElementById("error_tl").innerHTML = "Quý vị chưa nhập số tiền";
        document.getElementById("error_tl").classList.add("error");
    } else {
        document.getElementById("error_tl").innerHTML = "";
        document.getElementById("error_tl").classList.remove("error");
    }

    var ten = document.getElementById("ten").value;
    if (ten.trim().length === 0) {
        document.getElementById("error_ten").innerHTML = "Quý vị chưa nhập họ tên";
        document.getElementById("error_ten").classList.add("error");
    } else {
        document.getElementById("error_ten").innerHTML = "";
        document.getElementById("error_ten").classList.remove("error");
    }

    var em = document.getElementById("em").value;
    if (em.trim().length === 0) {
        document.getElementById("error_em").innerHTML = "Quý vị chưa nhập email";
        document.getElementById("error_em").classList.add("error");
    } else {
        document.getElementById("error_em").innerHTML = "";
        document.getElementById("error_em").classList.remove("error");
    }

    var pw = document.getElementById("pw").value;
    if (pw.trim().length === 0) {
        document.getElementById("error_pw").innerHTML = "Quý vị chưa nhập mật khẩu";
        document.getElementById("error_pw").classList.add("error");
    } else {
        document.getElementById("error_pw").innerHTML = "";
        document.getElementById("error_pw").classList.remove("error");
    }
}

var indicator = document.querySelector('.nav-indicator');
var items = document.querySelectorAll('.nav-item1');
function handleIndicator(el) {
    items.forEach(function (item) {
        item.classList.remove('is-active');
        item.removeAttribute('style');
    });
    indicator.style.width = "".concat(el.offsetWidth, "px");
    indicator.style.left = "".concat(el.offsetLeft, "px");
    indicator.style.backgroundColor = el.getAttribute('active-color');
    el.classList.add('is-active');
    el.style.color = el.getAttribute('active-color');
}
items.forEach(function (item, index) {
    item.addEventListener('click', function (e) {
        handleIndicator(e.target);
    });
    item.classList.contains('is-active') && handleIndicator(item);
});


document.querySelector("#name").innerHTML += window.sessionStorage.getItem('name').toString();

/* Load acc list */
function loadAcc() {
    this.disabled = true;
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function(){
        if(this.readyState==4){
            if(this.status==200){
                let objArr = JSON.parse(this.responseText);
                document.querySelector("#tblAcc tbody").innerHTML = "";
                for(let i = 0; i< objArr.length; i++){
                    let tr = document.createElement("tr");
                    let c0 = document.createElement("td");
                    let c1 = document.createElement("td");
                    let c2 = document.createElement("td");
                    let c3 = document.createElement("td");
                    let c4 = document.createElement("td");

                    c0.innerHTML += "<input type=\"checkbox\">";
                    c1.innerHTML = objArr[i].id;
                    c2.innerHTML = objArr[i].name;
                    c3.innerHTML = objArr[i].username;
                    c4.innerHTML = objArr[i].location;

                    tr.appendChild(c0);
                    tr.appendChild(c1);
                    tr.appendChild(c2);
                    tr.appendChild(c3);
                    tr.appendChild(c4);

                    document.querySelector("#tblAcc tbody").appendChild(tr);
                }
            }
        }
    }
    xhttp.open("GET","http://localhost:8080/admin/listUser",true);
    xhttp.setRequestHeader("Authorization", "Bearer " + window.sessionStorage.getItem('token'))
    xhttp.send("null");
}
$(document).ready(function(){
    loadAcc();
})

let col = 0;
let dir = "";
let tbl = document.getElementById("tblData");

tbl.rows[0].cells[3].onclick = function() {
    if (col == 3) tbl.rows[0].cells[3].classList.remove(dir);
    else if (col == 7) tbl.rows[0].cells[7].classList.remove(dir);

    if (col == 3) {
        if (dir == "asc") {
            dir = "desc";
        } else {
            dir = "asc";
        }
    } else {
        col = 3;
        dir = "asc";
    }
    this.classList.add(dir);
    tblSort();
};
tbl.rows[0].cells[7].onclick = function() {
    if (col == 3) tbl.rows[0].cells[3].classList.remove(dir);
    else if (col == 7) tbl.rows[0].cells[7].classList.remove(dir);
    if (col == 7) {
        if (dir == "asc") {
            dir = "desc";
        } else {
            dir = "asc";
        }
    } else {
        col = 7;
        dir = "asc";
    }
    this.classList.add(dir);
    tblSort();
};


function tblSort() {
    let rows = tbl.rows;
    for (var i = 1; i < rows.length; i++) {
        for (var j = i+1; j < rows.length; j++) {
            if ((dir == "asc" && rows[i].cells[col].innerHTML.toLowerCase() >
                    rows[j].cells[col].innerHTML.toLowerCase()) ||
                (dir == "desc" && rows[i].cells[col].innerHTML.toLowerCase() <
                    rows[j].cells[col].innerHTML.toLowerCase())
            )
            {
                tmp = rows[i].cells[1].innerHTML;
                rows[i].cells[1].innerHTML = rows[j].cells[1].innerHTML;
                rows[j].cells[1].innerHTML = tmp;

                tmp = rows[i].cells[2].innerHTML;
                rows[i].cells[2].innerHTML = rows[j].cells[2].innerHTML;
                rows[j].cells[2].innerHTML = tmp;

                tmp = rows[i].cells[3].innerHTML;
                rows[i].cells[3].innerHTML = rows[j].cells[3].innerHTML;
                rows[j].cells[3].innerHTML = tmp;

                tmp = rows[i].cells[4].innerHTML;
                rows[i].cells[4].innerHTML = rows[j].cells[4].innerHTML;
                rows[j].cells[4].innerHTML = tmp;

                tmp = rows[i].cells[5].innerHTML;
                rows[i].cells[5].innerHTML = rows[j].cells[5].innerHTML;
                rows[j].cells[5].innerHTML = tmp;

                tmp = rows[i].cells[6].innerHTML;
                rows[i].cells[6].innerHTML = rows[j].cells[6].innerHTML;
                rows[j].cells[6].innerHTML = tmp;

                tmp = rows[i].cells[7].innerHTML;
                rows[i].cells[7].innerHTML = rows[j].cells[7].innerHTML;
                rows[j].cells[7].innerHTML = tmp;
            }
        }
    }
}

const ctx1 = document.getElementById('daban');
new Chart(ctx1, {
    type: 'bar',
    data: {
        labels: ['2017', '2018', '2019', '2020', '2021', '2022'],
        datasets: [{
            label: 'Macbook',
            data: [12, 19, 3, 5, 2, 3],
            borderWidth: 1
        }, {
            label: 'ASUS',
            data: [15, 16, 6, 3, 1, 4],
            borderWidth: 1
        }, {
            label: 'DELL',
            data: [8, 9, 6, 4, 3, 2],
            borderWidth: 1
        }

        ]
    },

    options: {
        scales: {
            y: {
                beginAtZero: true
            }
        }
    }
});

const ctx2 = document.getElementById('dasx');
new Chart(ctx2, {
    type: 'line',
    data: {
        labels: ['2017','2018','2019','2020','2021','2022'],
        datasets: [{
            label: 'Macbook',
            data: [80, 70, 89, 49, 60, 90],
            fill: false,
            borderColor: 'red',
            backgroundColor: 'red',
            tension: 0.1
        }, {
            label: 'ASUS',
            data: [59, 80, 81, 56, 55, 80],
            fill: false,
            borderColor: 'blue',
            backgroundColor: 'blue',
            tension: 0.1
        },  {
            label: 'DELL',
            data: [70, 84, 86, 60, 66, 87],
            fill: false,
            borderColor: 'green',
            backgroundColor: 'green',
            tension: 0.1
        }
        ]
    }
});

const ctx3 = document.getElementById('dabh');
new Chart(ctx3, {
    type: 'pie',
    data: {
        labels: ['Macbook','ASUS','DELL'],
        datasets: [{
            label: 'Số máy',
            data: [300, 50, 100],
            backgroundColor: [
                'rgb(255, 99, 132)',
                'rgb(54, 162, 235)',
                'rgb(255, 205, 86)'
            ],
            hoverOffset: 4
        }]

    }
});

function logout() {
    sessionStorage.removeItem('token');
    sessionStorage.clear();
}

function toastShow() {
    document.getElementById("liveToast").classList.remove("hide");
    document.getElementById("liveToast").classList.add("show");
    setTimeout(() => {
        document.getElementById("liveToast").classList.remove("show");
        document.getElementById("liveToast").classList.add("hide");
    }, 4000);
}