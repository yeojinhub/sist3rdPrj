document.addEventListener('DOMContentLoaded', function() {
	
	// 이름 실시간 유효성 검사
	const nameInput = document.getElementById("name");
	nameInput.addEventListener('input', function() {
	    validateName(nameInput.value);
	}); //idInput
	
	// 이름 유효성 검사 함수
	function validateName(name) {
		const nameText = /^[가-힣]+$/;
		// null 검사 실패
		if (!name){
			showErrorMessage("name", "이름을 입력하세요.");
			return false;
		} //end if
		
		// 길이 검사 실패
		if(name.length < 1) {
			showErrorMessage("name", "이름은 최소 2자리, 최대 10자리 가능합니다.");
			return false;
		} //end if
		
		// 한글 검사 실패
		if (!nameText.test(name)) {
			showErrorMessage("name", "이름은 한글만 가능합니다.");
			return false;
		} //end if
		
		// 유효성 검사 성공
		hideErrorMessage("name");
		return true;
	} //validateName
	
	// 아이디 실시간 유효성 검사
	const idInput = document.getElementById("id");
	idInput.addEventListener('input', function() {
	    validateID(idInput.value);
	}); //idInput
	
	// 아이디 유효성 검사 함수
	function validateID(id) {
	  const idRegex = /^[a-zA-Z0-9]+$/;

	  // null 검사 실패
	  if (!id) {
	    showErrorMessage("id", "아이디를 입력하세요.");
	    return false;
	  } //end if

	  // 길이 검사 실패
	  if (id < 4){
		showErrorMessage("id","아이디는 최소 4자리, 최대 15자리 가능합니다.")
		return false;
	  } //end if
	  
	  // 영문, 숫자 검사 실패
	  if (!idRegex.test(id)) {
	    showErrorMessage("id", "아이디는 영문 포함 숫자만 가능합니다.");
	    return false;
	  } //end if

	  // 유효성 검사 성공
	  hideErrorMessage("id");
	  return true;
	} //validateID
	
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
	
	// null 체크 함수('을')
	function validateNullEul(value, fieldName) {
		if (!value || !value.trim() === "") {
			alert(`${fieldName}을 입력해주세요.`);
			// 체크 실패
			return false;
		} //end if
		// 체크 성공
		return true;
	} //validateNullEul

	// null 체크 함수('를')
	function validateNullRul(value, fieldName) {
		if (!value || !value.trim() === "") {
			alert(`${fieldName}를 입력해주세요.`);
			// 체크 실패
			return false;
		} //end if
		// 체크 성공
		return true;
	} //validateNullRul
	
	const btnPassReset = document.getElementById("btnPassReset");
	btnPassReset.addEventListener("click", function(){
		const admNum = document.getElementById("admNum").value;
		
		fetch('/admin/account/adminPassModifyProcess', {
		  method: 'POST',
		  headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' },
		  body: new URLSearchParams({ admNum })
		}).then((res) => {
			if (!res.ok) throw new Error(`HTTP 에러 ${res.status}`);
				return res.json();
			}).then((data) => {
				console.log(data);
				if(data.result){
					// 수정 성공
					alert("비밀번호 초기화가 완료되었습니다.");
					// 이전 화면으로 이동
			        window.location.href = '/admin/account/admins';
			    } else {
			    	// 수정 실패
			    	alert(data.msg);
			    } //end if else
			}).catch((err) => {
				console.error("초기화 오류:", err);
			}); // 에러 처리
	}); //btnPassReset
	
	const btnModify = document.getElementById("btnModify");
	
	btnModify.addEventListener("click", function () {
		const admNum = document.getElementById("admNum").value;
		const name = document.getElementById("name").value;
	    const tel = document.getElementById("tel").value;
	    const id = document.getElementById("id").value;
		const rollType = document.querySelector('input[name="roll"]:checked').value;
		const banType = document.querySelector('input[name="ban"]:checked').value;

		// 전화번호 null 검사
		if( !validateNullEul(name, "이름") ){
			// null 검사 실패
			return;
		} //end if
		// null 검사 성공
		
		// 전화번호 null 검사
		if(!validateNullRul(tel, "전화번호")){
			// null 검사 실패
			return;
		} //end if
		// null 검사 성공

		// 아이디 null 검사
		if(!validateNullRul(id, "아이디")){
			// null 검사 실패
			return;
		} //end if
		// null 검사 성공
		
	    const jsonParam = {
			admNum : admNum,
			name : name,
			tel : tel,
			id : id,
			rollType : rollType,
			banType : banType
		};
		
		fetch('/admin/account/adminModifyProcess', {
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
				// 수정 성공
				alert("관리자 정보 수정이 완료되었습니다.");
	    		// 이전 화면으로 이동
	        	window.location.href = '/admin/account/admins';
	    	} else {
	    		// 수정 실패
	    		alert(data.msg);
	        } //end if else
		}).catch((err) => {
	        console.error("수정 오류:", err);
		}); // 에러 처리
	}); //btnModify
	
	const btnBack = document.getElementById("btnBack");
	btnBack.addEventListener("click", function () {
		// 이전 화면으로 이동
		window.location.href = '/admin/account/admins';
	}); //btnBack
	
}); //DOMContentLoaded