package com.ssafy.edu.service.user;

import com.ssafy.edu.service.user.UserMailSendService;
import com.ssafy.edu.model.user.SignUpRequest;
import com.ssafy.edu.repository.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Random;

/*
* 메일 내용 바꿔주세요.
* 서버 주소 바꿔주세요.
* i4b205.p.ssafy.io:8080
* */

@Service
public class UserMailSendServiceImpl implements UserMailSendService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Override
    public void mailSendWithUserKey(SignUpRequest signUpRequest, String key) throws MailException, MessagingException{

        String email = signUpRequest.getEmailId() + "@" + signUpRequest.getEmailSite();
        String nickname = signUpRequest.getNickname();

        MimeMessage mail = javaMailSender.createMimeMessage();
        String htmlStr = "<h2>안녕하세요 대전 B205팀입니다!</h2><br><br>"
                + "<h3>" + nickname + "님</h3>" + "<p>인증하기 버튼을 누르시면 로그인을 하실 수 있습니다 : "
                + "<a href='http://localhost:8080" // 우리의 서버 주소
                // + 우리의 contextPath
                + "/users/email_auth?email="+ email +"&key="+key+"'>인증하기</a></p>"
                + "(혹시 잘못 전달된 메일이라면 이 이메일을 무시하셔도 됩니다)";
        try {
            mail.setSubject("[본인인증] : 대전 B205팀에서 도착한 인증메일입니다.", "utf-8");
            mail.setText(htmlStr, "utf-8", "html");
            mail.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(email));
            javaMailSender.send(mail);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void sendTempPassword(String email, String key) throws MessagingException, MailException{

        MimeMessage mail = javaMailSender.createMimeMessage();
        String htmlStr = "<h2>안녕하세요 대전 B205팀입니다!</h2><br><br>"
                + "<h3>" + email + "님</h3>" + "<p>발급된 임시 비밀번호입니다.</p>"
                + key
                + "(혹시 잘못 전달된 메일이라면 이 이메일을 무시하셔도 됩니다)";
        try {
            mail.setSubject("[임시비밀번호] : 대전 B205팀에서 도착한 임시비밀번호입니다.", "utf-8");
            mail.setText(htmlStr, "utf-8", "html");
            mail.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(email));
            javaMailSender.send(mail);
        } catch (MessagingException | MailSendException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void mailSendExistUser(String email, String nickname) throws MessagingException, MailException{

        MimeMessage mail = javaMailSender.createMimeMessage();
        String htmlStr = "<h2>안녕하세요 대전 B205팀입니다!</h2><br><br>"
                + "<h3>" + nickname + "님</h3>" + "이미 가입된 이메일 주소입니다. "
                + "로그인 하러가기"; // 우리의 홈페이지 주소 넣기
        try {
            mail.setSubject("[본인인증] : 대전 B205팀에서 도착한 메일입니다.", "utf-8");
            mail.setText(htmlStr, "utf-8", "html");
            mail.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(email));
            javaMailSender.send(mail);
        } catch (MessagingException | MailSendException e) {
            e.printStackTrace();
        }

    }

    /* 난수 이용한 키 생성 */
    private boolean lowerCheck;
    private int size;

    @Override
    public String getKey(boolean lowerCheck, int size){
        this.lowerCheck = lowerCheck;
        this.size = size;
        return init();
    }

    /* 이메일 난수 만드는 메소드 */
    private String init(){

        Random ran = new Random();
        StringBuffer sb = new StringBuffer();
        int num = 0;

        do {
            num = ran.nextInt(75) + 48;
            if ((num >= 48 && num <= 57)
                    || (num >= 65 && num <= 90)
                    || (num >= 97 && num <= 122)) {
                sb.append((char) num);
            } else {
                continue;
            }
        } while (sb.length() < size);

        if (lowerCheck) {
            return sb.toString().toLowerCase();
        }
        return sb.toString();

    }




}
