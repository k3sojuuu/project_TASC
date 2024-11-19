package com.example.user_service.service.implement;

import com.example.user_service.dao.statement.PtDao;
import com.example.user_service.model.DTO.response.MyRespon;
import com.example.user_service.model.DTO.response.RegisPtResponse;
import com.example.user_service.service.PtService;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class PtServiceImpl implements PtService {
    private final PtDao ptDao;
    private final JavaMailSender javaMailSender;
    public PtServiceImpl(PtDao ptDao, JavaMailSender javaMailSender) {
        this.ptDao = ptDao;
        this.javaMailSender = javaMailSender;
    }

    @Override
    @Transactional
    public ResponseEntity<?> rejectPt(RegisPtResponse response) {
        MyRespon myRespon = new MyRespon();
        Integer check1 = ptDao.deleteRolePt(response.getUserId());
        Integer check2 = ptDao.deleteCertification(response.getUserId());
        if (check1 > 0 && check2 > 0){
            String mess = response.getMessage();
            String email = "nightfurygoodkid@gmail.com";
            sendMail(mess,email);
            myRespon.setMessage("success");
            return ResponseEntity.status(200).body(myRespon);
        }else {
            myRespon.setMessage("fail");
            return ResponseEntity.status(400).body(myRespon);
        }
    }

    @Override
    public ResponseEntity<?> approvalPt(RegisPtResponse response) {
        MyRespon myRespon = new MyRespon();
        Integer check = ptDao.acceptsPT(response.getUserId());
        if (check > 0){
            String mess = response.getMessage();
            String email = "nightfurygoodkid@gmail.com";
            sendMail(mess,email);
            myRespon.setMessage("success");
            return ResponseEntity.status(200).body(myRespon);
        }else {
            myRespon.setMessage("fail");
            return ResponseEntity.status(400).body(myRespon);
        }
    }

    void sendMail(String msg,String email){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("MyPT notifications");
        message.setText(msg);
        javaMailSender.send(message);
    }

}
