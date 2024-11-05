package passwordComplexcity.soumyajit.password.Controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/apis")
public class PasswordController {
    String lastpass = null;
    @GetMapping("/pass-policy")
    public ResponseEntity<List<String>> passwordPolicy(){
        List<String> policy = new ArrayList<>();
        policy.add("Minimum length of 8 characters");
        policy.add("At least one uppercase letter");
        policy.add("At least one lowercase letter");
        policy.add("At least one digit");
        policy.add("At least one special character (@, #, $, %, ^, &, +, =)");

        return ResponseEntity.ok(policy);
    }

    @PostMapping("/pass-valid")
    public ResponseEntity<List<String>> valided(@RequestParam String password){
        List<String> reponse = new ArrayList<>();
        lastpass = password;
        if(isPassword(password)) {
            reponse.add("Password meets complexity requirements");
            return ResponseEntity.ok(reponse);
        }else{
            reponse.add("Password does not meet complexity requirements");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(reponse);
        }
    }

    @GetMapping("/pass-get")
    public ResponseEntity<String> getallPass(){
        if(lastpass!=null){
            return ResponseEntity.ok("Last Submitted Password"+lastpass);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No password has been submitted yet.");
    }


    private boolean isPassword(String password){
        if(password.length() < 8) return false; // length
        if (!password.matches(".*[A-Z].*")) return false; // Uppercase letter
        if (!password.matches(".*[a-z].*")) return false; // Lowercase letter
        if (!password.matches(".*\\d.*")) return false;    // Digit
        if (!password.matches(".*[@#$%^&+=!].*")) return false; // Special character
        return true;
    }
}
