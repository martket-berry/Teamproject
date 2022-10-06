package com.example.project.Service.Member;

import com.example.project.Entity.Member.Member;
import org.springframework.validation.Errors;

import java.util.List;
import java.util.Map;

public interface MemberService {


    void insertMember(Member member);

    void updateMember(Member member);

    public Map<String, String> member_Availability(Errors errors);

    void deleteMember(Member member);

    public Member getMember(Member member);

    List<Member> getMemberList();

    //아이디 중복확인할 때 쓰는 것.
    Member getMemberWhereId(String id);



}
