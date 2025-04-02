CREATE TABLE user (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      real_name VARCHAR(31),
                      user_name VARCHAR(63),
                      phone_number VARCHAR(15),
                      password CHAR(31),
                      nick_name VARCHAR(31),
                      role ENUM('USER', 'ADMIN') NOT NULL,
                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                      deleted_at TIMESTAMP NULL
);

CREATE TABLE token (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       user_id INT,
                       access_token TEXT,
                       refresh_token CHAR(32),
                       FOREIGN KEY (user_id) REFERENCES user(id)
);

CREATE TABLE restaurant (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            user_id INT,
                            location_id INT,
                            name VARCHAR(30),
                            price TEXT,
                            category ENUM('KOREAN', 'JAPANESE', 'CHINESE', 'WESTERN', 'OTHER'),
                            nut_review DOUBLE,
                            rating DOUBLE,
                            phone_number VARCHAR(15),
                            amenity TEXT,
                            deleted_at TIMESTAMP NULL,
                            image TEXT,
                            business_type ENUM('DINE-IN', 'TAKEOUT', 'ALL'),
                            last_image TIMESTAMP NULL,
                            FOREIGN KEY (user_id) REFERENCES user(id),
                            FOREIGN KEY (location_id) REFERENCES location(id)
);

CREATE TABLE location (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          sido VARCHAR(31),
                          gungu VARCHAR(31),
                          eupmyeondong VARCHAR(31)
);

CREATE TABLE menu (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      restaurant_id INT,
                      name VARCHAR(63),
                      quantity VARCHAR(63) NULL,
                      price TEXT,
                      image TEXT,
                      nut_review INT,
                      rating DOUBLE,
                      description TEXT,
                      FOREIGN KEY (restaurant_id) REFERENCES restaurant(id)
);

CREATE TABLE business_date (
                               id INT AUTO_INCREMENT PRIMARY KEY,
                               restaurant_id INT,
                               day VARCHAR(31),
                               first_order CHAR(5),
                               last_order CHAR(5),
                               break_time CHAR(11),
                               FOREIGN KEY (restaurant_id) REFERENCES restaurant(id)
);

CREATE TABLE reservation (
                             id INT AUTO_INCREMENT PRIMARY KEY,
                             restaurant_id INT,
                             created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                             date DATE,
                             first_order CHAR(5),
                             last_order CHAR(5),
                             capacity INT,
                             interval INT,
                             description TEXT,
                             deleted_at TIMESTAMP NULL,
                             FOREIGN KEY (restaurant_id) REFERENCES restaurant(id)
);

CREATE TABLE reservation_user (
                                  id INT AUTO_INCREMENT PRIMARY KEY,
                                  restaurant_id INT,
                                  reservation_id INT,
                                  user_id INT,
                                  num_people INT,
                                  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                  reservation_at CHAR(5),
                                  state ENUM('PENDING', 'CONFIRMED', 'CANCELED', 'NOSHOW'),
                                  deleted_at TIMESTAMP NULL,
                                  FOREIGN KEY (restaurant_id) REFERENCES restaurant(id),
                                  FOREIGN KEY (reservation_id) REFERENCES reservation(id),
                                  FOREIGN KEY (user_id) REFERENCES user(id)
);

CREATE TABLE review (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        user_id INT,
                        rating DOUBLE,
                        image TEXT,
                        content TEXT NULL,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        updated_at TIMESTAMP NULL,
                        deleted_at TIMESTAMP NULL,
                        object_id INT,
                        FOREIGN KEY (user_id) REFERENCES user(id)
);

CREATE TABLE writing (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         restaurant_id INT,
                         user_id INT,
                         is_visited BOOLEAN,
                         content TEXT,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         deleted_at TIMESTAMP NULL,
                         FOREIGN KEY (restaurant_id) REFERENCES restaurant(id),
                         FOREIGN KEY (user_id) REFERENCES user(id)
);

CREATE TABLE bookmark (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          restaurant_id INT,
                          user_id INT,
                          FOREIGN KEY (restaurant_id) REFERENCES restaurant(id),
                          FOREIGN KEY (user_id) REFERENCES user(id)
);

CREATE TABLE notification (
                              id INT AUTO_INCREMENT PRIMARY KEY,
                              title TEXT,
                              content TEXT,
                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              state ENUM('unread', 'read')
);

CREATE TABLE notification_recipients (
                                         id INT AUTO_INCREMENT PRIMARY KEY,
                                         notification_id INT,
                                         user_id INT,
                                         is_read BOOLEAN,
                                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                         FOREIGN KEY (notification_id) REFERENCES notification(id),
                                         FOREIGN KEY (user_id) REFERENCES user(id)
);
