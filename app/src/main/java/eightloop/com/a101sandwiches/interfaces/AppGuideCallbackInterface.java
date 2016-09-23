package eightloop.com.a101sandwiches.interfaces;

/**
 * Manages the events triggered in App Guide Fragments
 */

public interface AppGuideCallbackInterface {

    void nextCalled(int guideID);
    void skipCalled(int guideID);
}
