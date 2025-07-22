document.addEventListener('DOMContentLoaded', function() {
	
	const btnAdd = document.getElementById("btnAdd");
	
	btnAdd.addEventListener("click", function () {
		const name = document.getElementById("name").value;
		const tel = document.getElementById("tel").value;
		const id = document.getElementById("id").value;
	    const pass = document.getElementById("pass").value;
	    const regDate = document.getElementById("reg-date").value;
		const banValue = document.querySelector('input[name="ban"]:checked').value;

	    const jsonParam = {
			name : name,
			tel : tel,
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