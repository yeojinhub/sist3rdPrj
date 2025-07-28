document.addEventListener('DOMContentLoaded', function() {
    // 전화번호 유효성 검사
    function validateTel(tel) {
        const telText = /^\d{10,11}$/;
        if (!telText.test(tel)) {
            alert("전화번호는 숫자만 입력 가능, 최소 10자리, 최대 11자리만 가능합니다.");
            return false;
        }
        return true;
    }

    // 사업자등록번호 유효성 검사 (숫자 10자리)
    function validateBizNum(biz) {
        const bizText = /^\d{10}$/;
        if (!bizText.test(biz)) {
            alert("사업자등록번호는 숫자 10자리만 입력 가능합니다.");
            return false;
        }
        return true;
    }

    // 아이디 유효성 검사
    function validateID(id) {
        const idRegex = /^[a-zA-Z0-9]+$/;
        if (id.length < 4 || id.length > 15) {
            alert("아이디는 최소 4자리, 최대 15자리 가능합니다.");
            return false;
        }
        if (!idRegex.test(id)) {
            alert("아이디는 영문 포함 숫자만 가능합니다.");
            return false;
        }
        return true;
    }

    // 빈 값 검사
    function validateNotEmpty(value, fieldName) {
        if (!value || !value.trim()) {
            alert(fieldName + "을(를) 입력해주세요.");
            return false;
        }
        return true;
    }

    const btnModify = document.getElementById("btnModify");
    btnModify.addEventListener("click", function () {
        const comNum        = document.getElementById("comNum").value;
        const comName       = document.getElementById("comName").value;
        const ceoName       = document.getElementById("ceoName").value;
        const name          = document.getElementById("name").value;
        const tel           = document.getElementById("tel").value;
        const businessNum   = document.getElementById("businessNum").value;
        const id            = document.getElementById("id").value;
        const passElem      = document.getElementById("pass");
        const pass          = passElem ? passElem.value : "";
        const zipcode       = document.getElementById("zipcode").value;
        const addressRoad   = document.getElementById("road-address").value;
        const addressDetail = document.getElementById("detail-address").value;
        const addressExtra  = document.getElementById("extra-address").value;
        const withdraw      = document.querySelector('input[name="withdraw"]:checked').value;

        // 유효성 검사
        if (!validateNotEmpty(comName, "기업명"))           return;
        if (!validateNotEmpty(ceoName, "대표자명"))         return;
        if (!validateNotEmpty(name, "담당자명"))            return;
        if (!validateNotEmpty(tel, "전화번호"))             return;
        if (!validateTel(tel))                              return;
        if (!validateNotEmpty(businessNum, "사업자등록번호")) return;
        if (!validateBizNum(businessNum))                   return;
        if (!validateNotEmpty(id, "아이디"))                return;
        if (!validateID(id))                                return;

        const address = addressRoad + "/" + addressDetail + "/" + addressExtra;

        const jsonParam = {
            comNum,
            comName,
            ceoName,
            name,
            tel,
            businessNum,
            id,
            pass,
            zipcode,
            address,
            withdraw
        };

        fetch('/admin/account/companyModifyProcess', {
            method: "POST",
            headers: { "Content-Type": "application/json; charset=UTF-8" },
            body: JSON.stringify(jsonParam),
        })
        .then(res => {
            if (!res.ok) throw new Error(`HTTP 에러 ${res.status}`);
            return res.json();
        })
        .then(data => {
            if (data.result) {
                alert("기업 정보 수정이 완료되었습니다.");
                window.location.href = '/admin/account/company';
            } else {
                alert(data.msg);
            }
        })
        .catch(err => {
            console.error("수정 오류:", err);
        });
    });

    const btnBack = document.getElementById("btnBack");
    btnBack.addEventListener("click", function () {
        window.location.href = '/admin/account/company';
    });
});
