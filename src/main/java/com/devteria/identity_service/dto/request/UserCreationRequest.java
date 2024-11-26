package com.devteria.identity_service.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.Size;

import com.devteria.identity_service.validator.DobContraint;

import lombok.*;
import lombok.experimental.FieldDefaults;

// @Getter
// @Setter
@Data // @Data = @Getter + @Setter + ...
@NoArgsConstructor // Constructer no param
@AllArgsConstructor // Constructer all param
@Builder // Tao builder tuy chon n param.
@FieldDefaults(level = AccessLevel.PRIVATE) // field k khai bao access modifier thi default la PRIVATE
public class UserCreationRequest {

    @Size(min = 4, message = "USERNAME_INVALID")
    String username;

    @Size(min = 8, message = "PASSWORD_INVALID")
    //    @Email()
    //    @NotBlank()
    //    @NotEmpty()
    //    ...
    String password;

    String firstName;
    String lastName;

    @DobContraint(min = 18, message = "DOB_INVALID") // Custom annotation
    LocalDate dob;
}
