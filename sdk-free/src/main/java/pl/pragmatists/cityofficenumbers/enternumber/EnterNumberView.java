package pl.pragmatists.cityofficenumbers.enternumber;

public interface EnterNumberView {
    void setCurrentNumber(String currentNumber);

    void setQueueSize(String queueSize);

    void setUsersNumber(String usersNumber);

    void setQueueBeforeSize(String queueBefore);

    void setExpectedTime(String expectedTime);
}
