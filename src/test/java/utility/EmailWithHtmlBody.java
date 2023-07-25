package utility;

 

import java.util.Date;
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

 

import helper.WebHelper;
import services.WebServices;

 

public class EmailWithHtmlBody {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.office365.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.debug", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("comtestautomation@philips.com", "Bangalore#57");
            }
        });

 

        try {
            String toRecepients = "partner.anush.ky@philips.com";
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("comtestautomation@philips.com"));
            msg.setRecipients(Message.RecipientType.TO, toRecepients);
            msg.setSubject("Cosolidated Mail Report");
            msg.setSentDate(new Date());

 

            Multipart multipart = new MimeMultipart();

 

            MimeBodyPart htmlPart = new MimeBodyPart();
            String htmlContent = "<table style=\"width: 100%; height: 50px; background-color: rgb(204, 220, 246); border-collapse: collapse; border: 1px solid rgb(9, 1, 121);\">\r\n"
                    + "    <tbody>\r\n" + "        <tr>\r\n"
                    + "            <td style=\"width: 100%; background-color: rgb(123, 164, 232); border: 1px solid rgb(9, 1, 121);\">\r\n"
                    + "                <div style=\"text-align: center;\"><span style=\"font-size: 26px;\">D2C Experience [.com &amp; Onesite] Automation Execution Report</span></div>\r\n"
                    + "            </td>\r\n" + "        </tr>\r\n" + "        <tr>\r\n"
                    + "            <td style=\"width: 100%; background-color: rgb(255, 255, 255); border: 1px solid rgb(9, 1, 121);\">\r\n"
                    + "                <p>Hi Team,</p>\r\n"
                    + "                <p>RT execution completed for current release<br>Please find the below details</p>\r\n"
                    + "                <table style=\"width: 500px; border-collapse: separate; border: 1px solid rgb(0, 0, 0);\">\r\n"
                    + "                    <thead>\r\n" + "                        <tr>\r\n"
                    + "                            <th style=\"width: 19.9301%; border: 1px solid rgb(0, 0, 0); background-color: rgb(204, 220, 246);\">\r\n"
                    + "                                <div style=\"text-align: left;\">Browser</div>\r\n"
                    + "                            </th>\r\n"
                    + "                            <th style=\"width: 20%; border: 1px solid rgb(0, 0, 0); background-color: rgb(204, 220, 246);\">Pass #</th>\r\n"
                    + "                            <th style=\"border: 1px solid rgb(0, 0, 0); background-color: rgb(204, 220, 246); width: 20%;\">Fail #</th>\r\n"
                    + "                            <th style=\"width: 20%; border: 1px solid rgb(0, 0, 0); background-color: rgb(204, 220, 246);\">Not Run #</th>\r\n"
                    + "                            <th style=\"width: 20%; border: 1px solid rgb(0, 0, 0); background-color: rgb(204, 220, 246);\">Pass %</th>\r\n"
                    + "                        </tr>\r\n" + "                    </thead>\r\n"
                    + "                    <tbody>\r\n" + "                        <tr>\r\n"
                    + "                            <td style=\"width: 19.9301%; border: 1px solid rgb(0, 0, 0); background-color: rgb(204, 220, 246);\"><strong>Chrome</strong></td>\r\n"
                    + "                            <td style=\"width: 20%; border: 1px solid rgb(0, 0, 0);\"><br></td>\r\n"
                    + "                            <td style=\"width: 20%; border: 1px solid rgb(0, 0, 0);\"><br></td>\r\n"
                    + "                            <td style=\"width: 20%; border: 1px solid rgb(0, 0, 0);\"><br></td>\r\n"
                    + "                            <td style=\"width: 20%; border: 1px solid rgb(0, 0, 0);\"><br></td>\r\n"
                    + "                        </tr>\r\n" + "                        <tr>\r\n"
                    + "                            <td style=\"width: 19.9301%; border: 1px solid rgb(0, 0, 0); background-color: rgb(204, 220, 246);\"><strong>Edge</strong></td>\r\n"
                    + "                            <td style=\"width: 20%; border: 1px solid rgb(0, 0, 0);\"><br></td>\r\n"
                    + "                            <td style=\"width: 20%; border: 1px solid rgb(0, 0, 0);\"><br></td>\r\n"
                    + "                            <td style=\"width: 20%; border: 1px solid rgb(0, 0, 0);\"><br></td>\r\n"
                    + "                            <td style=\"width: 20%; border: 1px solid rgb(0, 0, 0);\"><br></td>\r\n"
                    + "                        </tr>\r\n" + "                        <tr>\r\n"
                    + "                            <td style=\"width: 19.9301%; border: 1px solid rgb(0, 0, 0); background-color: rgb(204, 220, 246);\"><strong>Firefox</strong></td>\r\n"
                    + "                            <td style=\"width: 20%; border: 1px solid rgb(0, 0, 0);\"><br></td>\r\n"
                    + "                            <td style=\"width: 20%; border: 1px solid rgb(0, 0, 0);\"><br></td>\r\n"
                    + "                            <td style=\"width: 20%; border: 1px solid rgb(0, 0, 0);\"><br></td>\r\n"
                    + "                            <td style=\"width: 20%; border: 1px solid rgb(0, 0, 0);\"><br></td>\r\n"
                    + "                        </tr>\r\n" + "                    </tbody>\r\n"
                    + "                </table>\r\n" + "                <p><strong>Thanks</strong><br>QA Team</p>\r\n"
                    + "                <p>Note: This is an auto generated email, please don&apos;t reply</p>\r\n"
                    + "            </td>\r\n" + "        </tr>\r\n" + "    </tbody>\r\n" + "</table>";
            htmlPart.setContent(htmlContent, "text/html");
            multipart.addBodyPart(htmlPart);

 

            // MimeBodyPart attachementPart = new MimeBodyPart();
            // attachementPart.attachFile(new File("D:/cp/pic.jpg"));
            // multipart.addBodyPart(attachementPart);

 

            msg.setContent(multipart);
            Transport.send(msg);
            System.out.println("---Done---");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static void sendReportEmailForBrokenURLs() {
        if(Utility.getUrlStatusList().size() < 1) {
            return;
        }

 

        String env = WebHelper.getValue(WebServices.ENV);
        String browser = WebHelper.getValue(WebServices.BROWSER);
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.office365.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.debug", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("comtestautomation@philips.com", "Bangalore#57");
            }
        });

 

        try {
            String toRecepients = "partner.pramod.acharya@philips.com,partner.anush.ky@philips.com";
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("comtestautomation@philips.com"));
            msg.setRecipients(Message.RecipientType.TO, toRecepients);
            if(env.equalsIgnoreCase("acc")){
               if(browser.equalsIgnoreCase("chrome")){
                    msg.setRecipients(Message.RecipientType.CC, "Anita.Kumar@philips.com");
                }
            }
            else
            {
                if(browser.equalsIgnoreCase("chrome")){
                    msg.setRecipients(Message.RecipientType.CC, "partner.anush.ky@philips.com");
                }
            }
            msg.setSubject("Component Regression - Broken URL Report - " + browser + " - " + env);
            msg.setSentDate(new Date());

 

            Multipart multipart = new MimeMultipart();
            MimeBodyPart htmlPart = new MimeBodyPart();

            StringBuilder tableRows = new StringBuilder();
            for(String urlStatus : Utility.getUrlStatusList()) {
                tableRows.append("                        <tr>\r\n")
                         .append("                            <td style=\"border: 1px solid rgb(0, 0, 0); background-color: rgb(204, 220, 246);\"><strong>" + urlStatus.split("@")[0] + "</strong></td>\r\n")
                         .append("                            <td style=\"border: 1px solid rgb(0, 0, 0);\">" + urlStatus.split("@")[1] + "</td>\r\n")
                         .append("                        </tr>\r\n");
            }

 

            String htmlContent = "<table style=\"width: 100%; height: 50px; background-color: rgb(204, 220, 246); border-collapse: collapse; border: 1px solid rgb(9, 1, 121);\">\r\n"
                    + "    <tbody>\r\n" + "        <tr>\r\n"
                    + "            <td style=\"width: 100%; background-color: rgb(123, 164, 232); border: 1px solid rgb(9, 1, 121);\">\r\n"
                    + "                <div style=\"text-align: center;\"><span style=\"font-size: 26px;\">D2C Experience Broken URL Report After Automation Execution on " + browser + " - " + env +"</span></div>\r\n"
                    + "            </td>\r\n" + "        </tr>\r\n" + "        <tr>\r\n"
                    + "            <td style=\"width: 100%; background-color: rgb(255, 255, 255); border: 1px solid rgb(9, 1, 121);\">\r\n"
                    + "                <p>Hi Team,</p>\r\n"
                    + "                <p>UI Test execution is completed on browser:" + browser + " in " + env + " environment.<br>Please find the tests skipped due to their broken urls with reasons.</p>\r\n"
                    + "                <table style=\"border-collapse: separate; border: 1px solid rgb(0, 0, 0);\">\r\n"
                    + "                    <thead>\r\n"
                    + "                        <tr>\r\n"
                    + "                            <th style=\"width: auto; border: 1px solid rgb(0, 0, 0); background-color: rgb(204, 220, 246);\">\r\n"
                    + "                                <div style=\"text-align: left;\">Scenario Name</div>\r\n"
                    + "                            </th>\r\n"
                    + "                            <th style=\"width: auto; border: 1px solid rgb(0, 0, 0); background-color: rgb(204, 220, 246);\"><div style=\"text-align: left;\">Reason for Broken URL</div></th>\r\n"
                    + "                        </tr>\r\n"
                    + "                    </thead>\r\n"
                    + "                    <tbody>\r\n"
                    +                          tableRows.toString()
                    + "                    </tbody>\r\n"
                    + "                </table>\r\n"
                    + "                <p><strong>Thanks</strong><br>QA Team</p>\r\n"
                    + "                <p>Note: This is an auto generated email, please don&apos;t reply</p>\r\n"
                    + "            </td>\r\n"
                    + "        </tr>\r\n"
                    + "    </tbody>\r\n"
                    + "</table>";
            htmlPart.setContent(htmlContent, "text/html");
            multipart.addBodyPart(htmlPart);

 

            msg.setContent(multipart);
            Transport.send(msg);
            System.out.println("---Done---");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}