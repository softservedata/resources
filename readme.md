It is necessary to configure the MySQL server for the correct work with cyrillic characters.
Please check do You server configuration file (my.ini for Windows or my.cnf for Unix-based system) have the following settings:

[client]
default-character-set=utf8

[mysql]
default-character-set=utf8

[mysqld]
collation-server = utf8_unicode_ci
init-connect='SET NAMES utf8'
character-set-server = utf8

All database settings You can change in database.properties file.