package com.example.carappketan.adapter;

import android.app.Application;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.zegocloud.uikit.prebuilt.call.ZegoUIKitPrebuiltCallService;
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationConfig;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        // Check if the message contains data payload
        if (remoteMessage.getData().size() > 0) {
            // Handle data payload, e.g., check if it's a call notification
            if (remoteMessage.getData().containsKey("call_notification")) {
                // Extract call information from data payload and handle the incoming call
                handleIncomingCall(remoteMessage.getData());
            }
        }

        // Check if the message contains notification payload
        if (remoteMessage.getNotification() != null) {
            // Handle notification payload
            // Show notification to the user
            showNotification(remoteMessage.getNotification());
        }
    }

    private void handleIncomingCall(Map<String, String> data) {
        // Extract call information and start an activity or service to handle the call
        // You may need to use ZegoUIKitPrebuiltCallService or your custom call handling logic here

        // Get user ID from data payload
        String userId = data.get("userId");

        // Start the call service with the provided user ID
        startService(userId);
    }

    private void showNotification(RemoteMessage.Notification notification) {
        // Build and display notification to the user
        // You can customize the notification content and actions according to your requirements
    }

    private void startService(String userId1){
        Application application = getApplication(); // Android's application context
        long appID = 1708811769;   // yourAppID
        String appSign ="6679eefc4d002d9e7dad772774b6617d8c0bc50f7013aa2631b5461890bc247c";  // yourAppSign
        String userID =userId1; // yourUserID, userID should only contain numbers, English characters, and '_'.
        String userName =userId1;   // yourUserName

        ZegoUIKitPrebuiltCallInvitationConfig callInvitationConfig = new ZegoUIKitPrebuiltCallInvitationConfig();

        ZegoUIKitPrebuiltCallService.init(getApplication(), appID, appSign, userID, userName, callInvitationConfig);
    }
}
