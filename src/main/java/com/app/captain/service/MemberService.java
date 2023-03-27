package com.app.captain.service;

import com.app.captain.domain.dao.MemberDAO;
import com.app.captain.domain.vo.MemberVO;
import com.app.captain.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberDAO memberDAO;

    /* 로그인 */
    public MemberVO getMember(MemberVO memberVO) {
        return memberDAO.findById(memberVO);
    }

    /* 회원 가입 */
    public void setMember(MemberVO memberVO) {
        memberDAO.save(memberVO);
    }

    public int checkId(String memberIdentification) {
        return memberDAO.checkId(memberIdentification);
    }

    public int checkPhone(String memberPhone) {
        return memberDAO.checkPhone(memberPhone);
    }

    public int checkNickname(String memberNickname) {
        return memberDAO.checkNickname(memberNickname);
    }

    public void checkSms(String memberPhone, String code) {

        String api_key = "NCSF8LRG4LDF323M";
        String api_secret = "UGS24OKHX6YCPEFTFUK7WBZFEFD35UCD";
        Message coolsms = new Message(api_key, api_secret);

        // 4 params(to, from, type, text) are mandatory. must be filled
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("to", memberPhone);    // 수신전화번호
        params.put("from", "01012341234");    // 발신전화번호. 테스트시에는 발신,수신 둘다 본인 번호로 하면 됨
        params.put("type", "SMS");
        params.put("text", "인증번호는" + "[" + code + "]" + "입니다.");
        params.put("app_version", "test app 1.2"); // application name and version

        try {
            JSONObject obj = (JSONObject) coolsms.send(params);
            System.out.println(obj.toString());
        } catch (CoolsmsException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCode());
        }

    }
}
