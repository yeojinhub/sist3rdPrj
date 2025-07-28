document.addEventListener('DOMContentLoaded', () => {
	
	// 전화번호 포맷 함수
	function formatTel(tel) {
		// 10자리
		if(tel.length === 10){
			return tel.replace(/(\d{3})(\d{3})(\d{4})/,'$1-$2-$3')
		} //end if
		
		// 11자리
		if(tel.length === 11){
			return tel.replace(/(\d{3})(\d{4})(\d{4})/,'$1-$2-$3')
		} //end if
		
		return tel;
	} //formatTel
	
  const form = document.querySelector('.signup-form');

  form.addEventListener('submit', e => {
    e.preventDefault();

	const tel = document.getElementById('tel').value
	const telFormat = formatTel(tel);
	
    const payload = {
      nickname: document.getElementById('nickname').value,
      tel:      telFormat,
      zipcode:  document.getElementById('zipcode').value,
      address:
        document.getElementById('road-address').value + "/" +
        document.getElementById('detail-address').value + "/" +
        document.getElementById('extra-address').value
    };
	

	fetch('/mypage/update-info', {
	  method: 'PUT',
	  headers: {'Content-Type': 'application/json'},
	  body: JSON.stringify(payload)
	})
	.then(res => {
	  if (!res.ok) throw new Error('업데이트 요청 실패: ' + res.status);
	  return res.json();
	})
	.then(json => {
	  if (!json.success) {
	    alert('변경 실패: ' + (json.msg||'알 수 없는 오류'));
	    return;
	  }

	  // 1) 업데이트 성공 알림
	  alert('정보가 정상적으로 변경되었습니다.');
	  window.location.href = '/mypage';
	  
	  // 2) fragment만 다시 로드
	  fetch('/mypage/info-fragment')
	    .then(r => {
	      if (!r.ok) throw new Error('fragment 로드 실패: ' + r.status);
	      return r.text();
	    })
	    .then(html => {
	      document.querySelector('#info-container').innerHTML = html;
	    })
	    .catch(err => {
	      // fragment 로딩 문제는 console에만 찍고 alert는 생략
	      console.error('Fragment load error:', err);
	    });
	})
	.catch(err => {
	  // 오직 업데이트 단계에서 발생한 에러만 여기서 처리
	  console.error('Update error:', err);
	  alert('통신 중 오류가 발생했습니다.');
	});
	});
}); //addEventListener