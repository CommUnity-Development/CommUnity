package com.development.community;

import Notification.MyResponse;
import Notification.Sender;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface APIService {
    @Header(
            {"Content-Type:application/json",
                    "Authorization:key=AAAAy9b-wz8:APA91bHaRb9GoLUv4ZsofPQX4l4HaUg5vH81ArDFRw-4kOO0xJjIeKX0r_TyibW2PuHCgkufxLbE_Os0794hNi9mKjt4TJZ0DJzOe-9uwEV49Tyi3Zc5SETdHioF6XuyB2JWoxLgDRPL"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
