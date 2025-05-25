package com.selenium;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;

import org.apache.commons.exec.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.video.ExtractLibFromJar;

public class Main {

    public static void main(String[] args) throws InterruptedException, ExecuteException, IOException {

        ExtractLibFromJar.copyToM2Repo();

        // Run screencast in the background silently
        Thread screencastThread = new Thread(() -> {
            try {
                CommandLine cmd = CommandLine.parse("screencast -folder ./images");

                // Suppress all output
                OutputStream nullStream = OutputStream.nullOutputStream(); // Java 11+
                PumpStreamHandler silentHandler = new PumpStreamHandler(nullStream, nullStream);

                DefaultExecutor executor = new DefaultExecutor();
                executor.setStreamHandler(silentHandler);

                // Don't throw exception on non-zero exit value
                executor.setExitValues(null);

                executor.execute(cmd);
            } catch (Exception e) {

                // Optional: log or ignore
                System.err.println("Screencast process failed: " + e.getMessage());
            }
        });

        screencastThread.setDaemon(true); // So it doesn't block JVM exit
        screencastThread.start();

        // Selenium part
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-debugging-port=9222");
        options.addArguments("--user-data-dir=C:\\chrome-data");
        options.addArguments("--mute-audio");
        // options.addArguments("--headless=new");
        // test to turn offf headless mode
        // now open brower as normal

        // now it will open browers as

        WebDriver driver = new ChromeDriver(options);
        driver.get("https://www.youtube.com/watch?v=JVlDTK2-hUI");

        Thread.sleep(20_000);// sleep 20s
        driver.quit();

        String userHome = System.getProperty("user.home");

        // Build path to ffmpeg executable inside .m2 repository
        String ffmpegPath = Path.of(userHome, ".m2", "repository", "ffmpeg", "ffmpeg.exe").toString();

        // Build the command line for ffmpeg

        // // conver all image to videos
        // CommandLine cmd = CommandLine.parse(ffmpegPath)
        // .addArgument("-y") // force overwrite output files
        // .addArgument("-framerate")
        // .addArgument("35")
        // .addArgument("-i")
        // .addArgument("images/screenshot_%06d.png")
        // .addArgument("-vf")
        // .addArgument("scale=trunc(iw/2)*2:trunc(ih/2)*2", false) // false = no
        // quoting
        // .addArgument("-c:v")
        // .addArgument("libx264")
        // .addArgument("-pix_fmt")
        // .addArgument("yuv420p")
        // .addArgument("output.mp4");

        // // Execute command
        // DefaultExecutor executor = new DefaultExecutor();
        // executor.setExitValues(null); // Do not throw exception on non-zero exit
        // int exitCode = executor.execute(cmd);

        // System.out.println("Process exited with code: " + exitCode);
    }

}
