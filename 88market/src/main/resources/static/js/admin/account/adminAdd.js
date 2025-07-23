document.addEventListener('DOMContentLoaded', function() {
	const tel = document.getElementById("tel").value;
	// 전화번호에 하이픈 추가
	const formattedTel = formatTel(tel);
	
	// 전화번호 포맷 함수
	function formatTel(tel) {
		return tel.replace(/(\d{3})(\d{3,4})(\d{4})/, "$1-$2-$3");
	} //formatTel
	
	// null 체크 함수('을')
	function validateNullEul(value, fieldName) {
		if (!value || value.trim() === "") {
			showErrorMessage(fieldName, `${fieldName}을 입력해주세요.`);
			// 체크 실패
			return false;
		} //end if
		// 체크 성공
		hideErrorMessage(fieldName);
		return true;
	} //validateNullEul
	
	// null 체크 함수('를')
	function validateNullRul(value, fieldName) {
		if (!value || value.trim() === "") {
			showErrorMessage(fieldName,`${fieldName}를 입력해주세요.`);
			// 체크 실패
			return false;
		} //end if
		// 체크 성공
		hideErrorMessage(fieldName);
		return true;
	} //validateNullRul
	
	// 이름 유효성 검사
	function validateName(name) {
		const nameText = /^[가-힣]{2,10}$/;
		if (!name || !nameText.test(name)) {
			// 유효성 검사 실패
			showErrorMessage("name", "이름은 최소 2자리, 최대 10자리 한글만 가능합니다.");
			return false;
		} //end if
		// 유효성 검사 성공
		hideErrorMessage("name");
		return true;
	} //validateName

	// 비밀번호 유효성 검사(일치 여부 확인)
	function validatePass(pass, passCheck){
		if(pass == passCheck){
			showErrorMessage("pass","비밀번호와 비밀번호 확인이 일치하지 않습니다.\n비밀번호를 다시 확인해주세요.");
			// 유효성 검사 실패
			return false;
		} //end if
		// 유효성 검사 성공
		hideErrorMessage("pass");
		return true;
	} //validatePass
	
	// 전화번호 유효성 검사
	function validateTel(tel) {
		const telText = /^\d{10,11}$/;
		if (!tel || !telText.test(tel)) {
			showErrorMessage("tel","전화번호는 숫자만 입력하고, 10자리 또는 11자리만 가능합니다.");
			// 유효성 검사 실패
			return false;
		} //end if
		// 유효성 검사 성공
		hideErrorMessage("tel");
		return true;
	} //validateTel
	
	// 에러 메시지를 출력하는 함수
	function showErrorMessage(fieldName, message) {
	    const errorDiv = document.querySelector(`#${fieldName} + .error-message`);

	    if (errorDiv && errorDiv.classList.contains('error-message')) {
	        errorDiv.textContent = message;
	        errorDiv.style.display = 'block';
			errorDiv.style.color = 'red';
	    } //end if
		// 입력값이 없을 때
	    const inputElement = document.getElementById(fieldName);
	    inputElement.style.borderColor = 'red';
	    inputElement.style.borderWidth = '1px';
	    inputElement.style.borderStyle = 'solid';
	} //showErrorMessage
	
	// 에러 메시지를 숨기는 함수
	function hideErrorMessage(fieldName) {
	    const errorDiv =  document.querySelector(`#${fieldName} + .error-message`);

	    if (errorDiv && errorDiv.classList.contains('error-message')) {
	        errorDiv.style.display = 'none';
	    } //end if
		// 입력값이 있을 때
	    const inputElement = document.getElementById(fieldName);
		inputElement.style.borderColor = 'black';
		inputElement.style.borderWidth = '1px';
		inputElement.style.borderStyle = 'solid';
		inputElement.style.backgroundColor = '#e0f7fa';
	} //hideErrorMessage
	
	// 각 입력 필드에 input 이벤트 리스너 추가
	const inputs = document.querySelectorAll('input');
	inputs.forEach(input => {
		input.addEventListener('input', function() {
			const value = this.value;
			const id = this.id;

			// 각 필드별 유효성 검사
			if (id === 'name') {
				validateName(value);
			} else if (id === 'tel') {
				validateTel(value);
			} else if (id === 'id') {
				validateNullRul(value, "아이디");
			} else if (id === 'pass') {
				validatePass(document.getElementById("pass").value, document.getElementById("pass-Check").value);
			} //end if else
		}); //input
	}); //forEach
	
	
	const btnAdd = document.getElementById("btnAdd");
	btnAdd.addEventListener("click", function () {
		const name = document.getElementById("name").value;
		const id = document.getElementById("id").value;
	    const pass = document.getElementById("pass").value;
	    const passCheck = document.getElementById("pass-check").value;
	    const regDate = document.getElementById("reg-date").value;
		const banValue = document.querySelector('input[name="ban"]:checked').value;

		if( !validateNullEul(name, "이름") ){
			return;
		}
		// 전화번호 유효성 검사
		if(!validateNullRul(tel, "전화번호")){
			// 유효성 검사 실패
			return;
		} //end if
		// 유효성 검사 성공
		
		// 아이디 유효성 검사
		if(!validateNullRul(id, "아이디")){
			// 유효성 검사 실패
			return;
		} //end if
		// 유효성 검사 성공
		
		if(!validateNullRul(pass, "비밀번호") || !validateNullEul(passCheck, "비밀번호 확인")){
			// 유효성 검사 실패
			return;
		} //end if
		// 유효성 검사 성공
		
		
	    const jsonParam = {
			name : name,
			tel : formattedTel,
			id : id,
			pass : pass,
			inputDate : regDate,
			banType : banValue
		};
		
		fetch('/admin/account/adminAddProcess', {
			method: "POST",
			headers: {
				 "Content-Type": "application/json; charset=UTF-8",
			 },
			 body: JSON.stringify(jsonParam),
		}).then((res) => {
			if (!res.ok) throw new Error(`HTTP 에러 ${res.status}`);
			return res.json();
		}).then((data) => {
			console.log(data);
			if(data.result){
				// 등록 성공
				alert("관리자 정보 등록이 완료되었습니다.");
	    		// 이전 화면으로 이동
	        	window.history.back();
	    	} else {
	    		// 등록 실패
	    		alert(data.msg);
	        } //end if else
		}).catch((err) => {
	        console.error("등록 오류:", err);
		}); // 에러 처리
	}); //btnAdd

	const btnBack = document.getElementById("btnBack");
	btnBack.addEventListener("click", function () {
		// 이전 화면으로 이동
		window.history.back();
	}); //btnBack
	
}); //DOMContentLoaded