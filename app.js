const { spawn, exec } = require("child_process");
const fs = require("fs");
const path = require("path");


// Start streaming Docker logs
const logStream = spawn("docker", ["logs", "-f", "chrome-screencast"]);

logStream.stdout.on("data", (data) => {
  const line = data.toString().trim();
 

  // === When session starts ===
if (line.includes("Session created by the Distributor")) {
  console.log(" Deleted existing contents in 'images'");
  console.log(" Detected new session — starting screencast...");

  const sessionFolder = "images";

  // Clean the folder if it exists
  if (fs.existsSync(sessionFolder)) {
    fs.rmSync(sessionFolder, { recursive: true, force: true });
    console.log(" Deleted existing contents in 'images'");
  }

  // Recreate the folder
  fs.mkdirSync(sessionFolder, { recursive: true });

  // Run screencast inside the container
  const cmd = `docker exec chrome-screencast /usr/src/app/screencastlinuxv2 --folder /usr/src/app/images`;
  exec(cmd, (err) => {
    if (err) {
      console.error(" Failed to trigger screencast:", err.message);
      return;
    }
    console.log(`📹 Screencast started in: ${sessionFolder}`);
  });
}


  // === When session ends ===
  if (line.toLowerCase().includes("stopping session") ) {
    
    console.log(" Detected session end — converting video...");

       const videosFolder = `videos`;

    // Create local folder if needed
    if (!fs.existsSync(videosFolder)) {
      fs.mkdirSync(videosFolder, { recursive: true });
    }

  const cmd = `ffmpeg -y -framerate 25 -i images/screenshot_%06d.png -vf "scale=trunc(iw/2)*2:trunc(ih/2)*2" -c:v libx264 -pix_fmt yuv420p videos/output.mp4`;

    exec(cmd, (err) => {
      if (err) {
        console.error(" Failed to convert video:", err.message);
        return;
      }
      console.log(" Converted images to videos/output.mp4");
    });
  }
});


