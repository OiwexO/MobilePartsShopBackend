#!/bin/bash

# ==========================================
# CONFIGURATION
# ==========================================
DB_NAME="mobile_parts_shop"
DB_USER="root"

# Base directory where uploaded images are stored
IMAGES_DIR="/home/iwex/Documents/mobile_parts_shop"

# Directory where all backups will be saved
BACKUPS_DIR="/home/iwex/Documents/backups_mobile_parts_shop"

# ==========================================
# LOGIC
# ==========================================

show_menu() {
    echo "========================================="
    echo "  Mobile Parts Shop - Backup Manager"
    echo "========================================="
    echo "1. Create backup (Database + Images)"
    echo "2. Restore from backup"
    echo "3. Exit"
    echo "========================================="
    read -p "Select an option (1-3): " choice

    case $choice in
        1) make_backup ;;
        2) restore_backup ;;
        3) echo "Exiting..."; exit 0 ;;
        *) echo "Invalid choice. Please try again."; echo ""; show_menu ;;
    esac
}

make_backup() {
    # Format the current date and time (Year-Month-Day_Hour-Minute-Second)
    TIMESTAMP=$(date +"%Y-%m-%d_%H-%M-%S")
    CURRENT_BACKUP_DIR="${BACKUPS_DIR}/backup_${TIMESTAMP}"

    echo ""
    echo ">>> Creating backup in directory: $CURRENT_BACKUP_DIR"
    mkdir -p "$CURRENT_BACKUP_DIR"

    echo "1/2 Backing up MySQL database..."
    # Prompting for the database password (avoiding hardcoded credentials for security)
    sudo mysqldump -u "$DB_USER" -p "$DB_NAME" > "$CURRENT_BACKUP_DIR/database.sql"

    echo "2/2 Archiving the images directory..."
    # Create a tar.gz archive with the contents of the images folder
    tar -czf "$CURRENT_BACKUP_DIR/images.tar.gz" -C "$IMAGES_DIR" .

    echo ">>> SUCCESS! Backup saved."
    echo ""
    show_menu
}

restore_backup() {
    echo ""
    # Check if any backups exist in the target directory
    if ! ls -d "$BACKUPS_DIR"/backup_* > /dev/null 2>&1; then
        echo ">>> Error: No backups found in $BACKUPS_DIR"
        echo ""
        show_menu
        return
    fi

    echo ">>> Available backups:"
    # Bash 'select' command generates a convenient numbered menu automatically
    select backup_folder in "$BACKUPS_DIR"/backup_*; do
        if [ -n "$backup_folder" ]; then
            echo "You selected to restore: $(basename "$backup_folder")"
            break
        else
            echo "Invalid choice."
        fi
    done

    echo "1/2 Restoring MySQL database..."
    sudo mysql -u "$DB_USER" -p "$DB_NAME" < "$backup_folder/database.sql"

    echo "2/2 Restoring images..."
    # Ensure the target image directory exists
    mkdir -p "$IMAGES_DIR"

    # Extract files directly into the target directory (overwriting existing files)
    tar -xzf "$backup_folder/images.tar.gz" -C "$IMAGES_DIR"

    echo ">>> SUCCESS! Data restored."
    echo ""
    show_menu
}

# ==========================================
# EXECUTION
# ==========================================

# Ensure the main backup directory exists before running the script
mkdir -p "$BACKUPS_DIR"
show_menu