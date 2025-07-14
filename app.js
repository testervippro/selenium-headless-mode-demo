const { spawn, exec } = require("child_process");
const fs = require("fs/promises");

// Folder and container names
const IMAGES = "images";
const VIDEOS = "videos";
const CONTAINER = "chrome-screencast";
const screencastCmd = `docker exec ${CONTAINER} /usr/src/app/screencastlinuxv2 --folder /usr/src/app/images`;
const ffmpegCmd = `ffmpeg -y -framerate 25 -i ${IMAGES}/screenshot_%06d.png \
-vf "scale=trunc(iw/2)*2:trunc(ih/2)*2" -c:v libx264 -pix_fmt yuv420p ${VIDEOS}/output.mp4`;


// Helper to run shell commands and return output or error
const run = (cmd) =>
  new Promise((resolve, reject) => {
    exec(cmd, (err, stdout, stderr) => {
      if (err) reject(stderr || err);
      else resolve(stdout);
    });
  });

// Helper to clean and recreate a folder
const reset = async (folder) => {
  try {
    await fs.rm(folder, { recursive: true, force: true });
  } catch (_) {}
  await fs.mkdir(folder, { recursive: true });
};



// Listen to Docker container logs
const logStream = spawn("docker", ["logs", "-f", CONTAINER]);

logStream.stdout.on("data", async (data) => {
  const line = data.toString();

  // === Start screencast when session is created ===
  if (line.includes("Session created by the Distributor")) {
    console.log("New session — resetting folder & starting screencast");
    await reset(IMAGES);
    try {
      await run(screencastCmd);
    } catch (e) {
      console.error(" Screencast error:", e);
    }
  }

  // === Convert screenshots to video when session ends ===
  if (line.toLowerCase().includes("stopping session")) {
    console.log(" Session ended — converting to video...");

    await fs.mkdir(VIDEOS, { recursive: true });

    try {
      await run(ffmpegCmd);
      console.log(" Video saved to:", `${VIDEOS}/output.mp4`);
    } catch (e) {
      console.error(" FFmpeg error:", e);
    }
  }
});
