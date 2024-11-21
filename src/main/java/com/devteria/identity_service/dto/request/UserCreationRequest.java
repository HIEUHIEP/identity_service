package com.devteria.identity_service.dto.request;

import com.devteria.identity_service.exception.ErrorCode;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

//@Getter
//@Setter
@Data // @Data = @Getter + @Setter + ...
@NoArgsConstructor // Constructer no param
@AllArgsConstructor // Constructer all param
@Builder // Tao builder tuy chon n param.
@FieldDefaults(level = AccessLevel.PRIVATE) // field k khai bao access modifier thi default la PRIVATE
public class UserCreationRequest {

    @Size(min = 3, message = "USERNAME_INVALID")
    String username;

    @Size(min = 8, message = "PASSWORD_INVALID")
//    @Email()
//    @NotBlank()
//    @NotEmpty()
//    ...
    String password;
    String firstName;
    String lastName;
    LocalDate dob;

}
