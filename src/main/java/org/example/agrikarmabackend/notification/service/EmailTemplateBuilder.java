package org.example.agrikarmabackend.notification.service;

import org.springframework.stereotype.Component;

@Component
public class EmailTemplateBuilder {

    public String buildTemplate(String title, String message, String status) {

        return """
                <html>
                <body style="font-family: Arial, sans-serif; background-color: #f4f6f8; padding: 20px;">
                    
                    <div style="max-width: 600px; margin: auto; background: white; border-radius: 10px; padding: 20px;">
                        
                        <h2 style="color: #2c3e50;">%s</h2>
                        
                        <p style="font-size: 16px; color: #555;">
                            %s
                        </p>

                        <div style="margin-top: 20px; padding: 10px; background-color: #ecf0f1; border-radius: 5px;">
                            <strong>Status:</strong> %s
                        </div>

                        <hr style="margin: 20px 0;"/>

                        <p style="font-size: 12px; color: #999;">
                            AgriKarma • Connecting Farmers & Buyers
                        </p>

                    </div>

                </body>
                </html>
                """.formatted(title, message, status);
    }
}
