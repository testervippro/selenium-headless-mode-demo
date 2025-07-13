

FROM selenium/standalone-chrome:4.34.0-20250707

# Set working directory for app scripts
WORKDIR /usr/src/app

# Copy screencast binary and session watcher script with proper permissions
COPY --chmod=755 screencastlinuxv2 /usr/src/app/screencastlinuxv2

