package com.Sanket.BlogApplication.utilities.EmailService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailDetails {
        private String recipient;
        private String msgBody;
        private String subject;
}

