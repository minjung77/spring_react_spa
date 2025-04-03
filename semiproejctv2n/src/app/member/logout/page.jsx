"use client"

import React from "react";

const Logout = () => {

    localStorage.removeItem("accessToken");

    return (
        location.href="/"
    );
}
export default Logout;