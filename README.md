## Configuration

This project uses several properties files located in the `src/main/resources` directory to manage environment-specific settings. Ensure these are configured correctly before running the application.

---

### **Admin Settings** (`admin.properties`)
Configures high-level administrative limits for the panel.
* `admin.max-count`: The maximum number of administrator accounts allowed.

```properties
admin.max-count=1
```

### **Database Configuration** (`datasource.properties`)
Settings for connecting to the MySQL database instance.

```properties
spring.datasource.url=jdbc:mysql://host:port/database_name
spring.datasource.username=user_name
spring.datasource.password=password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

### **Email Service** (`email.properties`)
Credentials and routing for system notifications.
* `spring.mail.username`: The authentication token or username for the SMTP server.
* `spring.mail.recipient`: The default address to receive system-generated emails.

```properties
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=sender@gmail.com
spring.mail.recipient=recipient@gmail.com
spring.mail.password=password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true

```

### **JWT Security** (`jwt.properties`)
Configuration for JSON Web Token authentication.
* `jwt.token.issuer`: The identifier for the server issuing the tokens.
* `jwt.token.expirationTimeMillis`: Token validity period (default is **3600000** ms or 1 hour).
* `jwt.secret.key`: The signing key used for token validation.

```properties
jwt.token.issuer=MobilePartsShop
jwt.token.expirationTimeMillis=360000
jwt.secret.key=key_hash
```

### **RSA Encryption** (`rsa.properties`)
Parameters for asymmetric encryption and secure data handling. Use https://travistidwell.com/jsencrypt/demo/ to encode keys
```properties
rsa.cipher=RSA/ECB/PKCS1Padding
rsa.key.algorithm=RSA
rsa.key.size=2048
rsa.private.key=base64_encoded_private_key
rsa.public.key=base64_encoded_public_key
```
