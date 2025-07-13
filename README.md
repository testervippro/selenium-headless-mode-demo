

# 🧪 Run Specific Test Methods with Maven

## ✅ On Windows

```bash
mvn -Dtest=TestRecordVideoHeadlessMode#testRecordVideoHeadlessModeWindow test
```

## ✅ On Linux

```bash
mvn -Dtest=TestRecordVideoHeadlessMode#testRecordVideoHeadlessModeLinux test
```

## ✅ On macOS

```bash
mvn -Dtest=TestRecordVideoHeadlessMode#testRecordVideoHeadlessModeMac test
```

---

## 🧰 Run on Selenium Grid

### 🏗️ 1. Build Docker Image

```bash
docker build -t chrome-screencast .
```

---

### 🐳 2. Run Container

#### ▶️ On Windows PowerShell

```powershell
docker run -d --name chrome-screencast `
  -p 4444:4444 -p 5900:5900 -p 9222:9222 `
  --shm-size="2g" `
  -e SE_VNC_NO_PASSWORD=true `
  -v "${PWD}/images:/usr/src/app/images" `
  chrome-screencast
```

#### ▶️ On macOS / Linux (bash)

```bash
docker run -d --name chrome-screencast \
  -p 4444:4444 -p 5900:5900 -p 9222:9222 \
  --shm-size="2g" \
  -e SE_VNC_NO_PASSWORD=true \
  -v "$(pwd)/images:/usr/src/app/images" \
  chrome-screencast
```

---

### 🟢 3. Start `Node app.js`

Run this Node script to:

* Trigger screencast capture inside the container.
* Automatically detect when a Selenium session starts and ends (using `docker logs` internally).

```bash
node app.js
```

---

### 🧪 4. Run the Test (Example for macOS)

```bash
mvn -Dtest=TestRecordVideoHeadlessMode#testRecordVideoHeadlessModeInSeleniumGrid test
```

---

## 📺 Demo

👉 [Watch on YouTube](https://www.youtube.com/watch?v=6i03jWjs_54)

---

