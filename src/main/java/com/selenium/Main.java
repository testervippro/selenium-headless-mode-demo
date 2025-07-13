package com.selenium;

import java.io.IOException;
import java.io.OutputStream;
import org.apache.commons.exec.*;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

public class Main {

    public static void main(String[] args) throws InterruptedException, ExecuteException, IOException {

        // // Step 1: Start background screencast (non-blocking, silent)
        Thread screencastThread = new Thread(() -> {

            executeCMDInSilentMode("screencast -folder ./images");

        });

        screencastThread.setDaemon(true); // Doesn’t block JVM exit
        screencastThread.start();

        // Step 3: Configure ChromeOptions
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
        String cmdConvert = "ffmpeg -y -framerate 35 -i images\\screenshot_%06d.png -vf scale=trunc(iw/2)*2:trunc(ih/2)*2 -c:v libx264 -pix_fmt yuv420p output.mp4\r\n"
                + //
                "";

        executeCMDInSilentMode(cmdConvert);

    }

    public static void executeCMDInSilentMode(String command) {
        try {
            CommandLine cmd = CommandLine.parse(command);

            OutputStream nullStream = OutputStream.nullOutputStream(); // Java 11+
            PumpStreamHandler silentHandler = new PumpStreamHandler(nullStream, nullStream);

            DefaultExecutor executor = new DefaultExecutor();
            executor.setStreamHandler(silentHandler);
            executor.setExitValues(null); // Don’t throw exception on non-zero exit

            executor.execute(cmd);

        } catch (Exception e) {
            e.printStackTrace(); // Or log it instead
        }
    }

}
