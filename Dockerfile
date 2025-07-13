# # Base image with JDK 17
# FROM openjdk:17-slim

# # Metadata
# LABEL maintainer="your_email@example.com"
# LABEL purpose="Chrome Headless + Screencast + JDK17 + Maven"

# # Install required dependencies
# RUN apt-get update && apt-get install -y \
#     wget curl gnupg unzip git ca-certificates \
#     libasound2 libatk-bridge2.0-0 libatk1.0-0 libcups2 \
#     libdbus-1-3 libgdk-pixbuf2.0-0 libnspr4 libnss3 \
#     libx11-xcb1 libxcomposite1 libxdamage1 libxrandr2 \
#     libappindicator3-1 xdg-utils fonts-liberation \
#     --no-install-recommends && \
#     apt-get clean && rm -rf /var/lib/apt/lists/*

# # Install Google Chrome
# RUN wget -q -O chrome.deb https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb && \
#     dpkg -i chrome.deb || apt-get -fy install && \
#     rm chrome.deb

# # Install Maven
# ENV MAVEN_VERSION=3.9.6
# RUN wget https://downloads.apache.org/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.tar.gz && \
#     tar xzf apache-maven-${MAVEN_VERSION}-bin.tar.gz -C /opt && \
#     ln -s /opt/apache-maven-${MAVEN_VERSION}/bin/mvn /usr/bin/mvn && \
#     rm apache-maven-${MAVEN_VERSION}-bin.tar.gz
# ENV MAVEN_HOME=/opt/apache-maven-${MAVEN_VERSION}
# ENV PATH=$MAVEN_HOME/bin:$PATH

# # Set working directory
# WORKDIR /usr/src/app

# # Copy screencast binary to container
# COPY screencastlinux ./screencastlinux
# RUN chmod +x ./screencastlinux

# # Optional: expose Chrome debug port
# EXPOSE 9222

# # Run both Chrome and screencastlinux when container starts
# CMD google-chrome --headless=new \
#      --remote-debugging-port=9222 
