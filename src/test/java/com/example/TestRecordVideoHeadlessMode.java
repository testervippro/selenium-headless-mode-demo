package com.example;

import org.apache.commons.exec.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import static com.selenium.Main.executeCMDInSilentMode;
import java.io.OutputStream;
import java.nio.file.Path;

public class TestRecordVideoHeadlessMode {

    // Should close current chrome to improve performance capture
    @Test()
    public void testRecordVideoHeadlessModeWindow() throws Exception {

        // // Step 1: Start background screencast (non-blocking, silent)
        Thread screencastThread = new Thread(() -> {

            executeCMDInSilentMode("screencast -folder ./images");

        });

        screencastThread.setDaemon(true);
        screencastThread.start();

        // 3. Setup Selenium in headless mode
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-debugging-port=9222");
        // options.addArguments("--user-data-dir=C:\\chrome-data");
        options.addArguments("--mute-audio");
        options.addArguments("--headless=new");

        // Step 4: Launch browser
        WebDriver driver = new ChromeDriver(options);
        driver.get("https://www.youtube.com/watch?v=JVlDTK2-hUI");
        Thread.sleep(3_000);
        // force play video
        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.SPACE).perform(); // Toggle play/pause

        Thread.sleep(20_000); // Watch video for 20 seconds
        driver.quit();

        // Hard code to convert
        String cmdConvert = "ffmpeg -y -framerate 25 -i images\\screenshot_%06d.png -vf scale=trunc(iw/2)*2:trunc(ih/2)*2 -c:v libx264 -pix_fmt yuv420p output.mp4\r\n"
                + //
                "";

        executeCMDInSilentMode(cmdConvert);

    }

    @Test()
    public void testRecordVideoHeadlessModeLinux() throws Exception {

        // Start background screencast (non-blocking, silent)
        Thread screencastThread = new Thread(() -> {
            executeCMDInSilentMode("chmod +x ./screencastlinux");

            executeCMDInSilentMode("./screencastlinux -folder ./images");

        });

        screencastThread.setDaemon(true);
        screencastThread.start();

        // Setup Selenium in headless mode
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-debugging-port=9222");
        // options.addArguments("--user-data-dir=C:\\chrome-data");
        options.addArguments("--mute-audio");
        options.addArguments("--headless=new");

        // Launch browser
        WebDriver driver = new ChromeDriver(options);
        driver.get("https://www.youtube.com/watch?v=JVlDTK2-hUI");

        Thread.sleep(3_000);
        // force play video
        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.SPACE).perform(); // Toggle play/pause

        Thread.sleep(20_000); // Watch video for 20 seconds
        driver.quit();

        // Hard code to convert
        // Hardcoded FFmpeg command for Linux
        String cmdConvert = "ffmpeg -y -framerate 25 -i images/screenshot_%06d.png "
                + "-vf scale=trunc(iw/2)*2:trunc(ih/2)*2 -c:v libx264 -pix_fmt yuv420p output.mp4";

        executeCMDInSilentMode(cmdConvert);

    }

    @Test()
    public void testRecordVideoHeadlessModeMac() throws Exception {

        // Start background screencast (non-blocking, silent)
        Thread screencastThread = new Thread(() -> {
            executeCMDInSilentMode("chmod +x ./screencastMacIntel");

            executeCMDInSilentMode("./screencastMacIntel -folder ./images");

        });

        screencastThread.setDaemon(true);
        screencastThread.start();

        // Setup Selenium in headless mode
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-debugging-port=9222");
        // options.addArguments("--user-data-dir=C:\\chrome-data");
        options.addArguments("--mute-audio");
        options.addArguments("--headless=new");

        // Launch browser
        WebDriver driver = new ChromeDriver(options);
        driver.get("https://www.youtube.com/watch?v=JVlDTK2-hUI");

        Thread.sleep(3_000);
        // force play video
        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.SPACE).perform(); // Toggle play/pause

        Thread.sleep(20_000); // Watch video for 20 seconds
        driver.quit();

        // Hard code to convert
        // Hardcoded FFmpeg command for Linux
        String cmdConvert = "ffmpeg -y -framerate 25 -i images/screenshot_%06d.png "
                + "-vf scale=trunc(iw/2)*2:trunc(ih/2)*2 -c:v libx264 -pix_fmt yuv420p output.mp4";

        executeCMDInSilentMode(cmdConvert);

    }

}
