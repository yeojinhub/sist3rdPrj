document.addEventListener('DOMContentLoaded', function() {
	const select = document.getElementById('select');
	const input  = document.getElementById('email-domain');

	select.addEventListener('change', function() {
		const val = this.value;
		
		if (val === 'other' || val === '') {
			input.value = '';
			input.removeAttribute('readonly');
			input.focus();
		} else {
			input.value = val;
			input.setAttribute('readonly', 'readonly');
		} //end if else
	}); //addEventListener
	
	const btnAdd = document.getElementById("btnAdd");
	
	btnAdd.addEventListener("click", function () {
		const name = document.getElementById("name").value;
	    const nickname = document.getElementById("nickname").value;
	    const tel = document.getElementById("tel").value;
	    const email = document.getElementById("email-id").value + "@" + document.getElementById("email-domain").value;
	    const pass = document.getElementById("pass").value;
	    const zipcode = document.getElementById("zipcode").value;
	    const address =
	      document.getElementById("road-address").value +"/"+
	      document.getElementById("detail-address").value + "/"+
	      document.getElementById("extra-address").value;
	    const regDate = document.getElementById("reg-date").value;
		const banValue = document.querySelector('input[name="ban"]:checked').value;
		const withdrawValue = document.querySelector('input[name="withdraw"]:checked').value;

	    const jsonParam = {
			name : name,
			nickname : nickname,
			tel : tel,
			email : email,
			pass : pass,
			zipcode : zipcode,
			address : address,
			inputDate : regDate,
			banType : banValue,
			withdraw : withdrawValue
		};
		
		fetch('/admin/account/userAddProcess', {
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
				alert("회원 정보 등록이 완료되었습니다.");
	    		// 이전 화면으로 이동
	        	window.location.href = '/admin/account/users';
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
		window.location.href = '/admin/account/users';
	}); //btnBack
	
}); //DOMContentLoaded