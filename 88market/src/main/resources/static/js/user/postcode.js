function sample6_execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 주소 변수 선언
            var addr = ''; // 주소
            var extraAddr = ''; // 참고항목

            // 사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져옴
            if (data.userSelectedType === 'R') { // 도로명 주소
                addr = data.roadAddress;
            } else { // 지번 주소
                addr = data.jibunAddress;
            }

            // 참고항목 조합
            if (data.userSelectedType === 'R') {
                if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
                    extraAddr += data.bname;
                }
                if (data.buildingName !== '' && data.apartment === 'Y') {
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                if (extraAddr !== '') {
                    extraAddr = ' (' + extraAddr + ')';
                }
                document.getElementById("sample6_extraAddress").value = extraAddr;
            } else {
                document.getElementById("sample6_extraAddress").value = '';
            }

            // ▶▶ 주소 필드에 자동 입력
            document.getElementById('zipcode').value = data.zonecode;
            document.getElementById("road-address").value = addr;

            // ▶▶ [추가된 부분] 자동으로 입력된 필드에 스타일 적용
            ['zipcode', 'road-address', 'sample6_extraAddress'].forEach(id => {
                const el = document.getElementById(id);
                el.style.color = 'blue';                  // 글자색
                el.style.backgroundColor = '#e0f7fa';      // 배경색
                el.style.borderColor = 'black';            // 테두리
                el.style.borderWidth = '1px';
                el.style.borderStyle = 'solid';
            });

            // 상세주소 입력란으로 포커스 이동
            document.getElementById("detail-address").focus();
        }
    }).open();
}