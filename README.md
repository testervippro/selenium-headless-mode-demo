

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

###  1. Run this cmd by powershell 

```bash
docker run -d  -p 4444:4444 -p 5900:5900 -p 9222:9222 --shm-size="2g" -e SE_VNC_NO_PASSWORD=true  -v "${PWD}/images:/usr/src/app/images"  -v "${PWD}/videos:/usr/src/app/videos" cuxuanthoai/standalone-chrome-record-headless
```
###  2. Run the Test 

```bash
mvn -Dtest=TestRecordVideoHeadlessMode#testRecordVideoHeadlessModeInSeleniumGrid test
```

---

## 📺 Demo

👉 [Watch on YouTube](https://www.youtube.com/watch?v=6i03jWjs_54)

---

