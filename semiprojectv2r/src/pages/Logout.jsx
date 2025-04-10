import React, {useEffect} from "react";

const Logout = () => {
    // localStorage.clear()

    const kakaoLogout = async () => {
        const response1 = await fetch('https://kapi.kakao.com/v1/user/logout', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8',
                'Authorization': `Bearer ${localStorage.getItem('accesstoken')}`,
            }
        });
        if(response1.ok){
            throw new Error(`오류발생 ${response1.statusText}`);
        }
        const data1 = response1.json();
        console.log(data1);

        const logoutUrl = 'http://kauth.kakao.com/oauth/logout';
        const params = `?client_id=${import.meta.env.VITE_APP_KAKAO_API_KEY}&logout_redirect_uri=${import.meta.env.VITE_APP_KAKAO_LOGOUT_URI}`;

        window.location.href=logoutUrl+params;
    }

    useEffect(() =>{
        const kakao = localStorage.getItem("kakao");

        if (kakao) {
            kakaoLogout();
            localStorage.removeItem("kakao");
        }
        localStorage.removeItem("accessToken");
    },[]);

    return (
        <></>
    );
}
export default Logout;