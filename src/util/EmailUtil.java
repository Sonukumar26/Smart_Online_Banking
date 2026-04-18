package util;

import java.io.FileInputStream;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailUtil {

    private static String FROM_EMAIL;
    private static String APP_PASSWORD;

    static {
        try {
            Properties p = new Properties();
            p.load(new FileInputStream(
                "C:/apache-tomcat-9.0.107/webapps/SmartOnlineBanking/WEB-INF/config/email.properties"
            ));

            FROM_EMAIL = p.getProperty("mail.from");
            APP_PASSWORD = p.getProperty("mail.password");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ===============================
    // SIMPLE EMAIL (OTP / ALERT)
    // ===============================
    public static boolean sendSimpleEmail(String to, String subject, String body) {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session mailSession = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(FROM_EMAIL, APP_PASSWORD);
                    }
                });

            Message message = new MimeMessage(mailSession);
            message.setFrom(new InternetAddress(FROM_EMAIL));
            message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(to)
            );
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ===============================
    // EMAIL WITH ATTACHMENT
    // ===============================
    public static void sendEmailWithAttachment(
            String to,
            String subject,
            String body,
            String filePath
    ) throws Exception {

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session mailSession = Session.getInstance(props,
            new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(FROM_EMAIL, APP_PASSWORD);
                }
            });

        Message message = new MimeMessage(mailSession);
        message.setFrom(new InternetAddress(FROM_EMAIL));
        message.setRecipients(
            Message.RecipientType.TO,
            InternetAddress.parse(to)
        );
        message.setSubject(subject);

        MimeBodyPart textPart = new MimeBodyPart();
        textPart.setText(body);

        MimeBodyPart attachmentPart = new MimeBodyPart();
        attachmentPart.attachFile(filePath);

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(textPart);
        multipart.addBodyPart(attachmentPart);

        message.setContent(multipart);
        Transport.send(message);
    }
}
