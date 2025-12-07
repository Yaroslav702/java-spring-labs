package yaroslav.max.currencyexchangeapp.config;

import java.util.UUID;

public class SessionManager {
    private final String sessionId = UUID.randomUUID().toString();

    public void printSession() {
        System.out.println("Session ID (Prototype test): " + sessionId);
    }
}
