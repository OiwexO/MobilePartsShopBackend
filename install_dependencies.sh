#!/bin/bash

# Function to read user input with a prompt
read_input() {
    local prompt=$1
    local input
    read -p "$prompt: " input
    echo $input
}

# Update package list and upgrade all packages
sudo apt update
sudo apt upgrade -y

# Install Java 17
sudo apt install -y openjdk-17-jdk

# Verify Java installation
java -version

# Add MySQL APT repository
wget https://dev.mysql.com/get/mysql-apt-config_0.8.36-1_all.deb
sudo dpkg -i mysql-apt-config_0.8.36-1_all.deb

# Update package list again after adding MySQL APT repository
sudo apt update

# Install MySQL Server 8
sudo apt install -y mysql-server

# Start MySQL service and enable it to start on boot
sudo systemctl start mysql
sudo systemctl enable mysql

# Secure MySQL installation (you will be prompted to set a root password and make some security choices)
sudo mysql_secure_installation

# Print MySQL server status
sudo systemctl status mysql

# Read MySQL root password from the user
root_password=$(read_input "Enter MySQL root password")

# Read new username and password for shop_admin
shop_admin_password=$(read_input "Enter password for new user 'shop_admin'")

# Create MySQL user, database, and grant privileges
sudo mysql -u root -p"$root_password" <<EOF
CREATE USER 'shop_admin'@'%' IDENTIFIED BY '$shop_admin_password';
CREATE DATABASE IF NOT EXISTS mobile_parts_shop;
GRANT ALL PRIVILEGES ON mobile_parts_shop.* TO 'shop_admin'@'%' WITH GRANT OPTION;
FLUSH PRIVILEGES;
ALTER USER 'shop_admin'@'%' IDENTIFIED WITH 'mysql_native_password' BY '$shop_admin_password';
EOF

echo "MySQL user 'shop_admin' and database 'mobile_parts_shop' created successfully."
