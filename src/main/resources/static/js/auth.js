// 로그인
async function login(e) {
    e.preventDefault();
    
    const email = document.getElementById('loginEmail').value;
    const password = document.getElementById('loginPassword').value;

    try {
        const response = await fetch(`${API_BASE}/users/login`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ email, password })
        });

        if (response.ok) {
            const tokenData = await response.json();
            
            // 토큰 저장
            accessToken = tokenData.accessToken;
            localStorage.setItem('accessToken', accessToken);
            localStorage.setItem('refreshToken', tokenData.refreshToken);
            
            // 사용자 정보 조회
            const userResponse = await fetch(`${API_BASE}/users/email/${email}`, {
                headers: { 
                    'Authorization': `Bearer ${accessToken}`
                }
            });
            
            if (userResponse.ok) {
                currentUser = await userResponse.json();
                localStorage.setItem('currentUser', JSON.stringify(currentUser));
                updateUserInfo();
                showMessage('로그인 성공!');
                showTab('auctions');
            }
        } else {
            showMessage('로그인 실패: 이메일 또는 비밀번호를 확인하세요.', 'error');
        }
    } catch (error) {
        showMessage('로그인 중 오류가 발생했습니다.', 'error');
    }
}

// 회원가입
async function register(e) {
    e.preventDefault();
    
    // 라디오 버튼에서 선택된 성별 가져오기
    const genderRadio = document.querySelector('input[name="gender"]:checked');
    
    const data = {
        username: document.getElementById('regUsername').value,
        password: document.getElementById('regPassword').value,
        email: document.getElementById('regEmail').value,
        phoneNumber: document.getElementById('regPhone').value,
        birthDate: document.getElementById('regBirthDate').value,
        gender: genderRadio ? genderRadio.value : null
    };

    try {
        const response = await fetch(`${API_BASE}/users/register`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        });

        if (response.ok) {
            showMessage('회원가입 성공! 로그인해주세요.');
            e.target.reset(); // 폼 초기화
            
            // 모든 탭 숨기기
            document.querySelectorAll('.tab-content').forEach(tab => {
                tab.classList.remove('active');
                tab.style.display = 'none';
            });
            document.querySelectorAll('.tab-btn').forEach(btn => {
                btn.classList.remove('active');
            });
            
            // 로그인 탭만 표시
            document.getElementById('login').classList.add('active');
            document.getElementById('login').style.display = 'block';
            document.querySelector('#guestTabs .tab-btn:first-child').classList.add('active');
        } else {
            const error = await response.text();
            showMessage(`회원가입 실패: ${error}`, 'error');
        }
    } catch (error) {
        showMessage('회원가입 중 오류가 발생했습니다.', 'error');
    }
}

// 로그아웃
function logout() {
    currentUser = null;
    accessToken = null;
    localStorage.removeItem('currentUser');
    localStorage.removeItem('accessToken');
    localStorage.removeItem('refreshToken');
    updateUserInfo();
    showMessage('로그아웃되었습니다.');
    showTab('login');
}
