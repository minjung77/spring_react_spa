package com.example.zzyzzy.semiprojectv2.service;

import com.example.zzyzzy.semiprojectv2.domain.Member;
import com.example.zzyzzy.semiprojectv2.domain.MemberDTO;

public interface MemberService {

    boolean newMember(MemberDTO member);

    Member loginMember(MemberDTO member);
}
