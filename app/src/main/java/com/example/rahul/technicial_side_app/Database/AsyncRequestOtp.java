package com.example.rahul.technicial_side_app.Database;

public interface AsyncRequestOtp {
    void OtpPostSuccessful(String otp);
    void otpPostFailure(String description);
}
