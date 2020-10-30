# MySQL (version 8) database configuration

    sudo mysql
    CREATE DATABASE RestDatabase;
    CREATE USER RestDatabaseAdmin@localhost IDENTIFIED BY 'rest_database_admin';
    GRANT ALL ON RestDatabase.* TO RestDatabaseAdmin@localhost;
    quit;
    
# MySQL database login

    mysql -u RestDatabaseAdmin -p
    USE RestDatabase;