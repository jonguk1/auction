// 탭 전환
function showTab(tabName) {
    // 로그인 필요 체크
    if (!currentUser && ['auctions', 'createAuction', 'myAuctions', 'myBids'].includes(tabName)) {
        showMessage('로그인이 필요한 서비스입니다.', 'error');
        return;
    }
    
    // 모든 탭 비활성화 및 숨기기
    document.querySelectorAll('.tab-content').forEach(tab => {
        tab.classList.remove('active');
        tab.style.display = 'none';
    });
    document.querySelectorAll('.tab-btn').forEach(btn => {
        btn.classList.remove('active');
    });

    // 선택한 탭 활성화 및 표시
    const targetTab = document.getElementById(tabName);
    if (targetTab) {
        targetTab.classList.add('active');
        targetTab.style.display = 'block';
    }
    if (event && event.target) {
        event.target.classList.add('active');
    }

    // 탭별 자동 로드
    if (tabName === 'auctions') loadAuctions();
    if (tabName === 'myAuctions') loadMyAuctions();
    if (tabName === 'myBids') loadMyBids();
}

// 사용자 정보 업데이트
function updateUserInfo() {
    const guestTabs = document.getElementById('guestTabs');
    const userTabs = document.getElementById('userTabs');
    
    if (currentUser) {
        // 로그인 상태
        document.getElementById('userInfo').classList.add('active');
        document.getElementById('userName').textContent = currentUser.username;
        
        // 탭 전환
        guestTabs.style.display = 'none';
        userTabs.style.display = 'flex';
        
        // 로그인/회원가입 탭 숨기기
        document.getElementById('login').style.display = 'none';
        document.getElementById('register').style.display = 'none';
        
        // 경매 목록으로 이동
        showTab('auctions');
    } else {
        // 로그아웃 상태
        document.getElementById('userInfo').classList.remove('active');
        
        // 탭 전환
        guestTabs.style.display = 'flex';
        userTabs.style.display = 'none';
        
        // 경매 관련 탭 숨기기
        document.getElementById('auctions').style.display = 'none';
        document.getElementById('createAuction').style.display = 'none';
        document.getElementById('myAuctions').style.display = 'none';
        document.getElementById('myBids').style.display = 'none';
        
        // 로그인/회원가입 탭 표시
        document.getElementById('login').style.display = 'block';
        document.getElementById('register').style.display = 'block';
    }
}
