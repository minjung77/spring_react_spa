const KakaoLogin = () => {
    const KAKAO_API_KEY = import.meta.env.VITE_APP_KAKAO_API_KEY;
    const VITE_APP_KAKAO_REDIRECT_URI = import.meta.env.VITE_APP_KAKAO_REDIRECT_URI;

    const handleKakaoLogin = () => {
        const authorizeUrl = "https://kauth.kakao.com/oauth/authorize";
        const params = `?client_id=${KAKAO_API_KEY}&redirect_uri=${VITE_APP_KAKAO_REDIRECT_URI}&response_type=code`;

        window.location.href=authorizeUrl + params;
    }

    return (
        <div className="d-flex justify-content-center py-2 gap-2">
            <img src="/image/kakao_login.png" alt="KakaoLogin"
                 onClick={handleKakaoLogin} style={{ cursor: 'pointer' }} />
        </div>
    )
}

export default KakaoLogin